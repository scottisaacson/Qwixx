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

public class TurnStatusPlayer extends JPanel {

    public Game game;
    public JFrame frame;
    public Player player;
    JLabel name;
    WhichTurn white;
    WhichTurn color;
 
    public TurnStatusPlayer(Player player) 
    {
        super();
        this.game = player.game;
        this.frame = player.game.frame;
        this.player = player;
    }
    
    public void buildAndShow() {
        
        setLayout(null);
        setBackground(Qwixx.myback1);

        int fullWidth = 200;
        int fullHeight = 100;
                
        setSize(fullWidth, fullHeight);

        int top = 5;
        int left = 5;
        
        int nameWidth = 190;
        int nameHeight = 40;
        
        name = new JLabel(player.name);
        name.setOpaque(true);
        name.setHorizontalAlignment(SwingConstants.CENTER);             
        name.setBackground(Color.gray);
        name.setFont(Qwixx.myfont14);
        name.setSize(nameWidth, nameHeight);
        name.setBounds(left, top, nameWidth, nameHeight);
        name.setVisible(true);
        add(name);

       
        int whichWidth = 90;
        int whichHeight = 45;
        
        top = top + nameHeight + 5;
        left = 5;
        
        white = new WhichTurn(WhichTurn.TYPE.WHITE);
        white.buildAndShow();
        white.setBounds(left, top, whichWidth, whichHeight);
        add(white);

        left = left + whichWidth + 10;

        color = new WhichTurn(WhichTurn.TYPE.COLOR);
        color.buildAndShow();
        color.setBounds(left, top, whichWidth, whichHeight);
        add(color);
            
        setVisible(true);

    }
}
