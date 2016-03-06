package taxi.dao;

import taxi.domain.Order;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Вадим on 28.02.2016.
 */

@Repository
public class OrderDaoImpl implements OrderDao {

    @Autowired(required = true)
    private SessionFactory factory;

    public OrderDaoImpl() {}

    public Long create(Order order) {
        Session session = factory.getCurrentSession();
        return (Long)session.save(order);
    }

    public boolean update(Order order) {
        Session session = factory.getCurrentSession();
        session.update(order);
        return true;
    }

    public Order read(Long id) {
        Session session = factory.getCurrentSession();
        return (Order)session.get(Order.class, id);
    }

    public boolean delete(Order order) {
        Session session = factory.getCurrentSession();
        session.delete(order);
        return true;
    }

    @SuppressWarnings("unchecked")
    public List<Order> findAll() {
        Session session = factory.getCurrentSession();
        return (List<Order>)session.createQuery("from Order").list();
    }

    @SuppressWarnings("unchecked")
    public List<Order> findByPortion(int page, int size) {
        Session session = factory.getCurrentSession();
        Query query = session.createQuery("from Order");
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return (List<Order>)query.list();
    }

    @SuppressWarnings("unchecked")
    public List<Order> findBetweenSumRange(int sumFrom, int sumTo) {
        Session session = factory.getCurrentSession();
        String sql = "select * from ORDERS o where o.AMOUNT >= :sumFrom and o.AMOUNT <= :sumTo";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Order.class);
        query.setParameter("sumFrom", sumFrom);
        query.setParameter("sumTo", sumTo);
        return (List<Order>)query.list();
    }
}
