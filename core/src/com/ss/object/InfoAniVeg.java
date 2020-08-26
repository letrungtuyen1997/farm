package com.ss.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
import com.ss.commons.Tweens;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.util.GAssetsManager;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.effects.SoundEffect;
import com.ss.gameLogic.StaticObjects.Config;
import com.ss.gameLogic.config.C;

public class InfoAniVeg {
    private TextureAtlas atlas, atlas2;
    private Group group = new Group();
    private String name;
    public Label timeCountDown, Result,LbTarget,textTT,textWatch;
    private Image icClock,LbTangToc, btnWatch;
    private vegetable vegetable;
    private animals animals;
    public InfoAniVeg(TextureAtlas atlas,String kind,int lv,int second,int target,int harvest,int TimeinSecond,vegetable vegetable,animals animals){
        atlas2 = GAssetsManager.getTextureAtlas("info.atlas");
        GStage.addToLayer(GLayer.top,group);
        this.atlas = atlas;
        this.vegetable= vegetable;
        this.animals = animals;
        group.setPosition(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,Align.center);
        darkScreen(group);
        setName(kind);
        render(kind,lv,target,harvest,second,TimeinSecond);


    }
    private void render(String kind , int id,int target,int harvest,int second,int TimeinSecond){
        Image frmInfo = GUI.createImage(atlas2,"frmInfo");
        frmInfo.setPosition(0,0, Align.center);
        group.addActor(frmInfo);
        Label lbname = new Label(name,new Label.LabelStyle(BitmapFontC.robotoVi, Color.BROWN));
        lbname.setAlignment(Align.center);
        lbname.setPosition(0,-frmInfo.getHeight()/2+(lbname.getHeight()*1.5f),Align.center);
        group.addActor(lbname);
        Image btnClose = GUI.createImage(atlas2,"btnClose");
        btnClose.setOrigin(Align.center);
        btnClose.setPosition(frmInfo.getWidth()/2-btnClose.getWidth()/2,-frmInfo.getHeight()/2+btnClose.getHeight()-5,Align.center);
        group.addActor(btnClose);
        eventBtnClose(btnClose);
        ///// icon //////
        Image icon = GUI.createImage(atlas,kind+id);
        icon.setPosition(0,-frmInfo.getHeight()*0.16f,Align.center);
        group.addActor(icon);
        ///// label lv /////
        Label LbLv= new Label(C.lang.level+id+"/10",new Label.LabelStyle(BitmapFontC.robotoVi,Color.BROWN));
        LbLv.setAlignment(Align.center);
        LbLv.setPosition(0,10,Align.center);
        group.addActor(LbLv);
        /////// Label target ///////
        LbTarget = new Label(""+harvest+"/"+target,new Label.LabelStyle(BitmapFontC.robotoVi,Color.GREEN));
        LbTarget.setFontScale(0.8f);
        LbTarget.setOrigin(Align.center);
        LbTarget.setAlignment(Align.center);
        LbTarget.setPosition(0,50,Align.center);
        group.addActor(LbTarget);
        /////// Label info ///////
        Label lbInfo = new Label(C.lang.add+name+" "+C.lang.in+TimeinSecond+" "+C.lang.second,new Label.LabelStyle(BitmapFontC.robotoVi,Color.BROWN));
        lbInfo.setFontScale(0.8f);
        lbInfo.setOrigin(Align.center);
        lbInfo.setAlignment(Align.center);
        lbInfo.setPosition(0,90,Align.center);
        group.addActor(lbInfo);
        /////// time Count Down/////////
        System.out.println("check: "+second);
        int minute = second/60;
        int Second = second%60;
        if(Second>60){
            minute++;
            Second = Second%60;
        }
        timeCountDown = new Label(""+minute+":"+Second,new Label.LabelStyle(BitmapFontC.robotoVi,Color.RED));
        timeCountDown.setFontScale(0.9f);
        timeCountDown.setOrigin(Align.center);
        timeCountDown.setAlignment(Align.center);
        timeCountDown.setPosition(0,135,Align.center);
        group.addActor(timeCountDown);
        ////// icon Clock ///////
        icClock = GUI.createImage(atlas2,"icTime");
        icClock.setPosition(timeCountDown.getX()-icClock.getWidth(),135,Align.center);
        group.addActor(icClock);
        ////// Lb Result /////
        Result = new Label(C.lang.full,new Label.LabelStyle(BitmapFontC.robotoVi,Color.BROWN));
        Result.setAlignment(Align.center);
        Result.setPosition(0,230,Align.center);
        group.addActor(Result);
        ////// Lb Tang toc //////
        LbTangToc = GUI.createImage(atlas2,"lbTangToc");
        LbTangToc.setPosition(0,230,Align.center);
        group.addActor(LbTangToc);
        textTT = new Label(C.lang.maxspeed,new Label.LabelStyle(BitmapFontC.robotoVi,Color.BROWN));
        textTT.setFontScale(0.8f);
        textTT.setOrigin(Align.center);
        textTT.setAlignment(Align.center);
        textTT.setPosition(LbTangToc.getX()+LbTangToc.getWidth()/2,LbTangToc.getY()+LbTangToc.getHeight()/2,Align.center);
        group.addActor(textTT);

        ////// btnTangToc //////
        Group groupbtn = new Group();
        btnWatch = GUI.createImage(atlas2,"btnWatch");
        btnWatch.setOrigin(Align.center);
        btnWatch.setPosition(0,0);
        groupbtn.addActor(btnWatch);
        textWatch = new Label(C.lang.free,new Label.LabelStyle(BitmapFontC.robotoVi,null));
        textWatch.setFontScale(0.7f);
        textWatch.setOrigin(Align.center);
        textWatch.setAlignment(Align.center);
        textWatch.setPosition(btnWatch.getX()+btnWatch.getWidth()*0.6f,btnWatch.getY()+btnWatch.getHeight()*0.4f,Align.center);
        groupbtn.addActor(textWatch);
        groupbtn.setWidth(btnWatch.getWidth());
        groupbtn.setHeight(btnWatch.getHeight());
        groupbtn.setOrigin(Align.center);
        groupbtn.setPosition(0,frmInfo.getHeight()/2-btnWatch.getHeight()*1.2f,Align.center);
        group.addActor(groupbtn);
        setDefault(second);
        ////event btn watch//////
        eventBtnWatch(groupbtn);


    }
    private void setDefault(int s){
        if(s>0){
            LbTangToc.setVisible(true);
            textTT.setVisible(true);
            btnWatch.setVisible(true);
            textWatch.setVisible(true);
            Result.setVisible(false);
            icClock.setVisible(true);
            timeCountDown.setVisible(true);
        }else {
            LbTangToc.setVisible(false);
            textTT.setVisible(false);
            btnWatch.setVisible(false);
            textWatch.setVisible(false);
            Result.setVisible(true);
            icClock.setVisible(false);
            timeCountDown.setVisible(false);
        }
    }

