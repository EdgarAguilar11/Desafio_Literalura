package com.desafio.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro (
    @JsonAlias("id") Long id,
    @JsonAlias("title") String titulo,
    @JsonAlias("authors") ArrayList<DatosAutor>  autores,
    @JsonAlias("languages") ArrayList<String> lenguajes,
    @JsonAlias("download_count") Long numeroDescargas
) {
}
