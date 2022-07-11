package com.codedifferently.tankofamerica;

import com.codedifferently.tankofamerica.domain.user.Exceptions.InvalidCredentialsException;
import com.codedifferently.tankofamerica.domain.user.Exceptions.UserNotFoundException;
import com.codedifferently.tankofamerica.domain.user.models.User;
import com.codedifferently.tankofamerica.domain.user.repos.UserRepo;
import com.codedifferently.tankofamerica.domain.user.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "= false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT + ".enabled=false"
})
@ExtendWith(SpringExtension.class)
public class UserImplementationTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepo userRepo;

    @Test
    public void getAllUsersTest01() throws UserNotFoundException {
        //Given
        User mockUser = new User("optimus","prime", "prime@gmail.com", "allspark" );
        mockUser.setId(3l);
        BDDMockito.doReturn(Optional.of(mockUser)).when(userRepo).findById(3l);
        //When
        User userActual = userService.getById(3l);
        //Then
        Assertions.assertEquals(mockUser, userActual);
    }

    @Test
    public void getAllUsersTest02() {
        BDDMockito.doReturn(Optional.empty()).when(userRepo).findById(1l);
        Assertions.assertThrows(UserNotFoundException.class, ()-> {
            userService.getById(1l);
        });
    }

    @Test
    public void getByEmailTest01() throws UserNotFoundException {
        //Given
        User mockUser = new User("optimus","prime", "prime@gmail.com", "allspark" );
        mockUser.setId(3l);
        BDDMockito.doReturn(Optional.of(mockUser)).when(userRepo).findByEmail("prime@gmail.com");
        //When
        User userActual = userService.getByEmail("prime@gmail.com");
        //Then
        Assertions.assertEquals(mockUser, userActual);
    }
    @Test
    public void getByEmailTest02() {
        BDDMockito.when(userRepo.findByEmail("prime@gmail.com")).thenReturn(Optional.empty()); ;
        Assertions.assertThrows(UserNotFoundException.class, ()-> {
            userService.getByEmail("prime@gmail.com");
        });
    }

    @Test
    public void validatePasswordTest01() throws InvalidCredentialsException {
        //Given
        User validUser = new User("optimus","prime", "prime@gmail.com", "allspark" );
        String password = "allspark";
        //When
        Boolean isValidated = userService.validatePassword(validUser, password);
        //Then
        Assertions.assertTrue(isValidated);
    }

    @Test public void  validatePasswordTest02() {
        User validUser = new User("optimus","prime", "prime@gmail.com", "allspark" );
        String password = "decepticon";
        Assertions.assertThrows(InvalidCredentialsException.class, ()-> {
            userService.validatePassword(validUser, password);
        });
    }

    @Test
    public void signInTest() throws UserNotFoundException, InvalidCredentialsException {
        //Given
        User mockUser = new User("optimus","prime", "prime@gmail.com", "allspark" );
        mockUser.setId(1l);
        String eMail = "prime@gmail.com";
        String password = "allspark";
        //When
        BDDMockito.doReturn(Optional.of(mockUser)).when(userRepo).findByEmail("prime@gmail.com");
        String actualUser = userService.signIn(eMail, password).toString();
        //Then
        Assertions.assertEquals(mockUser.toString(), actualUser);
    }

    @Test
    public void changeEmailTest() {
        User beforeUser = new User("optimus","prime", "prime@gmail.com", "allspark" );
        beforeUser.setEmail("autobots@aol.com");
        User afterUser = new User("optimus","prime", "autobots@aol.com", "allspark" );
        BDDMockito.doReturn(afterUser).when(userRepo).save(beforeUser);
        String expected = afterUser.toString();
        String actual = userService.changeEmail(beforeUser, "autobots@aol.com").toString();
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void changePasswordTest() {
        User beforeUser = new User("optimus","prime", "prime@gmail.com", "allspark" );
        beforeUser.setPassword("decepticons");
        User afterUser = new User("optimus","prime", "autobots@aol.com", "decepticons" );
        BDDMockito.doReturn(afterUser).when(userRepo).save(beforeUser);
        String expected = afterUser.toString();
        String actual = userService.changePassword(beforeUser, "decepti").toString();
        Assertions.assertEquals(expected, actual);
    }
}
