package tech.ada.java.movieslibrary.api.movie;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieJpaRespository extends JpaRepository<MovieModel, Long> {

    List<MovieModel> findByTitleContaining(String title);

}
