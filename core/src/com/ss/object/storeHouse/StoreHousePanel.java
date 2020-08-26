package com.ss.object.storeHouse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
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
import com.badlogic.gdx.utils.Array;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.ss.GMain;
import com.ss.HttpRequests;
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
import com.ss.scenes.GameScene;

public class StoreHousePanel {
  private GameScene game;
  private TextureAtlas atlas;
  private Group group, groupUd, groupVideo, groupBuy,greenBtn,buyBtn,confirmBtn;
  private Image panel, frameWatch,watch, clockWise, closeBtn, bgPanel;
  private int maxCapacity, momentCapacity;
  private ScrollPaneC scrollPaneC;
  private Label maxCapacityTxt, momentCapacityTxt, levelStoreHouseTxt;
  private Array<Data> data;
  private int levelStoreHouse = GMain.prefs.getInteger("levelStoreHouse", 1);

  private int priceUd = 0;

  //ud
  private Image panelUd, bgUd, bgUdCore, yellowBtn, house, closeBtnUd, closeBtnBuy;
  private Label title, to, momentPrice, nextPrice, priceUdTxt;

  //udS
  private Image bgUdS1, bgUdS2, videoBtn, goldStars, titlePanel, iExp, iCoin, iHouse;
  private Label expTxt, coinTxt;

  private Image panelBuy, ylBtn;
  private Label titleBuy, txtBuyBtn, moneyBuyTxt;
  private boolean isSelling = false;
  ////// bonus money +exp /////
  private long moneyBonus =1000;
  private long expBonus=0;


  public StoreHousePanel(GameScene game){
    this.game = game;
    initAtlas();
    initGroup();
    initData();
    initUI();
    initQuantityCapacity();

    group.setVisible(false);
  }

  private void initAtlas(){
    atlas = TextureAtlasC.storeHouse;
  }

  private void initGroup(){
    group = new Group();
    groupUd = new Group();
    groupBuy = new Group();
    greenBtn = new Group();
    buyBtn = new Group();
    confirmBtn = new Group();
    GStage.addToLayer(GLayer.top, group);
    GStage.addToLayer(GLayer.top, groupUd);
    GStage.addToLayer(GLayer.top, groupBuy);

  }

  private void initData(){
    data = new Array<>();
    Gson gson = new Gson();
    FileHandle fileHandle;
    String str, path;
    JsonArray jsonArray;
    path = "json/dataStorehouse.json";
    fileHandle = Gdx.files.internal(path);
    str = GMain.prefs.getString("itemStoreHouse", fileHandle.readString());
    jsonArray = gson.fromJson(str, JsonArray.class);
    for(int i = 0; i < jsonArray.size(); i++) {
      Data dt = gson.fromJson(jsonArray.get(i), Data.class);
      data.add(dt);
    }
  }

  private void initUI(){
    initGShape();
    panel = createImage("panel", Config.WidthScreen*.5f, Config.HeightScreen*.5f, true);
    watch = createImage("watch", panel.getX() + panel.getWidth()*.5f, panel.getY() + panel.getHeight()*.22f,true);
    clockWise = createImage("clockWise", watch.getX() + watch.getWidth()*.5f, watch.getY() + watch.getHeight()*0.37f, true);
    clockWise.setOrigin(Align.top);
    clockWise.setRotation(-90);
    frameWatch = createImage("frameWatch", watch.getX() + watch.getWidth()*.5f, watch.getY() + watch.getHeight()*.52f, true);
    bgPanel = createImage("bgPanel", panel.getX() + panel.getWidth()*.49f, panel.getY() + panel.getHeight()*.51f, true);

    btn(greenBtn,panel.getX() + panel.getWidth()*.3f, panel.getY() + panel.getHeight()*.85f,"btnNhan",C.lang.lbup,TextureAtlasC.atlasInfo);
    btn(buyBtn,panel.getX() + panel.getWidth()*.7f, panel.getY() + panel.getHeight()*.85f,"yellowBtn",C.lang.lbsale,TextureAtlasC.storeHouse);
    group.addActor(greenBtn);
    group.addActor(buyBtn);
    closeBtn = GUI.createImage(atlas, "btnClose");
    group.addActor(closeBtn);
    closeBtn.setPosition(panel.getX() + panel.getWidth() - closeBtn.getWidth(), panel.getY());

    initCapacity();

    scrollPaneC = new ScrollPaneC(atlas, group, data, bgPanel.getWidth()*.9f, bgPanel.getHeight()*.9f, bgPanel.getX() + bgPanel.getWidth()*0.07f, bgPanel.getY() + bgPanel.getHeight()*0.05f);

    addEventBtn();

    groupUd.setVisible(false);
    initPriceUd();
    initUIUd();
  }
  private void btn(Group grbtn,float x,float y,String type,String lb,TextureAtlas atlas){
    Image btn = GUI.createImage(atlas,type);
    btn.setPosition(0,0);
    grbtn.addActor(btn);
    Label lbbtn = new Label(lb,new Label.LabelStyle(BitmapFontC.robotoVi,null));
    lbbtn.setFontScale(0.7f);
    lbbtn.setOrigin(Align.center);
    lbbtn.setAlignment(Align.center);
    lbbtn.setPosition(btn.getX()+btn.getWidth()/2,btn.getY()+btn.getHeight()/2-lbbtn.getPrefHeight()/4,Align.center);
    grbtn.addActor(lbbtn);
    grbtn.setWidth(btn.getWidth());
    grbtn.setHeight(btn.getHeight());
    grbtn.setOrigin(Align.center);
    grbtn.setPosition(x,y,Align.center);
  }

