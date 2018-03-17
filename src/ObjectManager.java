
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JOptionPane;

public class ObjectManager {
	ArrayList<GameObject> objects;
	ArrayList<Room> rooms;
	Sketch hex;
	Color dark1 = new Color(100, 50, 50);
	Color dark2 = new Color(200, 50, 80);
	Color dark3 = new Color(250, 100, 10);
	Color dark4 = new Color(49, 0, 26);

	// *
	/**
	 * the below showScore method/variable/code does not work. This message has been
	 * printed below every instance where something related to it is mentioned.
	 */
	// Font funFont;
	long enemyTimer = 0;
	int enemySpawnTime = 1;
	int flynnbulletdamage = 1;

	public ObjectManager(Sketch hex) {
		objects = new ArrayList<GameObject>();
		rooms = new ArrayList<Room>();
		this.hex = hex;
	}

	public void addObject(GameObject o) {
		objects.add(o);
	}

	public void update() {

		for (int i = 0; i < objects.size(); i++) {
			GameObject o = objects.get(i);
			o.update();
		}
		checkCollision();
		purgeObjects();
	}

	public void draw(Graphics g) {
		for (int i = 0; i < objects.size(); i++) {
			GameObject o = objects.get(i);
			o.draw(g);
		}
	}

	public void addRoom(Room r, boolean isRight) {
		if (isRight) {
			rooms.add(r);
			addObject(r);
		} else {
			rooms.add(0, r);
			addObject(r);
		}
	}

	public Room getRoom(int roomNumber) {
		for (Room r : rooms) {
			if (r.roomsroomnumber == roomNumber) {
				return r;
			}
		}
		return null;
	}

	private void purgeObjects() {
		for (int i = 0; i < objects.size(); i++) {
			if (!objects.get(i).isAlive) {
				objects.remove(i);
			}
		}
	}

	public void manageEnemiesAndItems(int xdisplacement) {
		for (int i = 0; i < objects.size(); i++) {
			GameObject o1 = objects.get(i);
			if ((o1 instanceof Horde) || (o1 instanceof SpawningItem)) {
				o1.x += xdisplacement;
			}
		}

	}

