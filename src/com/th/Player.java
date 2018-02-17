package com.th;

import java.awt.*;

public class Player {
    public Image sprite;
    public Image hitMarker;

    public int x, y;
    public int vx, vy;

    public Player(){
        sprite = Toolkit.getDefaultToolkit().getImage("resources\\CharacterSprites\\hitbox.png");;
        x = 100;
        y = 100;
        vx = 5;
        vy = 5;
    }
    public void move(int xDir, int yDir){
        x += vx*xDir;
        y += vy*yDir;
        System.out.println("moved");
    }
}
