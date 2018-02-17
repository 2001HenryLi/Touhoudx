package com.th;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class Player {
    public BufferedImage sprite;
    public Image hitMarker;

    public int x, y;
    public int vx, vy;

    public Player(){
        try {
            sprite = ImageIO.read(new File("Resources/CharacterSprites/cirno.png"));
        } catch(IOException e) {
            System.out.println("failed");
            System.exit(-1);
        }
        x = 100;
        y = 100;
        vx = 5;
        vy = 5;
    }
    public void move(boolean[] keysDown){
        if(keysDown[0]) x -= vx;
        if(keysDown[1]) x += vx;
        if(keysDown[2]) y -= vy;
        if(keysDown[3]) y += vy;
    }
}
