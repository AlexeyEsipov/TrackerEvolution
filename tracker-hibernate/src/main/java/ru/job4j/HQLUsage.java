package ru.job4j;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

public class HQLUsage {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try (SessionFactory sf = new MetadataSources(registry)
                .buildMetadata().buildSessionFactory()) {
            Session session = sf.openSession();
            /* working with session */
            select(session);
            unique(session);
            findById(session, 1);
            update(session, 1, "NEW_!_!_!_!_!_!_!_!_!_NAME");
            select(session);
            findById(session, 1);
            delete(session, 1);
            select(session);
            insert(session);
            select(session);
            session.close();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static void unique(Session session) {
        Query<Item> query = session.createQuery(
                "from Item as i where i.id = 3", Item.class);
        System.out.println(query.uniqueResult());
    }

    public static void select(Session session) {
        Query<Item> query = session.createQuery("from ru.job4j.Item", Item.class);
        for (Object st : query.list()) {
            System.out.println(st);
        }
    }

    public static void findById(Session session, int id) {
        Query<Item> query = session.createQuery(
                "from Item as i where i.id = :fId", Item.class);
        query.setParameter("fId", id);
        System.out.println(query.uniqueResult());
    }

    public static void update(Session session, int id, String newName) {
        try {
            session.beginTransaction();
            session.createQuery(
                            "UPDATE Item SET name = :fName WHERE id = :fId")
                    .setParameter("fName", newName)
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    public static void delete(Session session, int id) {
        try {
            session.beginTransaction();
            session.createQuery(
                            "DELETE Item WHERE id = :fId")
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    public static void insert(Session session) {
        try {
            session.beginTransaction();
            session.createQuery(
                            "INSERT INTO Item (name) VALUES (:fName)")
                    .setParameter("fName", "new name")
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }
}