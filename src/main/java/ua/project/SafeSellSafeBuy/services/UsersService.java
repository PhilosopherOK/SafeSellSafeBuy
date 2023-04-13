package ua.project.SafeSellSafeBuy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ua.project.SafeSellSafeBuy.models.Product;
import ua.project.SafeSellSafeBuy.models.User;
import ua.project.SafeSellSafeBuy.repositories.ProductRepositories;
import ua.project.SafeSellSafeBuy.repositories.UsersRepositories;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class UsersService {
    private final UsersRepositories usersRepositories;
    private final ProductRepositories productRepositories;

    @Autowired
    public UsersService(UsersRepositories usersRepositories, ProductRepositories productRepositories) {
        this.usersRepositories = usersRepositories;
        this.productRepositories = productRepositories;
    }
    @Transactional(readOnly = true)
    public User findById(int id) {
        return usersRepositories.findById(id).orElse(null);
    }

    public void update(int id, User user) {
        user.setId(id);
        usersRepositories.save(user);
    }


    public void create(User user) {
        usersRepositories.save(user);
    }


    public void delete(int id){
        usersRepositories.deleteById(id);
    }


    public void saveImage(MultipartFile imageFile) throws IOException {
        String folder = "/image/";
        byte[] bytes = imageFile.getBytes();
        Path path = Paths.get(folder + imageFile.getOriginalFilename());
        Files.write(path, bytes);
    }
}
