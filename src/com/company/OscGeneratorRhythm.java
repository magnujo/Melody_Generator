package com.company;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.*;

import java.util.ArrayList;
import java.util.Random;

public class OscGeneratorRhythm {
    Synthesizer synth = JSyn.createSynthesizer();

    private Synthesizer synthSaw = JSyn.createSynthesizer();
    public Synthesizer synthSine = JSyn.createSynthesizer();
    private LineOut sawLineOut;
    private double frequency;
    double amplitude;
    public LineOut sineLineOut;
    LineOut lineOut = new LineOut();
    private LineOut squareLineOut;
    private Random random = new Random();
    private LineOut noiseLineOut;
    private Synthesizer synthSquare = JSyn.createSynthesizer();
    private Synthesizer synthNoise = JSyn.createSynthesizer();
    private double duration = 0.1;
    private UnitOscillator sineOsc = new SineOscillator();
    private UnitOscillator oscillator;
    private ArrayList<Integer> playListValues = new ArrayList<>();
    private ArrayList<Double> notes = new ArrayList<>();
    boolean runonce =false;
    fileReader fR = new fileReader(".idea/data");
    HashTest noteList = new HashTest();

    ArrayList<Integer> intRhytmList = fR.getPlaylist();

    public void OscSetup (UnitOscillator oscillator){
        this.oscillator = oscillator;

        synth.start();
        synth.add(oscillator);
        synth.add(lineOut);
        oscillator.amplitude.set(0.5);
        oscillator.output.connect(0, lineOut.input, 0);
        oscillator.output.connect(0, lineOut.input, 1);
    }

    public void Play(String note, double time){
        try{
            this.frequency = noteList.frequencyFinder(note.toUpperCase());}
        catch (NullPointerException e){
            System.out.println("ERROR 401: You propably didnt enter a valid note name. Enter something like 'A4' og 'C#4'");
        }
        oscillator.frequency.set(frequency);
        lineOut.start();

        try {
            synth.sleepFor(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lineOut.stop();
    }


    public void StopSynth(){
        synth.stop();
    }



    public ArrayList<Double> getNotes() {
        return notes;
    }
    public int getPlayingNoteNum(int i) {

        return playListValues.get(i);
    }


    public void SetupSine(){
        sineLineOut = new LineOut();
        synthSine.start();
        synthSine.add(sineOsc);
        synthSine.add(sineLineOut);

        sineOsc.amplitude.set(0.5);
        sineOsc.output.connect(0, sineLineOut.input, 0);
        sineOsc.output.connect(0, sineLineOut.input, 1);


    }

    public void PlaySine(double frequency){
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

        squareOsc.frequency.set(300);
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

    public int getRootNote() {

        Double d = notes.get(0);
        Integer u = d.intValue();

        return u;
    }


    public void RandomMelody(ArrayList<Double> scale, int i,String complexity) {
        if(complexity=="low complexity")
            duration = 0.2;
        if (complexity=="medium complexity"||complexity==null)
            duration = intRhytmList.get(i)*0.1;
        if (complexity=="high complexity")
            duration = random.nextInt(10);

        PlaySine(scale.get(intRhytmList.get(i)));
        //notes.add(scale.get(intRhytmList.get(i)));
        notes.add(scale.get(intRhytmList.get(i)));
        playListValues.add(intRhytmList.get(i));


        try {
            synthSine.sleepFor(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
