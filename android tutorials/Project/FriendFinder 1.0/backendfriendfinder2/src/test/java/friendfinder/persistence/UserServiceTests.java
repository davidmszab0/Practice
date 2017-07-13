package friendfinder.persistence;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import friendfinder.Application;
import friendfinder.api.domain.Account;
import friendfinder.api.domain.MovieGenres;
import friendfinder.api.domain.MusicGenres;
import friendfinder.api.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by grace on 12/07/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= Application.class)
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieGenresRepository movieGenresRepository;

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

        userRepository.deleteAll();
    }
    @Test
    public void testDeleteSQLdb() {

    }

    @Test
    public void testUpdateUser() throws Exception {

        Account postedAcc = given().
                contentType("application/json").
                accept("application/json").
                body(new Account("no-reply@gmail.com", "empty")).
                when().
                post("account").
                then().
                statusCode(200).
                extract().body().as(Account.class);

        HashSet<MovieGenres> movieG = new HashSet<MovieGenres>() {{
                add(new MovieGenres("Comedy"));
                add(new MovieGenres("Action"));
        }};

        HashSet<MusicGenres> musicG = new HashSet<MusicGenres>() {{
            add(new MusicGenres("Jazz"));
            add(new MusicGenres("Blues"));
        }};

        Response response = given().
                body(new User("Peter", User.Gender.Male, postedAcc, movieG, musicG)).
                contentType(ContentType.JSON).
                accept("application/json").
                when().
                put("user/" + Integer.toString(postedAcc.getId())).
                then().
                statusCode(200)
                .extract().response();
        String jsonAsString2 = response.asString();
        System.out.println("jsonAsString: "+jsonAsString2);
    }

    @Test
    public void testLoginAccount() {
        Account postedAcc = given().
                contentType("application/json").
                accept("application/json").
                body(new Account("no-reply@gmail.com", "empty")).
                when().
                post("account").
                then().
                statusCode(200).
                extract().body().as(Account.class);

        HashSet<MovieGenres> movieG = new HashSet<MovieGenres>() {{
            add(new MovieGenres("Comedy"));
            add(new MovieGenres("Action"));
        }};

        HashSet<MusicGenres> musicG = new HashSet<MusicGenres>() {{
            add(new MusicGenres("Jazz"));
            add(new MusicGenres("Blues"));
        }};

        given().
                body(new User("Peter", User.Gender.Male, postedAcc, movieG, musicG)).
                contentType(ContentType.JSON).
                accept("application/json").
                when().
                put("user/" + Integer.toString(postedAcc.getId())).
                then().
                statusCode(200);

        Response response = given().
                contentType(ContentType.JSON).
                accept("application/json").
                when().
                get("account?email=no-reply@gmail.com&password=empty").
                then().
                statusCode(200)
                .extract().response();
        String jsonAsString2 = response.asString();
        System.out.println("jsonAsString: "+jsonAsString2);

        Response response2 = given().
                contentType(ContentType.JSON).
                accept("application/json").
                when().
                get("user/" + Integer.toString(postedAcc.getId())).
                then().
                statusCode(200)
                .extract().response();
        String jsonAsString3 = response2.asString();
        System.out.println("jsonAsString: "+jsonAsString3);
    }

    @Test
    public void testUserSearch() throws Exception {

        Account postedAcc = given().
                contentType("application/json").
                accept("application/json").
                body(new Account("david@szabo.com", "empty")).
                when().
                post("account").
                then().
                statusCode(200).
                extract().body().as(Account.class);

        given().
                body(new User("David", User.Gender.Male, postedAcc)).
                contentType(ContentType.JSON).
                accept("application/json").
                when().
                put("user/" + Integer.toString(postedAcc.getId())).
                then().
                statusCode(200);

        given().
                accept("application/json").
                contentType(ContentType.JSON).
                when().
                get("user/search?name=David&gender=Male").
                then().
                statusCode(200);

        given().
                accept("application/json").
                contentType(ContentType.JSON).
                when().
                get("user/search?name=&gender=Male").
                then().
                statusCode(200);

        Response resp = given().log().all().
                accept("application/json").
                contentType(ContentType.JSON).
                when().
                get("user/search?name=David&gender=").
                then().
                statusCode(200).extract().response();
        String jsonAsString2 = resp.asString();
        System.out.println("jsonAsString: "+jsonAsString2);

        given().
                accept("application/json").
                contentType(ContentType.JSON).
                when().
                get("user/search?name=&gender=").
                then().
                statusCode(200);

        given().
                accept("application/json").
                contentType(ContentType.JSON).
                when().
                get("user/search?name=&gender=male").
                then().
                statusCode(200);
    }
}
