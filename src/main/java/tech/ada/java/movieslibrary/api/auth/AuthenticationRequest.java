package tech.ada.java.movieslibrary.api.auth;

import jakarta.validation.constraints.NotEmpty;

public record AuthenticationRequest (@NotEmpty String username, @NotEmpty String password){}
