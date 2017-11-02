package utilities;

import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import java.io.File;

// SoundManager for Asteroids

public class SoundManager {

	static int nBullet = 0;
	static boolean thrusting = false;

	// files path
	final static String path = "resources/sounds/";

	// note: having too many clips open may cause
	// "LineUnavailableException: No Free Voices"
	public final static Clip[] bullets = new Clip[15];

	//Importing the WAV files
	public final static Clip bangMedium = getClip("bangMedium");
	public final static Clip fire = getClip("fire");
	public final static Clip atomic = getClip("atomic");
	public final static Clip falling = getClip("falling");
	public final static Clip upgrade = getClip("upgrade");
	public final static Clip saucerAlarm = getClip("saucerAlarm");
	public final static Clip[] clips = { bangMedium, fire, atomic, falling};
	
 static {
		for (int i = 0; i < bullets.length; i++)
			bullets[i] = getClip("fire");
	}

	// methods which do not modify any fields

	//Method to play the WAV clip
	public static void play(Clip clip) {
		clip.setFramePosition(0);
		clip.start();
	}

	//Method which return a clip with the name provided as parameter
	private static Clip getClip(String filename) {
		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
			AudioInputStream sample = AudioSystem.getAudioInputStream(new File(path
					+ filename + ".wav"));
			clip.open(sample);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clip;
	}

	//Shooting sound
	public static void fire() {
		// fire the n-th bullet and increment the index
		Clip clip = bullets[nBullet];
		clip.setFramePosition(0);
		clip.start();
		nBullet = (nBullet + 1) % bullets.length;

	}

	// Custom methods playing a particular sound
	// Please add your own methods below

	//Starting the Explosion sound
	public static void asteroids() {
		play(bangMedium);
	}
	//Starting the Explosion Sound
	public static void atomic(){
		play(atomic);
	}
	//Starting the Nuke Falling Sound
	public static void falling(){
		play(falling);
	}
	//Playing the Upgrade Crate Sound
	public static void upgrade(){
		play(upgrade);
	}
	//Stopping the Nuke Falling Sound
	public static void stopFalling(){
		falling.stop();
	}

	//Starting  the Enemy Saucer sound;Playing in loop.
	public static void startAlarm() {
		if(!saucerAlarm.isRunning())
			saucerAlarm.start();
	}

	//Stopping the Enemy Saucer sound
	public static void stopAlarm() {

		if(saucerAlarm.isRunning())
			saucerAlarm.stop();
	}

}
