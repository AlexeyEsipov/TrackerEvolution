package ru.job4j.tracker;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DeleteItemTest {

    @Test
    public void whenDeleteSuccess() {
        Output out = new StubOutput();
        Store tracker = new MemTracker();
        tracker.add(new Item("Deleted item"));
        DeleteItem deleteAction = new DeleteItem(out);
        Input input = mock(Input.class);
        when(input.askInt(any(String.class))).thenReturn(1);
        input.askInt("Enter ID: ");
        deleteAction.execute(input, tracker);
        String ln = System.lineSeparator();
        assertEquals(out.toString(), ("Item was deleted successfully" + ln));
        assertNull(tracker.findById(1));
    }

    @Test
    public void whenDeleteFailure() {
        Output out = new StubOutput();
        Store tracker = new MemTracker();
        Item item = new Item("Deleted item");
        tracker.add(item);
        DeleteItem deleteAction = new DeleteItem(out);
        Input input = mock(Input.class);
        when(input.askInt(any(String.class))).thenReturn(2);
        input.askInt("Enter ID: ");
        deleteAction.execute(input, tracker);
        String ln = System.lineSeparator();
        assertEquals(out.toString(), ("Объект не удален" + ln));
        assertEquals(tracker.findById(1), item);
    }
}