    public void setTimeCountDown(int s, int harvest ,int target){
        int minute = s/60;
        int Second = s%60;
        if(Second>60){
            minute++;
            Second = Second%60;
        }
        timeCountDown.setText(""+minute+":"+Second);
        LbTarget.setText(""+harvest+"/"+target);
        setDefault(s);

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
                            return true;
                        })
                ));
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }
    private void eventBtnWatch(Group btn){
        btn.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                SoundEffect.Play(SoundEffect.pop_up);
                btn.addAction(Actions.sequence(
                        Actions.scaleTo(0.9f,0.9f,0.1f),
                        Actions.scaleTo(1f,1f,0.1f),
                        GSimpleAction.simpleAction((d,a)->{
                            showAds(group);
//                            if(vegetable==null)
//                                animals.setUpTime();
//                            if(animals==null){
//                                vegetable.setUpTime();
//                            }
                            return true;
                        })

                ));
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }
    private void setName(String kind){
        if(kind.equals("lua"))
            name= C.lang.lua;
        if(kind.equals("khoai"))
            name= C.lang.khoai;
        if(kind.equals("cachua"))
            name= C.lang.cachua;
        if(kind.equals("bap"))
            name= C.lang.bap;
        if(kind.equals("leo"))
            name= C.lang.leo;
        if(kind.equals("bi"))
            name= C.lang.bi;
        if(kind.equals("dua"))
            name= C.lang.dua;
        if(kind.equals("cai"))
            name= C.lang.cai;
        if(kind.equals("thom"))
            name= C.lang.thom;
        if(kind.equals("carot"))
            name= C.lang.carot;
        if(kind.equals("cuu"))
            name= C.lang.cuu;
        if(kind.equals("bo"))
            name= C.lang.bo;
        if(kind.equals("ga"))
            name= C.lang.ga;
        if(kind.equals("de"))
            name= C.lang.de;
    }
    void showAds(Group group){
        if(GMain.platform.isVideoRewardReady()) {
            GMain.platform.ShowVideoReward((boolean success) -> {
                if (success) {
                    GMain.platform.ShowFullscreen();
                    group.clear();
                    group.remove();
                    if(vegetable==null)
                        animals.setUpTime();
                    if(animals==null){
                        vegetable.setUpTime();
                    }
                }else {
                    GMain.platform.ShowFullscreen();
                    group.clear();
                    group.remove();
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
                        return true;
                    })
            ));

        }
    }
    private void darkScreen(Group group){
        final GShapeSprite blackOverlay = new GShapeSprite();
        blackOverlay.createRectangle(true, -GStage.getWorldWidth(),-GStage.getWorldHeight()/2, GStage.getWorldWidth()*2, GStage.getWorldHeight()*2);
        blackOverlay.setColor(0,0,0,0.7f);
        group.addActor(blackOverlay);
    }
}
