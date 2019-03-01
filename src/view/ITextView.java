package view;

import java.util.List;

import model.shapes.Shape;

/**
 * This view interfaces handles types of views which have text outputs.
 */
public interface ITextView extends IView {

  /**
   * Renders a text output for an animation with the given list of shapes, ticks per second, to
   * the given output mode.
   *
   * @param shapeList List of shapes in the animation.
   * @param ticksPerSecond ticks per second for the animation.
   * @param outputMode where the text is outputted.
   * @param appendable an Appendable that the output is added to.
   * @param hasLoop If the animation has a loop.
   */
  void renderText(List<Shape> shapeList, int ticksPerSecond, String outputMode,
                  Appendable appendable, boolean hasLoop);
}
