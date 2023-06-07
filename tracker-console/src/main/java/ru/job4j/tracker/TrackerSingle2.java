package ru.job4j.tracker;

public class TrackerSingle2 {

    private static TrackerSingle2 instance;

    private TrackerSingle2() {
    }

    public static TrackerSingle2 getInstance() {
        if (instance == null) {
            instance = new TrackerSingle2();
        }
        return instance;
    }

    public Item add(Item model) {
        return model;
    }
}