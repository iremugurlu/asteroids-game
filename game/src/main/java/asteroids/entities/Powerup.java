package asteroids.entities;

import asteroids.util.Vector2f;
import com.badlogic.gdx.graphics.Texture;

public abstract class Powerup extends Entity {


    /**
     * Spawn a new power-up with a certain size.
     *
     * @param size The size of the power-up.
     * @param position The position on screen.
     * @param texture The texture of the power-up.
     */
    public Powerup(double size, Vector2f position, Texture texture) {
        super(
                0,
                0,
                size,
                position,
                texture
        );
    }

    /**
     * Called when the power-up collides with another entity.
     * Destroy the power-up if it collides with a player.
     * @param other The other entity that you collide with.
     */
    @Override
    public void onCollision(Entity other) {
        if (other.getClass() == Player.class) {
            applyPowerup((Player) other);
            die();
        }
    }

    /**
     * Called when a player collides with this power-up.
     * @param player The player the power-up collided with.
     */
    abstract void applyPowerup(Player player);
}
