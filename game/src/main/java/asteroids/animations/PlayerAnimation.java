package asteroids.animations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.List;

public class PlayerAnimation extends Animation {

    public PlayerAnimation(Texture animationTexture) {
        super(animationTexture, 6);
    }

    public PlayerAnimation() {
        super(new Texture(com.badlogic.gdx.Gdx.files.internal("animation/player-blue.png")), 6);
    }

}
