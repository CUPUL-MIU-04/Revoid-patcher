#!/data/data/com.termux/files/usr/bin/bash

# Configuración para Termux
export JAVA_HOME=/data/data/com.termux/files/usr/lib/jvm/openjdk-21
export PATH=$JAVA_HOME/bin:$PATH

# También necesitamos exportar GITHUB_ACTOR y GITHUB_TOKEN si existen
if [ -f "gradle.properties" ]; then
    source <(grep -E '^(gpr\.user|gpr\.key)=' gradle.properties | sed 's/\./_/g' | sed 's/=/="/' | sed 's/$/"/')
    export GITHUB_ACTOR=${gpr_user}
    export GITHUB_TOKEN=${gpr_key}
fi

echo "=== Configuración Termux ==="
echo "JAVA_HOME: $JAVA_HOME"
echo "Java version: $(java -version 2>&1 | head -1)"
echo "Gradle version:"

# Ejecutar Gradle
./gradlew $@ --no-daemon
