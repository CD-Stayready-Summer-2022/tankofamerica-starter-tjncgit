package com.codedifferently.tankofamerica.domain.user.services;

import com.codedifferently.tankofamerica.domain.user.Exceptions.InvalidCredentialsException;
import com.codedifferently.tankofamerica.domain.user.Exceptions.UserNotFoundException;
import com.codedifferently.tankofamerica.domain.user.models.User;

public interface UserService {
    User create(User user);
    User update(User user);
    Boolean delete(User user);
    String getAllUsers();
    User getById(Long id) throws UserNotFoundException;
    User getByEmail(String email) throws UserNotFoundException;
    Boolean validatePassword(User user, String password) throws InvalidCredentialsException;

    String signIn(String email, String password) throws UserNotFoundException, InvalidCredentialsException;
}
