package ua.project.SafeSellSafeBuy.services;

import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import ua.project.SafeSellSafeBuy.models.Product;
import ua.project.SafeSellSafeBuy.repositories.ProductRepositories;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepositories productRepositories;

    public ProductService(ProductRepositories productRepositories) {
        this.productRepositories = productRepositories;
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







}
