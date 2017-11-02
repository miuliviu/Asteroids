package game1;

import utilities.JEasyFrame;
import utilities.SoundManager;
import utilities.Sprite;
import utilities.Vector2D;

import java.awt.*;
import java.util.*;
import java.util.List;

import static game1.Constants.*;

/**
 * Created by lmmiu on 27/01/2017.
 */
public class Game  {
    //Number of initial asteroids
    public static int N_INITIAL_ASTEROIDS = 1;
    //Variable to hold the option setting for asteroids according the difficulty from the Option Menu
    public static int OPTION_INITIAL_ASTEROIDS =1;
    //List to store the Asteroid Objects
    public static List<Asteroid> asteroids;
    //List to store all the objects in the game
    public static List<GameObject> objects;
    //Declaring a Key controller to receive action from keyboard input
    public static Keys ctrl;
    //Declaring ship for player
    public static Ship ship;
    //Declaring enemy saucer
    public static Saucer saucer;
    //Declaring The hud of the game
    public static Hud hud;
    //Declaring the highscore objects of the game
    public HighScore hScore;
    //Creating a boolean trigger for the game menu
    public static boolean gameMenu=true;
    //Creating a lsit for objects that are ready to be added in the objects list after the current frame update is done
    public static ArrayList<GameObject> objectsReady = new ArrayList<GameObject>();

    //Vector to state the up direction of the game
    public final static Vector2D UP = new Vector2D(1, 0);

    /**
     * Game constructor which prepares and initialises the game
     * @param sendhud Hud from another game object
     */
    public Game(Hud sendhud) {
        //Initialising the GameObjects list
        objects = new ArrayList<GameObject>();
        //Initialising the Asteroid objects list
        asteroids = new ArrayList<Asteroid>();
        //Populating the objects list with the initial number of asteroids
        for (int i = 0; i < N_INITIAL_ASTEROIDS; i++) {
            objects.add(Asteroid.makeRandomAsteroid());
        }

        //Creating an Enemy Saucer Objects
        saucer= new Saucer(new Vector2D(0,(int)(Math.random()*(565-1)*1)),new Vector2D(400,0));
        //Creating a new HighScore Object
        hScore = new HighScore();
        //Initialising the Key Controller
        ctrl = new Keys();
        //Creating a new Ship Object and adding it to the GameObject list
        ship = new Ship(ctrl);
        hud= sendhud;
        objects.add(ship);


    }


    //Main method of the game Class
    //This is where the game is started by creating a game instance and adding it to a new JEasyFrame object
    //Everything is updated and repainted in the infinite while loop
    public static void main(String[] args) throws Exception {
        //creating a new game with default hud stats
        Game game = new Game(new Hud(0,3,1));
        View view = new View(game);
        boolean sh;

        //Creating the Frame and adding the View component + key controller to it
        JEasyFrame win = new JEasyFrame(view, "Asteroids");
        win.addKeyListener(game.ctrl);
        // running the game
        while (true) {
            //updating all the game components
            game.update();
            //repainting all the objects present in the game
            view.repaint();
            //Frame delay
            Thread.sleep(DELAY);
            boolean check = false;
            boolean exCheck = false;

            //Checking if the current level has finished
                for (GameObject o : objects) {
                    if (o.getName().equals("Asteroid"))
                        check = true;
                    if(o.getName().equals("Explosion"))
                        exCheck =true;
                }


                //Restarting the game
                if (ctrl.action.start) {
                    game.hud.setLevel(game.hud.getLevel() + 1);
                    Hud next = new Hud(0, 3, 1);
                    N_INITIAL_ASTEROIDS = OPTION_INITIAL_ASTEROIDS;
                    if(OPTION_INITIAL_ASTEROIDS==3)
                        next.setNuke(1);
                    game = new Game(next);
                    view.setGame(game);
                    win.addKeyListener(game.ctrl);
                }

                //Sending the player to the next level of the game
                if (!check) {
                    if(!exCheck) {
                        sh = false;
                        if (ship.shield)
                            sh = true;
                        game.hud.setLevel(game.hud.getLevel() + 1);
                        Hud next = game.hud;
                        game.hud.setFuel(game.hud.getFuel() + 100 * game.hud.getLevel());
                        N_INITIAL_ASTEROIDS++;
                        game = new Game(next);
                        view.setGame(game);
                        win.addKeyListener(game.ctrl);
                        if (sh)
                            game.ship.shield = true;
                    }
                }

                //Starting the game if the menu was closed (Return key was pressed)
                 if(!gameMenu && ctrl.action.meniuClose)
                {
                    game = new Game(new Hud(0,3,1));
                    view.setGame(game);
                    win.addKeyListener(game.ctrl);
                    OPTION_INITIAL_ASTEROIDS=N_INITIAL_ASTEROIDS;
                    hud.setWeapon(1);
                    ctrl.action.meniuClose = false;
                    if(OPTION_INITIAL_ASTEROIDS==3)
                        game.hud.setNuke(1);
                }

                //in case nuke stat goes below zero set it to 0
                if(game.hud.getNuke()<0)
                    game.hud.setNuke(0);




        }
    }

