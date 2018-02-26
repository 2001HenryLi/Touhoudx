package com.th;

import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame {
    private SelectPanel slp;
    private StartPanel sp;
    private GlassPanel gp = new GlassPanel();
    private JPanel mainPanel = new JPanel();

    private TouhouDX tdx;
    private volatile boolean restartSwitch;

    public MainFrame(){
        super("L' TouHoupital DX");
        mainPanel.setPreferredSize(new Dimension(ScaleDimentions.WIDTH, ScaleDimentions.HEIGHT));
        mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));

        setSize(ScaleDimentions.WIDTH + 6, ScaleDimentions.HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage("resources\\misc\\logo.jpg"));
        setGlassPane(gp);
        getGlassPane().setVisible(false);
    }

    public static void main(String[] args){
        MainFrame mf = new MainFrame();
        BGMusic.setVolume(-80f);
        SFX.setVolume(-80f);
        while(true) {
            mf.start();
            mf.run();
        }
    }

    public void start(){
        BGMusic.playLoop("Resources\\BGM\\Title Theme - Super Mario World.wav", 0, -1);

        sp = new StartPanel();
        mainPanel.removeAll();
        mainPanel.add(sp);
        setContentPane(mainPanel);
        setVisible(true);
        UpdateRunner.run(new Runnable() {
            @Override
            public void run() {
                sp.update();
            }
        });
        sp.waitForInput();
    }

    public void run(){
        BGMusic.playLoop("Resources\\BGM\\Mint Espresso - Kirby Cafe.wav", 0, -1);
        slp = new SelectPanel();
        mainPanel.setVisible(false);
        mainPanel.removeAll();
        mainPanel.add(slp);
        slp.setVisible(true);

        UpdateRunner.run(new Runnable() {
            @Override
            public void run() {
                slp.update();
            }
        });
        mainPanel.setVisible(true);
        tdx = new TouhouDX(slp.waitForFocus());

        mainPanel.removeAll();
        mainPanel.add(tdx.pp);
        mainPanel.add(tdx.UI);
        getGlassPane().setVisible(true);
        setVisible(true);
        if(Math.random() >= 0.5) BGMusic.playLoop("Resources\\BGM\\Lunatic Eyes Invisible Full Moon - Touhou 14.5- Urban Legend in Limbo.wav", 0, -1);
        else BGMusic.playLoop("Resources\\BGM\\Beloved Tomboyish Girl (Alpha Mix) - Touhou 6_ the Embodiment of Scarlet Devil.wav", 0, -1);
        UpdateRunner.run(new Runnable() {
            @Override
            public void run() {
                update();
            }
        });
        while(!restartSwitch){}
        restartSwitch = false;
    }

    public void update(){
        if(tdx.pp.gameOver) gameOver();
        else if(tdx.pp.win) win();
        else if(tdx.gp.restart || tdx.wp.restart){
            UpdateRunner.stop();
            restartSwitch = true;
            return;
        }
        tdx.update();
    }

    public void win(){
        BGMusic.playLoop("Resources\\BGM\\Battle Victory - Mario & Luigi Bowser's Inside Story.wav", 0, -1);
        mainPanel.setVisible(false);
        mainPanel.removeAll();
        mainPanel.add(tdx.wp);
        mainPanel.setVisible(true);
        tdx.pp.win = false;
    }

    public void gameOver(){
        BGMusic.playOnce("Resources\\BGM\\Game Over - Super Mario World.wav");
        mainPanel.setVisible(false);
        mainPanel.removeAll();
        mainPanel.add(tdx.gp);
        mainPanel.setVisible(true);
        tdx.pp.gameOver = false;
    }
}