package com.turbogerm.hellhopper.screens.splash;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.germlibrary.util.GameUtils;
import com.turbogerm.hellhopper.HellHopper;
import com.turbogerm.hellhopper.resources.ResourceNames;

public final class SplashTitle {
    
    private static final float GRAVITY = 1100.0f;
    private static final float JUMP_SPEED = 850.0f;
    
    private static final float INITIAL_POSITION_Y = 1000.0f;
    private static final float COLLISION_OFFSET_Y = 45.0f;
    
    private static final float RESTITUTION_MULTIPLIER = 1.0f / 1.5f;
    private static final float RESTITUTION_SPEED_DECREASE = 50.0f;
    
    private final Sprite mSprite;
    
    private final Vector2 mPosition;
    private float mSpeedY;
    private float mCurrentMaxJumpSpeed;
    
    private float mMinPositionY;
    
    private boolean mIsFinished;
    
    private final Sound mJumpSound;
    
    public SplashTitle(AssetManager assetManager) {
        
        TextureAtlas atlas = assetManager.get(ResourceNames.GRAPHICS_GUI_ATLAS);
        mSprite = atlas.createSprite(ResourceNames.GUI_SPLASH_TITLE_IMAGE_NAME);
        
        mPosition = new Vector2();
        mPosition.x = (HellHopper.VIEWPORT_WIDTH - mSprite.getWidth()) / 2.0f;
        
        mJumpSound = assetManager.get(ResourceNames.SOUND_JUMP);
    }
    
    public void reset(float minPositionY) {
        mPosition.y = INITIAL_POSITION_Y;
        mMinPositionY = minPositionY - COLLISION_OFFSET_Y;
        mSpeedY = 0.0f;
        mCurrentMaxJumpSpeed = JUMP_SPEED; 
        mIsFinished = false;
    }
    
    public void update(float delta) {
        mPosition.y += mSpeedY * delta;
        mPosition.y = Math.max(mPosition.y, mMinPositionY);
        
        if (mPosition.y <= mMinPositionY) {
            mPosition.y = mMinPositionY + GameUtils.EPSILON;
            mSpeedY = Math.min(-mSpeedY * RESTITUTION_MULTIPLIER, mCurrentMaxJumpSpeed);
            mCurrentMaxJumpSpeed = Math.min(mCurrentMaxJumpSpeed, mSpeedY);
            mSpeedY = Math.max(mSpeedY - RESTITUTION_SPEED_DECREASE, 0.0f);
            if (mSpeedY <= GameUtils.EPSILON) {
                mIsFinished = true;
            }
            
            if (!mIsFinished) {
                mJumpSound.play();
            }
        } else {
            mSpeedY = MathUtils.clamp(mSpeedY - GRAVITY * delta, -mCurrentMaxJumpSpeed, mCurrentMaxJumpSpeed);
        }
    }
    
    public void render(SpriteBatch batch) {
        mSprite.setPosition(mPosition.x, mPosition.y);
        mSprite.draw(batch);
    }
    
    public boolean isFinished() {
        return mIsFinished;
    }
}
