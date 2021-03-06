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
package com.turbogerm.helljump.dataaccess;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import com.turbogerm.helljump.game.GameAreaUtils;

public final class EnemyData {
    
    public static final String SAW_TYPE = "saw";
    public static final String IMP_TYPE = "imp";
    public static final String LOCO_TYPE = "loco";
    public static final String KNIGHT_TYPE = "knight";
    public static final String EVIL_TWIN_TYPE = "eviltwin";
    public static final String COOL_CLERK_TYPE = "coolclerk";
    
    public static final String SPEED_PROPERTY = "speed";
    public static final String TRAVEL_PERIOD_PROPERTY = "travelperiod";
    public static final String RANGE_PROPERTY = "range";
    public static final String RANGES_PROPERTY = "ranges";
    public static final String INITIAL_OFFSET_PROPERTY = "initialoffset";
    
    private final String mType;
    private final float mStep;
    private final float mOffset;
    private final ObjectMap<String, String> mProperties;
    
    public EnemyData(String type, float step, float offset, ObjectMap<String, String> properties) {
        mType = type;
        mStep = step;
        mOffset = offset;
        mProperties = properties;
    }
    
    public String getType() {
        return mType;
    }
    
    public float getStep() {
        return mStep;
    }
    
    public float getOffset() {
        return mOffset;
    }
    
    public String getProperty(String name) {
        return mProperties.get(name);
    }
    
    public Vector2 getPosition(int startStep) {
        return GameAreaUtils.getPosition(startStep, mStep, mOffset);
    }
}
