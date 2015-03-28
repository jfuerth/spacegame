package net.bluecow.spacegame.cam;

import net.bluecow.spacegame.thing.HasPositionAndOrientation;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

/**
 * A camera that acts as if it's attached to a given object (at some offset from its center), always tracking its
 * current position and orientation.
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
  
  public ObjectViewCamera(HasPositionAndOrientation objectToTrack, Vector3 cameraOffset, Vector3 forward,  Vector3 top) {
    this.objectToTrack = objectToTrack;
    this.cameraOffset = cameraOffset;
    this.forward = forward;
    this.up = top;
  }
  
  @Override
  public void update(Camera cam) {
    Vector3 objectForward = objectToTrack.getOrientation().transform(new Vector3(forward));
    cam.direction.set(objectForward);
    
    Vector3 objectTop = objectToTrack.getOrientation().transform(new Vector3(this.up));
    cam.up.set(objectTop);

    cam.position.set(objectToTrack.getPosition());

    Vector3 transformedOffset = objectToTrack.getOrientation().transform(new Vector3(cameraOffset));
    cam.position.add(transformedOffset);
    
    cam.update();
  }

}
