package ua.project.SafeSellSafeBuy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.project.SafeSellSafeBuy.models.User;
import ua.project.SafeSellSafeBuy.services.ProductService;
import ua.project.SafeSellSafeBuy.services.UsersService;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UsersController {
    private final UsersService usersService;
    private final ProductService productService;

    @Autowired
    public UsersController(UsersService usersService, ProductService productService) {
        this.usersService = usersService;
        this.productService = productService;
    }

    @GetMapping("/create")
    public String createUser(@ModelAttribute("user") User user) {
        return "user/create";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "user/create";

        usersService.create(user);
        return "redirect:/user";
    }

    @GetMapping("/{id}/update")
    public String updateBlanc(@PathVariable("id") int id,
                              Model model){
        model.addAttribute("user", usersService.findOne(id));
        return "user/update";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user,@PathVariable("id") int id,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "user/update";

        usersService.update(id, user);
        return "redirect:/user";
    }
}
