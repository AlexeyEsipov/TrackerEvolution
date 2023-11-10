package ru.job4j.tracker;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

public class HbmTracker implements Store, AutoCloseable {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    @Override
    public Item add(Item item) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.persist(item);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return item;
    }

    @Override
    public boolean replace(int id, Item item) {
        boolean isReplaced = false;
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.update(item);
            session.getTransaction().commit();
            isReplaced = true;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return isReplaced;
    }

    @Override
    public boolean delete(int id) {
        int updates = 0;
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            updates = session.createQuery("DELETE Item WHERE id = :fId")
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return updates > 0;
    }

    @Override
    public List<Item> findAll() {
        Session session = sf.openSession();
        Query<Item> query = session.createQuery("FROM Item", Item.class);
        List<Item> list = query.list();
        session.close();
        return list;
    }

    @Override
    public List<Item> findByName(String key) {
        Session session = sf.openSession();
        Query<Item> query = session.createQuery(
                "FROM Item WHERE LOWER(name) = LOWER(:fName)", Item.class);
        query.setParameter("fName", key);
        List<Item> list = query.list();
        session.close();
        return list;
    }

    @Override
    public Item findById(int id) {
        Session session = sf.openSession();
        Query<Item> query = session.createQuery(
                "FROM Item WHERE id = :fId", Item.class);
        query.setParameter("fId", id);
        Item item = query.uniqueResult();
        session.close();
        return item;
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
