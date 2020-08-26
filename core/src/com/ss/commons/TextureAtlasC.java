package com.ss.commons;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.ss.core.util.GAssetsManager;

public class TextureAtlasC {
  public static TextureAtlas storeHouse;
  public static TextureAtlas atlasInfo;
  public static TextureAtlas atlasFotter;
  public static TextureAtlas atlas;

  public static void initAtlas(){
    storeHouse = GAssetsManager.getTextureAtlas("storeHouse.atlas");
    atlasInfo = GAssetsManager.getTextureAtlas("info.atlas");
    atlasFotter = GAssetsManager.getTextureAtlas("footer.atlas");
    atlas = GAssetsManager.getTextureAtlas("uiGame.atlas");
  }

  public static void loadAtlas(){

    GAssetsManager.loadTextureAtlas("storeHouse.atlas");
    GAssetsManager.loadTextureAtlas("info.atlas");
    GAssetsManager.loadTextureAtlas("footer.atlas");
    GAssetsManager.loadTextureAtlas("uiGame.atlas");
  }
}
