package com.company;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.*;

public class FileReader {
    public ArrayList<Integer> getNoteList() {
        return notelist;
    }

    //stores the notes to be played from a scale
    ArrayList<Integer> notelist = new ArrayList<>();
    //stores the path to the file
    private String path;
    //Stores the textline to be read
    private String textLine = null;
    private int octaves;

    /**
     * This class takes a textfile and turns it into numbers in an arraylist that are supposed to represent notes on a scale.
     * @param path
     * tha path to the file we need to parse
     */
    public FileReader(String path) {
        this.path = path;
        generateNoteList();
        this.octaves = octaves;
    }

    private void generateNoteList() {

        try {
            // FileReader reads text files in the default encoding.
            java.io.FileReader fileReader =
                    new java.io.FileReader(path );

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while((textLine = bufferedReader.readLine()) != null) {


                char tempChar;
                for (int i = 0; i < textLine.length(  ); i++) {


                    if (octaves < 14) {

                        tempChar = textLine.toLowerCase().charAt(i);
                        String b = new StringBuilder().append(tempChar).toString();

                        Pattern ae = Pattern.compile("[a-d]");   // the pattern to search for
                        Matcher m = ae.matcher(b);

                        Pattern hn = Pattern.compile("[e-g]");   // the pattern to search for
                        Matcher m2 = hn.matcher(b);

                        Pattern ko = Pattern.compile("[h-j]");   // the pattern to search for
                        Matcher m3 = ko.matcher(b);

                        Pattern pt = Pattern.compile("[k-m]");   // the pattern to search for
                        Matcher m4 = pt.matcher(b);

                        Pattern uz = Pattern.compile("[n-p]");   // the pattern to search for
                        Matcher m5 = uz.matcher(b);

                        Pattern qr = Pattern.compile("[q-r]");   // the pattern to search for
                        Matcher m6 = qr.matcher(b);

                        Pattern su = Pattern.compile("[s-u]");   // the pattern to search for
                        Matcher m7 = su.matcher(b);

                        Pattern vz = Pattern.compile("[v-å]");   // the pattern to search for
                        Matcher m8 = vz.matcher(b);


                        if (m.find()) {
                            // a to e value
                            notelist.add(0);
                        }
                        if (m2.find()) {
                            notelist.add(1);

                        }
                        if (m3.find()) {
                            notelist.add(2);

                        }
                        if (m4.find()) {
                            notelist.add(3);

                        }
                        if (m5.find()) {
                            notelist.add(4);
                        }
                        if (m6.find()) {
                            notelist.add(5);
                        }
                        if (m7.find()) {
                            notelist.add(6);
                        }
                        if (m8.find()) {
                            notelist.add(7);
                        }

                    }

                    if(octaves>14){
                        tempChar = textLine.charAt(i);
                        String b = new StringBuilder().append(tempChar).toString();

                        Pattern ae = Pattern.compile("[a-b]");   // the pattern to search for
                        Matcher m = ae.matcher(b);

                        Pattern hn = Pattern.compile("[c-d]");   // the pattern to search for
                        Matcher m2 = hn.matcher(b);

                        Pattern ko = Pattern.compile("[e-f]");   // the pattern to search for
                        Matcher m3 = ko.matcher(b);

                        Pattern pt = Pattern.compile("[g-h]");   // the pattern to search for
                        Matcher m4 = pt.matcher(b);

                        Pattern uz = Pattern.compile("[i-j]");   // the pattern to search for
                        Matcher m5 = uz.matcher(b);

                        Pattern qr = Pattern.compile("[k-l]");   // the pattern to search for
                        Matcher m6 = qr.matcher(b);

                        Pattern su = Pattern.compile("[m-n]");   // the pattern to search for
                        Matcher m7 = su.matcher(b);

                        Pattern vz = Pattern.compile("[o-p]");   // the pattern to search for
                        Matcher m8 = vz.matcher(b);

                        Pattern qrr = Pattern.compile("[q-r]");   // the pattern to search for
                        Matcher m9 = qrr.matcher(b);

                        Pattern st = Pattern.compile("[s-t]");   // the pattern to search for
                        Matcher m10 = st.matcher(b);

                        Pattern uv = Pattern.compile("[u-v]");   // the pattern to search for
                        Matcher m11 = uv.matcher(b);

                        Pattern xz = Pattern.compile("[x-z]");   // the pattern to search for
                        Matcher m12 = xz.matcher(b);

                        Pattern $å = Pattern.compile("[$-å]");   // the pattern to search for
                        Matcher m13 = $å.matcher(b);



                        if (m.find()) {
                            // a to e value
                            notelist.add(0);
                        }
                        if (m2.find()) {
                            notelist.add(1);

                        }
                        if (m3.find()) {
                            notelist.add(2);

                        }
                        if (m4.find()) {
                            notelist.add(3);

                        }
                        if (m5.find()) {
                            notelist.add(4);
                        }
                        if (m6.find()) {
                            notelist.add(5);
                        }
                        if (m7.find()) {
                            notelist.add(6);
                        }
                        if (m8.find()) {
                            notelist.add(7);
                        }
                        if (m9.find()) {
                            notelist.add(8);

                        }
                        if (m10.find()) {
                            notelist.add(9);

                        }
                        if (m11.find()) {
                            notelist.add(10);
                        }
                        if (m12.find()) {
                            notelist.add(11);
                        }
                        if (m13.find()) {
                            notelist.add(12);
                        }

                    }

                }

            }
            System.out.println("notelist size: "+ notelist.size());

        } catch (IOException e) {
            System.out.println("The file was not read correctly.");
        }

   }


}