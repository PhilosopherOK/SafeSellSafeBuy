package ua.project.SafeSellSafeBuy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ua.project.SafeSellSafeBuy.models.User;
import ua.project.SafeSellSafeBuy.repositories.ProductRepositories;
import ua.project.SafeSellSafeBuy.repositories.UserRepositories;
import ua.project.SafeSellSafeBuy.security.UserDetails;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Random;


@Service
public class UserService {
    private final UserRepositories userRepositories;
    private final PasswordEncoder passwordEncoder;
    private final ProductRepositories productRepositories;
    private final EmailSenderService senderService;

    @Value("${upload.user.path}")
    private String uploadUserPath;

    @Autowired
    public UserService(UserRepositories userRepositories, PasswordEncoder passwordEncoder, ProductRepositories productRepositories, EmailSenderService senderService) {
        this.userRepositories = userRepositories;
        this.passwordEncoder = passwordEncoder;
        this.productRepositories = productRepositories;
        this.senderService = senderService;
    }

    public List<User> findAll(){
        return userRepositories.findAll();
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

    @Transactional(readOnly = true)
    public User findByUser_email(String user_email){
        return userRepositories.findByUserEmail(user_email).orElse(null);
    }

    public void update(int id, User user) {
        user.setId(id);
        user.setRole(findNowUser().getRole());
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

//        senderService.sendEmail(user.getUser_email(),
//                "Dear " + user.getFirst_name(),
//                "Your email: " + user.getUser_email() + "\n" +
//                        "Login: " + user.getUsername() + "\n" +
//                        "Password: " + user.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userRepositories.save(user);

        standardAvatar(user);
    }

    private void standardAvatar(User user) {
        Random random = new Random();
        Path source = Paths.get(
                uploadUserPath + "/standardAva"
                        + (1 + random.nextInt(7)) + ".png");
        try {
            // rename a file in the same directory
            Files.copy(source, source.resolveSibling(user.getId() + ".png"),
                    StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
        }
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
