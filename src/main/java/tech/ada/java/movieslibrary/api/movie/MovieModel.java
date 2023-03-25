package tech.ada.java.movieslibrary.api.movie;

import jakarta.persistence.*;
import lombok.*;
import tech.ada.java.movieslibrary.client.omdb.MovieDetails;

@Entity
@Table(name = "tb_movie")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MovieModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imdbID;
    private String title;
    private String genre;
    private String director;
    private String actors;
    @Column( name = "year_film")
    private Integer year;

    public static MovieModel from(MovieDetails movieDetails) {
        return new MovieModel(movieDetails.getImdbID(), movieDetails.getTitle(), movieDetails.getGenre(),
            movieDetails.getDirector(), movieDetails.getActors(), movieDetails.getYear());
    }

    public MovieModel(String imdbID, String title, String genre, String director, String actors, Integer year) {
        this.imdbID = imdbID;
        this.title = title;
        this.genre = genre;
        this.director = director;
        this.actors = actors;
        this.year = year;
    }


}
