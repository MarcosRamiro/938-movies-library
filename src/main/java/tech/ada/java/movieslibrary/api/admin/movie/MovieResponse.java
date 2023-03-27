package tech.ada.java.movieslibrary.api.admin.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tech.ada.java.movieslibrary.api.movie.MovieModel;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponse {
    private Long id;
    private String imdbID;
    private String title;
    private String genre;
    private String director;
    private String actors;
    private Integer year;
    private Boolean available;

    public static MovieResponse of(MovieModel model){
        return MovieResponse.builder()
            .id(model.getId())
            .imdbID(model.getImdbID())
            .title(model.getTitle())
            .genre(model.getGenre())
            .director(model.getDirector())
            .actors(model.getActors())
            .year(model.getYear())
            .available(model.getAvailable())
            .build();
    }
}
