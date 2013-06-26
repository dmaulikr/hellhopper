package com.turbogerm.hellhopper.game.items;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.dataaccess.ItemData;

final class BlackBoxItem extends ItemBase {
    
    private final Rectangle mCollisionRect;
    
    public BlackBoxItem(ItemData itemData, int startStep, AssetManager assetManager) {
        super(itemData, ResourceNames.ITEM_BLACK_BOX_TEXTURE, startStep, assetManager);
        
        mCollisionRect = new Rectangle();
        
        updatePositionImpl();
    }
    
    @Override
    protected void updatePositionImpl() {
        mSprite.setPosition(mPosition.x, mPosition.y);
        mCollisionRect.set(mPosition.x, mPosition.y, mSize.x, mSize.y);
    }
    
    @Override
    public boolean isCollision(Rectangle rect) {
        return Intersector.overlapRectangles(rect, mCollisionRect);
    }
    
    @Override
    public int getEffect() {
        // TODO Auto-generated method stub
        return -1;
    }
    
    @Override
    public Object getValue() {
        // TODO Auto-generated method stub
        return null;
    }
}
