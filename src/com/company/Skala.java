package com.company;

public class Skala {
    private double a=0;
    private double halvtoneFaktor = 1.05946;
    private double heltonefaktor = 1.05946*1.05946;

    public double getA() {
        return a;
    }

    public void transponerHalvTone(double a) {
        this.a = a* halvtoneFaktor;

        double[] tempSkala = {a, a+32.03, a+68, a+87.6, a+130.37, a+178.37, a+231.37, a+261.62, a+325.7, a+397.62, a+436.83, a+522.36};
        this.cSkala = tempSkala;
    }

    public void transponerHelTone(double a) {
        this.a = a* halvtoneFaktor;
    }



    public double[] getcSkala() {
        return cSkala;
    }

    public void setcSkala(double[] cSkala) {
        this.cSkala = cSkala;
    }



    private double[] cSkala = {a, a+32.03, a+68, a+87.6, a+130.37, a+178.37, a+231.37, a+261.62, a+325.7, a+397.62, a+436.83, a+522.36};



}
