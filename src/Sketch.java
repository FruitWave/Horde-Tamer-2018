import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Sketch extends JPanel implements ActionListener, KeyListener {
	Color roomColor;

	// *
	ArrayList<Color> roomcolors;

	// *
	Timer gameSpeed;
	Timer roomSwitcherGuard;
	Timer fireTimer;
	final int MENU_STATE = 0;
	final int GAME_STATE = 1;
	final int END_STATE = 2;
	final int INSTRUCTIONS_STATE = 4;
	static int currentState = 4;
	static int casualtyCount;
	Flynn flynn;
	ObjectManager masterComm;
	Room base;
	Room onScreenRoom;
	Font font;
	Font funFont;
	Font statsFont;
	public int hordeAdder = 0;
	public int flynnroomnumber = 0;
	static public int statisticsrectwidth = Runner.width;
	static public int statisticsrectheight = 75;
	// boolean paused = false;
	boolean infoMsgShown;
	static BufferedImage spectreright;
	static BufferedImage spectreleft;
	static BufferedImage menuImg;
	static BufferedImage reaper;
	static BufferedImage leftbulletImg;
	static BufferedImage rightbulletImg;
	static BufferedImage horde1;
	static BufferedImage horde2;
	static BufferedImage horde3;
	static BufferedImage horde4;
	static BufferedImage horde5;
	static BufferedImage horde6;
	static BufferedImage horde7;
	static BufferedImage horde8;
	static BufferedImage horde9;
	static BufferedImage horde10;
	static BufferedImage nukacola;
	static BufferedImage bpGun;
	static BufferedImage healthpackimg;
	static BufferedImage notFound;
	static BufferedImage nukeimg;
	static BufferedImage an8bitfire;
	static BufferedImage an8bitquake;
	static BufferedImage an8bitvault;
	static BufferedImage fireplace;
	static BufferedImage run;
	static BufferedImage instructionsimg;
	String cheatsEnabledBasicAccessPassword = "peppermintHydra";
	String cheatsEnabledAdminAccessPassword = "SSC";
	boolean cheatsBasicAccessGranted = false;
	boolean cheatsAdminAccessGranted = false;
	Song main_title;
	Song game_theme;
	boolean gamesongPlayed = false;
	boolean maintitlePlayed = false;

	public Sketch() {
		gameSpeed = new Timer(1000 / 120, this);
		// font = new Font("Arial", Font.PLAIN, 48);
		// Font funFont = new Font("Comic Sans MS", Font.CENTER_BASELINE, 30);
		casualtyCount = 0;
		font = new Font("Arial", Font.PLAIN, 48);
		funFont = new Font("Comic Sans MS", Font.CENTER_BASELINE, 30);
		statsFont = new Font("Bank Gothic", Font.CENTER_BASELINE, 25);
		try {
			spectreleft = ImageIO.read(this.getClass().getResourceAsStream("spectreleft.png"));
			spectreright = ImageIO.read(this.getClass().getResourceAsStream("spectreright.png"));
			leftbulletImg = ImageIO.read(this.getClass().getResourceAsStream("bulletleft.png"));
			rightbulletImg = ImageIO.read(this.getClass().getResourceAsStream("bulletright.png"));
			reaper = ImageIO.read(this.getClass().getResourceAsStream("reaper.png"));
			menuImg = ImageIO.read(this.getClass().getResourceAsStream("menuImg.jpg"));
			horde1 = ImageIO.read(this.getClass().getResourceAsStream("Zombie1.png"));
			horde2 = ImageIO.read(this.getClass().getResourceAsStream("Zombie2.png"));
			horde3 = ImageIO.read(this.getClass().getResourceAsStream("Zombie3.png"));
			horde4 = ImageIO.read(this.getClass().getResourceAsStream("Zombie4.png"));
			horde5 = ImageIO.read(this.getClass().getResourceAsStream("Zombie5.jpg"));
			horde6 = ImageIO.read(this.getClass().getResourceAsStream("Zombie6.png"));
			horde7 = ImageIO.read(this.getClass().getResourceAsStream("Zombie7.png"));
			horde8 = ImageIO.read(this.getClass().getResourceAsStream("Zombie8.png"));
			horde10 = ImageIO.read(this.getClass().getResourceAsStream("Zombie10.png"));
			horde9 = ImageIO.read(this.getClass().getResourceAsStream("Zombie9.png"));
			nukacola = ImageIO.read(this.getClass().getResourceAsStream("nuka-cola.png"));
			bpGun = ImageIO.read(this.getClass().getResourceAsStream("bpGun.png"));
			healthpackimg = ImageIO.read(this.getClass().getResourceAsStream("healthpack.png"));
			notFound = ImageIO.read(this.getClass().getResourceAsStream("Not-found.jpg"));
			nukeimg = ImageIO.read(this.getClass().getResourceAsStream("atomicbomb.png"));
			an8bitfire = ImageIO.read(this.getClass().getResourceAsStream("8bitfire.png"));
			an8bitquake = ImageIO.read(this.getClass().getResourceAsStream("8bitquake.png"));
			an8bitvault = ImageIO.read(this.getClass().getResourceAsStream("8bitvault.png"));
			run = ImageIO.read(this.getClass().getResourceAsStream("run.png"));
			instructionsimg = ImageIO.read(this.getClass().getResourceAsStream("instructions_img.jpg"));

			fireplace = ImageIO.read(this.getClass().getResourceAsStream("fireplaceYTrim.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void showMessage() {

	}

	public int getcasualtyCount() {
		return casualtyCount;
	}

	public void setcasualtyCount(int s) {
		casualtyCount = s;
	}

	public void showStatistics(Graphics g) {
		g.setColor(Color.WHITE);
		// *
		g.setFont(statsFont);
		g.fillRect(Runner.width - statisticsrectwidth, 0, statisticsrectwidth, statisticsrectheight);
		showcasualtyAndHordeCount(g);
		showRoomNum(g);
		showHealthAndBullets(g);
		showNukeStats(g);
	}

	public void showcasualtyAndHordeCount(Graphics g) {
		int hordecount = 0;
		for (int i = 0; i < masterComm.objects.size(); i++) {
			GameObject o1 = masterComm.objects.get(i);
			if (o1 instanceof Horde) {
				hordecount++;
			}
		}
		g.setColor(Color.BLACK);

		// *
		g.setFont(statsFont);
		String rampage = "Kill Count: " + casualtyCount;
		String rampagers = "Horde Count: " + hordecount;
		g.drawString(rampage, 0, statisticsrectheight / 3);
		g.drawString(rampagers, 0, statisticsrectheight);
	}

	public void showRoomNum(Graphics g) {
		g.setColor(Color.BLACK);

		// *
		g.setFont(statsFont);
		String hotel = "Room " + flynnroomnumber;
		g.drawString(hotel, 0, (statisticsrectheight / 3) * 2);
	}

	public void showHealthAndBullets(Graphics g) {
		g.setColor(Color.BLACK);

		// *
		g.setFont(statsFont);
		String health = "Health: " + flynn.health;
		String bullets = "Bullets: " + flynn.bulletAmmo;
		g.drawString(health, statisticsrectwidth / 4, statisticsrectheight / 3);
		g.drawString(bullets, statisticsrectwidth / 4, (statisticsrectheight / 3) * 2);
	}

	public void showNukeStats(Graphics g) {
		Color evergreen = new Color(89, 229, 10);
		g.setColor(evergreen);

		// *
		g.setFont(statsFont);
		String nuks = "Nuclear Warheads: " + flynn.nukeCount;
		String nuksootz = "Nuka-Cola Suits: " + flynn.nukeSuitCount;
		String nuksootekwippt = "Nuka-Cola Suit Equipped: " + flynn.nukeSuitEquipped;
		g.drawString(nuks, (statisticsrectwidth / 2) + 50, statisticsrectheight / 3);
		g.drawString(nuksootz, (statisticsrectwidth / 2) + 50, (statisticsrectheight / 3) * 2);
		g.drawString(nuksootekwippt, statisticsrectwidth / 4, statisticsrectheight);
	}

	public void enteredNewRoom(boolean isGoingRight, boolean newRoom) {
		int xdisplacement = isGoingRight ? -Runner.width : Runner.width;
		System.out.println("Moving to Room " + flynnroomnumber);
		masterComm.manageEnemiesAndItems(xdisplacement);
		if (newRoom) {
			addToHorde(hordeAdder);
		}
	}

	@SuppressWarnings("static-access")
	public void bulletfired(boolean isDirectedRight) {
		if (flynn.bulletAmmo > 0) {
			flynn.bulletAmmo -= 1;
			System.out.println("Bullets: " + flynn.bulletAmmo);
			int bulletx = isDirectedRight ? flynn.x + flynn.width : flynn.x;
			Bullet bullet = new Bullet(bulletx, flynn.y + (flynn.height / 2), 8, 4);
			bullet.isGoingRight = isDirectedRight ? true : false;
			masterComm.addObject(bullet);
			flynn.transpex = isDirectedRight ? 1 : -1;
		} else {
			JOptionPane.showMessageDialog(null, "You've Run Out Of Bullet Ammo");
		}

	}

	public void addToHorde(int a) {
		for (int i = 0; i < a; i++) {
			masterComm.singlehorde(null);
			// *
			System.out.println("Zombie add count " + (i + 1));

		}

	}

	// arcade picture makedr link
	// https://www.imgonline.com.ua/eng/8bit-picture.php
	public void startGame() {
		gameSpeed.start();
		roomcolors = new ArrayList<Color>();
		// *
		base = new Room(0, 0, Runner.width, Runner.height, 0, true, Color.BLUE, this);
		roomcolors.add(base.color);
		roomSwitcherGuard = new Timer(1000 / 4, base);
		masterComm = new ObjectManager(this);
		flynn = new Flynn(Runner.width / 2, Runner.height / 2, 30, 60, 20, 15);
		masterComm.addObject(flynn);
		masterComm.addObject(base);
		masterComm.addRoom(base, false);
		onScreenRoom = base;
		addToHorde(hordeAdder);
		roomColor = onScreenRoom.color;
		// *
		roomSwitcherGuard.start();
		main_title = new Song("main_title.mp3");
		main_title.play();
		maintitlePlayed = true;
	}

	public void addRoomColor(Color c) {
		roomcolors.add(c);
		// *
	}

	public Color getAnyRoomsColor(int roomsnumber) {
		Color returncolor = roomcolors.get(roomsnumber);
		return returncolor;
		// *

	}

	void updateMenuState() {

	}

	void updateGameState() {
		masterComm.update();

		if (flynn.isAlive == false) {
			currentState = END_STATE;
		}

	}

	// megahead.showenemyLocation();

	void updateEndState() {

	}

	void updateInfoState() {

	}

	void drawMenuState(Graphics a) {

		int startscript = Runner.width / 3;
		// a.setColor(Color.blue);
		a.drawImage(menuImg, 0, 0, Runner.width, Runner.height, null);
		// a.fillRect(0, 0, 1000, 1000);

		a.setFont(font);
		a.setColor(Color.WHITE);

		a.drawString("===HORDE RUNNER===", startscript, 300);
		a.setColor(Color.GREEN);
		a.drawString("Press 'I' for instructions.", startscript, 400);
		a.setFont(funFont);
		a.setColor(Color.RED);
		a.drawString("Press Enter To Start Survival", startscript, 500);
		a.drawString("Press C For Invincible", startscript, 530);
		// *
	}

	void drawGameState(Graphics b) {
		themeStopper(true);
		if (gamesongPlayed == false) {
			game_theme = new Song("game_theme.mp3");
			game_theme.play();
			gamesongPlayed = true;
		}
		if (onScreenRoom.color == Color.BLUE) {
			b.drawImage(Sketch.run, 0, 75, Runner.width, Runner.height, null);
		} else if (onScreenRoom.color == Color.BLACK) {
			b.drawImage(Sketch.fireplace, 0, 75, Runner.width, Runner.height, null);
		} else if (onScreenRoom.color == Color.CYAN) {
			b.drawImage(Sketch.an8bitfire, 0, 75, Runner.width, Runner.height, null);
		} else if (onScreenRoom.color == Color.GREEN) {
			b.drawImage(Sketch.an8bitquake, 0, 75, Runner.width, Runner.height, null);
		} else if (onScreenRoom.color == Color.MAGENTA) {
			b.drawImage(Sketch.an8bitvault, 0, 75, Runner.width, Runner.height, null);
		} else {
			if (onScreenRoom.color == Color.WHITE) {
				b.drawImage(Sketch.fireplace, 0, 75, Runner.width, Runner.height, null);
			} else if (onScreenRoom.color == Color.GRAY) {
				b.drawImage(Sketch.an8bitfire, 0, 75, Runner.width, Runner.height, null);
			} else if (onScreenRoom.color == Color.ORANGE) {
				b.drawImage(Sketch.an8bitquake, 0, 75, Runner.width, Runner.height, null);
			} else if (onScreenRoom.color == Color.PINK) {
				b.drawImage(Sketch.an8bitvault, 0, 75, Runner.width, Runner.height, null);
			} else {
				System.out.println("image showing failed");
				b.setColor(onScreenRoom.color);
				b.fillRect(0, 0, Runner.width, Runner.height);
			}

		}
		// *

		// THE ROOMS DRAW HERE
		masterComm.draw(b);

		showStatistics(b);

	}

	void drawEndState(Graphics c) {
		int hectoheight = Runner.height / 4;
		int middletext = (Runner.width / 2) - 150;
		c.setColor(Color.red);
		c.fillRect(0, 0, Runner.width, Runner.height);
		c.setFont(font);
		c.setColor(Color.WHITE);
		c.drawString("GAME OVER", middletext, hectoheight);
		c.setColor(Color.BLACK);
		c.drawString("You scored " + casualtyCount + "!", middletext, hectoheight * 2);
		c.setFont(funFont);
		c.setColor(Color.WHITE);
		c.drawString("press delete to restart", middletext, hectoheight * 3);

		// *
	}

	void drawInfoState(Graphics i) {
		i.setColor(Color.white);
		i.drawImage(instructionsimg, 0, 0, Runner.width, Runner.height, null);
		i.setFont(funFont);

		if (infoMsgShown == false) {
			infoMsgShown = true;
			JOptionPane.showMessageDialog(null,
					"The HORDE (Horde Of Really Deadly Enemies) is composed of little zombies. " + "\n"
							+ "Avoid the Horde, and even little, individual zombies, at all costs!! However, " + "\n"
							+ "when two zombies of the same level touch each other, they combine. Zombies of the same level look the same."
							+ "\n"
							+ "When zombies combine, the resulting HORDE is generally bigger, and always much more powerful."
							+ "\n"
							+ "If a zombies, or a HORDE, touch(es) you, it will deal its damage to you and bounce off to your left or right."
							+ "\n" + "\n" + "Moving Rooms" + "\n"
							+ "To Go Into next room, simply run into the wall. One may move rooms  horizontally, but not vertically. "
							+ "\n"
							+ "Basic statistics such as health, bullet ammunition, and your kill count are displayed on the top of the screen. "
							+ "\n" + "Always arm a Nuka-Cola suit before detonating a nuclear bomb, or you will die."
							+ "\n" + "\n" + "Items" + "\n"
							+ "Items can be found randomly spawning in rooms. Nuka-Cola bottles will grant you one Nuka-Cola suit each, while Nuclear Bombs will grant you one nuke each. "
							+ "\n" + "Healthpacks, which are red with a white cross, will grant you 15 health each."
							+ "\n" + "Bulletpacks, black gun sillouhettes, will grant you 10 bullets each." + "\n"
							+ "\n" + "Controls" + "\n" + "Use the arrow keys to move. " + "\n"
							+ "Fire bullets by pressing Z (to fire left) or X (to fire right). " + "\n"
							+ "Arm a Nuka-Cola suit by pressing M." + "\n" + "Detonate a nuclear bomb by pressing N.");
			System.out.println(infoMsgShown);
			System.out.println("shown");
			/*
			 * infoMsgShown = true; JOptionPane.showMessageDialog(null,
			 * "IS this spamming. Yes, but why."); System.out.println(infoMsgShown);
			 * System.out.println("shown");
			 */

			// if this code is run (below), the if statement will never end. this is because
			// nothing is stopping the jop.showMessage command (according to Blythe). also
			// the "show" statement will repeat endlessly.
			/*
			 * //infoMsgShown = true; System.out.println("shown");
			 * JOptionPane.showMessageDialog(null, "IS this spamming. Yes, but why.");
			 * System.out.println(infoMsgShown);
			 *
			 */
		}

		i.setFont(funFont);
		i.setColor(Color.WHITE);
		// i.drawString("Press delete to start", HordeRunner.width / 5,
		// HordeRunner.height * (7 / 10));

		// *
	}

	// public void pause() {
	// gameSpeed.stop();
	// roomSwitcherGuard.stop();
	// }
	//
	// public void unpause() {
	// gameSpeed.start();
	// roomSwitcherGuard.start();
	// }

	public void paintComponent(Graphics delta) {
		// System.out.println("paintcomponent has been reached");
		if (currentState == INSTRUCTIONS_STATE) {
			drawInfoState(delta);
		} else if (currentState == MENU_STATE) {
			drawMenuState(delta);
		} else if (currentState == GAME_STATE) {
			drawGameState(delta);
		} else if (currentState == END_STATE) {
			drawEndState(delta);
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@SuppressWarnings("static-access")
	@Override
	public void keyPressed(KeyEvent e) {
		// System.out.println(e.getKeyCode());
		if (currentState == GAME_STATE) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				flynn.transpey = -5;
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				flynn.transpex = -5;
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				flynn.transpex = 5;
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				flynn.transpey = 5;
			}
			if (e.getKeyCode() == KeyEvent.VK_Z) {
				bulletfired(false);
			} else if (e.getKeyCode() == KeyEvent.VK_X) {
				bulletfired(true);
			}
			if ((e.getKeyCode() == KeyEvent.VK_M) && (flynn.nukeSuitEquipped == false)) {
				if (flynn.nukeSuitCount > 0) {
					flynn.nukeSuitEquipped = true;
					flynn.nukeSuitCount--;
				} else {
					JOptionPane.showMessageDialog(null, "No Nuka-Cola Suit Available For Use");
				}

			} else if ((e.getKeyCode() == KeyEvent.VK_M) && (flynn.nukeSuitEquipped == true)) {
				JOptionPane.showMessageDialog(null, "Nuka-Cola Suit Already Equipped");
			} else if (e.getKeyCode() == KeyEvent.VK_N) {
				if (flynn.nukeCount > 0) {
					flynn.nukeCount--;
					for (int i = 0; i < masterComm.objects.size(); i++) {
						GameObject o1 = masterComm.objects.get(i);
						if (o1 instanceof Horde) {
							Horde ohOne = (Horde) o1;
							ohOne.isAlive = false;
							casualtyCount += ohOne.deathPotential;
						}
					}
					if (flynn.nukeSuitEquipped) {

						flynn.nukeSuitEquipped = false;
					} else if (flynn.nukeSuitEquipped == false) {
						flynn.isAlive = false;
					}
				} else {
					if (flynn.nukeSuitEquipped == false) {
						JOptionPane.showMessageDialog(null,
								"Out Of Nuclear Warheads. You'll Have To Commit Suicide Some Other Way. Sorry, Chump");
					} else {
						JOptionPane.showMessageDialog(null, "Out Of Nuclear Warheads.");
					}

				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (currentState == MENU_STATE) {
				currentState = GAME_STATE;
			} else if (currentState == GAME_STATE) {
				currentState = END_STATE;
			}
			System.out.println("The current state is " + currentState + ".");
		}
		if (((!cheatsBasicAccessGranted) && (!cheatsAdminAccessGranted)) && ((e.getKeyCode() == KeyEvent.VK_C)
				|| (e.getKeyCode() == KeyEvent.VK_PERIOD) || (e.getKeyCode() == KeyEvent.VK_S)
				|| (e.getKeyCode() == KeyEvent.VK_COMMA) || (e.getKeyCode() == KeyEvent.VK_K)
				|| (e.getKeyCode() == KeyEvent.VK_J) || (e.getKeyCode() == KeyEvent.VK_L))) {
			JOptionPane.showMessageDialog(null,
					"Cheat Code Access is NOT granted. This cheat code is not available until you enter your Cheats Enabled Mode password, which you can enter in the menu screen by pressing P.");
		}
		if ((cheatsBasicAccessGranted) || (cheatsAdminAccessGranted)) {
			if ((e.getKeyCode() == KeyEvent.VK_C) && (currentState == MENU_STATE)) {
				currentState = GAME_STATE;
				flynn.health = 2000000000;
				flynn.bulletAmmo = 2000000000;
				flynn.nukeCount = 2000000000;
				flynn.nukeSuitCount = 2000000000;
				System.out.println("The current state is " + currentState + ".");
			} else if (e.getKeyCode() == KeyEvent.VK_PERIOD) {
				if (flynn.transpex < 0) {
					flynn.transpex -= 50;
				} else if (flynn.transpex > 0) {
					flynn.transpex += 50;
				}
				if (flynn.transpey < 0) {
					flynn.transpey -= 50;
				} else if (flynn.transpey > 0) {
					flynn.transpey += 50;
				}
			} else if (e.getKeyCode() == KeyEvent.VK_S) {
				String hordelevel = JOptionPane.showInputDialog(
						"What level horde do you want to spawn? Use the number keys, or the numpad. Press a number 1, 9, or any one inbetween. One is the lowest level, and nine is the highest.");
				String hordesize = JOptionPane
						.showInputDialog("How many shall this new Horde compose? Use the number keys, or the numpad.");
				int type1to9sketchex = Integer.parseInt(hordelevel);
				int numberToSpawnsketchex = Integer.parseInt(hordesize);
				masterComm.spawnHorde(type1to9sketchex, numberToSpawnsketchex);
			} else if (e.getKeyCode() == KeyEvent.VK_COMMA) {
				flynn.health += 50000;
				flynn.bulletAmmo += 50000;
			}
			if ((e.getKeyCode() == KeyEvent.VK_K) && (currentState == GAME_STATE)) {
				String healthpane = JOptionPane.showInputDialog("Cheat Code Activated! Enter Flynn's desired health!");
				int newhealth = Integer.parseInt(healthpane);
				flynn.health = newhealth;
				System.out.println("New Health: " + newhealth);
			} else if ((e.getKeyCode() == KeyEvent.VK_J) && (currentState == GAME_STATE)) {
				String bulletpane = JOptionPane
						.showInputDialog("Cheat Code Activated! Enter Flynn's desired bullet ammo stock!");
				int newammo = Integer.parseInt(bulletpane);
				flynn.bulletAmmo = newammo;
				System.out.println("New Bullet Ammo: " + newammo);

			} else if ((e.getKeyCode() == KeyEvent.VK_L) && (currentState == GAME_STATE)) {
				String lcheats = JOptionPane.showInputDialog("Type 1 to show level. Type 2 to show item locations.");
				if (lcheats.equals("1")) {
					System.out.println("Level: " + onScreenRoom.levelShower());
				} else if (lcheats.equals("2")) {
					int numberOfItems = 0;
					System.out.println("Spawned Item Locations:");
					for (int i = 0; i < masterComm.objects.size(); i++) {
						GameObject o1 = masterComm.objects.get(i);
						if (o1 instanceof SpawningItem) {
							SpawningItem s = (SpawningItem) o1;
							numberOfItems++;
							System.out.println("Item " + numberOfItems + ": TYPE: " + s.typeparameter + " X: " + s.x
									+ " Y: " + s.y);
						}
					}
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_I) {
			currentState = INSTRUCTIONS_STATE;
			/*
			 * String directory = JOptionPane.showInputDialog(
			 * "For BASIC PLAYING KNOWLEDGE, press 1. For CONTROLS, press 2. For CHEATS, press 3."
			 * ); int dirNum = Integer.parseInt(directory); switch (dirNum) { case 1:
			 * JOptionPane.showMessageDialog(null,
			 * "The HORDE (Horde Of Really Deadly Enemies) is composed of little horde. " +
			 * "\n" +
			 * "Avoid the Horde, and even little, individual horde, at all costs!! However, "
			 * + "\n" +
			 * "when two horde of the same level touch each other, they combine. Horde of the same level look the same."
			 * + "\n" +
			 * "When horde combine, the resulting HORDE is generally bigger, and always much more powerful."
			 * + "\n" +
			 * "If a horde, or a HORDE, touch(es) you, it will deal its damage to you and bounce off to your left or right."
			 * + "\n" + "\n" + "Moving Rooms" + "\n" +
			 * "To Go Into next room, simply run into the wall. One may move rooms horizontally, but not vertically. "
			 * + "\n" +
			 * "Basic statistics such as health, bullet ammunition, and your kill count are displayed on the top of the screen. "
			 * + "\n" +
			 * "Always arm a Nuka-Cola suit before detonating a nuclear bomb, or you will die."
			 * + "\n" + "\n" + "Items" + "\n" +
			 * "Items can be found randomly spawning in rooms. Nuka-Cola bottles will grant you one Nuka-Cola suit each, while Nuclear Bombs will grant you one nuke each. "
			 * + "\n" +
			 * "Healthpacks, which are red with a white cross, will grant you 15 health each."
			 * + "\n" +
			 * "Bulletpacks, black gun sillouhettes, will grant you 10 bullets each.");
			 * break; case 2: JOptionPane.showMessageDialog(null,
			 * "Use the arrow keys to move. Fire bullets by pressing Z or X. Arm a Nuka-Cola suit by pressing M. Detonate a nuclear bomb by pressing N. For the Cheats Enabled Mode password, close this window and press I, then 3."
			 * ); break; case 3: JOptionPane.showMessageDialog(null,
			 * "For the Cheats Enabled Mode password,  " + "\n" +
			 * "Come up to me and whisper into my ear," + "\n" + "\n" +
			 * "'You would not believe your  eyes" + "\n" + "If ten million fireflies\n" +
			 * "Lit up the world as I fell asleep\n" + "\n" +
			 * "'Cause they fill the open air\n" + "And leave teardrops everywhere\n" +
			 * "You'd think me rude but I would just stand and stare\n" + "\n" +
			 * "I'd like to make myself believe that planet earth turns slowly\n" +
			 * "It's hard to say that I'd rather stay awake when I'm asleep\n" +
			 * "'Cause everything is never as it seems\n" + "\n" +
			 * "'Cause I'd get a thousand hugs\n" + "From ten thousand lightning bugs\n" +
			 * "As they tried to teach me how to dance\n" + "\n" +
			 * "A foxtrot above my head\n" + "A sock hop beneath my bed\n" +
			 * "A disco ball is just hanging by a thread (thread, thread)\n" + "\n" +
			 * "I'd like to make myself believe that planet earth turns slowly\n" +
			 * "It's hard to say that I'd rather stay awake when I'm asleep\n" +
			 * "'Cause everything is never as it seems (when I fall asleep)\n" + "\n" +
			 * "Leave my door open just a crack\n" + "Please take me away from here\n" +
			 * "'Cause I feel like such an insomniac\n" + "Please take me away from here\n"
			 * + "Why do I tire of counting sheep\n" + "Please take me away from here\n" +
			 * "When I'm far too tired to fall asleep\n" + "\n" +
			 * "To ten million fireflies\n" + "I'm weird cause I hate goodbyes\n" +
			 * "I got misty eyes as they said farewell (said farewell)\n" + "\n" +
			 * "But I'll know where several are\n" + "If my dreams get real bizarre\n" +
			 * "'Cause I saved a few and I keep them in a jar (jar, jar)\n" + "\n" +
			 * "I'd like to make myself believe that planet earth turns slowly\n" +
			 * "It's hard to say that I'd rather stay awake when I'm asleep\n" +
			 * "'Cause everything is never as it seems (when I fall asleep)\n" + "\n" +
			 * "I'd like to make myself believe that planet earth turns slowly\n" +
			 * "It's hard to say that I'd rather stay awake when I'm asleep\n" +
			 * "'Cause everything is never as it seems (when I fall asleep)'. \n If you do this, I may consider lending you a cheat code or two. Also, the Cheats Enabled Mode password is peppermintHydra. Press P (for 'Cheat Portal') when on the menu screen to activate it--just follow the instructions."
			 * ); break;
			 * 
			 * default: JOptionPane.showMessageDialog(null,
			 * "Invalid Key Entered. No Information Available."); break; } // if
			 * (directory.equals("1")) { // JOptionPane.showMessageDialog(null, // "To Go
			 * Into next room, simply run into the wall. One may move // rooms //
			 * horizontally, but not vertically. " // + "Basic statistics such as health,
			 * bullet ammunition, and your // kill count // are displayed on the top of the
			 * screen."); // }
			 */
		} else if ((e.getKeyCode() == KeyEvent.VK_P) && (currentState == MENU_STATE)) {
			String password = JOptionPane.showInputDialog("Password Pane opened."
					+ "continue by entering the Cheats Enabled Mode password you have been given. Then press enter, or ckick 'okay'.");
			if (password.equalsIgnoreCase(cheatsEnabledBasicAccessPassword)) {
				cheatsBasicAccessGranted = true;
				JOptionPane.showMessageDialog(null, "Cheats Enabled Mode is on!");
			}
			if (password.equalsIgnoreCase(cheatsEnabledAdminAccessPassword)) {
				cheatsAdminAccessGranted = true;
				JOptionPane.showMessageDialog(null, "Cheats Enabled Mode is on!");
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			fullRestart();
		}

		// if ((e.getKeyCode() == KeyEvent.VK_P) && (paused == false)) {
		// pause();
		// }
		// if ((e.getKeyCode() == KeyEvent.VK_P) && (paused == true)) {
		// unpause();
		// paused = false;
		// }
		// for some reason unpause doesnt work (haven't looked into it yet)
	}

	private void fullRestart() {
		currentState = MENU_STATE;
		casualtyCount = 0;
		roomSwitcherGuard.stop();
		gameSpeed.stop();
		masterComm.reset();
		roomcolors.clear();
		// *
		hordeAdder = 0;
		onScreenRoom = base;
		onScreenRoom.level = 1;
		onScreenRoom.levelupper = 0;
		onScreenRoom.leveluppermultiplier = 1;
		onScreenRoom.leveluppermultipliercounter = 0;
		flynn.isAlive = true;
		// paused = false;
		flynnroomnumber = 0;
		cheatsBasicAccessGranted = false;
		cheatsAdminAccessGranted = false;
		if (gamesongPlayed == false) {
			themeStopper(true);
		}
		if (gamesongPlayed) {
			themeStopper(false);
		}

		gamesongPlayed = false;
		maintitlePlayed = false;
		infoMsgShown = false;
		startGame();
	}

	void themeStopper(boolean isMain_Title) {
		Song theme;
		theme = isMain_Title ? main_title : game_theme;
		theme.stop();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		flynn.transpex = 0;
		flynn.transpey = 0;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// if (e.getSource() == gameSpeed) {
		repaint();
		// System.out.println("action performed");
		if (currentState == MENU_STATE) {
			updateMenuState();
		} else if (currentState == GAME_STATE) {
			updateGameState();
		} else if (currentState == END_STATE) {
			updateEndState();
		} else if (currentState == INSTRUCTIONS_STATE) {
			updateInfoState();
		}

		// if (e.getSource() == fireTimer) {
		// System.out.println("fireTimer action reached");
		// boolean isDirectedRight;
		// if (bulletDirection == 1) {
		// isDirectedRight = false;
		// } else if (bulletDirection == 2) {
		// isDirectedRight = true;
		// } else {
		// JOptionPane.showMessageDialog(null, "BULLET DIRECTION ERROR");
		// isDirectedRight = true;
		// }
		// flynn.bulletAmmo -= 1;
		// System.out.println("Bullets: " + flynn.bulletAmmo);
		// int bulletx = isDirectedRight ? flynn.x + flynn.width : flynn.x;
		// Bullet bullet = new Bullet(bulletx, flynn.y + flynn.width / 2 +
		// (flynn.height
		// / 2), 8, 4, this);
		// bullet.isGoingRight = isDirectedRight ? true : false;
		// megahead.addObject(bullet);
		// flynn.transpex = isDirectedRight ? 1 : -1;
		// }
		// why doesnt this create rapidfire?

	}
}
