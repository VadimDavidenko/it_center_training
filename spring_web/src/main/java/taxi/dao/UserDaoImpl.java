package taxi.dao;

import taxi.domain.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Вадим on 28.02.2016.
 */

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired(required = true)
    private SessionFactory factory;
    
    public UserDaoImpl() {}

    public Long create(User user) {
        Session session = factory.getCurrentSession();
        return (Long)session.save(user);
    }

    public boolean update(User user) {
        Session session = factory.getCurrentSession();
        session.update(user);
        return true;
    }

    public User read(Long id) {
        Session session = factory.getCurrentSession();
        return (User)session.get(User.class, id);
    }

    public boolean delete(User user) {
        Session session = factory.getCurrentSession();
        session.delete(user);
        return true;
    }

    @SuppressWarnings("unchecked")
    public List<User> findAll() {
        Session session = factory.getCurrentSession();
        return (List<User>)session.createQuery("from User").list();
    }

    public User findByLogin(String login) {
        Session session = factory.getCurrentSession();
        Query query = session.createQuery("from User u where u.login = :login");
        query.setParameter("login", login);
        return (User) query.uniqueResult();
    }

    public User findByLoginAndPassword(String login, String pass) {
        Session session = factory.getCurrentSession();
        Query query = session.createQuery("from User u where u.login = :login and u.password = :password");
        query.setParameter("login", login);
        query.setParameter("password", pass);
        return (User) query.uniqueResult();
    }
}
