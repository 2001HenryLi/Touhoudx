package com.th;

public class TouhouDX {
    private BGMusic bgm = new BGMusic();
    public Player p;
    public PlayPanel pp;
    public UIPanel UI = new UIPanel();

    public TouhouDX () {
        System.out.println(1);
        p = new Player(pp);
        System.out.println(2);
        pp = new PlayPanel(p);
        System.out.println(3);
    }

    public void update(){
        System.out.println("up");
        pp.update();
        System.out.println("date");
        UI.update();

    }
}
