package friendfinder.persistence;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import friendfinder.Application;
import friendfinder.domain.Account;
import friendfinder.domain.User;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.Matchers.equalTo;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

/**
 * Created by grace on 25/06/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)
public class ControllerTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setup() {
        String baseHost = System.getProperty("server.host");
        if(baseHost==null){
            baseHost = "http://localhost";
        }
        RestAssured.baseURI = baseHost;

        String port = System.getProperty("server.port");
        if (port == null) {
            RestAssured.port = Integer.valueOf(8080);
        }
        else{
            RestAssured.port = Integer.valueOf(port);
        }

        String basePath = System.getProperty("server.base");
        if(basePath==null){
            basePath = "/";
        }
        RestAssured.basePath = basePath;


        accountRepository.deleteAll();
        userRepository.deleteAll();
    }

    // Start the application before you run the tests
    @Test
    public void testCreateAccount() throws Exception {
        Account account1 = new Account("david@szabo.com", "empty");

        given().
                body(account1).
                contentType("application/json").
                accept("application/json").
                when().
                post("account").
                then().
                statusCode(200);
        assertEquals(1, accountRepository.count());
    }

    @Test
    public void testGetAll() throws Exception {
        Account account1 = new Account("david@szabo.com", "empty");
        User user1 = new User("David Szabo", "male", account1);
        account1.setUser(user1);

        Account savedAccount = accountRepository.save(account1);
        User savedUser = userRepository.save(user1);

        given().
                accept("application/json").
                when().
                get("account/all").
                then().
                statusCode(200);
    }

    @Test
    public void testSearch() throws Exception {

        given().
                contentType("application/json").
                accept("application/json").
                body(new Account("david@szabo.com", "empty")).
                when().
                post("account").
                then().
                statusCode(200);

        Account postedAcc = accountRepository.findByEmail("david@szabo.com");

        given().
                body(new User("David", "male")).
                contentType(ContentType.JSON).
                accept("application/json").
                when().
                put("user/" + Integer.toString(postedAcc.getAccountId())).
                then().
                statusCode(200);

        given().log().all().
                accept("application/json").
                contentType(ContentType.JSON).
                when().
                get("user/search?name=David&gender=male").
                then().
                statusCode(200);

        given().
                accept("application/json").
                contentType(ContentType.JSON).
                when().
                get("user/search?name=&gender=male").
                then().
                statusCode(200);

        given().
                accept("application/json").
                contentType(ContentType.JSON).
                when().
                get("user/search?name=David&gender=").
                then().
                statusCode(200);

        given().
                accept("application/json").
                contentType(ContentType.JSON).
                when().
                get("user/search?name=&gender=").
                then().
                statusCode(200);
    }

}
