/****************************************************************************
 * 2019 The Precept Group, LLC. 
 * See LICENSE for license information.
 ***************************************************************************/

package com.precept.qwixx;

import javax.swing.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.lang.*;
import java.io.*;
import java.net.*;
import java.text.*;

public class MyImage extends JPanel {

    private Image blue1;
    private Image red6;
    private Image yellow3;
    private Image green5;
    private Image white4;
    private Image white2;

    public MyImage() {

        initPanel();
    }

    private void initPanel() {

        //loadImage();
        var dm = new Dimension(500, 300);
        setPreferredSize(dm);

        blue1 = Qwixx.getResourceImage(this, "blue1_50.png");

        red6 = Qwixx.getResourceImage(this, "red6_50.png");

        yellow3 = Qwixx.getResourceImage(this, "yellow3_50.png");

        green5 = Qwixx.getResourceImage(this, "green5_50.png");

        white4 = Qwixx.getResourceImage(this, "white4_50.png");

        white2 = Qwixx.getResourceImage(this, "white2_50.png");

    }


    private void doDrawing(Graphics g) {

        var g2d = (Graphics2D) g;
        int x, y;
        
        g2d.setColor(Qwixx.myback1);
        g2d.fillRect(0, 0, 400, 300);

        
        g2d.setRenderingHint(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        
        Font font = new Font("ARIEL", Font.BOLD, 36);
        g2d.setFont(font);
        g2d.setColor(Color.BLACK);

        
        
        // 400 x 300
        x = 135;
        y = 165;
        
        g2d.drawString("QWIXX", x, y);
        
        g2d.drawImage(blue1, 20, 20, null);
        g2d.drawImage(red6, 330, 20, null);
        g2d.drawImage(yellow3, 20, 230, null);
        g2d.drawImage(green5, 330, 230, null);

        g2d.drawImage(white4, 50, 125, null);
        g2d.drawImage(white2, 300, 125, null);
        
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }
}
