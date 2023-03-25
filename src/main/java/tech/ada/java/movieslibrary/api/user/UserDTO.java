package tech.ada.java.movieslibrary.api.user;

public record UserDTO(String username, String email) {

    public UserDTO(UserModel userModel) {
        this(userModel.getUsername() , userModel.getEmail());
    }

}
