package tech.ada.java.movieslibrary.omdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import tech.ada.java.movieslibrary.omdb.model.MovieModel;

import java.util.Arrays;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MovieDetails {

    @JsonProperty("imdbID")
    private String imdbID;
    @JsonProperty("Title")
    private String title;
    @JsonProperty("Genre")
    private String genre;
    @JsonProperty("Director")
    private String director;
    @JsonProperty("Writer")
    private String writer;
    @JsonProperty("Actors")
    private String actors;
    @JsonProperty("Language")
    private String language;

    private Integer year;
    @JsonProperty("Year")
    public void setYear(String year) {
        this.year = convertYear(year);
    }

    private int convertYear(final String year) {
        if (year.matches("\\d+")) {
            return Integer.parseInt(year);
        }
        return Arrays.stream(year.split("\\D"))
                .map(Integer::parseInt)
                .findFirst()
                .orElseThrow();
    }

    public MovieModel toModel(){

        MovieModel movie = new MovieModel();

        movie.setActors(this.getActors());
        movie.setTitle(this.getTitle());
        movie.setDirector(this.getDirector());
        movie.setGenre(this.getGenre());
        movie.setYear((long) this.getYear());
        movie.setImdbID(this.getImdbID());

        return movie;
    }

}
