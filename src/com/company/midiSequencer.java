package com.company;

import com.jsyn.midi.MidiSynthesizer;
import com.jsyn.util.MultiChannelSynthesizer;

import javax.sound.midi.*;
import javax.sound.midi.Track;
import java.io.File;

import static javax.sound.midi.ShortMessage.NOTE_ON;

public class midiSequencer {

    public static void main(String[] args) throws MidiUnavailableException {

        Sequencer seq;
        Transmitter seqTrans;
        Synthesizer synthJava;

        MultiChannelSynthesizer synthMulti = new MultiChannelSynthesizer();

        MidiSynthesizer synthMidi = new MidiSynthesizer(synthMulti);
        Receiver synthRcvr;

        try {
            seq = MidiSystem.getSequencer();
            seqTrans = seq.getTransmitter();
            synthJava = MidiSystem.getSynthesizer();
            synthRcvr = synthJava.getReceiver();
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

        /*try {
            File myMidiFile = new File("./data/chords1.mid");
            // Construct a Sequence object, and
            // load it into my sequencer.
            Sequence mySeq = MidiSystem.getSequence(myMidiFile);
            sequencer.setSequence(mySeq);
        } catch (Exception e) {
            System.out.println("problem importing file");
            // Handle error and/or return
        }

        sequencer.start(); */

        class CustomReceiver implements Receiver {
            @Override
            public void send(MidiMessage message, long timeStamp) {
                byte[] bytes = message.getMessage();
                synthMidi.onReceive(bytes, 0, bytes.length);
            }
            @Override
            public void close() {
                System.out.println("closed");
            }
        }

        /*
        //TODO: create a new sequence with our data somehow
        public void writeSequence() {
        Sequence mySeq = null;
        try {
            mySeq = new Sequence(3,2);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        if (mySeq != null) {
            mySeq.createTrack();
            ShortMessage message = new ShortMessage();
            try {
                MidiEvent midiEvent = new MidiEvent(message.setMessage(NOTE_ON), 2);
            } catch (InvalidMidiDataException e) {
                e.printStackTrace();
            }
        }

        }*/
    }
}