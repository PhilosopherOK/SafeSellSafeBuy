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
import ua.project.SafeSellSafeBuy.models.User;
import ua.project.SafeSellSafeBuy.services.ProductForCheckService;
import ua.project.SafeSellSafeBuy.services.ProductService;
import ua.project.SafeSellSafeBuy.services.UserService;
import ua.project.SafeSellSafeBuy.util.UserValidator;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final ProductService productService;
    private final UserValidator userValidator;
    private final ProductForCheckService productForCheckService;
    private final ModelMapper modelMapper;
    @Value("${upload.user.path}")
    private String uploadUserPath;



    @Autowired
    public UserController(UserService userService, ProductService productService,
                          UserValidator userValidator, ProductForCheckService productForCheckService,
                          ModelMapper modelMapper) {
        this.userService = userService;
        this.productService = productService;
        this.userValidator = userValidator;
        this.productForCheckService = productForCheckService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/profile")
    public String show(Model model) {
        model.addAttribute("user", userService.findById(userService.findNowUser().getId()));
        return "user/profile";
    }


    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,
                       Model model) {
        model.addAttribute("user", userService.findById(id));
        return "user/findProfile";
    }

    @GetMapping("/create")
    public String createUserPage(@ModelAttribute("user") User user) {
        return "user/create";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "user/create";

        userService.create(user);
        return "redirect:/auth/login";
    }


    @GetMapping("/update/{id}")
    public String updateBlanc(@PathVariable("id") int id,
                              Model model) {
        model.addAttribute("user", userService.findById(id));
        return "user/update";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user,
                         @PathVariable("id") int id, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "user/update";

        userService.update(id, user);
        return "redirect:/user/"+id;
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/product/main";
    }


    //https://mkyong.com/spring-mvc/spring-mvc-file-upload-example/
    //https://mkyong.com/spring-boot/spring-boot-configure-maxswallowsize-in-embedded-tomcat/
    //https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#appendix.application-properties
    @PostMapping("/upload")
    public String add(@RequestParam("file") MultipartFile file,
                      RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/user/profile";
        }
        if(!file.getContentType().equals("image/png") && !file.getContentType().equals("image/jpeg")){
            redirectAttributes.addFlashAttribute("message", "Please select file with png or jpg format");
            return "redirect:/user/profile";
        }

        try {
            if (file != null) {
                File uploadDir = new File("/" + uploadUserPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                file.transferTo(new File("/" + uploadUserPath + "/" + userService.findNowUser().getId() + ".png"));
                redirectAttributes.addFlashAttribute("message",
                        "You successfully uploaded '" + file.getOriginalFilename() + "'");
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/user/profile";
    }
}
