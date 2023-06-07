package ru.job4j.tracker;

import org.junit.Test;

import static org.junit.Assert.*;

public class TrackerSingle4Test {

    @Test
    public void getInstance() {
        TrackerSingle4 tr1 = TrackerSingle4.getInstance();
        TrackerSingle4 tr2 = TrackerSingle4.getInstance();
        assertEquals(tr1, tr2);
    }
}