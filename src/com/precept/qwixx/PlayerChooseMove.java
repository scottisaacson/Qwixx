/**********************************************************
 *  Copyright 2019, The Precept Group, LLC
 *  See the LICENSE file for license information.
 *
 *********************************************************/


package com.precept.qwixx;

import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;

class PlayerChooseMove extends JPanel {

    public static final Dimension size50 = new Dimension(50, 50);
    // public static final Dimension size30 = new Dimension(30, 30);
    
    Game game;
    Frame frame;
    Player player;

    ArrayList<MoveSelect> reds;
    ArrayList<MoveSelect> yellows;
    ArrayList<MoveSelect> greens;
    ArrayList<MoveSelect> blues;
    DisplayValue dvScore;
    DisplayValue dvPenalties;
    JButton skip;
    JLabel title;
    
    boolean isselected = false;
    boolean isskip = false;
    
    boolean consider = false;
    
    public SheetEntry thisOne = null;
    public ArrayList<Move> whiteMoves = null;
    public ArrayList<Move> colorMoves = null;
    public Qwixx.MOVETYPE type = Qwixx.MOVETYPE.WHITES;
    public WhichTurn.TYPE turnType = null;
    

    
    public PlayerChooseMove (Player player, Qwixx.MOVETYPE type, ArrayList<Move> whiteMoves, ArrayList<Move> colorMoves) {
        
        super();
        
        if (player == null)
        {
            this.player = null;
            this.game = null;
            this.frame = null;
        }
        else
        {
            this.player = player;
            this.game = player.game;
            this.frame = player.game.frame;
        }

        this.type = type;
        
        if (type == Qwixx.MOVETYPE.COLORS)
        {
            this.colorMoves = colorMoves;
            this.whiteMoves = null;
            this.turnType = WhichTurn.TYPE.COLOR;
            this.consider = false;
    }
        else if (type == Qwixx.MOVETYPE.WHITES)
        {
            this.whiteMoves = whiteMoves;
            this.colorMoves = null;
            this.turnType = WhichTurn.TYPE.WHITE;
            this.consider = false;
        }
        else if (type == Qwixx.MOVETYPE.WHITES_CONSIDERING_COLORS)
        {
            this.whiteMoves = whiteMoves;
            this.colorMoves = colorMoves;
            this.turnType = WhichTurn.TYPE.WHITE;
            this.consider = true;
        }
 
    }
    
