package com.company;

import java.util.HashMap;

public class HashTest {

    private ScaleGeneratorTest scaleGenerator = new ScaleGeneratorTest(150, 16.351597831287414);
    private HashMap<String,Double> hashMap = new HashMap<>();
    private String[] noteNames = {"C","C#","D","D#","E","F","F#","G","G#","A","A#","B"};
    private int j = 0;
    private int octaveNumber = 0;

    HashTest() {
        for (int i = 0; i < scaleGenerator.getScale().size(); i++) {

            if (i % 12 == 0 && i != 0) {
                j = 0;
                octaveNumber++;
            }
            hashMap.put(noteNames[j] + octaveNumber, scaleGenerator.getScale().get(i));
            j++;
        }
    }

    public double frequencyFinder (String noteValue){
        return hashMap.get(noteValue);
    }
}
