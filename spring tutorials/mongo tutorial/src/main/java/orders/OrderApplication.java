package orders;

import orders.Core.Order;
import orders.db.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by grace on 14/02/17.
 */
//@SpringBootApplication // same as @ComponentScan, @Configuration, @EnableMongoRepositories
@ComponentScan(basePackages={"orders.config"}) // otherwise you reference the config with XML in ->
// ApplicationContext context = new ClassPathXmlApplicationContext()
public class OrderApplication implements CommandLineRunner {

    // can't find the bean!!!
    @Autowired
    private OrderRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {


        // don't forget to start the mongodb in the terminal by typing: 'mongod'

        repository.deleteAll();

        // save a couple of orders
        Order order = new Order();
        order.setCustomer("Smith");
        order.setType("post");
        repository.save(order);

        Order order1 = new Order();
        order1.setCustomer("Henriksson");
        order1.setType("email");
        repository.save(order1);

        //repository.save(new Order("Smith", "post"));
        //repository.save(new Order("Bob", "carrier"));

        // fetch all orders
        System.out.println();
        System.out.println("Orders found with findAll():");
        System.out.println("-------------------------------");
        for (Order orderIterator : repository.findAll()) {
            System.out.println(orderIterator);
        }
        System.out.println();

        // fetch an individual customer
        System.out.println("Customer found with findByCustomer('Smith'):");
        System.out.println("--------------------------------");
        System.out.println(repository.findByCustomer("Smith"));
        System.out.println();

        System.out.println("Customers found with findByCustomerAndType('Smith', 'post'):");
        System.out.println("--------------------------------");
        for (Order orderIterator : repository.findByCustomerAndType("Smith", "post")) {
            System.out.println(orderIterator);
        }
        System.out.println();

        System.out.println("Customers found with findByQuery('customerName', 'type'):");
        System.out.println("--------------------------------");
        for (Order orderIterator : repository.findByQuery("", "")) {
            System.out.println(orderIterator);
        }
        System.out.println();
    }
}
