package tech.ada.java.movieslibrary.api.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<UserModel, Long> {

    Optional<UserModel> findByEmail(String email);

}
