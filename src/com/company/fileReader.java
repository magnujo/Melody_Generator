package com.company;
import java.io.*;
import java.util.regex.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class fileReader {
    int notePlayed=0;
    private String path;

    private fileReader(String path) {
        this.path = path;
    }


    public static void main(String[] args) {
        // Create an instance of File for data.txt file.
        fr("C:\\Users\\Gem\\Documents\\GitHub\\Osc_Objects_2\\.idea\\data");
    }

    private static void fr(String path) {
        File file = new File(path);

        try {

            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String line = scanner.nextLine();

                Pattern ae = Pattern.compile("[a-e]");   // the pattern to search for
                Matcher m = ae.matcher(line);

                Pattern hn = Pattern.compile("[f-j]");   // the pattern to search for
                Matcher m2 = hn.matcher(line);


                Pattern ko = Pattern.compile("[k-o]");   // the pattern to search for
                Matcher m3 = ko.matcher(line);

                Pattern pt = Pattern.compile("[p-t]");   // the pattern to search for
                Matcher m4 = pt.matcher(line);

                Pattern uz = Pattern.compile("[u-z]");   // the pattern to search for
                Matcher m5 = uz.matcher(line);




                if (m.find()) {
                    System.out.println("found 1");
                }
                if (m2.find()) {
                    System.out.println("found 2");

                }
                if (m3.find()) {
                    System.out.println("found 3");

                }
                if (m4.find()) {
                    System.out.println("found 4");

                }
                if (m5.find()) {
                    System.out.println("found 5");

                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}