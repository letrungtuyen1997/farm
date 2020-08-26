package com.ss.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
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
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.ss.GMain;
import com.ss.HttpRequests;
import com.ss.commons.BitmapFontC;
import com.ss.commons.TextureAtlasC;
import com.ss.commons.Tweens;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.util.GAssetsManager;
import com.ss.core.util.GLayer;
import com.ss.core.util.GLayerGroup;
import com.ss.core.util.GScreen;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.effects.SoundEffect;
import com.ss.effects.effectWin;
import com.ss.gameLogic.StaticObjects.Config;
import com.ss.gameLogic.config.C;
import com.ss.object.Data.Anidata;
import com.ss.object.Data.HaveAniData;
import com.ss.object.Data.Landdata;
import com.ss.object.Data.Vegdata;
import com.ss.object.Fotter;
import com.ss.object.Info;
import com.ss.object.Score;
import com.ss.object.Tutorial2;
import com.ss.object.animals;
import com.ss.object.land;
import com.ss.object.storeHouse.StoreHousePanel;
import com.ss.object.tutorial;
import com.ss.object.vegetable;

import java.util.ArrayList;

public class GameScene extends GScreen{
    private TextureAtlas atlas,atlasStore;
    private BitmapFont font;
    private GLayerGroup mainGroup;
    public Image bg;
    public Group groupScroll = new Group();
    private Table container;
    public ScrollPane scroll;
    private vegetable vegetable;
    private animals ani;
    public Array<Image> arrOverLap = new Array<>();
    public int Zindex =0;
    public int ZindexAni =0;
    public Vector2 EdgeX = new Vector2();
    public Vector2 EdgeY = new Vector2();
    public Array<vegetable> arrVegatable = new Array<>();
    public Array<Integer> arrAniEdge = new Array<>();
    public Array<Array<Integer>> arrAniIndex = new Array<>();
    private Array<Integer> arrHaveAni = new Array<>();
    public Array<animals> arrAnimals = new Array<>();
    private land land ;
    public Array<land> arrLand =new Array<>();
    private Fotter fotter;
    private Array<Integer> arrIndexAni = new Array<>();
    private Info info;
    private StoreHousePanel store;
    private int tic=0;
    private ArrayList<Vegdata> VegdataSave = new ArrayList<>();
    private ArrayList<Vegdata> VegdataLoad = new ArrayList<>();
    private ArrayList<Anidata> AnidataSave = new ArrayList<>();
    private ArrayList<Anidata> AnidataLoad = new ArrayList<>();
    private ArrayList<Landdata> LanddataSave = new ArrayList<>();
    private ArrayList<Landdata> LanddataLoad = new ArrayList<>();
    private ArrayList<HaveAniData> HaveAnidataSave = new ArrayList<>();
    private ArrayList<HaveAniData> HaveAnidataLoad = new ArrayList<>();
    public Image car,house;
    public boolean emptyVeg = true;
    public boolean emptyAni = true;
    private float pxCar = 0, pyCar = 0;
    // level up
    private Group groupPanelLvUp;
    private Label lb,lbLevle;


    @Override
    public void dispose() {

    }

    @Override
    public void init() {
        SoundEffect.Playmusic(1);

        initTexture();
        initFont();
        initGroup();
        initUI();
        initPanelLvUp();
        updateLvSc();
        if(GMain.prefs.getBoolean("checkFirst")==false) {
            new tutorial(fotter, this);
        }else {
            scroll.fling(0,0,0);
            scroll.layout();
            scroll.setScrollPercentX(0.335f);
            scroll.setScrollPercentY(0.2f);
            scroll.updateVisualScroll();
            scroll.setFlingTime(0);
        }

//
//        if(Tutorial2.step == 0){
//            Tutorial2.ShowTutorial(0, fotter, this);
//        }

    }

    private void initTexture(){

        atlas = GAssetsManager.getTextureAtlas("uiGame.atlas");
        atlasStore = TextureAtlasC.storeHouse;
    }

    private void initGroup(){
        mainGroup = new GLayerGroup();
        groupPanelLvUp = new Group();
        GStage.addToLayer(GLayer.ui, mainGroup);
        GStage.addToLayer(GLayer.ui, groupScroll);


    }


