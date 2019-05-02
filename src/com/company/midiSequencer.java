package com.company;

import javax.sound.midi.*;
import java.io.File;

public class midiSequencer {

    public static void main(String[] args) throws MidiUnavailableException {

        Sequencer          seq;
        Transmitter seqTrans;
        Synthesizer         synth;
        Receiver         synthRcvr;
        try {
            seq     = MidiSystem.getSequencer();
            seqTrans = seq.getTransmitter();
            synth   = MidiSystem.getSynthesizer();
            synthRcvr = synth.getReceiver();
            seqTrans.setReceiver(synthRcvr);
        } catch (MidiUnavailableException e) {
            System.out.println("midi unavailable");
            // handle or throw exception
        }

        Sequencer sequencer;
        // Get default sequencer.
        sequencer = MidiSystem.getSequencer();

        if (sequencer == null) {
            // Error -- sequencer device is not supported.
            // Inform user and return...
            System.out.println("sequencer not supported");
        } else {
            // Acquire resources and make operational.
            sequencer.open(); }

        try {
            File myMidiFile = new File("./midi/chords1.mid");
            // Construct a Sequence object, and
            // load it into my sequencer.
            Sequence mySeq = MidiSystem.getSequence(myMidiFile);
            sequencer.setSequence(mySeq);
        } catch (Exception e) {
            System.out.println("problem importing file");
            // Handle error and/or return
        }

        sequencer.start();

    }
}