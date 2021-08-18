import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GPoint;
import acm.graphics.GPolygon;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

/**
 * MIT License (http://choosealicense.com/licenses/mit/)
 * 
 * Asteroids: Asteroids
 * 
 * Asteroids is an arcade space shooter game. The player controls a spaceship in
 * an asteroid field. The object of the game is to shoot and destroy asteroids
 * while not colliding with them.
 * 
 * @see http://www.VariationenZumThema.de/
 * @author Eduard Moser, Ralph P. Lano
 */
public class Asteroids extends GraphicsProgram {

	private double SPEED_SHIP = 10;
	GLabel gameOver;
	GLabel lifeLost;

	double ASTEROID_MAX_SPEED = 3;
	// instance variables
	final int LENGTH = 600;
	final int HEIGHT = 400;

	// SpaceShip
	private GSpaceShip spaceShip;
	final int SPACE_SHIP_SIZE = 15;

	// Asteroid
	private GAsteroid[] asteroids;

	// Bullet
	private GBullet bullet;
	final double BULLET_SIZE = 0.1;
	double BULLET_SPEED = 10;

	public void run() {
		// println("run started");
		setup();
		// println("Please click to start");
		waitForClick();
		addKeyListeners();
		while (spaceShip != null) {
			moveAsteroids();
			moveBullet();
			checkForCollision();
			pause(4);
		}
	}

	public void fireBullet() {
		// create bullet
		// println("I'm in fireBullet");
		bullet = new GBullet(BULLET_SIZE, BULLET_SIZE);
		add(bullet, spaceShip.getX(), spaceShip.getY());
		bullet.setColor(Color.RED);
		bullet.setFilled(true);
		// println("Bullet created");

		bullet.vx = -Math.sin(Math.toRadians(spaceShip.angle)) * BULLET_SPEED;
		// println(bullet.vx);
		bullet.vy = -Math.cos(Math.toRadians(spaceShip.angle)) * BULLET_SPEED;
		// println(bullet.vy);
		// println(spaceShip.angle);

	}

	public void moveBullet() {
		// println("I'm in moveBullet");
		if (bullet != null) {
			// println("Bullet shot");
			bullet.move();
			// println(bullet.vx);
			// println(bullet.vy);
			// println("moveBullet END");

			if (bullet.getX() <= 0 || bullet.getX() >= LENGTH || bullet.getY() <= 0 || bullet.getY() >= HEIGHT) {
				remove(bullet);
				bullet = null;
				// println("bullet removed LENGTH");
			}
		}
	}

