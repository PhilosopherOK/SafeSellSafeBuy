package ua.project.SafeSellSafeBuy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.project.SafeSellSafeBuy.models.ProductForCheck;

@Repository
public interface ProductForCheckRepositories extends JpaRepository<ProductForCheck, Integer> {
}
