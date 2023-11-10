package ru.job4j.tracker;

import java.util.List;

public class ShowAllItems implements UserAction {
    private final Output out;

    public ShowAllItems(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "=== Show all items ===";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        List<Item> allItem = tracker.findAll();
        for (Item el: allItem) {
            out.println("Item: ID: " + el.getId() + ", Name: " + el.getName());
        }
        return true;
    }
}
