import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Room extends GameObject implements ActionListener {
	Sketch hex;

	int roomStuckTime = 0;
	int roomsroomnumber;
	boolean unspawnedhorde;
	Color color;
	// Color darkdarkblue = new Color(0, 0, 50);
	// int randomfunint1 = new Random().nextInt(255);
	// int randomfunint2 = new Random().nextInt(255);
	// int randomfunint3 = new Random().nextInt(255);
	// Color randomfun = new Color(randomfunint1, randomfunint2, randomfunint3);
	// int randomfunint11 = new Random().nextInt(255);
	// int randomfunint22 = new Random().nextInt(255);
	// int randomfunint33 = new Random().nextInt(255);
	// Color randomfun2 = new Color(randomfunint11, randomfunint22, randomfunint33);
	// int randomfunint111 = new Random().nextInt(255);
	// int randomfunint222 = new Random().nextInt(255);
	// int randomfunint333 = new Random().nextInt(255);
	// Color randomfun3 = new Color(randomfunint111, randomfunint222,
	// randomfunint333);
	// int randomfunint1111 = new Random().nextInt(255);
	// int randomfunint2222 = new Random().nextInt(255);
	// int randomfunint3333 = new Random().nextInt(255);
	// Color randomfun4 = new Color(randomfunint1111, randomfunint2222,
	// randomfunint3333);
	// int randomfunint11112 = new Random().nextInt(255);
	// int randomfunint22223 = new Random().nextInt(255);
	// int randomfunint33334 = new Random().nextInt(255);
	// Color randomfun5 = new Color(randomfunint11112, randomfunint22223,
	// randomfunint33334);
	// int randomfunint111123 = new Random().nextInt(255);
	// int randomfunint222234 = new Random().nextInt(255);
	// int randomfunint333345 = new Random().nextInt(255);
	// Color randomfun6 = new Color(randomfunint111123, randomfunint222234,
	// randomfunint333345);
	// int randomfunint1111234 = new Random().nextInt(255);
	// int randomfunint2222345 = new Random().nextInt(255);
	// int randomfunint3333456 = new Random().nextInt(255);
	// Color randomfun7 = new Color(randomfunint1111234, randomfunint2222345,
	// randomfunint3333456);
	// Color a;
	// *
	int level = 1;
	int levelupper = 0;
	int leveluppermultiplier = 1;
	int leveluppermultipliercounter = 0;
	SpawningItem xenomorpheousSubstance;
	int roomImage = 10;
	public static Color randomColorDuplicate;
	public int outsideRandNum = 20;
	Color rfduplicate;
	Color rf2duplicate;
	Color rf3duplicate;
	Color rf4duplicate;
	Color rf5duplicate;
	Color rf6duplicate;
	Color rf7duplicate;
	Color ddbduplicate;

	public Room(int x, int y, int width, int height, int roomsroomnumber, boolean unspawnedhorde, Color color,
			Sketch hex) {
		super(x, y, width, height);
		this.hex = hex;
		this.roomsroomnumber = roomsroomnumber;
		this.unspawnedhorde = unspawnedhorde;
		this.color = color;

	}

	public void update() {
		super.update();

		// moving to the next room right
		if (hex.flynn.x >= Runner.width) {
			roomSwitch(true);
		} else if (hex.flynn.x <= 0) {
			roomSwitch(false);
		}

	}

	private void roomSwitch(boolean isRight) {
		Room r00m;
		int itemwidth = 80;
		int itemheight = 80;
		int randomItemX = new Random().nextInt(Runner.width - (Runner.width / 5) - itemwidth);
		int randomItemY = new Random().nextInt(Runner.height - (Runner.height / 5) - itemheight);
		hex.flynn.x += isRight ? -Runner.width + 5 : Runner.width - 5;
		hex.flynnroomnumber += isRight ? 1 : -1;
		hex.roomcolors.add(color);
		Color a = randomColor();
		// *
		hex.roomColor = a;

		if (hex.masterComm.getRoom(hex.flynnroomnumber) == null) {
			levelupper++;
			if ((levelupper >= level) && (levelupper % 2 == 0)) {
				int apoint = level;
				level += levelupper / 2;
				int bpoint = level;
				System.out.println("Level Upper is: " + levelupper);
				if (apoint != bpoint) {
					// System.out.println(
					// "Level Up! (Now Level " + level + "!). Health is up too!
					// Now at " +
					// hex.flynn.health + ".");
					hex.flynn.health += 5 * level;
					hex.flynn.health += 7 * level;
					JOptionPane.showMessageDialog(null,
							"Level Up! (Now Level " + level + "!). Health and bullets are up too! Now at "
									+ hex.flynn.health + " and " + hex.flynn.bulletAmmo + ", respectively.");

				}
				levelupper = 0;
			}
			r00m = new Room(0, 0, Runner.width, Runner.height, hex.flynnroomnumber, true, a, hex);
			hex.masterComm.addObject(r00m);
			if (isRight) {
				hex.masterComm.addRoom(r00m, true);
				hex.hordeAdder = level;
			} else {
				hex.masterComm.addRoom(r00m, false);
				hex.hordeAdder = level;
			}
			hex.enteredNewRoom(isRight, true);
			xenomorpheousSubstance = new SpawningItem(randomItemX, randomItemY, itemwidth, itemheight, "type unset",
					hex);
			if (xenomorpheousSubstance.x < Runner.width / 5) {
				xenomorpheousSubstance.x += Runner.width / 5;
			} else if (xenomorpheousSubstance.x > Runner.width - (Runner.width / 5)) {
				xenomorpheousSubstance.x -= Runner.width - (Runner.width / 5);
			}
			if (xenomorpheousSubstance.y < Runner.height / 5) {
				xenomorpheousSubstance.y += Runner.height / 5;
			} else if (xenomorpheousSubstance.y > Runner.height - (Runner.height / 5)) {
				xenomorpheousSubstance.y -= Runner.height - (Runner.height / 5);
			}
			hex.masterComm.addObject(xenomorpheousSubstance);
		} else {
			r00m = hex.masterComm.getRoom(hex.flynnroomnumber);
			hex.enteredNewRoom(isRight, false);
		}

		hex.onScreenRoom = r00m;
	}

	public int levelShower() {
		return level;

	}

	public Color randomColor() {
		Color roomColorSetter;
		int randomNum = new Random().nextInt(8);
		outsideRandNum = randomNum;
		switch (randomNum) {
		case 0:
			roomColorSetter = Color.BLACK;
			// roomColorSetter = randomfun;
			// rfduplicate = randomfun;
			break;
		case 1:
			roomColorSetter = Color.CYAN;
			// roomColorSetter = randomfun7;
			// rf7duplicate = randomfun7;
			break;
		case 2:
			roomColorSetter = Color.GREEN;
			// roomColorSetter = randomfun6;
			// rf6duplicate = randomfun6;
			break;
		case 3:
			roomColorSetter = Color.MAGENTA;
			// roomColorSetter = randomfun5;
			// rf5duplicate = randomfun5;
			break;
		case 4:
			roomColorSetter = Color.WHITE;
			// roomColorSetter = darkdarkblue;
			// ddbduplicate = darkdarkblue;
			break;
		case 5:
			roomColorSetter = Color.GRAY;
			// roomColorSetter = randomfun4;
			// rf4duplicate = randomfun4;
			break;
		case 6:
			roomColorSetter = Color.ORANGE;
			// roomColorSetter = randomfun2;
			// rf2duplicate = randomfun2;
			break;
		case 7:
			roomColorSetter = Color.PINK;
			// roomColorSetter = randomfun3;
			// rf3duplicate = randomfun3;
			break;
		default:
			roomColorSetter = Color.yellow;
		}
		// randomColorDuplicate = roomColorSetter;
		return roomColorSetter;
	}

	// *
	public void draw(Graphics g) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ((hex.flynn.x == Runner.width) || (hex.flynn.x == 0)) {
			roomStuckTime++;
			if (roomStuckTime == 2) {
				roomStuckTime = 0;
				hex.flynn.x += Runner.width / 10 + hex.flynn.width;
			} else {
				JOptionPane.showMessageDialog(null, "Moving in: " + (4 - roomStuckTime) + ".");
			}
		}
	}

}
