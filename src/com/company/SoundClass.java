package com.company;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.instruments.SubtractiveSynthVoice;
import com.jsyn.unitgen.*;
import com.softsynth.shared.time.TimeStamp;
import java.util.ArrayList;
import java.util.Random;



public class SoundClass {
    /**
     * Creates a JSyn synthesizer with a SynthesisEngine that countains the sample rate and buffer size.
     */
    Synthesizer synth = JSyn.createSynthesizer();
    /**
     * UnitOscillator the motherclass for different oscillators.
     */
    private UnitOscillator osc;
    /**
     * Controls the routing of the sound data to the soundcard
     */
    LineOut lineOut = new LineOut();

    public boolean lineOutPlaying = false;

    private Random random = new Random();
    /**
     * rhythm values are accumulated in this variable as they are played in the program.
     */
    private double rhythmValueAccumulator = 0;

    private Rhythm rhythm = new Rhythm(80);
    private double measureAccumulator;
    private double loopLength;
    private boolean first = true;
    private int index = 0;
    private double rhythmValue;
    private int measureCounter;
    SubtractiveSynthVoice voice = new SubtractiveSynthVoice();
    private double dutyCycle = 0.8; //Controls decay
    ArrayList<Integer> noteList;

    /**
     * This constructor makes sure that the noteList is created from the inputtet data via FileReader.
     */

    SoundClass(){
        FileReader fileReader = new FileReader(".idea/data");
        this.noteList = fileReader.getNoteList();
        rhythmValue= rhythm.getLoop().get(index);

    }

    /**
     * This method is used in conjuction with the refresh method in the maincontroller. It re-reads the file after its updated, and generates a new notelist based on it.
     */

    public void refreshFileReader(){
        FileReader fileReader = new FileReader(".idea/data");
        this.noteList = fileReader.getNoteList();
        rhythm = new Rhythm(80);
    }

    /**
     * This method adds all the JSyn objects to he synthesizer, that is needed for it to play sound.
     * @param osc Determines which waveform oscillator to be added to the synth.
     */

    public void OscSetup(UnitOscillator osc){
        this.osc = osc;
        synth.add(osc);
        synth.add(lineOut);
        synth.add(voice);
        voice.getOutput().connect(0, lineOut.input, 0);
        voice.getOutput().connect(0, lineOut.input, 1);
        synth.start();
        lineOutPlaying = true;
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

        lineOutPlaying = true;
        if (randomInt<=100-rhythmRandomness){
            rhythmValue = rhythm.getLoop().get(index);
        }
        else {rhythmValue = rhythm.getRandomRhythmValue(2,4);
        }
        rhythmValueAccumulator = rhythmValueAccumulator+rhythmValue;
        index++;
        double timeNow = synth.getCurrentTime();
        try {
            if(!isMuted){
                noteOn(timeNow, scale, counter);                                  //PlayLoop a note at the current time
            }
            noteOff(timeNow+dutyCycle);                 //Realease the note after dutyCycle seconds

            timeNow = timeNow + rhythmValue;                            //Adds the rythm value to current time.
            if(timeNow >= measureAccumulator){                            // if the rhythmValue exceeds the current measure, dont sleepUntil the rythmValue,
                measureCounter++;                                       // but sleepUntil the end of the measure.
                synth.sleepUntil(measureAccumulator);                  //Sleeps until the end of the measure
                measureAccumulator = measureAccumulator + loopLength;  // adds the time where the new measure ends ie the current measure end point + a new measure lenght
                index = 0;                                             //Restarts the loop
            }
            else{synth.sleepUntil(timeNow);     //if the rhythmValue doesnt exceed the current measure, sleepUntil the rhythValue ie play the note in its given rhythmValue


            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lineOutPlaying = false;
    }


    /**
     * This method determines how and when a note should be played, using a TimeStamp
     * @param time Controls when the note should be played
     * @param scale Controls which scale the note should be played from
     * @param counter Controls which frequency from the scale the note should be played
     */

    private void noteOn(double time, ArrayList<Double> scale, int counter) {
        // double frequency = AudioMath.pitchToFrequency(note);  //Determins the pitch of the Note, out of tonicNote;
        double amplitude = 0.2;
        TimeStamp timeStamp = new TimeStamp(time);
        index++;
        voice.noteOn(scale.get(noteList.get(counter)), amplitude, timeStamp);
    }
    /**
     * This method determines how and when a note should stop being played, using a TimeStamp
     * @param time Controls when the note should stop being played
     */
    private void noteOff(double time) {
        voice.noteOff(new TimeStamp(time));
    }

    /**
     * @param i
     * The current note thats being played
     * @return
     * returns the current note thats being played
     */
    public int getPlayingNoteNum(int i) {
        return noteList.get(i);
    }

    /**
     *Returns the rhythm values
     * @return
     *
     */

    public String getRhythmValue() {
        if (rhythmValue==rhythm.getQuarterNote()){return "quarter";}
        if (rhythmValue==rhythm.getEighthNote()){return "eight";}
        if (rhythmValue==rhythm.getHalfNote()){return "half";}
        if(rhythmValue==rhythm.getSixteenthNote()){return "sixteenth";}
        if(rhythmValue==rhythm.getWholeNote()){return "whole";}
        return null;
    }

    public UnitOscillator getOsc() { return osc; }

}

