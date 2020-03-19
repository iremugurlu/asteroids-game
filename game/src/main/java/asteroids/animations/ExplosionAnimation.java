package asteroids.animations;

import com.badlogic.gdx.graphics.Texture;

public class ExplosionAnimation extends Animation {

    public ExplosionAnimation(Texture animationTexture) {
        super(animationTexture, 7);
    }

    public ExplosionAnimation() {
        super(new Texture(com.badlogic.gdx.Gdx.files.internal("animation/explosion.png")), 7);
    }

}
