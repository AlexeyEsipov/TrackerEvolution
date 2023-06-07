package ru.job4j;

public class FindItemById implements UserAction {

    private final Output out;

    public FindItemById(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "=== Find item by Id ===";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        int selectId = input.askInt("Enter ID: ");
        Item selectItem = tracker.findById(selectId);
        if (selectItem != null && selectItem.getName() != null) {
            out.println("ID: " + selectItem.getId() + ", Name: " + selectItem.getName());
        } else {
            out.println("Объект с требуемым ID не найден");
        }
        return true;
    }
}