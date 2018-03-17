import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Bullet extends GameObject {
	int speed;
	int roomNumber;
	boolean isGoingRight = true;
	Color darkred = new Color(148, 22, 10);
	int flyspeed;

	public Bullet(int x, int y, int width, int height) {
		super(x, y, width, height);
		speed = 7;

	}

	public void update() {
		super.update();
		if (isGoingRight) {
			flyspeed = speed;
			x += flyspeed;
		} else if (isGoingRight == false) {
			flyspeed = -speed;
			x += flyspeed;
		}

		if (x >= Runner.width) {
			isAlive = false;
		} else if (x <= 0) {
			isAlive = false;
		}
	}

	public void draw(Graphics pvd) {

		if (flyspeed > 0) {
			pvd.drawImage(Sketch.rightbulletImg, x, y, null);
		} else if (flyspeed < 0) {
			pvd.drawImage(Sketch.leftbulletImg, x, y, null);
		}

	}
}
