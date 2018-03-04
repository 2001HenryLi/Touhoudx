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
        BGMusic.setVolume(-10f);
        SFX.setVolume(-20f);
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

        UpdateRunner.run(new Runnable() {
            @Override
            public void run() {
                slp.update();
            }
        });
        mainPanel.setVisible(true);
        slp.requestFocus();
        tdx = new TouhouDX(slp.waitForInput());

        mainPanel.setVisible(false);
        mainPanel.removeAll();
        mainPanel.add(tdx.pp);
        mainPanel.add(tdx.UI);
        mainPanel.setVisible(true);
        tdx.pp.requestFocus();

        BGMusic.playLoop("Resources\\BGM\\corno.wav", 0, -1);
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
        tdx.wp.requestFocus();
    }

    public void gameOver(){
        BGMusic.playOnce("Resources\\BGM\\Game Over - Super Mario World.wav");
        mainPanel.setVisible(false);
        mainPanel.removeAll();
        mainPanel.add(tdx.gp);
        mainPanel.setVisible(true);
        tdx.pp.gameOver = false;
        tdx.gp.requestFocus();
    }
}