package ua.project.SafeSellSafeBuy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import ua.project.SafeSellSafeBuy.services.EmailSenderService;

@SpringBootApplication
public class SafeSellSafeBuyApplication {

//	@Autowired
//	private EmailSenderService senderService;
	public static void main(String[] args) {
		SpringApplication.run(SafeSellSafeBuyApplication.class, args);
	}
//	@EventListener(ApplicationReadyEvent.class)
//	public void sendMail(){
//		senderService.sendEmail("hnatiukrost@gmail.com",
//				"this is Subject",
//				"This is Body of Email");
//	}


}
