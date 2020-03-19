package asteroids.factories;

import asteroids.distributors.InputDistributor;
import asteroids.distributors.PlayerTextureDistributor;
import asteroids.entities.Player;
import asteroids.input.InputHandler;
import asteroids.util.Vector2f;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class PlayerFactory extends EntityFactory {

    private transient int lives;

    private transient InputHandler[] inputHandlers = InputDistributor.getPlayerInput();
    private transient Texture[] ships;

    private transient int playerCount = 0;

    /**
     * Basic constructor for the PlayerFactory.
     *
     * @param lives Integer number of lives per player.
     * @param texture Texture of the Player Entity.
     */
    public PlayerFactory(int lives, Texture texture) {
        super(texture);
        this.lives = lives;
        ships = PlayerTextureDistributor.getPlayerTextures();
    }

    /**
     * Constructor for testing purposes.
     *
     * @param lives Integer number of lives per player.
     * @param texture Texture that is being mocked.
     * @param inputHandler InputHandler that is being provided for mocking purposes.
     */
    public PlayerFactory(int lives, Texture texture, InputHandler inputHandler) {
        super(texture);
        this.lives = lives;
        inputHandlers = new InputHandler[] {inputHandler, inputHandler, inputHandler};
        ships = new Texture[] {texture, texture, texture};
    }

    @Override
    public Player create(Vector2f position) {
        InputHandler handler = inputHandlers[playerCount];

        Texture playerTexture = ships[playerCount];

        Player p = new Player(
                0,
                30,
                0.15,
                position,
                playerTexture,
                handler,
                lives,
                playerCount
        );
        playerCount++;
        return p;
    }

    public ArrayList<Player> spawnPlayers(int num) {
        ArrayList<Player> players = new ArrayList<>(num);

        for (int ii = 0; ii < num; ii++) {
            int x = 150 + ii * 200;
            int y = 150 + ii * 200;
            Player player = this.create(new Vector2f(x, y));
            players.add(player);
        }

        return players;
    }
}
