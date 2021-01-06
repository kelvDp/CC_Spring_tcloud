package tacos.starter_jpa.data;

import org.springframework.data.repository.CrudRepository;

import tacos.starter_jpa.Order;

public interface OrderRepo extends CrudRepository<Order, String> {
}
