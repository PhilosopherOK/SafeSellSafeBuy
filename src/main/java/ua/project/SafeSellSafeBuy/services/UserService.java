package ua.project.SafeSellSafeBuy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ua.project.SafeSellSafeBuy.models.User;
import ua.project.SafeSellSafeBuy.repositories.ProductRepositories;
import ua.project.SafeSellSafeBuy.repositories.UserRepositories;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class UserService {
    private final UserRepositories userRepositories;
    private final PasswordEncoder passwordEncoder;
    private final ProductRepositories productRepositories;

    @Autowired
    public UserService(UserRepositories userRepositories, PasswordEncoder passwordEncoder, ProductRepositories productRepositories) {
        this.userRepositories = userRepositories;
        this.passwordEncoder = passwordEncoder;
        this.productRepositories = productRepositories;
    }
    @Transactional(readOnly = true)
    public User findById(int id) {
        return userRepositories.findById(id).orElse(null);
    }


    @Transactional(readOnly = true)
    public User findByUsername(String username){
        return userRepositories.findByUsername(username).orElse(null);
    }

    public void update(int id, User user) {
        user.setId(id);
        userRepositories.save(user);
    }


    public void create(User user) {
        int temp;
        if(userRepositories.findAll().isEmpty()){
            temp = 1;
        }else{
            temp = (findById(userRepositories.findAll().size()).getId() + 1) ;
        }
        user.setId(temp);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userRepositories.save(user);
    }


    public void delete(int id){
        userRepositories.deleteById(id);
    }


    public void saveImage(MultipartFile imageFile) throws IOException {
        String folder = "/image/";
        byte[] bytes = imageFile.getBytes();
        Path path = Paths.get(folder + imageFile.getOriginalFilename());
        Files.write(path, bytes);
    }



}
