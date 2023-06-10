package ua.project.SafeSellSafeBuy;

import com.stripe.Stripe;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;


@SpringBootApplication
public class SafeSellSafeBuyApplication {

    @PostConstruct
    public void setup(){
        Stripe.apiKey = "sk_test_51MzlRYFqvRFvrqeTIGUTEjV3Z0LhMNF1ujwBW8GGKVMWirqKHrNzAGmwUY4FW3ee2QGZT3jdMX4Zua99rampRmrg005lMI2PeZ";
    }

    public static void main(String[] args) {
        SpringApplication.run(SafeSellSafeBuyApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}