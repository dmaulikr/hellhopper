package com.turbogerm.helljump.game.character.graphics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.helljump.resources.ResourceNames;

public final class CharacterEyesGraphicsStunned extends CharacterGraphicsBase {
    
    private static final float OFFSET_X = 0.1f;
    private static final float OFFSET_Y = 0.65f;
    private static final float WIDTH = 0.8f;
    private static final float HEIGHT = 0.35f;
    
    private static final Color DEFAULT_COLOR;
    
    private final Sprite mSprite;
    
    static {
        DEFAULT_COLOR = new Color(1.0f, 0.5f, 0.0f, 1.0f);
    }
    
    public CharacterEyesGraphicsStunned(AssetManager assetManager) {
        
        TextureAtlas atlas = assetManager.get(ResourceNames.CHARACTER_ATLAS);
        mSprite = atlas.createSprite(ResourceNames.CHARACTER_EYES_STUNNED_IMAGE_NAME);
        mSprite.setSize(WIDTH, HEIGHT);
        mSprite.setColor(DEFAULT_COLOR);
    }
    
    @Override
    public void reset() {
    }
    
    @Override
    public void render(SpriteBatch batch, Vector2 characterPosition) {
        mSprite.setPosition(characterPosition.x + OFFSET_X, characterPosition.y + OFFSET_Y);
        mSprite.draw(batch);
    }
}