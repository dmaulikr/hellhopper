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
package com.turbogerm.helljump.game.platforms.movement;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.helljump.dataaccess.PlatformMovementData;
import com.turbogerm.helljump.game.platforms.features.PlatformModifier;
import com.turbogerm.helljump.resources.ResourceNames;

public final class HorizontalPlatformMovement extends PlatformMovementBase {
    
    private final float mRange;
    private final float mSpeed;
    
    private final float mLeftLimit;
    private final float mRightLimit;
    private boolean mIsRightMovement;
    
    public HorizontalPlatformMovement(PlatformMovementData movementData, Vector2 initialPosition,
            AssetManager assetManager) {
        super(initialPosition, ResourceNames.PLATFORM_ENGINE_NORMAL_IMAGE_NAME,
                ResourceNames.PARTICLE_ENGINE_NORMAL, assetManager);
        
        mRange = Float.parseFloat(movementData.getProperty(PlatformMovementData.RANGE_PROPERTY));
        mSpeed = Float.parseFloat(movementData.getProperty(PlatformMovementData.SPEED_PROPERTY));
        
        mLeftLimit = initialPosition.x;
        mRightLimit = initialPosition.x + mRange;
        
        float initialOffset = Float.parseFloat(movementData.getProperty(PlatformMovementData.INITIAL_OFFSET_PROPERTY));
        if (initialOffset <= mRange) {
            changePosition(initialOffset);
            mIsRightMovement = true;
        } else {
            changePosition(mRange - initialOffset % mRange);
            mIsRightMovement = false;
        }
    }
    
    @Override
    protected void updateImpl(float delta) {
        float travelled = mSpeed * delta;
        if (!mIsRightMovement) {
            travelled = -travelled;
        }
        
        changePosition(travelled);
    }
    
    @Override
    public void applyModifier(PlatformModifier modifier) {
        modifier.spriteColor.set(0.5f, 0.5f, 0.0f, 1.0f);
    }
    
    private void changePosition(float change) {
        mPosition.x += change;
        if (mPosition.x <= mLeftLimit) {
            mIsRightMovement = true;
        } else if (mPosition.x >= mRightLimit) {
            mIsRightMovement = false;
        }
        
        mPosition.x = MathUtils.clamp(mPosition.x, mLeftLimit, mRightLimit);
    }
    
    @Override
    public boolean hasVerticalMovement() {
        return false;
    }
}
