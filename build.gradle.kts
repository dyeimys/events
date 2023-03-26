import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.0.4"
    id("io.spring.dependency-management") version "1.1.0"
    id("jacoco")
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
    kotlin("plugin.jpa") version "1.7.22"
    kotlin("kapt") version "1.5.10"
}


group = "br.dfranco.learn"
version = "0.2.0"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.mapstruct:mapstruct:1.5.3.Final")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.4")

    kapt("org.mapstruct:mapstruct-processor:1.5.3.Final")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    runtimeOnly("com.h2database:h2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
kapt {
    arguments {
        arg("mapstruct.defaultComponentModel", "spring")
    }
}

tasks.getByName<Jar>("jar") {
    enabled = false
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.test {
    extensions.configure(JacocoTaskExtension::class) {

    }

    finalizedBy("jacocoTestReport")
}

val fileFilter = listOf(
        "**/entities/**",
        "**/dtos/**",
        "**/requests/**",
        "**/responses/**")

jacoco {
    toolVersion = "0.8.7"
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "0.8".toBigDecimal()
            }
        }
    }
    classDirectories.setFrom(files(classDirectories.files.map {
        fileTree(it).apply {
            exclude(fileFilter)
        }
    }))
}


tasks.withType<JacocoReport> {
    reports {
        xml.required.set(true)
        csv.required.set(true)
        html.required.set(true)
    }
    afterEvaluate {
        classDirectories.setFrom(files(classDirectories.files.map {
            fileTree(it).apply {
                exclude(fileFilter)
            }
        }))
    }
}

// ./gradlew incrementVersion [-P[mode=major|minor|patch]|[overrideVersion=x]]
tasks.create("incrementVersion") {
    group = "my tasks"
    description = "Increments the version in this build file everywhere it is used."
    fun generateVersion(): String {
        val updateMode = properties["mode"] ?: "minor" // By default, update the minor
        val (oldMajor, oldMinor, oldPatch) = version.toString().split(".").map(String::toInt)
        var (newMajor, newMinor, newPatch) = arrayOf(oldMajor, oldMinor, 0)
        when (updateMode) {
            "major" -> newMajor = (oldMajor + 1).also { newMinor = 0 }
            "minor" -> newMinor = oldMinor + 1
            else -> newPatch = oldPatch + 1
        }
        println("Version before increment: $version")
        val incrementedVersion = "$newMajor.$newMinor.$newPatch"
        println("Version after increment: $incrementedVersion")

        return incrementedVersion
    }
    doLast {
        val newVersion = properties["overrideVersion"] as String? ?: generateVersion()
        val oldContent = buildFile.readText()
        val newContent = oldContent.replace("""= "$version"""", """= "$newVersion"""")
        buildFile.writeText(newContent)
    }

}


// ./gradlew printVersion
tasks.create("printVersion") {
    doLast {
        print(version)
    }
}
