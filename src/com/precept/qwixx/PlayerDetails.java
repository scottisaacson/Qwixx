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

class PlayerDetails extends JDialog {

    public static final Dimension size50 = new Dimension(50, 50);
    // public static final Dimension size30 = new Dimension(30, 30);
    
    Game game;
    Frame frame;
    Player player;

    ArrayList<DisplayValue> reds;
    ArrayList<DisplayValue> yellows;
    ArrayList<DisplayValue> greens;
    ArrayList<DisplayValue> blues;
    DisplayValue dvScore;
    DisplayValue dvPenalties;
    
    JButton ok = null;
    

    public PlayerDetails (Player player) {
        
        super(player.game.frame, "Player Details", Dialog.ModalityType.APPLICATION_MODAL);
        
        this.player = player;
        this.game = player.game;
        this.frame = player.game.frame;
        
        DisplayValue dv = null;
        Color c = null;
        
        reds = new ArrayList<DisplayValue>();
        for (SheetEntry se : player.sheet.reds)
        {
            c = Qwixx.myred;
            dv = new DisplayValue(se.val, c, se.marked);
            dv.buildAndShow();
            reds.add(dv);
        }

        yellows = new ArrayList<DisplayValue>();
        for (SheetEntry se : player.sheet.yellows)
        {
            c = Qwixx.myyellow;
            dv = new DisplayValue(se.val, c, se.marked);
            dv.buildAndShow();
            yellows.add(dv);
        }

        greens = new ArrayList<DisplayValue>();
        for (SheetEntry se : player.sheet.greens)
        {
            c = Qwixx.mygreen;
            dv = new DisplayValue(se.val, c, se.marked);
            dv.buildAndShow();
            greens.add(dv);
        }

        blues = new ArrayList<DisplayValue>();
        for (SheetEntry se : player.sheet.blues)
        {
            c = Qwixx.myblue;
            dv = new DisplayValue(se.val, c, se.marked);
            dv.buildAndShow();
            blues.add(dv);
        }
        
        dvScore = new DisplayValue(player.sheet.score, Color.white, false);
        
        dvPenalties = new DisplayValue(player.sheet.penalties, Color.white, false);
        

    }
        
