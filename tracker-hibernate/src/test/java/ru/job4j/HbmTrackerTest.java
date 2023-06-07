package ru.job4j;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class HbmTrackerTest {

    @Test
    public void whenAdd() {
        HbmTracker tracker = new HbmTracker();
        Item item = new Item(1, "name1", LocalDateTime.now(), "description1");
        tracker.add(item);
        List<Item> items =  tracker.findAll();
        assertThat(items.size(), is(1));
        assertThat(items.get(0).getId(), is(item.getId()));
        assertThat(items.get(0).getName(), is(item.getName()));
        assertThat(items.get(0).getCreated().withNano(0), is(item.getCreated().withNano(0)));
        assertThat(items.get(0).getDescription(), is(item.getDescription()));
    }

    @Test
    public void replace() {
        HbmTracker tracker = new HbmTracker();
        Item item = new Item(1, "name1", LocalDateTime.now(), "description1");
        tracker.add(item);
        Item itemNew = new Item(1, "name2", LocalDateTime.now(), "description2");
        tracker.replace(1, itemNew);
        List<Item> items =  tracker.findAll();
        assertThat(items.size(), is(1));
        assertThat(items.get(0).getId(), is(itemNew.getId()));
        assertThat(items.get(0).getName(), is(itemNew.getName()));
        assertThat(items.get(0).getCreated().withNano(0), is(itemNew.getCreated().withNano(0)));
        assertThat(items.get(0).getDescription(), is(itemNew.getDescription()));
    }

    @Test
    public void delete() {
        HbmTracker tracker = new HbmTracker();
        Item item1 = new Item(1, "name1", LocalDateTime.now(), "description1");
        tracker.add(item1);
        Item item2 = new Item(2, "name2", LocalDateTime.now(), "description2");
        tracker.add(item2);
        List<Item> items1 =  tracker.findAll();
        assertThat(items1.size(), is(2));
        tracker.delete(2);
        List<Item> items2 =  tracker.findAll();
        assertThat(items2.size(), is(1));
    }

    @Test
    public void findAll() {
        HbmTracker tracker = new HbmTracker();
        Item item1 = new Item(1, "name1", LocalDateTime.now(), "description1");
        tracker.add(item1);
        Item item2 = new Item(2, "name2", LocalDateTime.now(), "description2");
        tracker.add(item2);
        List<Item> items1 =  tracker.findAll();
        assertThat(items1.size(), is(2));
    }

    @Test
    public void findByName() {
        HbmTracker tracker = new HbmTracker();
        Item item1 = new Item(1, "name1", LocalDateTime.now(), "description1");
        tracker.add(item1);
        Item item2 = new Item(2, "name1", LocalDateTime.now(), "description2");
        tracker.add(item2);
        List<Item> rsl =  tracker.findByName("name1");
        assertThat(rsl.size(), is(2));
    }

    @Test
    public void findById() {
        HbmTracker tracker = new HbmTracker();
        Item item1 = new Item(1, "name1", LocalDateTime.now(), "description1");
        tracker.add(item1);
        Item item2 = new Item(2, "name1", LocalDateTime.now(), "description2");
        tracker.add(item2);
        Item rsl1 =  tracker.findById(1);
        assertThat(rsl1, is(item1));
        Item rsl2 =  tracker.findById(2);
        assertThat(rsl2, is(item2));
    }
}