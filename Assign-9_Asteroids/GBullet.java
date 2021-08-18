import acm.graphics.GOval;
import acm.graphics.GRect;

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
public class GBullet extends GOval {

	// Geschwindigkeit
	public double vx = 0;
	public double vy = 0;

	public GBullet(double vvx, double vvy) {
		super(5, 5);
		vx = vvx;
		vy = vvy;
	}

	public void move() {
		this.move(vx, vy);
	}
}