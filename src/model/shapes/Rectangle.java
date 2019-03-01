package model.shapes;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import model.commands.Command;
import model.Point;
import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import view.visitors.ShapeVisitor;

/**
 * A Rectangle is a rectangle Shape that represents the geometric figure with all data. A Rectangle
 * has a width, a height, a string name, a location for the top left corner of the shape, the time
 * that the shape appears at, the time the shape disappears at, and a list of all its commands.
 */
public class Rectangle extends ShapeImpl {

  /**
   * Constructs a rectangle.
   *
   * @param id The name or identifier for the shape.
   * @param width The width of the rectangle.
   * @param height The height of the rectangle.
   * @param location The location of shape in space.
   * @param color The color of the shape.
   * @param appearsAt The time that the shape appears at.
   * @param disappearsAt The time that the shape disappears at.
   * @param commands The list of commands for the shape.
   */
  public Rectangle(String id, float width, float height, Point location, float angle, Color color,
      int level,
      int appearsAt, int disappearsAt, List<Command> commands) throws IllegalArgumentException {
    super(id, width, height, location, angle, color, level, appearsAt, disappearsAt, commands);
    if (width < 0 || height < 0) {
      throw new IllegalArgumentException("width and height cannot be negative");
    }
  }

  /**
   * Constructs a rectangle.
   *
   * @param width The width of the rectangle.
   * @param height The height of the rectangle.
   * @param id The name or identifier for the shape.
   * @param location The location of shape in space.
   * @param color The color of the shape.
   * @param appearsAt The time that the shape appears at.
   * @param disappearsAt The time that the shape disappears at.
   */
  public Rectangle(String id, float width, float height, Point location, float angle,
      Color color, int level, int appearsAt, int disappearsAt) throws IllegalArgumentException {
    super(id, width, height, location, angle, color, level, appearsAt, disappearsAt);
    if (width < 0 || height < 0) {
      throw new IllegalArgumentException("width and height cannot be negative");
    }
  }

  @Override
  public Shape createShape(float width, float height, Point location, float angle, Color color,
      int level) {
    List<Command> commandCopy = new LinkedList<>();
    commandCopy.addAll(this.commands);
    return new Rectangle(this.id, width, height, location, angle, color, level, this.appearsAt,
        this.disappearsAt, commandCopy);
  }

  /**
   * Returns a string telling what the dimensions should be referred to as X distance, Y distance.
   */
  @Override
  public String[] dimensionIdentifier() {
    String[] str = new String[2];
    str[0] = "width";
    str[1] = "length";
    return str;
  }

  /**
   * Converts to svg.
   */
  @Override
  public Map<String, String> fieldToSvg() {
    Map svg = new HashMap();
    svg.put("height", "height");
    svg.put("width", "width");
    svg.put("xLocation", "x");
    svg.put("yLocation", "y");
    return svg;
  }

  /**
   * Creates a deep copy of this rectangle.
   *
   * @return A circle with all the same data as this rectangle.
   */
  @Override
  public Shape copy() {
    List<Command> commandCopy = new LinkedList<>();
    for (Command command : this.commands) {
      commandCopy.add(command.copy());
    }
    return new Rectangle(this.id, this.width, this.width, this.location.copy(),
        this.angle, new Color(this.color.getRGB()), this.level,
        this.appearsAt, this.disappearsAt, commandCopy);
  }

  /**
   * Converts the shape to a formatted string output.
   *
   * @return A String representing the state of the shape.
   */
  @Override
  public String toString(int ticksPerSecond) {
    DecimalFormat df = new DecimalFormat("0.0");
    return "Name: " + id + "\nType: rectangle\nCorner: (" + this.location.getX() + ", "
        + this.location.getY() + "), Width: " + this.width + ", Height: " + this.width
        + ", Color: (" +
        df.format(this.color.getRed() / 255.0) + "," + df.format(this.color.getGreen() / 255.0)
        + "," + df.format(this.color.getBlue() / 255.0) + ")\n" +
        this.timeToString(ticksPerSecond) + "\n\n";
  }

  /**
   * Accepts a visitor.
   *
   * @param visitor A visitor type that will act on the shape as union data.
   * @param <R> Output type determined by visitor.
   * @return Visitor implemented output type to be determined at runtime.
   */
  @Override
  public <R> R accept(ShapeVisitor<R> visitor) {
    return visitor.visitRectangle(this);
  }

}