    private void initUI(){
        renderListView();
        createEdge();
        createEdgeAni();
        createSlotAni();
        ///////// load frame avt //////
        info= new Info(TextureAtlasC.atlasInfo);
        ////////// load footer////////
        fotter = new Fotter(this,font);
        LoadLv(Config.Level);
        fotter.checkLock();

        /////// show Store house //////
        house = GUI.createImage(atlasStore,"house");
        house.setWidth(house.getWidth()*2f);
        house.setHeight(house.getHeight()*2f);
        house.setOrigin(Align.center);
        house.setPosition(GStage.getWorldWidth()*1.1f,120,Align.center);
        groupScroll.addActor(house);
        store = new StoreHousePanel(this);
        house.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                SoundEffect.Play(SoundEffect.click);
                Config.Stepindex=5;
                store.showStoreHouse();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        ////// save data/////
        if(GMain.prefs.getBoolean("checkFirst")==true){
            LoadData();
            info.updateMonney(Config.Money);
        }
        saveAllGame();


        /////// car/////////
        car = GUI.createImage(atlasStore, "car");
        pxCar = house.getX() + house.getWidth()*1.2f;
        pyCar = house.getY() + house.getHeight()*0.8f;
        car.setPosition(pxCar, pyCar);
        groupScroll.addActor(car);
        ///////// rank Top///////
        Rank();

    }
    private void initPanelLvUp(){
        GStage.addToLayer(GLayer.top, groupPanelLvUp);
        groupPanelLvUp.setVisible(false);
        GShapeSprite gs = new GShapeSprite();
        gs.createRectangle(true, 0, 0, Config.WidthScreen*3, Config.HeightScreen);
        gs.setColor(0, 0, 0, 0.4f);
        groupPanelLvUp.addActor(gs);
        String type = "frmLevelUpVn";
        if(C.lang.idcontry.equals("En")){
            type = "frmLevelUpEn";
        }
        Image panelLvUd = GUI.createImage(TextureAtlasC.atlasInfo, type);
        Image btnNhan = GUI.createImage(TextureAtlasC.atlasInfo, "btnNhan");
        Label lbNhan = new Label(C.lang.lbNhan,new Label.LabelStyle(BitmapFontC.robotoVi,null));
        lbNhan.setAlignment(Align.center);
        lbNhan.setPosition(btnNhan.getX()+btnNhan.getWidth()/2,btnNhan.getY()+btnNhan.getHeight()/2,Align.center);
        groupPanelLvUp.addActor(lbNhan);
        lb = new Label("0", new Label.LabelStyle(BitmapFontC.NerwynGrey, null));

        groupPanelLvUp.addActor(panelLvUd);
        groupPanelLvUp.addActor(btnNhan);
        groupPanelLvUp.addActor(lb);
        lb.setFontScale(1.5f);
        panelLvUd.setPosition(Config.WidthScreen/2, Config.HeightScreen/2, Align.center);
        btnNhan.setPosition(panelLvUd.getX() + panelLvUd.getWidth()/2, panelLvUd.getY() + panelLvUd.getHeight()*0.82f, Align.center);
        lb.setPosition(Config.WidthScreen/2, Config.HeightScreen*0.59f, Align.center);

        lbLevle = new Label(""+Config.Level,new Label.LabelStyle(BitmapFontC.robotoVi, Color.BROWN));
        lbLevle.setFontScale(1.5f);
        lbLevle.setOrigin(Align.center);
        lbLevle.setAlignment(Align.center);
        lbLevle.setPosition(GStage.getWorldWidth()/2,panelLvUd.getY()+80,Align.center);
        groupPanelLvUp.addActor(lbLevle);
        eventPanelLvUp(btnNhan);

    }

    public void showPanelLvUp(boolean isShow){
        SoundEffect.Play(SoundEffect.levelup);
        lb.setText(Config.Level*1000);
        lbLevle.setText(""+Config.Level);
        lb.setWidth(lb.getPrefWidth());
        lb.setPosition(Config.WidthScreen/2, Config.HeightScreen*0.59f, Align.center);
        groupPanelLvUp.setVisible(isShow);
        LoadLv(Config.Level);
        fotter.checkLock();

    }

    //updateHere
    private void eventPanelLvUp(Image img){
        img.setOrigin(Align.center);
        img.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                SoundEffect.Play(SoundEffect.click);
                img.addAction(Actions.sequence(
                        Actions.scaleBy(-0.3f, -0.3f, 0.2f, Interpolation.swingIn),
                        Actions.scaleBy(0.3f, 0.3f, 0.2f, Interpolation.swingOut),
                        Actions.run(()->{
                            groupPanelLvUp.setVisible(false);
//                            showPanelLvUp(false);
                            Config.Money += Config.Level*1000;
                            setMoney(Config.Money);
                        })
                ));
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }
    public void aniCar(Runnable runnable){
        car.addAction(Actions.sequence(
                Actions.moveTo(-car.getWidth(), car.getY(), 4, Interpolation.linear),
                Actions.delay(10),
                Actions.run(()->{
                    car.setX(Config.WidthScreen*3 + car.getWidth());
                    car.addAction(Actions.sequence(
                            Actions.moveTo(pxCar, pyCar, 4, Interpolation.linear),
                            Actions.run(runnable)
                    ));
                })
        ));
    }
    private void LoadLv(int Lv){
        int st0 = Config.LoadLv(Lv).getStatus0()[0];
        int st00 = Config.LoadLv(Lv).getStatus0()[1];

        int st1 = Config.LoadLv(Lv).getStatus1()[0];
        int st11 = Config.LoadLv(Lv).getStatus1()[1];

        int st2 = Config.LoadLv(Lv).getStatus2()[0];
        int st22 = Config.LoadLv(Lv).getStatus2()[1];
        if(arrLand.size!=0){
            for (int i = st0; i <= st00; i++){
                arrLand.get(i).Status = 0;
                arrLand.get(i).LoadByStatus();
            }
            for (int i1 = st1; i1<= st11; i1++){
//                if(arrLand.get(i1).Status!=-1||arrLand.get(i1).Status!=3||arrLand.get(i1).Status!=4){
                    arrLand.get(i1).Status = 1;
                    arrLand.get(i1).LoadByStatus();
//                }
            }
            for (int i2 = st2; i2 <= st22 ;i2++){
                if(arrLand.get(i2).Status!=-1&&arrLand.get(i2).Status!=3&&arrLand.get(i2).Status!=4){
                    arrLand.get(i2).Status = 2;
                    arrLand.get(i2).LoadByStatus();
                }else {

                    arrLand.get(i2).LoadByStatus();

                }
            }
        }

    }

