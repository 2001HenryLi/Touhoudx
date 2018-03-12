package com.th;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

class ImageLoader {
    private static ClassLoader classLoader = BGMusic.class.getClassLoader();

    public static BufferedImage openImage(String path){
        BufferedImage b = null;
        try{
            b = ImageIO.read(classLoader.getResource(path));
        }catch (IOException e){
            e.printStackTrace();
            System.exit(111);
        }catch(NullPointerException e){
            System.out.println(path);
            System.out.println(classLoader.getResource(path));
            e.printStackTrace();
            System.exit(-5);
        }catch(IllegalArgumentException e){
            System.out.println(path);
            System.out.println(classLoader.getResource(path));
            e.printStackTrace();
            System.exit(-5);
        }
        return b;
    }
}
