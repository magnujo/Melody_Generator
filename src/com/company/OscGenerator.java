package com.company;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.instruments.SubtractiveSynthVoice;
import com.jsyn.unitgen.*;
import com.jsyn.util.VoiceAllocator;
import com.softsynth.shared.time.TimeStamp;

import java.util.ArrayList;
import java.util.Random;

public class OscGenerator {
    Synthesizer synth = JSyn.createSynthesizer();

    private Synthesizer synthSaw = JSyn.createSynthesizer();
    public Synthesizer synthSine = JSyn.createSynthesizer();
    private LineOut sawLineOut;
    private UnitOscillator osc;

    private double frequency;
    double amplitude;
    public LineOut sineLineOut;
    LineOut lineOut = new LineOut();
    private LineOut squareLineOut;
    private Random random = new Random();
    private double rhythmValueAccumulator = 0;
    private Rhythm rhythm = new Rhythm(125,4.0);
    private double measureAccumulator;
    private double loopLength;
    private boolean first = true;
    private int index = 0;
    private LineOut noiseLineOut;
    private Synthesizer synthSquare = JSyn.createSynthesizer();
    private Synthesizer synthNoise = JSyn.createSynthesizer();
    private double duration;
    private UnitOscillator sineOsc = new SineOscillator();
    private UnitOscillator oscillator;
    private ArrayList<Integer> playListValues = new ArrayList<>();
    private ArrayList<Double> notes = new ArrayList<>();

    int tonicNote = 60;     //Controls pitch!
    private double rhythmValue;
    HashTest noteList = new HashTest();
    private int measureCounter;
    private VoiceAllocator allocator; //Needed to use noteon and noteoff methods that can control decay
    private UnitVoice[] voices; //Needed for VoiceAllocator to work
    SubtractiveSynthVoice voice = new SubtractiveSynthVoice();
    private double dutyCycle = 0.8; //Controls decay



    ArrayList<Integer> intRhytmList;

    OscGenerator (int octaves){

        FileReader fileReader = new FileReader(".idea/data", octaves);

        this.intRhytmList = fileReader.getPlaylist();

    }

    public void refreshFileReader(){
        FileReader fileReader = new FileReader(".idea/data", 0);

        this.intRhytmList = fileReader.getPlaylist();
    }

    public void OscSetup(UnitOscillator osc){
        synth = JSyn.createSynthesizer();
        synth.add(osc);
        synth.add(lineOut = new LineOut());
        synth.add(voice);
        voice.getOutput().connect(0, lineOut.input, 0);
        voice.getOutput().connect(0, lineOut.input, 1);
        voices = new UnitVoice[1];
        voices[0] = voice;
        allocator = new VoiceAllocator(voices);
        synth.start();
    }

    /**
     *
     * @param note Controls the pitch/frequencey of a note in the format "A5" or "B#2" etc.
     * @param decay Controls the amount of time the note is played
     * @param loopMeasureLength Controls how many measures the loop is
     * @param rhythmRandomness Controls how big a chance (0%-100%) there is for a note to be played with a
     *                         random rythmValue instead of one from the loop list.
     */


    public void PlayLoop(ArrayList<Double> scale, double decay, int loopMeasureLength, int rhythmRandomness){
        int randomInt = random.nextInt(100);

        this.dutyCycle = decay;

        if(first == true){
            loopLength=rhythm.getMeasure()*loopMeasureLength;
            measureAccumulator= loopLength;
        }
        first =false;

        rhythmValueAccumulator = rhythmValueAccumulator+rhythm.getLoop().get(index);   //Accumulates the rhythm values so that we know
        // how much time is left in the measure

        lineOut.start();
        if (randomInt<=100-rhythmRandomness){
            rhythmValue = rhythm.getLoop().get(index);
        }
        else {rhythmValue = rhythm.getRandomNoteLength(0,3);
        }

        double timeNow = synth.getCurrentTime();
        try {
            noteOn(timeNow, tonicNote, scale);                                  //PlayLoop a note at the current time
            noteOff(timeNow+dutyCycle,tonicNote);                 //Realease the note after dutyCycle seconds

            timeNow = timeNow + rhythmValue;                            //Adds the rythm value to current time
            index++;
            if(timeNow>=measureAccumulator){                            // if the rhythmValue exceeds the current measure, dont sleepUntil the rythmValue,
                measureCounter++;                                       // but sleepUntil the end of the measure.
                System.out.println("Playing last note of loop");
                synth.sleepUntil(measureAccumulator);                  //Sleep until the end of the measure
                measureAccumulator = measureAccumulator + loopLength;  // adds the time where the new measure ends ie the current measure end point + a new measure lenght
                index = 0;                                             //Restarts the loop
            }
            else{
                System.out.println("Playing note");
                synth.sleepUntil(timeNow);}                            //if the rhythmValue doesnt exceed the current measure, sleepUntil the rhythValue ie play the note in its given rhythmValue

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lineOut.stop();

    }
    private void noteOn(double time, int note, ArrayList<Double> scale) {
        // double frequency = AudioMath.pitchToFrequency(note);  //Determins the pitch of the Note, out of tonicNote;
        double amplitude = 0.2;
        TimeStamp timeStamp = new TimeStamp(time);
        allocator.noteOn(note, scale.get(intRhytmList.get(index)), amplitude, timeStamp);
    }

    private void noteOff(double time, int note) {
        allocator.noteOff(note, new TimeStamp(time));
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

    public UnitOscillator getSineOsc() {
        return sineOsc;
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


    public void Play(ArrayList<Double> scale, int i, String complexity, String oscillatorType, boolean muted) {



        if (complexity.equals("low complexity")) {
            duration = 0.1;
        }
        if (complexity.equals("medium complexity") || complexity == null) {

            duration = intRhytmList.get(i) * 0.1;
        }
        if (complexity.equals("high complexity")) {

            duration = random.nextInt(1);
        }

        if(!muted) {
            PlaySine(scale.get(intRhytmList.get(i)));
        }

        notes.add(scale.get(intRhytmList.get(i)));
        playListValues.add(intRhytmList.get(i));


        if (oscillatorType == null||oscillatorType=="sine") {
            try {
                synthSine.sleepFor(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (oscillatorType == null||oscillatorType=="square") {
            try {
                synthSine.sleepFor(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        HashTest noteMap = new HashTest();
         MajorScaleTest majorScala = new MajorScaleTest(13,noteMap.frequencyFinder("B0"));

        OscGenerator oscGen = new OscGenerator(0);
        oscGen.OscSetup(new SineOscillator());

        for (int i = 0; i <64; i++) {
            oscGen.PlayLoop(majorScala.getScale(),0.1,1,0);

        }
        oscGen.synth.stop();

    }


}

