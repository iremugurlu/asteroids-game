package asteroids.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

/**
 * Tests the Asteroid class.
 */
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
@ExtendWith(MockitoExtension.class)
public class BasicEnemyTest {

    private Entity entity;

    @Mock
    private Texture texture;

    @Mock
    private Random random;

    @Mock
    private transient SpriteBatch batch;

    private EntityManager manager;

    /**
     * Setup an default asteroid object, ran before each test.
     */
    @BeforeEach
    public void setUp() {
        manager = new EntityManager(batch, texture,random);

        when(texture.getWidth()).thenReturn(16);
        this.entity = new BasicEnemy(0,5,0.5, new Vector2f(0, 0), texture);
    }

    /**
     * Tests if the rotation of the sprite is set correctly when the asteroid is created.
     */
    @Test
    public void getSpriteTest() {
        Sprite s = entity.getSprite();
        assertEquals(s.getRotation(), entity.getRotation());
    }

    /**
     * Tests the getRotation function.
     */
    @Test
    public void getRotationTest() {
        assertEquals(0, entity.getRotation());
    }

    /**
     * Tests the setRotation function.
     */
    @Test
    public void setRotationTest() {
        entity.setRotation(-270);
        assertEquals(90, entity.getRotation());
    }

    /**
     * Tests the getAcceleration function.
     */
    @Test
    public void getAccelerationTest() {
        assertEquals(0, entity.getAcceleration());
    }

    /**
     * Tests the setAcceleration function.
     */
    @Test
    public void setAccelerationTest() {
        entity.setAcceleration(5.50f);
        assertEquals(5.50f, entity.getAcceleration());
    }

    /**
     * Tests the getRotationSpeed function.
     */
    @Test
    public void getRotationSpeedTest() {
        assertEquals(0, entity.getRotationSpeed());
    }

    /**
     * Tests the setRotationSpeed function.
     */
    @Test
    public void setRotationSpeedTest() {
        entity.setRotationSpeed(9);
        assertEquals(9, entity.getRotationSpeed());
    }

    /**
     * Tests the getSpeed function.
     */
    @Test
    public void getSpeedTest() {
        assertEquals(5, entity.getSpeed());
    }

    /**
     * Tests the setSpeed function.
     */
    @Test
    public void setSpeedTest() {
        entity.setSpeed(90);
        assertEquals(90, entity.getSpeed());
    }

    /**
     * Tests the setSpeed function.
     */
    @Test
    public void translateTest() {
        Vector2f translation = new Vector2f(
                (float) (1),
                (float) (2)
        );

        entity.translate(translation);
        assertEquals(1, entity.getPosition().getCoordinateX());
        assertEquals(2, entity.getPosition().getCoordinateY());
    }


    /**
     * Tests the getSize function.
     */
    @Test
    public void getSizeTest() {
        assertEquals(0.5, entity.getSize());
    }

    /**
     * Tests the getRadiusCollision function.
     */
    @Test
    public void getRadiusCollisionTest() {
        assertEquals(16, entity.getDiameterCollision());
        entity.getSprite();
        assertEquals(8, entity.getDiameterCollision());
    }


    /**
     * Tests if the asteroid "dies" after colliding with an player.
     * And spawns new asteroids.
     */
    @Test
    public void onCollisionTest3() {
        assertTrue(entity.isAlive());
        Player p = new Player(0, 0, 0, new Vector2f(0, 0), texture,1, 0);
        BasicEnemy a1 = new BasicEnemy(0,5,0.05,new Vector2f(0, 0),texture);

        a1.onCollision(p);
        assertFalse(a1.isAlive());

        manager.update(0);

        assertEquals(0, manager.getEntities().size());
    }

    /**
     * Enemy should die if it collides with another enemy.
     */
    @Test
    public void onCollisionTest2() {
        assertTrue(entity.isAlive());
        entity.onCollision(entity);
        assertFalse(entity.isAlive());
    }

    /**
     * Tests if the asteroid "dies" after colliding with an player.
     * And doesn't spawn new asteroids if it is small enough.
     */
    @Test
    public void onCollisionTest() {
        assertTrue(entity.isAlive());
        Player p = new Player(0, 0, 0, new Vector2f(0, 0), texture,1, 1);
        entity.onCollision(p);
        assertFalse(entity.isAlive());

        manager.update(0);
        assertTrue(manager.getEntities().size() == 0);
    }


    /**
     * Tests if the isAlive and die functions are working correctly.
     */
    @Test
    public void dieTest() {
        assertTrue(entity.isAlive());
        entity.die();
        assertFalse(entity.isAlive());
    }

    /**
     * Test if the asteroid moves forward correctly when rotated 0 degrees. (Looking to the upwards)
     */
    @Test
    public void updateTest() {
        assertEquals(-8, (int) entity.getSprite().getX());
        assertEquals(0, (int) entity.getSprite().getY());

        entity.update(1);

        assertEquals(-8, (int) entity.getSprite().getX());
        assertEquals(4, (int) entity.getSprite().getY());
    }

    /**
     * Test if the asteroid moves forward correctly when rotated 90 degrees. (Looking to the left)
     */
    @Test
    public void updateTest2() {
        entity.setRotation(90);
        assertEquals(-8, (int) entity.getSprite().getX());
        assertEquals(0, (int) entity.getSprite().getY());

        entity.update(1);

        assertEquals(-8, (int) entity.getSprite().getX());
        assertEquals(0, (int) entity.getSprite().getY());
    }
}
