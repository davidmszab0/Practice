package orders.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by grace on 14/02/17.
 */
@Configuration
@EnableMongoRepositories(basePackages={"orders.db"})
//@ComponentScan(basePackages={"orders"})
public class MongoConfig extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "OrdersDB";
    }

    @Override
    public Mongo mongo() throws Exception {
        // you can specify the server and port number here ->
        // return new MongoClient("mongodbserver", 37017);
        return new MongoClient("127.0.0.1");
    }
/*
    @Override
    public MongoTemplate mongoTemplate() throws  Exception {

        return new MongoTemplate();
    }
*/
}