    private void renderListView(){
        bg = GUI.createImage(atlas, "bg");
        bg.setWidth(bg.getWidth()*(Config.ratioX+0.15f));
        bg.setHeight(bg.getHeight()*(Config.ratioY+0.15f));
        bg.setOrigin(Align.center);
        bg.setPosition(-50,-50,Align.bottomLeft);
        groupScroll.setWidth(bg.getWidth()-100);
        groupScroll.setHeight(bg.getHeight()-100);
        groupScroll.setOrigin(Align.center);
        groupScroll.addActor(bg);
        container = new Table();
        container.setSize(GStage.getWorldWidth(), GStage.getWorldHeight());
        container.setPosition(0,0);
        mainGroup.addActor(container);
        Table table = new Table();
        scroll = new ScrollPane(table);
        scroll.pack();
        scroll.setTransform(true);
        container.add(scroll);
        table.add(groupScroll);




        //scroll.setFlingTime(0);

//        scroll.setVelocityX(0.1f);

        ////////// landPlot //////
        float paddingX=120;
        int paddingY=0;
        for(int i=0 ;i<18;i++){
            if(i==0) {
                paddingY = 0;
                paddingX = bg.getWidth() / 3+100;
            }
            else if(i==6){
                paddingY=0;
                paddingX = bg.getWidth()*2/3+70;

            }else if(i==12){
                paddingY=0;
                paddingX = 120;
            }
            for (int j=0;j<5;j++){
                land = new land(atlas,groupScroll,paddingX,paddingY,i,j,-1,this);
                arrLand.add(land);

            }
            paddingY++;

        }
        for(int i=0;i<arrLand.size;i++){
            arrLand.get(i).index=i+1;
            arrLand.get(i).setPrice();
            arrVegatable.add(null);
            Image overlap = GUI.createImage(atlas,"overlapland");
            overlap.setPosition(arrLand.get(i).body.getX()+arrLand.get(i).body.getWidth()/2,arrLand.get(i).body.getY()+arrLand.get(i).body.getHeight()/2,Align.center);
            groupScroll.addActor(overlap);
            arrOverLap.add(overlap);
            overlap.setVisible(false);
        }
    }

    public void createNewVegtbl(int id, String kind){
        int index =-1;
        for (land ob : arrLand){
            if(ob.Status==-1){
               index = arrLand.indexOf(ob,true);
                break;
            }
        }
        if(index!=-1){
            emptyVeg=true;
            SoundEffect.Play(SoundEffect.createNew);
            float x = arrLand.get(index).body.getX()+arrLand.get(index).body.getWidth()/2;
            float y = arrLand.get(index).body.getY()+arrLand.get(index).body.getHeight()/2;
            vegetable = new vegetable(atlas,groupScroll,this,x,y,kind,id,-1);
            vegetable.Vgtable.addAction(Actions.sequence(
                    Actions.scaleTo(1.2f,1.2f,0.2f),
                    Actions.scaleTo(1f,1f,0.2f),
                    GSimpleAction.simpleAction((d,a)->{
                        return true;
                    })
            ));
            vegetable.addDrag();
            arrVegatable.set(index,vegetable);

            arrLand.get(index).Status = 4;
        }else {
            emptyVeg=false;

        }

    }


