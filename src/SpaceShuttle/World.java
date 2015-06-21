package SpaceShuttle;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Manages the space, creates and updates it
 * 
 * @author Rune Krauss
 */
public class World {
	/**
	 * Position
	 */
	private float f_posx;
	
	/**
	 * Speed
	 */
	private float f_speed;
	
	/**
	 * Image
	 */
	private BufferedImage look;

	/**
	 * Instantiates the space
	 * @param f_speed Speed
	 */
	public World(float f_speed) {
		this.f_speed = f_speed;
		try {
			look = ImageIO.read(getClass().getClassLoader()
					.getResourceAsStream("gfx/space.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates the space regarding the position
	 * 
	 * @param timeSinceLastFrame Time since last frame
	 */
	public void update(float timeSinceLastFrame) {
		f_posx -= f_speed * timeSinceLastFrame;
		if (f_posx < -look.getWidth()) {
			f_posx += look.getWidth();
		}
	}

	/**
	 * Get the position of the world
	 * @return Position
	 */
	public int getX() {
		return (int) f_posx;
	}

	/**
	 * Get the image of the world
	 * @return Image
	 */
	public BufferedImage getLook() {
		return look;
	}
}
