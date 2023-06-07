package ru.job4j.tracker;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FindItemByIdTest {

    @Test
    public void whenFindByIdSuccess() {
        Output out = new StubOutput();
        Store tracker = new MemTracker();
        Item item = new Item("Found item");
        tracker.add(item);
        FindItemById findByIdAction = new FindItemById(out);
        Input input = mock(Input.class);
        when(input.askInt(any(String.class))).thenReturn(1);
        input.askInt("Enter ID: ");
        findByIdAction.execute(input, tracker);
        String ln = System.lineSeparator();
        assertEquals(out.toString(), ("ID: " + tracker.findById(1).getId()
                + ", Name: " + tracker.findById(1).getName() + ln));
        assertEquals(tracker.findById(1), item);
    }

    @Test
    public void whenFindByIdFailure() {
        Output out = new StubOutput();
        Store tracker = new MemTracker();
        Item item = new Item("Found item");
        tracker.add(item);
        FindItemById findByIdAction = new FindItemById(out);
        Input input = mock(Input.class);
        when(input.askInt(any(String.class))).thenReturn(2);
        input.askInt("Enter ID: ");
        findByIdAction.execute(input, tracker);
        String ln = System.lineSeparator();
        assertEquals(out.toString(), ("Объект с требуемым ID не найден" + ln));
        assertNull(tracker.findById(2));
    }
}