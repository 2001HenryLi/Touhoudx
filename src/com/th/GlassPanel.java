package com.th;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class GlassPanel extends JPanel implements MouseListener, MouseMotionListener{
    public final double MASTER_SCALE = 1.0;
    public GlassPanel(){
        setOpaque(false);
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    public void paintComponent(Graphics g) {

    }
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    public void mouseClicked(MouseEvent e){}
    public void mouseDragged(MouseEvent e){}
    public void mouseMoved(MouseEvent e){}
}
