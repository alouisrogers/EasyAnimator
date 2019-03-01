package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;
import model.AnimatorModel;

import view.GUIView;
import view.IVisualView;

/**
 * A Controller for a visual view that the user can interact with.
 */
public class VisualController implements Controller {

  protected AnimatorModel model;
  protected IVisualView view;
  protected int frameCount;
  protected Timer timer;
  protected int ticksPerSecond;

  public VisualController(AnimatorModel model, GUIView view) {
    this.model = model;
    this.view = view;
    this.ticksPerSecond = 1;
    this.frameCount = 0;
  }

  @Override
  public void setFrameRate(int frameRate) {
    this.ticksPerSecond = frameRate;
  }

  /**
   * This should set up a timer Create a task to show the model Then schedule runs the task at
   * intervals of the specified delay for the maxTick seconds Frame count should increase every time
   * until gets bigger than max ticks and terminate.
   */
  @Override
  public void animate()  {
    timer = new javax.swing.Timer(1000 / ticksPerSecond,  new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        view.renderImage(model.getAnimatorState(frameCount), ticksPerSecond);
        if ( frameCount > model.getMaxTick()) {
          timer.stop();
        }
        else {
          frameCount ++;
        }
      }
    });
    timer.start();
  }

  /**
   * Sets the output mode of the controller.
   */
  @Override
  public void setOutput(String output) throws UnsupportedOperationException {
    if(!output.equals("")) {
      throw new IllegalArgumentException("Visual view can only be outputted visually.");
    }
  }
}
