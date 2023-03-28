package tech.ada.java.movieslibrary.api.user.favorite;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FavJpaRepository extends JpaRepository<FavModel, Long> {

    @Query("""
        select new tech.ada.java.movieslibrary.api.user.favorite.FavDTO(f.userModel, f.movieModel)
        from FavModel f where f.userModel.id = :userModelId 
    """)
    List<FavDTO> findByUserModelId(@Param("userModelId") Long userModelId);

    Optional<FavModel> findByUserModelIdAndMovieModelId(Long userModelId, Long movieModelId);


    @Query("""
        select new tech.ada.java.movieslibrary.api.user.favorite.FavBasicDTO(f.userModel.username, f.movieModel.title)
        from FavModel f where f.userModel.id = :userModelId 
    """)
    List<FavBasicDTO> findBasicByUserModelId(@Param("userModelId") Long userModelId);
}