  private void initPriceUd(){
    switch (levelStoreHouse){
      case 1: {
        priceUd = 3600;
        break;
      }
      case 2: {
        priceUd = 4300;
        break;
      }
      case 3: {
        priceUd = 5100;
        break;
      }
      case 4: {
        priceUd = 6200;
        break;
      }
      case 5: {
        priceUd = 7400;
        break;
      }
      case 6: {
        priceUd = 9000;
        break;
      }
      case 7: {
        priceUd = 11000;
        break;
      }
      case 8: {
        priceUd = 13500;
        break;
      }
      case 9: {
        priceUd = 20000;
        break;
      }
      default: {
        priceUd = 20000;
        break;
      }
    }
  }

  private void initUIUd(){
    GShapeSprite gs = new GShapeSprite();
    gs.createRectangle(true, 0, 0, Config.WidthScreen, Config.HeightScreen);
    gs.setColor(0, 0, 0, 0.45f);
    groupUd.addActor(gs);

    panelUd = GUI.createImage(atlas, "panelUd");
    bgUd = GUI.createImage(atlas, "bgPanelUd");
    bgUdCore = GUI.createImage(atlas, "bgUdCore");
    yellowBtn = GUI.createImage(atlas, "yellowBtn");
    house = GUI.createImage(atlas, "house");
    closeBtnUd = GUI.createImage(atlas, "btnClose");
    title = new Label(C.lang.capacity, new Label.LabelStyle(BitmapFontC.RobotoViGrey, null));
    to = new Label(C.lang.lbTo, new Label.LabelStyle(BitmapFontC.RobotoViGrey, null));
    momentPrice = new Label("" + levelStoreHouse*5000, new Label.LabelStyle(BitmapFontC.NerwynGrey, null));
    nextPrice = new Label("" + (levelStoreHouse*5000 + 5000), new Label.LabelStyle(BitmapFontC.NerwynOrange, null));
    priceUdTxt = new Label("" + priceUd, new Label.LabelStyle(BitmapFontC.RubikOne, null));

    bgUdS1 = GUI.createImage(atlas, "bgUdS1");
    bgUdS2 = GUI.createImage(atlas, "bgUdS2");
    titlePanel = GUI.createImage(atlas, "titlePanel");
    goldStars = GUI.createImage(atlas, "goldStars");
//    confirmBtn = GUI.createImage(atlas, "confirmBtn");
//    confirmBtn.setPosition(panelUd.getX() + panelUd.getWidth()*.7f , panelUd.getY() + panelUd.getHeight()*.7f, Align.center);

    btn(confirmBtn,panelUd.getX() + panelUd.getWidth()*.7f , panelUd.getY() + panelUd.getHeight()*.7f,"btnNhan",C.lang.lbNhan,TextureAtlasC.atlasInfo);
    videoBtn = GUI.createImage(atlas, "videoBtnDb");
    iCoin = GUI.createImage(atlas, "iCoin");
    iExp = GUI.createImage(atlas, "iExp");
    iHouse = GUI.createImage(atlas, "house");
    expTxt = new Label("+0", new Label.LabelStyle(BitmapFontC.roboto, Color.BLUE));
    coinTxt = new Label("+0", new Label.LabelStyle(BitmapFontC.roboto, Color.YELLOW));

    groupUd.addActor(goldStars);
    groupUd.addActor(panelUd);
    groupUd.addActor(bgUd);
    groupUd.addActor(bgUdCore);
    groupUd.addActor(yellowBtn);
    groupUd.addActor(house);
    groupUd.addActor(closeBtnUd);
    groupUd.addActor(title);
    groupUd.addActor(to);
    groupUd.addActor(momentPrice);
    groupUd.addActor(nextPrice);
    groupUd.addActor(priceUdTxt);
    to.setFontScale(0.8f);

    groupUd.addActor(titlePanel);
    groupUd.addActor(bgUdS1);
    groupUd.addActor(bgUdS2);
    groupUd.addActor(confirmBtn);
    groupUd.addActor(videoBtn);
    groupUd.addActor(iCoin);
    groupUd.addActor(iExp);
    groupUd.addActor(iHouse);
    groupUd.addActor(expTxt);
    groupUd.addActor(coinTxt);
    iHouse.setSize(iHouse.getWidth()*0.3f, iHouse.getHeight()*0.3f);

    visibleUdS(false);

    panelUd.setPosition(Config.WidthScreen*.5f, Config.HeightScreen*.5f, Align.center);
    bgUd.setPosition(panelUd.getX() + panelUd.getWidth()*.49f, panelUd.getY() + panelUd.getHeight()*.5f, Align.center);
    bgUdCore.setPosition(panelUd.getX() + panelUd.getWidth()*.49f, panelUd.getY() + panelUd.getHeight()*.52f, Align.center);
    yellowBtn.setPosition(panelUd.getX() + panelUd.getWidth()*.49f, panelUd.getY() + panelUd.getHeight()*.85f, Align.center);
    house.setPosition(Config.WidthScreen*.49f, Config.HeightScreen*.51f, Align.center);
    closeBtnUd.setPosition(panelUd.getX() + panelUd.getWidth() - closeBtnUd.getWidth(), panelUd.getY());
    title.setPosition(bgUd.getX() + bgUd.getWidth()*.5f, bgUd.getY() + bgUd.getWidth()*.05f, Align.center);
    momentPrice.setPosition(bgUd.getX() + bgUd.getWidth()*.3f, bgUd.getY() + bgUd.getHeight()*.16f, Align.center);
    nextPrice.setPosition(bgUd.getX() + bgUd.getWidth()*.7f, bgUd.getY() + bgUd.getHeight()*.16f, Align.center);
    to.setPosition(bgUd.getX() + bgUd.getWidth()*.5f, bgUd.getY() + bgUd.getHeight()*.16f, Align.center);
    priceUdTxt.setPosition(yellowBtn.getX() + yellowBtn.getWidth()*.5f, yellowBtn.getY() + yellowBtn.getHeight()*.5f, Align.center );
    eventCloseUd(closeBtnUd);
    eventUdBtn(yellowBtn);
    eventConfirm(confirmBtn);

    goldStars.setPosition(panelUd.getX() + panelUd.getWidth()*.5f, panelUd.getY() - goldStars.getHeight()*.35f, Align.center);
    titlePanel.setPosition(panelUd.getX() + panelUd.getWidth()*.5f, panelUd.getY() + titlePanel.getHeight()*0.2f, Align.center);
    bgUdS1.setPosition(panelUd.getX() + panelUd.getWidth()*.5f, panelUd.getY() + panelUd.getHeight()*.25f, Align.center);
    bgUdS2.setPosition(panelUd.getX() + panelUd.getWidth()*.5f, panelUd.getY() + panelUd.getHeight()*.5f, Align.center);
    videoBtn.setPosition(panelUd.getX() + panelUd.getWidth()*.3f , panelUd.getY() + panelUd.getHeight()*.7f, Align.center);
    confirmBtn.setPosition(panelUd.getX() + panelUd.getWidth()*.7f , panelUd.getY() + panelUd.getHeight()*.7f, Align.center);
    iHouse.setPosition(bgUdS1.getX() + bgUdS1.getWidth()*.5f, bgUdS1.getY() + bgUdS1.getHeight()*.5f, Align.center);
    iExp.setPosition(bgUdS2.getX() + bgUdS2.getWidth()*.3f, bgUdS2.getY() + bgUdS2.getHeight()*.4f, Align.center);
    iCoin.setPosition(bgUdS2.getX() + bgUdS2.getWidth()*.7f, bgUdS2.getY() + bgUdS2.getHeight()*.4f, Align.center);
    expTxt.setPosition(iExp.getX() + (iExp.getWidth() - expTxt.getWidth())*.5f, iExp.getY() + iExp.getHeight()*1.1f);
    coinTxt.setPosition(iCoin.getX() + (iCoin.getWidth() - coinTxt.getWidth())/2, iCoin.getY() + iCoin.getHeight()*1.1f);

    eventVideoBtn(videoBtn);
    initUIVideo();
    initPanelBuy();
  }

