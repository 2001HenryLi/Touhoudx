package com.th;

public class TouhouDX {
    private BGMusic bgm = new BGMusic();
    public Player p;
    public Boss b;
    public PlayPanel pp;
    public UIPanel UI;
    public GameOverPanel gp = new GameOverPanel();
    public WinPanel wp = new WinPanel();
    public boolean music = false;

    public TouhouDX (Player P) {
        p = P;
        b = new Boss(pp);
        pp = new PlayPanel(p, b);
        p.pp = pp;
        b.pp = pp;
        UI = new UIPanel(p);
        if(Math.random() > 0.5) bgm.playBGMusic("Resources\\BGM\\Beloved Tomboyish Girl (Alpha Mix) - Touhou 6_ the Embodiment of Scarlet Devil.wav", 0);
        else bgm.playBGMusic("Resources\\BGM\\Lunatic EyesInvisible Full Moon - Touhou 14.5- Urban Legend in Limbo.wav", 0);
        music = true;
    }

    public void update(){
        if(pp.win){
            wp.update();
            if(music){
                bgm.stop();
                music = false;
            }
        }
        else if(!pp.gameOver && !pp.win){
            pp.update();
            UI.update();
        }
        else if(pp.gameOver && !pp.win){
            gp.update();
            if(music){
                bgm.stop();
                music = false;
            }
        }

    }
}
