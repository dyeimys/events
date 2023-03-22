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
version = "0.0.1-SNAPSHOT"
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

jacoco {
    toolVersion = "0.8.7"
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
                exclude(
//                        "**/entities/**",
//                        "**/mapper**/**",
//                        "**/dtos/**",
//                        "**/request/**",
//                        "**/response/**",
//                        "**/enuns/**",

                        )
            }
        }))
    }
}