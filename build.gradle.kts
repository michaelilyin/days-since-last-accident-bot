plugins {
    id("io.quarkus")
    kotlin("jvm") version "1.5.31"
    kotlin("kapt") version "1.5.31"
    kotlin("plugin.allopen") version "1.5.31"
    id("org.jetbrains.kotlinx.kover") version "0.5.0"
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

repositories {
    mavenCentral()
    mavenLocal()
}

group = "net.dslab"
version = "1.0.0-SNAPSHOT"

dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

allOpen {
    annotation("javax.ws.rs.Path")
    annotation("javax.enterprise.context.ApplicationScoped")
    annotation("io.quarkus.test.junit.QuarkusTest")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
    kotlinOptions.javaParameters = true
    kotlinOptions.freeCompilerArgs = listOf(
        "-Xjvm-default=all-compatibility"
    )
}

sourceSets.test {
    resources {
        srcDir("src/test/kotlin/resources")
    }
}

dependencies {
    implementation("io.quarkus:quarkus-container-image-docker")
//    implementation("io.quarkus:quarkus-hibernate-orm-panache-kotlin")
    implementation("io.quarkus:quarkus-rest-client")
    implementation("io.quarkus:quarkus-config-yaml")
    implementation("io.quarkus:quarkus-resteasy")
    implementation("io.quarkus:quarkus-resteasy-multipart")
//    implementation("io.quarkus:quarkus-jdbc-postgresql")
    implementation("io.quarkus:quarkus-smallrye-health")
    implementation("io.quarkus:quarkus-micrometer")
    implementation("io.quarkus:quarkus-rest-client-jackson")
    implementation("io.quarkus:quarkus-micrometer-registry-prometheus")
    implementation("io.quarkus:quarkus-websockets-client")
    implementation("io.quarkus:quarkus-hibernate-validator")
    implementation("io.quarkus:quarkus-quartz")
    implementation("io.quarkus:quarkus-kotlin")
    implementation("io.quarkus:quarkus-websockets")
    implementation("io.quarkus:quarkus-jackson")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
//    implementation("io.quarkus:quarkus-liquibase")
    implementation("io.quarkus:quarkus-resteasy-jackson")
    implementation("io.quarkus:quarkus-jackson-spi")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.quarkus:quarkus-arc")
    implementation("com.slack.api:slack-api-client:1.17.0")
    implementation("io.github.microutils:kotlin-logging-jvm:2.1.21")

    implementation("io.quarkiverse.googlecloudservices:quarkus-google-cloud-firestore:0.11.0")
//    implementation("org.mapstruct:mapstruct:1.5.0.Beta1")
//    kapt("org.mapstruct:mapstruct-processor:1.5.0.Beta1")
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.quarkus:quarkus-junit5-mockito")
    testImplementation("io.rest-assured:rest-assured")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
}

//kapt {
//    arguments {
//        arg("mapstruct.verbose", "true")
//        arg("mapstruct.defaultComponentModel", "cdi")
//        arg("mapstruct.defaultInjectionStrategy", "constructor")
//        arg("mapstruct.unmappedTargetPolicy", "ERROR")
//    }
//}
