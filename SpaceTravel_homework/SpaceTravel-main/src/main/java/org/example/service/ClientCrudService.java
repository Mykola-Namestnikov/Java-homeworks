package org.example.service;

import org.example.config.HibernateUtil;
import org.example.entity.Client;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ClientCrudService {
    public void save(Client client) {
        if (client.getName() == null || client.getName().length() < 3 || client.getName().length() > 200) {
            throw new IllegalArgumentException("Invalid client name");
        }
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(client);
            tx.commit();
        }
    }

    public Client findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Client.class, id);
        }
    }

    public void update(Client client) {
        if (client.getName() == null || client.getName().length() < 3 || client.getName().length() > 200) {
            throw new IllegalArgumentException("Invalid client name");
        }
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(client);
            tx.commit();
        }
    }

    public void delete(Client client) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(session.contains(client) ? client : session.merge(client));
            tx.commit();
        }
    }
}
