/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.precept.qwixx;

import static com.precept.qwixx.Qwixx.myfont14;
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

public class NoColorMovesDialog extends JDialog {

    public Game game;
    public JFrame frame;

    public String name;
    public Player player;
 
    public NoColorMovesDialog(Game game) 
    {
        super(game.frame, "No Color Moves", Dialog.ModalityType.APPLICATION_MODAL);
        //super(game.frame, "No Color Moves");
        
        this.frame = game.frame;
        this.game = game;
        this.player = game.current;
    }
    
    public void buildAndShow() {
        
        Container pane = getContentPane();
        pane.setLayout(null);
        pane.setBackground(Qwixx.myback1);
        
        
        JButton quit = new JButton("OK");
        quit.setFont(myfont14);
        quit.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
        quit.setBounds(10, 10, 100, 40);
        quit.setVisible(true);
        pane.add(quit);

        
        Insets outerInsets = getInsets();
        setSize(
                300 + outerInsets.left + outerInsets.right,
                300 + outerInsets.top + outerInsets.bottom);
        
        setVisible(true);
        
        

    }
}
