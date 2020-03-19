package asteroids.managers;

import asteroids.entities.BasicEnemy;
import asteroids.factories.EnemyFactory;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.List;

/**
 * A class that decides when and where to spawn new enemies.
 */
@SuppressWarnings({"PMD.DataflowAnomalyAnalysis"})
public class EnemySpawnManager {

    private transient float timer = 1;
    private transient float spawnFrequency = 4;

    transient EnemyFactory factory;

    /**
     * Create a new enemy spawnManager.
     */
    public EnemySpawnManager() {
        this.factory = new EnemyFactory(
                0.05,
                0.25,
                new Texture(Gdx.files.internal("ships/enemy1.png"))
        );
    }

    /**
     * Create a new enemy SpawnManager for testing purposes.
     * @param texture Texture to assign to an Asteroid.
     */
    public EnemySpawnManager(Texture texture) {
        this.factory = new EnemyFactory(
                0.05,
                0.25,
                texture
        );
    }

    /**
     * An update function called every frame.
     *
     * @param deltaTime The time that has elapsed since the previous frame.
     */
    public void update(float deltaTime) {
        timer += deltaTime;
        if (timer >= spawnFrequency) {
            timer = 0;
            spawnEnemy();
        }
    }

    /**
     * Spawn a new enemy.
     */
    private void spawnEnemy() {
        BasicEnemy enemy = factory.create();
        EntityManager.getInstance().spawnEntity(enemy);
    }
}