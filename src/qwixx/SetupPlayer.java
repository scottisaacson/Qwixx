/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qwixx;

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


class SetupPlayer extends JPanel {
    
    Player player;
    int number;
    JLabel label;
    JTextField name;
    JRadioButton type;
    
    public SetupPlayer (int number, Player player) {
        
        super();
        this.number = number;
        this.player = player;
        
        int height = 40;
        
        this.setLayout(null);

        label = new JLabel("Player " + this.number);
        label.setFont(Qwixx.myfont14);
        label.setBackground(Qwixx.myback1);
        label.setOpaque(true);
        Dimension ld = label.getPreferredSize();
        label.setSize(90, height);
        label.setBounds(0, 0, 100, height);
        label.setVisible(true);
        add(label);
        
        name = new JTextField(player.name);
        name.setFont(Qwixx.myfont14);
        Dimension nd = name.getPreferredSize();
        // nd.width = 100;
        // name.setPreferredSize(nd);
        name.setSize(100, height);
        name.setBounds(100, 0, 100, height);
        name.setVisible(true);
        add(name);
        
        type = new JRadioButton("Computer");
        if (player.strategy == Player.STRATEGY.Computer)
        {
            type.setSelected(true);
        }
        else
        {
            type.setSelected(false);
        }
        type.setFont(Qwixx.myfont14);
        type.setBackground(Qwixx.myback1);
        type.setOpaque(true);
        Dimension td = type.getPreferredSize();
        type.setSize(200, height);
        type.setBounds(200, 0, 200, height);
        type.setVisible(true);
        add(type);

        setSize(500, height);
        
        setVisible(true);
        
    }
    
    
}
