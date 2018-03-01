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
    private boolean isInVuln = false;

    public PlayPanel pp;

    private final String SPRITE_DIRECTORY = "Resources/CharacterSprites/";
    private String name;
    private String bulletType;

    private BufferedImage sprite;
    private BufferedImage[] sprites = new BufferedImage[7];
    private int spriteIndex = 3;
    private int spriteWidth = 64;
    private int spriteHeight = 64;
    private BufferedImage hitbox;
    private int hitboxWidth = 16;
    private int hitboxHeight = 16;
    private int hitboxSpriteWidth = 64;
    private int hitboxSpriteHeight = 64;

    private String bulletPath = "Resources/ProjectileSprites/";
    private String bombPath = "Resources/ProjectileSprites/CircleLargeBoss.png";
    private BufferedImage[] bullets = new BufferedImage[2];

    private int x = 1280 * 3 / 5 / 2;
    private int y = 900;
    private int vx = 8;
    private int vy = 8;

    public int bombs = 3;
    public volatile boolean canBomb = true;
    public int lives = 3;

    private BulletPattern BOMB = new BulletPattern() {
        @Override
        public ArrayList<Bullet> makePattern() {
            ArrayList<Bullet> pattern = new ArrayList<Bullet>();
            double offset = Math.random()* 2 * Math.PI;
            for(int i = 0; i < 16; i++){
                double radians = 2 * Math.PI * i / 16 + offset;
                pattern.add(new Bomb(bullets[1], x - 32, y - 32, 64, 64, new MovePath() {
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

    public Player(String n){
        name = n;
        if(name.equals("cirno")) bulletType = "Cusp.png";
        else bulletType = "VertTangent.png";
        startTime = System.nanoTime();
        try {
            sprite = ImageIO.read(new File(SPRITE_DIRECTORY+name+".png"));
            sprites[0] = ImageIO.read(new File(SPRITE_DIRECTORY+name+"Left.png"));
            sprites[1] = ImageIO.read(new File(SPRITE_DIRECTORY+name+"transitleft2.png"));
            sprites[2] = ImageIO.read(new File(SPRITE_DIRECTORY+name+"transitleft1.png"));
            sprites[3] = ImageIO.read(new File(SPRITE_DIRECTORY+name+".png"));
            sprites[4] = ImageIO.read(new File(SPRITE_DIRECTORY+name+"transitright1.png"));
            sprites[5] = ImageIO.read(new File(SPRITE_DIRECTORY+name+"transitright2.png"));
            sprites[6] = ImageIO.read(new File(SPRITE_DIRECTORY+name+"Right.png"));
            bullets[0] = ImageIO.read(new File(bulletPath+bulletType));
            bullets[1] = ImageIO.read(new File(bombPath));
            hitbox = ImageIO.read(new File("Resources/CharacterSprites/eff_sloweffect.png"));
        } catch(IOException e) {
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
            synchronized (pp.playerProjectiles) {
                SFX.playOnce("Resources/SfX/se_etbreak.wav");
                pp.playerProjectiles.add(new Bullet(bullets[0], x + spriteWidth / 2, y, 32, 32, new MovePath() {
                    @Override
                    public int[] move(long t, int x0, int y0) {
                        int[] pos = {x0, y0};
                        pos[0] = x0;
                        pos[1] = y0 - (int) (TimeUnit.MILLISECONDS.convert(t, TimeUnit.NANOSECONDS) * 3);
                        return pos;
                    }
                }));
                pp.playerProjectiles.add(new Bullet(bullets[0], x - spriteWidth / 2, y, 32, 32, new MovePath() {
                    @Override
                    public int[] move(long t, int x0, int y0) {
                        int[] pos = {x0, y0};
                        pos[0] = x0;
                        pos[1] = y0 - (int) (TimeUnit.MILLISECONDS.convert(t, TimeUnit.NANOSECONDS) * 3);
                        return pos;
                    }
                }));
            }
        }
    }
    public void bomb(){
        if(bombs > 0 && canBomb){
            if(isInVuln)
                SFX.playOnce("Resources\\SFX\\DEAD.wav");
            else
                SFX.playOnce("Resources\\SFX\\SPELLCARD.wav");
            ArrayList<Bullet> addProjectiles = BOMB.makePattern();
            for(Bullet b : addProjectiles) pp.bombProjectiles.add((Bomb)b);
            bombs--;
            canBomb = false;
        }
    }

    public boolean takeDamage(Bullet b){
        if(b.getRect().intersects(getRect()) && !isInVuln){
            inVulnTime = System.nanoTime() - startTime;
            lives--;
            isInVuln = true;
            try {
                sprites[0] = ImageIO.read(new File(SPRITE_DIRECTORY+name+"Lefthit.png"));
                sprites[3] = ImageIO.read(new File(SPRITE_DIRECTORY+name+"hit.png"));
                sprites[6] = ImageIO.read(new File(SPRITE_DIRECTORY+name+"Righthit.png"));
            } catch(IOException e) {
                System.exit(-1);
            }
            x = 1280 * 3 / 5 / 2;
            y = 960;
            bombs = 4;
            canBomb = true;
            bomb();
            canBomb = true;
            return true;
        }
        return false;
    }

    public void drawSprite(Graphics g, ImageObserver imageObserver){
        g.drawImage(sprite, getSpriteX(), getSpriteY(), spriteWidth, spriteHeight, imageObserver);
    }
    public void drawHitbox(Graphics g, ImageObserver imageObserver){
        g.drawImage(hitbox, getSpriteX(), getSpriteY(), hitboxSpriteWidth, hitboxSpriteHeight, imageObserver);
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
    public Rectangle getRect(){
        return new Rectangle(getHitboxX(), getHitboxY(), hitboxWidth, hitboxHeight);
    }
}
