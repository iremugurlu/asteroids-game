package asteroids.entities;

import asteroids.util.Vector2f;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

@SuppressWarnings({"PMD.BeanMembersShouldSerialize", "PMD.DataflowAnomalyAnalysis"})
public abstract class Entity {

    private float rotation;
    private double rotationSpeed;
    private Vector2f position;
    private float speed;
    private float acceleration;
    private boolean alive;
    private double size;
    private double diameterCollision;
    private Sprite sprite;
    private boolean exploding;

    /**
     * Entity objects are objects which have interactivity on the playing field
     * e.g. players asteroids etc.
     *
     * @param rotation the rotation of the entity
     * @param speed    the speed of the entity
     * @param size     the size of the entity
     * @param position the initial coordinates of the entity
     * @param texture  the texture of the entity
     */
    public Entity(float rotation, int speed, double size, Vector2f position, Texture texture) {
        this.rotation = rotation;
        this.rotationSpeed = 0;
        this.speed = speed;
        this.alive = true;
        this.size = size;
        this.position = position;
        this.sprite = new Sprite(texture);
        this.sprite.setCenter(position.getCoordinateX(), position.getCoordinateY());
        this.diameterCollision = sprite.getWidth();
        exploding = false;
    }

    /**
     * Called to set the life status of the entity.
     * Result: the entity is aliven't.
     */
    public void die() {
        this.alive = false;
    }

    /**
     * Called to move the Entity.
     *
     * @param vector Vector2f pointing in the direction to move in
     */
    public void translate(Vector2f vector) {
        position.add(vector);
    }

    /**
     * Sets the rotation of the Entity.
     *
     * @param rotation Float rotation of the Entity.
     */
    public void setRotation(float rotation) {
        if (rotation < 0) {
            this.rotation = 360 + rotation;
        } else {
            this.rotation = rotation;
        }
    }

    public double getRotationSpeed() {
        return rotationSpeed;
    }

    public float getAcceleration() {
        return this.acceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public void setRotationSpeed(double rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getRotation() {
        return rotation;
    }

    public float getSpeed() {
        return speed;
    }

    public boolean isAlive() {
        return alive;
    }

    public double getSize() {
        return size;
    }

    public Vector2f getPosition() {
        return position;
    }

    public double getDiameterCollision() {
        return diameterCollision;
    }

    public boolean isExploding() {
        return exploding;
    }

    public void setExploding() {
        this.exploding = true;
    }

    /**
     * A method that updates the position of the entity based
     * on the current speed and rotation of the entity.
     *
     * @param deltaTime The time that has elapsed since the latest movement update.
     */
    protected void applyMovement(float deltaTime) {
        double angle = rotation * -(Math.PI / 180);

        Vector2f translation = new Vector2f(
                (float) (speed * Math.sin(angle) * deltaTime),
                (float) (speed * Math.cos(angle) * deltaTime)
        );

        this.position.add(translation);
    }

    /**
     * A method that is called every frame.
     *
     * @param deltaTime The time that has elapsed since the last frame.
     */
    public void update(float deltaTime) {
        applyMovement(deltaTime);
    }

    /**
     * A method to convert the information on the entity into Sprite data.
     *
     * @return A Sprite containing the logic of the entity.
     */
    public Sprite getSprite() {
        sprite.setCenter(position.getCoordinateX(), position.getCoordinateY());
        if (this.getClass() != Asteroid.class) {
            sprite.setRotation(rotation);
        }
        sprite.setScale((float) size);
        diameterCollision = sprite.getWidth() * size;

        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    /**
     * onCollision method, called when colliding with another entity.
     * The entity gets destroyed when this method is called.
     *
     * @param other The entity that this entity collided with.
     */
    public void onCollision(Entity other) {
        die();
    }

    /**
     * Method that checks if the entity is in the board.
     *
     * @return true if the entity is in the board.
     */
    public boolean inBoard() {
       return inBoard(this.getPosition());
    }

    /**
     * Method that checks if the entity is in the board.
     *
     * @param position position of an element.
     * @return true if the entity is in the board.
     */
    public static boolean inBoard(Vector2f position) {
        int boardHeight = 1000;
        int boardWidth = 1000;
        if (Gdx.graphics != null) {
            boardWidth = Gdx.graphics.getWidth();
            boardHeight = Gdx.graphics.getHeight();
        }
        Vector2f v = new Vector2f(boardWidth, boardHeight);
        return !(position.getCoordinateX() < 0
                || position.getCoordinateY() < 0
                || position.getCoordinateX() >= boardWidth
                || position.getCoordinateY() >= boardHeight);
    }
}
