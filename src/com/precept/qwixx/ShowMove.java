package com.precept.qwixx;

import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;

class ShowMove extends JPanel {

    public static final Dimension size50 = new Dimension(50, 50);
    
    Game game;
    Frame frame;
    Player player;

    ArrayList<MoveSelect> reds;
    ArrayList<MoveSelect> yellows;
    ArrayList<MoveSelect> greens;
    ArrayList<MoveSelect> blues;
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
    
 
    JLabel event;
    
    public SheetEntry thisOne = null;
    
    public Qwixx.MOVETYPE type = Qwixx.MOVETYPE.WHITES;
    

    public ShowMove (Player player, Qwixx.MOVETYPE type, SheetEntry thisOne) {
        
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

        if (type == Qwixx.MOVETYPE.COLORS)
        {
            this.type = type;
        }
        else
        {
            this.type = Qwixx.MOVETYPE.WHITES;
        }
   
        this.thisOne = thisOne;
        
    }

    
    public void build () 
    {
        
        setLayout(null);
        setBackground(Qwixx.myback1);
        setBorder(BorderFactory.createLineBorder(Color.black));

        int fullWidth = 800;
        int fullHeight = 300;
        
        setSize(fullWidth, fullHeight);
        
        if (player == null) return;
        
        MoveSelect ms = null;
        Color c = null;
        
        reds = new ArrayList<MoveSelect>();
        for (SheetEntry se : player.sheet.reds)
        {
            c = Qwixx.myred;
            ms = new MoveSelect(player, se.val, c, se.marked, type);
            reds.add(ms);
        }

        yellows = new ArrayList<MoveSelect>();
        for (SheetEntry se : player.sheet.yellows)
        {
            c = Qwixx.myyellow;
            ms = new MoveSelect(player, se.val, c, se.marked, type);
            yellows.add(ms);
        }

        greens = new ArrayList<MoveSelect>();
        for (SheetEntry se : player.sheet.greens)
        {
            c = Qwixx.mygreen;
            ms = new MoveSelect(player, se.val, c, se.marked, type);
            greens.add(ms);
        }

        blues = new ArrayList<MoveSelect>();
        for (SheetEntry se : player.sheet.blues)
        {
            c = Qwixx.myblue;
            ms = new MoveSelect(player, se.val, c, se.marked, type);
            blues.add(ms);
        }
        
        score = new DisplayValue(player.sheet.score, Color.white, false);
        score.buildAndShow();
        
        penalties = new DisplayValue(player.sheet.penalties, Color.white, false);
        penalties.buildAndShow();


        String eventString;
        StringBuffer sb = new StringBuffer();
        
        if (thisOne == null)
        {
            if (type == Qwixx.MOVETYPE.WHITES || type == Qwixx.MOVETYPE.WHITES_CONSIDERING_COLORS)
            {
                sb.append("SKIPPED");
            }
            else if (type == Qwixx.MOVETYPE.COLORS)
            {
                sb.append("TOOK A PENALTY");
            }
            else
            {
                //
            }
        }
        else
        {
            sb.append("TOOK ");

            if (thisOne.color == Game.COLORS.RED) sb.append(" RED ");
            if (thisOne.color == Game.COLORS.YELLOW) sb.append(" YELLOW ");
            if (thisOne.color == Game.COLORS.GREEN) sb.append(" GREEN ");
            if (thisOne.color == Game.COLORS.BLUE) sb.append(" BLUE ");
            
            sb.append(thisOne.val);
        }
        
        event = new JLabel(sb.toString());
        event.setFont(Qwixx.myfont18);
        event.setBackground(Color.white);
        event.setOpaque(true);
        event.setSize(400, 40);
        event.setHorizontalAlignment(JLabel.CENTER);
        event.setVerticalAlignment(JLabel.CENTER);        
        add(event);

        int idx = 0;
        int top = 5;
        int left = 5;
        int diceGapLittle = 5;
        

        redScore = new DisplayValue(player.sheet.redsScore, Color.white, false);
        redScore.setBounds(left, top, 50, 50);
        redScore.buildAndShow();
        add(redScore);

        left = left + 75;
        for (MoveSelect rs : reds)
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
        
        
        top = top + 55;
        left = 5;
        idx = 0;

        yellowScore = new DisplayValue(player.sheet.yellowsScore, Color.white, false);
        yellowScore.setBounds(left, top, 50, 50);
        yellowScore.buildAndShow();
        add(yellowScore);
        
        
        left = left + 75;
        for (MoveSelect ys : yellows)
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
        

        top = top + 55;
        left = 5;
        idx = 0;
        
        DisplayValue greenScore = new DisplayValue(player.sheet.greensScore, Color.white, false);
        greenScore.setBounds(left, top, 50, 50);
        greenScore.buildAndShow();
        add(greenScore);
        
        left = left + 75;
        for (MoveSelect gs : greens)
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
        
        
        top = top + 55;
        left = 5;
        idx = 0;

        blueScore = new DisplayValue(player.sheet.bluesScore, Color.white, false);
        blueScore.setBounds(left, top, 50, 50);
        blueScore.buildAndShow();
        add(blueScore);
        
        left = left + 75;
        for (MoveSelect bs : blues)
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

        
        left = 500;
        event.setBounds(left, top, 250, 50);
        event.setVisible(true);

        
        top = top + 60;
        left = 10;

        
        // SheetEntry highlightThis = player.findEntry(thisOne);
        SheetEntry highlightThis = thisOne;
        SheetEntry saveThis = thisOne;
        
        MoveSelect.HIGHLIGHT h = null;
        if (type == Qwixx.MOVETYPE.COLORS)
        {
            h = MoveSelect.HIGHLIGHT.COLOR;
        }
        if (type == Qwixx.MOVETYPE.WHITES || type == Qwixx.MOVETYPE.WHITES_CONSIDERING_COLORS) 
        {
            h = MoveSelect.HIGHLIGHT.WHITE;
        }
        
        highlight(thisOne, h);
        
        setVisible(false);

    }

    private void highlight(SheetEntry se, MoveSelect.HIGHLIGHT h)
    {
        
        if (se == null)
        {
            return;
        }
        
        
        if (se.color == Game.COLORS.RED)
        {
            for (MoveSelect ms : reds)
            {
                if (ms.val == se.val)
                {
                    ms.setHighlight(h);
                }
            }
        }

        if (se.color == Game.COLORS.YELLOW)
        {
            for (MoveSelect ms : yellows)
            {
                if (ms.val == se.val)
                {
                    ms.setHighlight(h);
                }
            }
        }

        if (se.color == Game.COLORS.GREEN)
        {
            for (MoveSelect ms : greens)
            {
                if (ms.val == se.val)
                {
                    ms.setHighlight(h);
                }
            }
        }

        if (se.color == Game.COLORS.BLUE)
        {
            for (MoveSelect ms : blues)
            {
                if (ms.val == se.val)
                {
                    ms.setHighlight(h);
                }
            }
        }
        
        return;
        
    }

}


