package ua.project.SafeSellSafeBuy.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.project.SafeSellSafeBuy.models.Product;
import ua.project.SafeSellSafeBuy.models.User;
import ua.project.SafeSellSafeBuy.repositories.ProductRepositories;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Random;

@Service
public class ProductService {
    private final ProductRepositories productRepositories;
    private final UserService usersService;
    private final EmailSenderService senderService;

    @Value("${upload.product.path}")
    private String uploadProductPath;

    @Autowired
    public ProductService(ProductRepositories productRepositories,
                          UserService userService, EmailSenderService senderService) {
        this.productRepositories = productRepositories;
        this.usersService = userService;
        this.senderService = senderService;
    }

    public List<Product> allProduct(Integer page) {
        if (page == null) {
            page = 0;
        }
        return productRepositories.findAll(PageRequest.of(page, 10)).getContent();
    }
    public List<Product> allProduct() {
        return productRepositories.findAll();
    }

    public Product findByStartingTitleWith(String titleStartingWith) {
        return productRepositories.findByTitleStartingWith(titleStartingWith).
                stream().findAny().orElse(null);
    }

    public Product findById(int id) {
        return productRepositories.findById(id).orElse(null);
    }

    public void createProduct(int userId, Product product) {

        productRepositories.save(product);

        User user = usersService.findById(userId);
        user.setSellProducts(product);
        usersService.update(user.getId(), user);

        for (int i = 0; i < 3; i++) {
            standardAvatarForProduct(product.getId(), i);
        }
    }


    public void deleteProduct(int id) {
        if (findById(id).getSeller() != null) {
            findById(id).getSeller().getSellProducts().remove(findById(id));
        } else if (findById(id).getBuyer() != null) {
            findById(id).getBuyer().getBuyProducts().remove(findById(id));
        }
        productRepositories.deleteById(id);
    }

    public void updateProduct(int id, Product product) {
        product.setId(id);
        productRepositories.save(product);
    }


    public void addProductForBuyer(int userId, int productId) {
        User user = usersService.findById(userId);
        Product productWithBuyer = findById(productId);

        user.setBuyProducts(productWithBuyer);
        productWithBuyer.setBuyer(user);

        usersService.update(user.getId(), user);
        updateProduct(productWithBuyer.getId(), productWithBuyer);

        senderService.sendEmail(user.getUser_email(),
                productWithBuyer.getTitle(),
                "Email in game: " + productWithBuyer.getEmail_in_game() + "\n" +
                        "Login in game: " + productWithBuyer.getLogin_in_game() + "\n" +
                        "Password in game: " + productWithBuyer.getPassword_in_game() + "\n" +
                        "Secret question:" + productWithBuyer.getSecret_question() + "\n" +
                        "Secret answer:" + productWithBuyer.getSecret_answer());
    }

    public void standardAvatarForProduct(int id, int numberOfArt) {
        Random random = new Random();
        Path source = Paths.get(
                uploadProductPath + "/standardAva.png");
        try {
            // rename a file in the same directory
            Files.copy(source, source.resolveSibling(id + "." + numberOfArt + ".png"),
                    StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
