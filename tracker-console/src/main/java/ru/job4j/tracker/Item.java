package ru.job4j.tracker;

public class Item {

    private int id;
    private String name;

    public Item(String name) {
        this.name = name;
    }

    public Item() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Item: ID: " + id + ", Name: " + name;
    }
}
