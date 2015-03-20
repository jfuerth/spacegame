package net.bluecow.spacegame.cam;

import com.badlogic.gdx.graphics.Camera;

public interface CameraTrackingStrategy {

  /**
   * Updates the given camera's position and orientation based on this strategy's settings. No further preparation (for
   * example, a call to {@link Camera#update()} is required after this method returns.
   */
  void update(Camera cam);
  
}
