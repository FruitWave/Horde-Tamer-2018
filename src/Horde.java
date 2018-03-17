import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JOptionPane;

public class Horde extends GameObject {
	Sketch hex;
	int transpex;
	int transpey;
	BufferedImage imagex;
	int speed = 1;
	final int speedchecker = speed;
	int deathPotential;
	int health;
	int level;
	boolean typeIsNormal;
	// boolean overridefindstated;
	Bullet temp;
	boolean dodgeTypeIsUp;
	boolean masterDodge;
	boolean continueAvoidance = true;
	boolean bulletExists = false;

	public Horde(int x, int y, int width, int height, Sketch hex, int health, BufferedImage imagex) {

		// *
		super(x, y, width, height);
		transpex = 0;
		transpey = 0;
		this.hex = hex;
		this.imagex = imagex;
		deathPotential = 1;
		this.health = health;
		level = 1;
		// overridefindstated = false;
		typeIsNormal = typeSetter();
		masterDodge = typeSetter();
		boolean bulletbelowSet = false;
	}

	public void update() {
		super.update();
		if (typeIsNormal) {
			update1();
		} else if (!typeIsNormal) {
			System.out.println("checking bullets");
			bulletExists = checkBullets2();
			System.out.println("running update2");
			update2(bulletExists);
		}
	}

	private void updateall() {
		speed = 1;
		if (speedchecker != speed) {
			JOptionPane.showMessageDialog(null, "SPEED NOT RIGHT");
		}

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
		if (!typeIsNormal) {
			if ((transpey == 0) && (transpex == 0)) {
				JOptionPane.showMessageDialog(null, "NOT MOVING");
			}

		}
	}

	private void update1() {
		updateall();
	}

	private boolean checkBullets2() {
		for (int i = 0; i < hex.masterComm.objects.size(); i++) {
			GameObject o1 = hex.masterComm.objects.get(i);
			if (o1 instanceof Bullet) {
				temp = (Bullet) o1;
				System.out.println("BULLET FOUND");

				// overridefindstated = false;
				// // if bullet is going right and horde is on bullet's right
				//
				return true;
			}
		}
		return false;
	}

	private void update2(boolean bulletexists) {
		if (bulletexists) {

			if (!temp.isAlive) {
				temp = null;
			}
			if (temp != null) {

				if ((temp.y > y) && (temp.y < (y + height))) {
					// if bullet's y is anywhere where it can hit the horde without moving it's y
					// i dont know how to do complex ?/: variable functions, so I'm just going to do
					// some longer code instead
					evasiveAction(masterDodge);
				} else {
					updateall();
				}

			} 
		}
	}

	private void evasiveAction(boolean theDodgeTypeIsUp) {
		y += theDodgeTypeIsUp ? -transpey * 10 : transpey * 10;
		System.out.println("Dodging. Going upwards: " + theDodgeTypeIsUp);
	}

	public boolean typeSetter() {
		int randEnterprises = new Random().nextInt(2);
		boolean wow = randEnterprises == 0 ? true : false;
		return wow;
	}

	public void draw(Graphics pvd) {
		pvd.drawImage(imagex, x, y, width, height, null);
		// pvd.setColor(color);
		// pvd.fillRect(x, y, width, height);
	}

}