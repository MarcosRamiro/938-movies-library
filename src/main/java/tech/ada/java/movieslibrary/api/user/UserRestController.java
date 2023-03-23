package tech.ada.java.movieslibrary.api.user;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO cadastrar(@RequestBody @Valid UserRequest userRequest) {
        UserModel userModel = this.userJpaRepository.save(this.modelMapper.map(userRequest, UserModel.class));
        return new UserDTO(userModel);
    }

    @GetMapping
    public List<UserDTO> listar() {
        return this.userJpaRepository.findAll().stream()
            .map(it -> new UserDTO(it.getName(), it.getEmail()))
            .toList();
    }

}
