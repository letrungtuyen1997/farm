package com.ss.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.ss.commons.BitmapFontC;
import com.ss.commons.TextureAtlasC;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.util.GAssetsManager;
import com.ss.core.util.GLayer;
import com.ss.core.util.GScreen;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.effects.SoundEffect;
import com.ss.gameLogic.config.C;

public class loadingScene extends GScreen {
    Group group = new Group();

    @Override
    public void dispose() {

    }

    @Override
    public void init() {
        GStage.addToLayer(GLayer.ui,group);
        TextureAtlasC.loadAtlas();
        BitmapFontC.initBitmapFont();
        loading();
        SoundEffect.initSound();
        C.init();



    }

    float waitTime = 4f;
    @Override
    public void run() {
         System.out.println("run");
         waitTime-= Gdx.graphics.getDeltaTime();
         if(waitTime<=0) {
             if (!GAssetsManager.isFinished()) {
                 GAssetsManager.update();
             } else {
                 TextureAtlasC.initAtlas();

                 this.setScreen(new GameScene());
                 System.out.println("chuyen");
             }
         }

    }
    void loading(){
//        TextureAtlas loadding = GAssetsManager.getTextureAtlas("loadding.atlas");
//        Image load = GUI.createImage(loadding,"loadding");
        Image bg = new Image(new Texture("textureAtlas/bgload.png"));
        bg.setSize(GStage.getWorldWidth(),GStage.getWorldHeight());
        bg.setScale(1,-1);
        bg.setOrigin(Align.center);
        group.addActor(bg);
        Image load = new Image(new Texture("textureAtlas/loadding.png"));
        load.setOrigin(Align.center);
        load.setPosition(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2, Align.center);
        group.addActor(load);
        aniload(load);
    }
    void aniload(Image img){
        img.addAction(Actions.sequence(
                Actions.rotateBy(360,1f),
                GSimpleAction.simpleAction((d,a)->{
                    aniload(img);
                    return true;
                })
        ));
    }
}