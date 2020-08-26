package com.ss.object.storeHouse;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.ss.commons.BitmapFontC;
import com.ss.commons.TextureAtlasC;
import com.ss.core.util.GUI;

public class Tile {
  private TextureAtlas atlas;
  private Group group, groupParent;
  private Image tile, shape;
  private String strShape;
  private int quantity, price;
  private Label quantityTxt, priceTxt;

  public Tile(Group groupParent, String strShape, int price, int quantity){
    this.groupParent = groupParent;
    this.strShape = strShape;
    this.price = price;
    this.quantity = quantity;
    initAtlas();
    initGroup();
    initUI();
  }

  private void initAtlas(){
    atlas = TextureAtlasC.storeHouse;
  }

  private void initGroup(){
    group = new Group();
    group.setOrigin(Align.center);
    groupParent.addActor(group);
  }

  private void initUI(){
    tile = GUI.createImage(atlas, "tile");
    shape = GUI.createImage(atlas, strShape);
    priceTxt = new Label("$" + price, new Label.LabelStyle(BitmapFontC.roboto, null));
    quantityTxt = new Label("" + quantity, new Label.LabelStyle(BitmapFontC.roboto, null));
    group.addActor(tile);
    group.addActor(shape);
    group.addActor(priceTxt);
    group.addActor(quantityTxt);

    priceTxt.setPosition(tile.getWidth()*0.1f, tile.getHeight()*0.05f);
    quantityTxt.setPosition(tile.getWidth()*0.5f, tile.getHeight()*1.1f, Align.center);
    shape.setPosition(tile.getX() + tile.getWidth()/2,tile.getY() + tile.getHeight()/2, Align.center);
    group.setSize(tile.getWidth(), tile.getHeight()*1.5f);
  }

  public Group getGroup(){
    group.setOrigin(Align.center);
    group.setScaleY(-1);
    return group;
  }

  public float getWidth(){
    return group.getWidth();
  }

  public float getHeight(){
    return group.getHeight();
  }

  public void setQuantity(int value){
    this.quantity = value;
    quantityTxt.setText(""+value);
    quantityTxt.setPosition(tile.getWidth()*0.5f - quantityTxt.getWidth()*.5f, tile.getHeight()*1.1f);
  }

  public void setPrice(int value){
    this.price = value;
    priceTxt.setText(value);
  }

  public int getMoney(){
    int money = 0;
    money = quantity*price;
    return money;
  }
}
