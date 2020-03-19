package asteroids.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import asteroids.managers.EntityManager;
import asteroids.util.Vector2f;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@SuppressWarnings("PMD.BeanMembersShouldSerialize")
@ExtendWith(MockitoExtension.class)
public class PowerupHealTest {


    private PowerupHeal powerupHeal;

    @Mock
    private Texture texture;

    @Mock
    private transient SpriteBatch batch;

    private EntityManager manager;

    @Mock
    private Random random;

    /**
     * Ran before each test, setting up the entitymanager and powerup.
     */
    @BeforeEach
    public void setUp() {
        manager = new EntityManager(batch, texture, random);

        when(texture.getWidth()).thenReturn(16);

        this.powerupHeal = new PowerupHeal(1, new Vector2f(0, 0), texture);
    }

    /**
     * Tests if the rotation of the sprite is set correctly when the asteroid is created.
     */
    @Test
    public void getSpriteTest() {
        Sprite s = powerupHeal.getSprite();
        assertEquals(s.getRotation(), powerupHeal.getRotation());
    }

    /**
     * Tests if the powerup gets applied correctly to a player.
     */
    @Test
    public void onCollisionTest() {
        Player p = new Player(0,5,0.5,new Vector2f(0, 0), texture, 1, 0);

        powerupHeal.onCollision(p);

        assertEquals(2, p.getLives());
        assertEquals(false, powerupHeal.isAlive());

    }

    /**
     * Tests if the powerup doesn't get destroyed when colliding with an asteroid.
     */
    @Test
    public void onCollisionTest2() {
        Asteroid a = new Asteroid(0,5,0.5,new Vector2f(0, 0), texture);

        powerupHeal.onCollision(a);

        assertEquals(true, a.isAlive());
        assertEquals(true, powerupHeal.isAlive());
    }
}
