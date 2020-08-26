package com.ss.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.scenes.GameScene;

public class Tutorial2 {
    public static int step = 0;
    private static GShapeSprite overlay;



    public static void ShowTutorial(int step, Fotter footer, GameScene game){
        if(overlay==null){
            overlay = new GShapeSprite();
            overlay.createRectangle(true,0,0, GStage.getWorldWidth(), GStage.getWorldHeight());
            overlay.setColor(0,0,0,0.8f);
        }
        Group group = new Group();

        GStage.addToLayer(GLayer.top, overlay);
        GStage.addToLayer(GLayer.top, group);


        group.addActor(footer.group);


        group.addActor(game.arrLand.get(0).childGroup);


        Actor icon = game.arrLand.get(0).childGroup;
        Vector2 pos1 = icon.localToActorCoordinates(group, new Vector2(icon.getX(), icon.getY()));
        group.addActor(footer.arrButton.get(0));
        icon.setPosition(pos1.x, pos1.y, Align.center);
        //icon.setColor(Color.RED);

    }
}
