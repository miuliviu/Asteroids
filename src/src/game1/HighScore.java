package game1;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Liviu on 11-Mar-17.
 */

/*
* Class which creates a .dat file and saves the High Score list in it.
* It has methods to load save and update the file, but also create it.
* */
public class HighScore {

    //Array to save the High Score values
    private ArrayList <Integer> hscore;

    //File Name
    private static final String HSFILE = "highscore.dat";

    //Output stream to output the array to file
    ObjectOutputStream outputStream = null;

    //Input stream to read the array from file
    ObjectInputStream inputStream = null;


    /**
     * HighScore default constructor
     */
    public HighScore(){
        hscore = new ArrayList<Integer>();
    }

    //Getter for hscore ArrayList
    public ArrayList<Integer> getHscore() {
        return hscore;
    }

    //Method to read the array from file
    public void loadFile() {
        try {
            inputStream = new ObjectInputStream(new FileInputStream(HSFILE));
            hscore = (ArrayList<Integer>) inputStream.readObject();

            //Handling errors
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("CNF Error: " + e.getMessage());
        } finally {
            try {

                //flushing and closing the file
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                System.out.println("IO Error: " + e.getMessage());
            }
        }
    }

    //Method to save the array to the file
    public void updateScoreFile() {
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(HSFILE));
            outputStream.writeObject(hscore);

            //Handling errors
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e.getMessage() + ",the program will try and make a new file");
        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
        } finally {
            try {

                //Flushing and closing the file
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    //Method to add score in the Array, sort it and saving it to the file
    public void addScore(int score) {
        loadFile();
        hscore.add(score);
        Collections.sort(hscore ,Collections.reverseOrder());
        updateScoreFile();
    }
}
