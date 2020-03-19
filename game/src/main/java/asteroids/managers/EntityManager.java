package asteroids.managers;

import asteroids.entities.Asteroid;
import asteroids.entities.Entity;
import asteroids.entities.Player;
import asteroids.factories.PlayerFactory;
import asteroids.factories.PowerUpFactory;
import asteroids.input.InputHandler;
import asteroids.util.Vector2f;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class EntityManager {

    private static int PLAYERS_REQUIRED = 1;
    private static int NUM_PLAYERS = 1;
    private static final int PLAYER_LIVES = 2;
    private boolean versus = false;


    private boolean running;

    private static EntityManager object = null;

    private transient CollisionsManager collisionsManager;
    private transient Spawner spawner;

    private transient PlayerFactory playerFactory;

    private transient SpriteBatch batch;
    private transient ArrayList<Entity> entities;
    private transient ArrayList<Entity> newEntities;
    private transient ArrayList<Player> players;

    private transient SoundManager soundManager;

    /**
     * Initialise the EntityManager.
     * Create a Asteroid SpawnManager and a Player Factory.
     */
    public void init() {
        this.spawner = new Spawner();
        this.playerFactory = new PlayerFactory(
                PLAYER_LIVES,
                new Texture(Gdx.files.getFileHandle("ships/player-blue.png", Files.FileType.Local))
        );

        this.players = new ArrayList<>();
        this.soundManager = SoundManager.getInstance();

        spawnPlayers(NUM_PLAYERS);
        //setVersus(true);
    }

    /**
     * Initialise the EntityManager for testing purposes.
     * Create a Asteroid SpawnManager and a Player Factory.
     *
     * @param texture Texture to assign to Entities.
     */
    public void init(Texture texture, InputHandler inputHandler) {
        this.spawner = new Spawner(texture);
        this.playerFactory = new PlayerFactory(
                PLAYER_LIVES,
                texture,
                inputHandler
        );
        this.players = new ArrayList<>();

        spawnPlayers(NUM_PLAYERS);
    }

    /**
     * Constructor for the EntityManager class.
     */
    private EntityManager() {
        this.entities = new ArrayList<Entity>();
        this.newEntities = new ArrayList<Entity>();
        this.batch = new SpriteBatch();
        this.collisionsManager = new CollisionsManager(entities);
        this.running = true;
        this.players = new ArrayList<>();
    }

    /**
     * Get the AsteroidSpawn manager.
     *
     * @return The asteroid spawnManager.
     */
    public AsteroidSpawnManager getSpawnManager() {
        return spawner.getAsteroidSpawnManager();
    }

    /**
     * Method to get the instance of the EntityManager class.
     *
     * @return Instance of EntityManager
     */
    public static EntityManager getInstance() {
        if (object == null) {
            object = new EntityManager();
        }

        return object;
    }

    /**
     * Overloaded constructor for mocking purposes.
     *
     * @param batch SpriteBatch that is being mocked.
     */
    @SuppressWarnings("PMD.AssignmentToNonFinalStatic")
    public EntityManager(SpriteBatch batch, Texture texture, Random random) {
        this.newEntities = new ArrayList<Entity>();
        this.batch = batch;
        this.entities = new ArrayList<Entity>();
        this.collisionsManager = new CollisionsManager(entities);
        this.spawner = new Spawner(texture);
        this.playerFactory = new PlayerFactory(PLAYER_LIVES, texture, null);
        object = this;
        this.running = true;
    }

    /**
     * Method that is called every frame.
     */
    public void update(float deltaTime) {
        collisionsManager.calculateCollisions();

        entities.addAll(newEntities);
        this.newEntities.clear();

        spawnAsteroids(deltaTime);

        int living = checkLiving();
        drawLiving(deltaTime);

        if (living < PLAYERS_REQUIRED) {
            endGame();
        }
    }

    /**
     * Method that requests the EntityManager to spawn a new entity on the next frame.
     *
     * @param entity Entity requested to be spawned.
     * @return Boolean if the request was successful.
     */
    public boolean spawnEntity(Entity entity) {
        return newEntities.add(entity);
    }

    public void endGame() {
        setRunning(false);
    }

    /**
     * Remove entities that are dead.
     * A dead entity is recognised by an internal boolean value.
     *
     * @return The number of player entities alive;
     */
    private int checkLiving() {
        int numPlayers = 0;

        Iterator<Entity> it = entities.iterator();
        while (it.hasNext()) {
            Entity item = it.next();

            if (item instanceof Player) {
                numPlayers++;
            }
            if (!item.isAlive()) {
                it.remove();
            }
        }

        return numPlayers;
    }

    /**
     * Draw all current entities.
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    private void drawLiving(float deltaTime) {
        batch.begin();
        for (Entity entity : entities) {
            entity.update(deltaTime);
            Sprite sprite = entity.getSprite();
            if (entity.getClass().equals(Asteroid.class)) {
                sprite.rotate((float) 0.5);
            }
            sprite.draw(batch);
        }
        batch.end();
    }

    private void spawnAsteroids(float deltaTime) {
        spawner.update(deltaTime);
    }

    private void spawnPlayers(int num) {
        ArrayList<Player> playerList = playerFactory.spawnPlayers(num);
        newEntities.addAll(playerList);
        players.addAll(playerList);
    }

    public void spawnAsteroid(Vector2f position) {
        spawner.spawnPowerup(position);
    }

    /**
     * Disposes of the current instance of EntityManager.
     */
    @SuppressWarnings("PMD.NullAssignment")
    public void dispose() {
        batch.dispose();
        object = null;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Enables versus multi-player mode.
     *
     * @param value Boolean if versus is enabled.
     */
    public void setVersus(Boolean value) {
        this.versus = value;
        if (value) {
            PLAYERS_REQUIRED = 2;
        }
    }

    public boolean getVersus() {
        return versus;
    }
    
    public SoundManager getSoundManager() {
        return soundManager;
    }

    public static void setNumOfPlayers(int n) {
        NUM_PLAYERS = n;
    }
}
