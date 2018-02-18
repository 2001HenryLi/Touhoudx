package com.th;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Player {
    public PlayPanel pp;

    public BufferedImage sprite;
    public int spriteWidth = 64;
    public int spriteHeight = 64;
    public BufferedImage hitbox;
    public int hitboxWidth = 16;
    public int hitboxHeight = 16;

    public int x = 100;
    public int y = 100;
    public int vx = 8;
    public int vy = 8;

    public Player(PlayPanel p){
        pp = p;
        try {
            sprite = ImageIO.read(new File("Resources/CharacterSprites/cirno.png"));
            hitbox = ImageIO.read(new File("Resources/CharacterSprites/hitbox.png"));
        } catch(IOException e) {
            System.out.println("failed");
            System.exit(-1);
        }
    }
    public void update(){
        move();
        shoot();
    }
    private void move(){
        if(pp.keysDown[0]) x -= vx / (pp.keysDown[4] ? 2 : 1);
        if(pp.keysDown[1]) x += vx / (pp.keysDown[4] ? 2 : 1);
        if(pp.keysDown[2]) y -= vy / (pp.keysDown[4] ? 2 : 1);
        if(pp.keysDown[3]) y += vy / (pp.keysDown[4] ? 2 : 1);
        x = Math.min(x + spriteWidth/2, 1280 * 3 / 5);
        x = Math.max(x - spriteWidth/2, 0);
        y = Math.min(y + spriteHeight/2, 960);
        y = Math.max(y - spriteHeight/2, 0);
    }

    private void shoot(){
        if(pp.keysDown[5]){
            pp.projectiles.add(new Bullet(x + spriteWidth/2, y, new MovePath() {
                @Override
                public int[] move(long t, int x0, int y0) {
                    int[] pos = {x0, y0};
                    pos[0] = x0;
                    pos[1] = y0 - (int)(TimeUnit.MILLISECONDS.convert(t, TimeUnit.NANOSECONDS) * 3);
                    return pos;
                }
            }));
            pp.projectiles.add(new Bullet(x - spriteWidth/2, y, new MovePath() {
                @Override
                public int[] move(long t, int x0, int y0) {
                    int[] pos = {x0, y0};
                    pos[0] = x0;
                    pos[1] = y0 - (int)(TimeUnit.MILLISECONDS.convert(t, TimeUnit.NANOSECONDS) * 3);
                    return pos;
                }
            }));
        }
        if(pp.keysDown[6]){
        }
    }

    public int getSpriteX(){
        return x - spriteWidth/2;
    }
    public int getSpriteY(){
        return y - spriteWidth/2;
    }
    public int getHitboxX(){
        return x - hitboxWidth/2;
    }
    public int getHitboxY(){
        return y - hitboxWidth/2;
    }
}
