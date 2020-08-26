package com.ss.object.storeHouse;

public class Data {
  public static final int PADDY = 0;
  public static final int TOMATO = 1;
  public static final int POTATO = 2;
  public static final int COW = 3;
  public static final int WINTER_MELON = 4;
  public static final int CORN = 5;
  public static final int CARROT = 6;
  public static final int EGG = 7;
  public static final int CABBAGE = 8;
  public static final int PUMPKIN = 9;
  public static final int PINEAPPLE = 10;
  public static final int GOAT = 11;
  public static final int COCONUT = 12;
  public static final int WOOL = 13;

  public int lvUnlock = 0;
  public String strName = "";
  public int price = 0;
  public int quantity = 0;

  public void setQuantity(int value){
    quantity = value;
  }
}
