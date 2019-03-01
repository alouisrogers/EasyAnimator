package model.commands;

import java.util.Map;
import model.shapes.Shape;
import view.visitors.CommandVisitor;

/**
 * A Command is a function object that will alter that state of a shape that it acts over in order
 * to change the shapes state and create an animation.
 */
public interface Command {

  /**
   * Executes the command on the given shape at the given time mutating the shape to its post
   * command state.
   *
   * @param shape The shape to be executed on and mutated.
   */
  Shape execute(Shape shape, int time) throws IllegalArgumentException;

  /**
   * Converts this command to a string at the given state up to the time t.
   *
   * @param shape The shape that the command acts on.
   * @param ticksPerSecond The ticks per second that the command occurs.
   * @return A String representing the state of the command UP TO the given time.
   */
  String toString(Shape shape, int ticksPerSecond) throws IllegalArgumentException;

  /**
   * Gets the data describing the change of the command.
   */
  Map<String, Float> getData();

  /**
   * Gets the time that this command begins to execute at.
   *
   * @return An integer representing the start time of this command.
   */
  int getStartTime();

  /**
   * Gets the time that this command terminates to execution at.
   *
   * @return An integer representing the start time of this command.
   */
  int getEndTime();

  /**
   * Checks whether is another command of the same type present in the time interval.
   */
  boolean commandOverlap(Command command);

  /**
   * Creates a deep copy of this command.
   *
   * @return A Command with identical data as this command.
   */
  Command copy();

  /**
   * Accepts a visitor.
   *
   * @param visitor A visitor type that will act on the shape as union data.
   * @param <R> Output type determined by visitor.
   * @return Visitor implemented output type to be determined at runtime.
   */
  public abstract <R> R accept(CommandVisitor<R> visitor, Shape shape)
      throws IllegalArgumentException;

}

