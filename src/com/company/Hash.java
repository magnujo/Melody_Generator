package com.company;

import java.util.HashMap;

public class Hash {

    public static void main(String[] args) {

        ScaleGeneratorTest scaleGenerator = new ScaleGeneratorTest(150, 16.351597831287414);
        HashMap<String,Double> hashMap = new HashMap<>();
        String[] noteNames = {"C","C#","D","D#","E","F","F#","G","G#","A","A#","B"};
        System.out.println("hashmap empty?" + hashMap.isEmpty());
        int j = 0;
        int octaveNumber = 0;
        for (int i = 0; i < scaleGenerator.getScale().size(); i++) {

            if (i%12==0 && i!=0) {
                j = 0;
                octaveNumber++;
            }
            hashMap.put(noteNames[j]+octaveNumber,scaleGenerator.getScale().get(i));
            j++;
        }


    }
}






