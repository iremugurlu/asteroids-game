package asteroids.distributors;

import asteroids.input.InputHandler;
import com.badlogic.gdx.Input.Keys;

import java.util.List;

public final class InputDistributor {

    private static final InputHandler[] playerInput = {
        new InputHandler(List.of(Keys.W, Keys.S, Keys.A, Keys.D, Keys.SPACE)),
        new InputHandler(List.of(Keys.I, Keys.K, Keys.J, Keys.L, Keys.ENTER))
    };

    private InputDistributor() {

    }

    public static InputHandler[] getPlayerInput() {
        return playerInput;
    }

    public static void setPlayerInput(int id, InputHandler playerInput) {
        InputDistributor.playerInput[id] = playerInput;
    }
}
