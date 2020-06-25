plugins {
    `java-library`
    `maven-publish`
    kotlin("jvm") version "1.3.72"
    id("io.gitlab.arturbosch.detekt") version "1.9.1"
    id ("com.jfrog.bintray") version "1.8.5"
}

group = "it.unisannio.assd.untori"
version = "0.0.1-alpha-2"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation ("cafe.cryptography:ed25519-elisabeth:0.1.0")

    testCompile("junit", "junit", "4.12")
    testImplementation ("org.amshove.kluent:kluent:1.61")

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.9.1")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name.set(project.name)
                description.set("A Kotlin implementation of the TCN protocol.")
                url.set("https://git.norangeb.it/norangebit/TKN")
                licenses {
                    license {
                        name.set("The MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
            }
        }
    }
}

bintray {
    user = properties["bintray.user"].toString()
    key = properties["bintray.key"].toString()
    publish = true

    setPublications("mavenJava")

    pkg.apply {
        repo = "untori"
        name = "TKN"
        userOrg = "untori"
        vcsUrl = "https://git.norangeb.it/norangebit/TKN"
        description = "A Kotlin implementation of the TCN protocol."
        setLicenses("MIT")
        desc = description

        version.name = "0.0.1-alpha-2"
    }

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
