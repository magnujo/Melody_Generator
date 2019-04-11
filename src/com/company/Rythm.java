package com.company;

import java.util.Scanner;

    public class Rythm {

        static OscGenerator osc = new OscGenerator();

        public static void main(String[] args) {

            Scanner scanner = new Scanner(System.in);
            int bpm = 120;

            boolean temponotchosen = true;
            String userinput;
            String quarternote = "1/4";
            String stopplaying = "stop";

            System.out.println("BPM set to: " + bpm);
            System.out.println("Enter a note value: 1/4, 1/8, 1/16");

            while (temponotchosen) {
                userinput = scanner.nextLine();
                if (userinput.equals(quarternote)) {
                    osc.PlaySine(400);
                }else if(userinput.equals(stopplaying)){
                    osc.sineLineOut.stop();
                    break;
                }
            }
        }
    }

