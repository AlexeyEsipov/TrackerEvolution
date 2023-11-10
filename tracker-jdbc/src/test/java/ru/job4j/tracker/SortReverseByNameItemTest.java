package ru.job4j.tracker;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class  SortReverseByNameItemTest {

    @Test
    public void whenSortReverseByNameItem() {
        Item n3 = new Item();
        n3.setName("n3");
        n3.setId(3);
        Item n1 = new Item();
        n1.setName("n1");
        n1.setId(1);
        Item n2 = new Item();
        n2.setName("n2");
        n2.setId(2);
        List<Item> orderItem = Arrays.asList(n3, n1, n2);
        orderItem.sort(new SortReverseByNameItem());
        List<Item> expected = Arrays.asList(n3, n2, n1);
        assertArrayEquals(expected.toArray(), orderItem.toArray());
    }
}