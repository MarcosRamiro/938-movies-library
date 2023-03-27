package tech.ada.java.movieslibrary.api.movie;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
@Log4j2
public class MovieListRestController {

    private final MovieJpaRespository movieJpaRespository;

    @GetMapping
    public List<MovieResponse> listar() {
        return this.movieJpaRespository.findAllByAvailableTrue().stream()
            .map(MovieResponse::of)
            .toList();
    }

    @GetMapping(params = "titulo")
    public List<MovieResponse> consultar(@RequestParam String titulo) {
        return this.movieJpaRespository.findByTitleContainingAndAvailableTrue(titulo).stream()
            .map(MovieResponse::of)
            .toList();
    }

}
