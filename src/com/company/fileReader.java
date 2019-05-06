package com.company;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.*;

public class fileReader {
    public ArrayList<Integer> getPlaylist() {
        return playlist;
    }

    //stores the notes to be played from a scale
    ArrayList<Integer> playlist = new ArrayList<>();
    //stores the path to the file
    private String path;
    //Stores the textline to be read
    private String textLine = null;

    public fileReader(String path) {
        this.path = path;
        generatePlaylist();
    }

    private void generatePlaylist() {

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(path );

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while((textLine = bufferedReader.readLine()) != null) {


                char a;
                for (int i = 0; i < textLine.length(  ); i++) {

                    a = textLine.charAt(i);
                    String b = new StringBuilder().append(a).toString();

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

                    Pattern vz = Pattern.compile("[v-x]");   // the pattern to search for
                    Matcher m8 = vz.matcher(b);

                    Pattern zå = Pattern.compile("[z-å]");   // the pattern to search for
                    Matcher m9 = zå.matcher(b);


                    if (m.find()) {
                        // a to e value
                        playlist.add(0);
                    }
                    if (m2.find()) {
                        playlist.add(1);

                    }
                    if (m3.find()) {
                        playlist.add(2);

                    }
                    if (m4.find()) {
                        playlist.add(3);

                    }
                    if (m5.find()) {
                        playlist.add(4);
                    }
                    if (m6.find()) {
                        playlist.add(5);
                    }
                    if (m7.find()) {
                        playlist.add(6);
                    }
                    if (m8.find()) {
                        playlist.add(7);
                    }
                    if (m9.find()) {
                        playlist.add(8);
                    }
                }

            }
            System.out.println(playlist.size());

        } catch (IOException e) {
            System.out.println("The file was not read correctly.");
        }

   }


}