package com.desafio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Entity
@Table(name = "libros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idGutendex;
    private String titulo;
    private String lenguaje;
    private Long numeroDescargas;
    @OneToOne(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.EAGER) //busqueda ansiosa
    private Autor autor;

    public Libro(DatosLibro datosLibro) {
        this.idGutendex = datosLibro.id();
        this.titulo = datosLibro.titulo();
        if(datosLibro.lenguajes().size()>0){
            this.lenguaje = datosLibro.lenguajes().get(0);
        }else{
            this.lenguaje = null;
        }
        this.numeroDescargas = datosLibro.numeroDescargas();
        if(datosLibro.autores().size()>0){
            this.autor = new Autor(datosLibro.autores().get(0));
            this.autor.setLibro(this);
        }else{
            this.autor = null;
        }
    }

    @Override
    public String toString() {
        return "Título: " + titulo
                + "\n" + autor.toString()
                + "\nIdioma: " + lenguaje
                + "\nNúmero de descargas: " + numeroDescargas;
    }
}
