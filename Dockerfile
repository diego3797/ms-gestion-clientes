# Usa una imagen base de OpenJDK compatible con Java 21
FROM openjdk:21

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo JAR de la aplicación al contenedor
COPY target/ms-gestion-clientes-0.0.1-SNAPSHOT.jar app-gestion-clientes.jar

# Expone el puerto de la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "app-gestion-clientes.jar"]
