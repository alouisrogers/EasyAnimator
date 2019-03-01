package view;


import java.awt.*;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.event.ChangeListener;
import model.shapes.Shape;
import java.util.List;

import view.background.BasicBackground;
import view.background.IBackground;

/**
 * Visual view created using Java Swing.
 */
public class GUIView extends JFrame implements IVisualView {

  VisualPanel visualPanel;
  JScrollPane scrollPane;
  JSlider frame;
  IBackground background;


  /**
   * Creates a new GUIView using the Java Swing to set default visuals.
   */
  public GUIView() {
    super();
    setSize(800, 900);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.visualPanel = new VisualPanel();
    this.visualPanel.setPreferredSize(new Dimension(1000, 1000));
    scrollPane = new JScrollPane(visualPanel);
    this.add(scrollPane);
    this.background = new BasicBackground(Color.WHITE);
    scrollPane.setVisible(true);
    setVisible(true);
    this.setFocusable(true);
    this.setAutoRequestFocus(true);
  }

  /**
   * Renders an image of the view with the given shapes.
   */
  @Override
  public void renderImage(List<Shape> shapes, int ticksPerSecond) {
    visualPanel.refresh(shapes);
    visualPanel.setBackground(background.getColor());
  }

  /**
   * Reset the focus on the appropriate part of the view that has the keyboard listener attached to
   * it, so that keyboard events will still flow through.
   */
  @Override
  public void resetFocus() {
    this.requestFocus();
  }


  @Override
  public void addMouseListener(MouseListener mouseListener) {
    this.scrollPane.addMouseListener(mouseListener);
  }

}
