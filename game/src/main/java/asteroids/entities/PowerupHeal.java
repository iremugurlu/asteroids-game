package asteroids.entities;

import asteroids.util.Vector2f;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class PowerupHeal extends Powerup {

    /**
     * Spawn a new heal power-up with a certain size.
     *
     * @param size The size of the power-up.
     * @param position The position on screen.
     */
    public PowerupHeal(double size, Vector2f position) {
        super(
                size,
                position,
                new Texture(Gdx.files.getFileHandle("upgrades/heal.png", Files.FileType.Local))
        );
    }

    /**
     * Spawn a new heal power-up with a certain size and with a specific texture.
     * Used for testing.
     *
     * @param size The size of the power-up.
     * @param position The position on screen.
     * @param texture The texture of the heal powerup.
     */
    public PowerupHeal(double size, Vector2f position, Texture texture) {
        super(
                size,
                position,
                texture
        );
    }

    /**
     * Called when a player collides with this power-up.
     * @param player The player the power-up collided with.
     */
    void applyPowerup(Player player) {
        player.increaseLives(1);
    }
}
