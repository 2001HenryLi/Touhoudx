package com.th;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bomb extends Bullet {

    public Bomb(BufferedImage img, int X, int Y, int w, int h, MovePath MP){
        super(img, X, Y, w, h, MP);
    }

    public boolean collide(Bullet b){
        return b.getRect().intersects(getRect());
    }
}
