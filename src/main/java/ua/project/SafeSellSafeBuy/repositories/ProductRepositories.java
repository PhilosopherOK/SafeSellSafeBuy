package ua.project.SafeSellSafeBuy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.project.SafeSellSafeBuy.models.Product;

import java.util.List;

@Repository
public interface ProductRepositories extends JpaRepository<Product, Integer> {
    List<Product> findByTitleStartingWith(String startingWith);
}
