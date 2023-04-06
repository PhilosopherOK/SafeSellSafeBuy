package ua.project.SafeSellSafeBuy.services;

import org.springframework.stereotype.Service;
import ua.project.SafeSellSafeBuy.repositories.UsersRepositories;

@Service
public class UsersService {
    private final UsersRepositories usersRepositories;

    public UsersService(UsersRepositories usersRepositories) {
        this.usersRepositories = usersRepositories;
    }
}
