package taxi.dao;

import taxi.domain.Client;
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
public class ClientDaoImpl implements ClientDao {

    @Autowired(required = true)
    private SessionFactory factory;

    public ClientDaoImpl() {}

    public Long create(Client client) {
        Session session = factory.getCurrentSession();
        return (Long)session.save(client);
    }

    public boolean update(Client client) {
        Session session = factory.getCurrentSession();
        session.update(client);
        return true;
    }

    public Client read(Long id) {
        Session session = factory.getCurrentSession();
        return (Client)session.get(Client.class, id);
    }

    public boolean delete(Client client) {
        Session session = factory.getCurrentSession();
        session.delete(client);
        return true;
    }

    @SuppressWarnings("unchecked")
    public List<Client> findAll() {
        Session session = factory.getCurrentSession();
        return (List<Client>)session.createQuery("from Client").list();
    }

    @SuppressWarnings("unchecked")
    public List<Client> findByPortion(int page, int size) {
        Session session = factory.getCurrentSession();
        Query query = session.createQuery("from Client");
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return (List<Client>)query.list();
    }

    @SuppressWarnings("unchecked")
    public List<Client> findGtSum(int sum) {
        Session session = factory.getCurrentSession();
        //String sql = "select * from CLIENTS c where :gtSum < (select sum(o.AMOUNT) from ORDERS o where o.CLIENT_ID = c.CLIENT_ID)";
        String sql = "select * from CLIENTS c where c.AMOUNT > :gtSum";
        SQLQuery query = session.createSQLQuery(sql);
        query.setParameter("gtSum", sum);
        query.addEntity(Client.class);
        return (List<Client>)query.list();
    }

    @SuppressWarnings("unchecked")
    public List<Client> findLastMonth() {
        Session session = factory.getCurrentSession();
        String sql = "select * from CLIENTS c where c.LAST_ORDER_DATE between trunc(sysdate, 'mm') and SYSDATE";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Client.class);
        return (List<Client>)query.list();
    }
}
