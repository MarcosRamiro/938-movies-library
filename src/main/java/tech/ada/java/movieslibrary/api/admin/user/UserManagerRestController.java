package tech.ada.java.movieslibrary.api.admin.user;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.ada.java.movieslibrary.api.user.UserDTO;
import tech.ada.java.movieslibrary.api.user.UserJpaRepository;

@RestController
@RequestMapping("/admin/users")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class UserManagerRestController {

    private final UserJpaRepository userJpaRepository;

    @GetMapping
    public List<UserDTO> listar() {
        return this.userJpaRepository.findAll().stream()
            .map(UserDTO::new)
            .toList();
    }

}
