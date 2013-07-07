package com.turbogerm.hellhopper.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.turbogerm.germlibrary.util.ParticleEffectLoader;
import com.turbogerm.germlibrary.util.ParticleEffectLoader.ParticleEffectParameter;

public final class Resources {
    
    private final AssetManager mAssetManager;
    private final Skin mGuiSkin;
    private final BitmapFont mItemFont;
    
    public Resources() {
        mAssetManager = new AssetManager();
        mAssetManager.setLoader(ParticleEffect.class, new ParticleEffectLoader(new InternalFileHandleResolver()));
        
        TextureParameter textureParameterLinear = new TextureParameter();
        textureParameterLinear.minFilter = TextureFilter.Linear;
        textureParameterLinear.magFilter = TextureFilter.Linear;
        textureParameterLinear.genMipMaps = true;
        
        TextureParameter textureParameterNearest = new TextureParameter();
        textureParameterNearest.minFilter = TextureFilter.Nearest;
        textureParameterNearest.magFilter = TextureFilter.Nearest;
        textureParameterNearest.genMipMaps = false;
        
        mAssetManager.load(ResourceNames.GRAPHICS_GUI_ATLAS, TextureAtlas.class);
        mAssetManager.load(ResourceNames.PLATFORMS_ATLAS, TextureAtlas.class);
        mAssetManager.load(ResourceNames.CHARACTER_ATLAS, TextureAtlas.class);
        mAssetManager.load(ResourceNames.ENEMIES_ATLAS, TextureAtlas.class);
        mAssetManager.load(ResourceNames.ITEMS_ATLAS, TextureAtlas.class);
        mAssetManager.load(ResourceNames.BACKGROUND_ATLAS, TextureAtlas.class);
        
        // PARTICLES
        ParticleEffectParameter particleEffectParameter = new ParticleEffectParameter();
        particleEffectParameter.assetManager = mAssetManager;
        particleEffectParameter.atlasName = ResourceNames.PLATFORMS_ATLAS;
        mAssetManager.load(ResourceNames.PARTICLE_ENGINE_NORMAL, ParticleEffect.class, particleEffectParameter);
        mAssetManager.load(ResourceNames.PARTICLE_ENGINE_REPOSITION, ParticleEffect.class, particleEffectParameter);
        
        // SOUNDS
        mAssetManager.load(ResourceNames.SOUND_JUMP, Sound.class);
        mAssetManager.load(ResourceNames.SOUND_JUMP_BOOST, Sound.class);
        mAssetManager.load(ResourceNames.SOUND_FART, Sound.class);
        mAssetManager.load(ResourceNames.SOUND_COIN, Sound.class);
        mAssetManager.load(ResourceNames.SOUND_ITEM, Sound.class);
        mAssetManager.load(ResourceNames.SOUND_ENEMY, Sound.class);
        mAssetManager.load(ResourceNames.SOUND_SAW, Sound.class);
        mAssetManager.load(ResourceNames.SOUND_FIRE, Sound.class);
        mAssetManager.load(ResourceNames.SOUND_FALL, Sound.class);
        for (int i = 0; i < ResourceNames.SOUND_SHEEP_COUNT; i++) {
            mAssetManager.load(ResourceNames.getSoundSheep(i), Sound.class);
        }
        
        mAssetManager.finishLoading();
        
        // FONTS
        mItemFont = new BitmapFont(Gdx.files.internal(ResourceNames.FONT_ITEM), false);
        mItemFont.setUseIntegerPositions(false);
        mItemFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        mGuiSkin = new Skin(Gdx.files.internal(ResourceNames.GUI_SKIN));
        mGuiSkin.getFont("default-font").getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        mGuiSkin.getFont("small-font").getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        mGuiSkin.getFont("medium-font").getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        mGuiSkin.getFont("large-font").getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        mGuiSkin.getFont("xl-font").getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        mGuiSkin.getFont("xxl-font").getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        mGuiSkin.getFont("xxxl-font").getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
    }
    
    public Skin getGuiSkin() {
        return mGuiSkin;
    }
    
    public AssetManager getAssetManager() {
        return mAssetManager;
    }
    
    public BitmapFont getItemFont() {
        return mItemFont;
    }
    
    public void dispose() {
        mGuiSkin.dispose();
        mAssetManager.dispose();
        mItemFont.dispose();
    }
}
