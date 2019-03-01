package model.shapes;

import java.util.List;
import java.util.Map;
import model.commands.Command;
import model.Point;
import java.awt.Color;
import view.visitors.ShapeVisitor;

public interface Shape {

  /**
   * Creates a deep copy of the shape.
   *
   * @return A Shape with the exact data as the original shape.
   */
  Shape copy();


  int getLevel();

  /**
   * Gets the location of this shape as deep copy of the Point object.
   *
   * @return A Point representing the location of this shape.
   */
  Point getLocation();

  /**
   * Gets a deep copy of the color of this shape.
   *
   * @returns The color of this shape.
   */
  Color getColor();

  /**
   * Gets the height of the shape.
   */
  float getHeight();

  /**
   * Gets the width of the shape.
   */
  float getWidth();

  /**
   * Adds a command to the List of commands in this shape in order based in the start time at which
   * the command will be executed.
   */
  void addCommand(Command command);

  /**
   * Gets the id of the shape as a string.
   *
   * @return A string representing the id of the shape.
   */
  String getShapeName();

  /**
   * Converts the shape to a formatted string output.
   *
   * @return A String representing the state of the shape.
   */
  String toString(int ticksPerSecond);

  /**
   * Gets the time that the shape appears at.
   *
   * @return An integer representing the time that the shape appears at.
   */
  int getAppearsAt();

  /**
   * Gets a deep copy of the shape in its current state at the specified time with all commands
   * running up the given point which serve to modify the shape state.
   *
   * @param time The time at which the state of the shape is to be evaluated at.
   */
  Shape getShapeState(int time);

  /**
   * Get the time at which the first command in this shape is run.
   *
   * @return A integer representing the time of the start time of the first command.
   */
  int animatesAt();

  int getDisappearsAt();

  /**
   * Gets the next command from the shape and remove it from the list of command mutating the shapes
   * list of commands.
   *
   * @return The command that comes next for the shape.
   */
  Command nextCommand();

  /**
   * Builds a new shape with the given data.
   */
  Shape createShape(float xRadius, float yRadius, Point location, float angle, Color color,
      int level);

  /**
   * Returns a string telling what the dimensions should be referred to as X distance, Y distance.
   */
  String[] dimensionIdentifier();

  /**
   * Converts to svg.
   */
  Map<String, String> fieldToSvg();

  /**
   * Gets the commands of the shapes.
   *
   * @return The list of commands.
   */
  List<Command> getCommands();

  /**
   * The angle of rotation of this shape.
   */
  float getAngle();

  /**
   * Accepts a visitor.
   *
   * @param visitor A visitor type that will act on the shape as union data.
   * @param <R> Output type determined by visitor.
   * @return Visitor implemented output type to be determined at runtime.
   */
  <R> R accept(ShapeVisitor<R> visitor);

}
