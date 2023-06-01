package ua.project.SafeSellSafeBuy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.project.SafeSellSafeBuy.models.User;
import ua.project.SafeSellSafeBuy.repositories.ProductRepositories;
import ua.project.SafeSellSafeBuy.repositories.UserRepositories;
import ua.project.SafeSellSafeBuy.security.UserDetails;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;


@Service
public class UserService {
    private final UserRepositories userRepositories;
    private final PasswordEncoder passwordEncoder;
    private final ProductRepositories productRepositories;
    private final EmailSenderService senderService;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public UserService(UserRepositories userRepositories, PasswordEncoder passwordEncoder, ProductRepositories productRepositories, EmailSenderService senderService) {
        this.userRepositories = userRepositories;
        this.passwordEncoder = passwordEncoder;
        this.productRepositories = productRepositories;
        this.senderService = senderService;
    }

    public User findNowUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User userMain = userDetails.getUser();
        return userMain;
    }

    @Transactional(readOnly = true)
    public User findById(int id) {
        return userRepositories.findById(id).orElse(null);
    }


    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepositories.findByUsername(username).orElse(null);
    }

    public void update(int id, User user) {
        user.setId(id);
        userRepositories.save(user);
    }


    public void create(User user) {
        int temp;
        if (userRepositories.findAll().isEmpty()) {
            temp = 1;
        } else {
            temp = (findById(userRepositories.findAll().size()).getId() + 1);
        }
        user.setId(temp);

        senderService.sendEmail(user.getUser_email(),
                "Dear " + user.getFirst_name(),
                "Your email: " + user.getUser_email() + "\n" +
                        "Login: " + user.getUsername() + "\n" +
                        "Password: " + user.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userRepositories.save(user);

        String sourceDirName = uploadPath + "/standardAva.png";
        String targetSourceDir = uploadPath + "/" + user.getId() + ".png";
        try {
            copyDir(sourceDirName, targetSourceDir);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void copyDir(String sourceDirName, String targetSourceDir) throws IOException {
        File file = new File(sourceDirName);
        Path destDir = Paths.get(targetSourceDir);
        File file1 = new File(String.valueOf(Files.copy(file.toPath(), destDir.resolve(file.getName()), StandardCopyOption.REPLACE_EXISTING)));
        Files.copy(file.toPath(), destDir.resolve(file.getName()), StandardCopyOption.REPLACE_EXISTING);
    }


    public void delete(int id) {
        userRepositories.deleteById(id);
    }


    public void saveImage(MultipartFile imageFile) throws IOException {
        String folder = "/image/";
        byte[] bytes = imageFile.getBytes();
        Path path = Paths.get(folder + imageFile.getOriginalFilename());
        Files.write(path, bytes);
    }
}
