package asteroids.factories;

import asteroids.entities.BasicEnemy;
import asteroids.entities.Entity;
import asteroids.util.Vector2f;
import com.badlogic.gdx.graphics.Texture;

public class EnemyFactory extends EntityFactory {

    private transient double minSize;
    private transient double maxSize;

    /**
     * Constructor for the enemy Factory.
     *
     * @param minSize The min. size an enemy needs to be.
     * @param maxSize The max. size an enemy can be.
     * @param texture The texture that the enemy has to get.
     */
    public EnemyFactory(double minSize, double maxSize, Texture texture) {
        super(texture);
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    /**
     * This method can be called to create enemies.
     *
     * @return an instantiated enemy.
     */
    public BasicEnemy create() {
        return new BasicEnemy(
                0,
                0,
                0.1,
                new Vector2f(300, 200)
        );
    }

    /**
     * This method can be called to create enemies at a specific position.
     *
     * @param position the desired spawn location
     * @return an instantiated enemy.
     */
    @Override
    public Entity create(Vector2f position) {
        return new BasicEnemy(
                0,
                0,
                0.1,
                position
        );
    }
}