  private void initUIVideo(){
    groupVideo = new Group();
    GStage.addToLayer(GLayer.top, groupVideo);
    groupVideo.setVisible(false);

    GShapeSprite gs = new GShapeSprite();
    gs.createRectangle(true, 0, 0, Config.WidthScreen, Config.HeightScreen);
    gs.setColor(0, 0, 0, 0.5f);
    groupVideo.addActor(gs);

    Image panelVideo = GUI.createImage(atlas, "panelvideo");
    Image vdBtn = GUI.createImage(atlas, "videoBtn");
    Image closeVdBtn = GUI.createImage(atlas, "btnClose");
    groupVideo.addActor(panelVideo);
    groupVideo.addActor(vdBtn);
    groupVideo.addActor(closeVdBtn);

    panelVideo.setPosition(Config.WidthScreen*.5f, Config.HeightScreen*.5f, Align.center);
    vdBtn.setPosition(panelVideo.getX() + panelVideo.getWidth()*.5f, panelVideo.getY() + panelVideo.getHeight()*.6f, Align.center);
    closeVdBtn.setPosition(panelVideo.getX() + panelVideo.getWidth() - closeVdBtn.getWidth(), panelVideo.getY());
    eventBtnCloseVd(closeVdBtn, groupVideo);
    eventVdBtn(vdBtn);
  }

