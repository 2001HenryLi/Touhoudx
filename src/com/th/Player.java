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
        System.out.println(sprite);
        x = 100;
        y = 100;
        vx = 5;
        vy = 5;
    }
    public void move(int xDir, int yDir){
        x += vx*xDir;
        y += vy*yDir;
    }
}
