package tech.ada.java.movieslibrary.api.auth;

import org.springframework.data.repository.CrudRepository;
public interface TokenBlocklistRepository extends CrudRepository<TokenModel, Long> {

    boolean existsByToken(String token);

}
