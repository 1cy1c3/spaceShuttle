package SpaceShuttle;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Keyboard handler to control keystrokes
 * 
 * @author Rune Krauss
 */
public class Keyboard implements KeyListener {
	private static boolean[] keys = new boolean[1024];

	/**
	 * Check if key is down 
	 * 
	 * @param keyCode Key code
	 * @return Keystroke
	 */
	public static boolean isKeyDown(int keyCode) {
		if (keyCode >= 0 && keyCode < keys.length) {
			return keys[keyCode];
		} else {
			return false;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode >= 0 && keyCode < keys.length) {
			keys[keyCode] = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode >= 0 && keyCode < keys.length) {
			keys[keyCode] = false;
		}
	}

	// Unused
	@Override
	public void keyTyped(KeyEvent e) {}
}