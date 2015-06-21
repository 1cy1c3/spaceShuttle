package SpaceShuttle;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.List;

import javax.swing.JFrame;

/**
 * Manages the view of this game
 * 
 * @author Rune Krauss
 */
public class Frame extends JFrame {
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 1L;
	// private Screen screen;

	/**
	 * Instance of the protagonist
	 */
	final Player player;
	
	/**
	 * Instance of the space
	 */
	final World bg;
	
	/**
	 * List of bullets
	 */
	List<Bullet> bullets;
	
	/**
	 * List of enemies
	 */
	List<Enemy> enemies;

	/**
	 * Buffer strategy for avoiding flicker
	 */
	private BufferStrategy strat;

	/**
	 * Instantiates the view of this game
	 * 
	 * @param player Protagonist
	 * @param bg Space
	 * @param bullets List of bullets
	 * @param enemies List of enemies
	 */
	public Frame(Player player, World bg, List<Bullet> bullets,
			List<Enemy> enemies) {
		super("MoveTest");
		setUndecorated(true);
		getRootPane().putClientProperty("apple.awt.draggableWindowBackground",
				true);
		addKeyListener(new Keyboard());
		this.player = player;
		this.bg = bg;
		this.bullets = bullets;
		this.enemies = enemies;
	}

	/**
	 * Creates the buffer strategy
	 */
	public void makeStrat() {
		createBufferStrategy(2);
		strat = getBufferStrategy();
	}

	/**
	 * Shows the buffer strategy
	 */
	public void repaintScreen() {
		Graphics g = strat.getDrawGraphics();
		draw(g);
		g.dispose();
		strat.show();
	}

	/**
	 * Draws the graphics like enemies and bullets
	 * 
	 * @param g2 2D-Graphic
	 */
	private void draw(Graphics g2) {
		Graphics2D g = (Graphics2D) g2;
		g.drawImage(bg.getLook(), bg.getX(), 0, null);
		g.drawImage(bg.getLook(), bg.getX() + bg.getLook().getWidth(), 0, null);
		for (int i = 0; i < enemies.size(); i++) {
			Enemy enemy = enemies.get(i);
			g.drawImage(enemy.getLook(), enemy.getBounding().getBounds().x,
					enemy.getBounding().getBounds().y, null);
		}
		for (int i = 0; i < bullets.size(); i++) {
			Bullet bullet = bullets.get(i);
			g.drawImage(bullet.getLook(), (int) (bullet.getBounding().getX()
					+ player.getLook().getWidth() / 2 - bullet.getLook()
					.getWidth() / 2), (int) (bullet.getBounding().getY())
					+ player.getLook().getHeight() / 2
					- bullet.getLook().getHeight() / 2, null);
		}
		g.drawImage(player.getLook(), (int) player.getBounding().getX(),
				(int) player.getBounding().getY(), null);
	}
}
