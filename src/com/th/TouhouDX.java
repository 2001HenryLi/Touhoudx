package com.th;

public class TouhouDX {
    public Player p;
    public Boss b;
    public PlayPanel pp;
    public UIPanel UI;
    public GameOverPanel gp = new GameOverPanel();
    public WinPanel wp = new WinPanel();

    public TouhouDX (Player P) {
        p = P;
        b = new Boss(pp);
        pp = new PlayPanel(p, b);
        p.pp = pp;
        b.pp = pp;
        UI = new UIPanel(p);
    }

    public void update(){
        if(wp.getParent() != null){
            wp.update();
        }
        else if(pp.getParent() != null){
            pp.update();
            UI.update();
        }
        else if(gp.getParent() != null){
            gp.update();
        }
    }
}
