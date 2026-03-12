package com.example.demo;

import com.example.demo.cli.MenuConsola;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

    private final MenuConsola terminalInteractivo;

    @Autowired
    public LiteraluraApplication(MenuConsola terminalInteractivo) {
        this.terminalInteractivo = terminalInteractivo;
    }

    public static void main(String[] args) {
        SpringApplication.run(LiteraluraApplication.class, args);
    }

    @Override
    public void run(String... argumentosArranque) throws Exception {
        terminalInteractivo.iniciarAplicacion();
    }
}