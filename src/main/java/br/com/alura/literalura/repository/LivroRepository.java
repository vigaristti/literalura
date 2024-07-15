package br.com.alura.literalura.repository;


import br.com.alura.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    List<Livro> findByIdioma(String Idioma);

    Integer countByIdioma(String Idioma);

    List<Livro> findTop10ByOrderByNumeroDownloadsDesc();
}