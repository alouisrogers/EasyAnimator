package controller;

import model.AnimatorModel;
import view.ITextView;

/**
 * The animator controller takes input from the user and gives the model and view actions to carry
 * out based on the instructions.
 */
public class TextController implements Controller {

  private AnimatorModel model;
  private ITextView view;
  private Appendable appendable;

  private int ticksPerSecond;
  private String outputMode;

  /**
   * Creates a new TextController with the given list of String arguments and Appendable. The other
   * fields are set to their defaults.
   */
  public TextController(AnimatorModel model, ITextView view) {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Null field given.");
    }
    this.model = model;
    this.view = view;
  }

  /**
   * This method animates the controller depending on all of its fields and presents a view to the
   * user.
   */
  @Override
  public void animate() {
    view.renderText(model.getShapes(), ticksPerSecond, outputMode, appendable, false);
  }

  /**
   * Sets the output mode of the controller.
   */
  @Override
  public void setOutput(String output) {
    if (outputMode.equals("")) {
      this.outputMode = "out";
    } else {
      this.outputMode = output;
    }
  }

  @Override
  public void setFrameRate(int frameRate) {
    this.ticksPerSecond = frameRate;
  }
}
