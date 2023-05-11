package ua.project.SafeSellSafeBuy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.project.SafeSellSafeBuy.models.ProductForCheck;
import ua.project.SafeSellSafeBuy.models.User;
import ua.project.SafeSellSafeBuy.repositories.ProductForCheckRepositories;

import java.util.List;

@Service
public class ProductForCheckService {
    private final ProductForCheckRepositories productForCheckRepositories;
    private final UserService userService;

    @Autowired
    public ProductForCheckService(ProductForCheckRepositories productForCheckRepositories, UserService userService) {
        this.productForCheckRepositories = productForCheckRepositories;
        this.userService = userService;
    }

    public ProductForCheck findById(int id) {
        return (ProductForCheck) productForCheckRepositories.findById(id).orElse(null);
    }

    public List<ProductForCheck> findAll() {
        return productForCheckRepositories.findAll();
    }

    public void create(int userId, ProductForCheck productForCheck) {
        User user = userService.findById(userId);
        productForCheck.setSeller(user);

        productForCheckRepositories.save(productForCheck);
    }

    public void delete(int id) {
        productForCheckRepositories.deleteById(id);
    }
}
