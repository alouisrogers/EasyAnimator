package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import model.comparators.LayerComparator;
import model.shapes.Shape;
import view.visitors.GUIRenderVisitor;

/**
 * The VisualPanel is a JPanel on which the animation is being drawn and it contains a list of
 * shapes that are being drawn.
 */
public class VisualPanel extends JPanel {

  List<Shape> shapes;
  boolean levelSpecific;

  /**
   * Constructs a new VisualPanel that the animation can be drawn on.
   */
  public VisualPanel() {
    super();
    this.shapes = new ArrayList<>();
  }

  @Override
  protected void paintComponent(Graphics graphics) {
    //Override the JPanel paint component method
    super.paintComponent(graphics);

    //make the graphics have the stuff needed in the graphics 2d class
    Graphics2D graphics2D = (Graphics2D) graphics;

    //Visitor will take care of the renderings for each of the shapes
    GUIRenderVisitor renderVisitor = new GUIRenderVisitor(graphics2D);

    //this should draw all the shapes given using the visitor pattern (See file view.visitors.GUIRenderVisitor)
    for (Shape shape : this.shapes) {
      renderVisitor.apply(shape);
    }
  }

  public void levelImportant() {
    this.levelSpecific = true;
  }

  /**
   * Refreshes the shapes in the image for the next frame.
   */
  public void refresh(List<Shape> shapes) {
    if (levelSpecific) {
      shapes.sort(new LayerComparator());
      this.shapes = shapes;
    } else {
      this.shapes = shapes;
    }
    this.repaint();
  }
}
