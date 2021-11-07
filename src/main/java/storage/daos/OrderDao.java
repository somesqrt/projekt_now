package storage.daos;

import storage.constructors.Order;
import storage.constructors.Product;

import java.util.List;

public interface OrderDao {
    List<Order> getAll();
    Order createOrder(Order order);
    List<Product> getProductInOrder(Order order);
    Order update(Order order);
    Order getIdFromOrder(Order order);


}
