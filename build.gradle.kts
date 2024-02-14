import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.21"
    kotlin("plugin.spring") version "1.9.21"
    kotlin("plugin.jpa") version "1.9.21"
    kotlin("kapt") version "1.9.21"
}

group = "com.hanssem"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

val querydslVersion = "5.0.0"
val mapstructVersion = "1.5.2.Final"

dependencies {
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    /* Spring Boot */
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-security")

    /* JWT */
    implementation("io.jsonwebtoken:jjwt-api:0.12.5")
    implementation("io.jsonwebtoken:jjwt-impl:0.12.5")
    implementation("io.jsonwebtoken:jjwt-jackson:0.12.5")

    /* Swagger */
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")

    /* QueryDSL */
    implementation("com.querydsl:querydsl-jpa:$querydslVersion:jakarta")
    kapt("com.querydsl:querydsl-apt:${querydslVersion}:jakarta")
    kapt ("jakarta.annotation:jakarta.annotation-api")
    kapt ("jakarta.persistence:jakarta.persistence-api")

    /*Map Struct*/
    implementation("org.mapstruct:mapstruct:${mapstructVersion}")
    kapt("org.mapstruct:mapstruct-processor:${mapstructVersion}")
    kaptTest("org.mapstruct:mapstruct-processor:${mapstructVersion}")

    /* Redis */
    implementation("it.ozimov:embedded-redis:0.7.2")

    runtimeOnly("com.mysql:mysql-connector-j")

    /*QueryDSL*/

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
    annotation("jakarta.persistence.MappedSuperclass")
}

kapt {
    javacOptions {
        option("querydsl.entityAccessors", true)
    }
    arguments {
        arg("plugin", "com.querydsl.apt.jpa.JPAAnnotationProcessor")
    }
}

noArg {
    annotation("jakarta.persistence.Entity")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
