package friendfinder.persistence;

import friendfinder.Application;
import friendfinder.domain.Account;
import friendfinder.domain.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by grace on 25/06/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class DBTests {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;


    @Before
    public void setup() {
        accountRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testCreateAccount(){
        Assert.assertEquals(accountRepository.count(),0);
        Assert.assertEquals(userRepository.count(),0);

        Account account1 = new Account("david@szabo.com", "empty");
        Assert.assertNotNull(account1);
        User user1 = new User("David Szabo", "male", account1);
        account1.setUser(user1);
        Assert.assertNotNull(user1);

        accountRepository.save(account1);
        userRepository.save(user1);

        Assert.assertEquals(accountRepository.count(),1);
        Assert.assertEquals(userRepository.count(),1);
    }

    @Test
    public void testFindAllAccounts() {

    }
}
