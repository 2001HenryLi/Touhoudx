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

    private StartPanel sp = null;
    private GlassPanel gp = new GlassPanel();
    private JPanel mainPanel = new JPanel();

    private TouhouDX tdx = new TouhouDX();
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

        ScheduledExecutorService exe = Executors.newSingleThreadScheduledExecutor();
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
        mainPanel.remove(sp);
        mainPanel.add(tdx.pp);
        mainPanel.add(tdx.UI);
        getGlassPane().setVisible(true);
        setVisible(true);

        ScheduledExecutorService exe = Executors.newSingleThreadScheduledExecutor();
        exe.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                tdx.update();
            }
        }, 0 , 1000/FPS, TimeUnit.MILLISECONDS);
    }
}