	public void checkCollision() {
		for (int i = 0; i < objects.size(); i++) {
			for (int j = i + 1; j < objects.size(); j++) {
				// what is the purpose of the 'i' for loop enclosing the 'j' for
				// loop?
				GameObject o1 = objects.get(i);
				GameObject o2 = objects.get(j);

				if (o1.collisionArea.intersects(o2.collisionArea)) {

					if ((o1 instanceof Horde && o2 instanceof Bullet)
							|| (o2 instanceof Horde && o1 instanceof Bullet)) {
						Bullet bullet = (o1 instanceof Bullet) ? (Bullet) o1 : (Bullet) o2;
						Horde shotHorde = (o1 instanceof Horde) ? (Horde) o1 : (Horde) o2;
						// if (hex.onScreenRoom.level % 5 == 0) {
						// flynnbulletdamage = hex.onScreenRoom.level / 5;
						// }
						// shotHorde.health -= flynnbulletdamage;
						shotHorde.health -= 1;
						o2.isAlive = false;
						if (shotHorde.health <= 0) {
							shotHorde.isAlive = false;
							Sketch.casualtyCount += shotHorde.deathPotential;

						}
					} else if ((o1 instanceof Horde && o2 instanceof Flynn)
							|| (o2 instanceof Horde && o1 instanceof Flynn)) {

						Horde hordie = o1 instanceof Horde ? (Horde) o1 : (Horde) o2;
						Flynn omflynn = o1 instanceof Flynn ? (Flynn) o1 : (Flynn) o2;

						int randomDisplacementNum = new Random().nextInt(2);
						if (randomDisplacementNum == 0) {
							hordie.x -= 200;
						} else if (randomDisplacementNum == 1) {
							hordie.x += 200;
						}
						omflynn.health -= hordie.deathPotential;
						System.out.println("Flynn's health is now " + omflynn.health + ".");

						if (omflynn.health <= 0) {
							omflynn.isAlive = false;
						} else if (hordie.health <= 0) {
							hordie.isAlive = false;
						}
					} else if ((o1 instanceof SpawningItem && o2 instanceof Flynn)
							|| (o2 instanceof SpawningItem && o1 instanceof Flynn)) {

						SpawningItem item = o1 instanceof SpawningItem ? (SpawningItem) o1 : (SpawningItem) o2;
						Flynn imflynnity = o1 instanceof Flynn ? (Flynn) o1 : (Flynn) o2;

						// System.out.println("Flynn's health is now " +
						// imflynnity.health + ".");
						if (item.typeparameter.equals(item.type1)) {
							imflynnity.bulletAmmo += 10;
							System.out.println("New Bullets: " + imflynnity.bulletAmmo);
						} else if (item.typeparameter.equals(item.type2)) {
							imflynnity.health += 15;
							System.out.println("New Health: " + imflynnity.health);
						} else if (item.typeparameter.equals(item.type3pt1)) {
							imflynnity.nukeCount += 1;
							System.out.println("New Nuke Count: " + imflynnity.nukeCount);
						} else if (item.typeparameter.equals(item.type3pt2)) {
							imflynnity.nukeSuitCount += 1;
							System.out.println("New Nuka-Cola Suit Count: " + imflynnity.nukeSuitCount);
						}
						System.out.println("Item " + item.typeparameter + " picked up!");
						item.isAlive = false;
					} else if (o1 instanceof Horde && o2 instanceof Horde) {
						Horde zombie = (Horde) o1;
						// System.out.println("zombie initial health is at "
						// + zombie.health);
						Horde zombietwo = (Horde) o2;
						if ((zombie.level == 1) && (zombietwo.level == 1)) {
							minihorde(zombie, zombietwo);

						} else if ((zombie.level == 2) && (zombietwo.level == 2)) {
							horde(zombie, zombietwo);

						} else if ((zombie.level == 3) && (zombietwo.level == 3)) {
							megahorde(zombie, zombietwo);

						} else if ((zombie.level == 4) && (zombietwo.level == 4)) {
							ultrahorde(zombie, zombietwo);

						} else if ((zombie.level == 5) && (zombietwo.level == 5)) {
							hellhorde(zombie, zombietwo);

						} else if ((zombie.level == 6) && (zombietwo.level == 6)) {
							deathhorde(zombie, zombietwo);

						} else if ((zombie.level == 7) && (zombietwo.level == 7)) {
							archhorde(zombie, zombietwo);

						} else if ((zombie.level == 8) && (zombietwo.level == 8)) {
							darkhorde(zombie, zombietwo);

						} else if ((zombie.level == 9) && (zombietwo.level == 9)) {
							eternalhellicates(zombie, zombietwo);

						}

						// *
					}

				}
			}
		}
	}

	public void reset() {
		objects.clear();
	}

	public void spawnHorde(int type1to9, int numberToSpawn) {
		for (int i = 0; i < numberToSpawn; i++) {
			switch (type1to9) {
			case 1:
				singlehorde(null);
			case 2:
				minihorde(null, null);
			case 3:
				horde(null, null);
			case 4:
				megahorde(null, null);
			case 5:
				ultrahorde(null, null);
			case 6:
				hellhorde(null, null);
			case 7:
				deathhorde(null, null);
			case 8:
				archhorde(null, null);
			case 9:
				darkhorde(null, null);
			case 10:
				eternalhellicates(null, null);

			default:
				JOptionPane.showMessageDialog(null, "Horde Spawn Error");
				break;
			}
		}
	}

	public Horde singlehorde(Horde alreadyExistent) {
		if (alreadyExistent == null) {
			int dissolventwidth = 30;
			int dissolventheight = 60;
			int randomxone = new Random().nextInt(Runner.width - dissolventwidth);
			int randomyone = new Random().nextInt(Runner.height - dissolventheight);
			Horde basiclisantratimortis = new Horde(randomxone, randomyone, dissolventwidth, dissolventheight, hex, 1,
					null);
			// *
			hordeImageUp(basiclisantratimortis);
			addObject(basiclisantratimortis);
			if (basiclisantratimortis.x < Runner.width / 5) {
				basiclisantratimortis.x += Runner.width / 5;
			} else if (basiclisantratimortis.x > Runner.width - (Runner.width / 5)) {
				basiclisantratimortis.x -= Runner.width / 5;
			}
			if (basiclisantratimortis.y < Runner.height / 5) {
				basiclisantratimortis.y += Runner.height / 5;
			} else if (basiclisantratimortis.y > Runner.height - (Runner.height / 5)) {
				basiclisantratimortis.y -= Runner.height / 5;
			}
			return basiclisantratimortis;
		} else {
			Horde russel = alreadyExistent;
			return russel;
		}

	}

