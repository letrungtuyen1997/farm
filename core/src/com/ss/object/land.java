package com.ss.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.util.GAssetsManager;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.gameLogic.StaticObjects.Config;
import com.ss.gameLogic.config.C;
import com.ss.scenes.GameScene;

import java.text.DecimalFormat;

public class land {
    public Rectangle body;
    public TextureAtlas atlas;
    public Image land, land2,landLockOff,landLockOn;
    public int Status=-1;
    public int index=0;
    public BitmapFont font;
    public long price=0;
    public  Label LbPrice;
    private GameScene gameScene;
    final GShapeSprite blackOverlay = new GShapeSprite();
    private Group group;
    public Group childGroup;


    public land(TextureAtlas atlas, Group group, float PaddingX, int PaddingY, int row, int col, int Status, GameScene gameScene){
        this.atlas = atlas;
        this.Status = Status;
        this.gameScene=gameScene;
        this.group = group;
        childGroup = new Group();
        this.group.addActor(childGroup);
        initFont();

        land = GUI.createImage(atlas,"land");
//        land.setPosition(PaddingX +(land.getWidth()+10)*col,200-land.getHeight()*3/2+20)+(land.getHeight()+10)*PaddingY, Align.center);
        land.setPosition(PaddingX+(land.getWidth()+10)*col, GStage.getWorldHeight()/2+270 -(land.getHeight()*3/2+20)+(land.getHeight()+10)*PaddingY,Align.center);
        land.setOrigin(Align.center);
        childGroup.addActor(land);
        body = new Rectangle(land.getX(),land.getY(),land.getWidth()/2,land.getHeight()/2);
        body.setPosition(land.getX()+land.getWidth()*(0.25f),land.getY()+land.getHeight()*(0.25f));
//        blackOverlay.createRectangle(true, body.getX(),body.getY(), body.getWidth(), body.getHeight());
//        blackOverlay.setColor(0,0,0,0.5f);
//        group.addActor(blackOverlay);
        ////// land2 //////
        land2 = GUI.createImage(atlas,"land2");
        land2.setPosition(land.getX()+land.getWidth()/2,land.getY()+land.getHeight()/2,Align.center);
        childGroup.addActor(land2);
        ///// landLockOff //////
        landLockOff = GUI.createImage(atlas,"landLockOff");
        landLockOff.setPosition(land.getX()+land.getWidth()/2,land.getY()+land.getHeight()/2,Align.center);
        childGroup.addActor(landLockOff);
        ///////// landLockOn ////////
        landLockOn = GUI.createImage(atlas,"landLockOn");
        landLockOn.setPosition(land.getX()+land.getWidth()/2,land.getY()+land.getHeight()/2,Align.center);
        childGroup.addActor(landLockOn);
        //////// price ///////
//        price =
        LbPrice = new Label(""+price,new Label.LabelStyle(font,null));
        LbPrice.setAlignment(Align.center);
        LbPrice.setPosition(landLockOn.getX()+landLockOn.getWidth()/2,landLockOn.getY()+landLockOn.getHeight()/2-LbPrice.getHeight(),Align.center);
        childGroup.addActor(LbPrice);

        landLockOn.setVisible(false);
        landLockOff.setVisible(false);
        land2.setVisible(false);
        LbPrice.setVisible(false);
        ////// event /////////
        eventUnlockLand();

    }
    private void eventUnlockLand(){
        landLockOn.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            super.touchUp(event, x, y, pointer, button);
            if(Config.Money-price>=0){
                Config.Money-=price;
                gameScene.setMoney(Config.Money);
                Status = -1;
                LoadByStatus();
            }else {
                Label text = new Label(C.lang.endmoney,new Label.LabelStyle(font, Color.RED));
                text.setAlignment(Align.center);
                text.setPosition(landLockOn.getX()+landLockOn.getWidth()/2,landLockOn.getY(),Align.center);
                group.addActor(text);
                text.addAction(Actions.sequence(
                        Actions.moveBy(0,-100,0.5f),
                        GSimpleAction.simpleAction((d, a)->{
                            text.clear();
                            text.remove();
                            return true;
                        })
                ));

            }

            }
        });
    }
    public void setPrice(){
        if(index%5==0){
            price=index*500;
            LbPrice.setText(FortmartPrice(price));
        }else {
            if((index+1)%5==0){
                price=(index+1)*500;
                LbPrice.setText(FortmartPrice(price));
            }
            if((index+2)%5==0){
                price=(index+2)*500;
                LbPrice.setText(FortmartPrice(price));
            }
            if((index+3)%5==0){
                price=(index+3)*500;
                LbPrice.setText(FortmartPrice(price));
            }
            if((index+4)%5==0){
                price=(index+4)*500;
                LbPrice.setText(FortmartPrice(price));
            }
        }

    }
    public void LoadByStatus(){
        switch(this.Status){
            case 0:{
                land.setVisible(false);
                land2.setVisible(false);
                landLockOff.setVisible(false);
                landLockOn.setVisible(false);
                LbPrice.setVisible(false);
                break;
            }
            case 1:{
                land.setVisible(false);
                land2.setVisible(false);
                landLockOn.setVisible(false);
                landLockOff.setVisible(true);
                LbPrice.setVisible(false);
                break;
            }
            case 2:{
                land.setVisible(false);
                land2.setVisible(false);
                landLockOn.setVisible(true);
                landLockOff.setVisible(false);
                LbPrice.setVisible(true);
                break;
            }
            case -1:{
                land.setVisible(true);
                land2.setVisible(false);
                landLockOff.setVisible(false);
                landLockOn.setVisible(false);
                LbPrice.setVisible(false);
                break;
            }
            case 3:{
                land.setVisible(true);
                land2.setVisible(false);
                landLockOff.setVisible(false);
                landLockOn.setVisible(false);
                LbPrice.setVisible(false);
                break;
            }
            case 4:{
                land.setVisible(true);
                land2.setVisible(false);
                landLockOff.setVisible(false);
                landLockOn.setVisible(false);
                LbPrice.setVisible(false);
                break;
            }
            default:{
               break;
            }
        }
    }
    private String FortmartPrice(Long Price) {

        DecimalFormat mDecimalFormat = new DecimalFormat("###,###,###,###");
        String mPrice = mDecimalFormat.format(Price);

        return mPrice;
    }
    private void initFont(){
        font = GAssetsManager.getBitmapFont("font_name_bot.fnt");
    }

}
