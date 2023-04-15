package ua.project.SafeSellSafeBuy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.project.SafeSellSafeBuy.models.User;
import ua.project.SafeSellSafeBuy.repositories.UserRepositories;

import java.util.Optional;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {


    private final UserRepositories userRepositories;

    @Autowired
    public UserDetailsService(UserRepositories userRepositories) {
        this.userRepositories = userRepositories;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepositories.findByUsername(username);

        if(user.isEmpty())
            throw new UsernameNotFoundException("User not found !");

        return new ua.project.SafeSellSafeBuy.security.UserDetails(user.get());
    }
}
