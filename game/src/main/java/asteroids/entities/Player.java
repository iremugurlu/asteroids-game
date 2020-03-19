package asteroids.entities;

import asteroids.animations.ExplosionAnimation;
import asteroids.animations.PlayerAnimation;
import asteroids.input.InputHandler;
import asteroids.managers.EntityManager;
import asteroids.util.Vector2f;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

/**
 * The entity which is used to create player characters.
 */
public class Player extends BasicShip {
    private static final int MIN_LIFE = 1;
    private static final int PROJECTILE_SPEED = 300;
    private static final long FIRE_DELAY = 300;
    private static final long INVINCIBILITY = 3000;
    private transient int lives = 3;
    private transient int playerId;
    private transient float deltaTime;
    private transient long lastShot = 0;
    private transient final String[] colors = {"blue", "green", "red"};
    private transient PlayerAnimation playerAnimation;
    private transient ExplosionAnimation explosionAnimation;
    private transient boolean isInvincible;
    private transient long endInvincibility = 0;
    private transient long currentInvincibility = 0;

    /**
     * Constructs an entity.
     *
     * @param rotation the rotation of this Entity
     * @param speed    the speed of the Entity
     * @param size     the size of the entity
     * @param position the coordinates of the Entity
     * @param lives    the number of lives a player possesses
     */
    public Player(
            float rotation,
            int speed,
            double size,
            Vector2f position,
            int lives,
            int playerId) {
        super(
                rotation,
                speed,
                size,
                position,
                new Texture(com.badlogic.gdx.Gdx.files.internal("ships/player-blue.png")),
                new InputHandler()
        );
        this.lives = lives;
        playerAnimation = new PlayerAnimation();
        explosionAnimation = new ExplosionAnimation();
        this.playerId = playerId;
        isInvincible = false;
    }

    /**
     * Constructs an entity.
     *
     * @param rotation the rotation of this Entity
     * @param speed    the speed of the Entity
     * @param size     the size of the entity
     * @param position the coordinates of the Entity
     * @param texture  the texture of the Entity
     * @param lives    the number of lives of the Entity
     */
    public Player(
            float rotation,
            int speed,
            double size,
            Vector2f position,
            Texture texture,
            int lives, int playerId) {

        super(rotation, speed, size, position, texture, new InputHandler());
        if (com.badlogic.gdx.Gdx.files != null) {
            playerAnimation = new PlayerAnimation();
            explosionAnimation = new ExplosionAnimation();
        }
        this.lives = lives;
        this.playerId = playerId;
        isInvincible = false;
    }

    /**
     * Constructs an entity.
     *
     * @param rotation     the rotation of this Entity
     * @param speed        the speed of the Entity
     * @param size         the size of the entity
     * @param position     the coordinates of the Entity
     * @param texture      the texture of the Entity
     * @param inputHandler the input handler of the Entity
     * @param lives        the number of lives of the Entity
     */
    public Player(float rotation, int speed, double size,
                  Vector2f position, Texture texture, InputHandler inputHandler, int lives, int playerId) {
        super(rotation, speed, size, position, texture, inputHandler);
        this.lives = lives;
        this.inputHandler = inputHandler;
        if (com.badlogic.gdx.Gdx.files != null) {
            texture = new Texture(com.badlogic.gdx.Gdx.files.internal("animation/player-" + colors[playerId] + ".png"));
            playerAnimation = new PlayerAnimation(texture);
            explosionAnimation = new ExplosionAnimation();
        }
        this.playerId = playerId;
        isInvincible = false;
    }

    /**
     * This function performs the special update requirements of the player entity.
     *
     * @param deltaTime The time that has elapsed since the last frame.
     */
    @Override
    public void update(float deltaTime) {
        this.deltaTime = deltaTime;
        ArrayList actions = super.inputHandler.checkNextActions(
                this.getPosition(), this.getRotation()
        );
        super.actionHandler(actions);
        if (updateSpeed(deltaTime)) {
            super.update(deltaTime);
        }
        if (isInvincible) {
            if (endInvincibility > currentInvincibility) {
                currentInvincibility = System.currentTimeMillis();
            } else {
                currentInvincibility = 0;
                isInvincible = false;
            }
        }
        if (getAcceleration() <= 0 && !isExploding() && com.badlogic.gdx.Gdx.files != null) {
            Sprite sprite = playerAnimation.getNextSpriteReverse();
            if (isInvincible) {
                sprite.setAlpha(0.5f);
            }
            setSprite(sprite);

        } else if (isExploding()) {
            if (explosionAnimation.isFinished()) {
                die();
            } else {
                setSprite(explosionAnimation.getNextSprite());
            }
        }
    }

    /**
     * This method contains the logic of shooting.
     */
    public void shoot() {
        if (System.currentTimeMillis() > lastShot + FIRE_DELAY) {
            float direction = getRotation();
            Projectile projectile = new Projectile(
                    direction,
                    PROJECTILE_SPEED,
                    0.8,
                    new Vector2f(
                            getPosition().getCoordinateX(),
                            getPosition().getCoordinateY()
                    ),
                    new Texture(Gdx.files.getFileHandle("projectiles/" + colors[playerId] + ".png", Files.FileType.Local)),
                    playerId
            );
            EntityManager.getInstance().spawnEntity(projectile);
            EntityManager.getInstance().getSoundManager().setSound("laser.wav");
            EntityManager.getInstance().getSoundManager().play(false);
            lastShot = System.currentTimeMillis();
        }
    }

    /**
     * onCollision method, called when colliding with another entity.
     * The asteroid gets destroyed when this method is called.
     *
     * @param other The entity that this asteroid collided with.
     */
    @Override
    public void onCollision(Entity other) {
        boolean versus = EntityManager.getInstance().getVersus();

        if (other instanceof Projectile) {
            Projectile p = (Projectile) other;
            if (p.getEntityIdOwner() == playerId || !versus) {
                return;
            }
        }

        if ((other instanceof Powerup) || (other instanceof Player && !versus) || other.isExploding() || isInvincible) {
            return;
        }

        lives--;
        if (lives < MIN_LIFE) {
            if (com.badlogic.gdx.Gdx.files == null) {
                die();
            } else {
                setExploding();
            }
        } else {
            isInvincible = true;
            endInvincibility = System.currentTimeMillis() + INVINCIBILITY;
            if (EntityManager.getInstance().getSoundManager() != null) {
                EntityManager.getInstance().getSoundManager().setSound("hurt.mp3");
                EntityManager.getInstance().getSoundManager().play(false);
            }
        }
    }

    /**
     * Getter for the lives variable.
     *
     * @return the current value of the lives variable
     */
    public int getLives() {
        return this.lives;
    }

    /**
     * Getter for the playerId variable.
     *
     * @return The id of the player object.
     */
    public int getPlayerId() {
        return this.playerId;
    }

    /**
     * Adds one to the live variable.
     *
     * @param amount The amount to increase the lives with the player with.
     */
    public void increaseLives(int amount) {
        lives += amount;
    }

    /**
     * This method contains the logic of accelerating.
     */
    @Override
    public void accelerate() {
        if (getAcceleration() > 0 && com.badlogic.gdx.Gdx.files != null) {
            Sprite sprite = playerAnimation.getNextSprite();
            if (isInvincible) {
                sprite.setAlpha(0.5f);
            }
            setSprite(sprite);
        }
        super.accelerate();
    }
}
