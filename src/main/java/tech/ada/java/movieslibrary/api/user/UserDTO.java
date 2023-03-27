package tech.ada.java.movieslibrary.api.user;

public record UserDTO(String username, String email, String role) {

    public UserDTO(UserModel userModel) {
        this(userModel.getUsername() , userModel.getEmail(), userModel.getRole().toString());
    }

}
