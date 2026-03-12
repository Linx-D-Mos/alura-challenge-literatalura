package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String tituloObra;

    @ManyToOne
    private Autor autor;

    private String lenguaje;
    private Double totalDescargas;

    public Libro() {}

    public Libro(DatosLibro informacionLibro, Autor autorRegistrado) {
        this.tituloObra = informacionLibro.nombreLibro();
        this.totalDescargas = informacionLibro.cantidadDescargas();
        if (informacionLibro.listaIdiomas() != null && !informacionLibro.listaIdiomas().isEmpty()) {
            this.lenguaje = informacionLibro.listaIdiomas().get(0);
        }
        this.autor = autorRegistrado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTituloObra() {
        return tituloObra;
    }

    public void setTituloObra(String tituloObra) {
        this.tituloObra = tituloObra;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(String lenguaje) {
        this.lenguaje = lenguaje;
    }

    public Double getTotalDescargas() {
        return totalDescargas;
    }

    public void setTotalDescargas(Double totalDescargas) {
        this.totalDescargas = totalDescargas;
    }
}