	public Horde minihorde(Horde alreadyExistent, Horde alreadyExistentDiedInCombination) {
		if (alreadyExistent == null) {
			int dissolventwidth = 60;
			int dissolventheight = 120;
			int randomxone = new Random().nextInt(Runner.width - dissolventwidth);
			int randomyone = new Random().nextInt(Runner.height - dissolventheight);
			Horde russel = new Horde(randomxone, randomyone, dissolventwidth, dissolventheight, hex, 2, null);
			russel.level = 2;
			hordeImageUp(russel);
			// *
			russel.deathPotential = 2;
			russel.speed = 2;
			addObject(russel);
			System.out.println("Minihorde!");
			if (russel.x < Runner.width / 5) {
				russel.x += Runner.width / 5;
			} else if (russel.x > Runner.width - (Runner.width / 5)) {
				russel.x -= Runner.width / 5;
			}
			if (russel.y < Runner.height / 5) {
				russel.y += Runner.height / 5;
			} else if (russel.y > Runner.height - (Runner.height / 5)) {
				russel.y -= Runner.height / 5;
			}
			return russel;
		} else {
			alreadyExistentDiedInCombination.isAlive = false;
			Horde russel = alreadyExistent;
			russel.level = 2;
			hordeImageUp(russel);
			russel.width = 60;
			russel.height = 120;
			russel.speed = 2;
			russel.deathPotential = 2;
			russel.health = 2;
			// russel.color = Color.magenta;
			// *
			System.out.println("Minihorde!");
			return russel;
		}

	}

	public Horde horde(Horde alreadyExistent, Horde alreadyExistentDiedInCombination) {
		if (alreadyExistent == null) {
			int packwidth = 120;
			int packheight = 240;
			int randomxone = new Random().nextInt(Runner.width - packwidth);
			int randomyone = new Random().nextInt(Runner.height - packheight);
			Horde pack = new Horde(randomxone, randomyone, packwidth, packheight, hex, 3, null);
			// *
			pack.level = 3;
			hordeImageUp(pack);
			pack.deathPotential = 4;
			pack.speed = 3;
			addObject(pack);
			System.out.println("Horde!");
			if (pack.x < Runner.width / 5) {
				pack.x += Runner.width / 5;
			} else if (pack.x > Runner.width - (Runner.width / 5)) {
				pack.x -= Runner.width / 5;
			}
			if (pack.y < Runner.height / 5) {
				pack.y += Runner.height / 5;
			} else if (pack.y > Runner.height - (Runner.height / 5)) {
				pack.y -= Runner.height / 5;
			}
			return pack;
		} else {
			if (alreadyExistentDiedInCombination != null) {
				alreadyExistentDiedInCombination.isAlive = false;
			}
			Horde pack = alreadyExistent;
			pack.level = 3;
			hordeImageUp(pack);
			pack.width = 120;
			pack.height = 240;
			pack.speed = 3;
			pack.deathPotential = 4;
			pack.health = 3;
			// pack.color = Color.green;
			// *
			System.out.println("Horde!");
			return pack;
		}

	}

