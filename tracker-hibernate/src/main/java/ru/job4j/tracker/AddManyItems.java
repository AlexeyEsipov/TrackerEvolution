package ru.job4j.tracker;

public class AddManyItems implements UserAction {
    private final Output out;

    public AddManyItems(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Add many items";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        Item item = null;
        for (int i = 1; i <= 10000000; i++) {
            item = new Item("name" + i);
            tracker.add(item);
            out.println("Добавленная заявка: " + item);
        }
        return true;
    }
}
