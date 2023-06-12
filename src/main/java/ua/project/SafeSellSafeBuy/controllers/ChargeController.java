package ua.project.SafeSellSafeBuy.controllers;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ua.project.SafeSellSafeBuy.dto.ChargeRequest;
import ua.project.SafeSellSafeBuy.models.Product;
import ua.project.SafeSellSafeBuy.models.User;
import ua.project.SafeSellSafeBuy.services.ProductService;
import ua.project.SafeSellSafeBuy.services.StripeService;
import ua.project.SafeSellSafeBuy.services.UserService;

@Controller
public class ChargeController {

    private final ProductService productService;
    private final UserService userService;
    private final StripeService paymentsService;


    @Autowired
    public ChargeController(ProductService productService, UserService userService, StripeService paymentsService) {
        this.productService = productService;
        this.userService = userService;
        this.paymentsService = paymentsService;
    }

    @PostMapping("/charge/{id}")
    public String charge(@PathVariable("id") int productId, ChargeRequest chargeRequest, Model model)
            throws StripeException {
        chargeRequest.setDescription("Example charge");
        chargeRequest.setCurrency(ChargeRequest.Currency.USD);
        Charge charge = paymentsService.charge(chargeRequest);
        model.addAttribute("id", charge.getId());
        model.addAttribute("status", charge.getStatus());
        model.addAttribute("chargeId", charge.getId());
        model.addAttribute("balance_transaction", charge.getBalanceTransaction());

        User userMain = userService.findNowUser();
        Product productOnBuy = productService.findById(productId);
        productService.addProductForBuyer(userMain.getId(), productId);

        return "payment/result";
    }

    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "payment/result";
    }
}
