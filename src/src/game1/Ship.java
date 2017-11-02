package game1;

import utilities.SoundManager;
import utilities.Sprite;
import utilities.Vector2D;

import java.awt.*;


import static game1.Constants.DT;
import static game1.Constants.FRAME_HEIGHT;
import static game1.Constants.FRAME_WIDTH;
import static game1.Game.objects;
import static game1.Game.objectsReady;

/**
 * Created by lmmiu on 27/01/2017.
 */


public class Ship extends GameObject {

        //Trust boolean trigger the trusting animation
        public static boolean thrusting = false;

        //Time protection after shield was destroyed
        public int TIME_PROTECTION = 200;

        //Shoot Delay after each rocket
        public int SHOOT_DELAY=0;

        //Bullet in which the bullet object is saved
        public Bullet bullet = null;

        //Boolean to trigger the shield to be painted
        public boolean shield=false;



        // rotation velocity in radians per second
        public static final double STEER_RATE = 2* Math.PI;

        // acceleration when thrust is applied
        public static final double MAG_ACC = 200;


        //Direction vector which says where the ship is pointing
        public Vector2D direction;

        // controller which provides an Action object in each frame
        private Controller ctrl;


    /**
     * Ship default Constructor
     * @param ctrl Controller
     */
    public Ship(Controller ctrl) {
            //GameObject constructor
            super(8, new Vector2D(Constants.FRAME_CENTER), new Vector2D(0,0));

            //controller setter
            this.ctrl = ctrl;

            //ship direction
            direction = new Vector2D(0,-1);
        }

        //override update method for the ship
        public void update() {

            //Destroying the ship if the fuel is 0
            if(Game.hud.getFuel()<=0)
            {
                this.dead=true;
                Game.hud.setLives(Game.hud.getLives()-1);
                objectsReady.add(new Explosion(this.RADIUS,this.position, new Vector2D(0,0)));
            }

            //Creating an action object
            Action action = ctrl.action();

            //Moving the ship according the action
            switch (action.turn)
            {
                case 0:
                    break;
                case 1:
                    this.direction.rotate(1 * DT * STEER_RATE);
                    break;
                case -1:
                    this.direction.rotate(-1* DT * STEER_RATE);
                    break;
            }

            switch(action.thrust)
            {
                case 0:
                    thrusting=false;
                    break;
                case 1:
                    thrusting =true;
                    this.velocity.addScaled(this.direction, MAG_ACC*DT );
            }

            //creating a Bullet object if shooting
            if (action.shoot)
            {
                mkBullet();
                action.shoot=false;
            }

            //GameObject update method
            super.update();

            //Decreasing the Protection Timer
            if(shield)
                TIME_PROTECTION=210;
            else
                TIME_PROTECTION--;

            //Decreasing the Shooting Delay
            if(SHOOT_DELAY!=0)
                SHOOT_DELAY--;

        }

        //method to create a Bullet in front of the ship
        private void mkBullet() {
            Vector2D extra = new Vector2D(Game.UP).rotate(this.direction.angle()).mult(25);
            Vector2D bulletPosition = new Vector2D(position).add(extra);
            Vector2D bulletVelocity = new Vector2D(Game.UP).rotate(this.direction.angle()).mult(Bullet.BULLET_SPEED);
            if(SHOOT_DELAY==0) {
                bullet = new Bullet(4, bulletPosition, bulletVelocity);

                if (Game.ctrl.action.sounds)
                    SoundManager.fire();
                SHOOT_DELAY=10;
            }

         }




        //Painting the ship
        public void draw(Graphics2D g) {

            //Painting the ship sprite
            Sprite sprite = new Sprite(Constants.SHIP, this.position, this.direction, 2 * RADIUS+25, 2 * RADIUS+25);
            sprite.draw(g);

            //Painting the shield when shield got hit
            if(TIME_PROTECTION>0 && TIME_PROTECTION%20!=0)
            {
                Sprite shield = new Sprite(Constants.SHIELD, this.position, this.direction, 2 * RADIUS+32, 2 * RADIUS+32);
                shield.draw(g);
            }

            //Painting the trust animation
            if (thrusting) {
                Sprite prop = new Sprite(Constants.PROPULSION,  new Vector2D(position).add(new Vector2D(Game.UP).rotate(this.direction.angle()).mult(-25)),
                        this.direction, 2 * RADIUS+15, 2 * RADIUS);
                prop.draw(g);

                //Decreasing fuel while thrusting
                Game.hud.setFuel(Game.hud.getFuel()-1);

            }

        }

    @Override
    public String getName(){
        super.getName();
        return"Ship";
    }


    //Overriding the hit method
    @Override
    public void hit() {
        super.hit();
        objects.remove(this);
        objects.add(new Explosion(this.RADIUS, this.position,new Vector2D(0,0)));
    }
}

