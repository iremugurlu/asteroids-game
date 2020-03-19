package asteroids.entities;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import asteroids.input.Actions;
import asteroids.input.InputHandler;
import asteroids.managers.EntityManager;
import asteroids.util.Vector2f;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@SuppressWarnings("PMD.BeanMembersShouldSerialize")
@ExtendWith(MockitoExtension.class)
public class PlayerTest {

    @Mock
    Texture texture;

    @Mock
    InputHandler inputHandler;

    private Player player;

    @Mock
    private transient SpriteBatch batch;

    private transient EntityManager manager;

    @Mock
    private transient Random random;

    /**
     * Ran before each test, setting up the player and entityManager.
     */
    @BeforeEach
    public void setUp() {
        manager = new EntityManager(batch, texture, random);
        when(texture.getWidth()).thenReturn(16);
        this.player = new Player(0,5,0.5,new Vector2f(0, 0), texture, inputHandler, 1, 0);
    }

    /**
     * Tests if the rotation of the sprite is set correctly when the asteroid is created.
     */
    @Test
    public void getSpriteTest() {
        Sprite s = player.getSprite();
        assertEquals(s.getRotation(), player.getRotation());
    }

    /**
     * Tests the getRotation function.
     */
    @Test
    public void getRotationTest() {
        assertEquals(0, player.getRotation());
    }

    /**
     * Tests the setRotation function.
     */
    @Test
    public void setRotationTest() {
        player.setRotation(-270);
        assertEquals(90, player.getRotation());
    }

    /**
     * Tests the getAcceleration function.
     */
    @Test
    public void getAccelerationTest() {
        assertEquals(0, player.getAcceleration());
    }

    /**
     * Tests the setAcceleration function.
     */
    @Test
    public void setAccelerationTest() {
        player.setAcceleration(5.50f);
        assertEquals(5.50f, player.getAcceleration());
    }

    /**
     * Tests the getRotationSpeed function.
     */
    @Test
    public void getRotationSpeedTest() {
        assertEquals(0, player.getRotationSpeed());
    }

    /**
     * Tests the setRotationSpeed function.
     */
    @Test
    public void setRotationSpeedTest() {
        player.setRotationSpeed(9);
        assertEquals(9, player.getRotationSpeed());
    }

    /**
     * Tests the getSpeed function.
     */
    @Test
    public void getSpeedTest() {
        assertEquals(5, player.getSpeed());
    }

    /**
     * Tests the setSpeed function.
     */
    @Test
    public void setSpeedTest() {
        player.setSpeed(90);
        assertEquals(90, player.getSpeed());
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

        player.translate(translation);
        assertEquals(1, player.getPosition().getCoordinateX());
        assertEquals(2, player.getPosition().getCoordinateY());
    }


    /**
     * Tests the getSize function.
     */
    @Test
    public void getSizeTest() {
        assertEquals(0.5, player.getSize());
    }

    /**
     * Tests the getRadiusCollision function.
     */
    @Test
    public void getRadiusCollisionTest() {
        assertEquals(16, player.getDiameterCollision());
        player.getSprite();
        assertEquals(8, player.getDiameterCollision());
    }

    /**
     * Tests if the player dies after colliding with another entity.
     */
    @Test
    public void onCollisionTest() {
        Asteroid a = new Asteroid(0,5,0.5,new Vector2f(0, 0), texture);

        int initialLives = 2;
        assertTrue(player.isAlive());
        player.onCollision(a);
        assertTrue(player.getLives() < initialLives);
        player.onCollision(a);
        assertFalse(player.isAlive());

    }

    /**
     * Tests if the player does not die when colliding with his own bullet.
     */
    @Test
    public void onCollisionTest2() {
        Projectile projectile = new Projectile(0, 5, 1, new Vector2f(0, 0), texture, 0);

        int initialLives = 1;
        assertTrue(player.isAlive());

        player.onCollision(projectile);
        assertTrue(player.getLives() == initialLives);

        player.onCollision(projectile);
        assertTrue(player.isAlive());

    }

    /**
     * Tests if the player does not die when colliding with his own bullet, when versus is true.
     */
    @Test
    public void onCollisionTest3() {
        Projectile projectile = new Projectile(0, 5, 1, new Vector2f(0, 0), texture, 0);
        manager.setVersus(true);

        int initialLives = 1;
        assertTrue(player.isAlive());

        player.onCollision(projectile);
        assertTrue(player.getLives() == initialLives);

        player.onCollision(projectile);
        assertTrue(player.isAlive());

    }

    /**
     * Tests if the player does not die when colliding with the bullet of someone else.
     */
    @Test
    public void onCollisionTest4() {
        Projectile projectile = new Projectile(0, 5, 1, new Vector2f(0, 0), texture, 5);

        int initialLives = 1;
        assertTrue(player.isAlive());

        player.onCollision(projectile);
        assertTrue(player.getLives() == initialLives);

        player.onCollision(projectile);
        assertTrue(player.isAlive());
    }

