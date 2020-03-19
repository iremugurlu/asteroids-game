package asteroids.distributors;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public final class PlayerTextureDistributor {

    private static final Texture[] textures = {
        new Texture(Gdx.files.getFileHandle("ships/player-blue.png", Files.FileType.Local)),
        new Texture(Gdx.files.getFileHandle("ships/player-green.png", Files.FileType.Local)),
        new Texture(Gdx.files.getFileHandle("ships/player-red.png", Files.FileType.Local)),
    };

    private PlayerTextureDistributor() {

    }

    public static Texture[] getPlayerTextures() {
        return textures;
    }
}
