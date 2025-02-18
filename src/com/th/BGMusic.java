package com.th;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

class BGMusic {  //this plays the background music. no changes needed here unless further notice
    private static ClassLoader classLoader = BGMusic.class.getClassLoader();
    private static Clip mus = null;
    private static String f = "";
    private static float volume;

    private static void openFile(String file){
        f = file;
        try {
            stop();
            mus = AudioSystem.getClip();
            mus.open(AudioSystem.getAudioInputStream(classLoader.getResource(f)));
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
        catch(NullPointerException e){
            e.printStackTrace();
            System.exit(-5);
        }
        FloatControl gainControl = (FloatControl) mus.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(volume);
    }
    public static void playOnce(String file){
        openFile(file);
        mus.start();
        mus.loop(0);
    }
    public static void playLoop(String file, int loopStart, int loopEnd){
        openFile(file);

        mus.start();
        mus.setLoopPoints(loopStart, loopEnd);
        mus.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public static void stop(){
        if(mus != null) mus.stop();
    }

    public static String getMusic(){
        return f;
    }
    public static void setVolume(float v){
        volume = v;
    }
    public static float getVolume(){
        return volume;
    }
}
