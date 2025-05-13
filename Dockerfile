# Usa una imagen base de OpenJDK
FROM openjdk:23-jdk-slim

RUN apt-get update && apt-get install -y iputils-ping && rm -rf /var/lib/apt/lists/*

# Crea un directorio de trabajo
WORKDIR /app

# Copia el archivo .jar generado al contenedor
COPY target/Proyectoud3-1.0-SNAPSHOT.jar /app/Proyectoud3-1.0-SNAPSHOT.jar

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "/app/Proyectoud3-1.0-SNAPSHOT.jar"]
