package com.th;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Player {
    private long startTime = -1;
    private long elapsedTime = 0;

    private long inVulnTime = 0;
    public boolean isInVuln = false;

    public PlayPanel pp;

    public final String SPRITE_DIRECTORY = "Resources/CharacterSprites/";
    public String name = "cirno";
    public BufferedImage sprite;
    public BufferedImage[] sprites = new BufferedImage[7];
    public int spriteIndex = 3;

    public int spriteWidth = 64;
    public int spriteHeight = 64;
    public BufferedImage hitbox;
    public int hitboxWidth = 16;
    public int hitboxHeight = 16;
    String bulletPath = "Resources/ProjectileSprites/Cusp.png";

    public int x = 1280 * 3 / 5 / 2;
    public int y = 900;
    public int vx = 8;
    public int vy = 8;

    public int bombs = 3;
    public int lives = 3;

    private BulletPattern BOMB = new BulletPattern() {
        @Override
        public ArrayList<Bullet> makePattern() {
            ArrayList<Bullet> pattern = new ArrayList<Bullet>();
            double offset = Math.random()* 2 * Math.PI;
            for(int i = 0; i < 16; i++){
                double radians = 2 * Math.PI * i / 16 + offset;
                pattern.add(new Bomb("Resources\\ProjectileSprites\\CircleLarge.png", x - 32, y - 32, 64, 64, new MovePath() {
                    @Override
                    public int[] move(long t, int x0, int y0) {
                        int[] pos = {x0, y0};
                        long tMilli = TimeUnit.MILLISECONDS.convert(t, TimeUnit.NANOSECONDS);
                        pos[0] = x0 + (int)(Math.sin(radians) * tMilli / 4);
                        pos[1] = y0 + (int)(Math.cos(radians) * tMilli / 4);
                        return pos;
                    }
                }));
            }
            return pattern;
        }
    };

    public Player(PlayPanel p){
        startTime = System.nanoTime();
        pp = p;
        try {
            sprite = ImageIO.read(new File(SPRITE_DIRECTORY+name+".png"));
            sprites[0] = ImageIO.read(new File(SPRITE_DIRECTORY+name+"Left.png"));
            sprites[1] = ImageIO.read(new File(SPRITE_DIRECTORY+name+"transitleft2.png"));
            sprites[2] = ImageIO.read(new File(SPRITE_DIRECTORY+name+"transitleft1.png"));
            sprites[3] = ImageIO.read(new File(SPRITE_DIRECTORY+name+".png"));
            sprites[4] = ImageIO.read(new File(SPRITE_DIRECTORY+name+"transitright1.png"));
            sprites[5] = ImageIO.read(new File(SPRITE_DIRECTORY+name+"transitright2.png"));
            sprites[6] = ImageIO.read(new File(SPRITE_DIRECTORY+name+"Right.png"));

            hitbox = ImageIO.read(new File("Resources/CharacterSprites/hitbox.png"));
        } catch(IOException e) {
            System.out.println("failed");
            System.exit(-1);
        }
    }
    public void update(){
        if(startTime == -1) startTime = System.nanoTime();
        elapsedTime = System.nanoTime() - startTime;

        if(isInVuln && elapsedTime - inVulnTime >= 1000000000){
            isInVuln = false;
            try {
                sprites[0] = ImageIO.read(new File(SPRITE_DIRECTORY+name+"Left.png"));
                sprites[3] = ImageIO.read(new File(SPRITE_DIRECTORY+name+".png"));
                sprites[6] = ImageIO.read(new File(SPRITE_DIRECTORY+name+"Right.png"));
            } catch(IOException e) {
                System.out.println("failed");
                System.exit(-1);
            }
        }

        move();
        shoot();
    }
    private void move(){
        if(pp.keysDown[0]){
            x -= vx / (pp.keysDown[4] ? 2 : 1);
            spriteIndex--;
        }
        if(pp.keysDown[1]){
            x += vx / (pp.keysDown[4] ? 2 : 1);
            spriteIndex++;
        }
        if(pp.keysDown[2]) y -= vy / (pp.keysDown[4] ? 2 : 1);
        if(pp.keysDown[3]) y += vy / (pp.keysDown[4] ? 2 : 1);

        x = Math.min(x + spriteWidth/2, 1280 * 3 / 5);
        x = Math.max(x - spriteWidth/2, 0);
        y = Math.min(y + spriteHeight/2, 960);
        y = Math.max(y - spriteHeight/2, 0);

        spriteIndex = Math.max(0, spriteIndex);
        spriteIndex = Math.min(6, spriteIndex);
        sprite = sprites[spriteIndex];
        if(spriteIndex < 3) spriteIndex++;
        if(spriteIndex > 3) spriteIndex--;
    }

    private void shoot(){
        if(pp.keysDown[5]){
            pp.projectiles.add(new Bullet(bulletPath, x + spriteWidth/2, y, 32, 32, new MovePath() {
                @Override
                public int[] move(long t, int x0, int y0) {
                    int[] pos = {x0, y0};
                    pos[0] = x0;
                    pos[1] = y0 - (int)(TimeUnit.MILLISECONDS.convert(t, TimeUnit.NANOSECONDS) * 3);
                    return pos;
                }
            }));
            pp.projectiles.add(new Bullet(bulletPath, x - spriteWidth/2, y, 32, 32, new MovePath() {
                @Override
                public int[] move(long t, int x0, int y0) {
                    int[] pos = {x0, y0};
                    pos[0] = x0;
                    pos[1] = y0 - (int)(TimeUnit.MILLISECONDS.convert(t, TimeUnit.NANOSECONDS) * 3);
                    return pos;
                }
            }));
        }
        if(pp.keysDown[6] && bombs > 0){
            ArrayList<Bullet> addProjectiles = BOMB.makePattern();
            for(Bullet b : addProjectiles) pp.bombProjectiles.add((Bomb)b);
            bombs--;
        }
    }

    public boolean takeDamage(Bullet b){
        Rectangle bRect = new Rectangle(b.getSpriteX(), b.getSpriteY(), b.spriteWidth, b.spriteHeight);
        Rectangle pRect = new Rectangle(getHitboxX(), getHitboxY(), hitboxWidth, hitboxHeight);
        if(bRect.intersects(pRect) && !isInVuln){
            inVulnTime = System.nanoTime() - startTime;
            lives--;
            isInVuln = true;
            try {
                sprites[0] = ImageIO.read(new File(SPRITE_DIRECTORY+name+"Lefthit.png"));
                sprites[3] = ImageIO.read(new File(SPRITE_DIRECTORY+name+"hit.png"));
                sprites[6] = ImageIO.read(new File(SPRITE_DIRECTORY+name+"Righthit.png"));
            } catch(IOException e) {
                System.out.println("failed");
                System.exit(-1);
            }
            x = 1280 * 3 / 5 / 2;
            y = 960;
            return true;
        }
        return false;
    }

    public boolean isAlive(){
        return lives > 0;
    }
    public int getSpriteX(){
        return x - spriteWidth/2;
    }
    public int getSpriteY(){
        return y - spriteHeight/2;
    }
    public int getHitboxX(){
        return x - hitboxWidth/2;
    }
    public int getHitboxY(){
        return y - hitboxHeight/2;
    }
}