	public Horde megahorde(Horde alreadyExistent, Horde alreadyExistentDiedInCombination) {
		if (alreadyExistent == null) {
			int packwidth = 240;
			int packheight = 480;
			int randomxone = new Random().nextInt(Runner.width - packwidth);
			int randomyone = new Random().nextInt(Runner.height - packheight);
			Horde deathcrowd = new Horde(randomxone, randomyone, packwidth, packheight, hex, 4, null);
			// *
			deathcrowd.level = 4;
			hordeImageUp(deathcrowd);
			deathcrowd.deathPotential = 8;
			deathcrowd.speed = 4;
			addObject(deathcrowd);
			System.out.println("Megahorde!");
			if (deathcrowd.x < Runner.width / 5) {
				deathcrowd.x += Runner.width / 5;
			} else if (deathcrowd.x > Runner.width - (Runner.width / 5)) {
				deathcrowd.x -= Runner.width / 5;
			}
			if (deathcrowd.y < Runner.height / 5) {
				deathcrowd.y += Runner.height / 5;
			} else if (deathcrowd.y > Runner.height - (Runner.height / 5)) {
				deathcrowd.y -= Runner.height / 5;
			}
			return deathcrowd;
		} else {
			if (alreadyExistentDiedInCombination != null) {
				alreadyExistentDiedInCombination.isAlive = false;
			}
			Horde deathpack = alreadyExistent;
			deathpack.level = 4;
			hordeImageUp(deathpack);
			deathpack.width = 240;
			deathpack.height = 480;
			deathpack.speed = 4;
			deathpack.deathPotential = 8;
			deathpack.health = 4;
			// deathpack.color = Color.blue;
			// *
			System.out.println("Megahorde!");
			return deathpack;
		}

	}

	public Horde ultrahorde(Horde alreadyExistent, Horde alreadyExistentDiedInCombination) {
		if (alreadyExistent == null) {

			int packwidth = 245;
			int packheight = 485;
			int randomxone = new Random().nextInt(Runner.width - packwidth);
			int randomyone = new Random().nextInt(Runner.height - packheight);
			Horde ultroidcapacitor = new Horde(randomxone, randomyone, packwidth, packheight, hex, 8, null);
			// *
			ultroidcapacitor.level = 5;
			hordeImageUp(ultroidcapacitor);
			ultroidcapacitor.deathPotential = 16;
			ultroidcapacitor.speed = 5;
			addObject(ultroidcapacitor);
			System.out.println("Ultrahorde!");
			if (ultroidcapacitor.x < Runner.width / 5) {
				ultroidcapacitor.x += Runner.width / 5;
			} else if (ultroidcapacitor.x > Runner.width - (Runner.width / 5)) {
				ultroidcapacitor.x -= Runner.width / 5;
			}
			if (ultroidcapacitor.y < Runner.height / 5) {
				ultroidcapacitor.y += Runner.height / 5;
			} else if (ultroidcapacitor.y > Runner.height - (Runner.height / 5)) {
				ultroidcapacitor.y -= Runner.height / 5;
			}
			return ultroidcapacitor;
		} else {

			if (alreadyExistentDiedInCombination != null) {
				alreadyExistentDiedInCombination.isAlive = false;
			}
			Horde ultroidcapacitor = alreadyExistent;
			ultroidcapacitor.level = 5;
			hordeImageUp(ultroidcapacitor);
			ultroidcapacitor.width = 245;
			ultroidcapacitor.height = 485;
			ultroidcapacitor.speed = 5;
			ultroidcapacitor.deathPotential = 16;
			ultroidcapacitor.health = 8;
			// ultroidcapacitor.color = Color.red;
			// *
			System.out.println("Ultrahorde!");
			return ultroidcapacitor;
		}

	}

	public Horde hellhorde(Horde alreadyExistent, Horde alreadyExistentDiedInCombination) {
		if (alreadyExistent == null) {
			int packwidth = 250;
			int packheight = 490;
			int randomxone = new Random().nextInt(Runner.width - packwidth);
			int randomyone = new Random().nextInt(Runner.height - packheight);
			Horde hellsGreeters = new Horde(randomxone, randomyone, packwidth, packheight, hex, 16, null);
			hellsGreeters.level = 6;
			hordeImageUp(hellsGreeters);
			hellsGreeters.deathPotential = 32;
			hellsGreeters.speed = 6;
			addObject(hellsGreeters);
			System.out.println("Hellhorde!");
			if (hellsGreeters.x < Runner.width / 5) {
				hellsGreeters.x += Runner.width / 5;
			} else if (hellsGreeters.x > Runner.width - (Runner.width / 5)) {
				hellsGreeters.x -= Runner.width / 5;
			}
			if (hellsGreeters.y < Runner.height / 5) {
				hellsGreeters.y += Runner.height / 5;
			} else if (hellsGreeters.y > Runner.height - (Runner.height / 5)) {
				hellsGreeters.y -= Runner.height / 5;
			}
			return hellsGreeters;
		} else {
			if (alreadyExistentDiedInCombination != null) {
				alreadyExistentDiedInCombination.isAlive = false;
			}
			Horde hellsGreeters = alreadyExistent;
			hellsGreeters.width = 250;
			hellsGreeters.level = 6;
			hordeImageUp(hellsGreeters);
			hellsGreeters.height = 490;
			hellsGreeters.speed = 6;
			hellsGreeters.deathPotential = 32;
			hellsGreeters.health = 16;
			// hellsGreeters.color = Color.yellow;
			System.out.println("Hellhorde!");
			return hellsGreeters;
		}

	}

