package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("title") String nombreLibro,
        @JsonAlias("authors") List<DatosAutor> listaAutores,
        @JsonAlias("languages") List<String> listaIdiomas,
        @JsonAlias("download_count") Double cantidadDescargas
) {
}