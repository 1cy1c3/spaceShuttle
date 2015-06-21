package SpaceShuttle;

import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * Manages the player, creates and updates it
 * 
 * @author Rune Krauss
 */
public class Player {
	/**
	 * Bounding of the player for collision detection
	 */
	private Ellipse2D bounding;

	/**
	 * Position on the x-axis
	 */
	private float f_posx;
	
	/**
	 * Position on the y-axis
	 */
	private float f_posy;
	
	/**
	 * Width of the space
	 */
	private int worldsize_x;
	
	/**
	 * Height of the space
	 */
	private int worldsize_y;
	
	/**
	 * Speed
	 */
	private float speed;

	/**
	 * Time since last shot
	 */
	private float timeSinceLastShot = 0;
	
	/**
	 * Delay rate
	 */
	private final float DELAY_RATE = 0.2f;

	/**
	 * Image of a player
	 */
	private BufferedImage look;
	
	/**
	 * Image of a dead player
	 */
	private BufferedImage lookDead;

	/**
	 * List of bullets
	 */
	private List<Bullet> bullets;

	/**
	 * List of enemies
	 */
	private List<Enemy> enemies;
	
	/**
	 * Flag for the life of a player
	 */
	private boolean alive = true;

	/**
	 * Instantiates a player
	 * 
	 * @param x Position on x-axis
	 * @param y Position on y-axis
	 * @param worldsize_x Width of the space
	 * @param worldsize_y Height of the space
	 * @param speed Speed
	 * @param bullets List of bullets
	 * @param enemies List of enemies
	 */
	public Player(float x, float y, int worldsize_x, int worldsize_y, float speed, 
			List<Bullet> bullets, List<Enemy> enemies) {
		try {
			look = ImageIO.read(getClass().getClassLoader()
					.getResourceAsStream("gfx/shuttle.png"));
			lookDead = ImageIO.read(getClass().getClassLoader()
					.getResourceAsStream("gfx/shuttle_broken.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		bounding = new Ellipse2D.Float(x, y, look.getWidth(), look.getHeight());
		f_posx = x;
		f_posy = y;
		this.worldsize_x = worldsize_x;
		this.worldsize_y = worldsize_y;
		this.speed = speed;
		this.bullets = bullets;
		this.enemies = enemies;
	}

	/**
	 * Scales a player
	 * 
	 * @param img Image
	 * @param width Width
	 * @param height Height
	 * @return Scaled player
	 */
	public BufferedImage scale(BufferedImage img, int width, int height) {
		BufferedImage ergebnis = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		ergebnis.getGraphics().drawImage(img, 0, 0, width, height, null);
		return ergebnis;
	}

	/**
	 * Updates a player regarding the position
	 * 
	 * @param timeSinceLastFrame Time since last frame
	 */
	public void update(float timeSinceLastFrame) {
		if (!alive)
			return;

		timeSinceLastShot += timeSinceLastFrame;

		if (Keyboard.isKeyDown(KeyEvent.VK_W))
			f_posy -= speed * timeSinceLastFrame;
		if (Keyboard.isKeyDown(KeyEvent.VK_S))
			f_posy += speed * timeSinceLastFrame;
		if (Keyboard.isKeyDown(KeyEvent.VK_A))
			f_posx -= speed * timeSinceLastFrame;
		if (Keyboard.isKeyDown(KeyEvent.VK_D))
			f_posx += speed * timeSinceLastFrame;

		if (timeSinceLastShot > DELAY_RATE
				&& Keyboard.isKeyDown(KeyEvent.VK_SPACE)) {
			bullets.add(new Bullet((int) f_posx, (int) f_posy, 500, 0, bullets));
			timeSinceLastShot = 0;
		}

		if (f_posx < 0)
			f_posx = 0;
		if (f_posy < 0)
			f_posy = 0;
		if (f_posx > worldsize_x - bounding.getWidth())
			f_posx = (float) (worldsize_x - bounding.getWidth());
		if (f_posy > worldsize_y - bounding.getHeight())
			f_posy = (float) (worldsize_y - bounding.getHeight());

		bounding.setFrame(f_posx, f_posy, look.getWidth(), look.getHeight());

		for (int i = 0; i < enemies.size(); i++) {
			Enemy enemy = enemies.get(i);
			if (enemy.isAlive() && Collision.intersects(bounding, enemy.getBounding()))
				alive = false;
		}
	}

	/**
	 * Returns the bounding of a bullet for collision detection
	 * 
	 * @return Bounding of a bullet for collision detection
	 */
	public Ellipse2D getBounding() {
		return bounding;
	}

	/**
	 * Returns the image of a player
	 * 
	 * @return Image of a player
	 */
	public BufferedImage getLook() {
		if (alive)
			return look;
		else
			return lookDead;
	}
	
	/**
	 * Returns the position on the x-axis
	 * 
	 * @return Position on the x-axis
	 */
	public double getX() {
		return bounding.getX();
	}
	
	/**
	 * Returns the position on the y-axis
	 * 
	 * @return Position on the y-axis
	 */
	public double getY() {
		return bounding.getY();
	}
}