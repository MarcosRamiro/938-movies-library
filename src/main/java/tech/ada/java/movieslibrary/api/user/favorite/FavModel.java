package tech.ada.java.movieslibrary.api.user.favorite;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import tech.ada.java.movieslibrary.api.movie.MovieModel;
import tech.ada.java.movieslibrary.api.user.UserModel;

@Entity
@Table(name = "tb_favorite")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FavModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_model_id")
    private MovieModel movieModel;

    @ManyToOne
    @JoinColumn(name = "user_model_id")
    private UserModel userModel;

    public FavModel(MovieModel movieModel, UserModel userModel) {
        this.movieModel = movieModel;
        this.userModel = userModel;
    }
}
