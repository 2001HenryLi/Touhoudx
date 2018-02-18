package com.th;

public class TouhouDX {
    private BGMusic bgm = new BGMusic();
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
        if(!pp.gameOver && !pp.win){
            pp.update();
            UI.update();
        }
        else if(pp.gameOver && !pp.win){
            gp.update();
        }
        else if(pp.win)
        {
            System.out.println("DK DONKEY KONG");
            wp.update();
        }
    }
}
