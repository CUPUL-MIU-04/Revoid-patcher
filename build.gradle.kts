plugins {
    kotlin("jvm") version "1.8.21"
    `maven-publish`
    signing
    `java-library`
}

group = "com.revoid"
version = "1.0.0" // Asegúrate de tener una versión

val githubUsername: String = project.findProperty("gpr.user") as? String ?: System.getenv("GITHUB_USER")
val githubToken: String = project.findProperty("gpr.key") as? String ?: System.getenv("GITHUB_TOKEN")

repositories {
    mavenCentral()
}

java {
    withSourcesJar()
    withJavadocJar()
}

tasks {
    val javadocJar by existing(Jar::class) {
        archiveClassifier.set("javadoc")
    }
}

publishing {
    publications {
        create<MavenPublication>("github") {
            from(components["java"])
            
            groupId = "com.revoid"
            artifactId = "revoid-patcher"
            version = version
            
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
                        email.set("tu-email@ejemplo.com")
                    }
                }
                
                scm {
                    connection.set("scm:git:git://github.com/CUPUL-MIU-04/Revoid-patcher.git")
                    developerConnection.set("scm:git:ssh://github.com/CUPUL-MIU-04/Revoid-patcher.git")
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
                password = githubToken
            }
        }
    }
}

// Firma opcional (solo si publicas en Maven Central)
signing {
    val signingKey: String? by project
    val signingPassword: String? by project
    useInMemoryPgpKeys(signingKey, signingPassword)
    sign(publishing.publications["github"])
}
