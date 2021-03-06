/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.app;

import android.content.SharedPreferences;

public class ClientConfig {
    static final String KEY_CURRNET_REGION = "KEY_CURRNET_REGION";
    static final String KEY_DISTURB_SWITCH = "KEY_DISTURB_SWITCH";
    static final String KEY_MESSAGE_SOUND_SWITCH = "KEY_MESSAGE_SOUND_SWITCH";
    static final String KEY_NOTIFY_SWITCH = "KEY_NOTIFY_SWITCH";
    static final String KEY_SHAKE_SOUND_SWITCH = "KEY_MESSAGE_SOUND_SWITCH";
    static final String MODEL_KEY = "CLIENT_CONFIG";
    
    public static void disturbEnable() {
        SharedPreferences sp = CriusApplication.getInstance().getSharedPreferences("CLIENT_CONFIG", 0x0);
        sp.edit().putBoolean(KEY_DISTURB_SWITCH, true).commit();
    }
    
    public static void disturbDisEnable() {
        SharedPreferences sp = CriusApplication.getInstance().getSharedPreferences("CLIENT_CONFIG", 0x0);
        sp.edit().putBoolean(KEY_DISTURB_SWITCH, false).commit();
    }
    
    public static boolean disturbIsEnable() {
        SharedPreferences sp = CriusApplication.getInstance().getSharedPreferences("CLIENT_CONFIG", 0x0);
        return sp.getBoolean(KEY_DISTURB_SWITCH, true);
    }
    
    public static void notifyEnable() {
        SharedPreferences sp = CriusApplication.getInstance().getSharedPreferences("CLIENT_CONFIG", 0x0);
        sp.edit().putBoolean(KEY_NOTIFY_SWITCH, true).commit();
    }
    
    public static void notifyDisEnable() {
        SharedPreferences sp = CriusApplication.getInstance().getSharedPreferences("CLIENT_CONFIG", 0x0);
        sp.edit().putBoolean(KEY_NOTIFY_SWITCH, false).commit();
    }
    
    public static boolean notifyIsEnable() {
        SharedPreferences sp = CriusApplication.getInstance().getSharedPreferences("CLIENT_CONFIG", 0x0);
        return sp.getBoolean(KEY_NOTIFY_SWITCH, true);
    }
    
    public static void soundEnable() {
        SharedPreferences sp = CriusApplication.getInstance().getSharedPreferences("CLIENT_CONFIG", 0x0);
        sp.edit().putBoolean(KEY_MESSAGE_SOUND_SWITCH, true).commit();
    }
    
    public static void soundDisEnable() {
        SharedPreferences sp = CriusApplication.getInstance().getSharedPreferences("CLIENT_CONFIG", 0x0);
        sp.edit().putBoolean("KEY_MESSAGE_SOUND_SWITCH", false).commit();
    }
    
    public static boolean soundIsEnable() {
        SharedPreferences sp = CriusApplication.getInstance().getSharedPreferences("CLIENT_CONFIG", 0x0);
        return sp.getBoolean("KEY_MESSAGE_SOUND_SWITCH", true);
    }
    
    public static void shakeSoundEnable() {
        SharedPreferences sp = CriusApplication.getInstance().getSharedPreferences("CLIENT_CONFIG", 0x0);
        sp.edit().putBoolean("KEY_MESSAGE_SOUND_SWITCH", true).commit();
    }
    
    public static void shakeSoundDisEnable() {
        SharedPreferences sp = CriusApplication.getInstance().getSharedPreferences("CLIENT_CONFIG", 0x0);
        sp.edit().putBoolean("KEY_MESSAGE_SOUND_SWITCH", false).commit();
    }
    
    public static boolean shakeSoundIsEnable() {
        SharedPreferences sp = CriusApplication.getInstance().getSharedPreferences("CLIENT_CONFIG", 0x0);
        return sp.getBoolean("KEY_MESSAGE_SOUND_SWITCH", true);
    }
    
    public static void setCurrentRegion(String region) {
        SharedPreferences sp = CriusApplication.getInstance().getSharedPreferences("CLIENT_CONFIG", 0x0);
        sp.edit().putString("KEY_CURRNET_REGION", region).commit();
    }
    
    public static String getCurrentRegion() {
        SharedPreferences sp = CriusApplication.getInstance().getSharedPreferences("CLIENT_CONFIG", 0x0);
        return sp.getString("KEY_CURRNET_REGION", null);
    }
}
