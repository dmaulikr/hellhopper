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
package com.turbogerm.helljump.game.character.graphics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.helljump.resources.ResourceNames;

public final class CharacterMouthGraphicsSmile extends CharacterGraphicsBase {
    
    private static final float OFFSET_X = 0.3f;
    private static final float OFFSET_Y = 0.46f;
    private static final float WIDTH = 0.4f;
    private static final float HEIGHT = 0.1f;
    
    private static final Color DEFAULT_COLOR;
    
    private final Sprite mSprite;
    
    static {
        DEFAULT_COLOR = new Color(1.0f, 0.5f, 0.0f, 1.0f);
    }
    
    public CharacterMouthGraphicsSmile(AssetManager assetManager) {
        
        TextureAtlas atlas = assetManager.get(ResourceNames.CHARACTER_ATLAS);
        mSprite = atlas.createSprite(ResourceNames.CHARACTER_MOUTH_SMILE_IMAGE_NAME);
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
