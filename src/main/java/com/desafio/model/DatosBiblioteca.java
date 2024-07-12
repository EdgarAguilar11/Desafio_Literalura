package com.desafio.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosBiblioteca (
    @JsonAlias("count") Long totalLibros,
    @JsonAlias("next") String paginaSiguienteDeResultados,
    @JsonAlias("previous") String paginaAnteriorDeResultados,
    @JsonAlias("results") ArrayList<DatosLibro> listaDeLibros
) {
}
