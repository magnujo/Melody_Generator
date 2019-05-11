package com.company;

import com.jsyn.unitgen.UnitVoice;
import com.jsyn.util.VoiceAllocator;
import com.softsynth.math.AudioMath;
import com.softsynth.shared.time.TimeStamp;

public class OscGeneratorChild extends OscGenerator{

    private double dutyCycle = 0.8; //Controls decay
    private Rhythm rhythm = new Rhythm(100,4.0);
    private VoiceAllocator allocator; //Needed to use noteon and noteoff methods that can control decay
    private UnitVoice[] voices; //Needed for VoiceAllocator to work
    int tonicNote = 60;     //Controls pitch!

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
        double frequency = AudioMath.pitchToFrequency(note);  //Determins the pitch of the Note, out of tonicNote;
        double amplitude = 0.2;
        TimeStamp timeStamp = new TimeStamp(time);
        allocator.noteOn(note, frequency, amplitude, timeStamp);
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
