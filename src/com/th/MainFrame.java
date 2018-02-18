package com.th;

import java.util.*;
import java.io.*;
import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

public class MainFrame extends JFrame {
    private final double MASTER_SCALE = 1.0;
    private final int WIDTH = 1280;
    private final int HEIGHT = 960;
    public static final int FPS = 60;

    ScheduledExecutorService exe;
    private SelectPanel slp;
    private StartPanel sp;
    private GlassPanel gp = new GlassPanel();
    private JPanel mainPanel = new JPanel();

    private TouhouDX tdx;
    private boolean gameOverSwitch = false;
    private boolean restartSwitch = false;
    private boolean winSwitch = false;

    public MainFrame(){
        super("Touhou DX");
        mainPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));

        setSize((int)(WIDTH*MASTER_SCALE) + 6, (int)(HEIGHT*MASTER_SCALE));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage("resources\\misc\\logo.jpg"));
        setGlassPane(gp);
        getGlassPane().setVisible(false);

        sp = new StartPanel();
        slp = new SelectPanel();
    }

    public static void main(String[] args){
        MainFrame mf = new MainFrame();
        mf.start();
        mf.run();
    }

    public void start(){
        mainPanel.add(sp);
        setContentPane(mainPanel);
        setVisible(true);

        exe = Executors.newSingleThreadScheduledExecutor();
        exe.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                sp.update();
            }
        }, 0 , 1000/FPS, TimeUnit.MILLISECONDS);
        sp.waitForInput();
        exe.shutdown();
    }

    public void run(){
        slp = new SelectPanel();
        mainPanel.setVisible(false);
        mainPanel.remove(sp);
        mainPanel.add(slp);
        slp.setVisible(true);

        exe = Executors.newSingleThreadScheduledExecutor();
        exe.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                slp.update();
            }
        }, 0 , 1000/FPS, TimeUnit.MILLISECONDS);
        mainPanel.setVisible(true);
        tdx = new TouhouDX(slp.waitForFocus());
        exe.shutdown();

        mainPanel.remove(slp);
        mainPanel.add(tdx.pp);
        mainPanel.add(tdx.UI);
        getGlassPane().setVisible(true);
        setVisible(true);

        exe = Executors.newSingleThreadScheduledExecutor();
        exe.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                update();
            }
        }, 0 , 1000/FPS, TimeUnit.MILLISECONDS);
    }

    public void update(){
        if(!gameOverSwitch && tdx.pp.gameOver) gameOver();
        else if(!winSwitch && tdx.pp.win) win();
        else if(!restartSwitch && (tdx.gp.restart || tdx.wp.restart)) restart();
        tdx.update();
    }

    public void win(){
        mainPanel.setVisible(false);
        mainPanel.remove(tdx.pp);
        mainPanel.remove(tdx.UI);
        mainPanel.add(tdx.wp);
        mainPanel.setVisible(true);

        winSwitch = true;
        gameOverSwitch = false;
        restartSwitch = false;
    }

    public void gameOver(){
        mainPanel.setVisible(false);
        mainPanel.remove(tdx.pp);
        mainPanel.remove(tdx.UI);
        mainPanel.add(tdx.gp);
        mainPanel.setVisible(true);

        gameOverSwitch = true;
        restartSwitch = false;
        winSwitch = false;
    }

    public void restart(){
        exe.shutdown();
        mainPanel.setVisible(false);
        if(gameOverSwitch) mainPanel.remove(tdx.gp);
        else if(winSwitch) mainPanel.remove(tdx.wp);

        sp = new StartPanel();
        mainPanel.add(sp);
        mainPanel.setVisible(true);
        sp.requestFocus();
        sp.waitForInput();
        run();

        restartSwitch = true;
        gameOverSwitch = false;
        winSwitch = false;
    }

}