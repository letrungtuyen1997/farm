package com.ss.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.util.GAssetsManager;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.effects.SoundEffect;
import com.ss.gameLogic.StaticObjects.Config;
import com.ss.gameLogic.config.C;
import com.ss.scenes.GameScene;

public class Fotter {
    private TextureAtlas atlas;
    public Group group = new Group();
    public Group groupScrollVegtbl = new Group();
    private Group groupScrollAni = new Group();
    public Array<Image> arrButton = new Array<>();
    private Array<Image> arrButtonAni = new Array<>();
    private ScrollPane Scroll;
    final GShapeSprite blackOverlay = new GShapeSprite();
    private boolean isdrag = false;
    private int id=0;
    private String kind = "";
    private int idAni=0;
    private String kindAni = "";
    private GameScene gameScene;
    private int priceVegtbl = 0;
    private int priceAni = 100;
    private BitmapFont font;
    private Array<Integer> arrPriceVegtbl = new Array<>();
    private Array<Integer> arrPriceAni = new Array<>();
    public Array<Image> arrIcon = new Array<>();
    private Array<Image> arrIconAni = new Array<>();
    private Array<Image> arrFrmLock = new Array<>();
    private Array<Image> arrFrmLockAni = new Array<>();


    Image frm;
    public Fotter(GameScene gameScene,BitmapFont font){
        this.font = font;
        this.gameScene = gameScene;
        GStage.addToLayer(GLayer.top,group);
        initTexture();
        //////// windown and hand /////
        Image windown = GUI.createImage(atlas,"windown");
        windown.setPosition(GStage.getWorldWidth()/2-windown.getWidth()/2,0,Align.center);
        group.addActor(windown);
        Image hand = GUI.createImage(atlas,"hand");
        hand.setOrigin(Align.center);
        hand.setPosition(windown.getX()+windown.getWidth()/2,windown.getY()+windown.getHeight()/2,Align.center);
        group.addActor(hand);
        render();
        group.setPosition(GStage.getWorldWidth()/2,GStage.getWorldHeight()-frm.getHeight()/2);

        //// event btn ///////
        eventBtn();
        eventBtnAni();
        eventHarvest(hand);

    }

