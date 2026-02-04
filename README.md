# Log Service

Microservicio de registros y logs para la plataforma Metradingplat. Centraliza la recoleccion, almacenamiento y consulta de logs de todos los microservicios de la plataforma.

## Tecnologias

- Java 21
- Spring Boot 3.5.10
- Spring Cloud 2025.0.0
- Spring Data JPA
- PostgreSQL
- Apache Kafka
- Eureka Client (Service Discovery)
- MapStruct
- Lombok
- Docker

## Arquitectura

El servicio implementa arquitectura hexagonal:

```
src/main/java/com/metradingplat/log_service/
├── application/          # Puertos de entrada y salida
├── domain/               # Modelos, enums y casos de uso
└── infrastructure/       # Adaptadores (REST, Kafka, JPA)
```

## Requisitos

- JDK 21+
- Maven 3.9+
- PostgreSQL 15+
- Apache Kafka
- Eureka Server

## Configuracion

El servicio se ejecuta en el puerto `8084` por defecto.

Variables de entorno principales:
- `SPRING_PROFILES_ACTIVE`: Perfil activo (`dev`, `prod`)
- `SPRING_DATASOURCE_URL`: URL de conexion PostgreSQL
- `SPRING_DATASOURCE_USERNAME`: Usuario de base de datos
- `SPRING_DATASOURCE_PASSWORD`: Password de base de datos
- `EUREKA_CLIENT_SERVICEURL_DEFAULTZONE`: URL del servidor Eureka
- `SPRING_KAFKA_BOOTSTRAP_SERVERS`: Servidores Kafka

## Ejecucion

### Local

```bash
./mvnw spring-boot:run
```

### Docker

```bash
docker build -t log-service .
docker run -p 8084:8084 log-service
```

## Endpoints

| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| GET | `/api/logs` | Listar logs |
| GET | `/api/logs/{id}` | Obtener log por ID |
| GET | `/actuator/health` | Health check |

## Funcionalidades

- **Kafka Consumer**: Recibe logs de otros microservicios
- **Kafka Producer**: Publica eventos a notification-service
- **Persistencia**: Almacenamiento en PostgreSQL
- **Niveles de log**: DEBUG, INFO, WARN, ERROR
- **Categorias**: SISTEMA, SEGURIDAD, NEGOCIO, AUDITORIA
- **Internacionalizacion**: Soporte para ES/EN

## Testing

```bash
./mvnw test
```
