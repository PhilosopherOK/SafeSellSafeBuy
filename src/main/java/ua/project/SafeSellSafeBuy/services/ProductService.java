package ua.project.SafeSellSafeBuy.services;


import org.springframework.stereotype.Service;
import ua.project.SafeSellSafeBuy.models.Product;
import ua.project.SafeSellSafeBuy.models.User;
import ua.project.SafeSellSafeBuy.repositories.ProductRepositories;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepositories productRepositories;
    private final UserService usersService;

    public ProductService(ProductRepositories productRepositories, UserService userService) {
        this.productRepositories = productRepositories;

        this.usersService = userService;
    }

    public List<Product> allProduct(){
        return productRepositories.findAll();
    }
    public Product findByStartingTitleWith(String titleStartingWith){
        return productRepositories.findByTitleStartingWith(titleStartingWith).
                stream().findAny().orElse(null);
    }

    public Product findById(int id){
        return productRepositories.findById(id).orElse(null);
    }

    public void createProduct(Product product){
        int temp;
        if(allProduct().isEmpty()){
            temp = 1;
        }else{
            temp = (findById(allProduct().size()).getId() + 1) ;
        }
        product.setId(temp);
        productRepositories.save(product);
    }
    public void deleteProduct(int id){
        if (findById(id).getSeller() != null){
            findById(id).getSeller().getSellProducts().remove(findById(id));
        }else if (findById(id).getBuyer() != null){
            findById(id).getBuyer().getBuyProducts().remove(findById(id));
        }
        productRepositories.deleteById(id);
    }
    public void updateProduct(int id, Product product){
        product.setId(id);
        productRepositories.save(product);
    }

    public void addProductOnSell(int id, Product product) {
        User user = usersService.findById(id);
        user.setSellProducts(product);
        Product productWithSeller = findById(product.getId());
        productWithSeller.setSeller(user);
        usersService.update(user.getId(), user);
        updateProduct(productWithSeller.getId(), productWithSeller);
    }


    public void addProductOnBuy(int id, Product product) {
        User user = usersService.findById(id);
        user.setBuyProducts(product);
        findById(product.getId()).setBuyer(user);
    }





}