    /**
     * Tests if the player dies when colliding with the bullet of someone else, when versus ist rue.
     */
    @Test
    public void onCollisionTest5() {
        Projectile projectile = new Projectile(0, 5, 1, new Vector2f(0, 0), texture, 5);
        manager.setVersus(true);

        int initialLives = 2;
        assertTrue(player.isAlive());

        player.onCollision(projectile);
        assertTrue(player.getLives() <= initialLives);

        player.onCollision(projectile);
        assertFalse(player.isAlive());
    }

    /**
     * Tests if the player does not die when colliding with an powerUp.
     */
    @Test
    public void onCollisionTest6() {
        Powerup powerupHeal = new PowerupHeal(1, new Vector2f(0, 0), texture);

        int initialLives = 1;
        assertTrue(player.isAlive());

        player.onCollision(powerupHeal);
        assertTrue(player.getLives() == initialLives);

        player.onCollision(powerupHeal);
        assertTrue(player.isAlive());
    }

    /**
     * Tests if the player does not die when colliding with another player, when versus is false.
     */
    @Test
    public void onCollisionTest7() {
        Player player = new Player(0,5,0.5,new Vector2f(0, 0), texture, inputHandler, 1, 1);

        int initialLives = 1;
        assertTrue(this.player.isAlive());

        this.player.onCollision(player);
        assertTrue(this.player.getLives() == initialLives);

        this.player.onCollision(player);
        assertTrue(this.player.isAlive());
    }

    /**
     * Tests if the player dies when colliding with another player, when versus is true.
     */
    @Test
    public void onCollisionTest8() {
        Player player = new Player(0,5,0.5,new Vector2f(0, 0), texture, inputHandler, 1, 1);
        manager.setVersus(true);

        int initialLives = 2;
        assertTrue(this.player.isAlive());

        this.player.onCollision(player);
        assertTrue(this.player.getLives() < initialLives);

        this.player.onCollision(player);
        assertFalse(this.player.isAlive());
    }

    /**
     * Tests if the player invincibility works after first collision.
     */
    @Test
    public void invincibilityTest() {
        Player player = new Player(0,5,0.5,new Vector2f(0, 0), texture, inputHandler, 2, 0);
        Asteroid a = new Asteroid(0,5,0.5,new Vector2f(0, 0), texture);

        int livesLeft = 1;
        assertTrue(player.isAlive());
        player.onCollision(a);
        assertTrue(player.getLives() == livesLeft);
        player.onCollision(player);
        assertTrue(player.isAlive());
        assertTrue(player.getLives() == livesLeft);
    }

    /**
     * Tests if the player accelerates correctly.
     */
    @Test
    public void accelerateTest() {
        player.setAcceleration(1);
        player.accelerate();
        assertEquals(1.5, player.getAcceleration());
    }

    /**
     * Tests if the player accelerates correctly if the MAX_ACCELERATION is reached.
     */
    @Test
    public void accelerateTest2() {
        player.setAcceleration(3);
        player.accelerate();
        assertEquals(3, player.getAcceleration());
    }

    /**
     * Tests if the player accelerates correctly.
     */
    @Test
    public void accelerateTest3() {
        player.setAcceleration(2.1f);
        player.accelerate();
        assertEquals(2.1f * 1.3, player.getAcceleration(), 0.01);
    }

    /**
     * Tests if the player decelerates correctly.
     */
    @Test
    public void deceleratesTest() {
        player.setAcceleration(2f);
        player.decelerate(1f);
        assertEquals(1f, player.getAcceleration(), 0.01);
    }

    /**
     * Tests if the player decelerates correctly, when reaching the -MAX_ACCELERATION.
     */
    @Test
    public void deceleratesTest2() {
        player.setAcceleration(2f);
        player.decelerate(6f);
        assertEquals(-3f, player.getAcceleration(), 0.01);
    }

    /**
     * Tests if the rotateLeft function works correctly.
     */
    @Test
    public void rotateLeftTest() {
        player.setRotation(5);
        player.rotateLeft();
        assertEquals(8f, player.getRotation());
    }

    /**
     * Tests if the rotateRight function works correctly.
     */
    @Test
    public void rotateRightTest() {
        player.setRotation(5);
        player.rotateRight();
        assertEquals(2f, player.getRotation());
    }

    /**
     * Tests if the updateSpeed sets you to the MAX_SPEED if you go faster.
     */
    @Test
    public void updateSpeedTest() {
        player.setSpeed(300);
        player.setAcceleration(0);
        player.updateSpeed(0);
        assertEquals(250f, player.getSpeed());
    }

    /**
     * Tests if the updateSpeed updates your speed correctly.
     */
    @Test
    public void updateSpeedTest2() {
        player.setSpeed(200);
        player.setAcceleration(15);
        player.updateSpeed(0);
        assertEquals(215f, player.getSpeed());
    }

    /**
     * Tests if the updateSpeed stops you correctly.
     */
    @Test
    public void updateSpeedTest3() {
        player.setSpeed(2);
        player.setAcceleration(-1.95f);
        player.updateSpeed(0);
        assertEquals(0f, player.getSpeed());
    }

