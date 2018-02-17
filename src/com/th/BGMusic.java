package com.th; /**
 * Created by RyanNiu on 3/19/2016.
 */

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

class BGMusic {  //this plays the background music. no changes needed here unless further notice
    private Clip mus = null;
    private String f = "";
    public BGMusic(){}
    public void playBGMusic(String file, int loopStart){
        f = file;
        try {
            mus = AudioSystem.getClip();
            mus.open(AudioSystem.getAudioInputStream(new File(file)));
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
        FloatControl gainControl = (FloatControl) mus.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-6.0206f);
        mus.start();  //start from the beginning
        mus.setLoopPoints(loopStart,-1);  //loop the correct parts
        mus.loop(Clip.LOOP_CONTINUOUSLY);  //tell it to loop
    }
    public void stop(){ if(mus != null) mus.stop(); }
    public String getMusic(){return f;}
}
