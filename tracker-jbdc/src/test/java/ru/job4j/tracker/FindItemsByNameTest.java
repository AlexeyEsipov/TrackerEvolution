package ru.job4j.tracker;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;

public class FindItemsByNameTest {

    @Test
    public void whenFindByNameSuccess() {
        Output out = new StubOutput();
        Store tracker = new MemTracker();
        Item item = new Item("Found item");
        tracker.add(item);
        FindItemsByName findByNameAction = new FindItemsByName(out);
        Input input = mock(Input.class);
        when(input.askStr(any(String.class))).thenReturn("Found item");
        input.askStr("Enter name: ");
        findByNameAction.execute(input, tracker);
        String ln = System.lineSeparator();
        assertEquals(out.toString(), ("ID: " + tracker.findById(1).getId()
                + ", Name: " + tracker.findById(1).getName() + ln));
        assertEquals(tracker.findByName("Found item").get(0), item);
    }

    @Test
    public void whenFindByNameFailure() {
        Output out = new StubOutput();
        Store tracker = new MemTracker();
        Item item = new Item("Found item");
        tracker.add(item);
        FindItemsByName findByNameAction = new FindItemsByName(out);
        Input input = mock(Input.class);
        when(input.askStr(any(String.class))).thenReturn("Unknown");
        input.askStr("Enter name: ");
        findByNameAction.execute(input, tracker);
        String ln = System.lineSeparator();
        assertEquals(
                out.toString(),
                ("Объекты с требуемым именем не найдены" + ln)
        );
        assertEquals(tracker.findByName("Unknown"), Collections.emptyList());
    }
}