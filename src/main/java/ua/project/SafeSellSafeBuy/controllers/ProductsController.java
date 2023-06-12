package ua.project.SafeSellSafeBuy.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.project.SafeSellSafeBuy.models.Product;
import ua.project.SafeSellSafeBuy.models.ProductForCheck;
import ua.project.SafeSellSafeBuy.models.User;
import ua.project.SafeSellSafeBuy.services.ProductForCheckService;
import ua.project.SafeSellSafeBuy.services.ProductService;
import ua.project.SafeSellSafeBuy.services.UserService;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/product")
public class ProductsController {
    private final ProductService productService;
    private final ProductForCheckService productForCheckService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    @Value("${upload.product.path}")
    private String uploadProductPath;


    @Autowired
    public ProductsController(ProductService productService,
                              ProductForCheckService productForCheckService, UserService userService, ModelMapper modelMapper) {
        this.productService = productService;
        this.productForCheckService = productForCheckService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/main")
    public String mainPage(Model model) {
        model.addAttribute("products", productService.allProduct());
        return "product/main";
    }

    @GetMapping("/{id}")
    public String showProduct(@PathVariable("id") int id, Model model) {
        User userMain = userService.findNowUser();
        model.addAttribute("user", userMain);
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("findNowUser", userService.findNowUser());
        return "product/show";
    }

    @GetMapping("/{id}/create")
    public String createProductG(@ModelAttribute("product") Product product, @PathVariable("id") int id, Model model) {
        model.addAttribute("user_id", id);
        return "product/create";
    }

    @PostMapping("/{id}/create")
    public String createProductP(@ModelAttribute("product") @Valid Product product,
                                 @PathVariable("id") int id, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "product/create";

        ProductForCheck productForCheck = convertProductToProductForCheck(product);
        productForCheckService.create(id, productForCheck);

        return "redirect:/user/profile";
    }


    @PostMapping("/{id}/buy")
    public String takeOnBuy(@PathVariable("id") int productId, Model model) {
        User userMain = userService.findNowUser();

        Product productOnBuy = productService.findById(productId);

        productService.addProductForBuyer(userMain.getId(), productId);

        return "redirect:/product/" + productId;
    }


    @GetMapping("/{id}/update")
    public String updateProductG(@PathVariable("id") int id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "product/update";
    }

    @PatchMapping("/{id}")
    public String updateProductP(@PathVariable("id") int id,
                                 @ModelAttribute("product") @Valid Product product,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "product/update";

        productService.updateProduct(id, product);
        return "redirect:/product/" + id;
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") int id) {
        productService.deleteProduct(id);
        return "redirect:/product/main";
    }

    private ProductForCheck convertProductToProductForCheck(Product product) {
        return modelMapper.map(product, ProductForCheck.class);
    }


//    https://mkyong.com/spring-mvc/spring-mvc-file-upload-example/
//      https://mkyong.com/spring-boot/spring-boot-file-upload-example/
//    https://mkyong.com/spring/spring-mvc-file-upload-example-commons-fileupload/

    @PostMapping("/uploadMulti/{id}")
    public String multiFileUpload(@RequestParam("files") MultipartFile[] files,
                                  @PathVariable("id") int id,
                                  RedirectAttributes redirectAttributes) {

        int tempId = 0;
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                productService.standardAvatarForProduct(id, tempId);
            } else {
                if (!file.getContentType().equals("image/png") && !file.getContentType().equals("image/jpeg")) {
                    redirectAttributes.addFlashAttribute("message", "Please select file with png or jpg format");
                    return "redirect:/product/" + id;
                }

                try {
                    if (file != null) {
                        File uploadDir = new File("/" + uploadProductPath);
                        if (!uploadDir.exists()) {
                            uploadDir.mkdir();
                        }
                        file.transferTo(new File("/" + uploadProductPath + "/" + id + "." + tempId + ".png"));
                        redirectAttributes.addFlashAttribute("message",
                                "You successfully uploaded all images :)");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            tempId++;
        }
        return "redirect:/product/" + id;
    }


}
