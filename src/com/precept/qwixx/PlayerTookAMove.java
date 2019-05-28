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

public class PlayerTookAMove extends JDialog {

    public Player player;
    public Game game;
    public JFrame frame;
    public SheetEntry se;
    public Qwixx.MOVETYPE type;
    public boolean isQuit = false;

    
    public PlayerTookAMove(Player player, Qwixx.MOVETYPE type, SheetEntry se) 
    {
        super(player.game.frame, "Player Took A Move", Dialog.ModalityType.APPLICATION_MODAL);
        
        this.player = player;
        this.game = player.game;
        this.frame = game.frame;
        this.type = type;
        this.se = se;
    }
    
    public void buildAndShow() {
        
        Container pane = getContentPane();
        pane.setLayout(null);
        pane.setBackground(Qwixx.myback1);
        
        pane.setLayout(null);
        Insets paneInsets = pane.getInsets();

        String nameTurnString = null;
        String typeString = null;
        if (type == Qwixx.MOVETYPE.COLORS)
        {
            typeString = "COLOR";
        }
        else
        {
            typeString = "WHITE";
        }
        String nameMoveString = null;
                
        nameTurnString = game.current.name + "'s Roll";
        nameMoveString = player.name + "'s " + typeString + " Move";

        
        int left = 10;
        int top = 10;

        JLabel nameTurn = new JLabel(nameTurnString);
        nameTurn.setFont(Qwixx.myfont18);
        nameTurn.setBackground(Color.white);
        nameTurn.setOpaque(true);
        nameTurn.setBounds(left, top, 250, 40);
        nameTurn.setSize(250, 40);
        nameTurn.setHorizontalAlignment(JLabel.CENTER);
        nameTurn.setVerticalAlignment(JLabel.CENTER);        
        nameTurn.setVisible(true);
        pane.add(nameTurn);

        JLabel nameMove = new JLabel(nameMoveString);
        nameMove.setFont(Qwixx.myfont18);
        nameMove.setBackground(Color.white);
        nameMove.setOpaque(true);
        nameMove.setBounds(left + 250 + 20, top, 250, 40);
        nameMove.setSize(250, 40);
        nameMove.setHorizontalAlignment(JLabel.CENTER);
        nameMove.setVerticalAlignment(JLabel.CENTER);        
        nameMove.setVisible(true);
        pane.add(nameMove);

        top = top + 100;
        left = 230;
        
        if (se != null)
        {
            DisplayValue move = null;
            move = new DisplayValue(se);
            move.setBounds(left, top, 50, 50);
            move.setVisible(true);
            pane.add(move);
        }
        else
        {
            JLabel nullMove = null;
            if (type == Qwixx.MOVETYPE.WHITES)
                nullMove = new JLabel(player.name + " SKIPPED");
            if (type == Qwixx.MOVETYPE.COLORS)
                nullMove = new JLabel(player.name + " TOOK A PENALTY");
            
            nullMove.setFont(Qwixx.myfont18);
            nullMove.setBackground(Color.white);
            nullMove.setOpaque(true);
            nullMove.setBounds(left - 160, top, 300, 40);
            nullMove.setSize(400, 40);
            nullMove.setHorizontalAlignment(JLabel.CENTER);
            nullMove.setVerticalAlignment(JLabel.CENTER);        
            nullMove.setVisible(true);
            pane.add(nullMove);
            
        }
        
        top = top + 100;
        left  = 10;
        
        JButton quit = new JButton("QUIT");
        quit.setFont(Qwixx.myfont18);
        quit.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) {
                    isQuit = true;
                    dispose();
                }
            });
        quit.setBounds(left, top, 100, 40);
        quit.setVisible(true);
        pane.add(quit);

        left = 550 - 150;

        JButton ok = new JButton("OK");
        ok.setFont(Qwixx.myfont18);
        ok.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
        ok.setBounds(left, top, 80, 40);
        ok.setVisible(true);
        pane.add(ok);
        
        
        Insets outerInsets = getInsets();
        setSize(
                560 + outerInsets.left + outerInsets.right,
                300 + outerInsets.top + outerInsets.bottom);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(400, (dim.height/2-this.getSize().height/2) + 100);                
        
        setVisible(true);
        
        


    }

    class PCMQuitActionListener implements ActionListener {

        //close and dispose of the window.
        public void actionPerformed(ActionEvent e) {
            isQuit = true;
            setVisible(false);
        }
    }    
    
    
}
