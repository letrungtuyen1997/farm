package com.ss.gameLogic.StaticObjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.ss.GMain;
import com.ss.core.util.GStage;
import com.ss.object.LoadLv;

public class Config {
  //constant
  public static float ratioX = GStage.getWorldWidth()/720;
  public static float ratioY = GStage.getWorldHeight()/1280;
  public static float WidthScreen = GStage.getWorldWidth();
  public static float HeightScreen = GStage.getWorldHeight();
  public static int Level = GMain.prefs.getInteger("level", 1);
  public static long Money =10000;
  public static long BestMoney =0;
    public static long momentExp = GMain.prefs.getLong("momentExp", 0);
    public static long nextExp = GMain.prefs.getLong("nextExp", 10);  // (10*(0.5f*i)^2)
  public static int Stepindex=0;
  public static String compressCoin(long num, int numOf){
    String str = "0";
    String dv = "";
    int ratio = 0;
    double x = 0;

    if(num >= 1000000000){
      ratio = 1000000000;
      dv = "B";
    }
    else if(num >= 1000000){
      ratio = 1000000;
      dv = "M";
    }
    else if(num >= 1000){
      ratio = 1000;
      dv = "K";
    }
    else {
      ratio = 1;
      dv = "";
    }
    x = (double)num/ratio;
    x = Math.floor(x*Math.pow(10, numOf))/Math.pow(10, numOf);
    str = x + dv;


    String strTemp = str.substring(str.length() - 2, str.length());
    if(strTemp.equals(".0")){
      str = str.substring(0, str.length() - 2);
    }
    else {
      strTemp = str.substring(str.length() - 3, str.length()-1);
      if(strTemp.equals(".0")){
        str = str.substring(0, str.length()-3);
        str += dv;
      }
    }

    return str;
  }

  public static LoadLv LoadLv(int id){
    LoadLv info ;
    Json js = new Json();
    switch(id){
      case 1:{
        info = js.fromJson(LoadLv.class,"{status0:[15,89], status1:[10,14],status2:[5,9]}");
        break;
      }
      case 2:{
        info = js.fromJson(LoadLv.class,"{status0:[20,89],status1:[15,19] ,status2:[5,14]}");
        break;
      }
      case 3:{
        info = js.fromJson(LoadLv.class,"{status0:[25,89],status1:[20,24] ,status2:[5,19]}");
        break;
      }
      case 4:{
        info = js.fromJson(LoadLv.class,"{status0:[30,89],status1:[25,29] ,status2:[5,24]}");
        break;
      }
      case 5:{
        info = js.fromJson(LoadLv.class,"{status0:[35,89],status1:[30,34] ,status2:[5,29]}");
        break;
      }
      case 7:{}
      case 8:{}
      case 9:{}
      case 10:{}
      case 6:{
        info = js.fromJson(LoadLv.class,"{status0:[40,89],status1:[35,39] ,status2:[5,34]}");
        break;
      }
      case 11:{
        info = js.fromJson(LoadLv.class,"{ status0:[45,89],status1:[40,44] ,status2:[5,39]}");
        break;
      }
      case 12:{
        info = js.fromJson(LoadLv.class,"{ status0:[50,89],status1:[45,49] ,status2:[5,44]}");
        break;
      }
      case 13:{
        info = js.fromJson(LoadLv.class,"{ status0:[55,89],status1:[50,54] ,status2:[5,49]}");
        break;
      }
      case 14:{
        info = js.fromJson(LoadLv.class,"{ status0:[60,89],status1:[55,59] ,status2:[5,54]}");
        break;
      }
      case 15:{
        info = js.fromJson(LoadLv.class,"{ status0:[65,89],status1:[60,64] ,status2:[5,59]}");
        break;
      }
      case 17:{}
      case 18:{}
      case 19:{}
      case 16:{
        info = js.fromJson(LoadLv.class,"{ status0:[70,89],status1:[65,69] ,status2:[5,64]}");
        break;
      }
      case 20:{
        info = js.fromJson(LoadLv.class,"{ status0:[75,89],status1:[70,74] ,status2:[5,69]}");
        break;
      }
      case 21:{
        info = js.fromJson(LoadLv.class,"{ status0:[80,89],status1:[75,79] ,status2:[5,74]}");
        break;
      }
      case 22:{
        info = js.fromJson(LoadLv.class,"{ status0:[85,89],status1:[80,84] ,status2:[5,79]}");
        break;
      }
      case 23:{
        info = js.fromJson(LoadLv.class,"{ status0:[89,89],status1:[85,89] ,status2:[5,84]}");
        break;
      }
      case 24:{
        info = js.fromJson(LoadLv.class,"{ status0:[89,89],status1:[89,89] ,status2:[5,89]}");
        break;
      }
      case 25:{
        info = js.fromJson(LoadLv.class,"{ status0:[89,89],status1:[89,89] ,status2:[5,89]}");
        break;
      }
      default:{
        info = js.fromJson(LoadLv.class,"{ status0:[89,89],status1:[89,89] ,status2:[5,89]}");
        break;
      }
    }
    return info;
  }


}