	public void setup() {
		setSize(LENGTH, HEIGHT);
		setBackground(Color.BLACK);

		// create spaceShip
		spaceShip = new GSpaceShip(SPACE_SHIP_SIZE);
		add(spaceShip, LENGTH / 2, HEIGHT / 2);
		spaceShip.setColor(Color.YELLOW);
		spaceShip.setFilled(true);
		// println("SpaceShip");

		// create asteroids
		asteroids = new GAsteroid[10];
		RandomGenerator rgen = new RandomGenerator();
		for (int i = 0; i < 10; i++) {
			asteroids[i] = new GAsteroid(15, 15);
			asteroids[i].setColor(Color.BLUE);
			add(asteroids[i], rgen.nextInt(0, LENGTH), rgen.nextInt(0, HEIGHT));
			// println("Asteroids");
		}
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case 38: // up
			spaceShip.move();

			// println("Move Forward");
			break;

		case 37: // left
			spaceShip.rotate(10);
			spaceShip.vx = -Math.sin(Math.toRadians(spaceShip.angle)) * SPEED_SHIP;
			spaceShip.vy = -Math.cos(Math.toRadians(spaceShip.angle)) * SPEED_SHIP;
			// println("Left turn");
			break;

		case 39: // right
			spaceShip.rotate(-10);
			spaceShip.vx = -Math.sin(Math.toRadians(spaceShip.angle)) * SPEED_SHIP;
			spaceShip.vy = -Math.cos(Math.toRadians(spaceShip.angle)) * SPEED_SHIP;
			// println("Right turn");
			break;

		case ' ':
			// println(bullet.vx + "Space Case");
			// println("Space");
			if (bullet != null) {
				println("Reload");
			} else {
				fireBullet();
			}
		}
	}

	private void moveAsteroids() {
		// println("asteroids before for");
		for (int i = 0; i < 10; i++) {
			asteroids[i].move();

		}
		for (int i = 0; i < 10; i++) {
			asteroids[i].move();
			pause(10);
			if (asteroids[i].getX() < 0) {
				asteroids[i].setLocation(asteroids[i].getX() + LENGTH, asteroids[i].getY());
				// println("I'm here right side");
			} else if (asteroids[i].getX() > LENGTH) {
				asteroids[i].setLocation(asteroids[i].getX() - LENGTH, asteroids[i].getY());
				// println("I'm here left side");
			} else if (asteroids[i].getY() < 0) {
				asteroids[i].setLocation(asteroids[i].getY() + HEIGHT, asteroids[i].getX());
				// println("I'm here up");
			} else if (asteroids[i].getY() > HEIGHT) {
				asteroids[i].setLocation(asteroids[i].getY() - HEIGHT, asteroids[i].getX());
				// println("I'm here down");
				break;
			}
		}
	}

	private void checkForCollision() {
		checkForCollisionAsteroidsWithWall();
		checkForCollisionBulletWithAsteroid();
		checkForCollisionAsteroidWithSpaceShip();
		checkForCollisionAsteroidsWithWall();
		checkForCollisionSpaceshipWithWall();
	}

	private void checkForCollisionAsteroidWithSpaceShip() {
		double x = spaceShip.getX();
		double y = spaceShip.getY();
		GObject obj = getElementAt(x, y);
		if (obj != spaceShip) {
			removeAll();
			lifeLost = new GLabel("You're destroyed! Click to continue.");
			lifeLost.setFont(new Font("Arial", Font.PLAIN, 16));
			lifeLost.setColor(Color.WHITE);
			add(lifeLost, (LENGTH - lifeLost.getWidth()) / 2, (HEIGHT - lifeLost.getHeight()) / 2);
			waitForClick();
			SPEED_SHIP = 2;
			remove(lifeLost);
			run();
		}
	}

	private void checkForCollisionBulletWithAsteroid() {
		if (bullet != null) {
			double x = bullet.getX();
			double y = bullet.getY();
			GObject obj = getElementAt(x, y);
			if (obj != null && obj != spaceShip) {
				println(obj);
				remove(obj);
				remove(bullet);
				bullet = null;
			}
		}
	}

	private void checkForCollisionSpaceshipWithWall() {
		if (spaceShip.getX() < 0
				|| (spaceShip.getX() > LENGTH || (spaceShip.getY() < 0 || (spaceShip.getY() > HEIGHT)))) {
			// println("Got it");
			removeAll();
			gameOver = new GLabel("You're lost in the space");
			gameOver.setFont(new Font("Arial", Font.PLAIN, 16));
			gameOver.setColor(Color.WHITE);
			add(gameOver, (LENGTH - gameOver.getWidth()) / 2, (HEIGHT - gameOver.getHeight()) / 2);
			waitForClick();
			SPEED_SHIP = 2;
			remove(gameOver);
			run();
		}
	}

	private void checkForCollisionAsteroidsWithWall() {
		for (int i = 0; i < 10; i++) {
			if (asteroids[i].getX() < 0) {
				remove(asteroids[i]);
				// println("removed");
				asteroids[i].setLocation(asteroids[i].getX() + LENGTH, asteroids[i].getY());

				if (asteroids[i].getX() > LENGTH) {
					remove(asteroids[i]);
					// println("removed");
					asteroids[i].setLocation(asteroids[i].getX() - LENGTH, asteroids[i].getY());
				}
			}
		}
	}
}