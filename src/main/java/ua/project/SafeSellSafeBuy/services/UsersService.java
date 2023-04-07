package ua.project.SafeSellSafeBuy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.project.SafeSellSafeBuy.models.Product;
import ua.project.SafeSellSafeBuy.models.User;
import ua.project.SafeSellSafeBuy.repositories.ProductRepositories;
import ua.project.SafeSellSafeBuy.repositories.UsersRepositories;

@Service
public class UsersService {
    private final UsersRepositories usersRepositories;
    private final ProductRepositories productRepositories;

    @Autowired
    public UsersService(UsersRepositories usersRepositories, ProductRepositories productRepositories) {
        this.usersRepositories = usersRepositories;
        this.productRepositories = productRepositories;
    }

    public User findOne(int id) {
        return usersRepositories.findById(id).orElse(null);
    }

    public void update(int id, User user) {
        user.setId(id);
        usersRepositories.save(user);
    }

    public void create(User user) {
        usersRepositories.save(user);
    }

    public void addProductOnSell(int id, Product product) {
        User user = findOne(id);
        user.setSellProducts(product);
        productRepositories.findById(product.getId()).get().setSeller(user);
    }

    public void addProductOnBuy(int id, Product product) {
        User user = findOne(id);
        user.setBuyProducts(product);
        productRepositories.findById(product.getId()).get().setBuyer(user);
    }
}
