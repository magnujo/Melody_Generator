package com.company;
import java.io.*;
import java.util.regex.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class fileReader {
    public static void main(String[] args) {
        // Create an instance of File for data.txt file.
        File file = new File("C:\\Users\\Gem\\Documents\\GitHub\\Osc_Objects_2\\.idea\\data");
        try {

            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String line = scanner.nextLine();

                Pattern ag = Pattern.compile("[a-g]");   // the pattern to search for
                Matcher m = ag.matcher(line);

                if (m.find())
                    System.out.println("Found a match");
                else
                    System.out.println("Did not find a match");

                Pattern hn = Pattern.compile("[h-n]");   // the pattern to search for
                Matcher m2 = hn.matcher(line);


                if (m.find())
                    System.out.println("Found a match");
                else
                    System.out.println("Did not find a match");


                if (m2.find())
                    System.out.println("Found a match");
                else
                    System.out.println("Did not find a match");


            }



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}