    private void render(){
        frm = GUI.createImage(atlas,"frmFotter");
        frm.setPosition(-50,0, Align.center);
        group.addActor(frm);
        ////// GROUP VEGTABLE/////////
        groupScrollVegtbl.setWidth(frm.getWidth()-50);
        groupScrollVegtbl.setHeight(frm.getHeight()-20);
        groupScrollVegtbl.setPosition(frm.getX()+frm.getWidth()/2,frm.getY()+frm.getHeight()/2,Align.center);
        ////// group animals/////
        groupScrollAni.setWidth(frm.getWidth()-50);
        groupScrollAni.setHeight(frm.getHeight()-20);
        groupScrollAni.setPosition(frm.getX()+frm.getWidth()/2,frm.getY()+frm.getHeight()/2,Align.center);

        ////// table vegtable /////////
        tableVegtlb();
        ////// table animals /////////
        tableAni();
        groupScrollAni.setVisible(false);
        //////// btn Vegatable ON //////
        Image btnVegtblOff = GUI.createImage(atlas,"btnVegtblOff");
        btnVegtblOff.setPosition(frm.getX()+btnVegtblOff.getWidth()/2+20,frm.getY()-btnVegtblOff.getHeight()/2,Align.center);
        group.addActor(btnVegtblOff);
        //////// btn Vegatable OFF //////
        Image btnVegtblOn = GUI.createImage(atlas,"btnVegtblOn");
        btnVegtblOn.setPosition(btnVegtblOff.getX()+btnVegtblOn.getWidth()/2,btnVegtblOff.getY()+btnVegtblOn.getHeight()/2,Align.center);
        group.addActor(btnVegtblOn);
        ////// btn Animals ON////////
        Image btnAniOff = GUI.createImage(atlas,"btnAniOff");
        btnAniOff.setPosition(btnVegtblOn.getX()+btnVegtblOn.getWidth()*3/2+5,btnVegtblOn.getY()+btnVegtblOn.getHeight()/2,Align.center);
        group.addActor(btnAniOff);
        ////// btn Animals OFF////////
        Image btnAniOn = GUI.createImage(atlas,"btnAniOn");
        btnAniOn.setPosition(btnAniOff.getX()+btnAniOn.getWidth()/2,btnAniOff.getY()+btnAniOn.getHeight()/2,Align.center);
        group.addActor(btnAniOn);
        btnAniOn.setVisible(false);
        //// event btn Vegtable ////
        eventBtnToggle(btnVegtblOn,btnVegtblOff,btnAniOn,1);
        ////// event btn Ani /////
        eventBtnToggle(btnAniOn,btnAniOff,btnVegtblOn,2);

    }
    private void eventBtnToggle(Image btnOn,Image btnOff,Image btnToggle,int mode){
        btnOn.setOrigin(Align.center);
        btnOff.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                btnOn.setVisible(true);
                btnToggle.setVisible(false);
                if(mode==1){
                    groupScrollAni.setVisible(false);
                    groupScrollVegtbl.setVisible(true) ;
                }else {
                    groupScrollAni.setVisible(true);
                    groupScrollVegtbl.setVisible(false) ;
                }
                btnOn.addAction(Actions.sequence(
                        Actions.scaleTo(1.1f,1.1f,0.1f),
                        Actions.scaleTo(1f,1f,0.1f)
                        ));
                super.touchUp(event, x, y, pointer, button);

            }
        });

    }

    private void tableVegtlb(){
        ///////// scroll table  vegetable////
        Table table = new Table();
        Table tableScroll = new Table();
        for(int i=0;i<10;i++){
            Group grT = new Group();
            Image tile = GUI.createImage(atlas,"tileFotter");
            grT.addActor(tile);
            grT.setSize(tile.getWidth(),tile.getHeight());
            tableScroll.add(grT).center().padRight(10);
            ////// icon//////
            Image icon = GUI.createImage(atlas,"icon"+(i+1));
            icon.setScaleY(-1);
            icon.setOrigin(Align.center);
            icon.setPosition(tile.getX()+tile.getWidth()/2,tile.getY()+tile.getHeight()/2+10,Align.center);
            grT.addActor(icon);
            arrIcon.add(icon);
            ///// frame price //////
            Image frmPrice = GUI.createImage(atlas,"frmPrice");
            frmPrice.setPosition(tile.getX()+tile.getWidth()/2,tile.getY()+frmPrice.getHeight()/2,Align.center);
            grT.addActor(frmPrice);
            ///// Label price ///////
            priceVegtbl += ((i+1)*6);
            Label LblPrice = new Label(""+priceVegtbl,new Label.LabelStyle(font,null));
            LblPrice.setFontScale(0.8f,-0.8f);
            LblPrice.setOrigin(Align.center);
            LblPrice.setAlignment(Align.center);
            LblPrice.setPosition(frmPrice.getX()+frmPrice.getWidth()/2+15,frmPrice.getY()+60,Align.center);
            grT.addActor(LblPrice);
            arrPriceVegtbl.add(priceVegtbl);
            if(i!=0){
                Image frmLock = GUI.createImage(atlas,"C"+i);
                frmLock.setScale(1,-1);
                frmLock.setOrigin(Align.center);
                frmLock.setPosition(frmPrice.getX()+frmPrice.getWidth()/2,frmPrice.getY()+frmPrice.getHeight()/2,Align.center);
                grT.addActor(frmLock);
                arrFrmLock.add(frmLock);
            }
            ////// label lof ////////
            Image tile2 = GUI.createImage(atlas,"tileFotter");
            tile2.setPosition(tile.getX()+tile.getWidth()/2,tile.getY()+tile.getHeight()/2,Align.center);
            tile2.setColor(Color.CLEAR);
            grT.addActor(tile2);
            arrButton.add(tile2);

        }
        Scroll = new ScrollPane(tableScroll);
        table.setFillParent(true);
        table.add(Scroll).fill().expand();
        groupScrollVegtbl.setScale(1,-1);
        groupScrollVegtbl.setOrigin(Align.center);
        groupScrollVegtbl.addActor(table);
        group.addActor(groupScrollVegtbl);
    }
    private void tableAni(){
        ///////// scroll table  Animals////
        Table table = new Table();
        Table tableScroll = new Table();
        for(int i=0;i<4;i++){
            Group grT = new Group();
            Image tile = GUI.createImage(atlas,"tileFotter");
            grT.addActor(tile);
            grT.setSize(tile.getWidth(),tile.getHeight());
            tableScroll.add(grT).center().padRight(10);
            ////// icon//////
            Image icon = GUI.createImage(atlas,"ic"+(i+1));
            icon.setScaleY(-1);
            icon.setOrigin(Align.center);
            icon.setPosition(tile.getX()+tile.getWidth()/2,tile.getY()+tile.getHeight()/2+10,Align.center);
            grT.addActor(icon);
            arrIconAni.add(icon);
            ///// frame price //////
            Image frmPrice = GUI.createImage(atlas,"frmPrice");
            frmPrice.setPosition(tile.getX()+tile.getWidth()/2,tile.getY()+frmPrice.getHeight()/2,Align.center);
            grT.addActor(frmPrice);
            ///// Label price ///////
            priceAni += 100;
            Label LblPrice = new Label(""+priceAni,new Label.LabelStyle(font,null));
            LblPrice.setFontScale(0.8f,-0.8f);
            LblPrice.setOrigin(Align.center);
            LblPrice.setAlignment(Align.center);
            LblPrice.setPosition(frmPrice.getX()+frmPrice.getWidth()/2+15,frmPrice.getY()+60,Align.center);
            grT.addActor(LblPrice);
            arrPriceAni.add(priceAni);
            Image frmLock = GUI.createImage(atlas,"C"+((2+i)*2));
            frmLock.setScale(1,-1);
            frmLock.setOrigin(Align.center);
            frmLock.setPosition(frmPrice.getX()+frmPrice.getWidth()/2,frmPrice.getY()+frmPrice.getHeight()/2,Align.center);
            grT.addActor(frmLock);
            arrFrmLockAni.add(frmLock);
            ////// label lof ////////
            Image tile2 = GUI.createImage(atlas,"tileFotter");
            tile2.setPosition(tile.getX()+tile.getWidth()/2,tile.getY()+tile.getHeight()/2,Align.center);
            tile2.setColor(Color.CLEAR);
            grT.addActor(tile2);
            arrButtonAni.add(tile2);

        }
        Scroll = new ScrollPane(tableScroll);
        table.setFillParent(true);
        table.add(Scroll).fill().expand();
        groupScrollAni.setScale(1,-1);
        groupScrollAni.setOrigin(Align.center);
        groupScrollAni.addActor(table);
        group.addActor(groupScrollAni);
    }
    public void checkLock(){
        ////// Vegtable////
        if(Config.Level<22){
            int i=0;
            if(Config.Level<6)
                i=1;
            if(Config.Level<8 && Config.Level>=6)
                i=2;
            if(Config.Level<10 && Config.Level>=8)
                i=3;
            if(Config.Level<12 && Config.Level>=10)
                i=4;
            if(Config.Level<14 && Config.Level>=12)
                i=5;
            if(Config.Level<16 && Config.Level>=14)
                i=6;
            if(Config.Level<18 && Config.Level>=16)
                i=7;
            if(Config.Level<20 && Config.Level>=18)
                i=8;
            if(Config.Level<22 && Config.Level>=20)
                i=9;
            for (int ii = i;ii < arrIcon.size;ii++){
                arrIcon.get(ii).setColor(Color.DARK_GRAY);
//                arrFrmLock.get(ii-1).setVisible(true);
            }
            for (int i2 = (i-1); i2 >=0; i2--){
                System.out.println("framLock: "+i2);
                if(i2!=0){
                    arrFrmLock.get(i2-1).setVisible(false);
                }
                arrIcon.get(i2).setColor(Color.WHITE);
            }
        }else {
            for (int i=0;i<arrFrmLock.size;i++){
                arrFrmLock.get(i).setVisible(false);
            }
        }
        ////// animals//////
        for(int i=0;i<arrFrmLockAni.size;i++){
            arrFrmLockAni.get(i).setVisible(false);
            arrIconAni.get(i).setColor(Color.WHITE);

        }
        if(Config.Level<24){
            int i=0;
            if(Config.Level>=12&&Config.Level<16){
                i=1;
            }else if(Config.Level>=16&&Config.Level<20){
                i=2;
            }if(Config.Level>=20&&Config.Level<24){
                i=3;
            }
            for (int ii=i;ii<arrIconAni.size;ii++){
                arrIconAni.get(ii).setColor(Color.DARK_GRAY);
                arrFrmLockAni.get(ii).setVisible(true);
            }

        }else {
            for(int i=0;i<arrFrmLockAni.size;i++){
                arrFrmLockAni.get(i).setVisible(false);
            }
        }


    }

    private void eventBtn(){
        if(arrButton.size!=0){
            for (int i=0;i<arrButton.size;i++){
                int finalI = i;
                arrButton.get(i).addListener(new ClickListener(){
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchUp(event, x, y, pointer, button);
                    if(isdrag){
                        isdrag=false;
                    }else {
                        if(finalI!=0){
                            if(arrFrmLock.get(finalI-1).isVisible()==false){
                                if(gameScene.emptyVeg==true) {
                                    if (Config.Money - arrPriceVegtbl.get(finalI) >= 0) {
//                                    System.out.println("check empty: "+gameScene.emptyVeg);
                                        Config.Money -= arrPriceVegtbl.get(finalI);
                                        gameScene.setMoney(Config.Money);
                                        setIdAndKind(finalI);
                                        gameScene.createNewVegtbl(id, kind);
                                    } else {
                                        SoundEffect.Play(SoundEffect.createNew);
                                        Group group = new Group();
                                        GStage.addToLayer(GLayer.top, group);
                                        Label lb = new Label(C.lang.endmoney, new Label.LabelStyle(font, Color.RED));
                                        lb.setAlignment(Align.center);
                                        lb.setPosition(0, 0, Align.center);
                                        group.addActor(lb);
                                        group.setPosition(GStage.getWorldWidth() / 2, GStage.getWorldHeight() - 100, Align.center);
                                        group.addAction(Actions.sequence(
                                                Actions.moveBy(0, -100, 0.5f),
                                                GSimpleAction.simpleAction((d, a) -> {
                                                    group.clear();
                                                    group.remove();
                                                    return true;
                                                })
                                        ));
                                    }
                                }else {
                                    Group group = new Group();
                                    GStage.addToLayer(GLayer.top,group);
                                    Label text = new Label(C.lang.notempty,new Label.LabelStyle(font,null));
                                    text.setAlignment(Align.center);
                                    text.setPosition(0,0,Align.center);
                                    group.addActor(text);
                                    group.setPosition(GStage.getWorldWidth()/2,GStage.getWorldHeight()-100,Align.center);
                                    group.addAction(Actions.sequence(
                                            Actions.moveBy(0,-100,0.5f),
                                            GSimpleAction.simpleAction((d,a)->{
                                                group.clear();
                                                group.remove();
                                                return true;
                                            })
                                    ));
                                }

                            }else {
                                SoundEffect.Play(SoundEffect.createNew);
                                Group group = new Group();
                                GStage.addToLayer(GLayer.top,group);
                                Label lb = new Label(C.lang.locked,new Label.LabelStyle(font,null));
                                lb.setAlignment(Align.center);
                                lb.setPosition(0,0,Align.center);
                                group.addActor(lb);
                                group.setPosition(GStage.getWorldWidth()/2,GStage.getWorldHeight()-100,Align.center);
                                group.addAction(Actions.sequence(
                                        Actions.moveBy(0,-100,0.5f),
                                        GSimpleAction.simpleAction((d,a)->{
                                            group.clear();
                                            group.remove();
                                            return true;
                                        })
                                ));

                            }
                        }else {
                            if(gameScene.emptyVeg==true) {

                                if (Config.Money - arrPriceVegtbl.get(finalI) >= 0) {
                                    Config.Money -= arrPriceVegtbl.get(finalI);
                                    gameScene.setMoney(Config.Money);
                                    setIdAndKind(finalI);
                                    gameScene.createNewVegtbl(id, kind);
                                } else {
                                    SoundEffect.Play(SoundEffect.createNew);
                                    Group group = new Group();
                                    GStage.addToLayer(GLayer.top, group);
                                    Label lb = new Label(C.lang.endmoney, new Label.LabelStyle(font, Color.RED));
                                    lb.setAlignment(Align.center);
                                    lb.setPosition(0, 0, Align.center);
                                    group.addActor(lb);
                                    group.setPosition(GStage.getWorldWidth() / 2, GStage.getWorldHeight() - 100, Align.center);
                                    group.addAction(Actions.sequence(
                                            Actions.moveBy(0, -100, 0.5f),
                                            GSimpleAction.simpleAction((d, a) -> {
                                                group.clear();
                                                group.remove();
                                                return true;
                                            })
                                    ));
                                }
                            }else {
                                Config.Stepindex=2;
                                Group group = new Group();
                                GStage.addToLayer(GLayer.top,group);
                                Label text = new Label(C.lang.notempty,new Label.LabelStyle(font,null));
                                text.setAlignment(Align.center);
                                text.setPosition(0,0,Align.center);
                                group.addActor(text);
                                group.setPosition(GStage.getWorldWidth()/2,GStage.getWorldHeight()-100,Align.center);
                                group.addAction(Actions.sequence(
                                        Actions.moveBy(0,-100,0.5f),
                                        GSimpleAction.simpleAction((d,a)->{
                                            group.clear();
                                            group.remove();
                                            return true;
                                        })
                                ));
                            }

                        }




                    }
                    }
                });
                arrButton.get(i).addListener(new DragListener(){
                    @Override
                    public void dragStart(InputEvent event, float x, float y, int pointer) {
                        super.dragStart(event, x, y, pointer);
                        isdrag=true;
                    }
                });
            }
        }
    }
    private void eventBtnAni(){
        if(arrButtonAni.size!=0){
            for (int i=0;i<arrButtonAni.size;i++){
                int finalI = i;
                arrButtonAni.get(i).addListener(new ClickListener(){
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchUp(event, x, y, pointer, button);
                    if(isdrag){
                        isdrag=false;
                    }else {
                        if(arrFrmLockAni.get(finalI).isVisible()==false){
                            if(gameScene.checkEmptySlot()!=-1){
                                if(Config.Money-arrPriceAni.get(finalI)>=0){
                                    Config.Money-=arrPriceAni.get(finalI);
                                    gameScene.setMoney(Config.Money);
                                    setIdAndKindAni(finalI);
                                    gameScene.createNewAni(idAni,kindAni);
                                }else {
                                    SoundEffect.Play(SoundEffect.createNew);
                                    Group group = new Group();
                                    GStage.addToLayer(GLayer.top,group);
                                    Label lb = new Label(C.lang.endmoney,new Label.LabelStyle(font,Color.RED));
                                    lb.setAlignment(Align.center);
                                    lb.setPosition(0,0,Align.center);
                                    group.addActor(lb);
                                    group.setPosition(GStage.getWorldWidth()/2,GStage.getWorldHeight()-100,Align.center);
                                    group.addAction(Actions.sequence(
                                            Actions.moveBy(0,-100,0.5f),
                                            GSimpleAction.simpleAction((d,a)->{
                                                group.clear();
                                                group.remove();
                                                return true;
                                            })
                                    ));
                                }
                            }else {
                                Group group = new Group();
                                GStage.addToLayer(GLayer.top,group);
                                Label text = new Label(C.lang.notempty,new Label.LabelStyle(font,null));
                                text.setAlignment(Align.center);
                                text.setPosition(0,0,Align.center);
                                group.addActor(text);
                                group.setPosition(GStage.getWorldWidth()/2,GStage.getWorldHeight()-100,Align.center);
                                group.addAction(Actions.sequence(
                                        Actions.moveBy(0,-100,0.5f),
                                        GSimpleAction.simpleAction((d,a)->{
                                            group.clear();
                                            group.remove();
                                            return true;
                                        })
                                ));
                            }


                        }else {
                            SoundEffect.Play(SoundEffect.createNew);
                            Group group = new Group();
                            GStage.addToLayer(GLayer.top,group);
                            Label lb = new Label(C.lang.locked,new Label.LabelStyle(font,null));
                            lb.setAlignment(Align.center);
                            lb.setPosition(0,0,Align.center);
                            group.addActor(lb);
                            group.setPosition(GStage.getWorldWidth()/2,GStage.getWorldHeight()-100,Align.center);
                            group.addAction(Actions.sequence(
                                    Actions.moveBy(0,-100,0.5f),
                                    GSimpleAction.simpleAction((d,a)->{
                                        group.clear();
                                        group.remove();
                                        return true;
                                    })
                            ));

                        }

                    }
                    }
                });
                arrButtonAni.get(i).addListener(new DragListener(){
                    @Override
                    public void dragStart(InputEvent event, float x, float y, int pointer) {
                        super.dragStart(event, x, y, pointer);
                        isdrag=true;
                    }
                });
            }
        }
    }
    private void setIdAndKind(int index){
        if(index==0){
            kind = "lua";
            id=1;
        }else if(index==1){
            kind = "cachua";
            id=1;
        }else if(index==2){
            kind = "khoai";
            id=1;
        }else if(index==3){
            kind = "leo";
            id=1;
        }else if(index==4){
            kind = "bap";
            id=1;
        }else if(index==5){
            kind = "carot";
            id=1;
        }else if(index==6){
            kind = "cai";
            id=1;
        }else if(index==7){
            kind = "bi";
            id=1;
        }else if(index==8){
            kind = "thom";
            id=1;
        }else if(index==9){
            kind = "dua";
            id=1;
        }
    }
    private void setIdAndKindAni(int index){
        if(index==0){
            kindAni = "bo";
            idAni=1;
        }else if(index==1){
            kindAni = "ga";
            idAni=1;
        }else if(index==2){
            kindAni = "de";
            idAni=1;
        }else if(index==3){
            kindAni = "cuu";
            idAni=1;
        }
    }
    private void eventHarvest(Image btn){
        btn.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                SoundEffect.Play(SoundEffect.click);
                btn.setTouchable(Touchable.disabled);
                btn.addAction(Actions.sequence(
                        Actions.scaleTo(0.9f,0.9f,0.1f),
                        Actions.scaleTo(1f,1f,0.1f),
                        GSimpleAction.simpleAction((d,a)->{
                            gameScene.HarvestAniAndVeg();
                            btn.setTouchable(Touchable.enabled);
                            return true;
                        })
                ));
                return super.touchDown(event, x, y, pointer, button);
            }
        });

    }
    public void ActionsGr(int type){
        if(type==1)
            group.addAction(Actions.moveBy(0,300,0.2f));
        else
            group.addAction(Actions.moveBy(0,-300,0.2f));

    }


    private void initTexture(){
        atlas = GAssetsManager.getTextureAtlas("footer.atlas");
    }

}
