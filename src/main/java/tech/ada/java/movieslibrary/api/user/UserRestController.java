package tech.ada.java.movieslibrary.api.user;

import jakarta.validation.Valid;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class UserRestController {

    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO cadastrar(@RequestBody @Valid UserRequest userRequest) {
        Optional<UserModel> optionalUserModel = this.userJpaRepository.findByEmail(userRequest.getEmail());
        if (optionalUserModel.isPresent()) {
            throw new DuplicatedException("E-mail já cadastrado");
        }

        UserModel user = UserModel.builder()
                .email(userRequest.getEmail())
                .username(userRequest.getUsername())
                .role(userRequest.getRole())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .build();

        UserModel userModel = this.userJpaRepository.save(user);

        return new UserDTO(userModel);

    }


}
