package com.codedifferently.tankofamerica;

import com.codedifferently.tankofamerica.domain.account.models.Account;
import com.codedifferently.tankofamerica.domain.account.repos.AccountRepo;
import com.codedifferently.tankofamerica.domain.account.services.AccountService;
import com.codedifferently.tankofamerica.domain.transaction.models.Transaction;
import com.codedifferently.tankofamerica.domain.transaction.repos.TransactionRepo;
import com.codedifferently.tankofamerica.domain.user.Exceptions.UserNotFoundException;
import com.codedifferently.tankofamerica.domain.user.models.User;
import com.codedifferently.tankofamerica.domain.user.repos.UserRepo;
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
import java.util.UUID;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "= false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT + ".enabled=false"
})
@ExtendWith(SpringExtension.class)
public class AccountImplementationTest {
    @Autowired
    private AccountService accountService;

    @MockBean
    private AccountRepo accountRepo;

    @MockBean
    private TransactionRepo transactionRepo;

    @MockBean
    private UserRepo userRepo;

    @Test
    public void createTest() throws UserNotFoundException {
        User mockUser = new User("tama", "jong", "tj@gmail", "password");
        mockUser.setId(1l);
        BDDMockito.doReturn(Optional.of(mockUser)).when(userRepo).findById(1l);
        Account mockAccount = new Account("savings", mockUser);
        Account afterAccount = new Account("savings", mockUser);
        UUID id = new UUID(30,30);

        afterAccount.setId(id);
        BDDMockito.doReturn(afterAccount).when(accountRepo).save(mockAccount);
        String actual = accountService.create(1l, mockAccount).toString();
        Assertions.assertEquals(afterAccount.toString(),actual);
    }


}
