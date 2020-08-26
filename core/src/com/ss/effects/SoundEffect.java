package com.ss.effects;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.ss.core.util.GAssetsManager;

/* renamed from: com.ss.effect.SoundEffect */
public class SoundEffect {
  public static int MAX_COMMON = 12;
  public static Music bgSound = null;
  public static Music bgSound2 = null;
  public static Music bgSound3 = null;
  public static Sound[] commons = null;
  public static boolean music = false;
  public static boolean mute = false;
  public static int click = 0;
  public static int merge = 1;
  public static int tack = 2;
  public static int createNew = 3;
  public static int harvest = 4;
  public static int Land_press = 5;
  public static int pop_up = 6;
  public static int sell = 7;
  public static int levelup = 8;
  public static int panel_in = 9;
  public static int panel_out = 10;





  public static void initSound() {
    commons = new Sound[MAX_COMMON];
    commons[click] = GAssetsManager.getSound("sprout.mp3");
    commons[merge] = GAssetsManager.getSound("merge.mp3");
    commons[tack] = GAssetsManager.getSound("button_press.mp3");
    commons[createNew] = GAssetsManager.getSound("createNew.mp3");
    commons[harvest] = GAssetsManager.getSound("harvest.mp3");
    commons[Land_press] = GAssetsManager.getSound("land_press.mp3");
    commons[pop_up] = GAssetsManager.getSound("pop_up.mp3");
    commons[sell] = GAssetsManager.getSound("sell.mp3");
    commons[levelup] = GAssetsManager.getSound("levelUp.mp3");
    commons[panel_in] = GAssetsManager.getSound("panel_open.mp3");
    commons[panel_out] = GAssetsManager.getSound("panel_close.mp3");
    bgSound = GAssetsManager.getMusic("bg.mp3");
//    bgSound2 = GAssetsManager.getMusic("soundBg2.mp3");
//    bgSound3 = GAssetsManager.getMusic("soundBg3.mp3");
//
  }

  public static void Play(int i) {
    if (!mute) {
      commons[i].play();
    }
  }

  public static void Playmusic(int mode) {
    music = false;
    switch (mode) {
      case 1: {
        bgSound.play();
        bgSound.setLooping(true);
        bgSound.setVolume(0.2f);
        break;
      }
      case 2: {
        bgSound2.play();
        bgSound2.setLooping(true);
        bgSound2.setVolume(0.2f);
        break;
      }
      case 3: {
        bgSound3.play();
        bgSound3.setLooping(true);
        bgSound3.setVolume(0.2f);
        break;
      }
      default:{
        bgSound.play();
        bgSound.setLooping(true);
        bgSound.setVolume(0.2f);
        break;
      }
    }
  }

  public static void Stopmusic(int mode) {
    music = true;
    switch (mode){
      case 1: {
        bgSound.stop();
        break;
      }
      case 2: {
        bgSound2.stop();
        break;
      }
      case 3: {
        bgSound3.pause();
        break;
      }
      default:{
        bgSound.stop();
        break;
      }
    }
  }
}
