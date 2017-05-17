package hello;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="customers")
public class Customer {

    @Id
    public String customerId;

    public String firstName;
    public String lastName;

    public String accessAttributeId;

    public Customer() {}

    public Customer(String firstName, String lastName, String accessAttributeId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.accessAttributeId = accessAttributeId;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%s, firstName='%s', lastName='%s', accessAttributeId='%s']",
                customerId, firstName, lastName, accessAttributeId);
    }

}
