package asteroids.input;

import asteroids.util.Vector2f;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.ArrayList;
import java.util.List;

public class InputHandler implements ControlUnit {

    private transient List<Integer> keyList;

    /**
     * Constructs a new Input handler using a keyList.
     *
     * @param keyList List of keys you want to use order:
     *               Accelerate, decelerate, rotate left, rotate right, shoot.
     */
    public InputHandler(List<Integer> keyList) {
        this.keyList = keyList;
    }

    /**
     * Constructs a new Input handler using the default KeyList.
     *
     */
    public InputHandler() {
        this.keyList = List.of(
                Input.Keys.W,
                Input.Keys.S,
                Input.Keys.A,
                Input.Keys.D,
                Input.Keys.SPACE
        );
    }

    /**
     * A functions which checks the relevant pressed keys.
     *
     * @return a list of actions to perform depending on the pressed keys.
     */
    public ArrayList<Actions> checkNextActions(Vector2f pos, float rotation) {
        ArrayList actionList = new ArrayList<Actions>();
        if (Gdx.input.isKeyPressed(keyList.get(0))) {
            actionList.add(Actions.ACCELERATE);
        }
        if (Gdx.input.isKeyPressed(keyList.get(1))) {
            actionList.add(Actions.DECELERATE);
        }
        if (Gdx.input.isKeyPressed(keyList.get(2))) {
            actionList.add(Actions.ROTATE_LEFT);
        }
        if (Gdx.input.isKeyPressed(keyList.get(3))) {
            actionList.add(Actions.ROTATE_RIGHT);
        }
        if (Gdx.input.isKeyPressed(keyList.get(4))) {
            actionList.add(Actions.SHOOT);
        }
        return actionList;
    }
}
