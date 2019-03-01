package view.background;

import java.awt.Color;

/**
 * A single color plain non changing background.
 */
public class BasicBackground implements IBackground {

  Color color;

  /**
   * Constructs a background.
   * @param color The color of the background.
   */
  public BasicBackground(Color color) {
    this.color = color;
  }

  /**
   * Gets the color of the background.
   * @return
   */
  @Override
  public Color getColor() {
    return this.color;
  }
}
