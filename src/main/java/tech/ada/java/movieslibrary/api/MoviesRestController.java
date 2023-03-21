package tech.ada.java.movieslibrary.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.ada.java.movieslibrary.omdb.MovieDetails;
import tech.ada.java.movieslibrary.omdb.MovieMinimalRestRepository;
import tech.ada.java.movieslibrary.omdb.ResultSearch;
import tech.ada.java.movieslibrary.omdb.dto.MovieResponse;
import tech.ada.java.movieslibrary.omdb.model.MovieModel;
import tech.ada.java.movieslibrary.omdb.reposiory.MovieJpaRespository;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MoviesRestController {

    private final MovieMinimalRestRepository restRepository;
    private final MovieJpaRespository movieJpaRespository;

    public MoviesRestController(
            MovieMinimalRestRepository restRepository,
            MovieJpaRespository movieJpaRespository) {
        this.restRepository = restRepository;
        this.movieJpaRespository = movieJpaRespository;
    }

    @GetMapping(params = "nome")
    public ResultSearch buscar(@RequestParam String nome) {
        return this.restRepository.search(nome);
    }

    @GetMapping("/{id}")
    public MovieDetails detalhes(@PathVariable String id){
        return this.restRepository.details(id);
    }

    @PostMapping("/salvar")
    public ResponseEntity<MovieResponse> salvar(
            @RequestBody MovieDetails movieDetails){

        MovieModel movie = movieDetails.toModel();

        movieJpaRespository.save(movie);

        return ResponseEntity.ok(MovieResponse.of(movie));

    }

    @GetMapping("/favoritos" )
    public List<MovieResponse> favoritos(){

        return movieJpaRespository.findAll().stream()
                .map(MovieResponse::of)
                .toList();
    }

    @GetMapping( value = "/favoritos/consulta", params = "titulo")
    public List<MovieResponse> consultaFavoritos(@RequestParam String titulo){

        return movieJpaRespository.findByTitleContaining(titulo).stream()
                .map(MovieResponse::of)
                .toList();

    }

}
