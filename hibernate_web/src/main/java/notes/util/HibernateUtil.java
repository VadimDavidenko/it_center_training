package notes.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.Locale;

/**
 * Created by Вадим on 07.02.2016.
 */
public class HibernateUtil {

    public static SessionFactory getSessionFactory() {
        Locale.setDefault(Locale.ENGLISH);
        Configuration cfg = new Configuration().configure("notes/hibernate.cfg.xml");
        StandardServiceRegistryBuilder sb = new StandardServiceRegistryBuilder();
        sb.applySettings(cfg.getProperties());
        StandardServiceRegistry standardServiceRegistry = sb.build();

        return cfg.buildSessionFactory(standardServiceRegistry);
    }

}