    private void createEdge(){
        EdgeX.add(scroll.getScrollX()+100,(scroll.getScrollX()+GStage.getWorldWidth())-200);
        EdgeY.add(scroll.getScrollY(),(scroll.getScrollY()+GStage.getWorldHeight())-100);
    }
    public void checkEdge(float x,float y){
//        System.out.println(scroll.getScrollY());
//        System.out.println(scroll.getScrollX());
        if(x<=EdgeX.x) {
            scroll.scrollTo(scroll.getScrollX() - 10, EdgeY.x, bg.getWidth(), bg.getHeight());
            updateEdgeX(EdgeX.x - 10, EdgeX.y - 10);
        }else if(x>=EdgeX.y){
            scroll.scrollTo(scroll.getScrollX()+10,EdgeY.x,bg.getWidth(),bg.getHeight());
            updateEdgeX(EdgeX.x + 10, EdgeX.y + 10);

        }else if(y<=EdgeY.x){
            scroll.scrollTo(EdgeX.x,EdgeY.x-10,bg.getWidth(),bg.getHeight());
            updateEdgeY(EdgeY.x -10, EdgeY.y - 10);

        }else if(y>=EdgeY.y){
            scroll.scrollTo(EdgeX.x,EdgeY.x+10,bg.getWidth(),bg.getHeight());
            updateEdgeY(EdgeY.x +10, EdgeY.y + 10);
        }
    }
    public void updateEdgeX(float x, float y){
        EdgeX.set(x,y);

    }
    public void updateEdgeY(float x, float y){
        EdgeY.set(x,y);

    }
    public int checkRec(vegetable vegetable){
        for (int i=0;i<arrLand.size;i++){
            if(vegetable!= arrVegatable.get(i)){
                if(vegetable.body.overlaps(arrLand.get(i).body)==true&&arrLand.get(i).Status!=3){
                    return i;
                }
            }
        }
        return -1;
    }
    public void CheckVegtbl(vegetable vegtbl1,int index, Vector2 p1){
        if(arrVegatable.get(index)!=null){
            if(vegtbl1.type.equals(arrVegatable.get(index).type)==true && vegtbl1.id == arrVegatable.get(index).id){
                mergeVegtbl(vegtbl1,arrVegatable.get(index));
            }
            else {
                swapVgtable(arrVegatable,vegtbl1,index,p1);
            }
        }else {
            swapVgtable(arrVegatable,vegtbl1,index,p1);

        }



    }
    public void swapVgtable(Array<vegetable> arrvegtbl, vegetable vegtbl1, int index,Vector2 p1){
        SoundEffect.Play(SoundEffect.Land_press);
        vegetable vegtbl2;
        vegtbl2 = arrVegatable.get(index);
        if(vegtbl2!=null){
            int index1 = arrvegtbl.indexOf(vegtbl1, true);
            int index2 = arrvegtbl.indexOf(vegtbl2, true);
            float rowTemp = vegtbl1.x;
            float colTemp = vegtbl1.y;
            int zIndexTemp = vegtbl1.Vgtable.getZIndex();
            arrvegtbl.swap(index1, index2);
            vegtbl1.x = vegtbl2.x;
            vegtbl1.y = vegtbl2.y;
            vegtbl2.x = rowTemp;
            vegtbl2.y = colTemp;
//                setTouchCards(Touchable.disabled);
            vegtbl1.Vgtable.addAction(Actions.sequence(
                    Actions.moveTo(vegtbl2.Vgtable.getX(), vegtbl2.Vgtable.getY(), 0f, Interpolation.fastSlow),
                    GSimpleAction.simpleAction((d,a)->{
                        vegtbl1.body.setX(vegtbl2.Vgtable.getX());
                        vegtbl1.body.setY(vegtbl2.Vgtable.getY());
                        vegtbl1.gr.setX(vegtbl1.Vgtable.getX()+vegtbl1.Vgtable.getWidth()/2);
                        vegtbl1.gr.setY(vegtbl1.Vgtable.getY()+vegtbl1.Vgtable.getHeight()/2+30);
                        return true;
                    })

            ));

            vegtbl2.Vgtable.addAction(Actions.sequence(
                    Actions.moveTo(p1.x, p1.y, 0f, Interpolation.fastSlow),
                    GSimpleAction.simpleAction((d, a)->{
                        vegtbl2.body.setX(p1.x);
                        vegtbl2.body.setY(p1.y);
                        vegtbl2.gr.setX(vegtbl2.Vgtable.getX()+vegtbl2.Vgtable.getWidth()/2);
                        vegtbl2.gr.setY(vegtbl2.Vgtable.getY()+vegtbl2.Vgtable.getHeight()/2+30);

//                            setTouchCards(Touchable.enabled);
                        return true;
                    })
            ));


            vegtbl1.Vgtable.setZIndex(vegtbl2.Vgtable.getZIndex());
            vegtbl2.Vgtable.setZIndex(zIndexTemp);
        }else {
            int index1 = arrvegtbl.indexOf(vegtbl1, true);
            int StatusTemp=0;
            vegtbl1.x = arrLand.get(index).body.getX()+arrLand.get(index).body.getWidth()/2;
            vegtbl1.y = arrLand.get(index).body.getY()+arrLand.get(index).body.getHeight()/2;
            arrvegtbl.swap(index1, index);
            StatusTemp = arrLand.get(index).Status;
            arrLand.get(index).Status = arrLand.get(index1).Status;
            arrLand.get(index1).Status = StatusTemp;
//                setTouchCards(Touchable.disabled);
            vegtbl1.Vgtable.addAction(Actions.sequence(
                    Actions.moveTo(arrLand.get(index).body.getX()-(arrLand.get(index).body.getWidth()*3/4), arrLand.get(index).body.getY()-(arrLand.get(index).body.getHeight()*3/4), 0f, Interpolation.fastSlow),
                    GSimpleAction.simpleAction((d,a)->{
                        vegtbl1.body.setX(arrLand.get(index).body.getX()-(arrLand.get(index).body.getWidth()*3/4));
                        vegtbl1.body.setY(arrLand.get(index).body.getY()-(arrLand.get(index).body.getHeight()*3/4));
                        setDefaultLand(index);
                        return true;
                    })

            ));

        }


    }
    public void mergeVegtbl(vegetable vegtbl1,vegetable vegtbl2){
        Config.Stepindex=3;
        emptyVeg=true;
        SoundEffect.Play(SoundEffect.merge);
        store.addQuantityItem(setID(vegtbl1.type),vegtbl1.harvest);
        vegtbl1.Harvest();
        store.addQuantityItem(setID(vegtbl2.type),vegtbl2.harvest);
        vegtbl2.Harvest();
        store.saveData();
        float x = vegtbl2.x;
        float y = vegtbl2.y;
        int index1 = arrVegatable.indexOf(vegtbl1,true);
        int index2 = arrVegatable.indexOf(vegtbl2,true);
        String type = vegtbl1.type;
        int id = vegtbl1.id;
        int zindex =vegtbl2.Vgtable.getZIndex();
        if(vegtbl1.id<10)
            id = vegtbl1.id+1;
        vegtbl1.dispose();
        vegtbl2.dispose();
        //////  create new vegtable ////
        vegetable vegtbl = new vegetable(atlas,groupScroll,this,x,y,type,id,-1);
        vegtbl.addDrag();
        arrVegatable.set(index1,null);
        arrVegatable.set(index2,vegtbl);
        arrLand.get(index1).Status = -1;
        arrLand.get(index2).Status = 4;
        vegtbl.Vgtable.setZIndex(zindex);
        vegtbl.Vgtable.addAction(Actions.sequence(
                Actions.scaleTo(1.2f,1.2f,0.2f),
                Actions.scaleTo(1f,1f,0.2f)
        ));
        ///// particle///////
        effectWin ef = new effectWin(2,x,y);
        groupScroll.addActor(ef);
        ef.start();

    }
    public void setVisibleOverlap(boolean set){
        for (Image ob : arrOverLap){
            if(arrLand.get(arrOverLap.indexOf(ob,true)).Status==3||arrLand.get(arrOverLap.indexOf(ob,true)).Status==-1){
                ob.setVisible(set);
//                System.out.println("overLap: "+ arrOverLap.indexOf(ob,true));
            }
        }
    }
    public void setVisibleIndex(int index){
        for (Image ob : arrOverLap){
            if(arrOverLap.indexOf(ob,true)!=index){
                if(arrLand.get(arrOverLap.indexOf(ob,true)).Status==3||arrLand.get(arrOverLap.indexOf(ob,true)).Status==-1){
                    ob.setVisible(true);
                }
                arrLand.get(arrOverLap.indexOf(ob,true)).land.setScale(1);
            }else{
                if(arrLand.get(arrOverLap.indexOf(ob,true)).Status==3||arrLand.get(arrOverLap.indexOf(ob,true)).Status==-1){
                    ob.setVisible(false);
                }
                if(arrVegatable.get(index)==null){
                    arrLand.get(index).land.setScale(1.1f);
                }
            }
        }
    }

