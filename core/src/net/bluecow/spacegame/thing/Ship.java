package net.bluecow.spacegame.thing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.UBJsonReader;

public class Ship implements HasPositionAndOrientation {

  private Model model;
  private ModelInstance modelInstance;

  /** Speed of rotation around the ship's Y axis */
  private float rotationY;

  /** Speed of rotation around the ship's X axis */
  private float rotationX;

  private Vector3 position = new Vector3();
  private Vector3 velocity = new Vector3();
  private Quaternion orientation = new Quaternion();
  private final float sideThrustPower = 0.01f;
  private final float mainThrustPower = 0.001f;

  public Ship() {

  }

  public void initialize() {
    G3dModelLoader loader = new G3dModelLoader(new UBJsonReader());
    model = loader.loadModel(Gdx.files.internal("data/ship00.g3db"));

    modelInstance = new ModelInstance(model, 0, 0, 0);
    home();
  }

  public void dispose() {
    model.dispose();
  }

  public void home() {
    orientation.set(Vector3.X, 0);
    velocity.setZero();
    position.set(-3, 0, 0);
  }

  public void update() {
    Quaternion rot = new Quaternion();

    rot.set(Vector3.X, rotationX);
    orientation.mul(rot);

    rot.set(Vector3.Y, rotationY);
    orientation.mul(rot);

    position.add(velocity);
  }

  public void render(ModelBatch modelBatch, Environment environment) {
    modelInstance.transform.set(position, orientation);
    modelBatch.render(modelInstance, environment);
  }

  public void thrustLeft() {
    rotationX += sideThrustPower;
  }

  public void thrustRight() {
    rotationX -= sideThrustPower;
  }

  public void rollCW() {
    rotationY += sideThrustPower;
  }

  public void rollCCW() {
    rotationY -= sideThrustPower;
  }

  public void thrustForward() {
    Vector3 heading = new Vector3(0f, 1f, 0);
    orientation.transform(heading);
    velocity.add(
        heading.x * mainThrustPower,
        heading.y * mainThrustPower,
        heading.z * mainThrustPower);
  }

  @Override
  public Vector3 getPosition() {
    return position;
  }

  @Override
  public Quaternion getOrientation() {
    return orientation;
  }
}
