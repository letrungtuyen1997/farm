package com.ss.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class effectWin extends Actor{
    private static FileHandle love = Gdx.files.internal("particle/Veg");
    private static FileHandle merge = Gdx.files.internal("particle/merge");

    public ParticleEffect effect;
    private Actor parent = this.parent;

    public effectWin(int id, float f, float f2) {
        this.effect = new ParticleEffect();
        setX(f);
        setY(f2);
            if(id==1){
                this.effect.load(love, Gdx.files.internal("particle"));
                this.effect.scaleEffect(1f);
                for (int i = 0; i < this.effect.getEmitters().size; i++) {
                    ((ParticleEmitter) this.effect.getEmitters().get(i)).flipY();
                    ((ParticleEmitter) this.effect.getEmitters().get(i)).setFlip(true,false);
                }
            }else if(id==2){
                this.effect.load(merge, Gdx.files.internal("particle"));
                this.effect.scaleEffect(1.5f);
                for (int i = 0; i < this.effect.getEmitters().size; i++) {
                    ((ParticleEmitter) this.effect.getEmitters().get(i)).flipY();
                    ((ParticleEmitter) this.effect.getEmitters().get(i)).setFlip(true,false);
                }
            }
        this.effect.setPosition(f, f2);


    }

    public void act(float f) {
        super.act(f);
        this.effect.setPosition(getX(), getY());
        this.effect.update(f);
    }

    public void draw(Batch batch, float f) {
        super.draw(batch, f);
        if (!this.effect.isComplete()) {
            this.effect.draw(batch);
            return;
        }
        this.effect.dispose();
    }

    public void start() {
        this.effect.start();
    }
}