    public void setDefaultLand(int index){
//        System.out.println("check:======="+index);
        arrLand.get(index).land.setScale(1);

    }
    public int checkEdgeAnimal(int index){
        if(arrAniEdge!=null){
            for (int i=0;i<arrAniEdge.size;i++){
                if(index==arrAniEdge.get(i)){
                    return 1;
                }
            }
        }

        return -1;
    }
    private void createEdgeAni(){
        int arr[] = {4,9,14,19,24,25,26,27,28,29,34,39,44,49,54,55,56,57,58,59,64,69,74,79,84,85,86,87,88,89};
        for (int i=0;i<arr.length;i++){
            arrAniEdge.add(arr[i]);
        }
    }
    private Array<Integer> checkHaveSlot(int index){
        Array<Integer> arr = new Array();
        arr.add(index);
        arr.add(index+1);
        arr.add(index+5);
        arr.add(index+6);

        return arr;
    }
    private void createSlotAni(){
        for (int i=0;i<arrVegatable.size;i++){
            if(checkEdgeAnimal(i)==-1){
                arrAniIndex.add(checkHaveSlot(i));
            }

        }
    }
    public int checkEmptySlot(){
        int dem=0;
        int dem2=0;
        for (int ii=0;ii<arrAniIndex.size;ii++){
            for(int i=0;i<4;i++){
                if(arrLand.get(arrAniIndex.get(ii).get(i)).Status==-1){
                    dem++;
                }
                if(arrHaveAni.size!=0){
                    for(int i2=0;i2<arrHaveAni.size;i2++){
                        if(arrHaveAni.get(i2)==arrAniIndex.get(ii).get(i))
                        {
                            dem2++;
                        }
                    }
                }
            }
            if(dem == 4 && dem2==0){
                return ii;
            }else {
                dem=0;
                dem2=0;
            }

        }
        return -1;
    }
    /////////// create animals //////////
    public void createNewAni(int id,String Type){
            SoundEffect.Play(SoundEffect.createNew);
            int index = arrAniIndex.get(checkEmptySlot()).get(0);
            int index2= checkEmptySlot();
            float x = arrLand.get(index).land.getX()-5;
            float y = arrLand.get(index).land.getY()-10;
            ani = new animals(atlas,groupScroll,this,x,y,Type,id,index,-1);
            ani.addDrag();
            arrAnimals.add(ani);
            for (int i=0;i<4;i++){
                arrHaveAni.add(arrAniIndex.get(index2).get(i));
                arrLand.get(arrAniIndex.get(index2).get(i)).Status=3;
            }


    }
    public Array<Integer> CheckRecAni(animals animals){
        int dem=0;
        Array<Integer> arr = new Array<>();
        for(int i=0;i<arrLand.size;i++){
            if(animals.body.overlaps(arrLand.get(i).body)==true&&arrLand.get(i).Status==-1){
                dem++;
                arr.add(i);
                if(dem==4){
                   return arr;
                }
            }
        }
        return new Array<>();
    }
    public void StatusDragAni(animals ani,int set){
        int index =0;
        for(int i=0;i<arrAniIndex.size;i++){
            if(ani.index==arrAniIndex.get(i).get(0)){
                index=i;
            }
        }
        for(int i=0;i<4;i++){
           int index2 = arrAniIndex.get(index).get(i);
            arrLand.get(index2).Status=set;

        }

    }

