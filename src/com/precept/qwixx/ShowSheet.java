/****************************************************************************
 * 2019 The Precept Group, LLC. 
 * See LICENSE for license information.
 ***************************************************************************/

package com.precept.qwixx;

import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;

class ShowSheet extends JPanel {

    Game game;
    Frame frame;
    Player player;

    ArrayList<DisplayValue> reds;
    ArrayList<DisplayValue> yellows;
    ArrayList<DisplayValue> greens;
    ArrayList<DisplayValue> blues;
    JLabel scoreLabel;
    JLabel penaltiesLabel;
    DisplayValue score;
    DisplayValue penalties;
    DisplayValue redScore;
    DisplayValue yellowScore;
    DisplayValue greenScore;
    DisplayValue blueScore;
    DisplayValue redCount;
    DisplayValue yellowCount;
    DisplayValue greenCount;
    DisplayValue blueCount;
    
    JLabel title;
    
    
    

    public ShowSheet (Player player) {
        
        super();
        
        if (player != null)
        {
            this.player = player;
            this.game = player.game;
            this.frame = player.game.frame;
        }
        else
        {
            this.player = null;
            this.game = null;
            this.frame = null;
        }
        
    }

    
    public void build ()
    {
        
        setLayout(null);
        setBackground(Qwixx.myback1);
        setBorder(BorderFactory.createLineBorder(Color.black));


        int fullWidth = 800;
        int fullHeight = 420;
        
        int left = 5;
        int top = 5;
        int idx = 0;
        int diceGapLittle = 5;
        
        setSize(fullWidth, fullHeight);
        
        if (player == null) return;
        
        DisplayValue ms = null;
        Color c = null;
        
        reds = new ArrayList<DisplayValue>();
        for (SheetEntry se : player.sheet.reds)
        {
            c = Qwixx.myred;
            ms = new DisplayValue(se.val, c, se.marked);
            reds.add(ms);
        }

        yellows = new ArrayList<DisplayValue>();
        for (SheetEntry se : player.sheet.yellows)
        {
            c = Qwixx.myyellow;
            ms = new DisplayValue(se.val, c, se.marked);
            yellows.add(ms);
        }

        greens = new ArrayList<DisplayValue>();
        for (SheetEntry se : player.sheet.greens)
        {
            c = Qwixx.mygreen;
            ms = new DisplayValue(se.val, c, se.marked);
            greens.add(ms);
        }

        blues = new ArrayList<DisplayValue>();
        for (SheetEntry se : player.sheet.blues)
        {
            c = Qwixx.myblue;
            ms = new DisplayValue(se.val, c, se.marked);
            blues.add(ms);
        }
        
        score = new DisplayValue(player.sheet.score, Color.white, false);
        score.buildAndShow();
        
        penalties = new DisplayValue(player.sheet.penalties, Color.white, false);
        penalties.buildAndShow();


        // BUILD TITLE
        
        int titleWidth = 600;
        int titleHeight = 40;
        
        title = new JLabel(player.name + "'s Sheet");
        title.setFont(Qwixx.myfont18);
        title.setBackground(Color.white);
        title.setOpaque(true);
        title.setSize(titleWidth, titleHeight);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.CENTER);        
        add(title);

        top = 5;
        left = (fullWidth - 10 - titleWidth) / 2;
        left = left - 20;
        title.setBounds(left, top, titleWidth, titleHeight);
        title.setVisible(true);

        
        // REDS

        top = top + titleHeight + 10;
        left = 5;
        
        redScore = new DisplayValue(player.sheet.redsScore, Color.white, false);
        redScore.setBounds(left, top, 50, 50);
        redScore.buildAndShow();
        add(redScore);

        left = left + 75;
        for (DisplayValue rs : reds)
        {
            rs.setBounds(left + (55 * idx), top, 50, 50);
            rs.setVisible(true);
            add(rs);
            idx++;
        }

