package ru.job4j.tracker;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class StartUITest {

    @Test
    public void whenAddItem() {
        String[] answers = {"Fix PC"};
        Input input = new StubInput(answers);
        Tracker tracker = new Tracker();
        StartUI.createItem(input, tracker);
        Item created = tracker.findAll()[0];
        Item expected = new Item("Fix PC");
        assertThat(created.getName(), is(expected.getName()));
    }

    @Test
    public void whenReplaceItem() {
        Tracker tracker = new Tracker();
        Item item = new Item("new item");
        tracker.add(item);
        String[] answers = {String.valueOf(item.getId()), "replaced item"};
        StartUI.replaceItem(new StubInput(answers), tracker);
        Item replaced = tracker.findById(item.getId());
        assertThat(replaced.getName(), is("replaced item"));
    }

    @Test
    public void whenDeleteItem() {
        /* 1. Создаем объект Tracker */
        Tracker tracker = new Tracker();
        /* 2. Создаем объект item. */
        Item item = new Item("new item");
        /* 3. Добавляем item в tracker. После этой операции у нас есть id. */
        tracker.add(item);
        /* 4. Достаем item.id */
        int deleteId = item.getId();
        /* 4а. и создаем массив с ответами пользователя. */
        String[] answers = {String.valueOf(item.getId())};
        /* 5. Удаляем объект с заданным в StubInput(answers) ID */
        StartUI.deleteItem(new StubInput(answers), tracker);
        /* 6. Ищем по ID, найти не должны */
        Item deleted = tracker.findById(deleteId);
        /* проверяем результат на null */
        assertNull(deleted);
    }
}