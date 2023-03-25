package tech.ada.java.movieslibrary.api.user.favorite;

import tech.ada.java.movieslibrary.api.movie.MovieDTO;
import tech.ada.java.movieslibrary.api.movie.MovieModel;
import tech.ada.java.movieslibrary.api.user.UserDTO;
import tech.ada.java.movieslibrary.api.user.UserModel;

public record FavDTO(UserDTO userDTO, MovieDTO movieDTO) {

    public FavDTO(UserModel userModel, MovieModel movieModel) {
        this(new UserDTO(userModel), new MovieDTO(movieModel));
    }
}
