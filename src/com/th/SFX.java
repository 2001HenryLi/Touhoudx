package com.th;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

class SFX {  //this plays the sound effects. no changes needed here unless further notice
    private static Clip sfx = null;
    private static float volume;

    private static void openFile(String file){
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
        FloatControl gainControl = (FloatControl) sfx.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(volume);
    }
    public static void playOnce(String file){
        openFile(file);
        sfx.start();
        sfx.loop(0);
    }

    public static void setVolume(float v){
        volume = v;
    }
    public static float getVolume(){
        return volume;
    }
}