package net.bluecow.spacegame.cam;

import net.bluecow.spacegame.thing.HasPositionAndOrientation;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

/**
 * A camera that remains stuck on a given object, always tracking its current position and orientation.
 */
public class ObjectViewCamera implements CameraTrackingStrategy {

  /**
   * The object this camera is affixed to.
   */
  private HasPositionAndOrientation objectToTrack;
  
  /**
   * The camera's location relative to the origin point of the object we're tracking.
   */
  private Vector3 cameraOffset;
  
  /**
   * The direction the camera faces, in the object's local coordinate system of the object we're tracking.
   */
  private Vector3 forward = Vector3.Y;

  /**
   * The up direction for the camera, in the local coordinate system of the object we're tracking.
   */
  private Vector3 up = Vector3.X;
  
  public ObjectViewCamera(HasPositionAndOrientation objectToTrack, Vector3 cameraOffset) {
    this.objectToTrack = objectToTrack;
    this.cameraOffset = cameraOffset;
  }
  
  @Override
  public void update(Camera cam) {
    cam.position.set(objectToTrack.getPosition());
    cam.position.add(cameraOffset);
    
    Vector3 dir = new Vector3(forward);
    objectToTrack.getOrientation().transform(dir);
    cam.direction.set(dir);
    
    dir.set(up);
    objectToTrack.getOrientation().transform(dir);
    cam.up.set(dir);
    
    cam.update();
  }

}
