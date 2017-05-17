package hello;

/**
 * Created by grace on 14/02/17.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
//@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class})
public class Application implements CommandLineRunner {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private AccessRepository accessRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // don't forget to start the mongodb in the terminal by typing: 'mongod'

        customerRepository.deleteAll();
        organizationRepository.deleteAll();
        accessRepository.deleteAll();

        organizationRepository.save(new Organization("1", "Volvo"));
        organizationRepository.save(new Organization("2", "ABB"));
        organizationRepository.save(new Organization("3", "AstraZeneca"));

        accessRepository.save(new Access("11", "1", "Customer"));
        accessRepository.save(new Access("12", "2", "Customer"));

        // save a couple of customers
        customerRepository.save(new Customer("Alice", "Smith", "11"));
        customerRepository.save(new Customer("Bob", "Smith", "11"));

        // fetch all cusomers
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (Customer customer : customerRepository.findAll()) {
            System.out.println(customer);
        }
        System.out.println();

        // fetch an individual customer
        System.out.println("Customer found with findByFirstName('Alice'):");
        System.out.println("--------------------------------");
        System.out.println(customerRepository.findByFirstName("Alice"));

        System.out.println("Customers found with findByLastName('Smith'):");
        System.out.println("--------------------------------");
        for (Customer customer : customerRepository.findByLastName("Smith")) {
            System.out.println(customer);
        }

        verifNotUsedInAccess("1");
    }


    private void verifNotUsedInAccess(String organizationId) {
        List<Access> accessIds = accessRepository.findByOrganizationIdIn(Arrays.asList(organizationId));
        System.out.println("accessIds" + accessIds);

        List<String> accessStrings = accessIds.stream()
                .map(object -> object.getAccessId())
                        .collect(Collectors.toList());
        System.out.println("accessStrings are: " + accessStrings);
       /*
        List<String> strings = new ArrayList<>(accessIds.size());
        for (AccessEntity object : accessIds) {
            strings.add(Objects.toString(object, null));
        }
        */

        // List<UserAccessEntity> findByAccessIdsIn(List <String> accessIds);
        List<Customer> customers = customerRepository.findByAccessIdIn(accessStrings);
        System.out.println("customers are: " + customers);
        for (Customer cust : customers) {
            System.out.println("custs are: " + cust);
        }
        if ((customers != null) && customers.size() > 0)
            System.out.println("OrganizationId used by access");
    }
}


