package ru.job4j.tracker;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

public class SqlTrackerTest {

    private static Connection connection;

    @BeforeClass
    public static void initConnection() {
        try (InputStream in = SqlTrackerTest.class.getClassLoader().
                getResourceAsStream("test.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            connection = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")

            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @AfterClass
    public static void closeConnection() throws SQLException {
        connection.close();
    }

    @After
    public void wipeTable() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("delete from items")) {
            statement.execute();
        }
    }

    @Test
    public void whenSaveItemAndFindByGeneratedIdThenMustBeTheSame() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("item");
        tracker.add(item);
        assertEquals(tracker.findById(item.getId()).toString(), item.toString());
    }

    @Test
    public void whenSaveItemThenDeleteItem() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("item");
        tracker.add(item);
        tracker.delete(item.getId());
        Item zero = new Item(0);
        assertEquals(tracker.findById(item.getId()), zero);
    }

    @Test
    public void whenSaveItemThenDeleteWrongId() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("item");
        tracker.add(item);
        tracker.delete(-1);
        assertEquals(tracker.findById(item.getId()), item);
    }

    @Test
    public void whenSaveItemThenReplace() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("item");
        tracker.add(item);
        tracker.replace(item.getId(), new Item("replaced"));
        assertEquals(tracker.findById(item.getId()).getName(), "replaced");
    }

    @Test
    public void whenSaveItemThenReplaceWrongId() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("item");
        tracker.add(item);
        tracker.replace(-1, new Item("replaced"));
        assertEquals(tracker.findById(item.getId()).getName(), "item");
    }

    @Test
    public void whenSaveItemThenFindAll() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("item");
        tracker.add(item);
        assertEquals(tracker.findAll(), List.of(item));
    }

    @Test
    public void whenEmptyTableFindAll() {
        SqlTracker tracker = new SqlTracker(connection);
        assertEquals(tracker.findAll(), List.of());
    }

    @Test
    public void whenSaveItemThenFindByName() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("item");
        tracker.add(item);
        assertEquals(tracker.findByName("item"), List.of(item));
    }

    @Test
    public void whenSaveItemThenFindByWrongName() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("item");
        tracker.add(item);
        assertEquals(tracker.findByName("wrong_name"), List.of());
    }
}