  private void eventBtnCloseVd(Image img, Group groupVd){
    img.addListener(new ClickListener(){
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        SoundEffect.Play(SoundEffect.click);
        groupVd.setVisible(false);
        return super.touchDown(event, x, y, pointer, button);
      }
    });
  }

  private void eventVdBtn(Image img){
    img.addListener(new ClickListener(){
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        img.setColor(158, 125, 45, 0.5f);
        SoundEffect.Play(SoundEffect.click);
        return super.touchDown(event, x, y, pointer, button);
      }

      @Override
      public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        super.touchUp(event, x, y, pointer, button);
        img.setColor(Color.WHITE);
        showAds(1);
      }
    });
  }



  private void eventCloseUd(Image img){
    img.addListener(new ClickListener(){
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        SoundEffect.Play(SoundEffect.click);
        groupUd.setVisible(false);
        group.setVisible(true);
        return super.touchDown(event, x, y, pointer, button);
      }
    });
  }

  private void eventUdBtn(Image img){
    img.addListener(new ClickListener(){
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        img.setTouchable(Touchable.disabled);
        SoundEffect.Play(SoundEffect.click);
        img.setColor(158, 125, 45, 0.5f);
        return super.touchDown(event, x, y, pointer, button);
      }

      @Override
      public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        super.touchUp(event, x, y, pointer, button);
        img.setTouchable(Touchable.enabled);
        img.setColor(Color.WHITE);
        updateStoreHouse();
      }
    });
  }

  private void eventConfirm(Group img){
    img.addListener(new ClickListener(){
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        SoundEffect.Play(SoundEffect.click);
        img.setColor(158, 125, 45, 0.5f);
        return super.touchDown(event, x, y, pointer, button);
      }

      @Override
      public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        super.touchUp(event, x, y, pointer, button);
        img.setColor(Color.WHITE);
        visibleUd(true);
        System.out.println("nhan!!!!!!!!");
        Config.Money+=moneyBonus;
        game.setMoney(Config.Money);
        Config.momentExp+=expBonus;
        if(Config.momentExp>= Config.nextExp){
          Config.Level++;
          Config.nextExp = (10*Config.Level*Config.Level);
          Config.momentExp = 0;
          GMain.prefs.putInteger("level", Config.Level);
          GMain.prefs.putLong("momentExp", Config.momentExp);
          GMain.prefs.putLong("nextExp", Config.nextExp);
          GMain.prefs.flush();
          System.out.println("level up");
          game.updateLvSc();

          game.showPanelLvUp(true);
          //todo: goi ham show tăng lv
        }
        else {
          GMain.prefs.putLong("momentExp", Config.momentExp);
          GMain.prefs.flush();
          game.updateLvSc();
        }

      }
    });
  }

  private void eventVideoBtn(Image img){ //video x2 coin
    img.addListener(new ClickListener(){
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        SoundEffect.Play(SoundEffect.click);
        img.setColor(158, 125, 45, 0.5f);
        return super.touchDown(event, x, y, pointer, button);
      }

      @Override
      public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        super.touchUp(event, x, y, pointer, button);
        img.setColor(Color.WHITE);
        showAds(2);
      }
    });
  }

  private void updateStoreHouse(){
    SoundEffect.Play(SoundEffect.levelup);
    if(Config.Money >= priceUd){
      Config.Money -= priceUd;
      game.setMoney(Config.Money);
      levelStoreHouse++;
      GMain.prefs.putInteger("levelStoreHouse", levelStoreHouse);
      GMain.prefs.flush();
      visibleUd(false);
      initPriceUd();
      levelStoreHouseTxt.setText(C.lang.level + levelStoreHouse);
      maxCapacity = levelStoreHouse*5000;
      maxCapacityTxt.setText("/" + maxCapacity);
      priceUdTxt.setText(priceUd);
      momentPrice.setText(levelStoreHouse*5000);
      nextPrice.setText((levelStoreHouse+1)*5000);

      //setTextMoney
      game.setMoney(Config.Money);

    }
    else{
      groupVideo.setVisible(true);
      System.out.println("log herer!");
    }
  }

  private void visibleUdS(boolean isVisible){
    goldStars.setVisible(isVisible);
    bgUdS1.setVisible(isVisible);
    bgUdS2.setVisible(isVisible);
    confirmBtn.setVisible(isVisible);
    videoBtn.setVisible(isVisible);
    iCoin.setVisible(isVisible);
    iExp.setVisible(isVisible);
    iHouse.setVisible(isVisible);
    expTxt.setVisible(isVisible);
    coinTxt.setVisible(isVisible);
  }

  private void visibleUd(boolean isVisible){
    visibleUdS(!isVisible);
    closeBtnUd.setVisible(isVisible);
    house.setVisible(isVisible);
    bgUdCore.setVisible(isVisible);
    bgUd.setVisible(isVisible);
    float size = (float)title.getWidth()/title.getText().length;
    String str = isVisible ? C.lang.capacity : C.lang.levelhouse + levelStoreHouse;
    title.setText(str);
    title.setWidth(size*title.getText().length);
    title.setPosition(bgUd.getX() + (bgUd.getWidth() - title.getWidth())*.5f, bgUd.getY() - bgUd.getHeight()*.2f);
    yellowBtn.setVisible(isVisible);
    priceUdTxt.setVisible(isVisible);
    momentPrice.setVisible(isVisible);
    nextPrice.setVisible(isVisible);
    to.setVisible(isVisible);


  }

  private void initQuantityCapacity(){
    if(levelStoreHouse >= 10){
      maxCapacity = 50000;
    }
    else maxCapacity = 5000*levelStoreHouse;

    momentCapacity = scrollPaneC.getMomentCapacity();
    maxCapacityTxt.setText("/" + maxCapacity);
    momentCapacityTxt.setText(momentCapacity);
    momentCapacityTxt.setWidth(momentCapacityTxt.getPrefWidth());
  }

  private void initCapacity(){
    levelStoreHouseTxt = new Label(C.lang.level + levelStoreHouse, new Label.LabelStyle(BitmapFontC.RobotoViGrey, null));
    maxCapacityTxt = new Label("/" + maxCapacity, new Label.LabelStyle(BitmapFontC.roboto, null));
    momentCapacityTxt = new Label("" + momentCapacity, new Label.LabelStyle(BitmapFontC.roboto, null));

    group.addActor(maxCapacityTxt);
    group.addActor(momentCapacityTxt);
    group.addActor(levelStoreHouseTxt);

    momentCapacityTxt.setPosition(panel.getX() + panel.getWidth()*0.57f, panel.getY() + panel.getHeight()*0.13f);
    maxCapacityTxt.setPosition(momentCapacityTxt.getX() + momentCapacityTxt.getWidth(), momentCapacityTxt.getY());
    levelStoreHouseTxt.setPosition(panel.getX() + panel.getWidth()*0.2f, momentCapacityTxt.getY());
  }

  private void aniClockWise(){
    float ratio = (float)momentCapacity/maxCapacity;
    float de = ratio*180 - 90;
    if(de > 90) de = 90;
    clockWise.setRotation(-90);
    clockWise.addAction(Actions.sequence(
            Actions.rotateTo(89, 1, Interpolation.slowFast),
            Actions.rotateTo(de - 10, 1, Interpolation.fastSlow),
            Actions.rotateTo(de, 1, Interpolation.fastSlow)
    ));
  }

  private void initGShape(){
    GShapeSprite gShape = new GShapeSprite();
    gShape.createRectangle(true, 0, 0, Config.WidthScreen, Config.HeightScreen);
    gShape.setColor(0, 0, 0, 0.45f);
    group.addActor(gShape);
  }

  private Image createImage(String str, float x, float y, boolean isCenter){
    Image img = GUI.createImage(atlas, str);
    group.addActor(img);
    if(!isCenter)
      img.setPosition(x, y);
    else img.setPosition(x, y, Align.center);
    return img;
  }

  private void addEventBtn(){
    greenBtn.setOrigin(Align.center);
    greenBtn.addListener(new ClickListener(){
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        SoundEffect.Play(SoundEffect.click);
        greenBtn.setTouchable(Touchable.disabled);
        greenBtn.addAction(Actions.sequence(
                Actions.scaleBy(-0.2f, -0.2f, 0.1f, Interpolation.swingIn),
                Actions.scaleBy(0.2f, 0.2f, 0.1f, Interpolation.swingOut),
                Actions.run(()->{
                  greenBtn.setTouchable(Touchable.enabled);
                  group.setVisible(false);
                  groupUd.setVisible(true);
                  moneyBonus=moneyBonus*levelStoreHouse;
                  expBonus=moneyBonus/90;
                  coinTxt.setText(""+moneyBonus);
                  expTxt.setText(""+expBonus);
                })
        ));
        return super.touchDown(event, x, y, pointer, button);
      }
    });

    closeBtn.addListener(new ClickListener(){
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        group.setVisible(false);
        SoundEffect.Play(SoundEffect.click);
        return super.touchDown(event, x, y, pointer, button);
      }
    });

    buyBtn.setOrigin(Align.center);
    buyBtn.addListener(new ClickListener(){
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        SoundEffect.Play(SoundEffect.click);
        Config.Stepindex=6;
        buyBtn.setTouchable(Touchable.disabled);
        buyBtn.addAction(Actions.sequence(
                Actions.scaleBy(-0.2f, -0.2f, 0.1f, Interpolation.swingIn),
                Actions.scaleBy(0.2f, 0.2f, 0.1f, Interpolation.swingOut),
                Actions.run(()->{
                  buyBtn.setTouchable(Touchable.enabled);
                  showPanelBuy();
                })
        ));
        return super.touchDown(event, x, y, pointer, button);
      }
    });
  }

  private void initPanelBuy(){
    GShapeSprite gs = new GShapeSprite();
    gs.createRectangle(true, 0, 0, Config.WidthScreen, Config.HeightScreen);
    gs.setColor(0, 0, 0, 0.5f);
    panelBuy = GUI.createImage(atlas, "panelUd");
    ylBtn = GUI.createImage(atlas, "yellowBtn");
    closeBtnBuy = GUI.createImage(atlas, "btnClose");
    txtBuyBtn = new Label("OK", new Label.LabelStyle(BitmapFontC.RobotoViGrey, null));
    titleBuy = new Label(C.lang.moneytake, new Label.LabelStyle(BitmapFontC.RobotoViGrey, null));
    moneyBuyTxt = new Label("" + getMoney(), new Label.LabelStyle(BitmapFontC.RobotoViGrey, null));
    groupBuy.addActor(gs);
    groupBuy.addActor(panelBuy);
    groupBuy.addActor(ylBtn);
    groupBuy.addActor(titleBuy);
    groupBuy.addActor(closeBtnBuy);
    groupBuy.addActor(txtBuyBtn);
    groupBuy.addActor(moneyBuyTxt);
    panelBuy.setHeight(panelBuy.getHeight()*0.5f);
    closeBtnBuy.setHeight(closeBtnBuy.getHeight()*0.5f);
    panelBuy.setPosition(Config.WidthScreen*.5f, Config.HeightScreen*.5f, Align.center);
    ylBtn.setPosition(panelBuy.getX() + panelBuy.getWidth()*.5f, panelBuy.getY() + panelBuy.getHeight()*.8f, Align.center);
    txtBuyBtn.setPosition(ylBtn.getX() + ylBtn.getWidth()*.5f, ylBtn.getY() + ylBtn.getHeight()*.5f, Align.center);
    titleBuy.setPosition(panelBuy.getX() + panelBuy.getWidth()*.5f, panelBuy.getY() + panelBuy.getHeight()*.3f, Align.center);
    moneyBuyTxt.setPosition(panelBuy.getX() + panelBuy.getWidth()*.5f, panelBuy.getY() + panelBuy.getHeight()*.4f, Align.center);
    closeBtnBuy.setPosition(panelBuy.getX() + panelBuy.getWidth() - closeBtnBuy.getWidth(), panelBuy.getY());
    groupBuy.setVisible(false);
    eventCloseBtnBuy(closeBtnBuy);
    eventBuy(ylBtn);
  }

  private void eventCloseBtnBuy(Image img){
    img.addListener(new ClickListener(){
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        SoundEffect.Play(SoundEffect.click);
        groupBuy.setVisible(false);
        return super.touchDown(event, x, y, pointer, button);
      }
    });
  }

  private void eventBuy(Image img){
    img.addListener(new ClickListener(){
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        SoundEffect.Play(SoundEffect.sell);
        img.setColor(158, 125, 45, 0.5f);
        return super.touchDown(event, x, y, pointer, button);
      }

      @Override
      public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        img.setColor(Color.WHITE);
        buyItem();
        super.touchUp(event, x, y, pointer, button);
      }
    });
  }

  private void buyItem(){
    if(getMoney() != 0 && !isSelling){
      Config.Stepindex=7;
      if(GMain.prefs.getBoolean("checkFirst")==false) {
        GMain.platform.ShowFullscreen();
      }
      isSelling = true;
      game.aniCar(()->{
        isSelling = false;
        System.out.println("move completed");
      });
      //ani car
      int moneyAdd = getMoney();
      Config.Money += moneyAdd;
      game.setMoney(Config.Money);
      Config.BestMoney+=Config.Money;
      ////////////// post best score ////////
      if(GMain.prefs.getString("_id").length()==0){
        HttpRequests.getInstance().postNewMoney(Config.BestMoney);
      }else {
          HttpRequests.getInstance().updateMoney(GMain.prefs.getString("_id"),Config.BestMoney);
      }
      Config.momentExp += moneyAdd/10;
      if(Config.momentExp>= Config.nextExp){
        Config.Level++;
        Config.nextExp = (10*Config.Level*Config.Level);
        Config.momentExp = 0;
        GMain.prefs.putInteger("level", Config.Level);
        GMain.prefs.putLong("momentExp", Config.momentExp);
        GMain.prefs.putLong("nextExp", Config.nextExp);
        GMain.prefs.flush();
        System.out.println("level up");
        game.updateLvSc();

        game.showPanelLvUp(true);
        //todo: goi ham show tăng lv
      }
      else {
        GMain.prefs.putLong("momentExp", Config.momentExp);
        GMain.prefs.flush();
        game.updateLvSc();
      }

      //udate txt ;money
      game.setMoney(Config.Money);

      System.out.println(scrollPaneC.getSizeTiles() + " size tiles");
      for(int i = 0; i < scrollPaneC.getSizeTiles(); i++) {
        addQuantityItem(i, -1*data.get(i).quantity);
      }
      saveData();
      group.setVisible(false);
    }
    groupBuy.setVisible(false);
  }

  private int getMoney(){
    return scrollPaneC.getMoney();
  }

  private void showPanelBuy(){
    if(getMoney() == 0){
      System.out.println("alo");
      float size = titleBuy.getWidth()/titleBuy.getText().length;
      titleBuy.setText(C.lang.empty);
      titleBuy.setWidth(size*titleBuy.getText().length);
      titleBuy.setX(panelBuy.getX() + (panelBuy.getWidth() - titleBuy.getWidth())/2);
      moneyBuyTxt.setVisible(false);
    }
    else {
      moneyBuyTxt.setVisible(true);
      float size = moneyBuyTxt.getWidth()/moneyBuyTxt.getText().length;
      moneyBuyTxt.setText(getMoney());
      moneyBuyTxt.setWidth(size*moneyBuyTxt.getText().length);
      moneyBuyTxt.setX(panelBuy.getX() + (panelBuy.getWidth() - moneyBuyTxt.getWidth())/2);
    }
    groupBuy.setVisible(true);
  }

  public void showStoreHouse(){
    group.setVisible(true);
    initQuantityCapacity();
    momentCapacityTxt.setText(momentCapacity);
    momentCapacityTxt.setWidth(momentCapacityTxt.getPrefWidth());
    maxCapacityTxt.setText("/" + maxCapacity);
    maxCapacityTxt.setPosition(momentCapacityTxt.getX() + momentCapacityTxt.getWidth(), momentCapacityTxt.getY());
    aniClockWise();
    scrollPaneC.updateDataTable(data);
  }

  public boolean addQuantityItem(int id, int quantity){
    if(maxCapacity >= (momentCapacity + quantity)){ //todo: còn đủ chổ để lưu
      data.get(id).setQuantity(data.get(id).quantity + quantity);
      scrollPaneC.updateDataTable(data);
      momentCapacity = scrollPaneC.getMomentCapacity();
      updatePositionCapacityTxt();
      return true;
    }
    return false;
  }

  private void updatePositionCapacityTxt(){
    momentCapacityTxt.setWidth(momentCapacityTxt.getPrefWidth());
    maxCapacityTxt.setX(momentCapacityTxt.getX() + momentCapacityTxt.getWidth());
  }

  public void saveData(){
    String str = "[";
    String ope = ",";
    for(Data dt : data){
      if(data.indexOf(dt, true) >= data.size -1){
        ope = "]";
      }
      str += "{lvUnlock: " + dt.lvUnlock + ", strName: " + dt.strName + ", price: " + dt.price + ", quantity: " + dt.quantity + "}" + ope;
    }
    GMain.prefs.putString("itemStoreHouse", str);
    GMain.prefs.flush();
  }
  void showAds(int X2Coin){
    Group group = new Group();
    GStage.addToLayer(GLayer.top,group);
    if(GMain.platform.isVideoRewardReady()) {
      GMain.platform.ShowVideoReward((boolean success) -> {
        if (success) {
          GMain.platform.ShowFullscreen();
          group.clear();
          group.remove();
          Config.Money+=(2000*X2Coin);
          game.setMoney(Config.Money);
        }else {
          GMain.platform.ShowFullscreen();
          group.clear();
          group.remove();
        }
      });
    }else {
      Label notice = new Label(C.lang.checkconnect,new Label.LabelStyle(BitmapFontC.robotoVi, Color.RED));
      notice.setPosition(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,Align.center);
      group.addActor(notice);
      notice.addAction(Actions.sequence(
              Actions.moveBy(0,-50,0.5f),
              GSimpleAction.simpleAction((d, a)->{
                notice.clear();
                notice.remove();
                group.clear();
                group.remove();
                return true;
              })
      ));

    }
  }

}


