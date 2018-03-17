import javax.swing.JFrame;

public class Runner {
	JFrame frame;
	static final int width = 1920;
	static final int height = 1080;
	Sketch hex;

	// constructor
	public Runner() {
		frame = new JFrame();
		hex = new Sketch();
		setup();
	}

	public static void main(String[] args) {
		Runner battlehead = new Runner();
	}

	void setup() {
		frame.add(hex);
		frame.setTitle("Horde Runner");
		frame.addKeyListener(hex);
		frame.setSize(width, height);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		hex.startGame();
	}
}
