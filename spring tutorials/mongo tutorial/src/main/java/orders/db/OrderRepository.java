package orders.db;

import orders.Core.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by grace on 14/02/17.
 */
@Repository // not needed, but it is clearer
public interface OrderRepository
        extends MongoRepository<Order, String>, OrderRepositoryCustom {

    List<Order> findByCustomer(String customer);
    List<Order> findByCustomerLike(String c);
    List<Order> findByCustomerAndType(String customer, String type);
    List<Order> findByCustomerLikeAndType(String c, String t);
}
