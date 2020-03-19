package asteroids.entities;

import asteroids.util.Vector2f;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Asteroid class, used to represent an asteroid in the game.
 */
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class Projectile extends Entity {

    private int entityIdOwner;

    /**
     * Constructs a projectile.
     *
     * @param rotation the rotation of this Entity
     * @param speed    the speed of the Entity
     * @param size     the size of the entity
     * @param position the coordinates of the Entity
     */
    public Projectile(
            float rotation,
            int speed,
            double size,
            Vector2f position,
            int entityIdOwner) {
        super(
                rotation,
                speed,
                size,
                position,
                new Texture(Gdx.files.getFileHandle("projectiles/blue.png", Files.FileType.Local))
        );
        this.entityIdOwner = entityIdOwner;
    }

    public int getEntityIdOwner() {
        return entityIdOwner;
    }

    public void setEntityIdOwner(int entityId) {
        this.entityIdOwner = entityId;
    }

    /**
     * Constructs a projectile.
     *
     * @param rotation the rotation of this Entity
     * @param speed    the speed of the Entity
     * @param size     the size of the entity
     * @param position the coordinates of the Entity
     */
    public Projectile(
            float rotation,
            int speed,
            double size,
            Vector2f position,
            Texture texture,
            int entityIdOwner) {
        super(rotation, speed, size, position, texture);
        this.entityIdOwner = entityIdOwner;
    }

    /**
     * onCollision method, called when colliding with another entity.
     *
     * @param other The entity that this projectile collided with.
     */
    @Override
    public void onCollision(Entity other) {

        if (other instanceof  Powerup) {
            return;
        }

        if (other instanceof Player) {
            Player p = (Player) other;
            if (entityIdOwner == p.getPlayerId()) {
                return;
            }
        }
        die();
    }

    /**
     * A method that is called every frame.
     * If the Projectile exits the board it should die.
     *
     * @param deltaTime The time that has elapsed since the last frame.
     */
    @Override
    public void update(float deltaTime) {
        applyMovement(deltaTime);
        if (!inBoard()) {
            die();
        }
    }
}
