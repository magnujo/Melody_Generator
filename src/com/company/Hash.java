package com.company;

import java.util.HashMap;

public class Hash {

    public static void main(String[] args) {
        // C0 = 16.351597831287414   261.62556530059913
        ScaleGeneratorTest scaleGenerator = new ScaleGeneratorTest(150, 55.0 * Math.pow(Math.pow(2,1.0/12.0),-21));
        scaleGenerator.ScaleGeneratorEquation2(150,55.0 * Math.pow(Math.pow(2,1.0/12.0),-21));
        HashMap<String,Double> hashMap = new HashMap<>();
        String[] noteNames = {"C","C#","D","D#","E","F","F#","G","G#","A","A#","B"};
        System.out.println("hashmap empty?" + hashMap.isEmpty());
        int j = 0;
        int octaveNumber = 0;
        for (int i = 0; i < scaleGenerator.getScale2().size(); i++) {

            if (i%12==0 && i!=0) {
                j = 0;
                octaveNumber++;
            }
            hashMap.put(noteNames[j]+octaveNumber,scaleGenerator.getScale2().get(i));
            j++;
        }

        System.out.println(hashMap.get("C4"));
    }
}






