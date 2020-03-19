package asteroids.distributors;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public final class AsteroidsTextureDistributor {

    private static final Texture[] textures = {
        new Texture(Gdx.files.getFileHandle("asteroids/asteroid1.png", Files.FileType.Local)),
        new Texture(Gdx.files.getFileHandle("asteroids/asteroid2.png", Files.FileType.Local)),
        new Texture(Gdx.files.getFileHandle("asteroids/asteroid3.png", Files.FileType.Local)),
        new Texture(Gdx.files.getFileHandle("asteroids/asteroid4.png", Files.FileType.Local)),
        new Texture(Gdx.files.getFileHandle("asteroids/asteroid5.png", Files.FileType.Local)),
        new Texture(Gdx.files.getFileHandle("asteroids/asteroid6.png", Files.FileType.Local)),
        new Texture(Gdx.files.getFileHandle("asteroids/asteroid7.png", Files.FileType.Local)),
        new Texture(Gdx.files.getFileHandle("asteroids/asteroid8.png", Files.FileType.Local))
    };

    private AsteroidsTextureDistributor() {
        
    }

    public static Texture[] getAsteroidTextures() {
        return textures;
    }
}
