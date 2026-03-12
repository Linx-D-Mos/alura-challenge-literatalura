<h1 align="center">LiterAlura 📚</h1>

<p align="center">
  <i>Aplicación interactiva de consola desarrollada con Spring Boot para la búsqueda y almacenamiento de libros y autores.</i>
</p>

## 📖 Descripción del Proyecto

**LiterAlura** es una aplicación de línea de comandos (CLI) escrita en Java utilizando el ecosistema de **Spring Boot**. El propósito principal de este sistema es interactuar con la API pública de **[Gutendex](https://gutendex.com/)** para consultar información detallada de libros y autores, parsear las respuestas JSON y almacenar los registros de manera persistente en una base de datos relacional mediante **Spring Data JPA**.

Este proyecto fue refactorizado aplicando buenas prácticas de desarrollo tales como el patrón de **Inyección de Dependencias (IoC)**, estructuración de entidades y repositorios, y el manejo idiomático de componentes en Spring (`@Component`, `@Service`).

## ✨ Características Principales

El menú interactivo de consola permite realizar las siguientes acciones:

1. **Buscar nuevo libro por nombre en la web:** Consulta en tiempo real la API de Gutendex y guarda el libro junto con su autor en la base de datos local.
2. **Mostrar todos los libros guardados:** Lista todas las obras que se encuentran persistidas localmente.
3. **Mostrar todos los autores guardados:** Lista todos los escritores cuyas obras han sido consultadas.
4. **Consultar autores activos en determinado año:** Filtra la base de datos en busca de autores que estaban vivos en un año específico.
5. **Buscar libros almacenados por lenguaje:** Permite filtrar los libros en la base de datos usando las siglas del idioma (por ejemplo: `es`, `en`, `fr`).

## 🛠️ Tecnologías y Herramientas Utilizadas

*   **Java 17:** Lenguaje de desarrollo.
*   **Spring Boot 3:** Framework base para inyección de dependencias (`CommandLineRunner`, `@Autowired`, `@Component`, `@Service`).
*   **Spring Data JPA:** Interacción con la base de datos a través de repositorios (`JpaRepository`).
*   **Hibernate:** Implementación de JPA para ORM.
*   **Jackson:** Librería para la serialización y deserialización de las respuestas JSON provistas por la API (con `@JsonAlias` y `record` de Java).
*   **Maven:** Herramienta para la gestión de dependencias y *build* del proyecto.
*   **Base de Datos:** Configurada a través de *application.properties* (MySQL / PostgreSQL según el entorno).

## 🗂️ Estructura del Proyecto

```text
src/main/java/com/example/demo/
├── cli
│   └── MenuConsola.java       # Menú interactivo y punto de inyección principal
├── model
│   ├── Autor.java             # Entidad JPA de Autores
│   ├── Libro.java             # Entidad JPA de Libros
│   ├── DatosAutor.java        # Record para mapeo JSON del Autor
│   ├── DatosLibro.java        # Record para mapeo JSON del Libro
│   └── DatosResultados.java   # Record envoltorio para respuestas de la API
├── repository
│   ├── AutorRepository.java   # Repositorio de Spring Data con consultas JPQL personalizadas
│   └── LibroRepository.java   # Repositorio de Spring Data para la gestión de Libros
├── service
│   ├── ITransformadorJson.java
│   ├── ServicioPeticiones.java # Servicio que expone HttpClient para invocar Gutendex
│   └── TransformadorJson.java  # Implementación de Jackson ObjectMapper
└── LiteraluraApplication.java  # Clase Main de arranque de Spring Boot
```

## 🚀 Instalación y Uso

### Prerrequisitos
- Tener instalado **Java 17** o superior.
- Tener configurada una base de datos soportada por JPA de acuerdo a lo declarado en tu archivo `src/main/resources/application.properties`.

### Ejecución
1. Clona el repositorio:
   ```bash
   git clone https://github.com/Linx-D-Mos/alura-challenge-literatalura.git
   ```
2. Navega al directorio del proyecto:
   ```bash
   cd literAlura
   ```
3. Ejecuta la aplicación mediante Maven Wrapper:
   ```bash
   ./mvnw spring-boot:run
   ```
   *Si estás en Windows:*
   ```cmd
   mvnw.cmd spring-boot:run
   ```

4. ¡Interactúa con los menús que aparecerán en la pantalla de la terminal!
