package asteroids.managers;

import asteroids.entities.Asteroid;
import asteroids.factories.AsteroidFactory;
import asteroids.util.Vector2f;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.List;

/**
 * A class that decides when and where to spawn new asteroids.
 */
@SuppressWarnings({"PMD.DataflowAnomalyAnalysis"})
public class AsteroidSpawnManager {

    private transient float timer = 0;
    private transient float spawnFrequency = 2;

    transient AsteroidFactory factory;

    /**
     * Create a new asteroid spawnManager.
     */
    public AsteroidSpawnManager() {
        this.factory = new AsteroidFactory(
                0.05,
                0.25,
                new Texture(
                        Gdx.files.getFileHandle("asteroids/asteroid1.png", Files.FileType.Local)
                )
        );
    }

    /**
     * Create a new Asteroid SpawnManager for testing purposes.
     * @param texture Texture to assign to an Asteroid.
     */
    public AsteroidSpawnManager(Texture texture) {
        this.factory = new AsteroidFactory(
                0.05,
                0.25,
                texture,
                true
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
            spawnAsteroid();
        }
    }

    /**
     * Spawn a new asteroid.
     * TODO: DON'T HARDCODE THE HEIGHT AND WIDTH, BUT USE THE SCREEN SIZE.
     */
    private void spawnAsteroid() {
        float x = (float) (Math.random() * 600);
        Asteroid asteroid = factory.create(new Vector2f(x, 480));

        EntityManager.getInstance().spawnEntity(asteroid);
    }

    /**
     * Split an asteroid.
     * @param current Asteroid to be split into smaller ones.
     */
    public void splitAsteroid(Asteroid current) {
        List<Asteroid> list = factory.splitAsteroid(current);

        for (Asteroid item : list) {
            EntityManager.getInstance().spawnEntity(item);
        }
    }
}
