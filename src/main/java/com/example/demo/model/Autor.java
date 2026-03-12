package com.example.demo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombreCompleto;
    
    private Integer anioNacimiento;
    private Integer anioFallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> obrasEscritas;

    public Autor() {}

    public Autor(DatosAutor informacionAutor) {
        this.nombreCompleto = informacionAutor.nombre();
        this.anioNacimiento = informacionAutor.anioNacimiento();
        this.anioFallecimiento = informacionAutor.anioFallecimiento();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public Integer getAnioNacimiento() {
        return anioNacimiento;
    }

    public void setAnioNacimiento(Integer anioNacimiento) {
        this.anioNacimiento = anioNacimiento;
    }

    public Integer getAnioFallecimiento() {
        return anioFallecimiento;
    }

    public void setAnioFallecimiento(Integer anioFallecimiento) {
        this.anioFallecimiento = anioFallecimiento;
    }

    public List<Libro> getObrasEscritas() {
        return obrasEscritas;
    }

    public void setObrasEscritas(List<Libro> obrasEscritas) {
        this.obrasEscritas = obrasEscritas;
    }
}