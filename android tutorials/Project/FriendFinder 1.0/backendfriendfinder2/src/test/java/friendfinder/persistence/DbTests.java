package friendfinder.persistence;

import friendfinder.Application;
import friendfinder.api.domain.Account;
import friendfinder.api.domain.MovieGenres;
import friendfinder.api.domain.MusicGenres;
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
    public void testCreateMoviesAndMusic(){
        Assert.assertEquals(accountRepository.count(),0);
        Assert.assertEquals(userRepository.count(),0);
        Assert.assertEquals(movieGenresRepository.count(),0);

        MovieGenres comedy = new MovieGenres("Comedy");
        MovieGenres action = new MovieGenres("Action");
        MovieGenres bromance = new MovieGenres("Bromance");

        MusicGenres jazz = new MusicGenres("Jazz");
        MusicGenres blues = new MusicGenres("Blues");
        HashSet<MusicGenres> musicGenresHashSet = new HashSet<>();
        musicGenresHashSet.add(jazz);
        musicGenresHashSet.add(blues);

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
        user2.setMusicGenres(musicGenresHashSet);
        //user2.addMovieGenres(comedy); // this works for not the inverse table too, who is not the owner
        //user2.addMovieGenres(action);

        User user3 = new User();
        user3.setName("User 2");
        user3.setGender(User.Gender.Male);
        user3.setMovieGenres(movieGenresHash2);
        user3.setMusicGenres(musicGenresHashSet);
        //user3.addMovieGenres(comedy); // to don't get detached tables
        //user3.addMovieGenres(bromance);

        Account acc2 = new Account("email", "pwd");
        acc2.setUser(user2);
        user2.setAccount(acc2);

        Account acc3 = new Account("email2", "pwd");
        acc3.setUser(user3);
        user3.setAccount(acc3);
        //
        // save a couple of Users,
        //
        HashSet<User> userHash = new HashSet<>();
        userHash.add(user2);
        userHash.add(user3);
        userRepository.save(userHash);

        //userRepository.save(user2);
        //userRepository.save(user3);
        Assert.assertEquals(userRepository.count(),2);

        //log.info("print hashSet2 " + userHash.toString());
        //Hibernate.initialize(user.getMovieGenres());

        /*userRepository.save(new HashSet<User>() {{
            add(new User("User A", new HashSet<MovieGenres>() {{
                add(comedy);
                add(action);
            }}));

            add(new User("User B", new HashSet<MovieGenres>() {{
                add(comedy);
                add(bromance);
            }}));
        }});

        // fetch all users
        for(User usr : userRepository.findAll()) {
//            log.debug(usr.toString());
        }
        //
        // save a couple of movieGenres
        //
       /* User userA = new User();
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
            log.debug(mvG.toString());
        }*/
    }
}
