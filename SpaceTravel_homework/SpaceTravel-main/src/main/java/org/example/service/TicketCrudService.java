package org.example.service;

import org.example.config.HibernateUtil;
import org.example.entity.Ticket;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class TicketCrudService {
    public void save(Ticket ticket) {
        validateTicket(ticket);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(ticket);
            tx.commit();
        }
    }

    public Ticket findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Ticket.class, id);
        }
    }

    public void update(Ticket ticket) {
        validateTicket(ticket);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(ticket);
            tx.commit();
        }
    }

    public void delete(Ticket ticket) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(session.contains(ticket) ? ticket : session.merge(ticket));
            tx.commit();
        }
    }

    private void validateTicket(Ticket ticket) {
        if (ticket.getClient() == null || ticket.getClient().getId() == null) {
            throw new IllegalArgumentException("Ticket must have a valid, existing client");
        }
        if (ticket.getFromPlanet() == null || ticket.getFromPlanet().getId() == null) {
            throw new IllegalArgumentException("Ticket must have a valid departure planet.");
        }
        if (ticket.getToPlanet() == null || ticket.getToPlanet().getId() == null) {
            throw new IllegalArgumentException("Ticket must have a valid destination planet.");
        }

        try (org.hibernate.Session session = org.example.config.HibernateUtil.getSessionFactory().openSession()) {
            if (session.get(org.example.entity.Client.class, ticket.getClient().getId()) == null) {
                throw new IllegalArgumentException("Client does not exist in the database.");
            }
            if (session.get(org.example.entity.Planet.class, ticket.getFromPlanet().getId()) == null || session.get(org.example.entity.Planet.class, ticket.getToPlanet().getId()) == null) {
                throw new IllegalArgumentException("One or both planets do not exist in the database.");
            }
        }
    }
}
