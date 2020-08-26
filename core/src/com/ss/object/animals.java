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
import com.ss.scenes.GameScene;

public class animals {
    private TextureAtlas atlas;
    private Group group;
    private Group groupTime = new Group();
    public Image animal,iconAni;
    public String type;
    public int id;
    public Rectangle body;
    public int index;
    private GameScene gameScene;
    final GShapeSprite blackOverlay = new GShapeSprite();
    public float Xmove=0,Ymove=0;
    private boolean isDrag = false;
    public int target=0;
    public int harvest=0;
    public int second =0;
    private InfoAniVeg info;
    private boolean isCountDown = false;
    private int tic=0;
    private int count =0;
    private Array<Image>  arrHarvest = new Array<>();
    private int checkOn=0;
    public float x,y;

    public animals(TextureAtlas atlas, Group group, GameScene gameScene,float x, float y,String type,int id,int index,int Second){
        TextureAtlasC.initAtlas();
        this.atlas = atlas;
       this.type = type;
       this.id = id;
       this.index = index;
       this.gameScene = gameScene;
       this.group = group;
       this.x = x;
       this.y = y;
       animal = GUI.createImage(atlas,type+id);
       animal.setOrigin(Align.center);
       animal.setPosition(x+animal.getWidth()/2,y+animal.getHeight()/2, Align.center);
       group.addActor(animal);
       body = new Rectangle(animal.getX(),animal.getY(),animal.getWidth()*0.7f,animal.getHeight()*0.7f);
       body.setPosition(animal.getX()+animal.getWidth()*0.15f,animal.getY()+animal.getHeight()*0.15f);
       Xmove=animal.getX();
       Ymove=animal.getY();
        target = 5+((id-1)*10);

        if(Second==-1){
           second = target*(31-id);

       }else {
           this.second = Second;
           harvest = target-(this.second/(31-id));
       }
       iconAni = GUI.createImage(TextureAtlasC.atlasFotter,gameScene.setIcon(type));
       iconAni.setOrigin(Align.center);
       iconAni.setPosition(animal.getX()+animal.getWidth()-50,animal.getY()+animal.getHeight()/2,Align.center);
       group.addActor(iconAni);
       animationIcon();
       CountDown();
        if(second==0){
            setUpTime();
        }
    }

   private void animationIcon(){
        iconAni.addAction(Actions.sequence(
                Actions.rotateTo(-15,0.2f),
                Actions.rotateTo(30,0.2f,Interpolation.bounceOut),
                Actions.delay(1),
                GSimpleAction.simpleAction((d,a)->{
                    animationIcon();
                    return true;
                })
        ));
   }
   public void addDrag(){
       animal.addListener(new ClickListener(){
           @Override
           public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
               SoundEffect.Play(SoundEffect.pop_up);
               gameScene.setTouchAni(Touchable.disabled,animals.this);
               gameScene.updateEdgeX(gameScene.scroll.getScrollX()+animal.getWidth()/2,gameScene.scroll.getScrollX()+ GStage.getWorldWidth()-animal.getWidth()/2);
               gameScene.scroll.setFlickScroll(false);
               gameScene.StatusDragAni(animals.this,-1);
               gameScene.setVisibleOverlap(true);
               gameScene.ZindexAni = animal.getZIndex();
               animal.setZIndex(1000);
               iconAni.setZIndex(animal.getZIndex()+1);
               iconAni.setVisible(false);
               return super.touchDown(event, x, y, pointer, button);
           }

           @Override
           public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
               super.touchUp(event, x, y, pointer, button);
               gameScene.setTouchAni2(Touchable.enabled);
               gameScene.scroll.setFlickScroll(true);
               gameScene.setVisibleOverlap(false);
               animal.setZIndex(gameScene.ZindexAni);
               iconAni.setVisible(true);
               iconAni.setZIndex(animal.getZIndex()+1);
               if(isDrag==false){
                   info= new InfoAniVeg(atlas,type,id,second,target,harvest,(31-id),null,animals.this);
                   gameScene.StatusDragAni(animals.this,3);

               }

           }
       });
       animal.addListener(new DragListener(){
           float pX, pY;
           @Override
           public void dragStart(InputEvent event, float x, float y, int pointer) {
               super.dragStart(event, x, y, pointer);
               Xmove = animal.getX();
               Ymove = animal.getY();
               pX = animal.getX();
               pY = animal.getY();
           }

           @Override
           public void drag(InputEvent event, float x, float y, int pointer) {
               super.drag(event, x, y, pointer);
               isDrag=true;
               animal.setX(animal.getX()-animal.getWidth()/2+x);
               animal.setY(animal.getY()-animal.getHeight()/2+y);
               body.setX(animal.getX());
               body.setY(animal.getY());
               iconAni.setX(animal.getX()+animal.getWidth()-50);
               iconAni.setY(animal.getY()+animal.getHeight()/2);
               gameScene.checkEdge(animal.getX()+animal.getWidth()/2,animal.getY()+animal.getHeight()/2);
               if(gameScene.CheckRecAni(animals.this).size!=0){
                   gameScene.setVisibleOverlap(gameScene.CheckRecAni(animals.this));
               }else {
                   gameScene.setVisibleOverlap(true);
               }
//               System.out.println("chech:"+gameScene.CheckOverLapAni(animals.this));
                gameScene.ScaleAniChose(gameScene.CheckOverLapAni(animals.this));
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
               if(gameScene.CheckRecAni(animals.this).size==0){
                   if(gameScene.CheckOverLapAni(animals.this)==-1){
                       backAni(pX,pY);
                       gameScene.StatusDragAni(animals.this,3);
                   }else {
//                       System.out.println("chech:"+gameScene.CheckOverLapAni(animals.this));
                       gameScene.SwapTwoAni(animals.this,gameScene.CheckOverLapAni(animals.this));
                       gameScene.ScaleDefault();


                   }

               }else {
//                   System.out.println("va cham: "+gameScene.CheckRecAni(animals.this));
                   gameScene.SwapAni(gameScene.CheckRecAni(animals.this),animals.this);
                   gameScene.ScaleDefault();
               }

           }
       });

   }
    private void backAni(float pX, float pY){
        SoundEffect.Play(SoundEffect.Land_press);
        animal.addAction(Actions.sequence(
                Actions.moveTo(pX, pY, 0.1f, Interpolation.fastSlow),
                GSimpleAction.simpleAction((d,a)->{
                    body.setX(animal.getX());
                    body.setY(animal.getY());
                    iconAni.setX(animal.getX()+animal.getWidth()-50);
                    iconAni.setY(animal.getY()+animal.getHeight()/2);
                    setMovoXMoveY();
                    return true;
                })
        ));

    }
    public void setMovoXMoveY(){
        Xmove = animal.getX();
        Ymove = animal.getY();
    }
    public void MoveAni(float x,float y){
        animal.addAction(Actions.sequence(
                Actions.moveTo(x,y),
                GSimpleAction.simpleAction((d,a)->{
                    body.setX(animal.getX());
                    body.setY(animal.getY());
                    iconAni.setX(animal.getX()+animal.getWidth()-50);
                    iconAni.setY(animal.getY()+animal.getHeight()/2);
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
                        if(count==(31-id)){
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
                        iconAni.setVisible(true);
                    }else {
                        iconAni.setVisible(false);
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
                ic.setPosition(animal.getX()+animal.getWidth()/2,animal.getY()+animal.getHeight()/2,Align.center);
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
            second = target*(31-id);
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
        animal.clear();
        animal.remove();
        groupTime.clear();
        groupTime.remove();
        iconAni.remove();
    }




}
