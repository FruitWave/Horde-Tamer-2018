import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Horde extends GameObject {
	Sketch hex;
	int transpex;
	int transpey;
	BufferedImage imagex;
	int speed;
	int deathPotential;
	int health;
	int level;
	int type;
	boolean type2overrideNecessary = false;
	boolean overridefindstated;
	Bullet temp;
	boolean deathlyBulletTooClose;
	boolean bulletbelowSet;
	boolean thebulletbelow;

	public Horde(int x, int y, int width, int height, Sketch hex, int health, BufferedImage imagex) {

		// *
		super(x, y, width, height);
		transpex = 0;
		transpey = 0;
		this.hex = hex;
		this.imagex = imagex;
		this.speed = 1;
		deathPotential = 1;
		this.health = health;
		level = 1;
		overridefindstated = false;
		deathlyBulletTooClose = false;
		type = typeSetter();
		boolean bulletbelowSet = false;
	}

	public void update() {
		super.update();
		// updateall();
		if (type == 1) {
			System.out.println("update type 1 reached");
			update1();
		} else if (type == 2) {
			System.out.println("update type 2 reached");
			update2();
			if (type2overrideNecessary) {
				override();
			} else {
				updateall();
			}
		}
	}

	private void updateall() {
		if (hex.flynn.x > x) {
			transpex = speed;
		} else if (hex.flynn.x < x) {
			transpex = -speed;
		}
		if (hex.flynn.y > y) {
			transpey = speed;
		} else if (hex.flynn.y < y) {
			transpey = -speed;
		}
		x += transpex;
		y += transpey;
		// fix shaking
	}

	private void update1() {
		updateall();
	}

	private void update2() {
		for (int i = 0; i < hex.masterComm.objects.size(); i++) {
			GameObject o1 = hex.masterComm.objects.get(i);
			if (o1 instanceof Bullet) {
				temp = (Bullet) o1;
				overridefindstated = false;
				// // if bullet is going right and horde is on bullet's right
				//
				if ((temp.y > y) && (temp.y < (y + height))) {
					// if bullet's y is anywhere where it can hit the horde without moving it's y
					// i dont know how to do complex ?/: variable functions, so I'm just going to do
					// some longer code instead
					type2overrideNecessary = (((temp.x < x) && (temp.isGoingRight))
							|| ((temp.x > x) && (!temp.isGoingRight))) ? true : false;
					if (!overridefindstated) {
						System.out.println("in bullet path is " + type2overrideNecessary);
						overridefindstated = true;
					}
				}
			}
		}
	}

	private void override() {
		// This is bullet avoidance code. It is run whenever a type 2 horde is in the
		// path of a bullet.
		System.out.println("OVERRIDE REACHED");
		if (!bulletbelowSet) {
			System.out.println("SET BULLET BELOW REACHED");
			thebulletbelow = (temp.y > (y + height / 2)) ? true : false;
			if (temp.y == y + height / 2) {
				thebulletbelow = true;
			}
			bulletbelowSet = true;
		}
		if (thebulletbelow && ((temp.y - y) < height + 1)) {
			deathlyBulletTooClose = true;
			System.out.println("set deadlybullettooclose REACHED");
		} else if (!thebulletbelow && ((y - temp.y) < height + 1)) {
			deathlyBulletTooClose = true;
			System.out.println("set deadlybullettooclose REACHED");
		}
		if (deathlyBulletTooClose) {
			y += thebulletbelow ? -transpey : transpey;
			System.out.println("DODGE CODE REACHED");
		}
		if (deathlyBulletTooClose) {
			// check if still within deadly range, then if not, run updateall()
			System.out.println("DODGE CODE REACHED");
		}
	}

	public int typeSetter() {
		int randEnterprises = new Random().nextInt(2);
		return randEnterprises + 1;
	}

	public void draw(Graphics pvd) {
		pvd.drawImage(imagex, x, y, width, height, null);
		// pvd.setColor(color);
		// pvd.fillRect(x, y, width, height);
	}

}