    public void buildAndShow () {
        
        Container pane = getContentPane();
        pane.setBackground(Qwixx.myback1);
 
        int top = 5;
        int left = 5;
        int diceGapLittle = 5;
        
        pane.setLayout(null);
        Insets paneInsets = pane.getInsets();

        JLabel name = new JLabel(player.name);
        name.setBackground(Color.white);
        name.setOpaque(true);
        name.setHorizontalAlignment(JLabel.CENTER);
        name.setVerticalAlignment(JLabel.CENTER);        
        name.setFont(Qwixx.myfont18);
        name.setBounds(left + 300, top, 150, 40);
        name.setSize(150, 40);
        name.setVisible(true);
        pane.add(name);

        int idx = 0;
        
        top = top + 55;
        
        
        left = 5;
        idx = 0;

        DisplayValue rscore = new DisplayValue(player.sheet.redsScore, Color.white, false);
        rscore.setBounds(left, top, 50, 50);
        rscore.buildAndShow();
        pane.add(rscore);

        left = left + 75;
        for (DisplayValue gs : reds)
        {
            gs.setBounds(left + (55 * idx), top, 50, 50);
            gs.setVisible(true);
            pane.add(gs);
            idx++;
        }

        left = left + (55 * 11) + 25;
        DisplayValue rcount = new DisplayValue(player.sheet.redsMarked, Color.white, false);
        rcount.setBounds(left, top, 50, 50);
        rcount.buildAndShow();
        pane.add(rcount);
        
        
        top = top + 55;
        left = 5;
        idx = 0;

        DisplayValue yscore = new DisplayValue(player.sheet.yellowsScore, Color.white, false);
        yscore.setBounds(left, top, 50, 50);
        yscore.buildAndShow();
        pane.add(yscore);
        
        left = left + 75;
        for (DisplayValue gs : yellows)
        {
            gs.setBounds(left + (55 * idx), top, 50, 50);
            gs.setVisible(true);
            pane.add(gs);
            idx++;
        }

        left = left + (55 * 11) + 25;
        DisplayValue ycount = new DisplayValue(player.sheet.yellowsMarked, Color.white, false);
        ycount.setBounds(left, top, 50, 50);
        ycount.buildAndShow();
        pane.add(ycount);
        
        
        top = top + 55;
        left = 5;
        idx = 0;
        
        DisplayValue gscore = new DisplayValue(player.sheet.greensScore, Color.white, false);
        gscore.setBounds(left, top, 50, 50);
        gscore.buildAndShow();
        pane.add(gscore);
        
        left = left + 75;
        for (DisplayValue gs : greens)
        {
            gs.setBounds(left + (55 * idx), top, 50, 50);
            gs.setVisible(true);
            pane.add(gs);
            idx++;
        }
        
        left = left + (55 * 11) + 25;
        DisplayValue gcount = new DisplayValue(player.sheet.greensMarked, Color.white, false);
        gcount.setBounds(left, top, 50, 50);
        gcount.buildAndShow();
        pane.add(gcount);
        
        
        
        top = top + 55;
        left = 5;
        idx = 0;

        DisplayValue bscore = new DisplayValue(player.sheet.bluesScore, Color.white, false);
        bscore.setBounds(left, top, 50, 50);
        bscore.buildAndShow();
        pane.add(bscore);
        
        left = left + 75;
        for (DisplayValue dv : blues)
        {
            dv.setBounds(left + (55 * idx), top, 50, 50);
            dv.buildAndShow();
            pane.add(dv);
            idx++;
        }
        
        left = left + (55 * 11) + 25;
        DisplayValue bcount = new DisplayValue(player.sheet.bluesMarked, Color.white, false);
        bcount.setBounds(left, top, 50, 50);
        bcount.buildAndShow();
        pane.add(bcount);
        
        top = top + 80;
        left = 25;
        
        JLabel scoreLabel = new JLabel("Score");
        scoreLabel.setFont(Qwixx.myfont18);
        scoreLabel.setBackground(Color.white);
        scoreLabel.setOpaque(true);
        scoreLabel.setBounds(left, top, 65, 50);
        scoreLabel.setSize(65, 50);
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreLabel.setVerticalAlignment(JLabel.CENTER);        
        scoreLabel.setVisible(true);
        pane.add(scoreLabel);

        left = left + 65;
        dvScore.setBounds(left, top, 50, 50);
        dvScore.buildAndShow();
        pane.add(dvScore);

        // top = top + 60;
        left = left + 50 + 50;
        
        JLabel penaltiesLabel = new JLabel("Penalties");
        penaltiesLabel.setFont(Qwixx.myfont18);
        penaltiesLabel.setBackground(Color.white);
        penaltiesLabel.setOpaque(true);
        penaltiesLabel.setBounds(left, top, 100, 50);
        penaltiesLabel.setSize(100, 50);
        penaltiesLabel.setHorizontalAlignment(JLabel.CENTER);
        penaltiesLabel.setVerticalAlignment(JLabel.CENTER);        
        penaltiesLabel.setVisible(true);
        pane.add(penaltiesLabel);
 
        left = left + 100;

        dvPenalties.setBounds(left, top, 50, 50);
        dvPenalties.buildAndShow();
        pane.add(dvPenalties);
        
        
        ok = new JButton();
        ok.setText("OK");
        ok.setFont(Qwixx.myfont14);
        // set action listener on the button
        ok.addActionListener(new RDOKActionListener());        
        ok.setBounds(660, 300, 100, 50);
        ok.setVisible(true);
        pane.add(ok);
        

        Insets outerInsets = getInsets();
        setSize(
            800 + outerInsets.left + outerInsets.right,
            400 + outerInsets.top + outerInsets.bottom);
        
        setVisible(true);
    }
    
    
    class RDOKActionListener implements ActionListener {

        //close and dispose of the window.
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
        }
    }    
    
}
