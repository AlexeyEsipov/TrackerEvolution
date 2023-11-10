package ru.job4j.tracker;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReplaceItemTest {

    @Test
    public void whenReplaceSuccess() {
        Output out = new StubOutput();
        Store tracker = new MemTracker();
        tracker.add(new Item("Replaced old item"));
        String replacedName = "New item name";
        ReplaceItem replaceAction = new ReplaceItem(out);
        Input input = mock(Input.class);
        when(input.askInt(any(String.class))).thenReturn(1);
        when(input.askStr(any(String.class))).thenReturn(replacedName);
        input.askInt("Enter ID: ");
        input.askStr("Enter new Name:");
        replaceAction.execute(input, tracker);
        String ln = System.lineSeparator();
        assertEquals(out.toString(), ("Объект успешно изменен" + ln));
        assertEquals(tracker.findAll().get(0).getName(), replacedName);
    }

    @Test
    public void whenReplaceFailure() {
        Output out = new StubOutput();
        Store tracker = new MemTracker();
        tracker.add(new Item("Replaced old item"));
        String replacedName = "New item name";
        ReplaceItem replaceAction = new ReplaceItem(out);
        Input input = mock(Input.class);
        when(input.askInt(any(String.class))).thenReturn(2);
        when(input.askStr(any(String.class))).thenReturn(replacedName);
        input.askInt("Enter ID: ");
        input.askStr("Enter new Name:");
        replaceAction.execute(input, tracker);
        String ln = System.lineSeparator();
        assertEquals(out.toString(), ("Объект не изменен" + ln));
        assertEquals(tracker.findAll().get(0).getName(), "Replaced old item");
    }
}