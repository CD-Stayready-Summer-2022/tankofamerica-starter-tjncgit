package com.codedifferently.tankofamerica.domain.user.controllers;

import com.codedifferently.tankofamerica.domain.user.Exceptions.InvalidCredentialsException;
import com.codedifferently.tankofamerica.domain.user.Exceptions.UserNotFoundException;
import com.codedifferently.tankofamerica.domain.user.models.User;
import com.codedifferently.tankofamerica.domain.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class UserController {
    public UserService userService;

    @Autowired
    public static User currentUser;

    @Autowired
    public UserController(UserService userService, User currentUser) {
        this.userService = userService;
        this.currentUser = null;
    }

    @ShellMethod("Create a new User")
    public User createNewUser(@ShellOption({"-F", "--firstname"}) String firstName,
                              @ShellOption({"-L", "--lastname"})String lastName,
                              @ShellOption({"-E", "--email"})String email,
                              @ShellOption({"-P", "--password"})String password){
        User user = new User(firstName,lastName,email,password);
        user = userService.create(user);
        return user;
    }

    @ShellMethod("Get All Users")
    public String getAllUsers(){
        return userService.getAllUsers();
    }

    @ShellMethod("Log In")
    public String logIn(@ShellOption({"-E", "-email"}) String userName, @ShellOption({"-P", "-pwd"}) String password){
        try{
            currentUser = userService.signIn(userName, password);
            return currentUser.getEmail() + " is signed In";
        } catch (UserNotFoundException e){
            return e.getMessage();
        } catch (InvalidCredentialsException e) {
            return e.getMessage();
        }
    }

    @ShellMethod("Change E-Mail")
    public User changeEmail(@ShellOption({"-E", "--email"}) String newEmail) {
        return userService.changeEmail(currentUser, newEmail);
    }
    @ShellMethod("Change password")
    public User changePassword(@ShellOption({"-P", "--password"}) String newPassword) {
        return userService.changePassword(currentUser, newPassword);
    }

    @ShellMethod("Log Out")
    public String logOut() {
        currentUser = null;
        return "User is Logged Out";
    }


}
