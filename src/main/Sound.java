package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {
    Clip clip;
    //store sound
    URL soundURL[] = new URL[30];
    public Sound(){
        soundURL[0] = getClass().getResource("/sound/gamemusic.wav");
        soundURL[1] = getClass().getResource("/sound/gamegunsound.wav");
        soundURL[2] = getClass().getResource("/sound/gameslimeattack.wav");
        soundURL[3] = getClass().getResource("/sound/gameslimedie.wav");
        soundURL[4] = getClass().getResource("/sound/gamesoundslimedamage.wav");
        soundURL[5] = getClass().getResource("/sound/gameupgradesound.wav");
        soundURL[6] = getClass().getResource("/sound/upgradebulletandspeed.wav");
        soundURL[7] = getClass().getResource("/sound/upgradeHeart.wav");
        soundURL[8] = getClass().getResource("/sound/upgradespeed.wav");
    }
    public void setFile(int i){
        try{
            // เปิด audio file ใน java
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void play(){
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }
}
