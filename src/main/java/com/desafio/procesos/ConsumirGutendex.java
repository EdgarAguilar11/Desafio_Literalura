package com.desafio.procesos;

import com.desafio.model.DatosBiblioteca;
import com.desafio.model.Libro;
import com.desafio.repository.AutorRepository;
import com.desafio.repository.LibroRepository;
import com.desafio.service.ConexionAPI;
import com.desafio.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;


public class ConsumirGutendex {
    private Scanner lectura = new Scanner(System.in);
    private ConexionAPI conexionAPI = new ConexionAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private final String URL_BUSCAR_LIBRO = "?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    public ConsumirGutendex (LibroRepository libroRepository, AutorRepository autorRepository){
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }


    public void ejecutaMenu(){
        var opcion = 1;
        inicio:
        while(opcion > 0){
            mostrarMensaje();
            if (!lectura.hasNextInt()){
                System.out.println("No ingresaste una entrada válida");
                lectura.nextLine();
                continue inicio;
            }
            opcion = lectura.nextInt();
            lectura.nextLine();

            switch (opcion){
                case 1:
                    buscarYGuardarLibro();
                    break;
                case 2:
                    listarLibrosBuscados();
                    break;
                case 3:
                    listarAutoresGuardados();
                    break;
                case 4:
                    buscarAutoresVivosEnAñoIngresado();
                    break;
                case 5:
                    buscarLibrosGuardadosPorIdioma();
                    break;
                case 0:
                    System.out.println("Finalizando programa...");
                    break;
                default:
                    System.out.println("Opción inválida, revisa e intenta nuevamente");
                    break;
            }
        }
    }

    private void mostrarMensaje(){
        System.out.print("""
                *********** Bienvenido a la aplicación para consumo de la API Gutendex ***********
                **** Gutendex nos permite consultar la información de una colección de libros ****
                -> Ingresa el número correspondiente a la opción deseada:
                1.- Buscar libro por título. 
                2.- Listar libros buscados.
                3.- Listar autores guardados.
                4.- Buscar autores vivos en un año determinado.
                5.- Buscar libros guardados por idioma.
                0.- Salir del programa.
                """);
    }

    private DatosBiblioteca consultaGutendex(String url){
        System.out.println("Buscando libro en Gutendex, espere por favor...");
        String json = conexionAPI.obtenerDatos(url);
        DatosBiblioteca datos = conversor.crearObjetoDeJson(json, DatosBiblioteca.class);
        return datos;
    }

    private void buscarYGuardarLibro(){
        System.out.println("Por favor, ingresa un título de libro para buscar su información");
        String titulo = lectura.nextLine();
        DatosBiblioteca datos = consultaGutendex(URL_BASE
                + URL_BUSCAR_LIBRO
                + titulo.replace(" ", "%20"));

        if (datos.listaDeLibros().size() <= 0){
            System.out.println("Lo siento, no se encontraron coincidencias.");
            return;
        }
        System.out.println("-> Se encontró " + datos.listaDeLibros().size()
                + (datos.listaDeLibros().size() == 1 ? " coincidencia, guardando coincidencia..."
                    : " coincidencias, guardando la primer coincidencia..."));

        var libroObtenido = datos.listaDeLibros().get(0);
        if (libroRepository.existsByIdGutendex(libroObtenido.id())){
            System.out.println("El libro ya fue registrado, no se puede guardar nuevamente");
            return;
        }
        Libro libroGuardado = libroRepository.save(new Libro(libroObtenido));
        System.out.println("Se guardó en la base de datos el libro: " + libroGuardado.toString());
    }

    private void listarLibrosBuscados(){
        System.out.println("Lista de libros buscados: ");
        libroRepository.findAll().stream()
                .forEach(System.out::println);
    }

    private void listarAutoresGuardados(){
        System.out.println("Lista de Autores guardados: ");
        autorRepository.findAll().stream()
                .forEach(System.out::println);
    }

    private void buscarAutoresVivosEnAñoIngresado(){
        System.out.println("Por favor, ingresa el año para buscar los autores");
        if (!lectura.hasNextInt()){
            System.out.println("No ingresaste una entrada válida para el año");
            lectura.nextLine();
            return;
        }
        var fechaAutorVivo = lectura.nextInt();
        if (Integer.toString(fechaAutorVivo).matches("\\d{4}")){
            System.out.println("No ingresaste un número válido para el año");
            lectura.nextLine();
            return;
        }

        var autoresVivos = autorRepository.findAll().stream()
                .filter(a -> a.getFechaFallecimiento() > fechaAutorVivo && a.getFechaNacimiento()<= fechaAutorVivo)
                .collect(Collectors.toList());

        System.out.println("Autores vivos en: " + fechaAutorVivo);
        autoresVivos.stream()
                .forEach(System.out::println);
    }

    private void buscarLibrosGuardadosPorIdioma(){
        var idiomasGuardados = libroRepository.encuentraDistintosIdiomasGuardados();
        System.out.println("""
                Existen libros con los siguientes idiomas guardados, ingresa el idioma para mostrarte los libros:""");
        idiomasGuardados.stream()
                .forEach(i -> System.out.println("Idioma: " + i));
        var idiomaUsuario = lectura.nextLine();
        var idiomaValido = idiomasGuardados.stream()
                .anyMatch(i -> idiomaUsuario.equals(i));
        if(!idiomaValido){
            System.out.println("No ingresaste un idioma de los que hay disponibles.");
            return;
        }
        var librosEncontrados = libroRepository.findByLenguaje(idiomaUsuario);
        librosEncontrados.stream()
                .forEach(System.out::println);
    }
}
