package asteroids.managers;

import asteroids.factories.PowerUpFactory;
import asteroids.util.Vector2f;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public final class Spawner {


    private transient AsteroidSpawnManager asteroidSpawnManager;
    private transient EnemySpawnManager enemySpawnManager;
    private transient PowerUpFactory powerUpFactory;

    /**
     * Basic constructor for the Spawner class.
     */
    public Spawner() {
        asteroidSpawnManager = new AsteroidSpawnManager();
        enemySpawnManager = new EnemySpawnManager();
        powerUpFactory = new PowerUpFactory(
                new Texture(Gdx.files.getFileHandle("upgrades/heal.png", Files.FileType.Local))
        );
    }

    /**
     * Constructor for testing purposes of the Spawner class.
     * @param texture
     */
    public Spawner(Texture texture) {
        asteroidSpawnManager = new AsteroidSpawnManager(texture);
        enemySpawnManager = new EnemySpawnManager(texture);
        powerUpFactory = new PowerUpFactory(texture);
    }

    public void update(float deltaTime) {
        asteroidSpawnManager.update(deltaTime);
        enemySpawnManager.update(deltaTime);
    }

    public void spawnPowerup(Vector2f position) {
        powerUpFactory.create(position);
    }

    public AsteroidSpawnManager getAsteroidSpawnManager() {
        return asteroidSpawnManager;
    }
}
