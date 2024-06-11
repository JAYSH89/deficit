plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.serialization)
    alias(libs.plugins.detekt)
    alias(libs.plugins.flyway)
    application
}

group = "nl.jaysh"
version = "1.0.0"

application {
    mainClass.set("nl.jaysh.ApplicationKt")
    applicationDefaultJvmArgs =
        listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

dependencies {
    implementation(projects.shared)
    implementation(libs.bundles.ktor.server)
    implementation(libs.bundles.persistence)
    implementation(libs.bundles.koin.server)

    implementation(libs.logback)
    implementation(libs.bcrypt)
    implementation(libs.kotlinx.serialization)
    implementation(libs.kotlinx.coroutines)

    detektPlugins(libs.detekt.formatting)

    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.koin.test)
    testImplementation(libs.koin.test.junit)
    testImplementation(libs.h2)
}
