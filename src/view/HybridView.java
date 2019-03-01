package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Color;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.event.ChangeListener;

import model.shapes.Shape;

/**
 * Interactive SVG and Visual view which can export the animation to an SVG file, and can create a
 * visual display of the animation. This view can also interact with the user. It allows the user to
 * start, pause, resume and rewind the animation, enable looping, adjust the speed of the animation
 * as it is being played, and export on command to an SVG file.
 */
public class HybridView extends GUIView implements IVisualView {

  JPanel mainPanel;
  JPanel svgPanel;
  JPanel keyPanel;
  JLabel lineBorderLabel;
  JLabel key;
  JSlider frame;
  JTextField textField;
  JButton button;

  /**
   * Constructs a new HybridView with the settings for the display.
   */
  public HybridView() {
    super();
    lineBorderLabel = new JLabel();
    mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    keyPanel = new JPanel();
    keyPanel.setBorder(BorderFactory.createTitledBorder("Key"));
    key = new JLabel("<html> -'space' to start animation <br>-'p': "
        + "&nbsp Play/Pause &nbsp&nbsp&nbsp&nbsp&nbsp  "
        + "-'l': &nbsp Enable/Disable loop &nbsp&nbsp&nbsp&nbsp&nbsp "
        + "-'r': &nbsp Rewind to <br>-'up/down': &nbsp increase/decrease speed "
        + " &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"
        + "&nbsp&nbsp&nbsp&nbsp -'left/right': &nbsp Fast forward/Rewind line</html>");
    keyPanel.add(key);
    mainPanel.add(keyPanel);
    svgPanel = new JPanel();
    svgPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    textField = new JTextField(25);
    button = new JButton("Export");
    button.setActionCommand("SVG Export");
    svgPanel.add(textField);
    svgPanel.add(button);
    mainPanel.add(svgPanel);
    mainPanel.add(this.scrollPane);
    this.add(mainPanel);
    visualPanel.levelImportant();
  }

  /**
   * Exports the current animation to an svg file.
   *
   * @param shapeList List of shapes in the animation.
   * @param ticksPerSecond Speed of animation.
   * @param outputMode Mode of output.
   * @param hasLoop If the animation has a loop.
   * @throws IllegalArgumentException If a field is null, the speed is negative, or the output mode
   *     is not a svg file.
   */
  public void svgExport(List<Shape> shapeList, int ticksPerSecond, String outputMode,
      boolean hasLoop) throws IllegalArgumentException {
    if (shapeList == null || outputMode == null) {
      throw new IllegalArgumentException("Null field given.");
    }
    if (ticksPerSecond < 1) {
      throw new IllegalArgumentException("Need positive ticks per second.");
    }
    if (!outputMode.endsWith(".svg")) {
      throw new IllegalArgumentException("Must export to an svg file.");
    }

    SVGView svgView = new SVGView();

    svgView.renderText(shapeList, ticksPerSecond, outputMode, new StringBuffer(), hasLoop);
  }

  /**
   * Returns the textField input.
   *
   * @return String that was in text field.
   */
  public String getSVGString() {
    if (textField.getText().endsWith(".svg")) {
      return textField.getText();
    } else {
      return textField.getText().substring(0, textField.getText().length() - 4);
    }
  }

  public void resetSVGString() {
    this.textField.setText("");
  }

  /**
   * Reset the focus on the appropriate part of the view that has the keyboard listener attached to
   * it, so that keyboard events will still flow through.
   */
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  /**
   * Updates the status label in the view.
   *
   * @param pause If the animation is paused or playing.
   * @param loop If the animation is looping.
   * @param speed The speed of the animation.
   */
  public void addStatusBar(boolean pause, boolean loop, int speed) {
    String pauseStatus;
    String loopStatus;

    if (pause) {
      pauseStatus = "Paused";
    } else {
      pauseStatus = "Playing";
    }
    if (loop) {
      loopStatus = "Looping";
    } else {
      loopStatus = "No loop";
    }

    lineBorderLabel.setText("Status: " + pauseStatus + "      Loop Status: " + loopStatus
        + "      FPS: " + speed);
    this.add(lineBorderLabel, BorderLayout.NORTH);
    this.repaint();
  }

  /**
   * Adds a slide listener to the view.
   *
   * @param slider The slider.
   * @param ticks The ticks in the slider.
   */
  public void addSlideListener(ChangeListener slider, int ticks) {
    this.frame = new JSlider(JSlider.HORIZONTAL, 0, ticks, 0);
    frame.setAlignmentX(Component.CENTER_ALIGNMENT);
    frame.setMinorTickSpacing(ticks / 50);
    frame.setMajorTickSpacing(25);
    frame.setPaintLabels(true);
    frame.setPaintTicks(true);
    this.add(frame, BorderLayout.SOUTH);
    this.frame.addChangeListener(slider);
    this.validate();
    this.repaint();
  }
}
