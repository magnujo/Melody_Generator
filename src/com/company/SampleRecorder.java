package com.company;

import com.jsyn.util.WaveRecorder;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SampleRecorder {

    //Path til temp fil
    //Path tempPath = Files.createTempDirectory("tempFiles");

    File wavFile;
    WaveRecorder waveRecorder;

    public void initRecording(OscGenerator oscGen) throws IOException {

        FileChooser fileChooser = new FileChooser();

        String userDir = System.getProperty("user.home");
        fileChooser.setInitialDirectory(new File(userDir+"/Desktop"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*."),
                new FileChooser.ExtensionFilter("Wave", "*.wav"),
                new FileChooser.ExtensionFilter("MP3", "*.mp3")
        );
        wavFile = fileChooser.showSaveDialog(new Stage());

        waveRecorder = new WaveRecorder(oscGen.synthSine, wavFile);
        oscGen.getSineOsc().output.connect(0, waveRecorder.getInput(), 0);
        oscGen.getSineOsc().output.connect(0, waveRecorder.getInput(), 1);
        System.out.println("ready to record - press play");

        if (oscGen.lineOut.isStartRequired()) {
            oscGen.getSineOsc().frequency.set(0);
        }
    }

    public void startRecording(OscGenerator oscGen) {

        if (oscGen.synthSine.isRunning()) {
            waveRecorder.start();
            System.out.println("sample is being recorded");
        }
    }

    public void stopRecording(OscGenerator ocsGen) throws IOException {
        if (!ocsGen.synthSine.isRunning()) {
            ocsGen.getSineOsc().stop();
            //ocsGen.lineOut.stop();
            waveRecorder.stop();
            waveRecorder.close();
            System.out.println("synth stopped, recording ended");

            //save-dialog burde være her når temp fil virker

            if (wavFile != null) {
                System.out.println("File saved as: "+wavFile.getName());
                System.out.println("File saved to: "+wavFile.getAbsolutePath());
            }
        }
    }
/*
    public void playFromFile(OscGenerator ocsGen) throws IOException {
        FloatSample mySample;
        mySample = SampleLoader.loadFloatSample(wavFile);

        ocsGen.SetupSine();
        ocsGen.getSineOsc().output.connect(0,mySample,0);
    }
*/
}
