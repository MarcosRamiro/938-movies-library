package tech.ada.java.movieslibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MoviesLibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoviesLibraryApplication.class, args);
    }

}
