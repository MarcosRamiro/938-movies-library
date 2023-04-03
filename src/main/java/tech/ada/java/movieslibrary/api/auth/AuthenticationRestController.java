package tech.ada.java.movieslibrary.api.auth;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpHeaders;
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
public class AuthenticationRestController {

    private final UserJpaRepository userJpaRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private final TokenBlocklistRepository tokenBlocklistRepository;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse login(@RequestBody @Valid AuthenticationRequest request){
        var authentication = new UsernamePasswordAuthenticationToken(request.username(), request.password());
        authenticationManager.authenticate(authentication);
        UserModel user = userJpaRepository.findByUsername(request.username()).orElseThrow();
        String token = jwtService.createToken(user);
        return  new AuthenticationResponse(user.getId(), token);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){

        token = token.substring(7);
        TokenModel tokenModel = new TokenModel();
        tokenModel.setToken(token);
        tokenBlocklistRepository.save(tokenModel);

    }



}
