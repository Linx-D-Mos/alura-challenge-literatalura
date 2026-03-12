package com.example.demo.cli;

import com.example.demo.model.Autor;
import com.example.demo.model.DatosAutor;
import com.example.demo.model.DatosLibro;
import com.example.demo.model.DatosResultados;
import com.example.demo.model.Libro;
import com.example.demo.repository.AutorRepository;
import com.example.demo.repository.LibroRepository;
import com.example.demo.service.ServicioPeticiones;
import com.example.demo.service.TransformadorJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class MenuConsola {

    private final Scanner lectorConsola = new Scanner(System.in);
    private final ServicioPeticiones servicioPeticiones;
    private final TransformadorJson transformadorJson;
    private final String URL_API = "https://gutendex.com/books/?search=";

    private final AutorRepository repositorioAutores;
    private final LibroRepository repositorioLibros;

    @Autowired
    public MenuConsola(AutorRepository repositorioAutores, LibroRepository repositorioLibros,
                       ServicioPeticiones servicioPeticiones, TransformadorJson transformadorJson) {
        this.repositorioAutores = repositorioAutores;
        this.repositorioLibros = repositorioLibros;
        this.servicioPeticiones = servicioPeticiones;
        this.transformadorJson = transformadorJson;
    }

    public void iniciarAplicacion() {
        int comandoUsuario = -1;
        while (comandoUsuario != 0) {
            String opcionesMenu = """
                    ========================================
                    === BIENVENIDO A LITERALURA ===
                    Selecciona una acción ingresando su número:
                    
                    1 - Buscar nuevo libro por nombre en la web
                    2 - Mostrar todos los libros guardados
                    3 - Mostrar todos los autores guardados
                    4 - Consultar autores activos en determinado año
                    5 - Buscar libros almacenados por lenguaje
                    0 - Salir del sistema
                    ========================================
                    """;
            System.out.println(opcionesMenu);
            if (lectorConsola.hasNextInt()) {
                comandoUsuario = lectorConsola.nextInt();
                lectorConsola.nextLine(); // Limpiar el buffer

                switch (comandoUsuario) {
                    case 1 -> gestionarBusquedaLibro();
                    case 2 -> mostrarLibrosGuardados();
                    case 3 -> mostrarAutoresGuardados();
                    case 4 -> mostrarAutoresActivosEnAnio();
                    case 5 -> filtrarLibrosPorLenguaje();
                    case 0 -> System.out.println("Saliendo. ¡Vuelve pronto!");
                    default -> System.out.println("Comando no reconocido, por favor intenta de nuevo.");
                }
            } else {
                System.out.println("Entrada inválida. Asegúrate de escribir un número.");
                lectorConsola.nextLine();
            }
        }
    }

    private void gestionarBusquedaLibro() {
        System.out.println("Por favor, introduce el título de la obra que quieres buscar:");
        String consulta = lectorConsola.nextLine();

        String respuestaJson = servicioPeticiones.ejecutarPeticion(URL_API + consulta.replace(" ", "%20"));
        DatosResultados datosObtenidos = transformadorJson.transformarDatos(respuestaJson, DatosResultados.class);

        if (datosObtenidos.librosEncontrados().isEmpty()) {
            System.out.println("Lo sentimos, no encontramos resultados en la API para esa búsqueda.");
            return;
        }

        DatosLibro infoLibro = datosObtenidos.librosEncontrados().get(0);
        DatosAutor infoAutor = infoLibro.listaAutores().get(0);

        Autor nuevoAutor = new Autor(infoAutor);
        repositorioAutores.save(nuevoAutor);

        Libro nuevoLibro = new Libro(infoLibro, nuevoAutor);
        repositorioLibros.save(nuevoLibro);

        System.out.println("-> El libro ha sido registrado con éxito en la base de datos local.");
        System.out.println("-> Obra: " + nuevoLibro.getTituloObra() + " | Escrito por: " + nuevoAutor.getNombreCompleto());
    }

    private void mostrarLibrosGuardados() {
        var listaLibros = repositorioLibros.findAll();
        System.out.println("\n*** LISTADO DE LIBROS EN BD ***");
        listaLibros.forEach(libro -> System.out.println("Obra: " + libro.getTituloObra() + " | Idioma: " + libro.getLenguaje()));
        if (listaLibros.isEmpty()) {
            System.out.println("Actualmente no hay libros almacenados.");
        }
    }

    private void mostrarAutoresGuardados() {
        var listaAutores = repositorioAutores.findAll();
        System.out.println("\n*** LISTADO DE AUTORES EN BD ***");
        listaAutores.forEach(autor -> System.out.println("- " + autor.getNombreCompleto()));
        if (listaAutores.isEmpty()) {
            System.out.println("Actualmente no hay autores almacenados.");
        }
    }

    private void mostrarAutoresActivosEnAnio() {
        System.out.println("Proporciona un año para verificar autores vivos (ej. 1950):");
        if (lectorConsola.hasNextInt()) {
            int anioConsulta = lectorConsola.nextInt();
            lectorConsola.nextLine();
            
            var autoresActivos = repositorioAutores.obtenerAutoresVivosPorAnio(anioConsulta);
            if (autoresActivos.isEmpty()) {
                System.out.println("Ningún autor registrado coincide con tener vida en el año proporcionado.");
            } else {
                System.out.println("\n*** AUTORES QUE ESTUVIERON CON VIDA EN " + anioConsulta + " ***");
                autoresActivos.forEach(a -> System.out.println("> " + a.getNombreCompleto()));
            }
        } else {
            System.out.println("Año inválido. Abortando búsqueda.");
            lectorConsola.nextLine();
        }
    }

    private void filtrarLibrosPorLenguaje() {
        System.out.println("Indica el lenguaje buscado usando las siglas (ej: 'es', 'en', 'fr'):");
        String lenguajeRequerido = lectorConsola.nextLine();
        var librosFiltrados = repositorioLibros.findByLenguaje(lenguajeRequerido);
        
        if (librosFiltrados.isEmpty()) {
            System.out.println("No guardamos registros de libros bajo ese idioma.");
        } else {
            System.out.println("\n*** RESULTADOS EN IDIOMA '" + lenguajeRequerido + "' ***");
            librosFiltrados.forEach(l -> System.out.println("Obra: " + l.getTituloObra() + " | Registrado en: " + l.getLenguaje()));
        }
    }
}
