/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.precept.qwixx;

import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.net.*;

class RolledDie extends JPanel {

    public static final String red = "red";
    public static final String yellow = "yellow";
    public static final String green = "green";
    public static final String blue = "blue";
    public static final String white = "blue";
    
    Die die;
    private Image image;

    Game.COLORS c;
    String colorName;
    boolean locked;
            
    String filename;

    public RolledDie(Die die, Game.COLORS c) {
    
  
        this.die = die;
        this.c = c;
        if (c == Game.COLORS.GREEN)
        {
            colorName = green;
        }
        if (c == Game.COLORS.BLUE)
        {
            colorName = "blue";
        }
        if (c == Game.COLORS.YELLOW)
        {
            colorName = "yellow";
        }
        if (c == Game.COLORS.RED)
        {
            colorName = "red";
        }
        if (c == Game.COLORS.WHITE)
        {
            colorName = "white";
        }

        image = Qwixx.getResourceImage(this, colorName + die.val + "_50.png");

        Dimension size = new Dimension(image.getWidth(null), image.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);

    }
    
    public void update(int val) 
    {
    
        die.val = val;
        image = Qwixx.getResourceImage(this, colorName + die.val + "_50.png");
        
        invalidate();

    }
    
  
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, null);
    }
    
    public void lock()
    {
        setVisible(false);
    }
    
    public void unlock()
    {
        setVisible(true);
    }
    
    
    
}
