package ru.job4j.tracker;

public enum TrackerSingle {
    INSTANCE;
    public Item add(Item model) {
        return model;
    }
}
