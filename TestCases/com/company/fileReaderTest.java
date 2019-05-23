package com.company;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class fileReaderTest {

    @Test
    void getPlaylist() {
        FileReader fileReader = new FileReader("data/TestData");
        assertEquals(5,fileReader.getNoteList().size());

    }

    @Test
    void generatePlaylist() {
    }
}