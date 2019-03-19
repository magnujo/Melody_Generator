package com.company;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.*;

import java.util.Random;

public class OscGenerator {
    private Synthesizer synthSaw = JSyn.createSynthesizer();
    private Synthesizer synthSine = JSyn.createSynthesizer();
    private LineOut sawLineOut;
    private double frequency;
    double amplitude;
    private LineOut sineLineOut;
    private LineOut squareLineOut;
    private Random random = new Random();
    private LineOut noiseLineOut;
    private Synthesizer synthSquare = JSyn.createSynthesizer();
    private Synthesizer synthNoise = JSyn.createSynthesizer();
    private double duration = 1;
    private UnitOscillator sineOsc = new SineOscillator();
    private double[] cSkala = {261.63, 293.66, 329.63, 349.23, 392.00, 440.00, 493.88, 523.25, 587.33, 659.25, 698.46, 783.99};



    private void SetupSine(){
        sineLineOut = new LineOut();
        synthSine.start();
        synthSine.add(sineOsc);
        synthSine.add(sineLineOut);


        sineOsc.amplitude.set(0.5);
        sineOsc.output.connect(0, sineLineOut.input, 0);
        sineOsc.output.connect(0, sineLineOut.input, 1);
    }

    private void PlaySine(double frequency){
        this.frequency = frequency;
        sineOsc.frequency.set(frequency);
        sineLineOut.start();
    }

    public void SetupSaw(){

        UnitOscillator sawOsc = new SawtoothOscillator();
        sawLineOut = new LineOut();
        synthSaw.start();
        synthSaw.add(sawOsc);
        synthSaw.add(sawLineOut);

        sawOsc.frequency.set(400);
        sawOsc.amplitude.set(0.4);
        sawOsc.output.connect(0, sawLineOut.input, 0);
        sawOsc.output.connect(0, sawLineOut.input, 1);

    }


    public void SetupSquare() {

        UnitOscillator squareOsc = new SquareOscillator();
        squareLineOut = new LineOut();
        synthSquare.start();
        synthSquare.add(squareOsc);
        synthSquare.add(squareLineOut);

        squareOsc.frequency.set(400);
        squareOsc.amplitude.set(0.4);
        squareOsc.output.connect(0, squareLineOut.input, 0);
        squareOsc.output.connect(0, squareLineOut.input, 1);

    }


    public void SetupRedNoise() {
        RedNoise noise = new RedNoise();
        noiseLineOut = new LineOut();
        synthNoise.start();
        synthNoise.add(noise);
        synthNoise.add(noiseLineOut);

        noise.frequency.set(3000);
        noise.amplitude.set(0.4);
        noise.output.connect(0, noiseLineOut.input, 0);
        noise.output.connect(0, noiseLineOut.input, 1);

    }

    public void RandomMelody() {
        SetupSine();


        for (int i = 0; i < 100; i++) {

            int rndIndex = random.nextInt(cSkala.length);
            PlaySine(cSkala[rndIndex]);
            System.out.print(i+"       "+cSkala[rndIndex]);
            System.out.println();



            try {
                synthSine.sleepFor(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sineLineOut.stop();
               /* case 1:
                    PlaySine(293.66);
                    try {
                        synthSine.sleepFor(duration);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    sineLineOut.stop();
                    System.out.println("D");
                    i++;

                case 2:
                    PlaySine(329.63);
                    try {
                        synthSine.sleepFor(duration);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    sineLineOut.stop();
                    System.out.println("E");
                    i++;

                case 3:
                    PlaySine(349.23);
                    try {
                        synthSine.sleepFor(duration);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    sineLineOut.stop();
                    System.out.println("F");
                    i++;

                case 4:
                    PlaySine(392.00);
                    try {
                        synthSine.sleepFor(duration);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    sineLineOut.stop();
                    System.out.println("G");
                    i++;

                case 5:
                    PlaySine(440.00);
                    try {
                        synthSine.sleepFor(duration);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    sineLineOut.stop();
                    System.out.println("A");
                    i++;

                case 6:
                    PlaySine(493.88);
                    try {
                        synthSine.sleepFor(duration);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    sineLineOut.stop();
                    System.out.println("B");
                    i++;
                    */

        }
        synthSine.stop();
    }

  /*  public void RandomMadness() {
        SetupSine(400,0.5);
        SetupSaw();
        SetupSquare();
        SetupRedNoise();


        for (int i = 0; i < 100; i++) {
            switch (random.nextInt(2)) {
                case 0:
                    sawLineOut.start();
                    try {
                        synthSaw.sleepFor(duration);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    sawLineOut.stop();
                    System.out.println("1");
                    i++;
                case 1:
                    sineLineOut.start();
                    try {
                        synthSaw.sleepFor(duration);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    sineLineOut.stop();
                    System.out.println("2");
                    i++;

                case 2:
                    squareLineOut.start();
                    try {
                        synthSquare.sleepFor(duration);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    squareLineOut.stop();
                    System.out.println("3");
                    i++;

                case 3:
                    noiseLineOut.start();
                    try {
                        synthNoise.sleepFor(duration);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    noiseLineOut.stop();
                    System.out.println("4");
                    i++;

            }
        }
        synthSquare.stop();
        synthSaw.stop();
        synthNoise.stop();
        synthSine.stop();
    }
*/


}

