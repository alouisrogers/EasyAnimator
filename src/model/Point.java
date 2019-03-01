package model;

/**
 * A Point represents an X and Y coordinate location.
 */
public class Point {

  private float x;
  private float y;

  /**
   * Constructs a Point.
   *
   * @param x the x coordinate of the point.
   * @param y the y coordinate of the point.
   */
  public Point(float x, float y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Creates a deep copy of the point with the same x and y value for location.
   */
  public Point copy() {
    return new Point(this.x, this.y);
  }

  /**
   * Gets the X coordinate of this point.
   *
   * @return a float representing the X coordinate.
   */
  public float getX() {
    return this.x;
  }

  /**
   * Gets the Y coordinate of the this point.
   *
   * @return a float representing the Y coordinate.
   */
  public float getY() {
    return this.y;
  }

  /**
   * Sets the X coordinate oif this point to the given value.
   *
   * @param x the new X coordinate of the point.
   */
  public void setX(float x) {
    this.x = x;
  }

  /**
   * Sets the Y coordinate of the this point to the given value.
   *
   * @param y The new Y coordinate of the point.
   */
  public void setY(float y) {
    this.y = y;
  }


}
