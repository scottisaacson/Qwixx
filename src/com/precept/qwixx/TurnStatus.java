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

public class TurnStatus extends JPanel {

    public Game game;
    public JFrame frame;
    public int numPlayers;
    ArrayList<TurnStatusPlayer> players;
 
    public TurnStatus(Game game) 
    {
        this.game = game;
        this.frame = game.frame;
        this.numPlayers = game.players.size();
        this.players = null;
        
    }
    
    public void buildAndShow() {
        
        setLayout(null);
        setBackground(Qwixx.myback1);
        setBorder(BorderFactory.createLineBorder(Color.black));

        int fullWidth = 820;
        int fullHeight = 120;
                
        setSize(fullWidth, fullHeight);

        int tspWidth = 200;
        int tspHeight = 100;
        
        int top = 0;
        int left = 0;
        
        players = new ArrayList<TurnStatusPlayer>();
        
        for (Player p : game.players)
        {
            TurnStatusPlayer tsp = new TurnStatusPlayer(p);
            players.add(tsp);
            
            tsp.buildAndShow();
            tsp.setBounds(left+10, top+10, tspWidth, tspHeight);
            add(tsp);
            
            left = left + tspWidth + 10;
            
        }
        
        
        setVisible(true);

    }
    
    public void setPlayerTurn(Player p, WhichTurn.TYPE type)
    {
        for (TurnStatusPlayer tsp : players)
        {
            if (tsp.player.equals(p))
            {
                tsp.name.setBackground(Color.yellow);
                if (type == WhichTurn.TYPE.COLOR)
                {
                    tsp.color.color = Color.yellow;
                    tsp.white.color = Color.gray;
                }
                else
                {
                    tsp.color.color = Color.gray;
                    tsp.white.color = Color.yellow;
                }
            }
            else if (tsp.player.equals(p.game.current))
            {
                tsp.name.setBackground(Color.orange);
                tsp.color.color = Color.gray;
                tsp.white.color = Color.gray;
            }
            else
            {
                tsp.name.setBackground(Color.gray);
                tsp.color.color = Color.gray;
                tsp.white.color = Color.gray;
            }
            
            tsp.repaint();
            
        }
        
        
    }
    
}
