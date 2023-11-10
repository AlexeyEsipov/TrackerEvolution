package ru.job4j.tracker;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class HQLUsage {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try (SessionFactory sf = new MetadataSources(registry)
                .buildMetadata().buildSessionFactory()) {

            /* SELECT */
            /* get all items */
            List<Item> list = findAll(sf);
            for (Object st : list) {
                System.out.println(st);
            }
            /* get item by id = 3 */
            Session session = sf.openSession();
            Query<Item> query2 = session.createQuery(
                    "from Item as i where i.id = 3", Item.class);
            System.out.println(query2.uniqueResult());
            session.close();

            /* get item by id using predefined variable id = 2 */
            Item item = findById(2, sf);
            System.out.println(item);

            /* UPDATE - transaction needed! */
            /* update item */
            Item newItem = new Item(item.getId(), "newName", item.getCreated());
            update(newItem, sf);
            System.out.println(findById(2, sf));

            /* DELETE - transaction needed! */
            /* delete item */
            delete(2, sf);
            for (Object st : findAll(sf)) {
                System.out.println(st);
            }

            /* INSERT - only insert from other table
            * To add new item to the table - use save() method (not INSERT)*/
            Item item2 = new Item("name");
            insert(item2, sf);
            for (Object st : findAll(sf)) {
                System.out.println(st);
            }

        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    /* get all items */
    public static List<Item> findAll(SessionFactory sf) {
        Session session = sf.openSession();
        Query<Item> query = session.createQuery("from Item", Item.class);
        List<Item> list = query.list();
        session.close();
        return list;
    }

    /* get item by id using predefined variable */
    public static Item findById(Integer id, SessionFactory sf) {
        Session session = sf.openSession();
        Query<Item> query = session.createQuery(
                "from Item as i where i.id = :fId", Item.class);
        query.setParameter("fId", id);
        Item item = query.uniqueResult();
        session.close();
        return item;
    }

    /* update item */
    public static void update(Item item, SessionFactory sf) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "UPDATE Item SET name = :fName, created = :fDate WHERE id = :fId")
                    .setParameter("fName", item.getName())
                    .setParameter("fId", item.getId())
                    .setParameter("fDate", LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        session.close();
    }

    /* delete item */
    public static void delete(int id, SessionFactory sf) {
        Session session = sf.openSession();
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
        session.close();
    }

    /* to add item - use save() method */
    public static void insert(Item item, SessionFactory sf) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        session.close();
    }
}
