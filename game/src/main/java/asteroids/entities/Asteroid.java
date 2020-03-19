package asteroids.entities;

import asteroids.animations.ExplosionAnimation;
import asteroids.managers.EntityManager;
import asteroids.managers.ScoreManager;
import asteroids.util.Vector2f;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Asteroid class, used to represent an asteroid in the game.
 */
public class Asteroid extends Entity {

    private static final float MIN_SPLIT_SIZE = 0.15f;
    private transient ExplosionAnimation explosionAnimation;

    /**
     * Constructs an asteroid.
     *
     * @param rotation the rotation of this Entity
     * @param speed    the speed of the Entity
     * @param size     the size of the entity
     * @param position the coordinates of the Entity
     * @param texture  the texture of the entity.
     */
    public Asteroid(float rotation, int speed, double size, Vector2f position, Texture texture) {
        super(rotation, speed, size, position, texture);
        if (com.badlogic.gdx.Gdx.files != null) {
            explosionAnimation = new ExplosionAnimation();
        }
    }

    /**
     * Called when the asteroid collides with another entity.
     * Destroys the asteroid if it is smaller than the MIN_SPLIT_SIZE.
     * Otherwise it spawns a random amount of new (smaller) asteroids (between 2 and 4)
     *
     * @param other The entity that this entity collided with.
     */
    @Override
    public void onCollision(Entity other) {
        if (other instanceof Asteroid || other instanceof Powerup) {
            return;
        }
        if (EntityManager.getInstance().getSoundManager() != null) {
            EntityManager.getInstance().getSoundManager().setSound("explosion.wav");
            EntityManager.getInstance().getSoundManager().play(false);
        }
        if (getSize() > MIN_SPLIT_SIZE) {
            EntityManager.getInstance().getSpawnManager().splitAsteroid(this);
            EntityManager.getInstance().spawnAsteroid(this.getPosition());
        }

        if (other instanceof Player) {
            Player that = (Player) other;
            ScoreManager.getInstance().increaseScore(50, that.getPlayerId());
        }

        if (other instanceof Projectile) {
            Projectile that = (Projectile) other;
            if (((Projectile) other).getEntityIdOwner() >= 0) {
                ScoreManager.getInstance().increaseScore(50, that.getEntityIdOwner());
            }
        }
        if (com.badlogic.gdx.Gdx.files == null) {
            die();
        } else {
            setExploding();
        }
    }

    /**
     * A method that is called every frame.
     * If the Asteroid exits the board it should die.
     *
     * @param deltaTime The time that has elapsed since the last frame.
     */
    @Override
    public void update(float deltaTime) {
        if (isExploding()) {
            if (explosionAnimation.isFinished()) {
                die();
            } else {
                setSprite(explosionAnimation.getNextSprite());
            }
        } else {
            applyMovement(deltaTime);
            if (!inBoard()) {
                die();
            }
        }
    }
}