    public void build () 
    {
        
        setBackground(Qwixx.myback1);
        setLayout(null);
        setBorder(BorderFactory.createLineBorder(Color.black));

        int fullWidth = 800;
        int fullHeight = 420;
        
        setSize(fullWidth, fullHeight);
        
        if (player == null) return;

        int left = 5;
        int top = 5;
        int idx = 0;
        int diceGapLittle = 5;
        
        
        MoveSelect ms = null;
        Color c = null;
        
        reds = new ArrayList<MoveSelect>();
        for (SheetEntry se : player.sheet.reds)
        {
            c = Qwixx.myred;
            ms = new MoveSelect(player, se.val, c, se.marked, turnType);
            reds.add(ms);
        }

        yellows = new ArrayList<MoveSelect>();
        for (SheetEntry se : player.sheet.yellows)
        {
            c = Qwixx.myyellow;
            ms = new MoveSelect(player, se.val, c, se.marked, turnType);
            yellows.add(ms);
        }

        greens = new ArrayList<MoveSelect>();
        for (SheetEntry se : player.sheet.greens)
        {
            c = Qwixx.mygreen;
            ms = new MoveSelect(player, se.val, c, se.marked, turnType);
            greens.add(ms);
        }

        blues = new ArrayList<MoveSelect>();
        for (SheetEntry se : player.sheet.blues)
        {
            c = Qwixx.myblue;
            ms = new MoveSelect(player, se.val, c, se.marked, turnType);
            blues.add(ms);
        }
        
        dvScore = new DisplayValue(player.sheet.score, Color.white, false);
        dvScore.buildAndShow();
        
        
        dvPenalties = new DisplayValue(player.sheet.penalties, Color.white, false);
        dvScore.buildAndShow();

        
        // BUILD TITLE
        
        int titleWidth = 600;
        int titleHeight = 50;
        
        title = new JLabel(player.name + "'s " + turnType + "S move");
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


        top = top + titleHeight + 10;
        //top = top + 55;
        
        // REDS
        
        left = 5;
        idx = 0;

        DisplayValue rscore = new DisplayValue(player.sheet.redsScore, Color.white, false);
        rscore.setBounds(left, top, 50, 50);
        rscore.buildAndShow();
        add(rscore);

        left = left + 75;
        for (MoveSelect gs : reds)
        {
            gs.setBounds(left + (55 * idx), top, 50, 50);
            gs.setVisible(true);
            add(gs);
            idx++;
        }

        left = left + (55 * 11) + 25;
        DisplayValue rcount = new DisplayValue(player.sheet.redsMarked, Color.white, false);
        rcount.setBounds(left, top, 50, 50);
        rcount.buildAndShow();
        add(rcount);
        
        // YELLOWS
        
        top = top + 55;
        left = 5;
        idx = 0;

        DisplayValue yscore = new DisplayValue(player.sheet.yellowsScore, Color.white, false);
        yscore.setBounds(left, top, 50, 50);
        yscore.buildAndShow();
        add(yscore);
        
        
        left = left + 75;
        for (MoveSelect gs : yellows)
        {
            gs.setBounds(left + (55 * idx), top, 50, 50);
            gs.setVisible(true);
            add(gs);
            idx++;
        }

        left = left + (55 * 11) + 25;
        DisplayValue ycount = new DisplayValue(player.sheet.yellowsMarked, Color.white, false);
        ycount.setBounds(left, top, 50, 50);
        ycount.buildAndShow();
        add(ycount);
        

        // GREENS
     
        top = top + 55;
        left = 5;
        idx = 0;
        
        DisplayValue gscore = new DisplayValue(player.sheet.greensScore, Color.white, false);
        gscore.setBounds(left, top, 50, 50);
        gscore.buildAndShow();
        add(gscore);
        
        left = left + 75;
        for (MoveSelect gs : greens)
        {
            gs.setBounds(left + (55 * idx), top, 50, 50);
            gs.setVisible(true);
            add(gs);
            idx++;
        }

        left = left + (55 * 11) + 25;
        DisplayValue gcount = new DisplayValue(player.sheet.greensMarked, Color.white, false);
        gcount.setBounds(left, top, 50, 50);
        gcount.buildAndShow();
        add(gcount);

        // BLUES
        
        top = top + 55;
        left = 5;
        idx = 0;

        DisplayValue bscore = new DisplayValue(player.sheet.bluesScore, Color.white, false);
        bscore.setBounds(left, top, 50, 50);
        bscore.buildAndShow();
        add(bscore);
        
        left = left + 75;
        for (MoveSelect gs : blues)
        {
            gs.setBounds(left + (55 * idx), top, 50, 50);
            gs.setVisible(true);
            add(gs);
            idx++;
        }

        left = left + (55 * 11) + 25;
        DisplayValue bcount = new DisplayValue(player.sheet.bluesMarked, Color.white, false);
        bcount.setBounds(left, top, 50, 50);
        bcount.buildAndShow();
        add(bcount);
        
        // SCORE
        
        top = top + 60;
        left = 120;

        JLabel scoreLabel = new JLabel("Score");
        scoreLabel.setFont(Qwixx.myfont18);
        scoreLabel.setBackground(Qwixx.myback1);
        scoreLabel.setOpaque(true);
        scoreLabel.setBounds(left, top, 60, 50);
        scoreLabel.setSize(60, 50);
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreLabel.setVerticalAlignment(JLabel.CENTER);        
        scoreLabel.setVisible(true);
        add(scoreLabel);

        left = left + 65;
        dvScore.setBounds(left, top, 50, 50);
        dvScore.buildAndShow();
        add(dvScore);

        // PENALTIES
        
        // top = top + 60;
        left = left + 50 + 50;
        
        JLabel penaltiesLabel = new JLabel("Penalties");
        penaltiesLabel.setFont(Qwixx.myfont18);
        penaltiesLabel.setBackground(Qwixx.myback1);
        penaltiesLabel.setOpaque(true);
        penaltiesLabel.setBounds(left, top, 100, 50);
        penaltiesLabel.setSize(100, 50);
        penaltiesLabel.setHorizontalAlignment(JLabel.CENTER);
        penaltiesLabel.setVerticalAlignment(JLabel.CENTER);        
        penaltiesLabel.setVisible(true);
        add(penaltiesLabel);
 
        left = left + 100;

        dvPenalties.setBounds(left, top, 50, 50);
        dvPenalties.buildAndShow();
        add(dvPenalties);
        
        
        
        
        
        
        
        
        
        // Highlight the moves
        // if consider, then highlight both whites with WHITE and colors with COLOR
        //      AND THEN check for overlap....
        // if !consider, just highlight colors with COLOR and whites with WHITE
        
        if (consider)
        {
            highlight(whiteMoves, MoveSelect.HIGHLIGHT.WHITE);
            highlight(colorMoves, MoveSelect.HIGHLIGHT.COLOR);
        }
        else
        {
            if (type == Qwixx.MOVETYPE.WHITES)
            {
                highlight(whiteMoves, MoveSelect.HIGHLIGHT.WHITE);
            }

            if (type == Qwixx.MOVETYPE.COLORS)
            {
                highlight(colorMoves, MoveSelect.HIGHLIGHT.COLOR);
            }
            
        }
        
        
        // SKIP or TAKE A PENALTY
        
        top = top + 60;

        // Create a button
        skip = new JButton();
        if (type == Qwixx.MOVETYPE.COLORS)
        {
            skip.setText("TAKE A PENTALTY");
        }
        else
        {
            skip.setText("SKIP");
        }
        skip.setFont(Qwixx.myfont18);
        // set action listener on the button
        skip.addActionListener(new PCMSkipActionListener());        

        
        skip.setBounds(500, top, 250, 50);
        skip.setVisible(true);
        add(skip);
        
        setVisible(false);

    }

    private void highlight(ArrayList<Move> moves, MoveSelect.HIGHLIGHT h)
    {
        for (Move m : moves)
        {
            if (m.se.color == Game.COLORS.RED)
            {
                for (MoveSelect ms : reds)
                {
                    if (ms.val == m.se.val)
                    {
                        ms.setHighlight(h);
                    }
                }
            }

            if (m.se.color == Game.COLORS.YELLOW)
            {
                for (MoveSelect ms : yellows)
                {
                    if (ms.val == m.se.val)
                    {
                        ms.setHighlight(h);
                    }
                }
            }

            if (m.se.color == Game.COLORS.GREEN)
            {
                for (MoveSelect ms : greens)
                {
                    if (ms.val == m.se.val)
                    {
                        ms.setHighlight(h);
                    }
                }
            }

            if (m.se.color == Game.COLORS.BLUE)
            {
                for (MoveSelect ms : blues)
                {
                    if (ms.val == m.se.val)
                    {
                        ms.setHighlight(h);
                    }
                }
            }
        }
        
        return;
        
    }

    class PCMSkipActionListener implements ActionListener {

        //close and dispose of the window.
        public void actionPerformed(ActionEvent e) {
            isskip = true;
        }
    }    


}


