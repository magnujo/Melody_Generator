package com.company;

public class Skala {
    private double rootNote =0; //rootnote
    private double faktor=1.05946;
    private double[] cSkala = {rootNote, rootNote +32.03, rootNote +68, rootNote +87.6, rootNote +130.37, rootNote +178.37, rootNote +231.37, rootNote +261.62, rootNote +325.7, rootNote +397.62, rootNote +436.83, rootNote +522.36};

    public double getRootNote() {
        return rootNote;
    }

    public void transponerHalvTone(double rootNote) {
        this.rootNote = rootNote*faktor;
        this.cSkala = new double[]{this.rootNote, this.rootNote+32.03, this.rootNote+68, this.rootNote+87.6, this.rootNote+130.37, this.rootNote+178.37, this.rootNote+231.37, this.rootNote+261.62, this.rootNote+325.7, this.rootNote+397.62, this.rootNote+436.83, this.rootNote+522.36};
       // this.cSkala = tempSkala;
    }

    public void transponerHelTone(double rootNote) {
        this.rootNote = rootNote*faktor;
    }

    public double[] getcSkala() {
        return cSkala;
    }

    public void setcSkala(double[] cSkala) {
        this.cSkala = cSkala;
    }

}
