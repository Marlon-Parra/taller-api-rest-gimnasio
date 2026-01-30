#  Taller – API REST | Gestión de Gimnasio

## Descripción del Proyecto

Este proyecto consiste en el desarrollo de una **API REST** utilizando **Spring Boot**, cuyo objetivo es gestionar un sistema básico de un gimnasio. El sistema permite administrar **Clientes** y **Rutinas**, implementando una relación **Muchos a Muchos (ManyToMany)** entre ambas entidades.

El proyecto fue desarrollado cumpliendo **estrictamente** con los requisitos establecidos en el taller, aplicando arquitectura en capas, persistencia con JPA, validaciones con Bean Validation y documentación automática mediante **Swagger (SpringDoc OpenAPI)**.

---

##  Objetivo del Taller

Desarrollar una API REST que permita:

* Gestionar clientes y rutinas
* Implementar una relación ManyToMany entre Cliente y Rutina
* Aplicar buenas prácticas de arquitectura en capas
* Documentar y probar los endpoints usando Swagger

---

##  Arquitectura del Proyecto

El proyecto sigue una **arquitectura en capas**, organizada de la siguiente forma:

```
com.example.gimnasio
├── controller   -> Manejo de peticiones HTTP
├── service      -> Lógica de negocio
├── repository   -> Persistencia de datos (JPA)
├── entity       -> Entidades JPA
├── dto          -> Objetos de transferencia de datos
└── GimnasioApplication.java
```

 **Reglas aplicadas:**

* Los controladores solo gestionan solicitudes HTTP
* Toda la lógica está en la capa Service
* El acceso a base de datos se realiza únicamente desde Repository
* No se utiliza Lombok (getters, setters y constructores escritos manualmente)

---

##  Modelo de Datos

###  Entidad Cliente

* `id` (Long, PK, autoincremental)
* `nombre` (String, obligatorio)
* `documento` (String, obligatorio, único)
* `activo` (boolean, obligatorio)

###  Entidad Rutina

* `id` (Long, PK, autoincremental)
* `nombre` (String, obligatorio, único)
* `nivel` (String, obligatorio)

  * Valores permitidos: `BASICO`, `INTERMEDIO`, `AVANZADO`

###  Relación ManyToMany

* Un Cliente puede tener muchas Rutinas
* Una Rutina puede estar asociada a muchos Clientes
* Se implementa con `@ManyToMany` y `@JoinTable`
* La tabla intermedia se genera automáticamente por JPA

---

##  Tecnologías y Dependencias Utilizadas

* Java 17+
* Spring Boot
* Spring Web
* Spring Data JPA
* MySQL Driver
* Bean Validation
* SpringDoc OpenAPI (Swagger)

---

##  Endpoints Implementados

###  CRUD Clientes

* `POST /api/clientes`
* `GET /api/clientes`
* `GET /api/clientes/{id}`
* `PUT /api/clientes/{id}`
* `DELETE /api/clientes/{id}` (eliminado lógico)

###  CRUD Rutinas

* `POST /api/rutinas`
* `GET /api/rutinas`
* `GET /api/rutinas/{id}`
* `PUT /api/rutinas/{id}`
* `DELETE /api/rutinas/{id}`

###  Gestión de la Relación

* Asignar rutina a cliente
  `POST /api/clientes/{clienteId}/rutinas/{rutinaId}`

* Listar rutinas de un cliente
  `GET /api/clientes/{clienteId}/rutinas`

* Listar clientes de una rutina
  `GET /api/rutinas/{rutinaId}/clientes`

* Quitar rutina a un cliente
  `DELETE /api/clientes/{clienteId}/rutinas/{rutinaId}`

---

##  Validaciones Aplicadas

* `@NotBlank` para campos obligatorios
* Validación de unicidad:

  * Documento del Cliente
  * Nombre de la Rutina
* Uso obligatorio de `@Valid` en los endpoints con `@RequestBody`

---

##  Documentación con Swagger

La API cuenta con documentación automática generada con **SpringDoc OpenAPI**.

 Acceso a Swagger UI:

```
http://localhost:8081/swagger-ui/index.html
```

Desde Swagger se pueden:

* Visualizar todos los endpoints
* Probar el CRUD completo
* Ver modelos DTO y ejemplos de request/response

---

##  Configuración de Base de Datos

###  Creación de la Base de Datos

Ejecutar el siguiente comando en MySQL:

```sql
CREATE DATABASE gimnasio_db;
```

###  Configuración en `application.properties`

(ajustar según el entorno local)

```
spring.datasource.url=jdbc:mysql://localhost:3306/gimnasio_db
spring.datasource.username=root
spring.datasource.password=tu_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

##  Instrucciones para Ejecutar el Proyecto

1. Clonar el repositorio
2. Crear la base de datos `gimnasio_db`
3. Configurar credenciales en `application.properties`
4. Ejecutar el proyecto desde el IDE o con:

   ```
   mvn spring-boot:run
   ```
5. Acceder a Swagger y probar los endpoints

---

##  Video de Evidencia

En el siguiente enlace se muestra:

* Estructura del proyecto
* Explicación de entidades y relación ManyToMany
* Ejecución del proyecto
* Swagger funcionando
* Prueba de todos los endpoints

 **Link del video:**
[https://youtu.be/wgBm9913oCE](https://youtu.be/wgBm9913oCE)

---

##  Autores

* **Marlon Eduardo Parra Ruedas**
* **Wendy Valentina Suarez Herrera**

---

