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
    private Rhythm rhythm = new Rhythm(125);
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

    int tonicNote = 60;     //Controls pitch!
    private double rhythmValue;
    FrequencyHashMap noteList = new FrequencyHashMap();
    private int measureCounter;
    private VoiceAllocator allocator; //Needed to use noteon and noteoff methods that can control decay
    private UnitVoice[] voices; //Needed for VoiceAllocator to work
    SubtractiveSynthVoice voice = new SubtractiveSynthVoice();
    private double dutyCycle = 0.8; //Controls decay


    ArrayList<Integer> intRhytmList;

    OscGenerator (){

        FileReader fileReader = new FileReader(".idea/data");

        this.intRhytmList = fileReader.getPlaylist();

    }

    /**
     * This method is used in conjuction with the refresh method in the maincontroller. It re-reads the file after its updated, and generates a new playlist based on it.
     */

    public void refreshFileReader(){
        FileReader fileReader = new FileReader(".idea/data");

        this.intRhytmList = fileReader.getPlaylist();
        rhythm = new Rhythm(80);
    }

    public void OscSetup(UnitOscillator osc){
        this.osc = osc;
        //synth = JSyn.createSynthesizer();
        synth.add(osc);
        //synth.add(lineOut = new LineOut());
        synth.add(lineOut);
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
     * note Controls the pitch/frequencey of a note in the format "A5" or "B#2" etc.
     * @param decay Controls the amount of time the note is played
     * @param loopMeasureLength Controls how many measures the loop is
     * @param rhythmRandomness Controls how big a chance (0%-100%) there is for a note to be played with a
     *                         random rythmValue instead of one from the loop list.
     */


    public void PlayLoop(ArrayList<Double> scale, boolean isMuted, double decay,
                         int loopMeasureLength, int rhythmRandomness, int counter){
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
            if(!isMuted)
            noteOn(timeNow, tonicNote, scale, counter);                                  //PlayLoop a note at the current time
            noteOff(timeNow+dutyCycle,tonicNote);                 //Realease the note after dutyCycle seconds

            timeNow = timeNow + rhythmValue;                            //Adds the rythm value to current time
            if(timeNow>=measureAccumulator){                            // if the rhythmValue exceeds the current measure, dont sleepUntil the rythmValue,
                measureCounter++;                                       // but sleepUntil the end of the measure.
            //    System.out.println("Playing last note of loop");
                synth.sleepUntil(measureAccumulator);                  //Sleep until the end of the measure
                measureAccumulator = measureAccumulator + loopLength;  // adds the time where the new measure ends ie the current measure end point + a new measure lenght
                index = 0;                                             //Restarts the loop
            }
            else{
                //System.out.println("Playing note");
                synth.sleepUntil(timeNow);}                            //if the rhythmValue doesnt exceed the current measure, sleepUntil the rhythValue ie play the note in its given rhythmValue

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lineOut.stop();

    }
    private void noteOn(double time, int note, ArrayList<Double> scale, int counter) {
        // double frequency = AudioMath.pitchToFrequency(note);  //Determins the pitch of the Note, out of tonicNote;
        double amplitude = 0.2;
        TimeStamp timeStamp = new TimeStamp(time);
        index++;
        allocator.noteOn(note, scale.get(intRhytmList.get(counter)), amplitude, timeStamp);
    }

    private void noteOff(double time, int note) {
        allocator.noteOff(note, new TimeStamp(time));
    }

    /**
    stops synth
     **/
    public void stopSynth(){
        synth.stop();
    }

    public double getMeasure(){
        return rhythm.getMeasure();
    }


    /**
     *
     * @param i
     * The current note thats being played
     * @return
     * returns the current note thats being played
     */
    public int getPlayingNoteNum(int i) {

        return intRhytmList.get(i);
    }

    /**
     *
     * @return
     * has to return quarternote, halfnote or eight-note
     */

    public String getRhythmValue() {
if (rhythmValue==rhythm.getQuarterNote())return "quarter";
        if (rhythmValue==rhythm.getEighthNote())return "eight";
        if (rhythmValue==rhythm.getHalfNote())return "half";
        if(rhythmValue==rhythm.getSixteenthNote())return "sixteenth";
        if(rhythmValue==rhythm.getWholeNote())return "whole";

        return null;
    }


    public UnitOscillator getOsc() { return osc; }




}

