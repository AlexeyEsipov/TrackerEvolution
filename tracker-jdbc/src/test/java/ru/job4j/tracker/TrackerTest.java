package ru.job4j.tracker;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class TrackerTest {

    public Connection init() {
        try (InputStream in = SqlTracker.class.getClassLoader()
                .getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            return DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")

            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void whenReplace() {
        /*try (Store tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {*/
        Store tracker = new MemTracker();
        int id = tracker.add(new Item("Bug")).getId();
            Item bugWithDesc = new Item("Bug with description");
            tracker.add(bugWithDesc);
            tracker.replace(id, bugWithDesc);
            assertThat(tracker.findById(id).getName(), is("Bug with description"));
        /*} catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @Test
    public void whenDelete() {
        try (Store tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            int id = tracker.add(new Item("Bug")).getId();
            Item expected = new Item(null);
            tracker.delete(id);
            assertThat(tracker.findById(id), is(expected));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}