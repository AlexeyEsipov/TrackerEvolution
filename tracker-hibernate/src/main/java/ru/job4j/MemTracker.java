package ru.job4j;

import java.util.ArrayList;
import java.util.List;

public class MemTracker implements Store {
    private final List<Item> items = new ArrayList<>();
    private int ids = 1;

    @Override
    public void init() {

    }

    public Item add(Item item) {
        item.setId(ids++);
        items.add(item);
        return item;
    }

    public Item findById(int id) {
        int i = indexOf(id);
        return i != -1 ? items.get(i) : null;
    }

    public List<Item> findAll() {
        return new ArrayList<>(items);
    }

    public List<Item> findByName(String key) {
        List<Item> result = new ArrayList<>();
        for (Item elem: items) {
            if (elem.getName().equals(key)) {
                result.add(elem);
            }
        }
        return result;
    }

    public boolean replace(int id, Item item) {
        int i = indexOf(id);
        boolean result = i != -1;
        if (result) {
            item.setId(id);
            items.set(i, item);
        }
        return result;
    }

    public boolean delete(int id) {
        int i = indexOf(id);
        boolean result = i != -1;
        if (result) {
            items.remove(i);
        }
        return result;
    }

    private int indexOf(int id) {
        int result = -1;
        int i = 0;
        for (Item elem: items) {
            if (elem.getId() == id) {
                result = i;
                break;
            }
            i++;
        }
        return result;
    }

    @Override
    public void close()  {
    }
}