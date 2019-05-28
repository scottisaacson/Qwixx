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

public class RollDice extends JDialog {

    public Game game;
    public JFrame frame;
    public ArrayList<Player> players;
    public Player current;
    public int size;
 
    public RollDice(Game game) 
    {
        // super(game.frame, "Result");
        super(game.frame, "Roll Dice", Dialog.ModalityType.APPLICATION_MODAL);
        this.game = game;
        this.frame = game.frame;
        this.players = game.players;
        this.current = game.current;
        this.size = players.size();
    }
    
    public void buildAndShow() {
        
        Container pane = getContentPane();
        pane.setLayout(null);
        pane.setBackground(Qwixx.mydarkgrey);


        int top = 20;
        int left = 50;
        
        var title = new JLabel(current.name + "'s ROLL");
        title.setBackground(Color.white);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.CENTER);        
        title.setOpaque(true);
        title.setFont(Qwixx.myfont18);
        title.setSize(300, 50);
        title.setBounds(left, top, 300, 50);
        title.setVisible(true);
        pane.add(title);
        
        top = top + 100;
        left = 163;
        
        JButton ok = new JButton("OK");
        ok.setFont(Qwixx.myfont14);
        ok.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                    // game = new Game(game);
                    dispose();
                }
            });
        Dimension od = ok.getPreferredSize();
        ok.setBounds(left, top, 75, 50);
        ok.setVisible(true);
        pane.add(ok);
        
        Insets outerInsets = getInsets();
        setSize(
                400 + outerInsets.left + outerInsets.right,
                300 + outerInsets.top + outerInsets.bottom);

        
        /*
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        */

        this.setLocation(0, 0);
        
        setVisible(true);

    }
}
