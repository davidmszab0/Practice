package friendfinder.persistence;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import friendfinder.Application;
import friendfinder.api.domain.Account;
import friendfinder.api.domain.User;
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
public class AccountServiceTests {

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

    // TODO Start the application before you run the tests
    @Test
    public void testRegisterAccount() throws Exception {
        Account account1 = new Account("david@szabo.com", "empty");

        Account acc1 = given().
                contentType(ContentType.JSON).
                accept("application/json").
                body(account1).
                when().
                post("account").
                then().
                statusCode(200).
                body("email", equalTo(account1.getEmail())).
                body("password", equalTo(account1.getPassword())).
                extract().body().as(Account.class);

        assertEquals(1, accountRepository.count());

        User usr = new User("Laszlo", User.Gender.Male);
        Response response2 = given().
                contentType(ContentType.JSON).
                accept("application/json").
                body(usr).
                when().
                put("user/" + acc1.getAccountId()).
                then().
                statusCode(200).
                extract().response();
        String jsonAsString2 = response2.asString();
        System.out.println("jsonAsString: "+jsonAsString2);
    }

    @Test
    public void testLoginAccount() throws Exception {
        Account account1 = new Account("david@szabo.com", "empty");

        Account acc = given().
                body(account1).
                contentType("application/json").
                accept("application/json").
                when().
                post("account").
                then().
                statusCode(200).
                body("email", equalTo(account1.getEmail())).
                body("password", equalTo(account1.getPassword())).
                extract().body().as(Account.class);

        assertEquals(1, accountRepository.count());

        //
        // Get the registered account
        //
        Response response =given().
                accept("application/json").
                contentType(ContentType.JSON).
                when().
                get("account?email=david@szabo.com&password=empty").
                then().
                statusCode(200).
                extract().response();
        String jsonAsString = response.asString();
        System.out.println("jsonAsString: "+jsonAsString);

        //
        // Get notFound for the  unRegistered account
        //
        Response response2 = given().
                accept("application/json").
                contentType(ContentType.JSON).
                when().
                get("account?email=david2@szabo.com&password=empty").
                then().
                statusCode(404).
                extract().response();
        String jsonAsString2 = response2.asString();
        System.out.println("jsonAsString2: "+jsonAsString2);
    }

    @Test
    public void testUpdateAccount() throws Exception {
        Account account1 = new Account("david@szabo.com", "empty");

        Account acc = given().
                body(account1).
                contentType("application/json").
                accept("application/json").
                when().
                post("account").
                then().
                statusCode(200).
                body("email", equalTo(account1.getEmail())).
                body("password", equalTo(account1.getPassword())).
                extract().body().as(Account.class);

        assertEquals(1, accountRepository.count());

        account1.setEmail("david2@szabo.com");
        Response response = given().
                body(account1).
                contentType("application/json").
                accept("application/json").
                when().
                put("account/" + acc.getAccountId()).
                then().
                statusCode(200).
                body("email", equalTo(account1.getEmail())).
                body("password", equalTo(account1.getPassword())).
                extract().response();
        String jsonAsString = response.asString();
        System.out.println("jsonAsString: "+jsonAsString);
    }

    @Test
    public void testGetAll() throws Exception {
        Account account1 = new Account("david@szabo.com", "empty");
        User user1 = new User("David Szabo", User.Gender.Male, account1);
        account1.setUser(user1);

        accountRepository.save(account1);
        userRepository.save(user1);

        given().
                accept("application/json").
                when().
                get("account/all").
                then().
                statusCode(200);
    }
}
