package net.bluecow.spacegame.cam;

import net.bluecow.spacegame.thing.HasPosition;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

/**
 * Manipulates the camera to point at a given object of interest without moving the camera itself.
 */
public class FixedPositionTrackingCamera implements CameraTrackingStrategy {

  private Vector3 position;
  private HasPosition objectToTrack;

  public FixedPositionTrackingCamera(Vector3 position, HasPosition objectToTrack) {
    this.position = position;
    this.objectToTrack = objectToTrack;
  }

  @Override
  public void update(Camera cam) {
    cam.position.set(position);
    cam.lookAt(objectToTrack.getPosition());
    cam.update();
  }

}
