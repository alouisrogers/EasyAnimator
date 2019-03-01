package model.commands;

import java.util.HashMap;
import java.util.Map;
import model.Point;
import model.shapes.Shape;
import view.visitors.CommandVisitor;

/**
 * A Move Object executes a change of position in the X direction. A Move object has an X distance
 * to move, a duration over which the move will occur and a start time when the move will begin.
 */
public class Move extends CommandImpl {

  private Point toLoc;
  private Point fromLoc;

  /**
   * Create a instance of the Move object.
   *
   * @param startTime The time at which the state of the move will be evaluated up to.
   * @param endTime The length of time that the move will last.
   */
  public Move(Point fromLoc, Point toLoc, int startTime, int endTime) {
    super(startTime, endTime);
    this.fromLoc = fromLoc;
    this.toLoc = toLoc;
  }

  /**
   * Changes the position of the shape in the X direction to where it would be at the given time. If
   * the given time is greater than the end time the whole move will be complete, but if the move
   * has not completed at the given time, the move will be shown as partially complete.
   *
   * @param shape The shape that will be moved.
   * @param time The time at which the command will act on the shape.
   */
  @Override
  public Shape execute(Shape shape, int time) throws IllegalArgumentException {
    if (shape == null || time < 0) {
      throw new IllegalArgumentException("Shape cannot be null");
    }

    float x = this.dimensionCalculation(this.fromLoc.getX(), this.toLoc.getX(), time);
    float y = this.dimensionCalculation(this.fromLoc.getY(), this.toLoc.getY(), time);

    return shape
        .createShape(shape.getWidth(), shape.getHeight(), new Point(x, y),
            shape.getAngle(), shape.getColor(), shape.getLevel());
  }


  /**
   * Converts this command to a string representing the command UP TO the given time.
   *
   * @param shape The shape on which the command will execute.
   * @param ticksPerSecond The time at which the string state will be evaluated.
   * @return A string representing the state of the command on the shape at the given time.
   */
  @Override
  public String toString(Shape shape, int ticksPerSecond) throws IllegalArgumentException {
    if (shape == null) {
      throw new IllegalArgumentException("Shape cannot be null");
    }

    return "Shape " + shape.getShapeName() + " moves from (" + this.fromLoc.getX() + ", "
        + this.fromLoc.getY()
        + ") to (" + this.toLoc.getX() + ", " + this.toLoc.getY()
        + ") " + this.timeToString(ticksPerSecond) + "\n";

  }

  @Override
  public Map<String, Float> getData() {
    Map<String, Float> data = new HashMap<>();
    data.put("fromX", this.fromLoc.getX());
    data.put("fromY", this.fromLoc.getY());
    data.put("toX", this.toLoc.getX());
    data.put("toY", this.toLoc.getY());
    return data;
  }

  /**
   * Checks whether is another command of the same type present in the time interval.
   */
  @Override
  public boolean commandOverlap(Command command) {
    if (command instanceof Move) {
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
   * @return A Command with identical data as this command.
   */
  @Override
  public Command copy() {
    return new Move(fromLoc.copy(), toLoc.copy(), this.startTime, this.endTime);
  }

  /**
   * Accepts a visitor.
   *
   * @param visitor A visitor type that will act on the shape as union data
   * @return Visitor implemented output type to be determined at runtime.
   */
  @Override
  public <R> R accept(CommandVisitor<R> visitor, Shape shape) throws IllegalArgumentException {
    return visitor.visitMove(this, shape);
  }
}
