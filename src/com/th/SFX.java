package com.th;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

class SFX {  //this plays the sound effects. no changes needed here unless further notice
    private static float volume;

    private static Clip openFile(String file){
        Clip sfx = null;
        try {
            sfx = AudioSystem.getClip();
            sfx.open(AudioSystem.getAudioInputStream(new File(file)));
        }catch(LineUnavailableException e){
            e.printStackTrace();
            System.exit(-1);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-2);
        }
        catch (UnsupportedAudioFileException e){
            e.printStackTrace();
            System.exit(-3);
        }
        catch(IOException e){
            e.printStackTrace();
            System.exit(-4);
        }

        try {
            FloatControl gainControl = (FloatControl) sfx.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume);
        }catch(IllegalArgumentException e){
            System.err.println(sfx.getControls().length);
            for(Control c : sfx.getControls()) System.err.println(c);
            e.printStackTrace();
            System.exit(-5);
        }

        return sfx;
    }
    public static void playOnce(String file){
        Clip sfx = openFile(file);
        sfx.start();
    }

    public static void setVolume(float v){
        volume = v;
    }
    public static float getVolume(){
        return volume;
    }
}