    /**
     * Tests if the updateSpeed decelerates you correctly.
     */
    @Test
    public void updateSpeedTest4() {
        player.setSpeed(200);
        player.setAcceleration(10f);
        player.updateSpeed(0);
        assertEquals(10f - 0.15f, player.getAcceleration(), 0.01);
    }

    /**
     * Tests if the action handler processes the ACCELERATE action correctly.
     */
    @Test
    public void actionHandlerAccelerateTest() {

        ArrayList actions = new ArrayList<Actions>();
        actions.add(Actions.ACCELERATE);
        when(inputHandler.checkNextActions(player.getPosition(),
                player.getRotation())).thenReturn(actions);

        player.setAcceleration(1f);

        player.update(1f);

        assertEquals(1.8f, player.getAcceleration(), 0.01);
    }

    /**
     * Tests if the action handler processes the DECELERATE action correctly.
     */
    @Test
    public void actionHandlerDecelerateTest() {

        ArrayList actions = new ArrayList<Actions>();
        actions.add(Actions.DECELERATE);
        when(inputHandler.checkNextActions(player.getPosition(),
                player.getRotation())).thenReturn(actions);

        player.setAcceleration(1f);

        player.update(1f);

        assertEquals(0.12f, player.getAcceleration(), 0.01);
    }

    /**
     * Tests if the action handler processes the ROTATE_LEFT action correctly.
     */
    @Test
    public void actionHandlerRotateLeftTest() {

        ArrayList actions = new ArrayList<Actions>();
        actions.add(Actions.ROTATE_LEFT);
        when(inputHandler.checkNextActions(player.getPosition(),
                player.getRotation())).thenReturn(actions);

        player.setRotation(0);
        player.update(1f);

        assertEquals(3f, player.getRotation(), 0.01);
    }

    /**
     * Tests if the action handler processes the ROTATE_RIGHT action correctly.
     */
    @Test
    public void actionHandlerRotateRightTest() {

        ArrayList actions = new ArrayList<Actions>();
        actions.add(Actions.ROTATE_RIGHT);
        when(inputHandler.checkNextActions(player.getPosition(),
                player.getRotation())).thenReturn(actions);

        player.setRotation(0);
        player.update(1f);

        assertEquals(357f, player.getRotation(), 0.01);
    }

    //    /**
    //     * Tests if the action handler processes the ROTATE_RIGHT action correctly.
    //     */
    //    @Test
    //    public void actionHandlerShootTest() {
    //
    //        ArrayList actions = new ArrayList<Actions>();
    //        actions.add(Actions.SHOOT);
    //
    //        EntityManager manager = new EntityManager(spriteBatch, texture);
    //
    //        p.setEntityManager(manager);
    //
    //        when(inputHandler.checkPressedKeys()).thenReturn(actions);
    //
    //        p.setRotation(0);
    //        p.update(1f);
    //
    //        assertEquals(1, manager.getEntities().size());
    //    }


    /**
     * Tests if the isAlive and die functions are working correctly.
     */
    @Test
    public void dieTest() {
        assertTrue(player.isAlive());
        player.die();
        assertFalse(player.isAlive());
    }

    /**
     * Test if the player moves forward correctly when rotated 0 degrees. (Looking to the upwards)
     */
    @Test
    public void updateTest() {
        assertEquals(0, (int) player.getSprite().getY());
        assertEquals(-8, (int) player.getSprite().getX());

        player.update(1);

        assertEquals(-8, (int) player.getSprite().getX());
        assertEquals(4, (int) player.getSprite().getY());
    }

    /**
     * The player should not exit the board.
     */
    @Test
    public void updateOutsideBoard() {
        player.setRotation(90);
        assertEquals(0, (int) player.getSprite().getY());
        assertEquals(-8, (int) player.getSprite().getX());

        player.update(1);

        assertEquals(-8, (int) player.getSprite().getX());
        assertEquals(0, (int) player.getSprite().getY());
    }

    /**
     * The player should be outside the board.
     */
    @Test
    public void updateOutsideLargeXBoard() {
        Player p = new Player(0,5,0.5,new Vector2f(10000, 0), texture, inputHandler, 1, 1);

        assertFalse(p.inBoard());
    }

    /**
     * The player should be outside the board.
     */
    @Test
    public void updateOutsideLargeYBoard() {
        Player p = new Player(0,5,0.5,new Vector2f(0, 10000), texture, inputHandler, 1, 1);

        assertFalse(p.inBoard());
    }

    /**
     * The player should be outside the board.
     */
    @Test
    public void updateOutsideSmallXBoard() {
        Player p = new Player(0,5,0.5,new Vector2f(-1000, 0), texture, inputHandler, 1, 1);

        assertFalse(p.inBoard());
    }

    /**
     * The player should be outside the board.
     */
    @Test
    public void updateOutsideSmallYBoard() {
        Player p = new Player(0,5,0.5,new Vector2f(0, -1000), texture, inputHandler, 1, 1);

        assertFalse(p.inBoard());
    }

    /**
     * The player should NOT be outside the board.
     */
    @Test
    public void updateNotOutsideBoard() {
        Player p = new Player(0,5,0.5,new Vector2f(298, 467), texture, inputHandler, 1, 1);

        assertTrue(p.inBoard());
    }
}
