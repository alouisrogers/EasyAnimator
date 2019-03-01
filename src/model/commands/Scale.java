package model.commands;

import java.util.HashMap;
import java.util.Map;
import model.shapes.Shape;
import view.visitors.CommandVisitor;

/**
 * Scale represents a command that floats the size of an object by changing a shapes given
 * dimensions by doubling them A Scale objects has a start time, a duration, and a percent change
 * which is always 200 percent.
 */
public class Scale extends CommandImpl {

  private float fromSx;
  private float fromSy;
  private float toSx;
  private float toSy;

  /**
   * Constructs a Scale Object.
   *
   * @param startTime The time that command will start executing.
   */
  public Scale(float fromSx, float fromSy, float toSx, float toSy, int startTime, int endTime)
      throws IllegalArgumentException {
    super(startTime, endTime);
    if (fromSx < 0 || fromSy < 0 || toSx < 0 || toSy < 0) {
      throw new IllegalArgumentException("Percentage change cannot be negative");
    }
    this.fromSx = fromSx;
    this.fromSy = fromSy;
    this.toSx = toSx;
    this.toSy = toSy;
  }

  /**
   * Creates a deep copy of this command.
   *
   * @return A Command with identical data as this command.
   */
  @Override
  public Command copy() {
    return new Scale(this.fromSx, this.fromSy, this.toSx, this.toSy, this.startTime, this.endTime);
  }

  /**
   * Accepts a visitor.
   *
   * @param visitor A visitor type that will act on the shape as union data.
   * @return Visitor implemented output type to be determined at runtime.
   */
  @Override
  public <R> R accept(CommandVisitor<R> visitor, Shape shape) throws IllegalArgumentException {
    return visitor.visitScale(this, shape);
  }

  /**
   * floats the size of the shape over the duration time interval. Executes the doubling process up
   * to the given time if time is less than the duration showing the shape only partially floated.
   *
   * @param shape The given shape that is to be floated.
   * @param time The time at which the state of the shape in the process of doubling is evaluated.
   */
  @Override
  public Shape execute(Shape shape, int time) throws IllegalArgumentException {
    if (shape == null || time < 0) {
      throw new IllegalArgumentException("Shape cannot be null");
    }

    float changeSx = this.dimensionCalculation(this.fromSx, this.toSx, time);
    float changeSy = this.dimensionCalculation(this.fromSy, this.toSy, time);

    return shape.createShape(changeSx, changeSy, shape.getLocation(),
        shape.getAngle(), shape.getColor(), shape.getLevel());

  }

  /**
   * Converts this command to a string representing the command UP TO the given time.
   *
   * @param shape The shape on which the command will execute.
   * @param ticksPerSecond The ticks per second that the command will occur.
   * @return A string representing the state of the command on the shape at the given time.
   */
  @Override
  public String toString(Shape shape, int ticksPerSecond) throws IllegalArgumentException {
    if (shape == null) {
      throw new IllegalArgumentException("Shape cannot be null");
    }
    String[] str = shape.dimensionIdentifier();
    return "Shape " + shape.getShapeName() + " scales from " + str[0] + ": "
        + this.fromSx + ", " + str[1] + ": " + this.fromSy + " to "
        + str[0] + ": " + this.toSx + ", " + str[1] + ": " + this.toSy + " " + this
        .timeToString(ticksPerSecond)
        + "\n";
  }

  @Override
  public Map<String, Float> getData() {
    Map<String, Float> data = new HashMap<>();
    data.put("fromSx", fromSx);
    data.put("fromSy", fromSy);
    data.put("toSx", toSx);
    data.put("toSy", toSy);
    return data;
  }

  /**
   * Checks whether is another command of the same type present in the time interval.
   */
  @Override
  public boolean commandOverlap(Command command) {
    if (command instanceof Scale) {
      return (command.getEndTime() > this.startTime && this.startTime > command.getStartTime()
          && this.endTime > command.getEndTime())
          || (this.endTime > command.getStartTime() && command.getEndTime() > this.endTime
          && command.getStartTime() > this.startTime);
    } else {
      return false;
    }
  }
}