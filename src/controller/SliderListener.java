package controller;


import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Creates a slider for the scrubbing.
 */
public class SliderListener implements ChangeListener {

  private int frame = 0;
  JSlider source = new JSlider();


  public boolean isAdjusting() {
    return source.getValueIsAdjusting();
  }

  int getFrame() {
    return this.frame;
  }

  /**
   * Invoked when the target of the listener has changed its state.
   *
   * @param e a ChangeEvent object
   */
  @Override
  public void stateChanged(ChangeEvent e) {
    source = (JSlider) e.getSource();
    if (source.getValueIsAdjusting()) {
      this.frame = source.getValue();
    }
  }
}