    public void setVisibleOverlap(Array<Integer> arr){
        setVisibleOverlap(true);
        for (int i=0;i<arr.size;i++){
            arrOverLap.get(arr.get(i)).setVisible(false);

        }

    }
    public void SwapAni(Array<Integer> arr, animals ani){
        SoundEffect.Play(SoundEffect.Land_press);
        float x = arrLand.get(arr.get(0)).land.getX()-5;
        float y = arrLand.get(arr.get(0)).land.getY()-10;
        int index =arrAnimals.indexOf(ani,true);
        int indexLand1=0;
        int indexLand2=0;
        for (int i=0;i<arrAniIndex.size;i++){
            if(arrAniIndex.get(i).get(0)==arrAnimals.get(index).index){
                indexLand1=i;
            }
            if(arrAniIndex.get(i).get(0)==arr.get(0)){
                indexLand2=i;
            }
        }
        ani.animal.addAction(Actions.moveTo(x,y));
        ani.Xmove = x;
        ani.Ymove = y;
        for (int i=0;i<arrHaveAni.size;i++){
            for (int i1=0;i1<4;i1++){
                if(arrHaveAni.get(i)==arrAniIndex.get(indexLand1).get(i1)){
                    arrHaveAni.set(i,arrAniIndex.get(indexLand2).get(i1));
                }
            }
        }
        ani.index=arr.get(0);
        for (int i=0;i<4;i++){
            arrLand.get(arrAniIndex.get(indexLand1).get(i)).Status=-1;
        }
        for (int i=0;i<4;i++){
            arrLand.get(arrAniIndex.get(indexLand2).get(i)).Status=3;
        }
    }
    public int CheckOverLapAni(animals ani){
        if(arrAnimals.size!=0){
            for (int i=0;i<arrAnimals.size;i++){
                if(ani!=arrAnimals.get(i)){
                    if(ani.body.overlaps(arrAnimals.get(i).body)){
                        return i;
                    }
                }
            }
        }
        return -1;
    }
    public void SwapTwoAni(animals ani, int index){
        int indexLandRemove=0;
        int indexAni1 = arrAnimals.indexOf(ani, true);
        int indexLand = arrAnimals.get(index).index;
        if(ani.id==arrAnimals.get(index).id && ani.type.equals(arrAnimals.get(index).type)==true){
            SoundEffect.Play(SoundEffect.merge);
                store.addQuantityItem(setID(ani.type),ani.harvest);
                ani.Harvest();
                store.addQuantityItem(setID(arrAnimals.get(index).type),arrAnimals.get(index).harvest);
                arrAnimals.get(index).Harvest();
                store.saveData();
                float x = arrAnimals.get(index).animal.getX();
                float y = arrAnimals.get(index).animal.getY();
                String type = arrAnimals.get(index).type;
                int id = arrAnimals.get(index).id+1;
                if(id>10)
                    id=10;
                arrAnimals.get(index).dispose();
                arrAnimals.get(indexAni1).dispose();
                animals aniNew = new animals(atlas,groupScroll,this,x,y,type,id,indexLand,-1);
                aniNew.addDrag();
                aniNew.animal.addAction(Actions.sequence(
                        Actions.scaleTo(1.1f,1.1f,0.2f),
                        Actions.scaleTo(1f,1f,0.2f)
                ));
                for (int i=0;i<arrAniIndex.size;i++) {
                    if (arrAniIndex.get(i).get(0) == arrAnimals.get(arrAnimals.indexOf(ani, true)).index) {
                        indexLandRemove = i;
                    }
                }
                for (int i=0;i<4;i++){
                    arrLand.get(arrAniIndex.get(indexLandRemove).get(i)).Status=-1;
                }
                for (int i=0;i<arrHaveAni.size;i++){
                    for (int i1=0;i1<4;i1++){
                        if(arrHaveAni.get(i)==arrAniIndex.get(indexLandRemove).get(i1)){
                            arrHaveAni.removeIndex(i);
                        }
                    }
                }

                arrAnimals.set(index,aniNew);
                arrAnimals.removeIndex(indexAni1);
                ///// particle///////
                effectWin ef = new effectWin(2,x+aniNew.animal.getWidth()/2,y+aniNew.animal.getHeight()/2);
                groupScroll.addActor(ef);
                ef.start();

            }else {
            SoundEffect.Play(SoundEffect.Land_press);
            int ani1 = arrAnimals.indexOf(ani,true);
            int ani2 = index;
            float x1 = arrAnimals.get(ani1).Xmove;
            float y1 = arrAnimals.get(ani1).Ymove;
            float x2 = arrAnimals.get(ani2).Xmove;
            float y2 = arrAnimals.get(ani2).Ymove;
            arrAnimals.get(ani1).MoveAni(x2,y2);
            arrAnimals.get(ani2).MoveAni(x1,y1);
           arrAnimals.get(ani1).Xmove=x2;
            arrAnimals.get(ani1).Ymove=y2;
            arrAnimals.get(ani2).Xmove=x1;
            arrAnimals.get(ani2).Ymove=y1;
            SwapValue(arrAnimals.get(ani1),arrAnimals.get(ani2));
            arrAnimals.swap(ani1,ani2);
            StatusDragAni(arrAnimals.get(ani1),3);
            StatusDragAni(arrAnimals.get(ani2),3);

        }

    }
    public void ScaleAniChose(int index){
        if(arrAnimals.size>2){
            for(int i=0;i<arrAnimals.size;i++){
                if(i==index){
                    arrAnimals.get(i).animal.setScale(1.1f);
                }else {
                    arrAnimals.get(i).animal.setScale(1f);
                }
            }
        }
    }
    public void ScaleDefault(){
        if(arrAnimals.size!=0){
            for (animals ani: arrAnimals){
                ani.animal.setScale(1);
            }
        }
    }
    public void SwapValue(animals ani1, animals ani2){
        int indexTemp = ani1.index;
        ani1.index = ani2.index;
        ani2.index = indexTemp;
    }
    public void setTouchVeg(Touchable touchable ,vegetable veg ){
        int index = arrVegatable.indexOf(veg,true);
        for (int i=0;i<arrVegatable.size;i++){
            if(arrVegatable.get(i)!=null&&index!=i){
                arrVegatable.get(i).Vgtable.setTouchable(touchable);
            }
        }
        if(arrAnimals.size!=0)
        for(int i=0;i<arrAnimals.size;i++){
            arrAnimals.get(i).animal.setTouchable(touchable);
        }

    }
    public void setTouchVeg2(Touchable touchable){
        for (int i=0;i<arrVegatable.size;i++){
            if(arrVegatable.get(i)!=null){
                arrVegatable.get(i).Vgtable.setTouchable(touchable);
            }
        }
        if(arrAnimals.size!=0)
            for(int i=0;i<arrAnimals.size;i++){
                arrAnimals.get(i).animal.setTouchable(touchable);
            }

    }
    public void setTouchAni(Touchable touchable ,animals ani ){
        int index = arrAnimals.indexOf(ani,true);
        for (int i=0;i<arrAnimals.size;i++){
            if(arrAnimals.get(i)!=null&&index!=i){
                arrAnimals.get(i).animal.setTouchable(touchable);
            }
        }
        if(arrVegatable.size!=0)
            for(int i=0;i<arrVegatable.size;i++){
                if(arrVegatable.get(i)!=null)
                    arrVegatable.get(i).Vgtable.setTouchable(touchable);
            }

    }
    public void setTouchAni2(Touchable touchable){
        for (int i=0;i<arrAnimals.size;i++){
            if(arrAnimals.get(i)!=null){
                arrAnimals.get(i).animal.setTouchable(touchable);
            }
        }
        if(arrVegatable.size!=0)
            for(int i=0;i<arrVegatable.size;i++){
                if(arrVegatable.get(i)!=null)
                    arrVegatable.get(i).Vgtable.setTouchable(touchable);
            }

    }
    public void HarvestAniAndVeg(){
        house.setOrigin(Align.top);
        house.addAction(Actions.sequence(
                Actions.scaleTo(1.1f,0.9f,0.2f),
                Actions.scaleTo(1f,1f,0.2f),
                Actions.scaleTo(1.1f,0.9f,0.2f),
                Actions.scaleTo(1f,1f,0.2f),
                Actions.scaleTo(1.1f,0.9f,0.2f),
                Actions.scaleTo(1f,1f,0.2f)
        ));
        SoundEffect.Play(SoundEffect.harvest);
        for (vegetable veg : arrVegatable){
            if(veg!=null&& veg.harvest!=0){
                Config.Stepindex=4;
                store.addQuantityItem(setID(veg.type),veg.harvest);
                veg.Harvest();
            }
        }
        for(animals ani : arrAnimals ){
            if(ani.harvest!=0){
                store.addQuantityItem(setID(ani.type),ani.harvest);
                ani.Harvest();
            }
        }
        store.saveData();
    }
    public int setID(String Type){
        if(Type.equals("lua"))
            return 0;
        if(Type.equals("khoai"))
            return 2;
        if(Type.equals("cachua"))
            return 1;
        if(Type.equals("bap"))
            return 5;
        if(Type.equals("leo"))
            return 4;
        if(Type.equals("bi"))
            return 9;
        if(Type.equals("dua"))
            return 12;
        if(Type.equals("cai"))
            return 8;
        if(Type.equals("thom"))
            return 10;
        if(Type.equals("carot"))
            return 6;
        if(Type.equals("cuu"))
            return 13;
        if(Type.equals("bo"))
            return 3;
        if(Type.equals("ga"))
            return 7;
        if(Type.equals("de"))
            return 11;
        return 0;
    }
    public String setIcon(String Type){
        if(Type.equals("lua"))
            return "icon1";
        if(Type.equals("khoai"))
            return "icon3";
        if(Type.equals("cachua"))
            return "icon2";
        if(Type.equals("bap"))
            return "icon5";
        if(Type.equals("leo"))
            return "icon4";
        if(Type.equals("bi"))
            return "icon8";
        if(Type.equals("dua"))
            return "icon10";
        if(Type.equals("cai"))
            return "icon7";
        if(Type.equals("thom"))
            return "icon9";
        if(Type.equals("carot"))
            return "icon6";
        if(Type.equals("cuu"))
            return "icc4";
        if(Type.equals("bo"))
            return "icc1";
        if(Type.equals("ga"))
            return "icc2";
        if(Type.equals("de"))
            return "icc3";
        return "";
    }
    public void MoveFotter(int type){
        fotter.ActionsGr(type);
    }
    public void setMoney(long money){
        info.updateMonney(money);
    }
    private void saveAllGame(){
        mainGroup.addAction(
                GSimpleAction.simpleAction((d,a)->{
                    tic++;
                    if(tic==60){
                        tic=0;
                        SaveData();
                    }
                    return false;
                })
        );
    }
    private void SaveData(){

        Gson gson = new Gson();
        ////// vegtable //////
        if(VegdataSave.size()!=0){
            VegdataSave.clear();
        }
        for(vegetable veg : arrVegatable){
            Vegdata data = new Vegdata();
            if(veg!=null){
                data.setX(veg.x);
                data.setY(veg.y);
                data.setType(veg.type);
                data.setId(veg.id);
                data.setIndex(arrVegatable.indexOf(veg,true));
                data.setSecond(veg.second);
                VegdataSave.add(data);
            }
        }
        String S = gson.toJson(VegdataSave);
        GMain.prefs.putString("arrVeg",S);
        /////// animals///////
        if(AnidataSave.size()!=0){
            AnidataSave.clear();
        }
        for(animals ani : arrAnimals){
            Anidata data = new Anidata();
            if(ani!=null){
                data.setX(ani.Xmove);
                data.setY(ani.Ymove);
                data.setType(ani.type);
                data.setId(ani.id);
                data.setIndex(ani.index);
                data.setSecond(ani.second);
                AnidataSave.add(data);
            }
        }
        String S2 = gson.toJson(AnidataSave);
        GMain.prefs.putString("arrAni",S2);
        //////// arrLand////////
        if(LanddataSave.size()!=0){
            LanddataSave.clear();
        }
        for(land land : arrLand){
            Landdata data = new Landdata();
            if(land!=null){
                data.setStatus(land.Status);
                LanddataSave.add(data);
            }
        }
        String S3 = gson.toJson(LanddataSave);
        GMain.prefs.putString("arrLand",S3);
        /////// arr have animails//////
        if(HaveAnidataSave.size()!=0){
            HaveAnidataSave.clear();
        }
        for(Integer ob : arrHaveAni){
            HaveAniData data = new HaveAniData();
            if(ob!=null){
                data.setIndex(ob);
                HaveAnidataSave.add(data);
            }
        }
        String S4 = gson.toJson(HaveAnidataSave);
        GMain.prefs.putString("arrHaveAni",S4);
        /////// save money //////

        GMain.prefs.putLong("Money",Config.Money);
        GMain.prefs.putBoolean("checkFirst",true);
        GMain.prefs.flush();



    }
    private void LoadData(){
        /////// vegtable ///////
        Gson gson = new Gson();
        String S = GMain.prefs.getString("arrVeg");
        JsonArray arr = gson.fromJson(S, JsonArray.class);
        for (JsonElement ob : arr){
            Vegdata dt = gson.fromJson(ob,Vegdata.class);
            VegdataLoad.add(dt);
        }
        for (Vegdata dt2 : VegdataLoad){
            vegetable = new vegetable(atlas,groupScroll,this,dt2.getX(),dt2.getY(),dt2.getType(),dt2.getId(),dt2.getSecond());
            vegetable.addDrag();
            arrVegatable.set(dt2.getIndex(),vegetable);
        }
        /////// animals ////////
        String S2 = GMain.prefs.getString("arrAni");
        JsonArray arr2 = gson.fromJson(S2, JsonArray.class);
        for (JsonElement ob : arr2){
            Anidata dt = gson.fromJson(ob,Anidata.class);
            AnidataLoad.add(dt);
        }
        for(Anidata dt2 : AnidataLoad){
            ani = new animals(atlas,groupScroll,this,dt2.getX(),dt2.getY(),dt2.getType(),dt2.getId(),dt2.getIndex(),dt2.getSecond());
            ani.addDrag();
            arrAnimals.add(ani);
        }
        //////// Land ///////
        String S3 = GMain.prefs.getString("arrLand");
        JsonArray arr3 = gson.fromJson(S3, JsonArray.class);
        for (JsonElement ob : arr3){
            Landdata dt = gson.fromJson(ob,Landdata.class);
            LanddataLoad.add(dt);
        }
        for(Landdata dt2: LanddataLoad){
            arrLand.get(LanddataLoad.indexOf(dt2)).Status=dt2.getStatus();
            arrLand.get(LanddataLoad.indexOf(dt2)).LoadByStatus();
        }
        //////// array have animals//////
        String S4 = GMain.prefs.getString("arrHaveAni");
        JsonArray arr4 = gson.fromJson(S4, JsonArray.class);
        for (JsonElement ob : arr4){
            HaveAniData dt = gson.fromJson(ob,HaveAniData.class);
            HaveAnidataLoad.add(dt);
        }
        for(HaveAniData dt2: HaveAnidataLoad){
            arrHaveAni.add(dt2.getIndex());
        }
        /////// load money //////
        Config.Money = GMain.prefs.getLong("Money");

    }



    private void initFont(){
        font = GAssetsManager.getBitmapFont("font_name_bot.fnt");
    }

    public void updateLvSc(){
        info.updateLvSc();
    }
    private void Rank(){
        Image btnRank = GUI.createImage(TextureAtlasC.atlasInfo,"btnRank");
        btnRank.setOrigin(Align.center);
        btnRank.setPosition(GStage.getWorldWidth()*1.78f,450);
        groupScroll.addActor(btnRank);
        btnRank.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                btnRank.setTouchable(Touchable.disabled);
                btnRank.addAction(Actions.sequence(
                        Actions.scaleTo(0.9f,0.9f,0.1f),
                        Actions.scaleTo(1f,1f,0.1f),
                        GSimpleAction.simpleAction((d,a)->{
                            HttpRequests.getInstance().getAllMoney();
                            new Score(btnRank);
                            return true;
                        })
                ));
            }
        });
    }




    @Override
    public void run() {

    }
}