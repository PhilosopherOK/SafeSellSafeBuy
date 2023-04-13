package ua.project.SafeSellSafeBuy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.project.SafeSellSafeBuy.models.Product;
import ua.project.SafeSellSafeBuy.services.ProductService;

import javax.validation.Valid;

@Controller
@RequestMapping("/product")
public class ProductsController {
    private final ProductService productService;

    @Autowired
    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/main")
    public String mainPage(Model model){
        model.addAttribute("products", productService.allProduct());
        return "product/main";
    }

    @GetMapping("/{id}")
    public String showProduct(@PathVariable("id") int id, Model model){
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

        return "redirect:/user/"+id;
      //  return "redirect:/product/main";
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
