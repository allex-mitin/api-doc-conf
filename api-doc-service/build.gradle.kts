import com.github.gradle.node.npm.task.NpxTask
import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

buildscript {
    dependencies {
        classpath("org.openapitools:openapi-generator-gradle-plugin:7.6.0")
        classpath("com.github.node-gradle:gradle-node-plugin:7.0.2")
    }
}

apply(plugin = "org.openapi.generator")
apply(plugin = "com.github.node-gradle.node")

plugins {
    distribution
    `maven-publish`
    id("com.github.node-gradle.node") version "7.0.2"

}

description = "api-doc-service"
version = property("api.version") as String


var buildSourcesDir = "${layout.buildDirectory.get()}/sources"
var buildGeneratedDir = "${layout.buildDirectory.get()}/generated"
var buildDistrDir = "${layout.buildDirectory.get()}/dist"

distributions {
    main {
        contents {
            from(buildDistrDir)
        }
    }
}

tasks.withType<Zip> {
    enabled = false
}

tasks.withType<Tar> {
    dependsOn("dist")
}

publishing {
    publications {
        create<MavenPublication>("publish-tar") {
            artifact(tasks.distTar.get())
            groupId = "org.example"
            artifactId = "api-doc-service"
            version = version
        }
    }
    repositories {
        mavenLocal()
    }
}

// Settings for nodeJs plugin
node {
    download.set(true)
    version.set("18.17.1")
    allowInsecureProtocol.set(true)
    nodeProjectDir.set(file("${project.projectDir}"))
}

//Step 1: copy sources to build dir. In this step we can do something with files after copy
tasks.create("sources") {
    group = BasePlugin.BUILD_GROUP
    description = "Copy sources to build directory"
    doLast {
        copy {
            from("${projectDir}/api-doc/")
            into(buildSourcesDir)
        }
    }
}

tasks.assemble {
    dependsOn("sources")
}

//Step 2: generate openapi specifications
tasks.register("openapi") {
    dependsOn("sources")
    group = "build openapi"
    description = "Prepare openapi validated and compiled files"
    doLast {
        println("Generate openapi...")
    }
}

tasks.register("asyncapi") {
    dependsOn("sources")
    group = "build asyncapi"
    description = "Build asyncapi files"
    doLast {
        println("Generate asyncapi...")
    }
}

//Step 3: create tar dist
tasks.register("dist") {
    description = "Generate documentation"
    group = "distribution"
    dependsOn("openapi", "asyncapi")
}


//create tasks for build openapi files and copy to dist folder
fileTree(buildSourcesDir).matching {
    include("**/openapi.yaml", "**/openapi.yml", "**/openapi.json")
}.forEachIndexed { index, el ->
    println("Generate openapi for ${index}. ${el.toURI()}")
    var buildFolder = "${buildGeneratedDir}/openapi/${el.relativeTo(file(buildSourcesDir)).parent}"

    tasks.register("generate-openapi-${index}", GenerateTask::class.java) {
        group = "build openapi"
        generatorName.set("openapi-yaml")
        inputSpec.set(el.path)
        outputDir.set(buildFolder)
        validateSpec.set(true)
        configOptions.set(
            mapOf(
                "allowUnicodeIdentifiers" to "false",
                "disallowAdditionalPropertiesIfNotPresent" to "false",
                "ensureUniqueParams" to "true",
                "enumUnknownDefaultCase" to "false",
                "legacyDiscriminatorBehavior" to "true",
                "prependFormOrBodyParameters" to "false",
                "sortModelPropertiesByRequiredFlag" to "true",
                "sortParamsByRequiredFlag" to "true"
            )
        )
        doLast {
            copy {
                from("${buildFolder}/openapi")
                include("*.yaml")
                into("${buildDistrDir}/${el.relativeTo(file(buildSourcesDir)).parent}")
            }
        }
        dependsOn("sources")
    }

    tasks.named("openapi") {
        dependsOn("generate-openapi-${index}")
    }
}




//create tasks for build asyncapi files and copy to dist folder
fileTree(buildSourcesDir).matching {
    include("**/asyncapi.yaml", "**/asyncapi.yml", "**/asyncapi.json")
}.forEachIndexed { index, el ->
    println("Generate asyncapi for ${index}. ${el.toURI()}")


    tasks.register<NpxTask>("generate-asyncapi-${index}", NpxTask::class) {
        val buildFolder = "${buildGeneratedDir}/asyncapi/${el.relativeTo(file(buildSourcesDir)).parent}"

        doFirst {
            mkdir(buildFolder)
        }


        description = "Build asyncapi file ${el.toURI()}"
        group = "build asyncapi"
        command.set("@asyncapi/cli")
        workingDir.set(el.parentFile)
        args.set(
            listOf(
                "bundle",
                el.toURI().path,
                "-o",
                "${buildFolder}/asyncapi.yaml",
                "-x"
            )
        )

        doLast {
            copy {
                from("${buildFolder}")
                include("*.yaml")
                into("${buildDistrDir}/${el.relativeTo(file(buildSourcesDir)).parent}")
            }
        }
        dependsOn("sources")

    }

    tasks.named("asyncapi") {
        dependsOn("generate-asyncapi-${index}")
    }
}

