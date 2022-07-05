package com.codedifferently.tankofamerica;

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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserImplementationTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepo userRepo;

    @Test
    public void getAllUsersTest() throws UserNotFoundException {
        //Given
        User mockUser = new User("tama","jong", "tama@g.com", "sfsdfs" );
        mockUser.setId(1l);
        BDDMockito.doReturn(Optional.of(mockUser)).when(userRepo).findById(1l);
        //When
        User userActual = userService.getById(1l);
        //Then
        Assertions.assertEquals(mockUser, userActual);

    }

}
