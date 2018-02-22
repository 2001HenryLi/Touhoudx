package com.th;

import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame {
    private SelectPanel slp;
    private StartPanel sp;
    private GlassPanel gp = new GlassPanel();
    private JPanel mainPanel = new JPanel();

    private TouhouDX tdx;
    private volatile boolean gameOverSwitch;
    private volatile boolean restartSwitch;
    private volatile boolean winSwitch;

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
        while(true) {
            mf.start();
            mf.run();
        }
    }

    public void start(){
        gameOverSwitch = false;
        winSwitch = false;
        restartSwitch = false;

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

        UpdateRunner.run(new Runnable() {
            @Override
            public void run() {
                update();
            }
        });
        while(!restartSwitch){}
    }

    public void update(){
        if(!gameOverSwitch && tdx.pp.gameOver) gameOver();
        else if(!winSwitch && tdx.pp.win) win();
        else if(!restartSwitch && (tdx.gp.restart || tdx.wp.restart)){
            UpdateRunner.stop();
            restartSwitch = true;
        }
        tdx.update();
    }

    public void win(){
        mainPanel.setVisible(false);
        mainPanel.removeAll();
        mainPanel.add(tdx.wp);
        mainPanel.setVisible(true);

        winSwitch = true;
        gameOverSwitch = false;
        restartSwitch = false;
    }

    public void gameOver(){
        mainPanel.setVisible(false);
        mainPanel.removeAll();
        mainPanel.add(tdx.gp);
        mainPanel.setVisible(true);

        gameOverSwitch = true;
        restartSwitch = false;
        winSwitch = false;
    }
}