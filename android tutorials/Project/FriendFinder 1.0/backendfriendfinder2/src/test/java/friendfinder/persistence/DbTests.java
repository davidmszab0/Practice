package friendfinder.persistence;

import friendfinder.Application;
import friendfinder.api.domain.Account;
import friendfinder.api.domain.MovieGenres;
import friendfinder.api.domain.User;
import friendfinder.services.controller.AccountController;
import org.hibernate.Hibernate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;

/**
 * Created by grace on 25/06/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class DbTests {

    private static final Logger log = LoggerFactory.getLogger(DbTests.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieGenresRepository movieGenresRepository;


    @Before
    public void setup() {
        accountRepository.deleteAll();
        userRepository.deleteAll();
        movieGenresRepository.deleteAll();
    }

    @Test
    public void testCreateAccount(){
        Assert.assertEquals(accountRepository.count(),0);
        Assert.assertEquals(userRepository.count(),0);

        Account account1 = new Account("david@szabo.com", "empty");
        Assert.assertNotNull(account1);
        User user1 = new User("David Szabo", User.Gender.Male, account1);
        account1.setUser(user1);
        Assert.assertNotNull(user1);

        accountRepository.save(account1);
        userRepository.save(user1);

        Assert.assertEquals(accountRepository.count(),1);
        Assert.assertEquals(userRepository.count(),1);
    }

    @Test
    public void testFindQueries() {
        Assert.assertEquals(accountRepository.count(),0);
        Assert.assertEquals(userRepository.count(),0);

        Account account1 = new Account("david@szabo.com", "empty");
        Assert.assertNotNull(account1);
        User user1 = new User("David Szabo", User.Gender.Male, account1);
        account1.setUser(user1);
        Assert.assertNotNull(user1);

        Account savedAccount = accountRepository.save(account1);
        User savedUser = userRepository.save(user1);

        Assert.assertEquals(accountRepository.count(),1);
        Assert.assertEquals(userRepository.count(),1);

        User newUser = userRepository.findUserById(savedUser.getId());
        Account newAccount = accountRepository.findByEmail(savedAccount.getEmail());
        Assert.assertNotNull(newUser);
        Assert.assertEquals("David Szabo", newUser.getName());
        Assert.assertEquals("david@szabo.com", newAccount.getEmail());
    }

    @Test
    public void testCreateMovies(){
        Assert.assertEquals(accountRepository.count(),0);
        Assert.assertEquals(userRepository.count(),0);
        Assert.assertEquals(movieGenresRepository.count(),0);

        MovieGenres comedy = new MovieGenres("Comedy");
        MovieGenres action = new MovieGenres("Action");
        MovieGenres bromance = new MovieGenres("Bromance");

        HashSet<MovieGenres> movieGenresHash = new HashSet<>();
        movieGenresHash.add(comedy);
        movieGenresHash.add(action);
        HashSet<MovieGenres> movieGenresHash2 = new HashSet<>();
        movieGenresHash2.add(comedy);
        movieGenresHash2.add(bromance);

        User user2 = new User();
        user2.setName("User 1");
        user2.setGender(User.Gender.Male);
        user2.setMovieGenres(movieGenresHash);

        User user3 = new User();
        user3.setName("User 2");
        user3.setGender(User.Gender.Male);
        user3.setMovieGenres(movieGenresHash2);

        //
        // save a couple of Users
        //
        HashSet<User> userHash = new HashSet<>();
        userHash.add(user2);
        userHash.add(user3);
        userRepository.save(userHash);
        Assert.assertEquals(userRepository.count(),2);

        log.info("print hashSet2 " + userHash.toString());
        //Hibernate.initialize(user2.getMovieGenres());

        // fetch all users
        for(User usr : userRepository.findAll()) {
            log.info(usr.toString());
        }
        //
        // save a couple of movieGenres
        //
        User userA = new User();
        userA.setName("User A");
        User userB = new User();
        userB.setName("User B");

        movieGenresRepository.save(new HashSet<MovieGenres>() {{
            add(new MovieGenres("MovieGenres A", new HashSet<User>() {{
                add(userA);
                add(userB);
            }}));

            add(new MovieGenres("MovieGenres B", new HashSet<User>() {{
                add(userA);
                add(userB);
            }}));
        }});
        Assert.assertEquals(movieGenresRepository.count(),2);

        // fetch all movieGenres
        for(MovieGenres mvG : movieGenresRepository.findAll()) {
            log.info(mvG.toString());
        }
    }
}
