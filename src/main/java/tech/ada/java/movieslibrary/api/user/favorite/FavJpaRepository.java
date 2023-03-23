package tech.ada.java.movieslibrary.api.user.favorite;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavJpaRepository extends JpaRepository<FavModel, Long> {

    List<FavModel> findByUserModelId(Long id);

}
