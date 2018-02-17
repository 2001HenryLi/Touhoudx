package com.th; /**
 * Created by RyanNiu on 5/8/2016.
 */

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

class SFX {  //this plays the background music. no changes needed here unless further notice
    private Clip mus = null;
    public SFX(){}
    public void playFX(String file){
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
        gainControl.setValue(6.0206f);
        mus.start();
    }
    public void stop(){ mus.stop(); }
}