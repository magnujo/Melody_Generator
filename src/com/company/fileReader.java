package com.company;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.*;
import java.io.File;
import java.io.FileNotFoundException;

public class fileReader {
    ArrayList<Integer> playlist = new ArrayList<>();
    int notePlayed=0;
    private String path;
    String aline = null;


    public fileReader(String path) {
        this.path = path;
    }


    public ArrayList<Integer> playNote() {

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(path);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            aline = bufferedReader.readLine();

            char[] charInput;
            charInput = bufferedReader.readLine().toCharArray();
            //charInput = in.nextLine().toCharArray();

            String[] letterString = {"a","b","c","d","e","h","i","j","k","l","m","n",
                    "o","p","q","s","t","u","v","w","x","y","z","æ","ø","å"};

            char[] letters = new char[letterString.length];

            for (int i = 0; i < letterString.length; i++) {
                letters = letterString[i].toCharArray();
            }

            //while((aline = bufferedReader.readLine()) != null) {
            //while(charInput != null); {

            for (int i = 0; i < charInput.length; i++) {

                if (charInput[i]==letters[0]||charInput[i]==letters[9]) {
                    playlist.add(1);
                }
                if (charInput[i]==letters[1]||charInput[i]==letters[10]) {
                    playlist.add(2);
                }

                Pattern chars_ad = Pattern.compile("[a-d]");   // the pattern to search for
                Matcher m = chars_ad.matcher(aline);
                if (m.find()) {
                    playlist.add(1);
                }

                Pattern chars_eg = Pattern.compile("[e-g]");   // the pattern to search for
                Matcher m2 = chars_eg.matcher(aline);
                if (m2.find()) {
                    playlist.add(2);
                }

                Pattern char_hj = Pattern.compile("[h-j]");   // the pattern to search for
                Matcher m3 = char_hj.matcher(aline);
                if (m3.find()) {
                    playlist.add(3);
                }

                Pattern char_km = Pattern.compile("[k-m]");   // the pattern to search for
                Matcher m4 = char_km.matcher(aline);
                if (m4.find()) {
                    playlist.add(4);
                }

                Pattern char_np = Pattern.compile("[n-p]");   // the pattern to search for
                Matcher m5 = char_np.matcher(aline);
                if (m5.find()) {
                    playlist.add(5);
                }

                Pattern char_gr = Pattern.compile("[q-r]");   // the pattern to search for
                Matcher m6 = char_gr.matcher(aline);
                if (m6.find()) {
                    playlist.add(6);
                }

                Pattern char_su = Pattern.compile("[s-u]");   // the pattern to search for
                Matcher m7 = char_su.matcher(aline);
                if (m7.find()) {
                    playlist.add(7);
                }

                Pattern char_vx = Pattern.compile("[v-x]");   // the pattern to search for
                Matcher m8 = char_vx.matcher(aline);
                if (m8.find()) {
                    playlist.add(8);
                }

                Pattern char_zå = Pattern.compile("[z-å]");   // the pattern to search for
                Matcher m9 = char_zå.matcher(aline);
                if (m9.find()) {
                    playlist.add(0);
                }

            }
        } catch (IOException e) {
            System.out.println("The file was not read correctly.");
        }

        return playlist;
   }

    public static void main(String[] args) {


        //fR.playnote();
        //System.out.println(fR.notePlayed);

            fileReader fR = new fileReader(".idea/data");
            System.out.println(fR.playNote());

    }
}