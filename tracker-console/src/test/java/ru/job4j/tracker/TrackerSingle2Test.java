package ru.job4j.tracker;

import org.junit.Test;

import static org.junit.Assert.*;

public class TrackerSingle2Test {

    @Test
    public void values() {
        TrackerSingle2 tr1 = TrackerSingle2.getInstance();
        TrackerSingle2 tr2 = TrackerSingle2.getInstance();
        assertEquals(tr1, tr2);
    }
}