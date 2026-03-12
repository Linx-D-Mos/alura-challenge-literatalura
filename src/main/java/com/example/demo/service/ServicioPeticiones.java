package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class ServicioPeticiones {

    public String ejecutarPeticion(String enlaceDestino) {
        HttpClient clienteEnRed = HttpClient.newHttpClient();
        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(enlaceDestino))
                .build();
        HttpResponse<String> respuestaRecibida;
        try {
            respuestaRecibida = clienteEnRed
                    .send(solicitud, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException excepcion) {
            throw new RuntimeException("Error al comunicarse con la API: " + excepcion.getMessage(), excepcion);
        }

        return respuestaRecibida.body();
    }
}
