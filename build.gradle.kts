plugins {
    java
    kotlin("jvm") version "1.3.72"
    id("io.gitlab.arturbosch.detekt") version "1.9.1"

}

group = "it.unisannio.assd.untori"
version = "0.0.1-alpha"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testCompile("junit", "junit", "4.12")
    implementation ("cafe.cryptography:ed25519-elisabeth:0.1.0")
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.9.1")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}