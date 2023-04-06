package ua.project.SafeSellSafeBuy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.project.SafeSellSafeBuy.models.Product;

@Repository
public interface ProductRepositories extends JpaRepository<Product, Integer> {
}
