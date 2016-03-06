package notes.dao;

import notes.domain.Store;
import org.hibernate.*;

import java.util.List;

/**
 * Created by Вадим on 14.02.2016.
 */
public class StoreDaoImpl implements StoreDao {

    private SessionFactory factory;

    public StoreDaoImpl() {}

    public StoreDaoImpl(SessionFactory factory) {
        this.factory = factory;
    }

    public Long create(Store store) {
        Session session = factory.openSession();
        Long id = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            id = (Long)session.save(store);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return id;
    }

    public Store read(Long id) {
        Session session = factory.openSession();
        Store store = null;
        try {
            store = (Store)session.get(Store.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return store;
    }

    public boolean update(Store store) {
        Session session = factory.openSession();
        boolean isUpdated = false;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(store);
            tx.commit();
            isUpdated = true;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return isUpdated;
    }

    public boolean delete(Store store) {
        Session session = factory.openSession();
        boolean isDeleted = false;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(store);
            tx.commit();
            isDeleted = true;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return isDeleted;
    }

    public List<Store> findAll() {
        Session session = factory.openSession();
        try {
            return (List<Store>)session.createQuery("from Store").list();
        } finally {
            session.close();
        }
    }

    public List<Store> findOnStorePresent() {
        Session session = factory.openSession();
        String sql = "select * from NOTEBOOK n, STORE s where n.NOTEBOOK_ID = s.NOTEBOOK_ID and s.AMOUNT > 0";
        try {
            SQLQuery query = session.createSQLQuery(sql);
            query.addEntity(Store.class);
            return (List<Store>)query.list();
        } finally {
            session.close();
        }
    }

}
