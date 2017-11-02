package game1;

/**
 * Created by lmmiu on 27/01/2017.
 */
public class Action {
    public int thrust; // 0 = off, 1 = on
    public int turn; // -1 = left turn, 0 = no turn, 1 = right turn
    public boolean shoot;// Trigger to shoot
    public boolean start; //Starts the game
    public boolean viewscore; //Opens highscore
    public boolean options; // Starts the game from the menu
    public boolean meniuClose;//Trigger to close the menu
    public static boolean sounds = true; //Sound Option
    public boolean nukeTrigger; //Triggers nuke
    public boolean nukeBoom; //explode bomb
    public int upgrade;//Trigger to drop an Upgrade Crate
    public boolean droped=false;//Trigger that checks if a Crate is dropped
}