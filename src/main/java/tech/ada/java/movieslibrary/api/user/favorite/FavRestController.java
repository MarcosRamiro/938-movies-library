package tech.ada.java.movieslibrary.api.user.favorite;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    private final ModelMapper modelMapper;

    @PostMapping("/{movieId}")
    @ResponseStatus(HttpStatus.CREATED)
    public FavDTO salvar(@PathVariable Long userId, @PathVariable Long movieId) {
        UserModel userModel = getUserModel(userId);
        MovieModel movieModel = this.movieJpaRespository.findById(movieId).orElseThrow();
        FavModel favModel = this.favJpaRepository.save(new FavModel(movieModel, userModel));
        return convertToFavDTO(favModel);
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
        List<FavModel> favModelList = this.favJpaRepository.findByUserModelId(userModel.getId());
        return favModelList.stream()
            .map(this::convertToFavDTO)
            .toList();
    }


}
