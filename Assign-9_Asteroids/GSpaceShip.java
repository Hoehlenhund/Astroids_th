import acm.graphics.GPoint;
import acm.graphics.GPolygon;

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
public class GSpaceShip extends GPolygon {
	public final int SPACE_SHIP_SIZE = 40;
	public int angle = 0;
	public double vx = 1;
	public double vy = 1;

	public GSpaceShip(int SPACE_SHIP_SIZE) {
		super();

		addVertex(0, -SPACE_SHIP_SIZE);
		addVertex(-2 * SPACE_SHIP_SIZE / 3, SPACE_SHIP_SIZE);
		addVertex(0, SPACE_SHIP_SIZE / 2);
		addVertex(2 * SPACE_SHIP_SIZE / 3, SPACE_SHIP_SIZE);
	}

	public void rotate(double angle) {
		super.rotate(angle);
		this.angle += angle;
	}

	public void move() {
		this.move(vx, vy);
	}
}