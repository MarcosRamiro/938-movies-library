package tech.ada.java.movieslibrary.api.user;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Log4j2
public class UserRestController {

    private final UserJpaRepository userJpaRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO cadastrar(@RequestBody @Valid UserRequest userRequest) {
        Optional<UserModel> optionalUserModel = this.userJpaRepository.findByEmail(userRequest.getEmail());
        if (optionalUserModel.isPresent()) {
            throw new DuplicatedEmailException("E-mail já cadastrado");
        }
        UserModel userModel = this.userJpaRepository.save(UserModel.from(userRequest));
        return new UserDTO(userModel);
    }

    @GetMapping
    public List<UserDTO> listar() {
        return this.userJpaRepository.findAll().stream()
            .map(it -> new UserDTO(it.getUsername(), it.getEmail()))
            .toList();
    }

}
