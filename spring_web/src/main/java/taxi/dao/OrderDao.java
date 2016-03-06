package taxi.dao;

import taxi.domain.Order;

import java.util.List;

/**
 * Created by Вадим on 28.02.2016.
 */
public interface OrderDao {

    Long create(Order order);

    Order read(Long id);

    boolean update(Order order);

    boolean delete(Order order);

    List findAll();

    List findByPortion(int page, int size);

    List findBetweenSumRange(int sumFrom, int sumTo);
}
