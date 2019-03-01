package controller;

import model.AnimatorModel;
import view.GUIView;
import view.HybridView;
import view.ITextView;
import view.IView;


/**
 * This interface represents a controller which interacts with the user and delegates actions to the
 * model and view.
 */
public interface Controller {


  void setFrameRate(int frameRate);

  class Factory {

    /**
     * Creation method that makes the view depenging on the given string.
     *
     * @return an IView depending on the String
     * @throws IllegalArgumentException if the given String is not a valid view.
     */
    public static Controller create(String viewType, AnimatorModel model, IView view)
        throws IllegalArgumentException {
      switch (viewType) {
        case "text":
        case "svg":
          return new TextController(model, (ITextView) view);
        case "visual":
          return new VisualController(model, (GUIView) view);
        case "interactive":
          return new InteractiveController(model, (HybridView) view);
        default:
          throw new IllegalArgumentException("Invalid view.");
      }
    }
  }

  /**
   * This method animates the controller depending on all of its
   * fields and presents a view to the user.
   */
  void animate();

  /**
   * Sets the output mode of the controller.
   */
  void setOutput(String output);

}
