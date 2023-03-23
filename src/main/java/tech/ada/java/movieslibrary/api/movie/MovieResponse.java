package tech.ada.java.movieslibrary.api.movie;

import lombok.Data;

@Data
public class MovieResponse {
    private Long id;
    private String imdbID;
    private String title;
    private String genre;
    private String director;
    private String actors;
    private Long year;

    public static MovieResponse of(MovieModel model){
        MovieResponse response = new MovieResponse();
        response.setActors(model.getActors());
        response.setYear(model.getYear());
        response.setId(model.getId());
        response.setDirector(model.getDirector());
        response.setTitle(model.getTitle());
        response.setGenre(model.getGenre());
        response.setImdbID(model.getImdbID());
        return response;
    }
}
