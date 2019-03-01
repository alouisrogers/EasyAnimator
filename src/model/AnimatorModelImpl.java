
package model;

import java.awt.Color;
import java.util.ArrayList;
import model.commands.ColorFade;
import model.commands.Command;
import model.commands.Move;
import model.commands.Scale;
import model.shapes.Oval;
import model.shapes.Rectangle;
import model.shapes.Shape;
import java.util.List;
import util.TweenModelBuilder;


/**
 * An AnimatorModelImpl is the implementation of the model for the Animator made in the MVC
 * structure. An animator model has shapes and modifies those shapes with given commands to create
 * animation using the data returned by the model. An AnimatorModelImpl has a list of shapes that it
 * modifies and executes commands on.
 */
public class AnimatorModelImpl implements AnimatorModel {

  private List<Shape> shapes;

  /**
   * Constructs a new Animator Model Impl with the list of shapes in the animation set to an empty
   * list.
   */
  public AnimatorModelImpl() {
    this.shapes = new ArrayList<>();
  }

  /**
   * Returns the shape from this animation with the given String id. Return null if the shape does
   * not exist.
   *
   * @param id Id of the shape that is being searched for.
   * @return Shape with given ID
   */
  private Shape getShape(String id) {
    for (Shape shape : this.shapes) {
      if (shape.getShapeName().equals(id)) {
        return shape;
      }
    }
    return null;
  }

  /**
   * Adds the given shape to the Animators List of shapes.
   *
   * @param shape The shape to be added to the animators list of shapes.
   */
  public void addShape(Shape shape) throws IllegalArgumentException {
    Shape s = this.getShape(shape.getShapeName());
    if (shape == null || s != null) {
      throw new IllegalArgumentException("Shape cannot be null or already exist");
    }
    this.shapes.add(shape);
  }


  /**
   * Adds a commands to the shape with the given name.
   *
   * @param id The name of the shape to add the command to.
   * @param command The command to be added to the specified shape.
   */
  public void addCommand(String id, Command command) throws IllegalArgumentException {
    Shape s = this.getShape(id);
    if (command == null || id == null || s == null) {
      throw new IllegalArgumentException("Shape must exist");
    }
    s.addCommand(command);

  }

  /**
   * Removes the specified shape form the animators list of shapes.
   *
   * @param id The name of the shape to be removed.
   */
  public void removeShape(String id) throws IllegalArgumentException {
    Shape s = this.getShape(id);
    if (id == null || s == null) {
      throw new IllegalArgumentException("Shape must exist");
    }
    shapes.remove(s);
  }

  /**
   * Gets the states of each of the shapes as their data in a map at hte specified time meaning that
   * all of the commands up to the specified time given.
   *
   * @param time time that the states of all the shapes is to be evaluated at.
   * @return A Map of all hte shapes in their state at the given time.
   */
  public List<Shape> getAnimatorState(int time) throws IllegalArgumentException {
    if (time < 0) {
      throw new IllegalArgumentException("Time cannot be negative");
    }
    List<Shape> shapesList = new ArrayList<>();
    for (Shape shape : this.shapes) {
      if (shape.getAppearsAt() <= time && shape.getDisappearsAt() >= time) {
        shapesList.add(shape.getShapeState(time));
      }
    }
    return shapesList;
  }

  /**
   * Returns a copy of the shapes as data.
   */
  @Override
  public List<Shape> getShapes() {
    List<Shape> shapesCopy = new ArrayList<>();
    for (Shape shape : this.shapes) {
      shapesCopy.add(shape.copy());
    }
    return shapesCopy;
  }

  @Override
  public int getMaxTick() {
    int maxSoFar = 0;
    for (Shape shape : this.shapes) {
      if (shape.getDisappearsAt() > maxSoFar) {
        maxSoFar = shape.getDisappearsAt();
      }
    }
    return maxSoFar;
  }


  /**
   * Builder Implementation that allows files to be read into our model.
   */
  public static class TweenModelBuilderImpl implements TweenModelBuilder<AnimatorModel> {

    AnimatorModel builderModel;

    /**
     * Constructs a new TweenModelBuilderImpl that sets the builderModel field to a new version of
     * our model.
     */
    public TweenModelBuilderImpl() {
      this.builderModel = new AnimatorModelImpl();
    }

    /**
     * Add a new oval to the model with the given specifications.
     *
     * @param name the unique name given to this shape
     * @param cx the x-coordinate of the center of the oval
     * @param cy the y-coordinate of the center of the oval
     * @param xRadius the x-radius of the oval
     * @param yRadius the y-radius of the oval
     * @param red the red component of the color of the oval
     * @param green the green component of the color of the oval
     * @param blue the blue component of the color of the oval
     * @param startOfLife the time tick at which this oval appears
     * @param endOfLife the time tick at which this oval disappears
     * @return the builder object
     */
    @Override
    public TweenModelBuilder addOval(String name, float cx, float cy, float xRadius, float yRadius,
        float red, float green, float blue, int startOfLife, int endOfLife) {
      this.builderModel.addShape(
          new Oval(name, xRadius, yRadius, new Point(cx, cy), 0, new Color(red, green, blue), 0,
              startOfLife, endOfLife));
      return this;
    }

    /**
     * Add a new rectangle to the model with the given specifications.
     *
     * @param name the unique name given to this shape
     * @param lx the minimum x-coordinate of a corner of the rectangle
     * @param ly the minimum y-coordinate of a corner of the rectangle
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     * @param red the red component of the color of the rectangle
     * @param green the green component of the color of the rectangle
     * @param blue the blue component of the color of the rectangle
     * @param startOfLife the time tick at which this rectangle appears
     * @param endOfLife the time tick at which this rectangle disappears
     * @return the builder object
     */
    @Override
    public TweenModelBuilder addRectangle(String name, float lx, float ly, float width,
        float height,
        float red, float green, float blue, int startOfLife, int endOfLife) {
      this.builderModel.addShape(
          new Rectangle(name, width, height, new Point(lx, ly), 0, new Color(red, green, blue), 0,
              startOfLife, endOfLife));
      return this;
    }

    /**
     * Move the specified shape to the given position during the given time interval.
     *
     * @param name the unique name of the shape to be moved
     * @param moveFromX the x-coordinate of the initial position of this shape. What this
     *     x-coordinate represents depends on the shape.
     * @param moveFromY the y-coordinate of the initial position of this shape. what this
     *     y-coordinate represents depends on the shape.
     * @param moveToX the x-coordinate of the final position of this shape. What this x-coordinate
     *     represents depends on the shape.
     * @param moveToY the y-coordinate of the final position of this shape. what this y-coordinate
     *     represents depends on the shape.
     * @param startTime the time tick at which this movement should start
     * @param endTime the time tick at which this movement should end
     */
    @Override
    public TweenModelBuilder addMove(String name, float moveFromX, float moveFromY, float moveToX,
        float moveToY, int startTime, int endTime) {
      this.builderModel
          .addCommand(name, new Move(new Point(moveFromX, moveFromY), new Point(moveToX, moveToY),
              startTime, endTime));
      return this;
    }

    /**
     * Change the color of the specified shape to the new specified color in the specified time
     * interval.
     *
     * @param name the unique name of the shape whose color is to be changed
     * @param oldR the r-component of the old color
     * @param oldG the g-component of the old color
     * @param oldB the b-component of the old color
     * @param newR the r-component of the new color
     * @param newG the g-component of the new color
     * @param newB the b-component of the new color
     * @param startTime the time tick at which this color change should start
     * @param endTime the time tick at which this color change should end
     */
    @Override
    public TweenModelBuilder addColorChange(String name, float oldR, float oldG, float oldB,
        float newR, float newG, float newB, int startTime, int endTime) {
      this.builderModel.addCommand(name,
          new ColorFade(new Color((int) (255 * oldR), (int) (255 * oldG), (int) (255 * oldB)),
              new Color((int) (255 * newR), (int) (255 * newG), (int) (255 * newB)), startTime,
              endTime));
      return this;
    }

    /**
     * Change the x and y extents of this shape from the specified extents to the specified target
     * extents. What these extents actually mean depends on the shape, but these are roughly the
     * extents of the box enclosing the shape.
     */
    @Override
    public TweenModelBuilder addScaleToChange(String name, float fromSx, float fromSy, float toSx,
        float toSy, int startTime, int endTime) {
      this.builderModel.addCommand(name, new Scale(fromSx, fromSy, toSx, toSy, startTime, endTime));
      return this;
    }

    /**
     * Return the model built so far.
     *
     * @return the model that was constructed so far.
     */
    @Override
    public AnimatorModel build() {
      return this.builderModel;
    }
  }
}

