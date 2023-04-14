package ua.project.SafeSellSafeBuy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.project.SafeSellSafeBuy.models.User;
import ua.project.SafeSellSafeBuy.repositories.UsersRepositories;

import java.util.Optional;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {


    private final UsersRepositories usersRepositories;

    @Autowired
    public UserDetailsService(UsersRepositories usersRepositories) {
        this.usersRepositories = usersRepositories;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = usersRepositories.findByUsername(username);

        if(user.isEmpty())
            throw new UsernameNotFoundException("User not found !");

        return new ua.project.SafeSellSafeBuy.security.UserDetails(user.get());
    }
}
