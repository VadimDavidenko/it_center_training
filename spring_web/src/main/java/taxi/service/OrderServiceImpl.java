package taxi.service;

import taxi.dao.OrderDao;
import taxi.domain.Client;
import taxi.domain.Order;
import taxi.exception.OrderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Вадим on 28.02.2016.
 */

@Service
@Scope("singleton")
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    private static int pageCounter = -1;

    public OrderServiceImpl() {
        Locale.setDefault(Locale.ENGLISH);
    }

    public boolean createOrder(Client client, Double amount, String addressFrom, String addressTo)
            throws OrderException {
        Order newOrder = new Order(client, new Date(), amount, addressFrom, addressTo);
        if (orderDao.create(newOrder) == null) {
            throw new OrderException("Order was not added!");
        }
        return true;
    }

    public void editOrder(Long id, Client client, Date orderDate, String amount, String addressFrom, String addressTo) {
        Order order = new Order(client, orderDate, Double.parseDouble(amount), addressFrom, addressTo);
        order.setId(id);
        orderDao.update(order);
    }

    @Transactional (readOnly = true)
    @SuppressWarnings("unchecked")
    public List<Order> showOrdersBetweenSumRange(int sumFrom, int sumTo) {
        return orderDao.findBetweenSumRange(sumFrom, sumTo);
    }

    @Transactional (readOnly = true)
    @SuppressWarnings("unchecked")
    public List<Order> showOrdersByPortion(int portionSize) {
        if (portionSize == 0) {
            pageCounter = -1;
            return null;
        } if (portionSize < 0) {
            if (pageCounter > 0) pageCounter--;
        } else {
            pageCounter++;
        }
        return orderDao.findByPortion(pageCounter, Math.abs(portionSize));
    }

    @Transactional (readOnly = true)
    public Order findOrderById(Long id) {
        return orderDao.read(id);
    }
}
