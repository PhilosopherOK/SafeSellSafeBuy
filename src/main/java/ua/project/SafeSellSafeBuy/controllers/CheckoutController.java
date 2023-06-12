package ua.project.SafeSellSafeBuy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.project.SafeSellSafeBuy.dto.ChargeRequest;
import ua.project.SafeSellSafeBuy.models.Product;
import ua.project.SafeSellSafeBuy.services.ProductService;

import javax.websocket.server.PathParam;

//  https://www.baeldung.com/java-stripe-api
@Controller
@RequestMapping("/payment")
public class CheckoutController {

    private final ProductService productService;
    @Value("${stripe.public.key}")
    private String stripePublicKey;

    @Autowired
    public CheckoutController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/checkout/{id}")
    public String checkout(@PathVariable("id") int productId, Model model) {
        Product product = productService.findById(productId);
        model.addAttribute("amount", product.getPrice() * 100); // in cents
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("currency", ChargeRequest.Currency.USD);
        model.addAttribute("id", productId);
        return "payment/checkout";
    }
}