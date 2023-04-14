package ua.project.SafeSellSafeBuy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.project.SafeSellSafeBuy.models.User;

import java.util.Optional;

@Repository
public interface UsersRepositories extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
