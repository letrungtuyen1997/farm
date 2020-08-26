package com.ss.object;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.ss.commons.TextureAtlasC;
import com.ss.commons.Tweens;
import com.ss.core.action.exAction.GArcMoveToAction;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.effects.SoundEffect;
import com.ss.effects.effectWin;
import com.ss.scenes.GameScene;

public class vegetable {
    private Group groupTime = new Group();
    private Group group;
    private TextureAtlas atlas;
    public Image Vgtable, land2;
    private GameScene gameScene;
    public Rectangle body;
    final GShapeSprite blackOverlay = new GShapeSprite();
    public String type;
    public int id;
    public float x,y;
    private boolean isDrag= false;
    public int target=0;
    public int harvest=0;
    public int second =0;
    private InfoAniVeg info;
    private boolean isCountDown = false;
    private int tic=0;
    private int count =0;
    private int checkOn=0;
    private Array<Image> arrHarvest = new Array<>();
    public Group gr = new Group();



    public vegetable(TextureAtlas atlas, Group group, GameScene gameScene, float x, float y, String type, int id,int Second){
        this.atlas = atlas;
        this.group = group;
        this.gameScene = gameScene;
        this.type=type;
        this.id = id;
        this.x=x;
        this.y=y;

        Vgtable = GUI.createImage(atlas,type+id);
        Vgtable.setPosition(x,y, Align.center);
        Vgtable.setOrigin(Align.center);
        group.addActor(Vgtable);
        body = new Rectangle(Vgtable.getX(),Vgtable.getY(),Vgtable.getWidth()/2,Vgtable.getHeight()/2);
        body.setPosition(Vgtable.getX()*(0.25f),Vgtable.getY()*(0.25f));

//        blackOverlay.createRectangle(true, body.getX(),body.getY(), body.getWidth(), body.getHeight());
//        blackOverlay.setColor(0,0,0,0.5f);
//        group.addActor(blackOverlay);

        target = 5+((id-1)*10);
        if(Second==-1){
            second = target*(11-id);


        }else {
            this.second = Second;
            harvest = target-(this.second/(11-id));
        }

        ///// count down /////
        CountDown();
        effectWin ef = new effectWin(1,0,0);
        gr.addActor(ef);
        gr.setPosition(x,y+30,Align.center);
        group.addActor(gr);
        if(second==0){
            setUpTime();
        }
    }
    public void addDrag(){
        Vgtable.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                SoundEffect.Play(SoundEffect.pop_up);
                gameScene.setTouchVeg(Touchable.disabled,vegetable.this);

                gameScene.updateEdgeX(gameScene.scroll.getScrollX()+Vgtable.getWidth()*0.75f,gameScene.scroll.getScrollX()+GStage.getWorldWidth()-Vgtable.getWidth()*0.75f);
                gameScene.scroll.setFlickScroll(false);
                gameScene.Zindex = Vgtable.getZIndex();
                Vgtable.setZIndex(1000);
                Vgtable.setScale(1.1f);
                gameScene.setVisibleOverlap(true);

                return super.touchDown(event, x, y, pointer, button);

            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                gameScene.setTouchVeg2(Touchable.enabled);
                gameScene.scroll.setFlickScroll(true);
                Vgtable.setScale(1);
                Vgtable.setZIndex(gameScene.Zindex);
                gameScene.setVisibleOverlap(false);
                if(isDrag==false){
                    info =  new InfoAniVeg(atlas,type,id,second,target,harvest,(11-id),vegetable.this,null);
                }

            }
        });

        Vgtable.addListener(new DragListener(){
            float pX, pY;
            @Override
            public void dragStart(InputEvent event, float x, float y, int pointer) {
                super.dragStart(event, x, y, pointer);
                pX = Vgtable.getX();
                pY = Vgtable.getY();
            }

            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                super.drag(event, x, y, pointer);
                isDrag=true;
                Vgtable.setX(Vgtable.getX()-Vgtable.getWidth()/2+x);
                Vgtable.setY(Vgtable.getY()-Vgtable.getHeight()/2+y);
                body.setX(Vgtable.getX());
                body.setY(Vgtable.getY());
                gr.setX(Vgtable.getX()+Vgtable.getWidth()/2);
                gr.setY(Vgtable.getY()+Vgtable.getHeight()/2+30);
                gameScene.checkEdge(Vgtable.getX()+Vgtable.getWidth()/2,Vgtable.getY()+Vgtable.getHeight()/2);
                gameScene.setVisibleIndex(gameScene.checkRec(vegetable.this));
                if(isDrag==true&&checkOn==0){
                    gameScene.MoveFotter(1);
                    checkOn++;
                }

            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer) {
                super.dragStop(event, x, y, pointer);
                gameScene.MoveFotter(2);
                checkOn=0;
                isDrag=false;
                if(gameScene.checkRec(vegetable.this)== -1){
                    backVegtble(pX,pY);
                }else {
                    int index = gameScene.checkRec(vegetable.this);
                    if(gameScene.arrLand.get(index).Status==4||gameScene.arrLand.get(index).Status==-1)
                        gameScene.CheckVegtbl(vegetable.this,index,new Vector2(pX,pY));
                    else
                        backVegtble(pX,pY);
                }
            }
        });
    }
    private void backVegtble(float pX, float pY){
        SoundEffect.Play(SoundEffect.Land_press);

        Vgtable.addAction(Actions.sequence(
                Actions.moveTo(pX, pY, 0.1f, Interpolation.fastSlow),
                GSimpleAction.simpleAction((d,a)->{
                    body.setX(Vgtable.getX());
                    body.setY(Vgtable.getY());
                    gr.setX(Vgtable.getX()+Vgtable.getWidth()/2);
                    gr.setY(Vgtable.getY()+Vgtable.getHeight()/2+30);
                    return true;
                })
        ));

    }
    private void CountDown(){
        GStage.addToLayer(GLayer.top,groupTime);
        groupTime.addAction(
            GSimpleAction.simpleAction((d,a)->{
                tic++;
                if(tic==60){
                    tic=0;
                    second-=1;
                    count++;
                    if(count==(11-id)){
                        count=0;
                        harvest++;
                    }
                    if(info!=null){
                        info.setTimeCountDown(second,harvest,target);
                    }

                    if(second==0){
                        isCountDown = true;
                    }
                }
                if(harvest>0){
                    gr.setVisible(true);
                }else {
                    gr.setVisible(false);
                }
                return isCountDown;
            })
        );

    }

    public void Harvest(){
        if(harvest!=0){
            int size =harvest;
            if(harvest>10){
                size=10;
            }
            for(int i=0;i<size;i++){
                Image ic = GUI.createImage(TextureAtlasC.atlasFotter,gameScene.setIcon(type));
                ic.setPosition(x,y,Align.center);
                group.addActor(ic);
                arrHarvest.add(ic);
            }
            for (Image img : arrHarvest){
                Tweens.setTimeout(group,0.1f*arrHarvest.indexOf(img,true),()->{
                    img.addAction(Actions.sequence(
                            GArcMoveToAction.arcMoveTo(GStage.getWorldWidth(),320,GStage.getWorldWidth(),400,0.5f,Interpolation.slowFast),
                            GSimpleAction.simpleAction((d,a)->{
                                img.remove();
                                return true;
                            })
                    ));
                });
            }
            target = 5+((id-1)*10);
            second = target*(11-id);
            harvest=0;
            arrHarvest.clear();
            if(isCountDown==true){
                isCountDown=false;
                CountDown();
            }
        }

    }
    public void setUpTime(){
        second=0;
        harvest=target;
        isCountDown=true;
        if(info!=null)
            info.setTimeCountDown(second,harvest,target);

    }

    public void dispose(){
        Vgtable.clear();
        Vgtable.remove();
        groupTime.clear();
        groupTime.remove();
        gr.clear();
        gr.remove();


    }


}
