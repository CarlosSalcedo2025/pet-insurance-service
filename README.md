# Pet Insurance Service - AseguraTuPata (Prueba Técnica)

Backend reactivo para la gestión de seguros de mascotas, enfocado en el ciclo de vida de venta: Cotización, Expedición y Facturación.

## 🚀 Tecnologías
- **Java 21**: Uso de Records, Pattern Matching y Programación Funcional.
- **Spring Boot 3.2.5**: Framework base.
- **Spring WebFlux**: Stack 100% reactivo y no bloqueante.
- **Spring Data R2DBC**: Persistencia reactiva.
- **PostgreSQL**: Base de datos relacional.
- **Docker Compose**: Orquestación de servicios.
- **Lombok & Jakarta Validation**: Productividad y validación de datos.

## 🏗️ Arquitectura
El proyecto sigue los principios de **Arquitectura Hexagonal (Clean Architecture)**:
- **Dominio**: Reglas de negocio puras (QuoteCalculator), sin dependencias externas.
- **Aplicación**: Casos de uso que orquestan el negocio (Interactors).
- **Infraestructura**: Adaptadores para REST (WebFlux) y Persistencia (R2DBC).

Consulta el detalle en [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md).

---

## 🛠️ Cómo Ejecutar

### Requisitos
- Docker y Docker Compose instalados.
- Java 21 (opcional si solo usas Docker para la DB).

### Pasos
1. **Levantar la Base de Datos**:
   ```bash
   docker-compose up -d
   ```
   *Nota: La DB corre en el puerto `5433` para evitar conflictos con instalaciones locales.*

2. **Ejecutar la Aplicación**:
   ```bash
   ./gradlew bootRun
   ```

3. **Ejecutar Pruebas**:
   ```bash
   ./gradlew test
   ```

---

## 📖 Endpoints y Uso

### 1. Generar Cotización (HU1)
`POST /api/quotes`
```json
{
  "name": "Rex",
  "species": "DOG",
  "breed": "Labrador",
  "age": 3,
  "plan": "BASIC"
}
```

### 2. Expedir Póliza (HU2)
`POST /api/policies`
```json
{
  "quoteId": "UUID-DE-LA-COTIZACION",
  "ownerName": "Juan Perez",
  "ownerId": "12345678",
  "ownerEmail": "juan.perez@example.com"
}
```

### 3. Gatillar Cobro (HU3)
Proceso automático disparado por el evento `PolicyIssuedEvent`. Al ejecutar la expedición, verás en la consola del servidor el log:
`BILLING TRIGGERED! Policy ID: ... Amount: $...`

---

## 🧪 Pruebas Técnicas Implementadas
- **Unitarias (Domain)**: Lógica de cálculo en `QuoteCalculatorTest` y Validaciones en `IssuePolicyInteractorTest`.
- **Integración (Infrastructure)**: `QuoteControllerTest` y `PolicyControllerTest` usando `WebTestClient`.
- **Colección de Postman**: Se incluye el archivo `PetInsurance_API.postman_collection.json` con flujos automatizados.

---
**Entregado por: Carlos Salcedo / Candidato Personalsoft**
