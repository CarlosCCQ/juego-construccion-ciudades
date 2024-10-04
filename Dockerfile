FROM maven:3.8.7-openjdk-18 AS build
WORKDIR /build
COPY pom.xml .

# Descargar dependencias
RUN mvn dependency:go-offline

# Copiar el código fuente
COPY src ./src

# Compilar y empaquetar la aplicación
RUN mvn clean package -DskipTests

# Runtime stage
FROM amazoncorretto:17
ARG PROFILE=dev
ARG APP_VERSION=0.0.1-SNAPSHOT

# Establecer el directorio de trabajo para la aplicación
WORKDIR /app

# Copiar el JAR generado de la etapa de construcción
COPY --from=build /build/target/juego.construccion.ciudades-*.jar /app/city_game.jar

# Exponer el puerto en el que la aplicación escuchará
EXPOSE 8081

# Configurar variables de entorno
ENV DB_URL=jdbc:postgresql://postgres-sql:5432/city_game_db
ENV ACTIVE_PROFILE=${PROFILE}
ENV JAR_VERSION=${APP_VERSION}

# Comando para ejecutar la aplicación
CMD java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 -Dspring.profiles.active=${ACTIVE_PROFILE} -Dspring.datasource.url=${DB_URL} city_game.jar