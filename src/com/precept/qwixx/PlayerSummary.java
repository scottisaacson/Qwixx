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

/**
 *
 * @author sisaacson
 */
public class PlayerSummary extends JPanel {


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
        player = p;
    }
    
    public void buildAndShow()
    {

        setLayout(null);
        setBackground(Qwixx.myback1);
        
        int fullWidth = 440;
        int fullHeight = 50;
        
        setSize(fullWidth, fullHeight);
        

        // name
        
        int top = 0;
        int left = 0;
        int nameWidth = 100;
        int nameHeight = 50;
        
        name = new JButton(player.name);
        name.setFont(Qwixx.myfont14);
        name.setBackground(Color.white);
        name.setBounds(left, top, nameWidth, nameHeight);
        
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
        
        left = left + nameWidth + 5;
       
        DisplayValue dvScore = new DisplayValue(player.sheet.score, Color.white, false);
        dvScore.setBounds(left, top, Qwixx.size50.width, Qwixx.size50.height);
        dvScore.buildAndShow();
        add(dvScore);

        left = left + Qwixx.size50.width + 5;
        
        DisplayValue dvPenalties = new DisplayValue(player.sheet.penalties, Color.white, false);
        dvPenalties.setBounds(left, top, Qwixx.size50.width, Qwixx.size50.height);
        dvPenalties.buildAndShow();
        add(dvPenalties);

        left = left + Qwixx.size50.width + 5;
        
        DisplayValue dvReds = new DisplayValue(player.sheet.redsMarked, Qwixx.myred, false);
        dvReds.setBounds(left, top, Qwixx.size50.width, Qwixx.size50.height);
        dvReds.buildAndShow();
        add(dvReds);

        left = left + Qwixx.size50.width + 5;

        DisplayValue dvYellows = new DisplayValue(player.sheet.yellowsMarked, Qwixx.myyellow, false);
        dvYellows.setBounds(left, top, Qwixx.size50.width, Qwixx.size50.height);
        dvYellows.buildAndShow();
        add(dvYellows);

        left = left + Qwixx.size50.width + 5;
        
        DisplayValue dvGreens = new DisplayValue(player.sheet.greensMarked, Qwixx.mygreen, false);
        dvGreens.setBounds(left, top, Qwixx.size50.width, Qwixx.size50.height);
        dvGreens.buildAndShow();
        add(dvGreens);

        left = left + Qwixx.size50.width + 5;
        
        DisplayValue dvBlues = new DisplayValue(player.sheet.bluesMarked, Qwixx.myblue, false);
        dvBlues.setBounds(left, top, Qwixx.size50.width, Qwixx.size50.height);
        dvBlues.buildAndShow();
        add(dvBlues);
        
        setVisible(true);
        
    }
    
}
