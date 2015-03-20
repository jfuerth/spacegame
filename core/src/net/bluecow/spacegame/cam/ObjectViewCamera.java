package net.bluecow.spacegame.cam;

import net.bluecow.spacegame.thing.HasPositionAndOrientation;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

/**
 * A camera that remains stuck on a given object, always tracking its current position and orientation.
 */
public class ObjectViewCamera implements CameraTrackingStrategy {

  private HasPositionAndOrientation objectToTrack;
  private Vector3 cameraOffset;
  private Vector3 up = new Vector3(0, 1, 0);

  public ObjectViewCamera(HasPositionAndOrientation objectToTrack, Vector3 cameraOffset) {
    this.objectToTrack = objectToTrack;
    this.cameraOffset = cameraOffset;
  }
  
  @Override
  public void update(Camera cam) {
    cam.position.set(objectToTrack.getPosition());
    cam.position.add(cameraOffset);
    
    Vector3 objectDirection = new Vector3(up);
    objectToTrack.getOrientation().transform(objectDirection);
    cam.direction.set(objectDirection);
    cam.up.set(Vector3.Z);
    
    cam.update();
  }

}
