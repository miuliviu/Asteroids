package utilities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

import utilities.ImageManager;
import utilities.Vector2D;

public class Sprite {

	//Declarations
	public Image image;
	public Vector2D position;
	public Vector2D direction;
	public double width;
	public double height;

	/**
	 * Sprite Constructor
	 * @param image Image to be used
	 * @param s Position
	 * @param direction Direction
	 * @param width Width
	 * @param height Height
	 */
	public Sprite(Image image, Vector2D s, Vector2D direction, double width,
			double height) {
		super();
		this.image = image;
		this.position = s;
		this.direction = direction;
		this.width = width;
		this.height = height;
	}

	//Return radius of sprite
	public double getRadius() {
		return (width + height) / 4.0;
	}

	//Returning a boundary box that can be used for collision detection
	public Rectangle2D getBounds2D() {
		return new Rectangle2D.Double((position.x - width / 2), position.y - height / 2, width,
				height);
	}

	//Method to draw the sprite
	public void draw(Graphics2D g) {
		double imW = image.getWidth(null);
		double imH = image.getHeight(null);
		AffineTransform t = new AffineTransform();
		t.rotate(direction.angle(), 0, 0);
		t.scale(width / imW, height / imH);
		t.translate(-imW / 2.0, -imH / 2.0);
		AffineTransform t0 = g.getTransform();
		g.translate(position.x, position.y);
		g.drawImage(image, t, null);
		g.setTransform(t0);
		g.setColor(Color.RED);
		// g.drawOval((int) (s.x - width / 2.0), (int) (s.y - width / 2.0),
		// (int) width, (int) height);
	}

}
