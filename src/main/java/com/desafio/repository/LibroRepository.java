package com.desafio.repository;

import com.desafio.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    Boolean existsByIdGutendex(Long idGutendex);

    @Query("SELECT DISTINCT l.lenguaje FROM Libro l")
    List<String> encuentraDistintosIdiomasGuardados();

    List<Libro> findByLenguaje(String idioma);
}
