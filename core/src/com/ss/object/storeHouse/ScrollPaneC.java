package com.ss.object.storeHouse;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.ss.gameLogic.StaticObjects.Config;

public class ScrollPaneC {
  private TextureAtlas atlas;
  private Group groupParent, group;
  private float width, height, x, y;
  private Table tableScroll;
  private Array<Tile> tiles;
  private Array<Data> data;

  public ScrollPaneC(TextureAtlas atlas, Group group, Array<Data> data, float width, float height, float x, float y){
    this.atlas = atlas;
    this.groupParent = group;
    this.data = data;
    this.width = width;
    this.height = height;
    this.x = x;
    this.y = y;
    initGroup();
    initListView();
  }

  private void initGroup(){
    group = new Group();
    group.setSize(width, height);
    group.setPosition(x, y);
  }



  private void initListView(){
    tiles = new Array<>();
    Table table = new Table();
    tableScroll = new Table();
    for(int i = 0; i < data.size; i++) {
      if(Config.Level >= data.get(i).lvUnlock){
        Tile tile = new Tile(group, data.get(i).strName, data.get(i).price, data.get(i).quantity);
        tableScroll.add(tile.getGroup()).padBottom(tile.getHeight()*.05f).padRight(tile.getWidth()*0.1f);
        tiles.add(tile);
        if(tableScroll.getCells().size%4 == 0){
          tableScroll.row();
        }
      }
    }

    ScrollPane scrollPane = new ScrollPane(tableScroll);
    table.setFillParent(true);
    table.add(scrollPane).fill().expand();
    group.setScale(1, -1);
    group.setOrigin(Align.center);
    group.addActor(table);
    groupParent.addActor(group);

  }

  public void updateDataTable(Array<Data> dataQuantity){
    for(int i = 0; i < dataQuantity.size; i++) {
      if(i >= tiles.size){
        if(Config.Level >= dataQuantity.get(i).lvUnlock){
          Tile tile = new Tile(group, dataQuantity.get(i).strName, dataQuantity.get(i).price, dataQuantity.get(i).quantity);
          tableScroll.add(tile.getGroup()).padBottom(tile.getHeight()*.05f).padRight(tile.getWidth()*0.1f);
          tiles.add(tile);
          if(tableScroll.getCells().size%4 == 0){
            tableScroll.row();
          }
        }
      }
      else {
        tiles.get(i).setQuantity(dataQuantity.get(i).quantity);
      }
    }
  }

  private void addCell(Group group){
    if(tableScroll.getCells().size % 4 == 0){
      tableScroll.row();
    }
    tableScroll.add(group).padBottom(group.getHeight()*.05f).padRight(group.getWidth()*0.1f);
  }

  public int getMomentCapacity(){
    int quantity = 0;
    for(Data dt : data){
      quantity += dt.quantity;
    }

    return  quantity;
  }

  public int getMoney(){
    int money = 0;
    for(Tile tile : tiles){
      money += tile.getMoney();
    }
    return money;
  }

  public int getSizeTiles(){
    return tiles.size;
  }
}
