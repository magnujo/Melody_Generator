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

    /**
     * Connects a JSyn waveRecorder to the oscillator from a OscGenerator
     * @param oscGen the OscGenerator to be recorded from
     * @throws IOException
     */
    public void initRecording(OscGenerator oscGen) throws IOException {

        waveRecorder = new WaveRecorder(oscGen.synth, RecFile);
        oscGen.voice.getOutput().connect(0,waveRecorder.getInput(),0);
        oscGen.voice.getOutput().connect(0,waveRecorder.getInput(),1);

        System.out.println("ready to record - press play");

        if (oscGen.lineOut.isStartRequired()) {
            oscGen.getOsc().frequency.set(0);
        }
    }

    /**
     * Starts recording the output from the oscillator in OscGenerator
     *(needs to be connected to the oscillator beforehand)
     * @param oscGen OscGenerator to be recorded from
     */
    public void startRecording(OscGenerator oscGen) {

        if (oscGen.synth.isRunning()) {
            waveRecorder.start();
            System.out.println("sample is being recorded");
        }
    }

    /**
     * When the synthesizer from the OscGenerator has been stopped the recording ends.
     * The recording can be saved as a Wave or an MP3 file
     * @param ocsGen
     * @throws IOException
     */
    public void stopRecording(OscGenerator ocsGen) throws IOException {
        if (!ocsGen.synth.isRunning()) {
            ocsGen.getOsc().stop();
            waveRecorder.stop();
            waveRecorder.close();
            System.out.println("synth stopped, recording ended");

            FileChooser fileChooser = new FileChooser();

            String userDir = System.getProperty("user.home");
            fileChooser.setInitialDirectory(new File(userDir+"/Desktop"));
            fileChooser.getExtensionFilters().addAll(
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
}
