import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootWar

plugins {
  id("org.springframework.boot") version "2.3.0.RELEASE"
  id("io.spring.dependency-management") version "1.0.9.RELEASE"
  war
  kotlin("jvm") version "1.3.72"
  kotlin("plugin.spring") version "1.3.72"
  kotlin("plugin.jpa") version "1.3.72"
  checkstyle
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
  sourceCompatibility = JavaVersion.VERSION_11
  targetCompatibility = JavaVersion.VERSION_11
}

val ui by configurations.creating
configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

repositories {
  mavenCentral()
}

dependencies {
  // implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  // implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
  // implementation("org.springframework.boot:spring-boot-starter-data-rest")
  // implementation("org.springframework.boot:spring-boot-starter-jersey")
  implementation("org.springframework.boot:spring-boot-starter-web")
  // implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("org.springframework.boot:spring-boot-starter-aop")
  implementation("org.liquibase:liquibase-core")
  // implementation("org.springframework.data:spring-data-rest-hal-browser")
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.security:spring-security-oauth2-resource-server")
  implementation("org.springframework.security:spring-security-oauth2-jose")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
  compileOnly("org.projectlombok:lombok")
  developmentOnly("org.springframework.boot:spring-boot-devtools")
  runtimeOnly("com.h2database:h2")
  runtimeOnly("org.postgresql:postgresql")
  // runtimeOnly("io.r2dbc:r2dbc-h2")
  // runtimeOnly("io.r2dbc:r2dbc-postgresql")
  annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
  annotationProcessor("org.projectlombok:lombok")
  // providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
  testImplementation("org.springframework.boot:spring-boot-starter-test") {
    exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
  }
  // testImplementation("io.projectreactor:reactor-test")
  testImplementation("org.springframework.security:spring-security-test")
  ui(project(":ui"))
}

tasks.withType<BootWar> {
  classpath(ui)
}

tasks.withType<Test> {
  useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = sourceCompatibility
  }
}

checkstyle {
  toolVersion = "8.28"
}
