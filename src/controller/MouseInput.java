package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.util.Map;

/**
 * Receives all mouse input and runs the appropriate function within its maps of functions that are
 * bound to keys.
 */
public class MouseInput extends MouseAdapter implements MouseListener {

  int scrollWheel;

  Map<Integer, Runnable> mouseClickedMap;
  Map<Integer, Runnable> scrollMap;
  Map<Integer, Runnable> mousePressedMap;
  Map<Integer, Runnable> mouseReleasedMap;

  /**
   * Set the scroll map.
   */
  public void setScrollMap(Map<Integer, Runnable> scrollMap) {
    this.scrollMap = scrollMap;
  }

  /**
   * Sets tge mouse clicked map.
   */
  public void setMouseClickedMap(Map<Integer, Runnable> mouseClickedMap) {
    this.mouseClickedMap = mouseClickedMap;
  }

  /**
   * Sets the mousePressedMap.
   **/
  public void setMousePressedMap(Map<Integer, Runnable> mousePressedMap) {
    this.mousePressedMap = mousePressedMap;
  }

  /**
   * Sets the mouseReleasedMap.
   */
  public void setMouseReleasedMap(Map<Integer, Runnable> mouseReleasedMap) {
    this.mouseReleasedMap = mouseReleasedMap;
  }


  /**
   * Gets the amount the wheel has scrolled.
   */
  public int getScrollWheel() {
    return this.scrollWheel;
  }

  /**
   * Finds out how much the mouse wheel has moved.
   */
  @Override
  public void mouseWheelMoved(MouseWheelEvent e) {
    if (scrollMap.containsKey(e.getWheelRotation())) {
      scrollMap.get(e.getWheelRotation()).run();
    }
  }

  /**
   * Invoked when the mouse button has been clicked (pressed and released) on a component.
   */
  @Override
  public void mouseClicked(MouseEvent e) {
    if (mouseClickedMap.containsKey(e.getButton())) {
      mouseClickedMap.get(e.getButton()).run();
    }
  }

  /**
   * Invoked when a mouse button has been pressed on a component.
   */
  @Override
  public void mousePressed(MouseEvent e) {
    if (mousePressedMap.containsKey(e.getButton())) {
      mousePressedMap.get(e.getButton()).run();
    }
  }

  /**
   * Invoked when a mouse button has been released on a component.
   */
  @Override
  public void mouseReleased(MouseEvent e) {
    if (mouseReleasedMap.containsKey(e.getButton())) {
      mouseReleasedMap.get(e.getButton()).run();
    }
  }

  /**
   * Invoked when the mouse enters a component.
   */
  @Override
  public void mouseEntered(MouseEvent e) {
    return;
  }

  /**
   * Invoked when the mouse exits a component.
   */
  @Override
  public void mouseExited(MouseEvent e) {
    return;
  }
}
