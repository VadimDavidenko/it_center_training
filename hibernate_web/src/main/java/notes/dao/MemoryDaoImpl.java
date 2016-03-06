package notes.dao;

import notes.domain.Memory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Created by Вадим on 14.02.2016.
 */
public class MemoryDaoImpl implements MemoryDao {

    private SessionFactory factory;

    public MemoryDaoImpl() {}

    public MemoryDaoImpl(SessionFactory factory) {
        this.factory = factory;
    }

    public Long create(Memory memory) {
        Session session = factory.openSession();
        Long id = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            id = (Long)session.save(memory);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return id;
    }

    public Memory read(Long id) {
        Session session = factory.openSession();
        Memory memory = null;
        try {
            memory = (Memory)session.get(Memory.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return memory;
    }

    public boolean update(Memory memory) {
        Session session = factory.openSession();
        boolean isUpdated = false;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(memory);
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

    public boolean delete(Memory memory) {
        Session session = factory.openSession();
        boolean isDeleted = false;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(memory);
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

    public List<Memory> findAll() {
        Session session = factory.openSession();
        try {
            return (List<Memory>)session.createQuery("from Memory").list();
        } finally {
            session.close();
        }
    }
}
