package ua.project.SafeSellSafeBuy.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.project.SafeSellSafeBuy.models.User;
import ua.project.SafeSellSafeBuy.services.UserService;

@Component
public class UserValidator implements Validator {

    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if(user.getId() == 0) {
            if (userService.findByUsername(user.getUsername()) != null) {
                errors.rejectValue("username", "", "User with this username is present");
            }
            if (userService.findByUser_email(user.getUserEmail()) != null) {
                errors.rejectValue("userEmail", "", "Email is already taken, please choose another one");
            }
        }else{
            if (userService.findByUsername(user.getUsername()) != null) {
                if(!user.getUsername().equals(userService.findNowUser().getUsername())){
                    errors.rejectValue("username", "", "User with this username is present");
                }
            }
            if (userService.findByUser_email(user.getUserEmail()) != null) {
                if(!user.getUserEmail().equals(userService.findNowUser().getUserEmail())){
                    errors.rejectValue("userEmail", "", "Email is already taken, please choose another one");
                }
            }
        }
    }
}
