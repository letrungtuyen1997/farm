package com.ss.gameLogic.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;
import com.ss.GMain;

import java.util.Locale;
import java.util.MissingResourceException;

public class C {

    public static class remote {
        public static int adsTime = 50;
        static void initRemoteConfig() {

        }
    }

    public static class lang {
        private static I18NBundle locale;
        public static String title = "";
        public static String adsTimeLbl = "";
        public static String notempty ="";
        public static String checkconnect ="";
        public static String endmoney ="";
        public static String locked ="";
        public static String lua="";
        public static String khoai="";
        public static String cachua="";
        public static String bap="";
        public static String leo="";
        public static String bi="";
        public static String dua="";
        public static String cai="";
        public static String thom="";
        public static String carot="";
        public static String cuu="";
        public static String bo="";
        public static String ga="";
        public static String de="";
        public static String level="";
        public static String add="";
        public static String in="";
        public static String second="";
        public static String full="";
        public static String setting="";
        public static String maxspeed="";
        public static String free="";
        public static String getcoin="";
        public static String watchads="";
        public static String empty="";
        public static String moneytake="";
        public static String capacity="";
        public static String levelhouse="";
        public static String idcontry="";
        public static String lbNhan="";
        public static String lbstep1="";
        public static String lbstep2="";
        public static String lbstep3="";
        public static String lbstep4="";
        public static String lbstep5="";
        public static String lbstep6="";
        public static String lbstep7="";
        public static String lbcontinus="";
        public static String lbTo="";
        public static String lbup="";
        public static String lbsale="";
        public static String lbRank="";


        static void initLocalize() {
            String deviceLang = GMain.platform.GetDefaultLanguage();

            FileHandle specFilehandle = Gdx.files.internal("i18n/lang_" + deviceLang);
            FileHandle baseFileHandle = Gdx.files.internal("i18n/lang");

            try {
                locale = I18NBundle.createBundle(specFilehandle, new Locale(""));
                idcontry = locale.get("idcontry");
            }
            catch (MissingResourceException e) {
                locale = I18NBundle.createBundle(baseFileHandle, new Locale(""));
                idcontry = locale.get("idcontry");
            }

            title = locale.get("title");
            adsTimeLbl = locale.format("adsTime", remote.adsTime);
            notempty = locale.get("notempty");
            checkconnect = locale.get("checkconnect");
            endmoney = locale.get("endmoney");
            locked = locale.get("locked");
            lua = locale.get("lua");
            khoai = locale.get("khoai");
            cachua = locale.get("cachua");
            bap = locale.get("bap");
            leo = locale.get("leo");
            bi = locale.get("bi");
            dua = locale.get("dua");
            cai = locale.get("cai");
            thom = locale.get("thom");
            carot = locale.get("carot");
            cuu = locale.get("cuu");
            bo = locale.get("bo");
            ga = locale.get("ga");
            de = locale.get("de");
            level = locale.get("level");
            add = locale.get("add");
            in = locale.get("in");
            second = locale.get("second");
            full = locale.get("full");
            setting = locale.get("setting");
            maxspeed = locale.get("maxspeed");
            free = locale.get("free");
            getcoin = locale.get("getcoin");
            watchads = locale.get("watchads");
            empty = locale.get("empty");
            moneytake = locale.get("moneytake");
            capacity = locale.get("capacity");
            levelhouse = locale.get("levelhouse");
            lbNhan = locale.get("lbNhan");
            lbstep1 = locale.get("lbstep1");
            lbstep2 = locale.get("lbstep2");
            lbstep3 = locale.get("lbstep3");
            lbstep4 = locale.get("lbstep4");
            lbstep5 = locale.get("lbstep5");
            lbstep6 = locale.get("lbstep6");
            lbstep7 = locale.get("lbstep7");
            lbcontinus = locale.get("lbcontinus");
            lbTo = locale.get("lbTo");
            lbup = locale.get("lbup");
            lbsale = locale.get("lbsale");
            lbRank = locale.get("lbRank");


        }
    }

    public static void init() {
        remote.initRemoteConfig();
        lang.initLocalize();
    }
}
