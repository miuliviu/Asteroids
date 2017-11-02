package game1;

import utilities.SoundManager;
import utilities.Vector2D;

import java.awt.*;
import java.util.Random;

import static game1.Constants.DT;
import static game1.Constants.FRAME_HEIGHT;
import static game1.Constants.FRAME_WIDTH;

/**
 * Created by lmmiu on 03/02/2017.
 */
public class GameObject {

    //Radius of the Object
    public int RADIUS;

    //Object State
    public boolean dead;

    //Object Position
    public Vector2D position;

    //Object Velocity
    public Vector2D velocity;


    /**
     * GameObject default Constructor
     * @param r Radius
     * @param p Position
     * @param v Velocity
     */
    public GameObject (int r, Vector2D p, Vector2D v){
        RADIUS=r;
        position=p;
        velocity=v;
    }

    //Method to set the object to dead state
    public void hit(){
        if(Game.ctrl.action.sounds)
            SoundManager.asteroids();
        dead=true;
    }

    //Method which updates the position of the the object
    public void update(){
        position.addScaled(velocity, DT);
        position.wrap(FRAME_WIDTH,FRAME_HEIGHT);
    }

    //Method to draw the Object
    public  void draw(Graphics2D g){

    }

    //Method which checks if the summed radius of 2 objects is bigger than the distance between the objects center position
    public boolean overlap(GameObject other){
        return this.RADIUS+other.RADIUS > this.position.dist(other.position);
    }

    //Method to check Collision of all the GameObjects using the overlap method; Collision Handler
    public void collisionHandling(GameObject other) {


        if (this.overlap(other)) {

            //Collision between Asteroid and player Ship
            if(((this.getName().equals("Asteroid") && other.getName().equals("Ship")) || (other.getName().equals("Asteroid") && this.getName().equals("Ship"))) && Game.ship.TIME_PROTECTION<=0) {
                this.hit();
                other.hit();
                Game.hud.setLives(Game.hud.getLives()-1);
            }

            //Collision between Asteroid and Ship when Shield is ON
            if(((this.getName().equals("Asteroid") && other.getName().equals("Ship")) || (other.getName().equals("Asteroid") && this.getName().equals("Ship"))) && Game.ship.shield) {
                if(this.getName().equals("Asteroid"))
                    this.hit();
                else
                    other.hit();
                Game.ship.shield=false;
                Game.hud.setScore(Game.hud.getScore()+100);
            }

            //Collision between player Bullet and Asteroid
            if((this.getName().equals("Bullet") && other.getName().equals("Asteroid")) || (other.getName().equals("Bullet") && this.getName().equals("Asteroid")) ) {
                this.hit();
                other.hit();
                Game.ctrl.action.upgrade = new Random().nextInt(100);
                Game.hud.setScore(Game.hud.getScore()+100);
                if(Game.ctrl.action.sounds)
                    SoundManager.asteroids();
            }

            //Collision between player Bullet and enemy Saucer
            if((this.getName().equals("Bullet") && other.getName().equals("Saucer")) || (other.getName().equals("Bullet") && this.getName().equals("Saucer")) ) {
                this.hit();
                other.hit();
                Game.hud.setScore(Game.hud.getScore()+500);
                Game.ship.shield=true;
                Game.hud.setKills(Game.hud.getKills()+1);
                if(Game.hud.getKills()==5)
                {
                    Game.hud.setNuke(Game.hud.getNuke()+1);
                    Game.hud.setKills(0);
                }
                if(Game.ctrl.action.sounds)
                    SoundManager.asteroids();
            }

            //Collision between enemy bullet EBullet and Player Ship
            if(((this.getName().equals("EBullet") && other.getName().equals("Ship")) || (other.getName().equals("EBullet") && this.getName().equals("Ship"))) && Game.ship.TIME_PROTECTION<=0) {
                this.hit();
                other.hit();
                if(Game.ctrl.action.sounds)
                    SoundManager.asteroids();
                Game.hud.setLives(Game.hud.getLives()-1);
            }

            //Collision between enemy Saucer and Asteroid
            if((this.getName().equals("Saucer") && other.getName().equals("Asteroid")) || (other.getName().equals("Saucer") && this.getName().equals("Asteroid")) ) {
                this.hit();
                other.hit();
                Game.hud.setScore(Game.hud.getScore()+100);
                if(Game.ctrl.action.sounds)
                    SoundManager.asteroids();
            }

            //Collision between enemy Saucer and player Ship
            if(((this.getName().equals("Saucer") && other.getName().equals("Ship")) || (other.getName().equals("Saucer") && this.getName().equals("Ship"))) && Game.ship.TIME_PROTECTION<=0) {
                this.hit();
                other.hit();
                Game.hud.setScore(Game.hud.getScore()+100);
                Game.hud.setLives(Game.hud.getLives()-1);
            }

            //Collision between enemy Saucer and player Ship while Shield is On
            if(((this.getName().equals("Saucer") && other.getName().equals("Ship")) || (other.getName().equals("Saucer") && this.getName().equals("Ship"))) && Game.ship.shield) {
                if(this.getName().equals("Saucer"))
                    this.hit();
                else
                    other.hit();
                Game.ship.shield=false;
                Game.hud.setScore(Game.hud.getScore()+500);
            }

            //Collision between Enemy Bullet and player Ship when shield is on
            if(((this.getName().equals("EBullet") && other.getName().equals("Ship")) || (other.getName().equals("EBullet") && this.getName().equals("Ship"))) && Game.ship.shield) {
                if(this.getName().equals("EBullet"))
                    this.hit();
                else
                    other.hit();
                Game.ship.shield=false;
            }

            //Collision between player Ship and the Upgrade Crate
            if((this.getName().equals("Upgrade") && other.getName().equals("Ship")) || (other.getName().equals("Upgrade") && this.getName().equals("Ship"))) {
                Upgrade vers;
                if(this.getName().equals("Upgrade")) {
                    this.hit();
                   vers = (Upgrade) this;
                }
                else {
                    other.hit();
                    vers = (Upgrade) other;
                }


                //Upgrading the weapon if Upgrade type is 1 or the lives if the upgrade type is 2
                if(vers.type==1) {
                    if (Game.hud.getWeapon() < 5)
                        Game.hud.setWeapon(Game.hud.getWeapon() + 1);
                    else
                        Game.hud.setScore(Game.hud.getScore() + 500);
                }else{
                    if (Game.hud.getLives() < 5)
                        Game.hud.setLives(Game.hud.getLives() + 1);
                    else
                        Game.hud.setScore(Game.hud.getScore() + 500);
                }

                if (Game.ctrl.action.sounds)
                    SoundManager.upgrade();
                Game.ctrl.action.droped=false;
            }





        }
    }

    //Method to return Name of the Object
    public String getName(){
        return "";
    }

    @Override
    public String toString() {
        return "GameObject{" +
                "RADIUS=" + RADIUS +
                ", dead=" + dead +
                ", position=" + position +
                ", velocity=" + velocity +
                '}';
    }
}
