# Challenge Backend - API de Cálculo con Historial

Este es un proyecto de backend desarrollado en **Java 21** utilizando el framework **Spring Boot 3**. El objetivo principal es proporcionar una API REST para realizar un cálculo específico (suma de dos números más un porcentaje obtenido externamente) y gestionar un historial detallado de cada llamada a la API, persistido en una base de datos PostgreSQL.

## Características Principales

-   **API REST:** Expone un endpoint para realizar el cálculo.
-   **Integración Externa:** Obtiene un valor porcentual de un servicio externo (simulado).
-   **Caché:** Utiliza Spring Cache (con Caffeine) para almacenar en caché el porcentaje obtenido, reduciendo las llamadas al servicio externo y mejorando el rendimiento. Incluye una estrategia de *fallback* para usar el último valor conocido si el servicio externo falla.
-   **Persistencia:** Guarda un registro de cada llamada (endpoint, método HTTP, parámetros, timestamp, respuesta o error, código de estado) en una base de datos PostgreSQL.
-   **Procesamiento Asíncrono:** El guardado del historial se realiza de forma asíncrona (`@Async`) para no retrasar la respuesta al cliente.
-   **Manejo de Excepciones:** Implementa manejo de errores centralizado con `@ExceptionHandler` para devolver respuestas JSON estructuradas y códigos de estado HTTP apropiados (400, 500).
-   **Dockerización:** Incluye `Dockerfile` y configuración para `docker-compose` (asumida) para facilitar la construcción y ejecución de la aplicación y la base de datos en contenedores.
-   **DTOs:** Utiliza Data Transfer Objects para definir claramente los contratos de la API.

## Tecnologías Utilizadas

-   **Java 21**
-   **Spring Boot 3.x**
-   **Spring Web** (API REST)
-   **Spring Data JPA** (Persistencia con PostgreSQL)
-   **Spring Cache** (Caché con Caffeine)
-   **Spring AOP** (Para `@Async` y `@Transactional`)
-   **PostgreSQL** (Base de datos relacional)
-   **Hibernate** (Implementación JPA)
-   **Maven** (Gestión de dependencias y construcción)
-   **Docker & Docker Compose** (Contenerización)
-   **SLF4J & Logback** (Logging)

## Estructura del Proyecto

-   `controller`: Controladores REST (`CalculatorController`) que manejan las solicitudes HTTP.
-   `service`: Contiene la lógica de negocio (`CalculatorService`, `HistoryService`, `ExternalPercentageService`).
-   `entity`: Entidades JPA (`CallHistory`) que mapean las tablas de la base de datos.
-   `repository`: Interfaces Spring Data JPA (`CallHistoryRepository`) para interactuar con la base de datos.
-   `dto`: Objetos de Transferencia de Datos (`CalculationRequestDTO`, `CalculationResponseDTO`, `ErrorResponse`) para las solicitudes y respuestas de la API.
-   `config`: Clases de configuración (`CacheConfig`) para habilitar funcionalidades de Spring como Cache y Async, y definir beans como el `CacheManager`.

## Prerrequisitos

-   JDK 21 o superior.
-   Maven 3.6 o superior.
-   Docker y Docker Compose.

## Configuración

La configuración de la base de datos se gestiona principalmente a través de variables de entorno en `docker-compose.yml` que sobrescriben las propiedades de `application.properties`. Asegúrate de que las credenciales en `docker-compose.yml` sean seguras.

-   `SPRING_DATASOURCE_URL`: URL de conexión JDBC (ej. `jdbc:postgresql://db:5432/challenge_db`).
-   `SPRING_DATASOURCE_USERNAME`: Usuario de la base de datos.
-   `SPRING_DATASOURCE_PASSWORD`: Contraseña de la base de datos.

La configuración de la caché (TTL, tamaño) se encuentra en `CacheConfig.java`.

## Cómo Construir el Proyecto

Puedes construir el archivo JAR ejecutable usando Maven: