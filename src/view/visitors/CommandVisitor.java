package view.visitors;

import java.util.function.BiFunction;

import model.commands.ColorFade;
import model.commands.Command;
import model.commands.Move;
import model.commands.Rotation;
import model.commands.Scale;
import model.shapes.Shape;

/**
 * The command visitor visits a command and executes an action on such a command based on the
 * implementing class.
 */
public interface CommandVisitor<R> extends BiFunction<Command, Shape, R> {

  /**
   * The visit colorFade visits a colorFade object and applies functionality specific to a colorFade
   * object.
   *
   * @param colorFade colorFade object.
   * @param shape shape object.
   */
  R visitColorFade(ColorFade colorFade, Shape shape);


  /**
   * The visit colorMove visits a colorFade object and applies functionality specific to a colorMove
   * object.
   *
   * @param move A move object.
   * @param shape A shape object.
   */
  R visitMove(Move move, Shape shape);

  /**
   * The visit scale visits a colorFade object and applies functionality specific to a scale
   * object.
   *
   * @param scale A scale object.
   * @param shape A shape object.
   */
  R visitScale(Scale scale, Shape shape);

  /**
   * The visitor rotation visits a rotation object and applies the functionality.
   */
  R visitRotation(Rotation rotation, Shape shape);

  /**
   * Applies the given visitor the shape.
   *
   * @param command A scale object.
   * @param shape A shape object.
   */
  @Override
  R apply(Command command, Shape shape);
}
