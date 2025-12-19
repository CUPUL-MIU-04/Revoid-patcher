plugins {
    kotlin("jvm") version "1.8.21"
    `maven-publish`
    signing
    `java-library`
}

// IMPORTANTE: Define explícitamente la versión
version = "1.0.0"  // Usa la misma versión que tu release v1.0.0
group = "com.revoid"

repositories {
    mavenCentral()
}

java {
    withSourcesJar()
    withJavadocJar()
}

publishing {
    publications {
        create<MavenPublication>("revoid-patcher") {
            // IMPORTANTE: Cambia esto a 'repositories' para usar el nombre correcto
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
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
