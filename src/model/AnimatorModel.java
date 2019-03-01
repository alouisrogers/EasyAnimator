
package model;

import java.util.List;
import model.commands.Command;
import model.shapes.Shape;

/**
 * An AnimatorModel is the model for the Animator made in the MVC structure. An animator model has
 * shapes and modifies those shapes with given commands to create animation using the data returned
 * by the model.
 */
public interface AnimatorModel {

  /**
   * Adds the given shape to the Animators List of shapes.
   *
   * @param shape The shape to be added to the animators list of shapes.
   */
  void addShape(Shape shape) throws IllegalArgumentException;

  /**
   * Adds a commands to the shape with the given name.
   *
   * @param id The name of the shape to add the command to.
   * @param command The command to be added to the specified shape.
   */
  void addCommand(String id, Command command) throws IllegalArgumentException;

  /**
   * Removes the specified shape form the animators list of shapes.
   *
   * @param id The name of the shape to be removed.
   */
  void removeShape(String id) throws IllegalArgumentException;

  /**
   * Gets the states of each of the shapes as their data in a map at
   * the specified time meaning that all of the commands up to the
   * specified time given.
   *
   * @param time time that the states of all the shapes is to be evaluated at.
   * @return A Map of all hte shapes in their state at the given time.
   */
  List<Shape> getAnimatorState(int time) throws IllegalArgumentException;

  List<Shape> getShapes();

  /**
   * Returns the integer representing the last disappearing time for a shape,
   * to show how long the animation should last for in total.
   *
   * @return how long the animation is in total.
   */
  int getMaxTick();
}

