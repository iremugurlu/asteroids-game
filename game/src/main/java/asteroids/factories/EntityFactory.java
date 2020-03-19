package asteroids.factories;

import asteroids.entities.Entity;
import asteroids.util.Vector2f;
import com.badlogic.gdx.graphics.Texture;

public abstract class EntityFactory {

    private Texture texture;

    public EntityFactory(Texture texture) {
        this.texture = texture;
    }

    public abstract Entity create(Vector2f position);

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
