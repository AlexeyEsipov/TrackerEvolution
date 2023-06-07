package ru.job4j;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.toone.Role;
import ru.job4j.toone.User;
import ru.job4j.toone.UserMessenger;

import java.util.List;

public class PatricipatesRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            var role = new Role();
            role.setName("ADMIN_Part");
            create(role, sf);
            var user = new User();
            user.setName("Admin Admin_Part");
            user.setMessengers(List.of(
                    new UserMessenger(0, "tg_P", "@tg_P"),
                    new UserMessenger(0, "wu_P", "@wu_P")
            ));
            user.setRole(role);
            create(user, sf);
            var item = new Item();
            item.setName("Learn Hibernate_P");
            item.setParticipates(List.of(user));
            create(item, sf);
            sf.openSession()
                    .createQuery("from Item where id = :fId", Item.class)
                    .setParameter("fId", item.getId())
                    .getSingleResult()
                    .getParticipates()
                    .forEach(System.out::println);
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static <T> T create(T model, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.persist(model);
        session.getTransaction().commit();
        session.close();
        return model;
    }

    public static void update(Item item, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.update(item);
        session.getTransaction().commit();
        session.close();
    }

    public static void delete(Integer id, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item item = new Item();
        item.setId(id);
        session.delete(item);
        session.getTransaction().commit();
        session.close();
    }

    public static List<Item> findAll(SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from ru.job4j.Item").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public static Item findById(Integer id, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item result = session.get(Item.class, id);
        session.getTransaction().commit();
        session.close();
        return result;
    }
}