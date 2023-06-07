package ru.job4j.tracker;

public class ReplaceItem implements UserAction {

    private final Output out;

    public ReplaceItem(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "=== Edit item ===";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        int selectId = input.askInt("Enter ID: ");
        Item editItem = new Item();
        editItem.setName(input.askStr("Enter new Name:"));
        if (tracker.replace(selectId, editItem)) {
            out.println("Объект успешно изменен");
        } else {
            out.println("Объект не изменен");
        }
        return true;
    }
}
