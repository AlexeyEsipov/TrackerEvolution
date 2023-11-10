package ru.job4j.tracker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HbmTrackerTest {
    @AfterEach
    public void clear() {
        try (HbmTracker tracker = new HbmTracker()) {
            tracker.findAll().forEach(item -> tracker.delete(item.getId()));
        }
    }

    @Test
    public void whenAddNewItemThenTrackerHasSameItem() {
        try (HbmTracker tracker = new HbmTracker()) {
            Item item = new Item("item111");
            tracker.add(item);
            int id = item.getId();
            Item result = tracker.findById(item.getId());
            assertThat(result.getName()).isEqualTo(item.getName());
        }
    }

    @Test
    public void whenReplaceThenItemNameChanged() {
        try (HbmTracker tracker = new HbmTracker()) {
            Item item1 = new Item("item222");
            tracker.add(item1);
            int id = item1.getId();

            Item item2 = new Item("item333");
            item2.setId(id);
            tracker.replace(id, item2);
            String result = tracker.findById(id).getName();

            assertThat(result).isEqualTo("item333");
        }
    }

    @Test
    public void whenFindAllThenGetList() {
        try (HbmTracker tracker = new HbmTracker()) {
            Item item1 = new Item("item444");
            Item item2 = new Item("item555");
            tracker.add(item1);
            tracker.add(item2);
            int id1 = item1.getId();
            int id2 = item2.getId();

            List<String> itemNameList = tracker.findAll().stream().map(Item::getName).toList();

            assertThat(itemNameList).contains("item444", "item555");
        }
    }

    @Test
    public void whenDeleteThenItemNotFound() {
        try (HbmTracker tracker = new HbmTracker()) {
            Item item = new Item("item666");
            tracker.add(item);
            int id = item.getId();

            tracker.delete(id);
            Item result = tracker.findById(id);

            assertThat(result).isNull();
        }
    }

    @Test
    public void whenFindByNameThenEqualsToAdded() {
        try (HbmTracker tracker = new HbmTracker()) {
            Item item1 = new Item("item777");
            Item item2 = new Item("item777");
            tracker.add(item1);
            tracker.add(item2);
            int id1 = item1.getId();
            int id2 = item2.getId();

            List<String> result = tracker.findByName("item777")
                    .stream().map(Item::getName).toList();

            assertThat(result).hasSize(2).contains("item777");
        }
    }

    @Test
    public void whenFindByIdThenGetIt() {
        try (HbmTracker tracker = new HbmTracker()) {
            Item item = new Item("item888");
            tracker.add(item);
            int id = item.getId();

            Item result = tracker.findById(id);

            assertThat(result.getName()).isEqualTo("item888");
        }
    }
}