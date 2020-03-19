package asteroids.factories;

import asteroids.distributors.AsteroidsTextureDistributor;
import asteroids.entities.Asteroid;
import asteroids.util.Vector2f;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory that creates asteroid objects.
 */
@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class AsteroidFactory extends EntityFactory {

    private transient double minSize;
    private transient double maxSize;

    private transient Texture[] textures;

    /**
     * Constructor for the Asteroid Factory.
     *
     * @param minSize The min. size an asteroid needs to be.
     * @param maxSize The max. size an asteroid can be.
     * @param texture The texture that the asteroid has to get.
     */
    public AsteroidFactory(double minSize, double maxSize, Texture texture) {
        super(texture);
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.textures = AsteroidsTextureDistributor.getAsteroidTextures();
    }

    /**
     * Overloaded constructor for mocking purposes.
     *
     * @param minSize The min. size an asteroid needs to be.
     * @param maxSize The max. size an asteroid can be.
     * @param texture The texture that the asteroid has to get.
     * @param mocked  The variable that indicates that the class is being mocked.
     */
    public AsteroidFactory(double minSize, double maxSize, Texture texture, boolean mocked) {
        super(texture);
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.textures = new Texture[] {texture, texture, texture};
    }

    @Override
    public Asteroid create(Vector2f position) {

        double size = (Math.random() * (maxSize - minSize)) + minSize;

        int asteroidRotation = (int) (Math.random() * ((60) + 1)) + 145;
        int asteroidSpeed = (int) (10 + 20 / (size + 0.5));

        int rand = (int) (Math.random() * (textures.length - 1) + 1);
        Texture asteroidTexture = textures[rand];

        return new Asteroid(
                asteroidRotation,
                asteroidSpeed,
                size,
                position,
                asteroidTexture
        );
    }

    /**
     * Split an Asteroid into 2 to 4 smaller pieces.
     *
     * @param current Asteroid to be split into smaller pieces.
     * @return List of new Asteroids to spawn.
     */
    public List<Asteroid> splitAsteroid(Asteroid current) {
        int asteroidAmount = (int) (Math.random() * 2) + 2;

        List<Asteroid> res = new ArrayList<>(asteroidAmount);

        for (int ii = 0; ii < asteroidAmount; ii++) {

            double asteroidSize = ((float) current.getSize() / (float) asteroidAmount) + Math.random() * 0.03f;
            int asteroidSpeed = (int) (10 + 20 / (asteroidSize + 0.5));
            float randomRotation = (float) Math.random() * 30;

            Vector2f pos = new Vector2f(
                    current.getPosition().getCoordinateX() + randomRotation,
                    current.getPosition().getCoordinateY() + randomRotation
            );

            int rand = (int) (Math.random() * (textures.length - 1) + 1);
            Texture asteroidTexture = textures[rand];

            res.add(new Asteroid(
                    current.getRotation() + randomRotation,
                    asteroidSpeed,
                    asteroidSize,
                    pos,
                    asteroidTexture
            ));
        }

        return res;
    }
}
