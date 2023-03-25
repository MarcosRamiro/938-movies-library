package tech.ada.java.movieslibrary.api.admin.movie;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tech.ada.java.movieslibrary.api.movie.MovieJpaRespository;
import tech.ada.java.movieslibrary.api.movie.MovieModel;
import tech.ada.java.movieslibrary.api.user.DuplicatedException;
import tech.ada.java.movieslibrary.client.omdb.MovieDetails;
import tech.ada.java.movieslibrary.client.omdb.MovieMinimalRestRepository;
import tech.ada.java.movieslibrary.client.omdb.ResultSearch;

@RestController
@RequestMapping("/admin/movies-manager")
@RequiredArgsConstructor
@Log4j2
public class MovieManagerRestController {

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

    @GetMapping
    public List<MovieResponse> listar() {
        return this.movieJpaRespository.findAll().stream()
            .map(MovieResponse::of)
            .toList();
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public MovieResponse salvar(@PathVariable String id) {
        MovieDetails movieDetails = this.getMovieDetails(id);
        Optional<MovieModel> movieModelOptional = this.movieJpaRespository.findByImdbID(movieDetails.getImdbID());
        if (movieModelOptional.isPresent() && movieModelOptional.get().getAvailable()) {
            throw new DuplicatedException("Filme já cadastrado");
        }
        MovieModel movieModel = MovieModel.from(movieDetails);
        this.movieJpaRespository.save(movieModel);
        return MovieResponse.of(movieModel);
    }

    @PutMapping("/{id}/{available-state}")
    @Transactional
    public void removerCatalogo(@PathVariable Long id, @PathVariable("available-state") AvailableState availableState) {
        MovieModel movieModel = this.movieJpaRespository.findById(id).orElseThrow();
        boolean available = switch (availableState) {
            case AVAILABLE -> true;
            case UNAVAILABLE -> false;
        };
        this.movieJpaRespository.setAvailable(available, movieModel.getId());
    }

    private enum AvailableState {
        AVAILABLE, UNAVAILABLE;
    }

}
