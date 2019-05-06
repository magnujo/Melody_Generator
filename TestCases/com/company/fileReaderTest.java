package com.company;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class fileReaderTest {

    @Test
    void playNote() {

        fileReader fR = new fileReader("C:\\Users\\Gem\\Documents\\GitHub\\Osc_Objects_2\\TestCases\\TestData");
        assertEquals(3,fR.getPlaylist().size());

    }

}