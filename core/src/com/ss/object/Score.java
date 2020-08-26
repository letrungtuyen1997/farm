package com.ss.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.ss.GMain;
import com.ss.HttpRequests;
import com.ss.commons.BitmapFontC;
import com.ss.commons.TextureAtlasC;
import com.ss.commons.Tweens;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.effects.SoundEffect;
import com.ss.gameLogic.config.C;
import com.ss.interfaces.IHttpRequest;
import com.ss.object.Data.FarmModel;

import java.util.List;

public class Score implements IHttpRequest {
    Group group = new Group();
    private Group groupScroll = new Group();
    private Image btnRank;
    private Table table;
    private  Table tableScroll;
    private HttpRequests httpRequests = HttpRequests.getInstance();
    private Array<Image> arrTile = new Array<>();
    private Array<Label> arrName = new Array<>();
    private Array<Label> arrMoney = new Array<>();
    private Array<Group> arrGr = new Array<>();
    public Score(Image btn){
        this.btnRank = btn;
        httpRequests.setiHttpRequest(this);
        GStage.addToLayer(GLayer.top,group);
        final GShapeSprite blackOverlay = new GShapeSprite();
        blackOverlay.createRectangle(true, -GStage.getWorldWidth(),-GStage.getWorldHeight()/2, GStage.getWorldWidth()*2, GStage.getWorldHeight()*2);
        blackOverlay.setColor(0,0,0,0.5f);
        group.addActor(blackOverlay);
        group.setScaleX(0);
        group.setOrigin(Align.center);
        group.setPosition(GStage.getWorldWidth()/2, GStage.getWorldHeight()/2,Align.center);
        group.addAction(Actions.scaleTo(1,1,0.3f, Interpolation.swingOut));
        renderListView();
    }
    private void renderListView(){
        Image Table = GUI.createImage(TextureAtlasC.atlasInfo,"frmScore");
        Table.setPosition(0,0,Align.center);
        group.addActor(Table);
        Label text = new Label(C.lang.lbRank,new Label.LabelStyle(BitmapFontC.robotoVi,Color.BROWN));
        text.setOrigin(Align.center);
        text.setPosition(0,-Table.getHeight()/2+50,Align.center);
        group.addActor(text);
        Image btnClose = GUI.createImage(TextureAtlasC.atlasInfo,"btnClose");
        btnClose.setOrigin(Align.center);
        btnClose.setPosition(Table.getWidth()/2-btnClose.getWidth()/2,-Table.getHeight()/2+btnClose.getHeight()-5,Align.center);
        group.addActor(btnClose);
        eventBtnClose(btnClose);


        groupScroll.setWidth(Table.getWidth()-100);
        groupScroll.setHeight(Table.getHeight()-240);
        groupScroll.setPosition(Table.getX()+Table.getWidth()/2+30,Table.getY()+Table.getHeight()/2,Align.center);
        ///////// scroll table ////
        table = new Table();
        tableScroll = new Table();
        for (int i=0;i<99;i++){
            Group grT = new Group();
            Image tile = GUI.createImage(TextureAtlasC.atlasInfo,"item");
            grT.addActor(tile);
            if(i<3){
                Image top = GUI.createImage(TextureAtlasC.atlasInfo,"Top"+(i+1));
                top.setScale(1,-1);
                top.setOrigin(Align.center);
                top.setPosition(-30,0);
                grT.addActor(top);
            }
            //////////// get best money///////
            Label nameLb = new Label("",new Label.LabelStyle(BitmapFontC.robotoVi,null));
            nameLb.setFontScale(1,-1);
            nameLb.setOrigin(Align.center);
            nameLb.setAlignment(Align.left);
            nameLb.setPosition(tile.getX()+40,tile.getY()+tile.getHeight()/2,Align.center);
            grT.addActor(nameLb);

            Label moneyLb= new Label("",new Label.LabelStyle(BitmapFontC.robotoVi,null));
            moneyLb.setFontScale(1,-1);
            moneyLb.setOrigin(Align.center);
            moneyLb.setAlignment(Align.left);
            moneyLb.setPosition(tile.getX()+tile.getWidth()/2+20,tile.getY()+tile.getHeight()/2,Align.center);
            grT.addActor(moneyLb);
            grT.setSize(tile.getWidth(),tile.getHeight());

            ////////// add arr /////
            arrTile.add(tile);
            arrName.add(nameLb);
            arrMoney.add(moneyLb);
            arrGr.add(grT);

        }
        ScrollPane Scroll = new ScrollPane(tableScroll);
        table.setFillParent(true);
        table.add(Scroll).fill().expand();
        groupScroll.setScale(1,-1);
        groupScroll.setOrigin(Align.center);
        groupScroll.addActor(table);
        group.addActor(groupScroll);

    }
    private void eventBtnClose(Image btn){
        btn.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                SoundEffect.Play(SoundEffect.tack);
                btn.addAction(Actions.sequence(
                        Actions.scaleTo(0.9f,0.9f,0.1f),
                        Actions.scaleTo(1f,1f,0.1f),
                        GSimpleAction.simpleAction((d, a)->{
                            group.clear();
                            group.remove();
                            btnRank.setTouchable(Touchable.enabled);
                            return true;
                        })
                ));
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    @Override
    public void finished(List<FarmModel> lsFarm) {
        Tweens.setTimeout(group,2f,()->{
            for(int i = 0 ; i < lsFarm.size() ; i++){
                int index = i;
                arrTile.get(index).setVisible(true);
                if(GMain.prefs.getString("_id").equals(lsFarm.get(index).get_id())){
                    arrName.get(index).setText("You");
                    arrName.get(index).setColor(Color.GREEN);
                    arrMoney.get(index).setColor(Color.GREEN);

                }else {
                    arrName.get(index).setText(lsFarm.get(index).getName());
                    arrName.get(index).setColor(Color.BROWN);
                    arrMoney.get(index).setColor(Color.BROWN);
                }
                arrMoney.get(index).setText(""+lsFarm.get(index).getMoney());
                tableScroll.add(arrGr.get(index)).center().pad(5);
                tableScroll.row();
            }
        });

    }
}
