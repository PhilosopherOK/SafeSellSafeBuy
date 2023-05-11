package ua.project.SafeSellSafeBuy.controllers;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.project.SafeSellSafeBuy.models.Product;
import ua.project.SafeSellSafeBuy.models.ProductForCheck;
import ua.project.SafeSellSafeBuy.models.User;
import ua.project.SafeSellSafeBuy.services.EmailSenderService;
import ua.project.SafeSellSafeBuy.services.ProductForCheckService;
import ua.project.SafeSellSafeBuy.services.ProductService;
import ua.project.SafeSellSafeBuy.services.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ProductService productService;
    private final ProductForCheckService productForCheckService;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final EmailSenderService senderService;

    @Autowired
    public AdminController(ProductService productService, ProductForCheckService productForCheckService,
                           ModelMapper modelMapper, UserService userService, EmailSenderService senderService) {
        this.productService = productService;
        this.productForCheckService = productForCheckService;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.senderService = senderService;
    }

    @GetMapping("/checkOrder")
    public String checkOrder(Model model){
        model.addAttribute("listProductForCheck", productForCheckService.findAll());
        return "admin/checkOrder";
    }

    @PostMapping("/checkOrder/good/{id}")
    public String checkOrderReady(@PathVariable("id") int id){

        ProductForCheck productForCheck = productForCheckService.findById(id);

        Product product = convertProductForCheckToProduct(productForCheck);

        productService.createProduct(product.getSeller().getId(), product);
        productForCheckService.delete(id);
        return "redirect:/admin/checkOrder";
    }

    @PostMapping("/checkOrder/bad/{id}")
    public String checkOrderNotReady(@PathVariable("id") int id){
        ProductForCheck productForCheck = productForCheckService.findById(id);
        User user = productForCheck.getSeller();

        senderService.sendEmail(user.getUser_email(),
                productForCheck.getTitle() + "kind regards Team safe sell safe buy !",
                "Dear " + user.getUsername() + "\n" +
                        "your product has not passed the test and cannot be listed," + "\n" +
                        "please correct the data and register the product again. " +"\n" +
                        "Kind regards Team SafeSellSafeBuy ");

        return "redirect:/admin/checkOrder";
    }
    private Product convertProductForCheckToProduct(ProductForCheck productForCheck) {
        return modelMapper.map(productForCheck, Product.class);
    }
}
