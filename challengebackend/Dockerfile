#Imagen de base de java 21


FROM openjdk:21-jdk-slim as builder

# Directorio de trabajo para el contenedor
WORKDIR /app/challengebackend

#Copi
#COPY ./pom.xml /app
COPY .mvn  .mvn
COPY mvnw  pom.xml ./
#COPY ./challengebackend/.mvnw  .
#COPY ./challengebackend/pom.xml  .


RUN ./mvnw dependency:go-offline -B


#Copiamos el codigo fuenteamos e codigo fuente
COPY src  ./src


RUN ./mvnw package -DskipTests -B

FROM  openjdk:21-jdk-slim


WORKDIR  /app

COPY --from=builder /app/target/challengebackend-0.0.1-SNAPSHOT.jar .
EXPOSE 8080

LABEL authors="juanDavid"


ENTRYPOINT ["java", "-jar", "challengebackend-0.0.1-SNAPSHOT.jar"]