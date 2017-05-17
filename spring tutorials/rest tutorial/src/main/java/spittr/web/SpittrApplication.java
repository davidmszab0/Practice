package spittr.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import spittr.config.WebConfig;

/**
 * Created by grace on 15/02/17.
 */
@SpringBootApplication(scanBasePackages = "config")
public class SpittrApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpittrApplication.class, args);

        // run tomcat server: /usr/local/Cellar/tomcat/8.5.11/bin/catalina run
    }
}
