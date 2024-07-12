package com.desafio.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "autores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer fechaNacimiento;
    private Integer fechaFallecimiento;
    private String nombre;

    @OneToOne
    @JoinColumn(name = "libros_id")
    private Libro libro;

    public Autor(DatosAutor datosAutor) {
        this.fechaNacimiento = datosAutor.fechaNacimiento();
        this.fechaFallecimiento = datosAutor.fechaFallecimiento();
        this.nombre = datosAutor.nombre();
    }

    @Override
    public String toString() {
        return "Autor: " + nombre
                + " (" + fechaNacimiento + " - " + fechaFallecimiento + ")";
    }
}
