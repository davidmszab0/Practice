package orders.db;

import orders.Core.Order;

import java.util.List;

/**
 * Created by grace on 14/02/17.
 */
public interface OrderRepositoryCustom {
    List<Order> findOrdersByType(String t);
    List<Order> findByQuery(
            String customerName, String type);
}
