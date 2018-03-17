import java.awt.Color;
import java.awt.Graphics;

public class Flynn extends GameObject {
	int transpex;
	int transpey;
	int health;
	public int nukeCount;
	public int nukeSuitCount;
	public boolean nukeSuitEquipped;
	int imagewidth;
	int imageheight;
	static int bulletAmmo;

	public Flynn(int x, int y, int width, int height, int flynnhealth, int bulletAmmo) {
		super(x, y, width, height);
		transpex = 5;
		transpex = 5;
		health = flynnhealth;
		Flynn.bulletAmmo = bulletAmmo;
	}

	public void update() {
		super.update();
		width = imagewidth;
		height = imageheight;
		x += transpex;
		y += transpey;
		if (health <= 0) {
			isAlive = false;
		}
	}

	public void draw(Graphics pvd) {
		// pvd.setColor(Color.BLACK);
		//
		// //*
		// pvd.fillRect(x, y, width, height);
		if (transpex > 0) {
			imagewidth = Sketch.spectreright.getWidth();
			imageheight = Sketch.spectreright.getHeight();
			pvd.drawImage(Sketch.spectreright, x, y, imagewidth, imageheight, null);
		} else if (transpex < 0) {
			imagewidth = Sketch.spectreleft.getWidth();
			imageheight = Sketch.spectreleft.getHeight();
			pvd.drawImage(Sketch.spectreleft, x, y, imagewidth, imageheight, null);
		} else {
			imagewidth = Sketch.reaper.getWidth();
			imageheight = Sketch.reaper.getHeight();
			pvd.drawImage(Sketch.reaper, x, y, imagewidth, imageheight, null);
		}
	}
}
