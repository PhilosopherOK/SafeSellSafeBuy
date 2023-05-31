package ua.project.SafeSellSafeBuy;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class SafeSellSafeBuyApplication {
    public static void main(String[] args) {
        SpringApplication.run(SafeSellSafeBuyApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


}
