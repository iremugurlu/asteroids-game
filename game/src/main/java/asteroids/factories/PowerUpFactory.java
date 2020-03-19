package asteroids.factories;

import asteroids.entities.Powerup;
import asteroids.entities.PowerupHeal;
import asteroids.managers.EntityManager;
import asteroids.util.Vector2f;
import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

public class PowerUpFactory extends EntityFactory {

    private static final float POWERUP_CHANCE = 0.1f;

    private transient Random random;

    public PowerUpFactory(Texture texture) {
        super(texture);
        random = new Random();
    }

    public PowerUpFactory(Texture texture, Random random) {
        super(texture);
        this.random = random;
    }

    @Override
    public Powerup create(Vector2f position) {
        int upperBound = (int) (1 / POWERUP_CHANCE);
        int randomNumber = random.nextInt(upperBound);

        if (randomNumber == 0) {
            PowerupHeal powerUp = new PowerupHeal(1, position, getTexture());
            EntityManager.getInstance().spawnEntity(powerUp);
        }

        return null;
    }
}
