package game1;

import utilities.Sprite;
import utilities.Vector2D;

import java.awt.*;

/**
 * Created by lmmiu on 17/03/2017.
 */
public class Explosion extends GameObject {

    //Iterator of the animation ArrayList
    public int Explosion_Iter = 0;
    //Direction Vector
    public Vector2D direction = new Vector2D(0,0);

    /**
     * Default GameObject Constructor
     * @param r Radius
     * @param pos Position
     * @param vel Velocity
     */
    public Explosion( int r,Vector2D pos, Vector2D vel){
        super(r,pos, vel);
    }

    //Method to update the iterator of the explosion
    public void update() {
        super.update();
        Explosion_Iter++;
      if(Explosion_Iter==12)
          this.dead = true;
    }

    //Method to draw each sprite of the explosion on the frame
    public void draw (Graphics2D g)
    {
        if(Explosion_Iter<=11)
        {
        Sprite sprite = new Sprite(Constants.explosion.get(Explosion_Iter), this.position, direction, 2 * RADIUS+40, 2 * RADIUS+40);
        sprite.draw(g);
        }

    }

    public String getName(){
        return "Explosion";
    }
}
