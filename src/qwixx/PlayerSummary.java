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

/**
 *
 * @author sisaacson
 */
public class PlayerSummary extends JPanel {

    // Name
    // Score
    // Penalites
    // Reds
    // Yellows
    // Greens
    // Blues
    
    Player player;
    JButton name;
    JLabel score;
    JLabel penalties;
    JLabel reds;
    JLabel yellows;
    JLabel greens;
    JLabel blues;
    
    
    public PlayerSummary (Player p)
    {
        super();
        
        this.player = p;
        this.setLayout(null);

        name = new JButton(player.name);
        name.setFont(Qwixx.myfont14);
        name.setBackground(Color.white);
        name.setBounds(10, 0, 100, 50);
        
        name.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                var playerDetails = new PlayerDetails(player);
                playerDetails.buildAndShow();

                playerDetails.dispose();

            }
        });
        
        name.setVisible(true);
        add(name);
        
        /*
        name = new JLabel(player.name);
        name.setFont(IkeQwixx3.myfont14);
        name.setBackground(IkeQwixx3.myback1);
        name.setBounds(10, 0, 100, 50);
        name.setVisible(true);
        add(name);
        */
        
        int top = 0;
        int left = 10;
       
        DisplayValue dvScore = new DisplayValue(player.sheet.score, Color.white, false);
        dvScore.setBounds( 120, top, 50, 50);
        dvScore.buildAndShow();
        add(dvScore);
        
        DisplayValue dvPenalties = new DisplayValue(player.sheet.penalties, Color.white, false);
        dvPenalties.setBounds( 175, top, 50, 50);
        dvPenalties.buildAndShow();
        add(dvPenalties);
        
        DisplayValue dvReds = new DisplayValue(player.sheet.redsMarked, Qwixx.myred, false);
        dvReds.setBounds( 230, top, 50, 50);
        dvReds.buildAndShow();
        add(dvReds);
        
        DisplayValue dvYellows = new DisplayValue(player.sheet.yellowsMarked, Qwixx.myyellow, false);
        dvYellows.setBounds( 285, top, 50, 50);
        dvYellows.buildAndShow();
        add(dvYellows);
        
        DisplayValue dvGreens = new DisplayValue(player.sheet.greensMarked, Qwixx.mygreen, false);
        dvGreens.setBounds( 340, top, 50, 50);
        dvGreens.buildAndShow();
        add(dvGreens);
        
        DisplayValue dvBlues = new DisplayValue(player.sheet.bluesMarked, Qwixx.myblue, false);
        dvBlues.setBounds( 395, top, 50, 50);
        dvBlues.buildAndShow();
        add(dvBlues);
        
        
        
        setSize(500, 50);
        
        setVisible(true);
        
    }
}
