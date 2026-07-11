package org.example.config;

import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                String url = "jdbc:h2:./spacetravel_db;DB_CLOSE_DELAY=-1";
                Flyway flyway = Flyway.configure()
                        .dataSource(url, "sa", "")
                        .locations("classpath:db/migration")
                        .cleanDisabled(false)
                        .load();
                flyway.clean();
                flyway.migrate();

                // 2. Ініціалізація Hibernate
                sessionFactory = new Configuration().configure().buildSessionFactory();
            } catch (Throwable ex) {
                System.err.println("Initial SessionFactory creation failed." + ex);
                ex.printStackTrace();
                throw new ExceptionInInitializerError(ex);
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}