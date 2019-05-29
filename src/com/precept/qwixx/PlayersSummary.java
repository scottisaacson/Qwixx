/**********************************************************
 *  Copyright 2019, The Precept Group, LLC
 *  See the LICENSE file for license information.
 *
 *********************************************************/

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

public class PlayersSummary extends JPanel {

    
    public Game game;
    public JFrame frame;
    public int size;
    
    public ArrayList<PlayerSummary> players;
 
    public PlayersSummary(Game game) 
    {
        this.game = game;
        this.frame = game.frame;
        this.size = game.players.size();
        this.players = null;
    }
    
    public void buildAndShow() {
        
        setLayout(null);
        setBackground(Qwixx.myback1);
        setBorder(BorderFactory.createLineBorder(Color.black));
        
        players = new ArrayList<PlayerSummary>();

        int fullWidth = 460;
        int fullHeight = (size * 50) + 50;
                
        setSize(fullWidth, fullHeight);

        int top = 10;
        int left = 10;
        
        int pWidth = 440;
        int pHeight = 50;
        
        for (Player p : game.players)
        {
            PlayerSummary ps = new PlayerSummary(p);
            ps.buildAndShow();
            ps.setBounds(left, top, pWidth, pHeight);
            add(ps);
            players.add(ps);
            top = top + pHeight + 10;
        }
        
        setVisible(true);

    }
    
    
    public void update() 
    {
        
        for (PlayerSummary ps : players)
        {
            remove(ps);
        }
        
        int fullWidth = 460;
        int fullHeight = (size * 50) + 40;

        int top = 10;
        int left = 10;
        
        int pWidth = 440;
        int pHeight = 50;
        
        for (Player p : game.players)
        {
            PlayerSummary ps = new PlayerSummary(p);
            ps.buildAndShow();
            ps.setBounds(left, top, pWidth, pHeight);
            add(ps);
            players.add(ps);
            top = top + pHeight + 10;
        }

        invalidate();
        repaint();
        setVisible(true);

    }
    
}