	public Horde deathhorde(Horde alreadyExistent, Horde alreadyExistentDiedInCombination) {
		if (alreadyExistent == null) {
			int packwidth = 255;
			int packheight = 495;
			int randomxone = new Random().nextInt(Runner.width - packwidth);
			int randomyone = new Random().nextInt(Runner.height - packheight);
			Horde hellsGreeters = new Horde(randomxone, randomyone, packwidth, packheight, hex, 32, null);
			hellsGreeters.level = 7;
			hordeImageUp(hellsGreeters);
			hellsGreeters.deathPotential = 64;
			hellsGreeters.speed = 7;
			addObject(hellsGreeters);
			System.out.println("Deathhorde!");
			if (hellsGreeters.x < Runner.width / 5) {
				hellsGreeters.x += Runner.width / 5;
			} else if (hellsGreeters.x > Runner.width - (Runner.width / 5)) {
				hellsGreeters.x -= Runner.width / 5;
			}
			if (hellsGreeters.y < Runner.height / 5) {
				hellsGreeters.y += Runner.height / 5;
			} else if (hellsGreeters.y > Runner.height - (Runner.height / 5)) {
				hellsGreeters.y -= Runner.height / 5;
			}
			return hellsGreeters;
		} else {
			if (alreadyExistentDiedInCombination != null) {
				alreadyExistentDiedInCombination.isAlive = false;
			}
			Horde hellsGreeters = alreadyExistent;
			hellsGreeters.level = 7;
			hordeImageUp(hellsGreeters);
			hellsGreeters.width = 255;
			hellsGreeters.height = 495;
			hellsGreeters.speed = 7;
			hellsGreeters.deathPotential = 64;
			hellsGreeters.health = 32;
			// hellsGreeters.color = dark1;
			System.out.println("Deathhorde!");
			return hellsGreeters;
		}
		// *

	}

	public Horde archhorde(Horde alreadyExistent, Horde alreadyExistentDiedInCombination) {
		if (alreadyExistent == null) {
			int packwidth = 260;
			int packheight = 500;
			int randomxone = new Random().nextInt(Runner.width - packwidth);
			int randomyone = new Random().nextInt(Runner.height - packheight);
			Horde archies = new Horde(randomxone, randomyone, packwidth, packheight, hex, 64, null);
			archies.level = 8;
			hordeImageUp(archies);
			archies.deathPotential = 128;
			archies.speed = 7;
			addObject(archies);
			System.out.println("Archpack!");
			if (archies.x < Runner.width / 5) {
				archies.x += Runner.width / 5;
			} else if (archies.x > Runner.width - (Runner.width / 5)) {
				archies.x -= Runner.width / 5;
			}
			if (archies.y < Runner.height / 5) {
				archies.y += Runner.height / 5;
			} else if (archies.y > Runner.height - (Runner.height / 5)) {
				archies.y -= Runner.height / 5;
			}
			return archies;
		} else {
			if (alreadyExistentDiedInCombination != null) {
				alreadyExistentDiedInCombination.isAlive = false;
			}
			Horde archies = alreadyExistent;
			archies.width = 260;
			archies.level = 8;
			hordeImageUp(archies);
			archies.height = 500;
			archies.speed = 7;
			archies.deathPotential = 128;
			archies.health = 64;
			// archies.color = dark2;
			System.out.println("Archpack!");
			return archies;
		}

		// *
	}

