package tech.ada.java.movieslibrary.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.ada.java.movieslibrary.omdb.MovieMinimalRestRepository;
import tech.ada.java.movieslibrary.omdb.ResultSearch;

@RestController
@RequestMapping("/movies")
public class MoviesRestController {

    private final MovieMinimalRestRepository restRepository;

    public MoviesRestController(MovieMinimalRestRepository restRepository) {
        this.restRepository = restRepository;
    }

    @GetMapping(params = "nome")
    public ResultSearch buscar(@RequestParam String nome) {
        return this.restRepository.search(nome);
    }

}
