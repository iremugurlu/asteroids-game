package asteroids.animations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.List;

public abstract class Animation {
    private transient Texture animationTexture;
    private transient final int height;
    private transient int spriteWidth;
    private transient final int width;
    private transient int y;
    private transient int x;
    private transient final int n;
    private transient int index;

    public Animation(Texture animationTexture, int n) {
        this.animationTexture = animationTexture;
        height = animationTexture.getHeight();
        width = animationTexture.getWidth();
        spriteWidth = width / n;
        this.n = n - 1;
        this.index = 0;
        x = 0;
        y = 0;
    }

    public boolean isFinished() {
        return index > n;
    }

    public Sprite getNextSprite() {
        if (index > n) {
            return getLastSprite();
        } else {
            Sprite sprite =  new Sprite(new TextureRegion(animationTexture, index * spriteWidth, 0, spriteWidth, height));
            index++;
            return sprite;
        }
    }

    public Sprite getNextSpriteReverse() {
        if (index < 0) {
            return getFirstSprite();
        } else {
            Sprite sprite =  new Sprite(new TextureRegion(animationTexture, index * spriteWidth, 0, spriteWidth, height));
            index--;
            return sprite;
        }
    }

    public Sprite getFirstSprite() {
        return new Sprite(new TextureRegion(animationTexture, 0, 0, spriteWidth, height));
    }

    public Sprite getLastSprite() {
        return new Sprite(new TextureRegion(animationTexture, spriteWidth * n, 0, spriteWidth, height));
    }
}
