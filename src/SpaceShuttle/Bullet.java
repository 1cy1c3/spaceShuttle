package SpaceShuttle;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * Manages the bullets, creates and updates it
 * 
 * @author Rune Krauss
 */
public class Bullet {
	/**
	 * Image of the bullet
	 */
	private static BufferedImage look;

	/**
	 * Position on the x-axis
	 */
	private float f_posx;
	
	/**
	 * Position on the y-axis
	 */
	private float f_posy;
	
	/**
	 * Speed on the x-axis
	 */
	private float f_speedx;
	
	/**
	 * Speed on the y-axis
	 */
	private float f_speedy;

	/**
	 * Bounding of the bullet for collision detection
	 */
	private Rectangle2D bounding;

	/**
	 * List of bullets
	 */
	private List<Bullet> bullets;
	
	/**
	 * Existence period of a bullet
	 */
	private float timeAlive;
	private final float TIME_TO_ALIVE = 10;

	static {
		try {
			look = ImageIO.read(Bullet.class.getClassLoader()
					.getResourceAsStream("gfx/shot.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Instantiates a list of bullets
	 * 
	 * @param x Position on the x-axis
	 * @param y Position on the y-axis
	 * @param speed_x Speed on the x-axis
	 * @param speed_y Speed on the y-axis
	 * @param bullets List of bullets
	 */
	public Bullet(int x, int y, float speed_x, float speed_y,
			List<Bullet> bullets) {
		f_posx = x;
		f_posy = y;
		f_speedx = speed_x;
		f_speedy = speed_y;
		bounding = new Rectangle2D.Float(x, y, look.getWidth(),
				look.getHeight());
		this.bullets = bullets;
	}

	/**
	 * Updates bullets regarding the position
	 * 
	 * @param timeSinceLastFrame Time since last frame
	 */
	public void update(float timeSinceLastFrame) {
		timeAlive += timeSinceLastFrame;
		if (timeAlive > TIME_TO_ALIVE)
			bullets.remove(this);
		f_posx += f_speedx * timeSinceLastFrame;
		f_posy += f_speedy * timeSinceLastFrame;
		bounding.setRect(f_posx, f_posy, look.getWidth(), look.getHeight());
	}

	/**
	 * Returns the image of a bullet
	 * 
	 * @return Image of the bullet
	 */
	public BufferedImage getLook() {
		return look;
	}

	/**
	 * Returns the bounding of a bullet for collision detection
	 * 
	 * @return Bounding of the bullet for collision detection
	 */
	public Rectangle2D getBounding() {
		return (Rectangle2D) bounding;
	}
}