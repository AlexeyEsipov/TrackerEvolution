package ru.job4j.tracker;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ItemTest {
    @Test
    public void whenSortItemAscByNameThenTrue() {
        List<Item> actual = Arrays.asList(
                new Item("Boris"),
                new Item("Anton"),
                new Item("Svetlana")
        );
        Collections.sort(actual, new ItemAscByName());
        List<Item> expected = Arrays.asList(
                new Item("Anton"),
                new Item("Boris"),
                new Item("Svetlana")
        );
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void whenSortItemDescByNameThenTrue() {
        List<Item> actual = Arrays.asList(
                new Item("Boris"),
                new Item("Anton"),
                new Item("Svetlana")
        );
        Collections.sort(actual, new ItemDescByName());
        List<Item> expected = Arrays.asList(
                new Item("Svetlana"),
                new Item("Boris"),
                new Item("Anton")
        );
        Assert.assertEquals(actual, expected);
    }
}