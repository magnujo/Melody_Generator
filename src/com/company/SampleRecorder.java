package com.company;

import com.jsyn.util.WaveRecorder;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

public class SampleRecorder {

    File wavFile;
    File RecFile = new File("./data/tempWave.wav");
    WaveRecorder waveRecorder;

    public void initRecording(OscGenerator oscGen) throws IOException {

        waveRecorder = new WaveRecorder(oscGen.synthSine, RecFile);
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
            waveRecorder.stop();
            waveRecorder.close();
            System.out.println("synth stopped, recording ended");

            FileChooser fileChooser = new FileChooser();

            String userDir = System.getProperty("user.home");
            fileChooser.setInitialDirectory(new File(userDir+"/Desktop"));
            fileChooser.getExtensionFilters().addAll(
                    //new FileChooser.ExtensionFilter("All Files", "*."),
                    new FileChooser.ExtensionFilter("Wave", "*.wav"),
                    new FileChooser.ExtensionFilter("MP3", "*.mp3")
            );
            wavFile = fileChooser.showSaveDialog(new Stage());

            if (RecFile != null) {
                System.out.println("File saved as: "+wavFile.getName());
                System.out.println("File saved to: "+wavFile.getAbsolutePath());
                RecFile.renameTo(wavFile);
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
