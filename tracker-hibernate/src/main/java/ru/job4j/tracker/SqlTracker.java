package ru.job4j.tracker;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SqlTracker implements Store, AutoCloseable {

    private Connection cn;

    public SqlTracker() {
    }

    public SqlTracker(Connection cn) {
        this.cn = cn;
    }

    public void init() {
        try (InputStream in = SqlTracker.class.getClassLoader()
                .getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            cn = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /*public void createTable() {
        try (Statement st = cn.createStatement()) {
            st.executeUpdate(String.format("CREATE TABLE if not exists tracker (%s, %s, %s);",
                    "id serial primary key", "name varchar(255)",
                     "created timestamp without time zone"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    private Item getItemFromResultSet(ResultSet resultSet) throws SQLException {
        return new Item(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getTimestamp("created").toLocalDateTime());
    }

    @Override
    public void close() throws Exception {
        if (cn != null) {
            cn.close();
        }
    }

    @Override
    public Item add(Item item) {
        try (PreparedStatement st = cn.prepareStatement(
                "INSERT INTO items (name, created) VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, item.getName());
            st.setTimestamp(2, Timestamp.valueOf(item.getCreated()));
            st.execute();
            try (ResultSet generatedKeys = st.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    item.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public boolean replace(int id, Item item) {
        boolean result = false;
        try (PreparedStatement st = cn.prepareStatement(
                "UPDATE items SET name = ?, created = ? WHERE id = ?")) {
                st.setString(1, item.getName());
                st.setTimestamp(2, Timestamp.valueOf(item.getCreated()));
                st.setInt(3, id);
                result = st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;
        try (PreparedStatement st = cn.prepareStatement(
                "DELETE FROM items WHERE id = ?")) {
            st.setInt(1, id);
            result = st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Item> findAll() {
        List<Item> list = new ArrayList<>();
        try (PreparedStatement st = cn.prepareStatement(
                "SELECT * FROM items ORDER BY id")) {
            try (ResultSet resultSet = st.executeQuery()) {
                while (resultSet.next()) {
                    list.add(getItemFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Item> findByName(String key) {
        List<Item> list = new ArrayList<>();
        try (PreparedStatement st = cn.prepareStatement(
                "SELECT * FROM items WHERE name = ? ORDER BY id")) {
            st.setString(1, key);
            try (ResultSet resultSet = st.executeQuery()) {
                while (resultSet.next()) {
                    list.add(getItemFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Item findById(int id) {
        Item item = null;
        try (PreparedStatement st = cn.prepareStatement(
                "SELECT * FROM items WHERE id = ?")) {
            st.setInt(1, id);
            try (ResultSet resultSet = st.executeQuery()) {
                if (resultSet.next()) {
                    item = getItemFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

}