        left = left + (55 * 11) + 25;
        redCount = new DisplayValue(player.sheet.redsMarked, Color.white, false);
        redCount.setBounds(left, top, 50, 50);
        redCount.buildAndShow();
        add(redCount);
        

        // YELLOWS

        
        top = top + 55;
        left = 5;
        idx = 0;

        yellowScore = new DisplayValue(player.sheet.yellowsScore, Color.white, false);
        yellowScore.setBounds(left, top, 50, 50);
        yellowScore.buildAndShow();
        add(yellowScore);
        
        
        left = left + 75;
        for (DisplayValue ys : yellows)
        {
            ys.setBounds(left + (55 * idx), top, 50, 50);
            ys.setVisible(true);
            add(ys);
            idx++;
        }

        left = left + (55 * 11) + 25;
        yellowCount = new DisplayValue(player.sheet.yellowsMarked, Color.white, false);
        yellowCount.setBounds(left, top, 50, 50);
        yellowCount.buildAndShow();
        add(yellowCount);
        
        // GREENS

        top = top + 55;
        left = 5;
        idx = 0;
        
        DisplayValue greenScore = new DisplayValue(player.sheet.greensScore, Color.white, false);
        greenScore.setBounds(left, top, 50, 50);
        greenScore.buildAndShow();
        add(greenScore);
        
        left = left + 75;
        for (DisplayValue gs : greens)
        {
            gs.setBounds(left + (55 * idx), top, 50, 50);
            gs.setVisible(true);
            add(gs);
            idx++;
        }

        left = left + (55 * 11) + 25;
        greenCount = new DisplayValue(player.sheet.greensMarked, Color.white, false);
        greenCount.setBounds(left, top, 50, 50);
        greenCount.buildAndShow();
        add(greenCount);
        
        // BLUES
        
        top = top + 55;
        left = 5;
        idx = 0;

        blueScore = new DisplayValue(player.sheet.bluesScore, Color.white, false);
        blueScore.setBounds(left, top, 50, 50);
        blueScore.buildAndShow();
        add(blueScore);
        
        left = left + 75;
        for (DisplayValue bs : blues)
        {
            bs.setBounds(left + (55 * idx), top, 50, 50);
            bs.setVisible(true);
            add(bs);
            idx++;
        }

        left = left + (55 * 11) + 25;
        blueCount = new DisplayValue(player.sheet.bluesMarked, Color.white, false);
        blueCount.setBounds(left, top, 50, 50);
        blueCount.buildAndShow();
        add(blueCount);
        
        // SCORE

        
        top = top + 60;
        left = 120;

        scoreLabel = new JLabel("Score");
        scoreLabel.setFont(Qwixx.myfont18);
        scoreLabel.setBackground(Color.white);
        scoreLabel.setOpaque(true);
        scoreLabel.setBounds(left, top, 70, 50);
        scoreLabel.setSize(70, 50);
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreLabel.setVerticalAlignment(JLabel.CENTER);        
        scoreLabel.setVisible(true);
        add(scoreLabel);

        left = left + 65;
        score.setBounds(left, top, 50, 50);
        score.buildAndShow();
        add(score);

        // PENALTIES

        
        // top = top + 60;
        left = left + 50 + 50;
        
        penaltiesLabel = new JLabel("Penalties");
        penaltiesLabel.setFont(Qwixx.myfont18);
        penaltiesLabel.setBackground(Color.white);
        penaltiesLabel.setOpaque(true);
        penaltiesLabel.setBounds(left, top, 100, 50);
        penaltiesLabel.setSize(100, 50);
        penaltiesLabel.setHorizontalAlignment(JLabel.CENTER);
        penaltiesLabel.setVerticalAlignment(JLabel.CENTER);        
        penaltiesLabel.setVisible(true);
        add(penaltiesLabel);
 
        left = left + 100;

        penalties.setBounds(left, top, 50, 50);
        penalties.buildAndShow();
        add(penalties);
        
        setVisible(false);

    }

}


