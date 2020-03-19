package asteroids.entities;

import asteroids.animations.ExplosionAnimation;
import asteroids.input.BasicAI;
import asteroids.managers.EntityManager;
import asteroids.util.Vector2f;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class BasicEnemy extends BasicShip {

    private transient ExplosionAnimation explosionAnimation;

    /**
     * Enemy objects are hostile objects which act according to their ai.
     *
     * @param rotation the rotation of the entity
     * @param speed    the speed of the entity
     * @param size     the size of the entity
     * @param position the initial coordinates of the entity
     */
    public BasicEnemy(float rotation, int speed, double size, Vector2f position) {
        super(rotation, speed, size, position, new Texture("ships/enemy1.png"), new BasicAI());
        MAX_SPEED = 150;
        if (com.badlogic.gdx.Gdx.files != null) {
            explosionAnimation = new ExplosionAnimation();
        }
    }

    /**
     * Enemy objects are hostile objects which act according to their ai.
     *
     * @param rotation the rotation of the entity
     * @param speed    the speed of the entity
     * @param size     the size of the entity
     * @param position the initial coordinates of the entity
     */
    public BasicEnemy(float rotation, int speed, double size, Vector2f position,Texture texture) {
        super(rotation, speed, size, position, texture, new BasicAI());
        if (com.badlogic.gdx.Gdx.files != null) {
            explosionAnimation = new ExplosionAnimation();
        }
    }

    /**
     * This function performs the special update requirements of the Enemy entity.
     *
     * @param deltaTime The time that has elapsed since the last frame.
     */
    @Override
    @SuppressWarnings({"PMD.DataflowAnomalyAnalysis"})
    public void update(float deltaTime) {
        ArrayList actions = super.inputHandler.checkNextActions(this.getPosition(), this.getRotation());
        super.actionHandler(actions);
        if (updateSpeed(deltaTime)) {
            super.update(deltaTime);
        }
        if (isExploding()) {
            if (explosionAnimation.isFinished()) {
                die();
            } else {
                setSprite(explosionAnimation.getNextSprite());
            }
        }
    }

    /**
     * onCollision method, called when colliding with another entity.
     * The Enemy gets destroyed when this method is called.
     *
     * @param other The entity that this Enemy collided with.
     */
    @Override
    public void onCollision(Entity other) {
        if ((other instanceof Projectile || other instanceof Player || other instanceof Asteroid || other instanceof BasicEnemy)
                && !other.isExploding()) {
            if (EntityManager.getInstance().getSoundManager() != null) {
                EntityManager.getInstance().getSoundManager().setSound("explosion.wav");
                EntityManager.getInstance().getSoundManager().play(false);
            }
            if (com.badlogic.gdx.Gdx.files == null) {
                die();
            } else {
                setExploding();
            }
        }
    }
}
