package com.th;

public class TouhouDX {
    private BGMusic bgm = new BGMusic();
    public Player p = new Player();
    public PlayPanel pp = new PlayPanel(p);
    public UIPanel UI = new UIPanel();

    public TouhouDX () {}

    public void update(){
        pp.update();
        UI.update();
    }
}
