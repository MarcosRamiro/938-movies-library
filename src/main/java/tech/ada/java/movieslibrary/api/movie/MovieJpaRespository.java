package tech.ada.java.movieslibrary.api.movie;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MovieJpaRespository extends JpaRepository<MovieModel, Long> {

    List<MovieModel> findAllByAvailableTrue();

    List<MovieModel> findByTitleContainingAndAvailableTrue(String title);

    @Modifying
    @Query("update MovieModel m set m.available = :available where m.id = :movieModelId")
    void setAvailable(@Param("available") Boolean available, @Param("movieModelId") Long movieModelId);

    Optional<MovieModel> findByImdbID(String imdbID);

}
