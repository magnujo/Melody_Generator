package com.company;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
                    new FileReader(path );

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while((aline = bufferedReader.readLine()) != null) {

                Pattern ae = Pattern.compile("[a-e]");   // the pattern to search for
                Matcher m = ae.matcher(aline);

                Pattern hn = Pattern.compile("[f-j]");   // the pattern to search for
                Matcher m2 = hn.matcher(aline);

                Pattern ko = Pattern.compile("[k-o]");   // the pattern to search for
                Matcher m3 = ko.matcher(aline);

                Pattern pt = Pattern.compile("[p-t]");   // the pattern to search for
                Matcher m4 = pt.matcher(aline);

                Pattern uz = Pattern.compile("[u-z]");   // the pattern to search for
                Matcher m5 = uz.matcher(aline);

                if (m.find()) {
                    // a to e value
                    playlist.add(1);
                }
                if (m2.find()) {
                    // System.out.println("found 2");
                    playlist.add(2);

                }
                if (m3.find()) {
                    //   System.out.println("found 3");
                    playlist.add(3);

                }
                if (m4.find()) {
                    // System.out.println("found 4");
                    playlist.add(4);

                }
                if (m5.find()) {
                    // System.out.println("found 5");
                    playlist.add(5);
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