package ru.job4j.tracker;

public class FindManyById implements UserAction {
    private final Output out;

    public FindManyById(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Find all items by id";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        Item item = null;
        for (int i = 1; i <= 1000; i++) {
            item = tracker.findById(i);
            out.println(item);
        }
        return true;
    }
}
