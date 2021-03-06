/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2013 Goran Mrzljak
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.turbogerm.helljump.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.turbogerm.helljump.CameraData;
import com.turbogerm.helljump.HellJump;
import com.turbogerm.helljump.debug.DebugData;
import com.turbogerm.helljump.game.background.EndBackgroundScene;
import com.turbogerm.helljump.game.background.GameBackground;
import com.turbogerm.helljump.game.background.GameForeground;
import com.turbogerm.helljump.game.character.GameCharacter;
import com.turbogerm.helljump.game.generator.RiseGenerator;

public final class GameArea {
    
    public static final float GAME_AREA_WIDTH = HellJump.VIEWPORT_WIDTH * GameAreaUtils.PIXEL_TO_METER;
    public static final float GAME_AREA_HEIGHT = HellJump.VIEWPORT_HEIGHT * GameAreaUtils.PIXEL_TO_METER;
    
    public static final float GAME_AREA_WIDTH_OFFSETS = GAME_AREA_WIDTH / GameAreaUtils.OFFSET_WIDTH;
    
    private static final float CHARACTER_POSITION_AREA_FRACTION = 0.4f;
    private static final float VISIBLE_AREA_MINIMUM_DISTANCE_TO_RISE = 2.0f;
    
    private static final float END_BACKGROUND_APPEARANCE_DISTANCE_FROM_END = 10.0f + GAME_AREA_HEIGHT;
    private static final float GAME_BACKGROUND_VANISHING_DISTANCE_FROM_END = 3.0f;
    
    private static final int SPRITE_BATCH_SIZE = 100;
    
    private final AssetManager mAssetManager;
    private final SpriteBatch mBatch;
    private final DebugData mDebugData;
    private final BitmapFont mItemFont;
    private final CameraData mCameraData;
    
    private Rise mRise;
    private float mRiseHeight;
    
    private int mRiseScore;
    
    private float mVisibleAreaPosition;
    private final GameCharacter mCharacter;
    private final GameActiveAreaObjects mActiveAreaObjects;
    
    private boolean mIsGameOver;
    
    private final GameBackground mGameBackground;
    private final EndBackgroundScene mEndBackgroundScene;
    private final GameForeground mGameForeground;
    
    public GameArea(CameraData cameraData, AssetManager assetManager, BitmapFont itemFont) {
        
        mAssetManager = assetManager;
        mBatch = new SpriteBatch(SPRITE_BATCH_SIZE);
        mDebugData = new DebugData();
        mItemFont = itemFont;
        mCameraData = cameraData;
        
        mCharacter = new GameCharacter(mCameraData, mAssetManager);
        mActiveAreaObjects = new GameActiveAreaObjects();
        
        mGameBackground = new GameBackground(
                GameAreaUtils.getBackgroundColorSpectrum(), true, mCameraData, assetManager);
        mEndBackgroundScene = new EndBackgroundScene(cameraData, assetManager);
        mGameForeground = new GameForeground(mCameraData, mAssetManager);
        
        reset();
    }
    
    public void reset() {
        mIsGameOver = false;
        
        mRise = RiseGenerator.generate(mAssetManager);
        mRiseHeight = mRise.getHeight();
        
        mRiseScore = 0;
        
        mVisibleAreaPosition = 0.0f;
        mCharacter.reset(mRiseHeight);
        
        mEndBackgroundScene.reset(mRiseHeight);
        mGameForeground.reset(mRiseHeight);
    }
    
    public void update(float delta) {
        
        mIsGameOver = mCharacter.isFinished();
        if (mIsGameOver) {
            return;
        }
        
        float horizontalSpeed = GameInput.getHorizontalSpeed();
        
        updateGameArea(horizontalSpeed, delta);
        
        float effectiveCharPositionY = Math.min(mCharacter.getPosition().y, mRiseHeight);
        mRiseScore = Math.max(mRiseScore, (int) (effectiveCharPositionY * GameAreaUtils.METER_TO_PIXEL));
        
        mGameBackground.setSpectrumFraction(mVisibleAreaPosition / mRiseHeight);
        
        if (isEndBackgroundVisible()) {
            mEndBackgroundScene.update(delta);
        }
        
    }
    
    public void render() {
        
        mCameraData.setGameAreaPosition(mVisibleAreaPosition);
        mBatch.setProjectionMatrix(mCameraData.getGameAreaMatrix());
        mBatch.begin();
        
        if (isGameBackgroundVisible()) {
            mGameBackground.render(mBatch, mVisibleAreaPosition);
        }
        if (isEndBackgroundVisible()) {
            mEndBackgroundScene.render(mBatch);
        }
        
        mActiveAreaObjects.render(mBatch);
        mCharacter.render(mBatch);
        
        mGameForeground.render(mBatch, mVisibleAreaPosition, mGameBackground.getBackgroundColor());
        mBatch.end();
        
        // TODO: for debugging, remove
        mDebugData.update(mBatch, getCurrentRiseSection(), mCharacter);
        
        mBatch.setProjectionMatrix(mCameraData.getGuiMatrix());
        mBatch.begin();
        mActiveAreaObjects.renderText(mBatch, mVisibleAreaPosition, mItemFont);
        mBatch.end();
    }
    
    private void updateGameArea(float horizontalSpeed, float delta) {
        
        mActiveAreaObjects.update(mRise, mCharacter, mVisibleAreaPosition, delta);
        
        mCharacter.update(
                horizontalSpeed,
                mActiveAreaObjects.getPlatformToCharCollisionData(),
                mActiveAreaObjects.getActiveRiseSections(),
                mActiveAreaObjects.getVisiblePlatforms(),
                mActiveAreaObjects.getVisibleEnemies(),
                mActiveAreaObjects.getVisibleItems(),
                mVisibleAreaPosition,
                delta);
        
        mVisibleAreaPosition = MathUtils.clamp(
                mCharacter.getPosition().y - GAME_AREA_HEIGHT * CHARACTER_POSITION_AREA_FRACTION,
                mVisibleAreaPosition, mRiseHeight - VISIBLE_AREA_MINIMUM_DISTANCE_TO_RISE);
    }
    
    private boolean isGameBackgroundVisible() {
        return mVisibleAreaPosition <= mRiseHeight - GAME_BACKGROUND_VANISHING_DISTANCE_FROM_END;
    }
    
    private boolean isEndBackgroundVisible() {
        return mVisibleAreaPosition >= mRiseHeight - END_BACKGROUND_APPEARANCE_DISTANCE_FROM_END;
    }
    
    // TODO: only for debugging
    private RiseSection getCurrentRiseSection() {
        Array<RiseSection> allRiseSections = mRise.getRiseSections();
        
        float characterY = mCharacter.getPosition().y;
        for (RiseSection riseSection : allRiseSections) {
            if (riseSection.getStartY() <= characterY && characterY < riseSection.getEndY()) {
                return riseSection;
            }
        }
        
        return null;
    }
    
    public int getScore() {
        return mRiseScore + mCharacter.getScore();
    }
    
    public int getLives() {
        return mCharacter.getLives();
    }
    
    public boolean isGameOver() {
        return mIsGameOver;
    }
    
    public float getRiseHeight() {
        return mRiseHeight;
    }
    
    public float getVisibleAreaPosition() {
        return mVisibleAreaPosition;
    }
    
    public DebugData getDebugData() {
        return mDebugData;
    }
}
