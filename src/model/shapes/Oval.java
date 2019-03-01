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
 * The Oval represents an Oval shape as geometric figure with the accompanying data An Oval has an x
 * radius, a y radius, an id for the name of the shape, the location of shape in space, the color of
 * the shape, the time that the shape appears at, the time that the shape disappears at, and a list
 * of all of its commands.
 */
public class Oval extends ShapeImpl {

  /**
   * Constructs an oval.
   *
   * @param id The name or identifier for the shape.
   * @param xRadius The x radius of the oval.
   * @param yRadius The y radius of the oval.
   * @param location The location of shape in space.
   * @param color The color of the shape.
   * @param appearsAt The time that the shape appears at.
   * @param disappearsAt The time that the shape disappears at.
   */
  public Oval(String id, float xRadius, float yRadius, Point location, float angle, Color color,
      int level,
      int appearsAt, int disappearsAt) throws IllegalArgumentException {
    super(id, xRadius, yRadius, location, angle, color, level, appearsAt, disappearsAt);
    if (xRadius < 0 || yRadius < 0) {
      throw new IllegalArgumentException("X radius and Y radius cannot be negative");
    }
  }

  /**
   * Constructs an oval.
   *
   * @param id The name or identifier for the shape.
   * @param xRadius The x radius of the oval.
   * @param yRadius The y radius of the oval.
   * @param location The location of shape in space.
   * @param color The color of the shape.
   * @param appearsAt The time that the shape appears at.
   * @param disappearsAt The time that the shape disappears at.
   * @param commands The list of commands for the oval.
   */
  public Oval(String id, float xRadius, float yRadius, Point location, float angle, Color color,
      int level,
      int appearsAt, int disappearsAt, List<Command> commands) throws IllegalArgumentException {
    super(id, xRadius, yRadius, location, angle, color, level, appearsAt, disappearsAt, commands);
    if (xRadius < 0 || yRadius < 0) {
      throw new IllegalArgumentException("X radius and Y radius cannot be negative");
    }
  }

  @Override
  public Shape createShape(float xRadius, float yRadius, Point location, float angle, Color color,
      int level) {
    List<Command> commandCopy = new LinkedList<>();
    commandCopy.addAll(this.commands);
    return new Oval(this.id, xRadius, yRadius, location, angle, color, level, this.appearsAt,
        this.disappearsAt, commandCopy);
  }

  /**
   * Returns a string telling what the dimensions should be referred to as X distance, Y distance.
   */
  @Override
  public String[] dimensionIdentifier() {
    String[] str = new String[2];
    str[0] = "X Radius";
    str[1] = "Y Radius";
    return str;
  }

  /**
   * Converts to svg.
   */
  @Override
  public Map<String, String> fieldToSvg() {
    Map svg = new HashMap();
    svg.put("height", "ry");
    svg.put("width", "rx");
    svg.put("xLocation", "cx");
    svg.put("yLocation", "cy");
    return svg;
  }

  /**
   * Creates a deep copy of this oval.
   *
   * @return A circle with all the same data as this oval.
   */
  @Override
  public Shape copy() {
    List<Command> commandCopy = new LinkedList<>();
    for (Command command : this.commands) {
      commandCopy.add(command.copy());
    }
    return new Oval(this.id, this.width, this.height, this.location.copy(),
        angle, new Color(this.color.getRGB()), this.level, this.appearsAt, this.disappearsAt,
        commandCopy);
  }

  /**
   * Converts the shape to a formatted string output.
   *
   * @return A String representing the state of the shape.
   */
  @Override
  public String toString(int ticksPerSecond) {
    DecimalFormat df = new DecimalFormat("0.0");
    double red = this.color.getRed() / 255.0;
    double green = this.color.getGreen() / 255.0;
    double blue = this.color.getBlue() / 255.0;
    return "Name: " + id + "\nType: oval\nCenter: (" + this.location.getX() + ", "
        + this.location.getY() + "), X radius: " + this.width + ", Y radius: " + this.height
        + ", Color: (" + df.format(red) + "," +
        df.format(green) + ","
        + df.format(blue) + ")\n" + this.timeToString(ticksPerSecond) + "\n\n";
  }

  /**
   * Accepts a visitor.
   *
   * @param visitor A visitor type that will act on the shape as union data.
   * @param <R> Output type determined by visitor.
   * @return Visitor implemented output type to be determined at runtime.
   */
  @Override
  public <R> R accept(ShapeVisitor<R> visitor) throws IllegalArgumentException {
    if (visitor == null) {
      throw new IllegalArgumentException("Visitor cannot be null");
    }
    return visitor.visitOval(this);
  }
}
