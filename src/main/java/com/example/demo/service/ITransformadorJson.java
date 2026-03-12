package com.example.demo.service;

public interface ITransformadorJson {
    <T> T transformarDatos(String contenidoJson, Class<T> tipoEstructura);
}
