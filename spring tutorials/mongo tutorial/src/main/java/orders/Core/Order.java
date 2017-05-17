package orders.Core;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * Created by grace on 14/02/17.
 */

@Document(collection = "orders")
public class Order {

    // for the queries to match the JSON structure in the db '.customerId'
    public static final String CUSTOMERNAME = "customer";
    public static final String CUSTOMERTYPE = "type";

    @Id
    private String customerId;

    @Field("client")
    private String customer;

    private String type;
/*
    public Order(String customer, String type) {
        this.customer = customer;
        this.type = type;
    }
*/
    //private Collection<Item> items = new LinkedHashSet<Item>();
    public String getCustomer() {
        return customer;
    }
    public void setCustomer(String customer) {
        this.customer = customer;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    /*public Collection<Item> getItems() {
        return items;
    }
    public void setItems(Collection<Item> items) {
        this.items = items;
    }*/
    public String getId() {
        return customerId;
    }

    @Override
    public String toString() {
        return String.format(
                "Order[id=%s, customer='%s', type='%s']",
                customerId, customer, type);
    }
}
