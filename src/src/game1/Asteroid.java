package game1;

import utilities.Sprite;
import utilities.Vector2D;

import static game1.Constants.DT;
import static game1.Constants.FRAME_HEIGHT;
import static game1.Constants.FRAME_WIDTH;
import static game1.Game.objects;
import static game1.Game.objectsReady;

import java.awt.*;
import java.util.*;

/**
 * Created by lmmiu on 27/01/2017.
 */




public class Asteroid extends GameObject {

    //Minimum radius which says how big the smallest asteroid should be
    public final double MINIMUM_RADIUS = 10;

    //Boolean to check if it was hit by nuke
    public boolean nuke;


    /**
     * Constructor for big Asteroid
     * @param position Position
     * @param velocity Velocity
     */
    public Asteroid(Vector2D position, Vector2D velocity) {
        super(80, position, velocity);
    }

    /**
     * Asteroid constructor with a specified Radius
     * @param IRadius Radius
     * @param position Position
     * @param velocity Velocity
     */
    public Asteroid(int IRadius,Vector2D position, Vector2D velocity) {
        super(IRadius, position, velocity);
    }

    /**
     * Constructor with boolean for nuke hit and radius
     * @param IRadius Radius
     * @param position Position
     * @param velocity Velocity
     * @param nHit Nuke Hit boolean
     */
    public Asteroid(int IRadius,Vector2D position, Vector2D velocity, boolean nHit) {
        super(IRadius, position, velocity);
        nuke=nHit;
    }


    //Method to spawn random asteroids on map
	public static Asteroid makeRandomAsteroid() {

        return new Asteroid(new Vector2D((Math.random()*FRAME_WIDTH),(Math.random()*FRAME_HEIGHT)),new Vector2D((Math.random()*100-50),(Math.random()*100-50)));
    }

    //Update method which updates the position of the Asteroid and checks for nuke explosion
    public void update() {
        super.update();
        if(nuke)
        {
            this.hit();
        }
    }

    //Method to paint Asteroids {Rock/Potato(Level 10)}
    public void draw(Graphics2D g) {

        if(Game.hud.getLevel()<10) {
            Sprite sprite = new Sprite(Constants.ASTEROID1, this.position, this.velocity, 2 * RADIUS, 2 * RADIUS);
            sprite.draw(g);
        }else{
            Sprite sprite = new Sprite(Constants.POTATOASTEROID, this.position, this.velocity, 2 * RADIUS, 2 * RADIUS);
            sprite.draw(g);
        }
    }

    @Override
    public String getName(){
        super.getName();
        return "Asteroid";
    }

    //Overridden hit method to spawn new asteroids an spawn Crates with a specified drop chance
    public void hit(){
        super.hit();
        if(this.RADIUS!=MINIMUM_RADIUS) {

            //Spawning 2 new asteroids
            objectsReady.add(new Asteroid(this.RADIUS / 2, new Vector2D(this.position.x, this.position.y),new Vector2D ((Math.random() * 100 - 50), (Math.random() * 100 - 50)), false));
            objectsReady.add(new Asteroid(this.RADIUS / 2, new Vector2D(this.position.x, this.position.y),new Vector2D ((Math.random() * 100 - 50), (Math.random() * 100 - 50)), false));
        }

        //Dropping Weapon Upgrade Crate with 2 % chance
        if(Game.ctrl.action.upgrade<=2 && Game.ctrl.action.upgrade>0 && !Game.ctrl.action.droped){

            objects.add(new Upgrade(20, this.position,new Vector2D(0,0),1));
            Game.ctrl.action.droped=true;

        }

        //Dropping Lives Upgrade Crates with 1 % drop chance
        if(Game.ctrl.action.upgrade==5 && !Game.ctrl.action.droped){

            objects.add(new Upgrade(20, this.position,new Vector2D(0,0),2));
            Game.ctrl.action.droped=true;

        }

        //Adding the explosion animation on the position of the destroyed asteroid
        objects.add(new Explosion(this.RADIUS, this.position,new Vector2D(0,0)));
    }


}