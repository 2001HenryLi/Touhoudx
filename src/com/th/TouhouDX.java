package com.th;

public class TouhouDX {
    private BGMusic bgm = new BGMusic();
    public Player p;
    public Boss b;
    public PlayPanel pp;
    public UIPanel UI = new UIPanel();
    public GameOverPanel gp = new GameOverPanel();

    public TouhouDX () {
        p = new Player(pp);
        b = new Boss(pp);
        pp = new PlayPanel(p, b);
        p.pp = pp;
        b.pp = pp;
    }

    public void update(){
        pp.update();
        UI.update();
    }
}
