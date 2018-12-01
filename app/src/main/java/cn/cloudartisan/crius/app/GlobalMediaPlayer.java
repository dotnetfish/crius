/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.app;

import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.db.ConfigDBManager;

import java.util.Calendar;
import java.util.HashMap;

public class GlobalMediaPlayer {
    GlobalVoicePlayer.OnPlayListener OnPlayListener;
    MediaPlayer mMediaPlayer;
    static GlobalMediaPlayer player;
    Resources resource;
    
    private GlobalMediaPlayer() {
        mMediaPlayer = new MediaPlayer();
        resource = CriusApplication.getInstance().getResources();
    }
    
    public static synchronized GlobalMediaPlayer getPlayer() {
        if(player == null) {
            player = new GlobalMediaPlayer();
        }
        return player;
    }



private synchronized void play(int rid) {
    if(mMediaPlayer.isPlaying()) {
        return;
    }
    try {
        AssetFileDescriptor afd = resource.openRawResourceFd(rid);
        mMediaPlayer.reset();
        mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
        mMediaPlayer.prepare();
        mMediaPlayer.start();
        return;
    } catch(Exception localException1) {
    }
}

public void start(int rid) {
    if(!ClientConfig.soundIsEnable()) {
        return;
    }
    play(rid);
}

public void playMessageSound() {
    HashMap<String, String> configs = ConfigDBManager.getManager().queryConfigs();
    if(!ClientConfig.soundIsEnable()) {
        return;
    }
    if(ClientConfig.disturbIsEnable()) {
        int hour = Calendar.getInstance().get(0xb);
        if((hour > 0x8) && (hour < 0x17)) {
        }
    }
    if(("1".equals(configs.get("message_sound_type"))) || (configs.get("message_sound_type") == null)) {
        play(R.raw.classic);
    }
    if("2".equals(configs.get("message_sound_type"))) {
        play(R.raw.dingdong);
    }
    if("3".equals(configs.get("message_sound_type"))) {
        RingtoneManager.getRingtone(CriusApplication.getInstance(), RingtoneManager.getDefaultUri(0x2)).play();
    }
}
}
