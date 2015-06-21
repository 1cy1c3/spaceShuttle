package SpaceShuttle;

import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * Manages the enemies, creates and updates them
 * 
 * @author Rune Krauss
 */
public class Enemy {
	/**
	 * Position on the x-axis
	 */
	private float f_posx;
	
	/**
	 * Position on the y-axis
	 */
	private float f_posy;
	
	/**
	 * List for animation images
	 */
	private static BufferedImage[] look = new BufferedImage[4];
	
	/**
	 * Image of a dead enemy
	 */
	private static BufferedImage lookDead;
	
	/**
	 * Bounding of the enemy for collision detection
	 */
	private Ellipse2D bounding;
	
	/**
	 * Time for animation images
	 */
	private float timeAnimate = 0;
	
	/**
	 * Threshold value for animation images
	 */
	private final float TIME_TO_ANIMATE = 1.0f;
	
	/**
	 * Speed of the enemy
	 */
	private float speed;
	
	/**
	 * Coincidence regarding the creation of enemies
	 */
	private Random rand = new Random();
	
	/**
	 * Width of the space
	 */
	int worldsize_x;
	
	/**
	 * Height of the space
	 */
	int worldsize_y;
	
	/**
	 * List of bullets
	 */
	List<Bullet> bullets;
	
	/**
	 * Flag for the life of an enemy
	 */
	private boolean alive = true;
	
	/**
	 * Instance of the protagonist
	 */
	Player player;

	static {
		try {
			look[0] = ImageIO.read(Enemy.class.getClassLoader()
					.getResourceAsStream("gfx/enemy1.png"));
			look[1] = ImageIO.read(Enemy.class.getClassLoader()
					.getResourceAsStream("gfx/enemy2.png"));
			look[2] = ImageIO.read(Enemy.class.getClassLoader()
					.getResourceAsStream("gfx/enemy3.png"));
			look[3] = look[1];
			lookDead = ImageIO.read(Enemy.class.getClassLoader()
					.getResourceAsStream("gfx/enemy_broken.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Instantiates an enemy
	 * 
	 * @param x Position on the x-axis
	 * @param y Position on the y-axis
	 * @param worldsize_x Width of the space
	 * @param worldsize_y Height of the space
	 * @param speed Speed of the enemy
	 * @param bullets List of bullets
	 * @param player Protagonist
	 */
	public Enemy(float x, float y, int worldsize_x, int worldsize_y,
			int speed, List<Bullet> bullets, Player player) {
		f_posx = x;
		f_posy = y;
		this.speed = rand.nextInt(speed) + 50;
		this.speed = speed;
		bounding = new Ellipse2D.Float(x, y, look[0].getWidth(),
				look[0].getHeight());
		this.bullets = bullets;
		this.worldsize_x = worldsize_x;
		this.worldsize_y = worldsize_y;
		this.player = player;
	}

	/**
	 * Updates an enemy regarding the position
	 * 
	 * @param timeSinceLastFrame Time since last frame
	 */
	public void update(float timeSinceLastFrame) {
		timeAnimate += timeSinceLastFrame;
		if (timeAnimate > TIME_TO_ANIMATE)
			timeAnimate = 0;
		
		/* Axis based enemy movement
		// Top
		if (player.getY() - f_posy < -10 && isAlive())
			f_posy -= speed * timeSinceLastFrame;
		// Bottom
		else if (player.getY() - f_posy > 10 && isAlive())
			f_posy += speed * timeSinceLastFrame;
		// Right
		if (player.getX() > f_posx && isAlive())
			f_posx += speed * timeSinceLastFrame;
		// Left
		if (player.getX() < f_posx && isAlive())
			f_posx -= speed * timeSinceLastFrame;
		*/
		
		if( isAlive()) {
			float speedX = (float) (player.getX() - f_posx);
			float speedY = (float) (player.getY() - f_posy);
			float length = (float) Math.sqrt((speedX * speedX) + (speedY * speedY));
			
			if (length != 0) {
				float xlength_1 = speedX / length;
				float ylength_1 = speedY / length;

				f_posx += speed * xlength_1 * timeSinceLastFrame;
				f_posy += speed * ylength_1 * timeSinceLastFrame;
			}
		}
		
		if (!isAlive())
			f_posx -= speed * timeSinceLastFrame;
		
		if (f_posx <= -bounding.getWidth()) {
			f_posx = worldsize_x;
			f_posy = rand.nextInt(worldsize_y - getLook().getHeight());
			alive = true;
		}

		for (int i = 0; i < bullets.size(); i++) {
			Bullet bullet = bullets.get(i);

			if (alive && Collision.intersects(bullet.getBounding(), bounding)) {
				alive = false;
				bullets.remove(bullet);
			}
		}

		bounding.setFrame(f_posx, f_posy, bounding.getWidth(),
				bounding.getHeight());
	}

	/**
	 * Check whether an opponent is still alive
	 * 
	 * @return Life
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 * Returns the bounding of an enemy
	 * 
	 * @return Bounding
	 */
	public Ellipse2D getBounding() {
		return (Ellipse2D) bounding;
	}

	/**
	 * Returns the image of an enemy
	 * 
	 * @return Image
	 */
	public BufferedImage getLook() {
		if (!alive)
			return lookDead;
		if (look.length == 0) {
			return null;
		}
		for (int i = 0; i < look.length; i++) {
			if (timeAnimate < (float) (TIME_TO_ANIMATE / look.length * (i + 1)))
				return look[i];
		}
		return look[look.length - 1];
	}

	/**
	 * Get the height of an enemy
	 * 
	 * @return Height
	 */
	public static int getHeight() {
		return look[0].getHeight();
	}

	/**
	 * Get the width of an enemy
	 * 
	 * @return Width
	 */
	public static int getWidth() {
		return look[0].getWidth();
	}
}