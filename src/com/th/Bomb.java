package com.th;

import java.awt.*;

public class Bomb extends Bullet {

    public Bomb(String path, int X, int Y, int w, int h, MovePath MP){
        super(path, X, Y, w, h, MP);
    }

    public boolean collide(Bullet b){
        Rectangle bRect = new Rectangle(b.getSpriteX(), b.getSpriteY(), b.spriteWidth, b.spriteHeight);
        Rectangle pRect = new Rectangle(getSpriteX(), getSpriteY(), spriteWidth, spriteHeight);
        return bRect.intersects(pRect);
    }
}
