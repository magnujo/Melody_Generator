package com.company;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.instruments.SubtractiveSynthVoice;
import com.jsyn.unitgen.*;
import com.jsyn.util.VoiceAllocator;
import com.softsynth.math.AudioMath;
import com.softsynth.shared.time.TimeStamp;

import java.util.ArrayList;
import java.util.Random;

public class OscGeneratorRhythm2 {
    Synthesizer synth = JSyn.createSynthesizer();
    private UnitOscillator osc;
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
    FileReader fR = new FileReader(".idea/data");
    HashTest noteList = new HashTest();

    private Rhythm rhythm = new Rhythm(100,4.0);

    ArrayList<Integer> intRhytmList = fR.getPlaylist();
    private double dutyCycle = 0.8; //Controls decay

    private VoiceAllocator allocator; //Needed to use noteon and noteoff methods that can control decay
    private UnitVoice[] voices; //Needed for VoiceAllocator to work
    int tonicNote = 60;     //Controls pitch!



    SubtractiveSynthVoice voice = new SubtractiveSynthVoice();

    //voices[MAX_VOICES] = voice;


    public void OscSetup (){
        synth = JSyn.createSynthesizer();
        synth.add(osc = new SawtoothOscillatorBL());
        synth.add(lineOut = new LineOut());
        synth.add(voice);
        voice.getOutput().connect(0, lineOut.input, 0);
        voice.getOutput().connect(0, lineOut.input, 1);
        voices = new UnitVoice[1];
        voices[0] = voice;

        allocator = new VoiceAllocator(voices);

        synth.start();

    }

    public void Play(double decay, int notesPerMeasure){
        this.dutyCycle = decay;
        lineOut.start();

        double timeNow = synth.getCurrentTime();

        try {
                doRythm(timeNow, tonicNote,notesPerMeasure);
                timeNow = timeNow + rhythm.getMeasure();   //Adds the time (seconds) of a measure of the given BPM and pulse to the timeNow
                synth.sleepUntil(timeNow);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //synth.stop();
        lineOut.stop();
    }

    public void doRythm(double time, int note, int notesPerMeasure) {


       for (int i = 0; i < notesPerMeasure; i++) {
            double localNoteLength = rhythm.getRandomNoteLength();
            noteOn(time, note);
            noteOff(time + dutyCycle * localNoteLength, note);
            time += localNoteLength;
        }
    }
    private void noteOff(double time, int note) {
        allocator.noteOff(note, new TimeStamp(time));
    }

    private void noteOn(double time, int note) {
        double frequency = AudioMath.pitchToFrequency(note);  //Determins the pitch of the note, out of tonicNote;
        double amplitude = 0.2;
        TimeStamp timeStamp = new TimeStamp(time);
        allocator.noteOn(note, frequency, amplitude, timeStamp);
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

    public static void main(String[] args) {
        OscGeneratorRhythm2 osc = new OscGeneratorRhythm2();
        osc.OscSetup();

        for (int i = 0; i <2; i++) {

            osc.Play(0.1,32);

        }
        osc.synth.stop();


    }
}
