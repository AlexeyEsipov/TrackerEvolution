package ru.job4j.tracker;

import java.util.Arrays;

public class Tracker {
    private final Item[] items = new Item[100];
    private int ids = 1;
    private int size = 0;

    public Item add(Item item) {
        item.setId(ids++);
        items[size++] = item;
        return item;
    }

    public Item findById(int id) {
        int i = indexOf(id);
        return i != -1 ? items[i] : null;
    }

    public Item[] findAll() {
        return Arrays.copyOf(items, size);
    }

    public Item[] findByName(String key) {
        Item[] result = new Item[size];
        int j = 0;
        for (int i = 0; i < size; i++) {
            if (items[i].getName().equals(key)) {
                result[j++] = items[i];
            }
        }
        return Arrays.copyOf(result, j);
    }

    public boolean replace(int id, Item item) {
        int i = indexOf(id);
        boolean result = i != -1;
        if (result) {
            item.setId(id);
            items[i] = item;
        }
        return result;
    }

    private int indexOf(int id) {
        int result = -1;
        for (int i = 0; i < size; i++) {
            if (items[i].getId() == id) {
                result = i;
                break;
            }
        }
        return result;
    }

    public boolean delete(int id) {
        int i = indexOf(id);
        boolean result = i != -1;
        if (result) {
            items[i] = null;
            System.arraycopy(items, i + 1, items, i, size - i);
            items[size - 1] = null;
            size--;
        }
        return result;
    }
}
