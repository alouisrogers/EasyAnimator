package view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.event.ChangeListener;
import model.shapes.Shape;

/**
 * Interface representing views which render visual representations.
 */
public interface IVisualView extends IView {
  /**
   * Renders an image of the view with the given shapes.
   * @param shapes
   */
  void renderImage(List<Shape> shapes, int ticksPerSecond);

  /**
   * Reset the focus on the appropriate part of the view that has the keyboard listener attached to
   * it, so that keyboard events will still flow through.
   */
  void resetFocus();

  /**
   * this is to force the view to have a method to set up the keyboard. The name has been chosen
   * deliberately. This is the same method signature to add a key listener in Java Swing.
   *
   * Thus our Swing-based implementation of this interface will already have such a method.
   */
  void addKeyListener(KeyListener listener);

  void addMouseListener(MouseListener listener);

}
