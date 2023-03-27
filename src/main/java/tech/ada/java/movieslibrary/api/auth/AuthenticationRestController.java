package tech.ada.java.movieslibrary.api.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import tech.ada.java.movieslibrary.api.system.JwtService;
import tech.ada.java.movieslibrary.api.user.UserJpaRepository;
import tech.ada.java.movieslibrary.api.user.UserModel;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Log
public class AuthenticationRestController {

    private final UserJpaRepository userJpaRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse login(@RequestBody AuthenticationRequest request){

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.username(),
                    request.password()
            )
        );

        UserModel user = userJpaRepository.findByUsername(request.username()).orElseThrow();

        String token = jwtService.createToken(user);

        return  new AuthenticationResponse(user.getId(), token);
    }

}
