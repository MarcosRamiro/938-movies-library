package tech.ada.java.movieslibrary.api.user.favorite;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class FavRestController {

    private final MovieJpaRespository movieJpaRespository;
    private final UserJpaRepository userJpaRepository;
    private final FavJpaRepository favJpaRepository;

    @PostMapping("/{movieId}")
    public ResponseEntity<FavDTO> salvar(@PathVariable Long userId, @PathVariable Long movieId) {
        UserModel userModel = getUserModel(userId);
        MovieModel movieModel = this.movieJpaRespository.findById(movieId).orElseThrow();
        Optional<FavModel> favModelOptional = favJpaRepository.findByUserModelIdAndMovieModelId(userModel.getId(), movieModel.getId());
        if (favModelOptional.isPresent()) {
            return new ResponseEntity<>(this.convertToFavDTO(favModelOptional.get()), HttpStatus.OK);
        }
        FavModel favModel = this.favJpaRepository.save(new FavModel(movieModel, userModel));
        return new ResponseEntity<>(this.convertToFavDTO(favModel), HttpStatus.CREATED);
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<FavDTO> remover(@PathVariable Long userId, @PathVariable Long movieId) {
        UserModel userModel = getUserModel(userId);
        MovieModel movieModel = this.movieJpaRespository.findById(movieId).orElseThrow();
        Optional<FavModel> optionalFavModel = this.favJpaRepository.findByUserModelIdAndMovieModelId(userModel.getId(),
            movieModel.getId());
        if (optionalFavModel.isPresent()) {
            this.favJpaRepository.delete(optionalFavModel.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
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
