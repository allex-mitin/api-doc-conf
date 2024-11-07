import com.github.gradle.node.npm.task.NpxTask
import org.openapitools.generator.gradle.plugin.tasks.GenerateTask


plugins {
    id("java")
    id("org.springframework.boot") version "3.3.2"
    id("org.openapi.generator") version "7.6.0"
    id("com.github.node-gradle.node") version "7.0.2"
}

version = "1.0.0"
group = "org.example"
description = "info-service"


val apiVersion = project.property("api.version") as String

val springBootVersion = project.property("springBoot.version") as String
val lombokVersion = project.property("lombok.version") as String
val springDocVersion = project.property("springdoc.version") as String
val mapstructVersion = project.property("mapstruct.version") as String

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:${springBootVersion}"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${springDocVersion}")
    implementation("org.springframework.kafka:spring-kafka")

    implementation("org.projectlombok:lombok:${lombokVersion}")
    implementation("org.mapstruct:mapstruct:${mapstructVersion}")

    annotationProcessor("org.projectlombok:lombok:${lombokVersion}")
    annotationProcessor("org.mapstruct:mapstruct-processor:${mapstructVersion}")

}

//Download and unzip documentation to folder /build/docs
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

//generate controllers
generateServerFromOpenapi(
    "api-backend",
    "${layout.buildDirectory.get()}/docs/api-doc-service-$apiVersion/services/info-service/openapi.yaml",
    "org.example.info.controller"
)

//generate http client to pet service
generateClientFromOpenapi(
    "pet-service",
    "${layout.buildDirectory.get()}/docs/api-doc-service-$apiVersion/services/pet-service/openapi.yaml",
    "org.example.info.client.pet"
)

//generate http client to user service
generateClientFromOpenapi(
    "user-service",
    "${layout.buildDirectory.get()}/docs/api-doc-service-$apiVersion/services/user-service/openapi.yaml",
    "org.example.info.client.user"
)

//generate dto of asyncapi
generateAsyncapiModel(
    "asyncapi-service",
    "${layout.buildDirectory.get()}/docs/api-doc-service-$apiVersion/services/info-service/asyncapi.yaml",
    "org.example.info.asyncapi"
)

tasks.named("compileJava") {
    dependsOn("uploadApiDoc")
}

tasks.withType(GenerateTask::class.java) {
    dependsOn("uploadApiDoc")
}

tasks.withType(NpxTask::class.java) {
    dependsOn("uploadApiDoc")
}


fun generateServerFromOpenapi(name: String, swaggerPath: String, pkg: String) {

    project.extensions.getByType(JavaPluginExtension::class).apply {
        sourceSets.getByName("main") {
            java.srcDirs(
                layout.buildDirectory.file("generated/sources/openapi/src/main/java").get().asFile.absolutePath
            )
        }
    }

    tasks.findByName("compileJava")?.apply {
        dependsOn("generateServerApiFromOpenapi-$name")
    }
    tasks.findByName("sourcesJar")?.apply {
        dependsOn("generateServerApiFromOpenapi-$name")
    }

    tasks.register("generateServerApiFromOpenapi-$name", GenerateTask::class.java) {
        group = "build"
        description = "Generates openapi server files $name"

        generatorName.set("spring")
        library.set("spring-boot")
        inputSpec.set(swaggerPath)
        outputDir.set(layout.buildDirectory.file("generated/sources/openapi").get().asFile.absolutePath)
        modelPackage.set("$pkg.dto")
        apiPackage.set(pkg)
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
}

fun generateClientFromOpenapi(name: String, swaggerPath: String, pkg: String) {
    tasks.findByName("compileJava")?.apply {
        dependsOn("generateOpenApiClient-$name")
    }
    tasks.findByName("sourcesJar")?.apply {
        dependsOn("generateOpenApiClient-$name")
    }

    project.extensions.getByType(JavaPluginExtension::class).apply {
        sourceSets.getByName("main") {
            java.srcDirs(
                layout.buildDirectory.file("generated/sources/openapi-client/src/main/java").get().asFile.absolutePath
            )
        }
    }


    tasks.register("generateOpenApiClient-$name", GenerateTask::class.java) {
        group = "build"
        description = "Generates openapi client files $name"

        generatorName.set("java")
        library.set("resttemplate")
        inputSpec.set(swaggerPath)
        outputDir.set(layout.buildDirectory.file("generated/sources/openapi-client").get().asFile.absolutePath)
        apiPackage.set("$pkg.client")
        modelPackage.set("$pkg.dto")
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
                "generateClientAsBean" to "false", //For resttemplate, configure whether to create `ApiClient.java` and Apis clients as bean (with `@Component` annotation). (Default: false)
                "serializableModel" to "true",
                "prependFormOrBodyParameters" to "true",
                "openApiNullable" to "false",
                "legacyDiscriminatorBehavior" to "true",
                "useJakartaEe" to "true",
                "useAbstractionForFiles" to "true"
            )
        )
    }
}


node {
    download.set(true)
    version.set("22.9.0")
    allowInsecureProtocol.set(true)
    nodeProjectDir.set(file("${project.rootDir}/.gradle/nodejs-modules"))
    workDir.set(file("${project.rootDir}/.gradle/nodejs"))
    npmWorkDir.set(file("${project.rootDir}/.gradle/npm"))
}

tasks.register("asyncapiInstall", NpxTask::class.java) {
    dependsOn("uploadApiDoc")
    group = "apidoc"
    description = "Install asyncapi dependencies"
    command = "npm"
    args = listOf("install", "@asyncapi/cli", "@asyncapi/modelina", "@asyncapi/java-spring-template")
}

fun generateAsyncapiModel(name: String, path: String, pkg: String) {
    tasks.findByName("compileJava")?.apply {
        dependsOn("generateAsyncApiModel-$name")
    }
    tasks.findByName("sourcesJar")?.apply {
        dependsOn("generateAsyncApiModel-$name")
    }

    project.extensions.getByType(JavaPluginExtension::class).apply {
        sourceSets.getByName("main") {
            java.srcDirs(
                layout.buildDirectory.file("generated/sources/asyncapi/src/main/java").get().asFile.absolutePath
            )
        }
    }

    tasks.register("generateAsyncApiModel-$name", NpxTask::class.java) {
        group = "build"
        description = "Generates asyncapi model files $name"
        dependsOn("asyncapiInstall")

        command = "@asyncapi/cli"
        args = listOf(
            "generate",
            "models",
            "java",
            path,
            "-o",
            layout.buildDirectory.file("generated/sources/asyncapi/src/main/java/${pkg.replace("\\.", "/")}").get().asFile.absolutePath,
            "--packageName=${pkg}",
            "--javaJackson",
            "--javaArrayType=List",
            "--javaIncludeComments",
        )
    }
}
