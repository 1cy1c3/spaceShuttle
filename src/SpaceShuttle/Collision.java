package SpaceShuttle;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * Manages the collision detection for several figures
 * 
 * @author Rune Krauss
 */
public class Collision {
	/**
	 * Calculates the area of the hypotenuse square
	 * 
	 * @param a Length of cathetus a
	 * @param b Length of cathetus b
	 * @return Length of hypotenuse c
	 */
	private static double pythagoras(float a, float b) {
		return (a * a + b * b);
	}

	/**
	 * Collision detection of two rectangles
	 * 
	 * @param rect Rect a
	 * @param rect2 Rect b
	 * @return Possible collision
	 */
	public static boolean intersects(Rectangle2D rect, Rectangle2D rect2) {
		boolean first = rect2.getX() < rect.getX() + rect.getWidth();
		boolean second = rect2.getX() + rect2.getWidth() > rect.getX();
		boolean third = rect2.getY() < rect2.getY() + rect.getHeight();
		boolean fourth = rect2.getY() + rect2.getHeight() > rect.getY();

		return (first && second && third && fourth);
	}

	/**
	 * Collision detection of two circles
	 * 
	 * @param circle Circle a
	 * @param circle2 Circle b
	 * @return Possible collision
	 */
	public static boolean intersects(Ellipse2D circle, Ellipse2D circle2) {
		float a = (float) (circle.getX() - circle2.getX());
		float b = (float) (circle.getY() - circle2.getY());
		double c = pythagoras(a, b);

		return (c <= ((circle.getWidth() / 2) + (circle2.getWidth() / 2))
				* ((circle.getWidth() / 2) + (circle2.getWidth() / 2)));
	}

	/**
	 * Collision detection of a rectangle and a circle
	 * 
	 * @param rect
	 * @param circle
	 * @return Possible collision
	 */
	public static boolean intersects(Rectangle2D rect, Ellipse2D circle) {
		boolean first = circle.getX() > rect.getX()
				&& circle.getX() < rect.getX() + rect.getWidth();
		boolean second = circle.getY() > rect.getY()
				&& circle.getY() < rect.getY() + rect.getHeight();
		if (first && second)
			return true;

		float a = (float) (circle.getX() - rect.getX());
		float b = (float) (circle.getY() - rect.getY());
		double c = pythagoras(a, b);
		boolean third = c <= ((circle.getWidth() / 2) * (circle.getWidth() / 2));
		if (third)
			return true;
		float a2 = (float) (circle.getX() - rect.getX());
		float b2 = (float) (circle.getY() - rect.getY() + rect.getHeight());
		double c2 = pythagoras(a2, b2);
		boolean fourth = c2 <= ((circle.getWidth() / 2) * (circle.getWidth() / 2));
		if (fourth)
			return true;
		float a3 = (float) (circle.getX() - rect.getX() + rect.getWidth());
		float b3 = (float) (circle.getY() - rect.getY());
		double c3 = pythagoras(a3, b3);
		boolean fifth = c3 <= ((circle.getWidth() / 2) * (circle.getWidth() / 2));
		if (fifth)
			return true;
		float a4 = (float) (circle.getX() - rect.getX() + rect.getWidth());
		float b4 = (float) (circle.getY() - rect.getY() + rect.getHeight());
		double c4 = pythagoras(a4, b4);
		boolean sixth = c4 <= ((circle.getWidth() / 2) * (circle.getWidth() / 2));
		if (sixth)
			return true;

		if (first || second) {
			double distanceTop = circle.getY() - rect.getY();
			double distanceBottom = (circle.getY() - rect.getY())
					+ rect.getHeight();
			if (distanceTop > distanceBottom) {
				boolean seventh = distanceBottom < (circle.getWidth() / 2);
				if (seventh)
					return true;
			}
		}
		return false;
	}
}
