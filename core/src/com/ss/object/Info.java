package com.ss.object;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
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

public class Info {
    private TextureAtlas atlas;
    private BitmapFont font;
    private Group group = new Group();
    private Label lv, lbMoney;
    private Image frmAvt,frmExp,frmScl,frmMoney,btnAddMore;
    private int click=0;
    public Info(TextureAtlas atlas){
        this.atlas = atlas;
        GStage.addToLayer(GLayer.top,group);
        render();
        eventSetting();
        AddMoney();
        tutorial();

    }
    private void render(){
        frmAvt = GUI.createImage(atlas,"frmAvt");
        frmAvt.setPosition(frmAvt.getWidth()/2+10,frmAvt.getHeight()-50, Align.center);
        group.addActor(frmAvt);
        ///// frm exp ////
        frmExp = GUI.createImage(atlas,"frmExp");
        frmExp.setPosition(frmAvt.getX()+frmAvt.getWidth()-frmExp.getWidth(),frmAvt.getY()+40,Align.center);
        group.addActor(frmExp);
        /////frm Scale ////
        frmScl = GUI.createImage(atlas,"sclExp");
        frmScl.setOrigin(Align.top);
        frmScl.setPosition(frmExp.getX()+frmExp.getWidth()/2,frmExp.getY()+frmExp.getHeight()/2,Align.center);
        group.addActor(frmScl);
        frmAvt.setZIndex(frmScl.getZIndex()+1);
        //// label lv //////
        lv = new Label(""+ Config.Level,new Label.LabelStyle(BitmapFontC.robotoVi,null));
        lv.setFontScale(0.9f);
        lv.setOrigin(Align.center);
        lv.setAlignment(Align.center);
        lv.setPosition(frmExp.getX()+frmExp.getWidth()/2,frmExp.getY()+frmExp.getHeight()+lv.getHeight()/2+5,Align.center);
        group.addActor(lv);
        /////// frm money//////
        frmMoney = GUI.createImage(atlas,"frmMoney");
        frmMoney.setPosition(GStage.getWorldWidth()/2+frmMoney.getWidth()/2,frmAvt.getY(),Align.center);
        group.addActor(frmMoney);
        ////// label money /////
        lbMoney = new Label(Config.compressCoin(Config.Money,1),new Label.LabelStyle(BitmapFontC.robotoVi,null));
        lbMoney.setFontScale(0.9f);
        lbMoney.setOrigin(Align.center);
        lbMoney.setAlignment(Align.center);
        lbMoney.setPosition(frmMoney.getX()+frmMoney.getWidth()/2,frmMoney.getY()+frmMoney.getHeight()/2,Align.center);
        group.addActor(lbMoney);
        ///// btn add more money ////
        btnAddMore = GUI.createImage(atlas,"btnAddMore");
        btnAddMore.setOrigin(Align.center);
        btnAddMore.setPosition(frmMoney.getX()+frmMoney.getWidth(),frmMoney.getY()+frmMoney.getHeight()/2,Align.center);
        group.addActor(btnAddMore);

    }
    public void updateMonney(long money){
        lbMoney.setText(Config.compressCoin(money,2));
    }
    public void setScale(float percent){
        frmScl.setScale(1,percent/100);

    }
    private void eventSetting(){
        frmAvt.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                new setting(TextureAtlasC.atlasInfo,frmAvt);
            }
        });
    }
    private void AddMoney(){
        btnAddMore.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                btnAddMore.setTouchable(Touchable.disabled);
                SoundEffect.Play(SoundEffect.tack);
                new showAds(btnAddMore);
            }
        });
    }
    public void updateLvSc(){
        frmScl.setScale(1,(float)Config.momentExp/Config.nextExp);
        lv.setText(Config.Level);
    }
    private void tutorial(){
        Image btnTuto = GUI.createImage(TextureAtlasC.atlasInfo,"btnTutorial");
        btnTuto.setOrigin(Align.center);
        btnTuto.setPosition(frmAvt.getX(),frmAvt.getY()+frmAvt.getHeight());
        group.addActor(btnTuto);
        eventbtnTuto(btnTuto);
        aniBtn(btnTuto);
    }
    private void eventbtnTuto(Image btn){
        btn.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                SoundEffect.Play(SoundEffect.click);
                Tutorial();

            }
        });
    }
    private void Tutorial(){

        Group gr = new Group();
        GStage.addToLayer(GLayer.top,gr);
        final GShapeSprite blackOverlay = new GShapeSprite();
        blackOverlay.createRectangle(true, -GStage.getWorldWidth(),-GStage.getWorldHeight()/2, GStage.getWorldWidth()*2, GStage.getWorldHeight()*2);
        blackOverlay.setColor(0,0,0,0.5f);
        gr.addActor(blackOverlay);
        gr.setScaleX(0);
        gr.setOrigin(Align.center);
        gr.setPosition(GStage.getWorldWidth()/2, GStage.getWorldHeight()/2,Align.center);
        gr.addAction(Actions.scaleTo(1,1,0.3f, Interpolation.swingOut));
        String t1="Tuto1vn";
        String t2="Tuto2vn";
        if(C.lang.idcontry.equals("en")==true){
            t1="Tuto1en";
            t2="Tuto2en";
        }
        Image tuto1 = GUI.createImage(TextureAtlasC.atlasInfo,t1);
        tuto1.setPosition(0,0,Align.center);
        gr.addActor(tuto1);
        Image tuto2 = GUI.createImage(TextureAtlasC.atlasInfo,t2);
        tuto2.setPosition(0,0,Align.center);
        gr.addActor(tuto2);
        tuto2.setVisible(false);
        Image btnNext = GUI.createImage(TextureAtlasC.atlasInfo,"skip");
        btnNext.setSize(btnNext.getWidth()*2f,btnNext.getHeight()*2f);
        btnNext.setOrigin(Align.center);
        btnNext.setPosition(GStage.getWorldWidth()/2-btnNext.getWidth()*0.7f,0,Align.center);
        gr.addActor(btnNext);
        aniBtn(btnNext);
        btnNext.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                SoundEffect.Play(SoundEffect.click);
                if(click==0){
                    click++;
                    tuto2.setVisible(true);
                }else {
                    click=0;
                    gr.clear();
                    gr.remove();
                }
            }
        });

    }
    private void aniBtn(Image btn){
        btn.addAction(Actions.sequence(
                Actions.scaleTo(0.8f,0.8f,0.2f),
                Actions.scaleTo(1f,1f,0.2f),
                Actions.delay(2f),
                GSimpleAction.simpleAction((d,a)->{
                    aniBtn(btn);
                    return true;
                })
        ));
    }
}
