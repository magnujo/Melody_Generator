package com.company;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class fileReaderTest {

    @Test
    void getPlaylist() {
        FileReader fileReader = new FileReader("C:\\Users\\magnu\\IdeaProjects\\SynthProject\\TestCases\\TestData");
        assertEquals(5,fileReader.getPlaylist().size());

    }

    @Test
    void generatePlaylist() {
    }
}