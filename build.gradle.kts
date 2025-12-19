plugins {
    kotlin("jvm") version "1.8.21"
    `maven-publish`
    signing
}

group = "com.revoid"

val githubUsername: String = project.findProperty("gpr.user") as? String ?: System.getenv("GITHUB_ACTOR")
val githubPassword: String = project.findProperty("gpr.key") as? String ?: System.getenv("GITHUB_TOKEN")

repositories {
    mavenCentral()
    mavenLocal()
    maven { url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots") }
    maven { url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2") }
    maven {
        url = uri("https://maven.pkg.github.com/CUPUL-MIU-04/smali")
        credentials {
            username = githubUsername
            password = githubPassword
        }
    }
}

dependencies {
    implementation("xpp3:xpp3:1.1.4c")
    implementation("com.revoid:smali:2.5.3-a3836655")
    implementation("com.revoid:multidexlib2:2.5.3-a3836655-SNAPSHOT")
    implementation("io.github.reandroid:ARSCLib:1.1.7")

    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.22")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.9.22")

    compileOnly("com.google.android:android:4.1.1.4")
}

tasks {
    test {
        useJUnitPlatform()
        testLogging {
            events("PASSED", "SKIPPED", "FAILED")
        }
    }
    processResources {
        expand("projectVersion" to project.version)
    }
}

java {
    withSourcesJar()
}

kotlin {
    jvmToolchain(17)
}

publishing {
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
            
            groupId = "com.revoid"
            artifactId = "revoid-patcher"
            version = project.version.toString()
            
            pom {
                name = "ReVoid Patcher"
                description = "Patcher library for ReVoid projects"
                url = "https://github.com/CUPUL-MIU-04/Revoid-patcher"
                
                licenses {
                    license {
                        name = "GNU General Public License v3.0"
                        url = "https://www.gnu.org/licenses/gpl-3.0.en.html"
                    }
                }
                
                developers {
                    developer {
                        id = "cupul-miu-04"
                        name = "cupul-miu-04"
                    }
                }
                
                scm {
                    connection = "scm:git:git://github.com/CUPUL-MIU-04/Revoid-patcher.git"
                    developerConnection = "scm:git:ssh://github.com:CUPUL-MIU-04/Revoid-patcher.git"
                    url = "https://github.com/CUPUL-MIU-04/Revoid-patcher"
                }
            }
        }
    }
    
    repositories {
        if (githubUsername != null && githubPassword != null) {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/CUPUL-MIU-04/Revoid-patcher")
                credentials {
                    username = githubUsername
                    password = githubPassword
                }
            }
        }
        
        val ossrhToken = System.getenv("OSSRH_TOKEN")
        val ossrhPassword = System.getenv("OSSRH_PASSWORD")
        if (ossrhToken != null && ossrhPassword != null) {
            maven {
                name = "Sonatype"
                url = if (project.version.toString().contains("SNAPSHOT")) {
                    uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
                } else {
                    uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                }
                credentials {
                    username = ossrhToken
                    password = ossrhPassword
                }
            }
        } else {
            mavenLocal()
        }
    }
}

signing {
    if (
        System.getenv("GPG_KEY_ID") == null
        || System.getenv("GPG_KEY") == null
        || System.getenv("GPG_KEY_PASSWORD") == null
    ) return@signing
    useInMemoryPgpKeys(
        System.getenv("GPG_KEY_ID"),
        System.getenv("GPG_KEY"),
        System.getenv("GPG_KEY_PASSWORD"),
    )
    sign(publishing.publications)
}
