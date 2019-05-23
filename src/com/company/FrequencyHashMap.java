package com.company;

import java.util.HashMap;

public class FrequencyHashMap {

    private ChromaticScaleGenerator chromaticScaleGenerator = new ChromaticScaleGenerator(150, 16.351597831287414);
    private HashMap<String,Double> freqMap = new HashMap<>();
    private HashMap<Double,Double> noteMap = new HashMap<>();
    private String[] noteNames = {"C","C#","D","D#","E","F","F#","G","G#","A","A#","B"};
    private int j = 0;
    private int octaveNumber = 0;

    /**
     * This class creates a hashmap of all frequencies in 10 octaves and links them with their musical scale key, for easier programming.
     * It also creates a map of notes that is not exponential. Which is used for the visual part of the program since a representation in notes is linear not logaritmic.
     * Just like the notes on a piano are linear. Even though the underlying frequencies rise exponentially the furhter uip you get on the piano.
     */
    FrequencyHashMap() {
        for (int i = 0; i < chromaticScaleGenerator.getScale().size(); i++) {

            if (i % 12 == 0 && i != 0) {
                j = 0;
                octaveNumber++;
            }
            freqMap.put(noteNames[j] + octaveNumber, chromaticScaleGenerator.getScale().get(i));
            noteMap.put(chromaticScaleGenerator.getScale().get(i),i*2.6);
            j++;
        }
    }

    //frequency finder
    public double frequencyFinder (String noteValue){return freqMap.get(noteValue);}
    //pitch map
    public double noteFinder (double frequencyValue){return noteMap.get(frequencyValue);}
}
