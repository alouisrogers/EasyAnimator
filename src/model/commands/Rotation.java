package model.commands;

import java.util.HashMap;
import java.util.Map;
import model.shapes.Shape;
import view.visitors.CommandVisitor;

/**
 * A rotation object will rotate shapes about their center a
 * specified angle for the duration given.
 */
public class Rotation extends CommandImpl {

  float startAngle;
  float endAngle;

  /**
   * Constructs a rotation.
   */
  Rotation(float startAngle, float endAngle, int startTime, int endTime) {
    super(startTime, endTime);
    this.startAngle = startAngle;
    this.endAngle = endAngle;
  }


  /**
   * Executes the command on the given shape at the given time mutating the shape to its post
   * command state.
   *
   * @param shape The shape to be executed on and mutated.
   */
  @Override
  public Shape execute(Shape shape, int time) throws IllegalArgumentException {
    if (shape == null || time < 0) {
      throw new IllegalArgumentException("Shape cannot be null");
    }

    float dTheta = this.dimensionCalculation(this.startAngle, this.endAngle, time);

    return shape.createShape(shape.getWidth(), shape.getHeight(),
        shape.getLocation(), dTheta, shape.getColor(), shape.getLevel());
  }

  /**
   * Converts this command to a string at the given state up to the time t.
   *
   * @param shape The shape that the command acts on.
   * @param ticksPerSecond The time that the string is to be evaluated at.
   * @return A String representing the state of the command UP TO the given time.
   */
  @Override
  public String toString(Shape shape, int ticksPerSecond) throws IllegalArgumentException {
    if (shape == null) {
      throw new IllegalArgumentException("Shape cannot be null");
    }
    String[] str = shape.dimensionIdentifier();
    return "Shape " + shape.getShapeName() + " rotates from Angle: "
        + this.startAngle + ", to Angle: " + this.endAngle + ", " +
        this.timeToString(ticksPerSecond) + "\n";
  }

  @Override
  public Map<String, Float> getData() {
    Map<String, Float> data = new HashMap();
    data.put("startAngle", this.startAngle);
    data.put("endAngle", this.endAngle);
    return data;
  }

  /**
   * Checks whether is another command of the same type present in the time interval.
   */
  @Override
  public boolean commandOverlap(Command command) {
    if (command instanceof Rotation) {
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
    return new Rotation(this.startAngle, this.endAngle, this.startTime, this.endTime);
  }

  /**
   * Accepts a visitor.
   *
   * @param visitor A visitor type that will act on the shape as union data
   * @return Visitor implemented output type to be determined at runtime.
   */
  @Override
  public <R> R accept(CommandVisitor<R> visitor, Shape shape) throws IllegalArgumentException {
    return visitor.visitRotation(this, shape);
  }
}
