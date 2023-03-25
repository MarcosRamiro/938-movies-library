package tech.ada.java.movieslibrary.api.movie;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tech.ada.java.movieslibrary.client.omdb.MovieDetails;
import tech.ada.java.movieslibrary.client.omdb.MovieMinimalRestRepository;
import tech.ada.java.movieslibrary.client.omdb.ResultSearch;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
@Log4j2
public class MoviesRestController {

    private final MovieMinimalRestRepository restRepository;
    private final MovieJpaRespository movieJpaRespository;

    @GetMapping(value = "/external-search", params = "title")
    public ResultSearch buscar(@RequestParam String title) {
        log.info("Consultado a API externa OMDB pelo título: {}", title);
        return this.restRepository.search(title);
    }

    @GetMapping("/external-search/{id}")
    public MovieDetails detalhes(@PathVariable String id) {
        return getMovieDetails(id);
    }

    private MovieDetails getMovieDetails(String id) {
        log.info("Consultado a API externa OMDB para detalhar o imdbId: {}", id);
        return this.restRepository.details(id);
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public MovieResponse salvar(@PathVariable String id) {
        MovieDetails movieDetails = this.getMovieDetails(id);
        MovieModel movie = MovieModel.from(movieDetails);
        movieJpaRespository.save(movie);
        return MovieResponse.of(movie);
    }

    @GetMapping
    public List<MovieResponse> listar() {
        return movieJpaRespository.findAll().stream()
            .map(MovieResponse::of)
            .toList();
    }

    @GetMapping(params = "titulo")
    public List<MovieResponse> consultar(@RequestParam String titulo) {
        return movieJpaRespository.findByTitleContaining(titulo).stream()
            .map(MovieResponse::of)
            .toList();
    }

}
