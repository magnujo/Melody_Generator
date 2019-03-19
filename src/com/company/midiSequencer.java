package com.company;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import java.io.File;

public class midiSequencer {

    static Sequence getSequencer() throws MidiUnavailableException {

        Sequencer sequencer;
        // Get default sequencer.
        sequencer = MidiSystem.getSequencer();
        if (sequencer == null) {
            // Error -- sequencer device is not supported.
            // Inform user and return...
            System.out.println("no sequencer?");
        } else {
            // Acquire resources and make operational.
            sequencer.open();
        }
        try {
            //File myMidiFile = new File("seq1.mid");
            File myMidiFile = new File("./Data/chords1.mid");
            // Construct a Sequence object, and
            // load it into my sequencer.
            Sequence mySeq = MidiSystem.getSequence(myMidiFile);
            sequencer.setSequence(mySeq);
        } catch (Exception e) {
            System.out.println("no access to file?");
            // Handle error and/or return
        }

        return sequencer.getSequence();
    }

    public void start() {
    }
}
