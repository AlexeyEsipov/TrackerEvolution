package ru.job4j.tracker;

public class DeleteItem implements UserAction {
    private final Output out;

    public DeleteItem(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "=== Delete item ===";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        int selectId = input.askInt("Enter ID: ");
        if (tracker.delete(selectId)) {
            out.println("Item was deleted successfully");
        } else {
            out.println("Объект не удален");
        }
        return true;
    }
}
