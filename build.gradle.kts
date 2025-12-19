plugins {
    kotlin("jvm") version "1.8.21"
    `maven-publish`
    signing
    `java-library`
}

group = "com.revoid"
// IMPORTANTE: Define la versión aquí o déjala como variable
version = "1.0.0" // O usa: version = project.properties["version"] ?: "1.0.0-SNAPSHOT"

// IMPORTANTE: Cambia estas líneas para usar GITHUB_ACTOR y GITHUB_TOKEN
val githubUsername: String = project.findProperty("gpr.user") as? String ?: System.getenv("GITHUB_ACTOR")
val githubPassword: String = project.findProperty("gpr.key") as? String ?: System.getenv("GITHUB_TOKEN")

repositories {
    mavenCentral()
    // IMPORTANTE: Asegúrate de que estas URLs sean correctas
    maven { url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots") }
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
    
    // Añade esta tarea para procesar recursos correctamente
    processResources {
        filteringCharset = "UTF-8"
        filesMatching("**/*.properties") {
            expand(project.properties)
        }
    }
}

java {
    withSourcesJar()
    withJavadocJar()
}

kotlin {
    jvmToolchain(17)
}

publishing {
    publications {
        create<MavenPublication>("github") {
            from(components["java"])
            
            groupId = "com.revoid"
            artifactId = "revoid-patcher"
            version = project.version.toString()
            
            pom {
                name.set("ReVoid Patcher")
                description.set("Patcher library for ReVoid projects")
                url.set("https://github.com/CUPUL-MIU-04/Revoid-patcher")
                
                licenses {
                    license {
                        name.set("GNU General Public License v3.0")
                        url.set("https://www.gnu.org/licenses/gpl-3.0.en.html")
                    }
                }
                
                developers {
                    developer {
                        id.set("cupul-miu-04")
                        name.set("CUPUL MIU 04 Team")
                    }
                }
                
                scm {
                    connection.set("scm:git:git://github.com/CUPUL-MIU-04/Revoid-patcher.git")
                    developerConnection.set("scm:git:ssh://github.com:CUPUL-MIU-04/Revoid-patcher.git")
                    url.set("https://github.com/CUPUL-MIU-04/Revoid-patcher")
                }
            }
        }
    }
    
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/CUPUL-MIU-04/Revoid-patcher")
            credentials {
                username = githubUsername
                password = githubPassword
            }
        }
    }
}

// Configuración opcional para signing (si quieres firmar los paquetes)
signing {
    // Solo firma si las variables de entorno están definidas
    val signingKey = System.getenv("SIGNING_KEY")
    val signingPassword = System.getenv("SIGNING_PASSWORD")
    
    if (signingKey != null && signingPassword != null) {
        useInMemoryPgpKeys(signingKey, signingPassword)
        sign(publishing.publications["github"])
    }
}
