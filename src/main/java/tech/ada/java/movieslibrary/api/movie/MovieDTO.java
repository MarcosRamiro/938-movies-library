package tech.ada.java.movieslibrary.api.movie;

public record MovieDTO(String imdbID, String title, String genre, String director, String actors, Long year) {

    public MovieDTO(MovieModel movieModel) {
        this(movieModel.getImdbID(), movieModel.getTitle(), movieModel.getGenre(),
            movieModel.getDirector(), movieModel.getActors(), movieModel.getYear());
    }
}
