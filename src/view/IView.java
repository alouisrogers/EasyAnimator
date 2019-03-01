
package view;

import java.io.IOException;

/**
 * Interface representing any type of view for the animation.
 */
public interface IView {
  /**
   * Factory for the three views, depending on the given String for a view.
   */
  class Factory {
    /**
     * Creation method that makes the view depenging on the given string.
     *
     * @param view The desired view to be created as a String.
     * @return an IView depending on the String
     * @throws IllegalArgumentException if the given String is not a valid view.
     */
    public static IView create(String view) throws IllegalArgumentException {
      switch (view) {
        case "text":
          return new TextView();
        case "visual":
          return new GUIView();
        case "svg":
          return new SVGView();
        case "interactive":
          return new HybridView();
        default:
          throw new IllegalArgumentException("Invalid view.");
      }
    }
  }

  /**
   * Tries appending the given string to the given appendable and throws an IllegalStateException
   * if there is an IOException.
   *
   * @param ap The appendable
   * @param str the String trying to be appended.
   * @return Appendable if the append attempt is successful
   * @throws IllegalStateException if there is an input output error.
   */
  static Appendable appendTry(Appendable ap, String str) throws IllegalStateException {
    try {
      ap.append(str);
      return ap;
    } catch (IOException e) {
      throw new IllegalStateException("Input output error.");
    }
  }
}

