package ru.job4j.tracker;

import java.util.List;

public class DeleteAllItems implements UserAction {
    private final Output out;

    public DeleteAllItems(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Delete all items";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        List<Item> items = tracker.findAll();
        for (Item item : items) {
            tracker.delete(item.getId());
            out.println(String.format("Заявка id%s удалена успешно.", item.getId()));
        }
        return true;
    }
}
