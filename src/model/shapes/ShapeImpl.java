
package model.shapes;

import java.util.ArrayList;
import model.commands.Command;
import model.comparators.CommandComparator;
import model.Point;
import java.awt.Color;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import view.visitors.ShapeVisitor;

/**
 * A ShapeImpl represents the abstract shape and the functionality that is true for all shapes A
 * ShapeImpl has a CommandComparator, a Point location, A Color color, A List of commands, a time of
 * appearance, a time of disappearance, and a string id.
 */
public abstract class ShapeImpl implements Shape {

  private Comparator cmdComp;
  protected Point location;
  protected Color color;
  protected List<Command> commands;
  protected int appearsAt;
  protected int disappearsAt;
  protected String id;
  protected float height;
  protected float width;
  private int commandCounter = 0;
  protected float angle;
  protected int level;

  /**
   * Constructs a StringImpl.
   *
   * @param id The name or identifier for the shape.
   * @param location The location of shape in space.
   * @param color The color of the shape.
   * @param appearsAt The time that the shape appears at.
   * @param disappearsAt The time that the shape disappears at.
   */
  protected ShapeImpl(String id, float width, float height, Point location, float angle,
      Color color, int level,
      int appearsAt, int disappearsAt) {
    if (id == null || id.equals("") || location == null || color == null || (disappearsAt
        <= appearsAt)
        || appearsAt < 0 || disappearsAt < 0 || width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Invalid constructor argument");
    }
    this.id = id;
    this.location = location;
    this.color = color;
    this.appearsAt = appearsAt;
    this.disappearsAt = disappearsAt;
    this.commands = new LinkedList<>();
    this.cmdComp = new CommandComparator();
    this.height = height;
    this.width = width;
    this.angle = angle;
    this.level = level;
  }

  /**
   * Constructs a StringImpl.
   *
   * @param id The name or identifier for the shape.
   * @param location The location of shape in space.
   * @param color The color of the shape.
   * @param appearsAt The time that the shape appears at.
   * @param disappearsAt The time that the shape disappears at.
   * @param commands The list of commands.
   */
  protected ShapeImpl(String id, float width, float height, Point location, float angle,
      Color color, int level,
      int appearsAt, int disappearsAt,
      List<Command> commands) {
    if (id == null || id.equals("") || location == null || color == null || (disappearsAt
        < appearsAt)
        || commands == null || appearsAt < 0 || disappearsAt < 0) {
      throw new IllegalArgumentException("Invalid constructor argument");
    }
    this.width = width;
    this.height = height;
    this.id = id;
    this.appearsAt = appearsAt;
    this.disappearsAt = disappearsAt;
    this.location = location;
    this.color = color;
    this.commands = commands;
    this.cmdComp = new CommandComparator();
    this.angle = angle;
    this.level = level;
  }

  public abstract Shape createShape(float xRadius, float yRadius, Point location, float angle,
      Color color, int level);

  /**
   * Creates a deep copy of the shape.
   *
   * @return A Shape with the exact data as the original shape.
   */
  public abstract Shape copy();

  /**
   * Gets the id of the shape as a string.
   *
   * @return A string representing the id of the shape.
   */
  @Override
  public String getShapeName() {
    return this.id;
  }

  /**
   * Get shape level
   */
  @Override
  public int getLevel() {
    return this.level;
  }

  /**
   * Gets the location of this shape as deep copy of the Point object.
   *
   * @return A Point representing the location of this shape.
   */
  @Override
  public Point getLocation() {
    return this.location.copy();
  }


  /**
   * Gets a deep copy of the color of this shape.
   *
   * @returns The color of this shape.
   */
  @Override
  public Color getColor() {
    return new Color(this.color.getRGB());
  }

  /**
   * Get the height of the shape.
   */
  @Override
  public float getHeight() {
    return this.height;
  }

  /**
   * Get the width of a shape.
   */
  @Override
  public float getWidth() {
    return this.width;
  }


  /**
   * The angle of rotation of this shape.
   */
  @Override
  public float getAngle() {
    return this.angle;
  }

  /**
   * Adds a command to the List of commands in this shape in order based in the start time at which
   * the command will be executed.
   */
  @Override
  public void addCommand(Command command) throws IllegalArgumentException {
    boolean overlap = false;
    for (Command c : this.commands) {
      overlap = overlap || c.commandOverlap(command);
    }
    if (command == null || overlap) {
      throw new IllegalArgumentException(
          "Command cannot be null or overlap with an existing command");
    }
    this.commands.add(command);
    this.commands.sort(this.cmdComp);
  }

  /**
   * Gets a deep copy of the shape in its current state at the specified time with all commands
   * running up the given point which serve to modify the shape state.
   *
   * @param time The time at which the state of the shape is to be evaluated at.
   */
  @Override
  public Shape getShapeState(int time) throws IllegalArgumentException {
    Shape stateShape = this;
    if (time < 0) {
      throw new IllegalArgumentException("Time cannot be negative");
    }
    for (Command command : commands) {
      stateShape = command.execute(stateShape, time);
    }
    return stateShape;
  }

  /**
   * Get the time at which the first command in this shape is run.
   *
   * @return A integer representing the time of the start time of the first command.
   */
  @Override
  public int animatesAt() {
    if (this.commands.size() > 0) {
      return this.commands.get(0).getStartTime();
    } else {
      return 0;
    }
  }

  /**
   * Converts the shape to a formatted string output.
   *
   * @return A String representing the state of the shape.
   */
  @Override
  public abstract String toString(int ticksPerSecond);

  /**
   * Gets the time that the shape appears at.
   *
   * @return An integer representing the time that the shape appears at.
   */
  @Override
  public int getAppearsAt() {
    return this.appearsAt;
  }

  /**
   * Gets the time that the shape disappears at.
   *
   * @return An integer representing the time that the shape disappears at
   */
  @Override
  public int getDisappearsAt() {
    return this.disappearsAt;
  }

  /**
   * Gets the next command from the shape and remove it from the list of command mutating the shapes
   * list of commands.
   *
   * @return The command that comes next for the shape.
   */
  @Override
  public Command nextCommand() throws IllegalArgumentException {
    if (this.commands.size() > commandCounter) {
      int count = commandCounter;
      commandCounter++;
      return this.commands.get(count);
    } else {
      throw new IllegalStateException("commands cannot be empty");
    }
  }

  @Override
  public List<Command> getCommands() {
    List<Command> commands = new ArrayList<>();
    for (Command command : this.commands) {
      commands.add(command.copy());
    }
    return commands;
  }

  /**
   * Accepts a visitor.
   *
   * @param visitor A visitor type that will act on the shape as union data.
   * @param <R> Output type determined by visitor.
   * @return Visitor implemented output type to be determined at runtime.
   */
  @Override
  public abstract <R> R accept(ShapeVisitor<R> visitor) throws IllegalArgumentException;

  /**
   * Creates a time representation of the start and end time given a ticks Per Second. If the ticks
   * per second is -1, displays as ticks.
   */
  protected String timeToString(int ticksPerSecond) {
    String time = "";
    if (ticksPerSecond == -1) {
      time = "Appears at t=" + this.appearsAt + "\nDisappears at t=" + this.disappearsAt;
    } else {
      time = "Appears at t=" + this.appearsAt / ticksPerSecond * 1.0 + "s\nDisappears at t=" +
          this.disappearsAt / ticksPerSecond * 1.0 + "s";
    }
    return time;
  }


}
