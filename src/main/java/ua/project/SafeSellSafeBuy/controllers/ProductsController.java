package ua.project.SafeSellSafeBuy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.project.SafeSellSafeBuy.models.Product;
import ua.project.SafeSellSafeBuy.models.User;
import ua.project.SafeSellSafeBuy.security.UserDetails;
import ua.project.SafeSellSafeBuy.services.EmailSenderService;
import ua.project.SafeSellSafeBuy.services.ProductService;
import ua.project.SafeSellSafeBuy.services.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/product")
public class ProductsController {
    private final EmailSenderService senderService;
    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public ProductsController(ProductService productService, EmailSenderService senderService, UserService userService) {
        this.productService = productService;
        this.senderService = senderService;
        this.userService = userService;
    }

    @GetMapping("/main")
    public String mainPage(Model model){
        model.addAttribute("products", productService.allProduct());
        return "product/main";
    }

    @GetMapping("/{id}")
    public String showProduct(@PathVariable("id") int id, Model model){
        User userMain = userService.findNowUser();
        model.addAttribute("user", userMain);
        model.addAttribute("product", productService.findById(id));
        return "product/show";
    }

    @GetMapping("/{id}/create")
    public String createProductG(@ModelAttribute("product") Product product, @PathVariable("id") int id, Model model){
        model.addAttribute("user_id", id);
        return "product/create";
    }

    @PostMapping("/{id}/create")
    public String createProductP(@ModelAttribute("product") @Valid Product product,
                                 @PathVariable("id") int id, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "product/create";

        productService.createProduct(product);
        productService.addProductOnSell(id, product);

        return "redirect:/user/profile";
    }


    @PostMapping("/{id}/buy")
    public String takeOnBuy(@PathVariable("id") int productId, Model model) {
        User userMain = userService.findNowUser();

        Product productOnBuy = productService.findById(productId);

        productService.addProductForBuyer(userMain.getId(), productId);

        senderService.sendEmail(userMain.getUser_email(),
				productOnBuy.getTitle(),
				"Email in game: " + productOnBuy.getEmail_in_game() +
                "Login in game: " + productOnBuy.getLogin_in_game() +
                "Password in game: " + productOnBuy.getPassword_in_game());

        return "redirect:/product/"+productId;
    }


    @GetMapping("/{id}/update")
    public String updateProductG(@PathVariable("id") int id, Model model){
        model.addAttribute("product", productService.findById(id));
        return "product/update";
    }

    @PatchMapping("/{id}")
    public String updateProductP(@PathVariable("id") int id,
                                 @ModelAttribute("product") @Valid Product product,
                                 BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "product/update";

        productService.updateProduct(id, product);
        return "redirect:/product/"+id;
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") int id){
        productService.deleteProduct(id);
        return "redirect:/product/main";
    }
































}
