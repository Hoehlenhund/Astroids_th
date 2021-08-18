import acm.graphics.GRect;
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
 * @author Ralph P. Lano
 */
public class GAsteroid extends GRect {

	// Geschwindigkeit
	public double vx = 0;
	public double vy = 0;

	public GAsteroid(double vvx, double vvy) {
		super(30, 30);
		RandomGenerator rgen = new RandomGenerator();
		vx = rgen.nextDouble(-3, 3);
		vy = rgen.nextDouble(-3, 3);

	}

	public void move() {
		this.move(vx, vy);
	}
}