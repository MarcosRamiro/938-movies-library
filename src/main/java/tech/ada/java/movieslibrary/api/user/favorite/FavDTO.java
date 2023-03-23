package tech.ada.java.movieslibrary.api.user.favorite;

import tech.ada.java.movieslibrary.api.movie.MovieDTO;
import tech.ada.java.movieslibrary.api.user.UserDTO;

public record FavDTO(UserDTO userDTO, MovieDTO movieDTO) {}
