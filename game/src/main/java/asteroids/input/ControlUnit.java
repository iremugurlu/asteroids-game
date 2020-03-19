package asteroids.input;

import asteroids.util.Vector2f;

import java.util.ArrayList;

public interface ControlUnit {
    public ArrayList<Actions> checkNextActions(Vector2f pos, float rotation);
}
