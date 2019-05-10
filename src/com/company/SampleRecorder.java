package com.company;

import com.jsyn.util.WaveRecorder;
import javafx.fxml.FXML;

import java.io.File;
import java.io.FileNotFoundException;

public class SampleRecorder {
    File wavFile = new File("./data/newSample.vaw");
    WaveRecorder waveRecorder;

    public void startRecording(OscGenerator ocsGen) throws FileNotFoundException {
        waveRecorder = new WaveRecorder(ocsGen.synthSine, wavFile);
        ocsGen.SetupSine();
        ocsGen.getSineOsc().output.connect(0,waveRecorder.getInput(),0);
        ocsGen.getSineOsc().output.connect(0,waveRecorder.getInput(),1);

        if (ocsGen.sineLineOut.isStartRequired()) {
            ocsGen.synth.start();
            ocsGen.PlaySine(0);
        }

        if (ocsGen.synthSine.isRunning()) {
            waveRecorder.start();
            System.out.println("sample is being recorded");
        }
        if (!ocsGen.synthSine.isRunning()) {
            waveRecorder.stop();
            System.out.println("synth stopped, recording ended");
        }
    }

}
