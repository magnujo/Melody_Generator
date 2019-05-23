package com.company;

import com.jsyn.util.WaveRecorder;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

public class SampleRecorder {

    File wavFile;
    File tempFile = new File("./data/tempWave.wav");
    WaveRecorder waveRecorder;

    /**
     * Connects a JSyn waveRecorder to the oscillator from a SoundClass
     * @param oscGen the SoundClass to be recorded from
     * @throws IOException
     */
    public void initRecording(SoundClass oscGen) throws IOException {

        waveRecorder = new WaveRecorder(oscGen.synth, tempFile);
        //waveRecorder gets the output from the synthesizer
        oscGen.voice.getOutput().connect(0,waveRecorder.getInput(),0);
        oscGen.voice.getOutput().connect(0,waveRecorder.getInput(),1);

        System.out.println("ready to record");

        //if synthesizer is running but not playing from file the frequency is set to zero
        if (oscGen.lineOut.isStartRequired()) {
            oscGen.getOsc().frequency.set(0);
        }
    }

    /**
     * Starts recording the output from the voice object in SoundClass
     * @param oscGen SoundClass to be recorded from
     */
    public void startRecording(SoundClass oscGen) {
        //recording is only started if the synthesizer is running
        if (oscGen.synth.isRunning()) {
            waveRecorder.start();
            System.out.println("sample is being recorded");
        }
    }

    /**
     * When the synthesizer from the SoundClass has been stopped the recording ends.
     * The recording can be saved as a Wave or an MP3 file
     * @param ocsGen
     * @throws IOException
     */
    public void stopRecording(SoundClass ocsGen) throws IOException {
        if (!ocsGen.synth.isRunning()) {
            ocsGen.getOsc().stop();
            waveRecorder.stop();
            waveRecorder.close();
            System.out.println("synth stopped, recording ended");

            //filechooser for save function
            FileChooser fileChooser = new FileChooser();

            String userDir = System.getProperty("user.home");
            fileChooser.setInitialDirectory(new File(userDir+"/Desktop"));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Wave", "*.wav"),
                    new FileChooser.ExtensionFilter("MP3", "*.mp3")
            );
            wavFile = fileChooser.showSaveDialog(new Stage());

            if (tempFile != null) {
                try {
                    System.out.println("File saved as: " + wavFile.getName());
                    System.out.println("File saved to: " + wavFile.getAbsolutePath());

                    //file with sample renamed to file with user's saved path
                    tempFile.renameTo(wavFile);

                } catch (NullPointerException e) {
                    System.out.println("file was not saved");
                }
            }
        }
    }
}
