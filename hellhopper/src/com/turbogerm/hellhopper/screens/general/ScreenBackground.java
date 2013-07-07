package com.turbogerm.hellhopper.screens.general;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.turbogerm.germlibrary.util.ColorPositionPair;
import com.turbogerm.germlibrary.util.SpectrumColorInterpolator;
import com.turbogerm.hellhopper.HellHopper;
import com.turbogerm.hellhopper.resources.ResourceNames;

public final class ScreenBackground {
    
    private static final float SPECTRUM_TRAVERSAL_HALF_PERIOD = 30.0f;
    private static final float SPECTRUM_TRAVERSAL_PERIOD = SPECTRUM_TRAVERSAL_HALF_PERIOD * 2.0f;
    
    private final SpectrumColorInterpolator mSpectrumColorInterpolator;
    
    private final Sprite mBackgroundColorSprite;
    private final Sprite mBackgroundSprite;
    
    private float mInternalTime;
    
    public ScreenBackground(AssetManager assetManager) {
        mSpectrumColorInterpolator = new SpectrumColorInterpolator(getBackgroundColorSpectrum());
        
        TextureAtlas graphicsGuiAtlas = assetManager.get(ResourceNames.GRAPHICS_GUI_ATLAS);
        mBackgroundColorSprite = graphicsGuiAtlas.createSprite(ResourceNames.GUI_GENERAL_WHITE_IMAGE_NAME);
        mBackgroundColorSprite.setBounds(0.0f, 0.0f, HellHopper.VIEWPORT_WIDTH, HellHopper.VIEWPORT_HEIGHT);
        
        TextureAtlas backgroundAtlas = assetManager.get(ResourceNames.BACKGROUND_ATLAS);
        mBackgroundSprite = backgroundAtlas.createSprite(ResourceNames.BACKGROUND_IMAGE_NAME);
    }
    
    public void reset() {
        mInternalTime = 0;
    }
    
    public void update(float delta) {
        mInternalTime += delta;
        mInternalTime %= SPECTRUM_TRAVERSAL_PERIOD;
        float spectrumFraction;
        if (mInternalTime <= SPECTRUM_TRAVERSAL_HALF_PERIOD) {
            spectrumFraction = mInternalTime / SPECTRUM_TRAVERSAL_HALF_PERIOD;
        } else {
            spectrumFraction = (SPECTRUM_TRAVERSAL_PERIOD - mInternalTime) / SPECTRUM_TRAVERSAL_HALF_PERIOD;
        }
        mBackgroundColorSprite.setColor(mSpectrumColorInterpolator.getBackgroundColor(spectrumFraction));
    }
    
    public void render(SpriteBatch batch) {
        mBackgroundColorSprite.draw(batch);
        mBackgroundSprite.draw(batch);
    }
    
    private static Array<ColorPositionPair> getBackgroundColorSpectrum() {
        Array<ColorPositionPair> colorPositionPairs = new Array<ColorPositionPair>(true, 4);
        
        colorPositionPairs.add(new ColorPositionPair(new Color(0.3f, 0.0f, 0.0f, 1.0f), 0.0f));
        colorPositionPairs.add(new ColorPositionPair(new Color(0.6f, 0.0f, 0.0f, 1.0f), 5.0f));
        colorPositionPairs.add(new ColorPositionPair(new Color(0.91f, 0.6f, 0.09f, 1.0f), 10.0f));
        colorPositionPairs.add(new ColorPositionPair(new Color(0.1f, 1.0f, 0.00f, 1.0f), 15.0f));
        
        return colorPositionPairs;
    }
}
