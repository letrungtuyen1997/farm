package com.ss.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.ss.GMain;
import com.ss.commons.BitmapFontC;
import com.ss.commons.TextureAtlasC;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.effects.SoundEffect;
import com.ss.gameLogic.StaticObjects.Config;
import com.ss.gameLogic.config.C;

public class showAds {
    private Group group = new Group();
    private Image btnAdd;
    showAds(Image btn){
        this.btnAdd = btn;
        GStage.addToLayer(GLayer.top,group);
        final GShapeSprite blackOverlay = new GShapeSprite();
        blackOverlay.createRectangle(true, -GStage.getWorldWidth(),-GStage.getWorldHeight()/2, GStage.getWorldWidth()*2, GStage.getWorldHeight()*2);
        blackOverlay.setColor(0,0,0,0.5f);
        group.addActor(blackOverlay);
        group.setScaleX(0);
        group.setOrigin(Align.center);
        group.setPosition(GStage.getWorldWidth()/2, GStage.getWorldHeight()/2,Align.center);
        group.addAction(Actions.scaleTo(1,1,0.3f, Interpolation.swingOut));
        Image frmAds = GUI.createImage(TextureAtlasC.atlasInfo,"frmAds");
        frmAds.setPosition(0,0, Align.center);
        group.addActor(frmAds);
        Label text = new Label(C.lang.getcoin,new Label.LabelStyle(BitmapFontC.robotoVi,Color.BROWN));
        text.setPosition(0,-frmAds.getHeight()/2+60,Align.center);
        group.addActor(text);
        Label text2 = new Label(C.lang.watchads,new Label.LabelStyle(BitmapFontC.robotoVi,Color.BROWN));
        text2.setPosition(0,50,Align.center);
        group.addActor(text2);
        Image btnWatch = GUI.createImage(TextureAtlasC.atlasInfo,"btnShowAds");
        btnWatch.setPosition(0,frmAds.getHeight()/2-btnWatch.getHeight()*2,Align.center);
        group.addActor(btnWatch);
        btnWatch.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                showAds(group);
            }
        });
        Image btnClose = GUI.createImage(TextureAtlasC.atlasInfo,"btnClose");
        btnClose.setOrigin(Align.center);
        btnClose.setPosition(frmAds.getWidth()/2-btnClose.getWidth()/2,-frmAds.getHeight()/2+btnClose.getHeight()-5,Align.center);
        group.addActor(btnClose);
        eventBtnClose(btnClose);

    }
    private void eventBtnClose(Image btn){
        btn.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                SoundEffect.Play(SoundEffect.tack);
                btn.addAction(Actions.sequence(
                        Actions.scaleTo(0.9f,0.9f,0.1f),
                        Actions.scaleTo(1f,1f,0.1f),
                        GSimpleAction.simpleAction((d,a)->{
                            group.clear();
                            group.remove();
                            btnAdd.setTouchable(Touchable.enabled);
                            return true;
                        })
                ));
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }
    void showAds(Group group){
        if(GMain.platform.isVideoRewardReady()) {
            GMain.platform.ShowVideoReward((boolean success) -> {
                if (success) {
                    GMain.platform.ShowFullscreen();
                    group.clear();
                    group.remove();
                    btnAdd.setTouchable(Touchable.enabled);
                    Config.Money+=5000;

                }else {
                    GMain.platform.ShowFullscreen();
                    group.clear();
                    group.remove();
                    btnAdd.setTouchable(Touchable.enabled);
                }
            });
        }else {
            Label notice = new Label(C.lang.checkconnect,new Label.LabelStyle(BitmapFontC.robotoVi, Color.RED));
            notice.setPosition(0,0,Align.center);
            group.addActor(notice);
            notice.addAction(Actions.sequence(
                    Actions.moveBy(0,-50,0.5f),
                    GSimpleAction.simpleAction((d, a)->{
                        notice.clear();
                        notice.remove();
                        btnAdd.setTouchable(Touchable.enabled);
                        return true;
                    })
            ));

        }
    }
}
