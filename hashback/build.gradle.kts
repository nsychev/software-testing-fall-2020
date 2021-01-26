import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("io.qameta.allure") version "2.8.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.springframework.boot") version "2.4.4"
    kotlin("jvm") version "1.4.30"
    kotlin("plugin.spring") version "1.4.30"
}

group = "ru.nsychev"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("org.jetbrains.kotlin:kotlin-reflect:1.4.30")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.30")

    implementation("org.springdoc:springdoc-openapi-kotlin:1.5.6")
    implementation("org.springdoc:springdoc-openapi-ui:1.5.6")

    implementation("com.auth0:java-jwt:3.14.0")
    implementation("org.bouncycastle:bcprov-jdk15on:1.68")

    implementation("com.github.kittinunf.fuel:fuel:2.3.1")
    implementation("com.github.kittinunf.fuel:fuel-jackson:2.3.1")

    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")

    runtimeOnly("org.postgresql:postgresql")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")

    testImplementation("org.mockito:mockito-core:3.8.0")
    testImplementation("org.testcontainers:testcontainers:1.15.2")
    testImplementation("org.testcontainers:junit-jupiter:1.15.2")
    testImplementation("org.testcontainers:postgresql:1.15.2")

    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")

    testRuntimeOnly("io.qameta.allure:allure-junit5:2.8.1")
    testImplementation("io.qameta.allure:allure-java-commons:2.8.1")
    testImplementation("io.qameta.allure:allure-attachments:2.8.1")
    testImplementation("io.qameta.allure:allure-generator:2.8.1")
    testImplementation("io.qameta.allure:allure-httpclient:2.8.1")
}

tasks.test {
    useJUnitPlatform()
    systemProperty("allure.results.directory", "$projectDir/build/allure-results")
    testLogging {
        events("PASSED", "FAILED", "SKIPPED", "STANDARD_OUT", "STANDARD_ERROR")
    }
    testLogging.showStandardStreams = true
    dependsOn("cleanTest")
}

allure {
    autoconfigure = true
    version = "2.13.5"
    reportDir = file("$projectDir/build/allure-report")
    resultsDir = file("$projectDir/build/allure-results")
    downloadLinkFormat = "https://dl.bintray.com/qameta/maven/io/qameta/allure/allure-commandline/%s/allure-commandline-%<s.zip"
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}
