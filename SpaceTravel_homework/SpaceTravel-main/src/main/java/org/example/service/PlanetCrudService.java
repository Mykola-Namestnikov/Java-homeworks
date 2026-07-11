package org.example.service;

import org.example.config.HibernateUtil;
import org.example.entity.Planet;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PlanetCrudService {
    public void save(Planet planet) {
        if (planet.getId() == null || !planet.getId().matches("^[A-Z0-9]+$")) {
            throw new IllegalArgumentException("Invalid planet ID. Must be upper-case alphanumeric.");
        }
        if (planet.getName() == null || planet.getName().length() < 1 || planet.getName().length() > 500) {
            throw new IllegalArgumentException("Invalid planet name length.");
        }
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(planet);
            tx.commit();
        }
    }

    public Planet findById(String id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Planet.class, id);
        }
    }

    public void update(Planet planet) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(planet);
            tx.commit();
        }
    }

    public void delete(Planet planet) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(session.contains(planet) ? planet : session.merge(planet));
            tx.commit();
        }
    }
}
