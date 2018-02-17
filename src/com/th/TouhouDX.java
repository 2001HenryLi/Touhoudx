package com.th;

import java.util.concurrent.*;

public class TouhouDX {
    private BGMusic bgm = new BGMusic();
    public Player p = new Player();
    public PlayPanel pp = new PlayPanel(p);
    public UIPanel UI = new UIPanel();


    public static final int FPS = 60;

    public TouhouDX () {}

    public void startGame(){
        ScheduledExecutorService exe = Executors.newSingleThreadScheduledExecutor();
        exe.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                update();
            }
        }, 0 , 1000/FPS, TimeUnit.MILLISECONDS);
    }

    private void update(){
        pp.update();
        UI.update();
    }
}