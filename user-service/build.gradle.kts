import org.openapitools.generator.gradle.plugin.tasks.GenerateTask


plugins {
    id("java")
    id("org.springframework.boot") version "3.3.2"
    id("org.openapi.generator") version "7.6.0"
}

version = "1.0.0"
group = "org.example"
description = "user-service"


val apiVersion = project.property("api.version") as String

val springBootVersion = project.property("springBoot.version") as String
val lombokVersion = project.property("lombok.version") as String
val springDocVersion = project.property("springdoc.version") as String

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:${springBootVersion}"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${springDocVersion}")

    implementation("org.projectlombok:lombok:${lombokVersion}")
    annotationProcessor("org.projectlombok:lombok:${lombokVersion}")
    implementation("org.instancio:instancio-junit:5.0.1")

}

tasks.register<Copy>("uploadApiDoc") {
    group = "apidoc"
    description = "Uploads the documentation artifact"

    configurations.create("docs")
    project.dependencies.add("docs", "org.example:api-doc-service:$version@tar")

    from({
        configurations.getByName("docs")
            .filter { it.name.endsWith("tar") }
            .map { tarTree(it) }
    })
    into(layout.buildDirectory.dir("docs"))
}

tasks.withType(GenerateTask::class.java) {
    dependsOn("uploadApiDoc")
}

project.extensions.getByType(JavaPluginExtension::class).apply {
    sourceSets.getByName("main") {
        java.srcDirs(
            layout.buildDirectory.file("generated/sources/openapi/src/main/java").get().asFile.absolutePath
        )
    }
}

tasks.findByName("compileJava")?.apply {
    dependsOn("generateOpenApiServer")
}
tasks.findByName("sourcesJar")?.apply {
    dependsOn("generateOpenApiServer")
}

tasks.register("generateOpenApiServer", GenerateTask::class.java) {
    group = "build"
    description = "Generates openapi server files $name"

    generatorName.set("spring")
    library.set("spring-boot")
    inputSpec.set("${layout.buildDirectory.get()}/docs/api-doc-service-$apiVersion/services/user-service/openapi.yaml")
    outputDir.set(layout.buildDirectory.file("generated/sources/openapi").get().asFile.absolutePath)
    modelPackage.set("org.example.user.web.dto")
    apiPackage.set("org.example.user.web")
    auth.set("false")
    generateApiDocumentation.set(false)
    generateApiTests.set(false)
    generateModelDocumentation.set(false)
    generateModelTests.set(false)
    enablePostProcessFile.set(true)
    validateSpec.set(true)
    generateAliasAsModel.set(true)

    configOptions.set(
        mapOf(
            "dateLibrary" to "java8",
            "interfaceOnly" to "true",
            "serializableModel" to "true",
            "openApiNullable" to "false",
            "legacyDiscriminatorBehavior" to "true",
            "requestMappingMode" to "api_interface",
            "useBeanValidation" to "true",
            "useJakartaEe" to "true",
            "useResponseEntity" to "false",
            "useSpringBoot3" to "true",
            "useSwaggerUI" to "false",
            "useTags" to "true",
            "useResponseEntity" to "true",
            "documentationProvider" to "springdoc"
        )
    )
}

