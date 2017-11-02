package game1;

import javax.swing.*;

/**
 * Created by lmmiu on 10/03/2017.
 */
public class Hud {

    //Hud stats which are player stats
    private int score;
    private int lives;
    private int level;
    private int fuel;
    private int nuke;
    private int kills;
    private int weapon;

    /**
     * Hud default Constructor
     * @param score Starting Score
     * @param lives Starting Lives
     * @param level Starting Level
     */
    public Hud(int score, int lives, int level) {
        this.score = score;
        this.lives = lives;
        this.level = level;

        //Making the fuel default 800 units
        fuel=800;

        //Nukes are by default 0. unless the Hard Difficulty is selected
        nuke=0;

        //Weapon default level
        weapon=1;
    }

    //Setter methods for all the player stats
    public void setScore(int score) {
        this.score = score;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public void setWeapon(int weapon) {
        this.weapon = weapon;
    }

    public void setNuke(int nuke) {
        this.nuke = nuke;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }


    //Getter methods for all the player stats
    public int getFuel() { return fuel; }

    public int getWeapon() {
        return weapon;
    }

    public int getLives() {
        return lives;
    }

    public int getScore() { return score; }

    public int getLevel() {
        return level;
    }

    public int getNuke() {
        return nuke;
    }

    public int getKills() {
        return kills;
    }

    //Methods to return the stats toString

    public String getTextScore() {
        return "Score:"+score;
    }

    public String getTextLives(){
        return "Lives:"+lives;
    }

    public String getTextLevel(){
        return "Level:"+level;
    }

    public String getTextFuel(){return "Fuel:"+fuel;}

    public String getTextNuke(){return  "Nuke:"+nuke;}





    @Override
    public String toString() {
        return "Hud{" +
                "score=" + score +
                ", lives=" + lives +
                ", level=" + level +
                ", fuel=" + fuel +
                ", nuke=" + nuke +
                ", kills=" + kills +
                '}';
    }
}
