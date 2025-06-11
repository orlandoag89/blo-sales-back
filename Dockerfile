# Usa la imagen oficial de OpenJDK 11
FROM openjdk:11-jre-slim

# Directorio de trabajo en el contenedor
WORKDIR /app

# Copia el archivo JAR al contenedor
COPY /target/sales-1.0.0-RELEASE.jar /app/sales-app-pre.jar

# Expone el puerto que usa tu servidor
EXPOSE 8181

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "sales-app-pre.jar"]