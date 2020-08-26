package com.ss.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.ss.GMain;
import com.ss.commons.BitmapFontC;
import com.ss.commons.TextureAtlasC;
import com.ss.commons.Tweens;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.effects.SoundEffect;
import com.ss.gameLogic.StaticObjects.Config;
import com.ss.gameLogic.config.C;
import com.ss.scenes.GameScene;

public class tutorial {
    private Group group= new Group();
    private Group groupnotice= new Group();
    private Group groupnotice2= new Group();
    private  Group groupBtn = new Group();
    private final GShapeSprite blackover = new GShapeSprite();
    private final GShapeSprite blackheader = new GShapeSprite();
    private final GShapeSprite blackfotter = new GShapeSprite();
    private final GShapeSprite blackleft = new GShapeSprite();
    private Label text;
    private Fotter fotter;
    private Image hand,hand2;
    private boolean runtime= false;
    private GameScene gameScene;

    public tutorial(Fotter fotter, GameScene gameScene){
        this.fotter = fotter;
        this.gameScene = gameScene;
        GStage.addToLayer(GLayer.top,group);
        group.addActor(groupnotice);
        ////// black over//////
        blackover.createRectangle(true,0,0, GStage.getWorldWidth(), GStage.getWorldHeight());
//        blackover.createPolygon(true,new float[]{0,0,0,GStage.getWorldHeight(),fotter.arrButton.get(0).getWidth()*0.5f,GStage.getWorldHeight()-fotter.frm.getHeight(),fotter.arrButton.get(0).getWidth()*2,GStage.getWorldHeight()-fotter.frm.getHeight(),fotter.arrButton.get(0).getWidth()*2,GStage.getWorldHeight(),GStage.getWorldWidth(),GStage.getWorldHeight(),GStage.getWorldWidth(),0});
        blackover.setColor(0,0,0,0.1f);
        group.addActor(blackover);
        ////// black header//////
        blackheader.createRectangle(true,0,0, GStage.getWorldWidth(), GStage.getWorldHeight());
        blackheader.setColor(0,0,0,0.8f);
        group.addActor(blackheader);
        blackheader.setVisible(false);
        /////// black fotter //////
        blackfotter.createRectangle(true,0,0, GStage.getWorldWidth(), GStage.getWorldHeight());
        blackfotter.setColor(0,0,0,0.8f);
        group.addActor(blackfotter);
        blackfotter.setVisible(false);
        /////// black left //////
        blackleft.createRectangle(true,0,0, GStage.getWorldWidth(), GStage.getWorldHeight());
        blackleft.setColor(0,0,0,0.8f);
        group.addActor(blackleft);
        blackleft.setVisible(false);
        Tweens.setTimeout(group,1f,()->{
            gameScene.scroll.fling(150,-3,-3);

        });
        Tweens.setTimeout(group,4f,()->{
            gameScene.scroll.fling(150,3,3);

        });
        Tweens.setTimeout(group,8f,()->{
            gameScene.scroll.fling(0,0,0);
            gameScene.scroll.layout();
            gameScene.scroll.setScrollPercentX(0.5f);
            gameScene.scroll.setScrollPercentY(0.5f);
            gameScene.scroll.updateVisualScroll();
            gameScene.scroll.setFlingTime(0);
            blackover.setColor(0,0,0,0.8f);
            notice();
        });
//        blackover.setColor(0,0,0,0.8f);
//        notice();
    }
    void notice(){
        GStage.addToLayer(GLayer.top,groupnotice);
        GStage.addToLayer(GLayer.top,groupnotice2);
        Image frm = GUI.createImage(TextureAtlasC.atlasInfo,"notice");
        frm.setPosition(0,0);
        groupnotice.addActor(frm);
        text = new Label(C.lang.lbstep1,new Label.LabelStyle(BitmapFontC.RobotoViGrey, Color.GREEN));
        text.setFontScale(1.2f);
        text.setOrigin(Align.center);
        text.setPosition(frm.getX()+30,frm.getY()+frm.getHeight()/3);
        groupnotice.addActor(text);

        Image btn =GUI.createImage(TextureAtlasC.atlasInfo,"btnNhan");
        btn.setPosition(0,0);
        groupBtn.addActor(btn);
        Label lbbtn = new Label(C.lang.lbcontinus,new Label.LabelStyle(BitmapFontC.robotoVi,null));
        lbbtn.setPosition(btn.getX()+btn.getWidth()/2,btn.getY()+btn.getHeight()/2,Align.center);
        groupBtn.addActor(lbbtn);
        groupBtn.setWidth(btn.getWidth());
        groupBtn.setHeight(btn.getHeight());
        groupBtn.setOrigin(Align.center);
        groupBtn.setPosition(frm.getX()+frm.getWidth()-btn.getWidth(),frm.getY()+frm.getHeight()-btn.getHeight()/2,Align.center);
        groupnotice.addActor(groupBtn);
        groupBtn.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                groupBtn.setVisible(false);
                SoundEffect.Play(SoundEffect.click);
                if(Config.Stepindex==0){
                    step1();
                    Config.Stepindex=1;
                }else if(Config.Stepindex==2){
                    step2();
                }else if(Config.Stepindex==3){
                    step3();
                }else if(Config.Stepindex==4){
                    step4();
                }else if(Config.Stepindex==8){
                    step8();
                }
            }
        });
        /////// set position group///
        groupnotice.setSize(frm.getWidth(),frm.getHeight());
        groupnotice.setOrigin(Align.center);
        groupnotice.setPosition(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2-frm.getHeight()/2,Align.center);

    }
    void step1(){
        gameScene.scroll.setScrollingDisabled(true, true);
        groupnotice.addAction(Actions.moveBy(0,-groupnotice.getHeight()/2,0.2f));
        text.setText(C.lang.lbstep2);
        blackover.createRectangle(true,0,0, GStage.getWorldWidth(), GStage.getWorldHeight()/2-groupnotice.getHeight());
//        blackover.setVisible(false);
//        blackheader.createRectangle(true,0,0, GStage.getWorldWidth(), GStage.getWorldHeight()-fotter.frm.getHeight());
        blackheader.createRectangle(  true,0,GStage.getWorldHeight()/2+100, GStage.getWorldWidth(), GStage.getWorldHeight()/2-fotter.frm.getHeight()-100);

        blackheader.setVisible(true);
        blackfotter.setPosition(fotter.arrButton.get(0).getWidth()*2,GStage.getWorldHeight()-fotter.frm.getHeight());
        blackfotter.setVisible(true);
        blackleft.setPosition(-GStage.getWorldWidth()+fotter.arrButton.get(0).getWidth()*0.5f,GStage.getWorldHeight()-fotter.frm.getHeight());
        blackleft.setVisible(true);
        hand();
        group.addAction(
                GSimpleAction.simpleAction((d,a)->{
                    if(Config.Stepindex==2){
                        groupBtn.setVisible(true);
                        hand.setPosition(groupBtn.getX(),groupBtn.getY()-groupBtn.getHeight());
                        runtime=true;
                    }
                    return runtime;
                })
        );

    }
    void hand(){
        hand = GUI.createImage(TextureAtlasC.atlasInfo,"hand");
        hand.setPosition(fotter.arrButton.get(0).getWidth(),GStage.getWorldHeight()-hand.getHeight()*5);
        hand.setRotation(200);
        hand.setOrigin(Align.center);
        groupnotice.addActor(hand);
        aniHand(hand);
    }
    void aniHand(Image btn){
        btn.addAction(Actions.sequence(
                Actions.moveBy(0,10,0.5f),
                Actions.moveBy(0,-10,0.5f),
                GSimpleAction.simpleAction((d,a)->{
                    aniHand(btn);
                    return true;
                })
        ));
    }
    void step2(){
        blackover.setVisible(false);
        runtime=false;
        blackleft.setVisible(false);
        blackheader.createRectangle(true,0,0, GStage.getWorldWidth(), GStage.getWorldHeight()/2-groupnotice.getHeight()/2);
        blackfotter.setPosition(0,GStage.getWorldHeight()/2+100);
        groupnotice.setVisible(false);
        Image frm = GUI.createImage(TextureAtlasC.atlasInfo,"frm");
        frm.setPosition(0,0);
        groupnotice2.setSize(frm.getWidth(),frm.getHeight());
        groupnotice2.setPosition(GStage.getWorldWidth()/2,groupnotice.getY()+groupnotice.getHeight()/2,Align.center);
        groupnotice2.addActor(frm);
        for (int i=0;i<2;i++){
            Image land = GUI.createImage(TextureAtlasC.atlas,"land");
            land.setPosition(frm.getX()+frm.getWidth()/2-land.getWidth()/2+(land.getWidth()+20)*i,frm.getY()+frm.getHeight()/2,Align.center);
            groupnotice2.addActor(land);
            Image lua = GUI.createImage(TextureAtlasC.atlas,"lua1");
            lua.setPosition(land.getX()+land.getWidth()/2,land.getY()+land.getHeight()/2,Align.center);
            groupnotice2.addActor(lua);
        }
        hand2 = GUI.createImage(TextureAtlasC.atlasInfo,"hand");
        hand2.setPosition(frm.getX()+hand2.getWidth(),frm.getY()-hand2.getHeight());
        hand2.setRotation(200);
        hand2.setOrigin(Align.center);
        groupnotice2.addActor(hand2);
        anihand2(hand2);
        groupnotice.setVisible(true);
        groupnotice.addAction(Actions.moveBy(0,groupnotice.getHeight()*1.7f,0.2f));
        text.setText(C.lang.lbstep3);
        hand.setVisible(false);
        group.addAction(
                GSimpleAction.simpleAction((d,a)->{
                    if(Config.Stepindex==3){
                        groupBtn.setVisible(true);
                        hand.setVisible(true);
                        hand.setPosition(groupBtn.getX(),groupBtn.getY()-groupBtn.getHeight());
                        runtime=true;
                    }
                    return runtime;
                })
        );
    }
    void anihand2(Image btn ){
        btn.addAction(Actions.sequence(
                Actions.moveBy(200,0,1f),
                Actions.moveBy(-200,0,0),
                GSimpleAction.simpleAction((d,a)->{
                    anihand2(btn);
                    return true;
                })
        ));

    }
    void step3(){
        runtime=false;
        blackheader.createRectangle(true,0,0, GStage.getWorldWidth(), GStage.getWorldHeight()-fotter.frm.getHeight());
        blackfotter.setPosition(-150,GStage.getWorldHeight()-fotter.frm.getHeight());
        groupnotice2.setVisible(false);
        hand.setVisible(true);
        hand.setPosition(groupBtn.getX()+groupBtn.getWidth()/2,groupBtn.getY()+groupBtn.getHeight()*1.5f);
        text.setText(C.lang.lbstep4);
        group.addAction(
                GSimpleAction.simpleAction((d,a)->{
                    if(Config.Stepindex==4){
                        groupBtn.setVisible(true);
                        hand.setVisible(true);
                        hand.setPosition(groupBtn.getX(),groupBtn.getY()-groupBtn.getHeight());
                        runtime=true;
                    }
                    return runtime;
                })
        );
    }
    void step4(){

        runtime=false;
        gameScene.scroll.setScrollingDisabled(false, false);
        gameScene.scroll.layout();
        gameScene.scroll.setScrollPercentX(0.2f);
        gameScene.scroll.setScrollPercentY(0.5f);
        gameScene.scroll.updateVisualScroll();
        gameScene.scroll.setFlingTime(10);
        gameScene.scroll.fling(500,0,-5);
        blackheader.createRectangle(true,-GStage.getWorldWidth()/2,0, GStage.getWorldWidth()-50, gameScene.house.getHeight()*0.75f);
        blackfotter.setPosition(0,gameScene.house.getHeight()*0.75f);
        groupnotice.addAction(Actions.moveBy(0,-200,0.2f));
        text.setText(C.lang.lbstep5);
        hand.setPosition(GStage.getWorldWidth()-200,-groupnotice.getHeight());
        group.addAction(
                GSimpleAction.simpleAction((d,a)->{
                    if(Config.Stepindex==5){
                        runtime=true;
                        step5();
                    }
                    return runtime;
                })
        );
    }
    void step5(){
        runtime=false;
        blackfotter.setVisible(false);
        blackheader.setVisible(false);
        hand.setPosition(GStage.getWorldWidth()/2+100,GStage.getWorldHeight()/2+200);
        groupnotice.addAction(Actions.moveTo(0,0,0.2f));
        text.setText(C.lang.lbstep6);
        group.addAction(
                GSimpleAction.simpleAction((d,a)->{
                    if(Config.Stepindex==6){
                        runtime=true;
                        step6();
                    }
                    return runtime;
                })
        );

    }
    void step6(){
        runtime=false;
        hand.setPosition(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2);
        group.addAction(
                GSimpleAction.simpleAction((d,a)->{
                    if(Config.Stepindex==7){
                        step7();
                        runtime=true;
                    }
                    return runtime;
                })
        );
    }
    void step7(){
        blackover.createRectangle(true,0,0, GStage.getWorldWidth(), GStage.getWorldHeight());
        Config.Stepindex=8;
        gameScene.scroll.fling(0,0,0);
        gameScene.scroll.layout();
        gameScene.scroll.setScrollPercentX(0.5f);
        gameScene.scroll.setScrollPercentY(0.5f);
        gameScene.scroll.updateVisualScroll();
        gameScene.scroll.setFlingTime(0);
        blackover.setVisible(true);
        groupnotice.addAction(Actions.moveTo(0,GStage.getWorldHeight()/2-groupnotice.getHeight()/2,0.5f));
        hand.setPosition(groupBtn.getX(),groupBtn.getY()-groupBtn.getHeight());
        text.setText(C.lang.lbstep7);
        text.setPosition(50,50);
        groupBtn.setVisible(true);


    }
    void step8(){
        group.remove();
        group.clear();
        groupnotice.remove();
        groupnotice.clear();
        groupnotice2.remove();
        groupnotice2.clear();
//        GMain.prefs.putBoolean("checkFirst",true);
//        GMain.prefs.flush();
    }

}
