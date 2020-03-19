package asteroids.entities;

import static org.junit.jupiter.api.Assertions.*;

import asteroids.input.InputHandler;
import asteroids.util.Vector2f;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests the Projectile class.
 */
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
@ExtendWith(MockitoExtension.class)
public class ProjectileTest {

    private Projectile projectile;

    @Mock
    private Texture texture;

    @Mock
    InputHandler inputHandler;

    /**
     * Setup an default projectile object, ran before each test.
     */
    @BeforeEach
    public void setUp() {
        this.projectile = new Projectile(0, 5, 1, new Vector2f(0, 0), texture, 0);
    }

    /**
     * Tests if the rotation of the sprite is set correctly when the projectile is created.
     */
    @Test
    public void getSpriteTest() {
        Sprite s = projectile.getSprite();
        assertEquals(s.getRotation(), projectile.getRotation());
    }

    /**
     * Tests if the setEntityOwner method works correctly.
     */
    @Test
    public void setEntityOwner() {
        assertEquals(0, projectile.getEntityIdOwner());

        projectile.setEntityIdOwner(3);

        assertEquals(3, projectile.getEntityIdOwner());
    }

    /**
     * Tests the getRotation function.
     */
    @Test
    public void getRotationTest() {
        assertEquals(0, projectile.getRotation());
    }

    /**
     * Tests the setRotation function.
     */
    @Test
    public void setRotationTest() {
        projectile.setRotation(-270);
        assertEquals(90, projectile.getRotation());
    }

    /**
     * Tests the getRotationSpeed function.
     */
    @Test
    public void getRotationSpeedTest() {
        assertEquals(0, projectile.getRotationSpeed());
    }

    /**
     * Tests the setRotationSpeed function.
     */
    @Test
    public void setRotationSpeedTest() {
        projectile.setRotationSpeed(9);
        assertEquals(9, projectile.getRotationSpeed());
    }

    /**
     * Tests the getSpeed function.
     */
    @Test
    public void getSpeedTest() {
        assertEquals(5, projectile.getSpeed());
    }

    /**
     * Tests the setSpeed function.
     */
    @Test
    public void setSpeedTest() {
        projectile.setSpeed(90);
        assertEquals(90, projectile.getSpeed());
    }

    /**
     * Tests the getSize function.
     */
    @Test
    public void getSizeTest() {
        assertEquals(1, projectile.getSize());
    }


    /**
     * Tests if the projectile dies after colliding with another entity.
     */
    @Test
    public void onCollisionTest() {
        assertTrue(projectile.isAlive());
        Asteroid a = new Asteroid(0, 0, 1, new Vector2f(0, 0), texture);
        projectile.onCollision(a);
        assertFalse(projectile.isAlive());
    }

    /**
     * Tests if the projectile passes through an powerUp.
     */
    @Test
    public void onCollisionTest2() {
        assertTrue(projectile.isAlive());

        PowerupHeal powerupHeal = new PowerupHeal(1, new Vector2f(0, 0), texture);
        projectile.onCollision(powerupHeal);

        assertTrue(projectile.isAlive());
    }

    /**
     * Tests if the projectile passes through the player that owns it.
     */
    @Test
    public void onCollisionTest3() {
        assertTrue(projectile.isAlive());
        projectile.setEntityIdOwner(1);

        Player player = new Player(0,5,0.5,new Vector2f(0, 0), texture, inputHandler, 1, 1);
        projectile.onCollision(player);

        assertTrue(projectile.isAlive());
    }

    /**
     * Tests if the projectile gets destroy when hitting a that does not own it.
     */
    @Test
    public void onCollisionTest4() {
        assertTrue(projectile.isAlive());
        projectile.setEntityIdOwner(2);

        Player player = new Player(0,5,0.5,new Vector2f(0, 0), texture, inputHandler, 1, 0);
        projectile.onCollision(player);

        assertFalse(projectile.isAlive());
    }

    /**
     * Tests if the isAlive and die functions are working correctly.
     */
    @Test
    public void dieTest() {
        assertTrue(projectile.isAlive());
        projectile.die();
        assertFalse(projectile.isAlive());
    }

    /**
     * Test if the projectile moves forward correctly when rotated 0 degrees.
     * (Looking to the upwards)
     */
    @Test
    public void updateTest() {
        assertEquals(0, (int) projectile.getSprite().getY());
        assertEquals(0, (int) projectile.getSprite().getX());

        projectile.update(1);

        assertEquals(0, (int) projectile.getSprite().getX());
        assertEquals(5, (int) projectile.getSprite().getY());
    }

    /**
     * Test if the projectile moves forward correctly when rotated 90 degrees. (Looking to the left)
     */
    @Test
    public void updateTest2() {
        projectile.setRotation(90);
        assertEquals(0, (int) projectile.getSprite().getY());
        assertEquals(0, (int) projectile.getSprite().getX());

        projectile.update(1);

        assertEquals(-5, (int) projectile.getSprite().getX());
        assertEquals(0, (int) projectile.getSprite().getY());
    }
}
