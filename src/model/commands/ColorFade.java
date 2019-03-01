package model.commands;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import model.shapes.Shape;
import java.awt.Color;
import view.visitors.CommandVisitor;

/**
 * Represents a command which changes the color of the shape gradually from the start time to the
 * given time A ColorFade has a start time, a duration, and a color to fade to.
 */
public class ColorFade extends CommandImpl {

  private Color oldColor;
  private Color newColor;

  /**
   * Constructs a color fade object.
   *
   * @param startTime The length of time over which the color transition occurs.
   */
  public ColorFade(Color oldColor, Color newColor, int startTime, int endTime)
      throws IllegalArgumentException {
    super(startTime, endTime);
    if (newColor == null || oldColor == null) {
      throw new IllegalArgumentException("Color cannot be null");
    }
    this.oldColor = oldColor;
    this.newColor = newColor;
  }

  /**
   * Changes the color from the initial color to the final color. If the given time is when the
   * color is transitioning, the color at that transition state will be given.
   *
   * @param shape The shape to transition colors.
   * @param time The current time to evaluate the state at.
   */
  @Override
  public Shape execute(Shape shape, int time) throws IllegalArgumentException {
    if (shape == null || time < 0) {
      throw new IllegalArgumentException("Shape cannot be null");
    }

    int rNew = Math
        .round((this.dimensionCalculation(this.oldColor.getRed(), this.newColor.getRed(), time)));
    int gNew = Math.round(
        (this.dimensionCalculation(this.oldColor.getGreen(), this.newColor.getGreen(), time)));
    int bNew = Math
        .round((this.dimensionCalculation(this.oldColor.getBlue(), this.newColor.getBlue(), time)));

    return shape.createShape(shape.getWidth(), shape.getHeight(), shape.getLocation(),
        shape.getAngle(), new Color(rNew, gNew, bNew), shape.getLevel());
  }


  /**
   * Converts the given command to a formatted string showing the.
   *
   * @param shape The shape that the command acts on.
   * @param ticksPerSecond The time to evaluate the string description of the command.
   * @return A formatted string representing the command.
   */
  @Override
  public String toString(Shape shape, int ticksPerSecond) throws IllegalArgumentException {
    if (shape == null) {
      throw new IllegalArgumentException("Shape cannot be null");
    }

    DecimalFormat df = new DecimalFormat("#.###");

    return "Shape " + shape.getShapeName() + " changes color from (" + df
        .format(oldColor.getRed() / 1.0) + "," + df.format(oldColor.getGreen() / 1.0)
        + "," + df.format(oldColor.getBlue() / 1.0) + ") to (" + df.format(newColor.getRed() / 1.0)
        + "," + df.format(newColor.getGreen() / 1.0)
        + "," + df.format(newColor.getBlue() / 1.0) + ") " + this.timeToString(ticksPerSecond)
        + "\n";
  }

  @Override
  public Map<String, Float> getData() {
    Map<String, Float> data = new HashMap<>();
    data.put("oldColor", (float) this.oldColor.getRGB());
    data.put("newColor", (float) this.newColor.getRGB());
    return data;
  }

  /**
   * Checks whether is another command of the same type present in the time interval.
   */
  @Override
  public boolean commandOverlap(Command command) {
    if (command instanceof ColorFade) {
      return (command.getEndTime() > this.startTime && this.startTime > command.getStartTime()
          && this.endTime > command.getEndTime())
          || (this.endTime > command.getStartTime() && command.getEndTime() > this.endTime
          && command.getStartTime() > this.startTime);
    } else {
      return false;
    }
  }


  /**
   * Creates a deep copy of this command.
   *
   * @return A Command that is an identical representation to the this one.
   */
  @Override
  public Command copy() {
    return new ColorFade(this.oldColor, this.newColor, this.startTime, this.endTime);
  }

  /**
   * Accepts a visitor.
   *
   * @param visitor A visitor type that will act on the shape as union data
   * @return Visitor implemented output type to be determined at runtime.
   */
  @Override
  public <R> R accept(CommandVisitor<R> visitor, Shape shape) throws IllegalArgumentException {
    return visitor.visitColorFade(this, shape);
  }

}

