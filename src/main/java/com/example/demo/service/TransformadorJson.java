package com.example.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class TransformadorJson implements ITransformadorJson {

    private final ObjectMapper formateadorJackson = new ObjectMapper();

    @Override
    public <T> T transformarDatos(String contenidoJson, Class<T> tipoEstructura) {
        try {
            return formateadorJackson.readValue(contenidoJson, tipoEstructura);
        } catch (JsonProcessingException excepcion) {
            throw new RuntimeException("Inconveniente al parsear los datos JSON: " + excepcion.getMessage(), excepcion);
        }
    }
}
