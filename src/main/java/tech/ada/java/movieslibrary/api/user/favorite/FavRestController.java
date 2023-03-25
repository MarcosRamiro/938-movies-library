package tech.ada.java.movieslibrary.api.user.favorite;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.ada.java.movieslibrary.api.movie.MovieDTO;
import tech.ada.java.movieslibrary.api.movie.MovieJpaRespository;
import tech.ada.java.movieslibrary.api.movie.MovieModel;
import tech.ada.java.movieslibrary.api.user.UserDTO;
import tech.ada.java.movieslibrary.api.user.UserJpaRepository;
import tech.ada.java.movieslibrary.api.user.UserModel;

@RestController
@RequestMapping("/users/{userId}/favorites")
@RequiredArgsConstructor
@Log4j2
public class FavRestController {

    private final MovieJpaRespository movieJpaRespository;
    private final UserJpaRepository userJpaRepository;
    private final FavJpaRepository favJpaRepository;

    @PostMapping("/{movieId}")
    public ResponseEntity<FavDTO> salvar(@PathVariable Long userId, @PathVariable Long movieId) {
        UserModel userModel = getUserModel(userId);
        MovieModel movieModel = this.movieJpaRespository.findById(movieId).orElseThrow();
        Optional<FavDTO> favModelOptional = favJpaRepository.findByUserModelIdAndMovieModelId(userModel.getId(), movieModel.getId());
        HttpStatus httpStatus;
        if (favModelOptional.isPresent()) {
            httpStatus = HttpStatus.OK;
        } else {
            httpStatus = HttpStatus.CREATED;
        }
        FavDTO favDTO = favModelOptional.orElseGet(() -> saveFavModel(userModel, movieModel));
        return new ResponseEntity<>(favDTO, httpStatus);
    }

    private FavDTO saveFavModel(UserModel userModel, MovieModel movieModel) {
        FavModel favModel = this.favJpaRepository.save(new FavModel(movieModel, userModel));
        return this.convertToFavDTO(favModel);
    }

    private FavDTO convertToFavDTO(FavModel favModel) {
        return new FavDTO(new UserDTO(favModel.getUserModel()), new MovieDTO(favModel.getMovieModel()));
    }

    private UserModel getUserModel(Long userId) {
        return this.userJpaRepository.findById(userId).orElseThrow();
    }

    @GetMapping
    public List<FavDTO> listardoUsuario(@PathVariable Long userId) {
        UserModel userModel = this.getUserModel(userId);
        return this.favJpaRepository.findByUserModelId(userModel.getId());
    }

    @GetMapping("/projection")
    public List<FavBasicDTO> listarProjectiondoUsuario(@PathVariable Long userId) {
        UserModel userModel = this.getUserModel(userId);
        return this.favJpaRepository.findBasicByUserModelId(userModel.getId());
    }


}
