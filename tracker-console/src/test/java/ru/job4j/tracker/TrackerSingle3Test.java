package ru.job4j.tracker;

import org.junit.Test;

import static org.junit.Assert.*;

public class TrackerSingle3Test {

    @Test
    public void getInstance() {
        TrackerSingle3 tr1 = TrackerSingle3.getInstance();
        TrackerSingle3 tr2 = TrackerSingle3.getInstance();
        assertEquals(tr1, tr2);
    }
}