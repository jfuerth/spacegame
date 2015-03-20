package net.bluecow.spacegame.thing;

import com.badlogic.gdx.math.Quaternion;

public interface HasPositionAndOrientation extends HasPosition {

  Quaternion getOrientation();
}
