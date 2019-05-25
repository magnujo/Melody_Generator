package com.company;

import com.jsyn.util.WaveRecorder;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

public class SampleRecorder {
    //file to save sample to
    File wavFile;
    //temporary file to write sample to
    File tempFile = new File("./data/tempWave.wav");
    WaveRecorder waveRecorder;

    /**
     * Connects a JSyn waveRecorder to the oscillator from a SoundClass
     * @param sound the SoundClass to be recorded from
     * @throws IOException
     */
    public void initRecording(SoundClass sound) throws IOException {

        waveRecorder = new WaveRecorder(sound.synth, tempFile);
        //waveRecorder gets the output from the synthesizer
        sound.getOsc().getOutput().connect(0,waveRecorder.getInput(),0);
        sound.getOsc().getOutput().connect(0,waveRecorder.getInput(),1);

        System.out.println("Ready to record");

        //if synthesizer is running but not playing from file the frequency is set to zero
        //prevents "hidden" notes from oscillator startup to be included in sample
        if (sound.lineOut.isStartRequired()) {
            sound.getOsc().frequency.set(0);
        }
    }

    /**
     * Starts recording the output from the voice object in SoundClass
     * @param sound SoundClass to be recorded from
     */
    public void startRecording(SoundClass sound) {
        if (sound.lineOutPlaying || sound.synth.isRunning()) {
            waveRecorder.start();
            System.out.println("The sample is being recorded");
        }
    }

    /**
     * Pauses recording if lineOut is not playing
     * Recording can be resumed with startRecording()
     * @param sound
     */
    public void pauseRecording(SoundClass sound) {
        if (!sound.lineOutPlaying) {
            waveRecorder.stop();
        }
    }

    /**
     * Stops recording if lineOut is no longer playing
     * The recording can be saved as a Wave or an MP3 file
     * @param sound
     * @throws IOException
     */
    public void stopRecording(SoundClass sound) throws IOException {
        //if (!sound.synth.isRunning()) {
        if (!sound.lineOutPlaying) {
            sound.getOsc().stop();
            waveRecorder.stop();
            waveRecorder.close();
            System.out.println("Recording ended");

            //filechooser for save function
            FileChooser fileChooser = new FileChooser();
            try { //locate the users desktop
                String userDir = System.getProperty("user.home");
                fileChooser.setInitialDirectory(new File(userDir + "/Desktop"));
            } catch (IllegalArgumentException e) {
                //else catch exception and direct to hard drive
                System.out.println("Could not find Desktop folder");
            }
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Wave", "*.wav"),
                    new FileChooser.ExtensionFilter("MP3", "*.mp3")
            );
            wavFile = fileChooser.showSaveDialog(new Stage());

            if (tempFile != null) {
                try {
                    System.out.println("Recording saved as: " + wavFile.getName());
                    System.out.println("Recording saved to: " + wavFile.getAbsolutePath());

                    //file with sample renamed to file with user's saved path
                    tempFile.renameTo(wavFile);

                } catch (NullPointerException e) {
                    System.out.println("The recording was not saved");
                }
            }
        }
    }
}
