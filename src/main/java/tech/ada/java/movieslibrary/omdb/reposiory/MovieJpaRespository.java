package tech.ada.java.movieslibrary.omdb.reposiory;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.ada.java.movieslibrary.omdb.model.MovieModel;

import java.util.List;

public interface MovieJpaRespository extends JpaRepository<MovieModel, Long> {

    List<MovieModel> findByTitle(String title);
    List<MovieModel> findByTitleContaining(String title);

}
