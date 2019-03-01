package model.commands;

import java.util.Map;
import model.shapes.Shape;

/**
 * A CommandImpl represents a Command and has the contains the common fields and functionality of
 * all commands. A CommandImpl has a start time and a duration which is the time interval or which
 * the command will act.
 */
abstract public class CommandImpl implements Command {

  protected int startTime;
  protected int duration;
  protected int endTime;

  /**
   * Constructs a CommandImpl.
   *
   * @param startTime The time at which the command will begin to run. the command will act.
   */
  protected CommandImpl(int startTime, int endTime) throws IllegalArgumentException {
    if (startTime < 0 || endTime < 0 || endTime < startTime) {
      throw new IllegalArgumentException("Cannot be negative");
    }
    this.startTime = startTime;
    this.endTime = endTime;
    this.duration = endTime - startTime;

  }

  /**
   * Calculate the change in dimensions.
   */
  protected float dimensionCalculation(float initialVal, float finalVal, int time) {
    float newVal;
    if (time < this.startTime) {
      newVal = initialVal;
    } else if (time <= this.startTime + this.duration) {
      newVal = (initialVal * (endTime - time) / (float) (duration))
          + (finalVal * (time - startTime) / (float) (duration));
    } else {
      newVal = finalVal;
    }
    return newVal;
  }

  /**
   * Converts this command to a string at the given state up to the time t.
   *
   * @param shape The shape that the command acts on.
   * @param time The time that the string is to be evaluated at.
   * @return A String representing the state of the command UP TO the given time.
   */
  public abstract String toString(Shape shape, int time) throws IllegalArgumentException;

  /**
   * Creates a time representation of the start and end time given a ticks Per Second. If the ticks
   * per second is -1, displays as ticks.
   */
  protected String timeToString(float ticksPerSecond) {
    String time = "";
    if (ticksPerSecond == -1) {
      time = "from t=" + this.startTime + "to t=" + this.endTime;
    } else {
      time =
          "from t=" + this.startTime / ticksPerSecond + "s to t=" + this.endTime / ticksPerSecond +
              "s";
    }
    return time;
  }

  @Override
  public abstract Map<String, Float> getData();

  /**
   * Gets the time that this command begins to execute at.
   *
   * @return An integer representing the start time of this command.
   */
  @Override
  public int getStartTime() {
    return startTime;
  }

  /**
   * Gets the time that this command terminates to execution at.
   *
   * @return An integer representing the start time of this command.
   */
  @Override
  public int getEndTime() {
    return this.endTime;
  }


  /**
   * Creates a deep copy of this command.
   *
   * @return A Command with identical data as this command.
   */
  @Override
  public abstract Command copy();


}

