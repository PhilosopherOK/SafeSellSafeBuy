package ua.project.SafeSellSafeBuy.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.project.SafeSellSafeBuy.dto.ChargeRequest;

//  https://www.baeldung.com/java-stripe-api
@Controller
@RequestMapping("/payment")
public class CheckoutController {

    @Value("${stripe.public.key}")
    private String stripePublicKey;

    @GetMapping("/checkout")
    public String checkout(Model model) {
        model.addAttribute("amount", 10 * 100); // in cents
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("currency", ChargeRequest.Currency.USD);
        return "payment/checkout";
    }
}