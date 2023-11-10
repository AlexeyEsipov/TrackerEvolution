package ru.job4j.tracker;

import java.util.Arrays;
import java.util.List;

public class StartUI {
    private final Output out;

    public StartUI(Output out) {
        this.out = out;
    }

    public void init(Input input, Store tracker, List<UserAction> actions) {
        boolean run = true;
        while (run) {
            this.showMenu(actions);
            int select = input.askInt("Select: ");
            if (select < 0 || select >= actions.size()) {
                out.println("Wrong input, you can select: 0 .. " + (actions.size() - 1));
                continue;
            }
            UserAction action = actions.get(select);
            run = action.execute(input, tracker);
        }
    }

    private void showMenu(List<UserAction> actions) {
        out.println("Menu:");
        for (int index = 0; index < actions.size(); index++) {
            out.println(index + ". " + actions.get(index).name());
        }
    }

    public static void main(String[] args) {
        Output output = new ConsoleOutput();
        Input input = new ValidateInput(output, new ConsoleInput());

        /*Old variant - without DB
        MemTracker tracker = new MemTracker();
        List<UserAction> actions = Arrays.asList(
                    new CreateAction(output), new ShowAllAction(output), new EditItemAction(output),
                    new DeleteAction(output), new FindByIdAction(output),
                    new FindByNameAction(output),
                    new ExitAction(output));
            new StartUI(output).init(input, tracker, actions);*/

//        try (SqlTracker tracker = new SqlTracker()) {
        try (HbmTracker tracker = new HbmTracker()) {
//            tracker.init();
            List<UserAction> actions = Arrays.asList(
                    new CreateAction(output), new ShowAllAction(output),
                    new EditItemAction(output),
                    new DeleteAction(output), new FindByIdAction(output),
                    new FindByNameAction(output),
                    new ExitAction(output));
            new StartUI(output).init(input, tracker, actions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}