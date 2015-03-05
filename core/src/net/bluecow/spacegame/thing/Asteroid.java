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

public class Asteroid {

  private Model model;
  private ModelInstance modelInstance;
  
  private Vector3 position = new Vector3();
  private Vector3 velocity = new Vector3();
  private Quaternion orientation = new Quaternion();
  
  public Asteroid() {
    
  }
  
  public void initialize() {
    G3dModelLoader loader = new G3dModelLoader(new UBJsonReader());
    model = loader.loadModel(Gdx.files.internal("data/asteroid2.g3db"));
    
    modelInstance = new ModelInstance(model, 0, 0, 0);
    home();
  }
  
  public void dispose() {
    model.dispose();
  }

  public void home() {
    orientation.set(Vector3.X, 0);
    orientation.mul(new Quaternion(Vector3.Y, -90));
    velocity.setZero();
    position.set(3, 0, 0);
  }
  
  public void update() {
    position.add(velocity);
  }
  
  public void render(ModelBatch modelBatch, Environment environment) {
    modelInstance.transform.set(position, orientation);
    modelBatch.render(modelInstance, environment);
  }

}
