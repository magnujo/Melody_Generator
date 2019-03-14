package com.company;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.*;

public class OscGenerator {

    LineOut sawLineOut;
    double frequency;
    double amplitude;
    LineOut sineLineOut;
    LineOut squareLineOut;
    LineOut noiseLineOut;

    public void Sine(double frequency, double amplitude) {
        this.frequency = frequency;
        this.amplitude = amplitude;

        Synthesizer synth = JSyn.createSynthesizer();
        UnitOscillator sineOsc = new SineOscillator();
        sineLineOut = new LineOut();
        synth.start();
        synth.add(sineOsc);
        synth.add(sineLineOut);

        sineOsc.frequency.set(frequency);
        sineOsc.amplitude.set(amplitude);
        sineOsc.output.connect(0, sineLineOut.input, 0);
        sineOsc.output.connect(0, sineLineOut.input, 1);
        sineLineOut.start();
        //magnus sucks
    }

    public void Saw(){

        Synthesizer synth = JSyn.createSynthesizer();
        UnitOscillator sawOsc = new SawtoothOscillator();
        sawLineOut = new LineOut();
        synth.start();
        synth.add(sawOsc);
        synth.add(sawLineOut);

        sawOsc.frequency.set(400);
        sawOsc.amplitude.set(0.4);
        sawOsc.output.connect(0, sawLineOut.input, 0);
        sawOsc.output.connect(0, sawLineOut.input, 1);
        sawLineOut.start();
    }

    public void Square() {

        Synthesizer synth = JSyn.createSynthesizer();
        UnitOscillator squareOsc = new SquareOscillator();
        squareLineOut = new LineOut();
        synth.start();
        synth.add(squareOsc);
        synth.add(squareLineOut);

        squareOsc.frequency.set(400);
        squareOsc.amplitude.set(0.4);
        squareOsc.output.connect(0, squareLineOut.input, 0);
        squareOsc.output.connect(0, squareLineOut.input, 1);
        squareLineOut.start();

    }
    public void redNoise() {


        Synthesizer synth = JSyn.createSynthesizer();
        RedNoise noise = new RedNoise();
        noiseLineOut = new LineOut();
        synth.start();
        synth.add(noise);
        synth.add(noiseLineOut);

        noise.frequency.set(3000);
        noise.amplitude.set(0.4);
        noise.output.connect(0, noiseLineOut.input, 0);
        noise.output.connect(0, noiseLineOut.input, 1);
        noiseLineOut.start();

    }

}
