package com.company;

import java.util.HashMap;

public class HashTest {

    private ScaleGeneratorTest scaleGenerator = new ScaleGeneratorTest(150, 16.351597831287414);
    private HashMap<String,Double> freqMap = new HashMap<>();
    private HashMap<Double,String> noteMap = new HashMap<>();
    private String[] noteNames = {"C","C#","D","D#","E","F","F#","G","G#","A","A#","B"};
    private int j = 0;
    private int octaveNumber = 0;

    HashTest() {
        for (int i = 0; i < scaleGenerator.getScale().size(); i++) {

            if (i % 12 == 0 && i != 0) {
                j = 0;
                octaveNumber++;
            }
            freqMap.put(noteNames[j] + octaveNumber, scaleGenerator.getScale().get(i));
            noteMap.put(scaleGenerator.getScale().get(i),noteNames[j]+octaveNumber);
            j++;
        }
    }

    public double frequencyFinder (String noteValue){return freqMap.get(noteValue);}
    public String noteFinder (double frequencyValue){return noteMap.get(frequencyValue);}
}
