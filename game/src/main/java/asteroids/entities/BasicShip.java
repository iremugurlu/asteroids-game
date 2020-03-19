package asteroids.entities;

import asteroids.input.Actions;
import asteroids.input.ControlUnit;
import asteroids.managers.EntityManager;
import asteroids.util.Vector2f;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class BasicShip extends Entity {

    private static final int MAX_ACCELERATION = 3;
    static int MAX_SPEED = 250;
    private static final int SPEED_THRESHOLD = 2;
    private static final double STOPPING_THRESHOLD = 0.1;

    transient ControlUnit inputHandler;

    /**
     * Constructs an entity.
     * @param rotation    the rotation of this Entity
     * @param speed       the speed of the Entity
     * @param size        the size of the entity
     * @param position    the coordinates of the Entity
     * @param controlUnit the unit which provides an AI.
     */
    public BasicShip(
            float rotation,
            int speed,
            double size,
            Vector2f position,
            Texture texture,
            ControlUnit controlUnit) {
        super(
                rotation,
                speed,
                size,
                position,
                texture
        );
        this.inputHandler = controlUnit;
    }

    /**
     * This function performs the special update requirements of the player entity.
     *
     * @param deltaTime The time that has elapsed since the last frame.
     */
    @Override
    public void update(float deltaTime) {
        ArrayList actions = inputHandler.checkNextActions(this.getPosition(), this.getRotation());
        actionHandler(actions);
        if (updateSpeed(deltaTime)) {
            super.update(deltaTime);
        }
    }

    /**
     * This method contains the logic of shooting.
     */
    public void shoot() {
        float direction = getRotation();
        Projectile projectile = new Projectile(
                direction,
                200,
                0.8,
                new Vector2f(
                        getPosition().getCoordinateX(),
                        getPosition().getCoordinateY()
                ),
                -1
        );
        EntityManager.getInstance().spawnEntity(projectile);
    }

    /**
     * This method contains the logic of accelerating.
     */
    public void accelerate() {
        float newAcc = (float) 1.3 * getAcceleration();
        if (newAcc > MAX_ACCELERATION) {
            newAcc = MAX_ACCELERATION;
        }
        if (newAcc <= SPEED_THRESHOLD) {
            newAcc += (float) 0.2;
        }
        this.setAcceleration(newAcc);
    }

    /**
     * This method contains the logic of decelerating.
     */
    public void decelerate(float decelerationFactor) {
        float newAcc = this.getAcceleration() - decelerationFactor;
        if (newAcc <= -MAX_ACCELERATION) {
            newAcc = -MAX_ACCELERATION;
        }
        this.setAcceleration(newAcc);
    }

    /**
     * This method contains the ship speed update logic.
     *
     * @param deltaTime The time that has elapsed since the latest movement update.
     * @return true if the player will not exit the screen with the new position.
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public boolean updateSpeed(float deltaTime) {
        float newSpeed = this.getAcceleration() + this.getSpeed();
        if (newSpeed > MAX_SPEED) {
            newSpeed = MAX_SPEED;
        }
        if (newSpeed <= STOPPING_THRESHOLD) {
            newSpeed = 0;
            this.setAcceleration(0);
        } else {
            decelerate((float) 0.15);
        }

        double angle = getRotation() * -(Math.PI / 180);
        Vector2f translation = new Vector2f(
                (float) (getSpeed() * Math.sin(angle) * deltaTime),
                (float) (getSpeed() * Math.cos(angle) * deltaTime)
        );

        Vector2f newPosition = translation.add(getPosition());
        boolean inBoard = inBoard(newPosition);
        if (inBoard) {
            setSpeed(newSpeed);
        }
        return inBoard;
    }

    /**
     * This method contains the logic of rotating left.
     */
    public void rotateLeft() {
        this.setRotation((this.getRotation() + 3) % 360);
    }

    /**
     * This method contains the logic of rotating right.
     */
    public void rotateRight() {
        this.setRotation((this.getRotation() - 3) % 360);
    }

    /**
     * This method runs different functions depending on which actions needed to be performed.
     *
     * @param actions a list of actions.
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    void actionHandler(ArrayList<Actions> actions) {
        for (Actions a : actions) {
            switch (a) {
                case ACCELERATE:
                    accelerate();
                    break;
                case DECELERATE:
                    decelerate((float) 0.29);
                    break;
                case ROTATE_LEFT:
                    rotateLeft();
                    break;
                case ROTATE_RIGHT:
                    rotateRight();
                    break;
                case SHOOT:
                    shoot();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * onCollision method, called when colliding with another entity.
     * The ship gets destroyed when this method is called.
     *
     * @param other The entity that this asteroid collided with.
     */
    @Override
    public void onCollision(Entity other) {
        super.die();
    }
}
