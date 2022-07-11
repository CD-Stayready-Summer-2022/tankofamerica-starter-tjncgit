package com.codedifferently.tankofamerica.domain.user.services;

import com.codedifferently.tankofamerica.domain.account.models.Account;
import com.codedifferently.tankofamerica.domain.account.services.AccountService;
import com.codedifferently.tankofamerica.domain.transaction.services.TransactionService;
import com.codedifferently.tankofamerica.domain.user.Exceptions.InvalidCredentialsException;
import com.codedifferently.tankofamerica.domain.user.Exceptions.UserNotFoundException;
import com.codedifferently.tankofamerica.domain.user.models.User;
import com.codedifferently.tankofamerica.domain.user.repos.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private UserRepo userRepo;
    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User create(User user){
        return userRepo.save(user);
    }

    @Override
    public User update(User user) {
        return userRepo.save(user);
    }

    @Override
    public Boolean delete(User user) {
        userRepo.delete(user);
        return null;
    }

    public String getAllUsers(){
        StringBuilder builder = new StringBuilder();
        Iterable<User> users = userRepo.findAll();
        for (User user:users) {
            builder.append(user.toString() +"\n");
        }
        return builder.toString().trim();
    }

    @Override
    public User getById(Long id) throws UserNotFoundException {
        Optional<User> optional = userRepo.findById(id);
        if(optional.isEmpty())
                throw new UserNotFoundException("user with " + id + "not found");
        return  optional.get();
    }

    @Override
    public User getByEmail(String email) throws UserNotFoundException {
        Optional<User> optional = userRepo.findByEmail(email);
        if(optional.isEmpty())
            throw new UserNotFoundException("user " + email + "not found");
        return optional.get();
    }

    @Override
    public Boolean validatePassword(User user, String password) throws InvalidCredentialsException {
        if(!user.getPassword().equals(password))
            throw new InvalidCredentialsException("password is incorrect");
        return true;
    }

    @Override
    public  User signIn(String email, String password) throws UserNotFoundException, InvalidCredentialsException {
        User user = getByEmail(email);
        validatePassword(user, password);
        return user;
    }

    @Override
    public User changeEmail(User user, String newEmail) {
        user.setEmail(newEmail);
        update(user);
        return update(user);
    }

    @Override
    public User changePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        return update(user);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserServiceImpl that = (UserServiceImpl) o;
        return Objects.equals(userRepo, that.userRepo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userRepo);
    }
}
