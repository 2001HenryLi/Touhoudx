package com.th;

public class TouhouDX {
    private BGMusic bgm = new BGMusic();
    public Player p;
    public PlayPanel pp;
    public UIPanel UI = new UIPanel();

    public TouhouDX () {
        p = new Player(pp);
        pp = new PlayPanel(p);
        p.pp = pp;
    }

    public void update(){
        pp.update();
        UI.update();
    }
}
