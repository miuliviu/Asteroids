package game1;

import utilities.Sprite;
import utilities.Vector2D;

import java.awt.*;

/**
 * Created by lmmiu on 17/03/2017.
 */
public class Nuke extends GameObject {

    //Vector to define the direction of the object
    Vector2D direction = new Vector2D(0,1);
    //Vector to define the position of the target in the middle of the frame
    Vector2D targetPos = new Vector2D(Constants.FRAME_WIDTH/2, Constants.FRAME_HEIGHT/2);

    /**
     * Default GameObject Constructor
     * @param r Radius
     * @param p Position
     * @param v Velocity
     */
    public Nuke(int r, Vector2D p, Vector2D v) {
        super(r, p, v);
    }

    //update method for position and time to activate the nuke explosion when it reaches the middle of the frame
    public void update() {
        super.update();
        if(this.position.y>=Constants.FRAME_HEIGHT/2-20)
        {
            this.dead=true;
            Game.ctrl.action.nukeBoom =true;
            this.position.y=0;
        }

    }

    //Method to draw the nuke and the target to the frame
    public void draw (Graphics2D g)
    {

        Sprite spriteTarget = new Sprite(Constants.TARGET, targetPos, direction, 2 * RADIUS+40, 2 * RADIUS+40);
        spriteTarget.draw(g);
        Sprite spriteNuke = new Sprite(Constants.NUKE, this.position, direction, 2 * RADIUS+40, 2 * RADIUS+20);
        spriteNuke.draw(g);


    }

    public String getName(){
        return "Nuke";
    }

    @Override
    public String toString() {
        return "Nuke{" +
                "direction=" + direction +
                ", position.x=" + position.x + ", position.y="+position.y+
                '}';
    }
}
