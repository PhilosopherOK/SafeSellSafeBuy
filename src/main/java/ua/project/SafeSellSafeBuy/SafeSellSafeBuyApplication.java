package ua.project.SafeSellSafeBuy;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.ui.ModelMap;
import ua.project.SafeSellSafeBuy.services.EmailSenderService;

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
