package ru.job4j.tracker;

import org.junit.Test;

import static org.junit.Assert.*;

public class TrackerSingleTest {

    @Test
    public void values() {
       TrackerSingle tr1 = TrackerSingle.INSTANCE;
       TrackerSingle tr2 = TrackerSingle.INSTANCE;
       assertEquals(tr1, tr2);
    }
}