	public Horde darkhorde(Horde alreadyExistent, Horde alreadyExistentDiedInCombination) {
		if (alreadyExistent == null) {
			int packwidth = 260;
			int packheight = 500;
			int randomxone = new Random().nextInt(Runner.width - packwidth);
			int randomyone = new Random().nextInt(Runner.height - packheight);
			Horde darknyssez = new Horde(randomxone, randomyone, packwidth, packheight, hex, 128, null);
			darknyssez.level = 9;
			hordeImageUp(darknyssez);
			darknyssez.deathPotential = 256;
			darknyssez.speed = 7;
			addObject(darknyssez);
			if (darknyssez.x < Runner.width / 5) {
				darknyssez.x += Runner.width / 5;
			} else if (darknyssez.x > Runner.width - (Runner.width / 5)) {
				darknyssez.x -= Runner.width / 5;
			}
			if (darknyssez.y < Runner.height / 5) {
				darknyssez.y += Runner.height / 5;
			} else if (darknyssez.y > Runner.height - (Runner.height / 5)) {
				darknyssez.y -= Runner.height / 5;
			}
			return darknyssez;
		} else {
			if (alreadyExistentDiedInCombination != null) {
				alreadyExistentDiedInCombination.isAlive = false;
			}
			Horde darknyssez = alreadyExistent;
			darknyssez.width = 260;
			darknyssez.level = 9;
			hordeImageUp(darknyssez);
			darknyssez.height = 500;
			darknyssez.speed = 7;
			darknyssez.deathPotential = 256;
			darknyssez.health = 128;
			// darknyssez.color = dark3;
			return darknyssez;
		}

	}

	public Horde eternalhellicates(Horde alreadyExistent, Horde alreadyExistentDiedInCombination) {
		if (alreadyExistent == null) {
			int packwidth = 260;
			int packheight = 500;
			int randomxone = new Random().nextInt(Runner.width - packwidth);
			int randomyone = new Random().nextInt(Runner.height - packheight);
			Horde eternityswhim = new Horde(randomxone, randomyone, packwidth, packheight, hex, 256, null);
			eternityswhim.level = 10;
			hordeImageUp(eternityswhim);
			eternityswhim.deathPotential = 512;
			eternityswhim.speed = 7;
			addObject(eternityswhim);
			System.out.println("Eternitypack!");
			if (eternityswhim.x < Runner.width / 5) {
				eternityswhim.x += Runner.width / 5;
			} else if (eternityswhim.x > Runner.width - (Runner.width / 5)) {
				eternityswhim.x -= Runner.width / 5;
			}
			if (eternityswhim.y < Runner.height / 5) {
				eternityswhim.y += Runner.height / 5;
			} else if (eternityswhim.y > Runner.height - (Runner.height / 5)) {
				eternityswhim.y -= Runner.height / 5;
			}
			return eternityswhim;
		} else {
			if (alreadyExistentDiedInCombination != null) {
				alreadyExistentDiedInCombination.isAlive = false;
			}
			Horde eternityswhim = alreadyExistent;
			eternityswhim.level = 10;
			hordeImageUp(eternityswhim);
			eternityswhim.width = 260;
			eternityswhim.height = 500;
			eternityswhim.speed = 7;
			eternityswhim.deathPotential = 512;
			eternityswhim.health = 256;
			// eternityswhim.color = dark4;
			System.out.println("Eternitypack!");
			return eternityswhim;
		}
		// *

	}

	public void hordeImageUp(Horde h) {
		switch (h.level) {
		case 1:
			h.imagex = Sketch.horde1;
			break;
		case 2:
			h.imagex = Sketch.horde2;
			break;
		case 3:
			h.imagex = Sketch.horde3;
			break;
		case 4:
			h.imagex = Sketch.horde4;
			break;
		case 5:
			h.imagex = Sketch.horde5;
			break;
		case 6:
			h.imagex = Sketch.horde6;
			break;
		case 7:
			h.imagex = Sketch.horde7;
			break;
		case 8:
			h.imagex = Sketch.horde8;
			break;
		case 9:
			h.imagex = Sketch.horde9;
			break;
		case 10:
			h.imagex = Sketch.horde10;
			break;
		}
	}

}
