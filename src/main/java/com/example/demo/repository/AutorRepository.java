package com.example.demo.repository;

import com.example.demo.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    
    @Query("SELECT a FROM Autor a WHERE a.anioNacimiento <= :anioBusqueda AND (a.anioFallecimiento >= :anioBusqueda OR a.anioFallecimiento IS NULL)")
    List<Autor> obtenerAutoresVivosPorAnio(@Param("anioBusqueda") int anioBusqueda);
}