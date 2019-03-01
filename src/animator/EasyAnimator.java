package animator;

import controller.Controller;
import java.io.FileNotFoundException;
import java.io.IOException;


import java.io.StringWriter;
import model.AnimatorModel;
import model.AnimatorModelImpl;
import util.AnimationFileReader;
import view.IView;

/**
 * Method which interacts with the user and takes in list of String as the arguments to follow.
 */
public final class EasyAnimator {

  /**
   * main method which runs the application with the given String arguments.
   *
   * @param args Commands for the application.
   */
  public static void main(String[] args) {

    String outputMode = "";
    int ticksPerSecond = 0;
    Appendable appendable = new StringWriter();
    AnimatorModel model = new AnimatorModelImpl();
    IView view = null;
    Controller controller = null;
    String viewType;
    /**
     * Processes the String arguments of this TextController and sets the field of this
     * Controller according to the arguments field in the controller.
     */
    for (int i = 0; i < args.length - 1; i += 2) {
      switch (args[i]) {
        case "-if":
          try {
            model = new AnimationFileReader().readFile(args[i + 1],
                new AnimatorModelImpl.TweenModelBuilderImpl());
          } catch (FileNotFoundException e) {
            appendTry(appendable, "File not found.");
            System.out.print("File not found.");
          }
          break;
        case "-iv":
          view = IView.Factory.create(args[i + 1]);
          controller = Controller.Factory.create(args[i + 1], model, view);
          break;
        case "-o":
          outputMode = args[i + 1];
          break;
        case "-speed":
          try {
            ticksPerSecond = Integer.parseInt(args[i + 1]);
          } catch (NumberFormatException e) {
            appendTry(appendable, "Need integer for speed");
            System.out.print(appendable);
          }
          break;
        default:
          appendTry(appendable, "Malformed commandline.");
          System.out.print(appendable);
          return;
      }
    }
    controller.setFrameRate(ticksPerSecond);
    try {
      controller.setOutput(outputMode);
    } catch (IllegalArgumentException e) {
      appendTry(appendable, e.toString());
      System.out.print(appendable);
    }
    if (view == null || model == null) {
      appendTry(appendable, "View option and file must be provided.");
      System.out.print(appendable);
    }
    if (ticksPerSecond < 1) {
      appendTry(appendable, "Speed must be positive.");
      System.out.print(appendable);
    }
    controller.animate();
  }

  /**
   * Tries appending the given string to the given appendable and throws
   * an IllegalStateException if there is an IOException.
   *
   * @param ap The appendable.
   * @param str The string to be appended.
   * @return Appendable if append attempt is successful.
   * @throws IllegalStateException if there is an input output error.
   */
  private static Appendable appendTry(Appendable ap, String str) throws IllegalStateException {
    try {
      ap.append(str);
      return ap;
    } catch (IOException e) {
      throw new IllegalStateException("Input output error.");
    }
  }
}