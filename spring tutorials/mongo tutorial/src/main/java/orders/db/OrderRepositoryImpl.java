package orders.db;

import orders.Core.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * Created by grace on 14/02/17.
 */
public class OrderRepositoryImpl implements OrderRepositoryCustom {

    @Autowired
    private MongoOperations mongoOperation; // many use MongoTemplate instead

    public List<Order> findOrdersByType(String t) {
        String type = t.equals("NET") ? "WEB" : t;
        Criteria where = Criteria.where("type").is(t);
        Query query = Query.query(where);
        return mongoOperation.find(query, Order.class);
    }

    @Override
    public List<Order> findByQuery(String customerName, String type) {
        Query query = new Query();

        if ((customerName != null) && (customerName.length() !=0)) {
            query.addCriteria(Criteria.where(Order.CUSTOMERNAME).is(customerName));
        }
        if ((type != null) && (type.length() !=0)) {
            query.addCriteria(Criteria.where(Order.CUSTOMERTYPE).is(type));
        }

        List<Order> entities =  mongoOperation.find(query, Order.class);

        return entities;
    }
}
