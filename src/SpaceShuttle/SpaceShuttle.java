package SpaceShuttle;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

/**
 * Main class of the program, includes the main method for executing
 * 
 * @author Rune Krauss
 */
public class SpaceShuttle {

	/**
	 * List of bullets
	 */
	private static List<Bullet> bullets = new LinkedList<Bullet>();
	
	/**
	 * List of enemies
	 */
	private static List<Enemy> enemies = new LinkedList<Enemy>();
	
	/**
	 * Protagonist of the game
	 */
	private static Player player = new Player(300, 300, 1280, 800, 300, bullets, enemies);
	
	/**
	 * Space with stars
	 */
	private static World bg = new World(200);
	
	/**
	 * Display with bit depth of 32
	 */
	private static DisplayMode dm = new DisplayMode(1280, 800, 32, 0);
	
	/**
	 * Coincidence regarding the creation of enemies
	 */
	private static Random rand = new Random();
	
	/**
	 * Frame for drawing elements like the player
	 */
	private static Frame f = new Frame(player, bg, bullets, enemies);
	
	/**
	 * Initializes the figures, calculates the positions and draws the elements
	 * 
	 * @param args Command line parameters
	 */
	public static void main(String[] args) {	
		enemies.add(new Enemy(1280, 150, 1280, 800, 150, bullets, player));
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(800, 600);
		f.setResizable(false);

		// Activate full screen mode
		GraphicsEnvironment environment = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice device = environment.getDefaultScreenDevice();

		if (device.isFullScreenSupported()) {
			device.setFullScreenWindow(f);
			device.setDisplayMode(dm);
		} else {
			System.out.print("Full screen is not supported!");
		}

		// Fix bug regarding OSX
		f.setVisible(false);
		f.setVisible(true);

		// Activate buffer strategy
		f.makeStrat();

		final float ENEMY_SPAWN_TIME = 1.0f;
		float timeSinceLastEnemySpawn = 0;
		
		long lastFrame = System.currentTimeMillis();
		while (!Keyboard.isKeyDown(KeyEvent.VK_ESCAPE)) {

			// Calculate timeSinceLastFrame
			long thisFrame = System.currentTimeMillis();
			float timeSinceLastFrame = (float) (thisFrame - lastFrame) / 1000f;
			lastFrame = thisFrame;

			// Calculation area
			bg.update(timeSinceLastFrame);
			
			timeSinceLastEnemySpawn += timeSinceLastFrame;
			if (timeSinceLastEnemySpawn > ENEMY_SPAWN_TIME) {
				timeSinceLastEnemySpawn -= ENEMY_SPAWN_TIME;
				spawnEnemy();
			}
			
			player.update(timeSinceLastFrame);
			for (int i = 0; i < bullets.size(); i++) {
				Bullet bullet = bullets.get(i);
				bullet.update(timeSinceLastFrame);
			}

			for (int i = 0; i < enemies.size(); i++) {
				enemies.get(i).update(timeSinceLastFrame);
			}

			// Drawing area
			f.repaintScreen();
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.exit(0);
	}
	
	/**
	 * Creates enemies at random position
	 */
	private static void  spawnEnemy() {
		enemies.add(new Enemy(1280, rand.nextInt(800 - Enemy.getHeight()), 1280, 800, 101, bullets, player));
	}
}