    //Update method which updates all the object in the game synchronized
    public void update() {
        //Check tos ee if the game started
        if(!gameMenu) {

            //List to add the dead objects before deleting them to avoid Concurrency exception
            List<GameObject> killList = new ArrayList<>();

            //Checking collision between all the objects (post)
            for (int i = 0; i < objects.size(); i++) {
                for (int j = i + 1; j < objects.size(); j++) {
                    if(objects.get(i).dead!=true && objects.get(j).dead!=true)
                        objects.get(i).collisionHandling(objects.get(j));
                }
            }

            //Updating all the objects and adding the dead ones to the kill list
            synchronized (Game.class) {
                for (GameObject a : objects) {
                    a.update();
                    if (a.dead) {
                        killList.add(a);
                    }
                }
            }

            //Removing all the dead objects from the objects list and adding the ready objects
            synchronized (Game.class) {
                objects.removeAll(killList);
                objects.addAll(objectsReady);
                objectsReady.clear();
            }

            //Weapon Level Shooting System
            //Shooting in 5 different ways according to the weapon level
            synchronized (Game.class) {
                if (ship.bullet != null) {
                    switch (hud.getWeapon()){

                        //Shooting only one bullet from the front of the ship
                        case 1 :
                            objects.add(ship.bullet);
                            break;
                        //Shooting two rockets from the top of the ship with a +10/-10 width offset
                        case 2:
                            Bullet duo1 = new Bullet(4, new Vector2D(ship.bullet.position).add(new Vector2D(0,10).rotate(Game.UP.angle(ship.direction))), new Vector2D(ship.bullet.velocity));
                            Bullet duo2 = new Bullet(4, new Vector2D(ship.bullet.position).add(new Vector2D(0,-10).rotate(Game.UP.angle(ship.direction))), new Vector2D(ship.bullet.velocity));

                            objects.add(duo1);
                            objects.add(duo2);
                            break;
                        //Shooting 3 missiles.
                        //One forward and two with a rotation of +/- 45 degrees rotation offset
                        case 3:
                            objects.add(ship.bullet);
                            Bullet one = new Bullet(4, new Vector2D(ship.bullet.position), new Vector2D(ship.bullet.velocity).rotate(-0.5));
                            Bullet two = new Bullet(4, new Vector2D(ship.bullet.position), new Vector2D(ship.bullet.velocity).rotate(0.5));
                            one.direction.rotate(-0.5);
                            two.direction.rotate(0.5);
                            objects.add(one);
                            objects.add(two);
                            break;

                        //Shooting 4 missiles.
                        //Two with a rotation of +/- 45 degrees rotation offset
                        //Two with a rotation of +/- ~25 degrees rotation offset
                        case 4:
                            Bullet first = new Bullet(4, new Vector2D(ship.bullet.position), new Vector2D(ship.bullet.velocity).rotate(-0.5));
                            Bullet second = new Bullet(4, new Vector2D(ship.bullet.position), new Vector2D(ship.bullet.velocity).rotate(0.5));
                            Bullet third = new Bullet(4, new Vector2D(ship.bullet.position), new Vector2D(ship.bullet.velocity).rotate(-0.25));
                            Bullet fourth = new Bullet(4, new Vector2D(ship.bullet.position), new Vector2D(ship.bullet.velocity).rotate(0.25));
                            first.direction.rotate(-0.5);
                            second.direction.rotate(0.5);
                            third.direction.rotate(-0.25);
                            fourth.direction.rotate(0.25);
                            objects.add(first);
                            objects.add(second);
                            objects.add(third);
                            objects.add(fourth);
                            break;

                        //Shooting 5 missiles
                        //One forward
                        //Two with a rotation of +/- 45 degrees rotation offset
                        //Two with a rotation of +/- ~25 degrees rotation offset
                        case 5:
                            objects.add(ship.bullet);
                            Bullet first5 = new Bullet(4, new Vector2D(ship.bullet.position), new Vector2D(ship.bullet.velocity).rotate(-0.5));
                            Bullet second5 = new Bullet(4, new Vector2D(ship.bullet.position), new Vector2D(ship.bullet.velocity).rotate(0.5));
                            Bullet third5 = new Bullet(4, new Vector2D(ship.bullet.position), new Vector2D(ship.bullet.velocity).rotate(-0.25));
                            Bullet fourth5 = new Bullet(4, new Vector2D(ship.bullet.position), new Vector2D(ship.bullet.velocity).rotate(0.25));
                            first5.direction.rotate(-0.5);
                            second5.direction.rotate(0.5);
                            third5.direction.rotate(-0.25);
                            fourth5.direction.rotate(0.25);
                            objects.add(first5);
                            objects.add(second5);
                            objects.add(third5);
                            objects.add(fourth5);
                            break;
                    }
                    ship.bullet = null;
                }
            }

            //Adding the Enemy Bullet to the Objects list and making the enemy bullet deposit null
            synchronized (Game.class) {
                if (saucer.ebullet != null) {
                    objects.add(saucer.ebullet);
                    saucer.ebullet = null;
                }
            }

            //Respawning the ship with 600 fuel and reducing one luife if the player dies
            synchronized (Game.class) {
                if (!objects.contains(ship) && hud.getLives() > 0) {
                    ship = new Ship(ctrl);
                    if (this.hud.getFuel() < 0)
                        this.hud.setFuel(0);
                    this.hud.setFuel(600);
                    objects.add(ship);
                }
            }

            //Spawning the enemy saucer randomly at 3000 score points
            synchronized (Game.class) {
                if (hud.getScore() % 3000 == 0 && hud.getScore() >= 3000) {
                    if (!objects.contains(saucer)) {
                        saucer = new Saucer(new Vector2D(0, (int) (Math.random() * (565 - 1) * 1)), new Vector2D(200, 0));
                        objects.add(saucer);
                    }
                }
            }

            //Adding a Nuke object to the objects list if the LCTRL key was pressed
            synchronized (Game.class) {
                if (ctrl.action.nukeTrigger) {
                    if(hud.getNuke()>0)
                    {
                        if(ctrl.action.sounds)
                        SoundManager.falling();
                        objects.add(new Nuke(8, new Vector2D(Constants.FRAME_WIDTH / 2, 0), new Vector2D(0, 60)));
                        hud.setNuke(hud.getNuke()-1);
                        ctrl.action.nukeTrigger = false;
                    }else{
                        ctrl.action.nukeTrigger = false;
                    }
                }
            }

            //Exploding the nuke, adding the explosion animation to the middle of the screen and killing all the asteroids in the objects list sending the player to the next level
            synchronized (Game.class) {
                if (ctrl.action.nukeBoom) {
                    if(Game.ctrl.action.sounds)
                    SoundManager.stopFalling();
                    if(Game.ctrl.action.sounds)
                    SoundManager.atomic();
                    objects.add(new Explosion(800,new Vector2D(FRAME_WIDTH/2,FRAME_HEIGHT/2),new Vector2D(0,0)));
                    for (int i = 0; i < objects.size(); i++) {
                        if (objects.get(i).getName().equals("Asteroid")) {
                            objects.remove(i);
                            i--;
                        }
                    }
                    if(hud.getNuke()<0)
                        hud.setNuke(0);

                    ctrl.action.nukeBoom = false;
                }
            }

        }
        }



    }


