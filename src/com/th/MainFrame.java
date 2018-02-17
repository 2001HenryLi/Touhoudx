package com.th;

import java.util.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame {
    private final double MASTER_SCALE = 1.0;
    private StartPanel sp = null;
    private GlassPanel gp = new GlassPanel();
    private JPanel mainPanel = new JPanel();

    private int stageStart = 0;
    public MainFrame(){
        super("Touhou DX");
        mainPanel.setPreferredSize(new Dimension(612,925));
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        setSize((int)(618*MASTER_SCALE),(int)(954*MASTER_SCALE));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);  //let's keep things simple, aight?
        setIconImage(Toolkit.getDefaultToolkit().getImage("resources\\misc\\logo.png"));
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
        if(sp.getInput()) stageStart = 46;
    }
    public void run(){
        mainPanel.remove(sp);
        //mainPanel.add(pd.ep);
        //mainPanel.add(pd.bp);
        //pack();
        getGlassPane().setVisible(true);
        setVisible(true);
        //pd.startGame(stageStart);
        if(true){  //win

        }
        else{  //loss

        }
        System.exit(0);
    }
}