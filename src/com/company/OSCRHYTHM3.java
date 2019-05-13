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

public class OSCRHYTHM3{
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
    private ArrayList<Double> loop = new ArrayList<>();
    boolean runonce =false;
    // FileReader fR = new FileReader(".idea/data");
    HashTest noteList = new HashTest();
    private boolean firstLoop = true;
    private double[] amps = {0.0,0.2};
    private Rhythm rhythm = new Rhythm(90,4.0);
    private double measureAccumulator = rhythm.getMeasure();
    // ArrayList<Integer> intRhytmList = fR.getPlaylist();
    private double rhythmValueAccumulator = 0;
    private double dutyCycle = 0.8; //Controls decay
    private int index = 0;
    private VoiceAllocator allocator; //Needed to use noteon and noteoff methods that can control decay
    private UnitVoice[] voices; //Needed for VoiceAllocator to work
    int tonicNote = 60;     //Controls pitch!
    private double rhythmValue;



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

    public void Play(double decay, int notesPerMeasure){ // denne metode spiller en takt
        this.dutyCycle = decay;
        int randomness = random.nextInt(20);

        System.out.println("rythmakku: " + rhythmValueAccumulator);
        System.out.println("measureAccumulator: "+measureAccumulator);

        rhythmValueAccumulator = rhythmValueAccumulator+rhythm.getLoop().get(index);

        lineOut.start();

        rhythmValue = rhythm.getLoop().get(index);

        double timeNow = synth.getCurrentTime();
        try {
            noteOn(timeNow,tonicNote);
            noteOff(timeNow+dutyCycle,tonicNote);

            timeNow = timeNow + rhythmValue;            //Adds the time (seconds) of a measure of the given BPM and pulse to the timeNow. Measure = taktens længde i sekunder
            index++;
            if(timeNow>=measureAccumulator){
                System.out.println("Ny takt");
                synth.sleepUntil(measureAccumulator);
                measureAccumulator = measureAccumulator + rhythm.getMeasure();
                index = 0;
            }
            else{synth.sleepUntil(timeNow);}

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lineOut.stop();
    }

    private void noteOff(double time, int note) {
        allocator.noteOff(note, new TimeStamp(time));
    }

    private void noteOn(double time, int note) {
        double frequency = AudioMath.pitchToFrequency(note);  //Determins the pitch of the Note, out of tonicNote;
        double amplitude = 0.2;
        TimeStamp timeStamp = new TimeStamp(time);
        System.out.println("Playing note");

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



    public int getRootNote() {

        Double d = notes.get(0);
        Integer u = d.intValue();

        return u;
    }


    public void RandomMelody(ArrayList<Double> scale, int i,String complexity) {
        if(complexity=="low complexity")
            duration = 0.2;
        if (complexity=="medium complexity"||complexity==null)
            //   duration = intRhytmList.get(i)*0.1;
            if (complexity=="high complexity")
                duration = random.nextInt(10);

        // PlaySine(scale.get(intRhytmList.get(i)));
        //notes.add(scale.get(intRhytmList.get(i)));
        // notes.add(scale.get(intRhytmList.get(i)));
        //  playListValues.add(intRhytmList.get(i));


        try {
            synthSine.sleepFor(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        OSCRHYTHM3 osc = new OSCRHYTHM3();
        osc.OscSetup();
        int measureCounter = 1;
        for (int i = 0; i <64; i++) {
            osc.Play(0.1,32);

        }
        osc.synth.stop();

    }
}
