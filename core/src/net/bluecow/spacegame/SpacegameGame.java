package net.bluecow.spacegame;

import java.util.ArrayList;
import java.util.List;

import net.bluecow.spacegame.cam.CameraTrackingStrategy;
import net.bluecow.spacegame.cam.FixedPositionTrackingCamera;
import net.bluecow.spacegame.cam.ObjectViewCamera;
import net.bluecow.spacegame.thing.Asteroid;
import net.bluecow.spacegame.thing.Ship;
import net.bluecow.spacegame.thing.StarSphere;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Vector3;

public class SpacegameGame extends ApplicationAdapter {
  private PerspectiveCamera camera;
  private ModelBatch modelBatch;
  private Environment environment;
  private Ship ship;
  private Asteroid asteroid;
  private StarSphere starSphere;
  
  private final List<CameraTrackingStrategy> camStrategies = new ArrayList<CameraTrackingStrategy>();
  private CameraTrackingStrategy currentCamStrategy;
  
  @Override
  public void create() {
    
    // Create camera sized to screens width/height with Field of View of 75 degrees
    camera = new PerspectiveCamera(75, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    // Near and Far (plane) repesent the minimum and maximum ranges of the camera in, um, units
    camera.near = 0.1f;
    camera.far = 600.0f;

    // A ModelBatch is like a SpriteBatch, just for models. Use it to batch up geometry for OpenGL
    modelBatch = new ModelBatch();

    // Finally we want some light, or we wont see our color. The environment gets passed in during
    // the rendering process. Create one, then create an Ambient ( non-positioned, non-directional ) light.
    environment = new Environment();
    environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1f, 1f, 1f, 1.0f));
    
    ship = new Ship();
    ship.initialize();
    
    camStrategies.add(new FixedPositionTrackingCamera(new Vector3(0f, 4f, 8f), ship));
    camStrategies.add(new ObjectViewCamera(ship, new Vector3(0, 0, 0)));
    currentCamStrategy = camStrategies.get(1);

    asteroid = new Asteroid();
    asteroid.initialize();
    
    starSphere = new StarSphere();
    starSphere.initialize();
  }

  @Override
  public void dispose() {
    modelBatch.dispose();
    ship.dispose();
    asteroid.dispose();
    starSphere.dispose();
  }

  @Override
  public void render() {
    Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    Gdx.gl.glClearColor(1, 1, 1, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

    if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
      ship.thrustLeft();
    }
    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
      ship.thrustRight();
    }
    if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
      ship.thrustForward();
    }
    if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
      ship.home();
    }
    if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
      currentCamStrategy = camStrategies.get(0);
    }
    if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
      currentCamStrategy = camStrategies.get(1);
    }
    
    currentCamStrategy.update(camera);
    
    // When you change the camera details, you need to call update();
    // Also note, you need to call update() at least once.
    ship.update();
    asteroid.update();
    starSphere.update();
    
    modelBatch.begin(camera);
    ship.render(modelBatch, environment);
    asteroid.render(modelBatch, environment);
    starSphere.render(modelBatch, environment);
    modelBatch.end();
  }

  @Override
  public void resize(int width, int height) {
  }

  @Override
  public void pause() {
  }

  @Override
  public void resume() {
  }
}
