package ua.project.SafeSellSafeBuy.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.project.SafeSellSafeBuy.models.Product;
import ua.project.SafeSellSafeBuy.models.ProductForCheck;
import ua.project.SafeSellSafeBuy.models.User;
import ua.project.SafeSellSafeBuy.security.UserDetails;
import ua.project.SafeSellSafeBuy.services.ProductForCheckService;
import ua.project.SafeSellSafeBuy.services.ProductService;
import ua.project.SafeSellSafeBuy.services.UserService;
import ua.project.SafeSellSafeBuy.util.UserValidator;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final ProductService productService;
    private final UserValidator userValidator;
    private final ProductForCheckService productForCheckService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ProductService productService, UserValidator userValidator, ProductForCheckService productForCheckService, ModelMapper modelMapper) {
        this.userService = userService;
        this.productService = productService;
        this.userValidator = userValidator;
        this.productForCheckService = productForCheckService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/profile")
    public String show(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User userMain = userDetails.getUser();

        model.addAttribute("user", userService.findById(userMain.getId()));
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


    @GetMapping("/{id}/update")
    public String updateBlanc(@PathVariable("id") int id,
                              Model model) {
        model.addAttribute("user", userService.findById(id));
        return "user/update";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user, @PathVariable("id") int id,
                         BindingResult bindingResult) {
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



    @Value("${upload.path}")
    private String uploadPath;

    @PostMapping("/upload")
    public String add(@RequestParam("file") MultipartFile file) throws IOException {

        if(file != null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));
        }
        return "user/profile";
    }



//    @PostMapping("/uploadImage")
//    public String uploadImage(@RequestParam("imageFile")MultipartFile imageFile,
//                              Model model){
//        String returnValue = "user/profile";
//        try {
//            userService.saveImage(imageFile);
//            User user = new User();
//            model.addAttribute("user", user);
//            returnValue = "user/profile";
//        } catch (IOException e) {
//            e.printStackTrace();
//            returnValue = "error";
//        }
//        return returnValue;
//    }


}
