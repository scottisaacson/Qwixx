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

class PlayerChooseMove extends JDialog {

    public static final Dimension size50 = new Dimension(50, 50);
    public static final Dimension size30 = new Dimension(30, 30);
    
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
    JButton quit;
    boolean isquit = false;
    boolean consider = false;
    
    public SheetEntry thisOne = null;
    public ArrayList<Move> whiteMoves = null;
    public ArrayList<Move> colorMoves = null;
    public Qwixx.MOVETYPE type = Qwixx.MOVETYPE.WHITES;
    

    public PlayerChooseMove (Player player, Qwixx.MOVETYPE type, ArrayList<Move> whiteMoves, ArrayList<Move> colorMoves) {
        
        super(player.game.frame, "Choose Move", Dialog.ModalityType.APPLICATION_MODAL);
        //super(player.game.frame, "Choose Move");
        
        this.player = player;
        this.game = player.game;
        this.frame = player.game.frame;

        this.consider = true;
        this.whiteMoves = whiteMoves;
        this.colorMoves = colorMoves;
        this.type = type;
        
    }

    public PlayerChooseMove (Player player, Qwixx.MOVETYPE type, ArrayList<Move> moves) {
        
        super(player.game.frame, "Choose Move", Dialog.ModalityType.APPLICATION_MODAL);
        
        this.player = player;
        this.game = player.game;
        this.frame = player.game.frame;

        this.consider = false;
        this.type = type;
        
        if (type == Qwixx.MOVETYPE.COLORS)
        {
            this.colorMoves = moves;
            this.whiteMoves = null;
        }
        if (type == Qwixx.MOVETYPE.WHITES)
        {
            this.whiteMoves = moves;
            this.colorMoves = null;
        }
    }
    
    public void buildAndShow () {
        
        Container pane = getContentPane();
        pane.setBackground(Qwixx.myback1);
        // setLocationRelativeTo(null);

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
        
        dvScore = new DisplayValue(player.sheet.score, Color.white, false);
        dvScore.buildAndShow();
        
        
        dvPenalties = new DisplayValue(player.sheet.penalties, Color.white, false);
        dvScore.buildAndShow();

        // Create a Quit Game Button
        quit = new JButton();
        quit.setText("QUIT GAME");
        quit.setFont(Qwixx.myfont18);
        // set action listener on the button
        quit.addActionListener(new PCMQuitActionListener());        

        
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

        
        int top = 5;
        int left = 5;
        int diceGapLittle = 5;
        
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
                
        nameTurnString = player.game.current.name + "'s Roll";
        nameMoveString = player.name + "'s " + typeString + " Move";

        JLabel nameTurn = new JLabel(nameTurnString);
        nameTurn.setFont(Qwixx.myfont18);
        nameTurn.setBackground(Color.white);
        nameTurn.setOpaque(true);
        nameTurn.setBounds(left + 50, top, 250, 40);
        nameTurn.setSize(250, 40);
        nameTurn.setHorizontalAlignment(JLabel.CENTER);
        nameTurn.setVerticalAlignment(JLabel.CENTER);        
        nameTurn.setVisible(true);
        pane.add(nameTurn);

        JLabel nameMove = new JLabel(nameMoveString);
        nameMove.setFont(Qwixx.myfont18);
        nameMove.setBackground(Color.white);
        nameMove.setOpaque(true);
        nameMove.setBounds(left + 400, top, 250, 40);
        nameMove.setSize(250, 40);
        nameMove.setHorizontalAlignment(JLabel.CENTER);
        nameMove.setVerticalAlignment(JLabel.CENTER);        
        nameMove.setVisible(true);
        pane.add(nameMove);
        
        int idx = 0;

        top = top + 55;
        
        left = 5;
        idx = 0;

        DisplayValue rscore = new DisplayValue(player.sheet.redsScore, Color.white, false);
        rscore.setBounds(left, top, 50, 50);
        rscore.buildAndShow();
        pane.add(rscore);

        left = left + 75;
        for (MoveSelect gs : reds)
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
        for (MoveSelect gs : yellows)
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
        for (MoveSelect gs : greens)
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
        for (MoveSelect gs : blues)
        {
            gs.setBounds(left + (55 * idx), top, 50, 50);
            gs.setVisible(true);
            pane.add(gs);
            idx++;
        }

        left = left + (55 * 11) + 25;
        DisplayValue bcount = new DisplayValue(player.sheet.bluesMarked, Color.white, false);
        bcount.setBounds(left, top, 50, 50);
        bcount.buildAndShow();
        pane.add(bcount);
        
        
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
        pane.add(scoreLabel);

        left = left + 65;
        dvScore.setBounds(left, top, 50, 50);
        dvScore.buildAndShow();
        pane.add(dvScore);

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
        pane.add(penaltiesLabel);
 
        left = left + 100;

        dvPenalties.setBounds(left, top, 50, 50);
        dvPenalties.buildAndShow();
        pane.add(dvPenalties);
        
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
        
        top = top + 60;

        quit.setBounds(10, top, 150, 50);
        quit.setVisible(true);
        pane.add(quit);
        
        skip.setBounds(500, top, 250, 50);
        skip.setVisible(true);
        pane.add(skip);
        
        
        Insets outerInsets = getInsets();
        setSize(
                800 + outerInsets.left + outerInsets.right,
                450 + outerInsets.top + outerInsets.bottom);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        //this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);                
        this.setLocation(400, (dim.height/2-this.getSize().height/2) + 100);                

        
        /*
        if  (
                type == Move.MOVETYPE.COLORS &&
                player.strategy == Player.STRATEGY.Human &&
                colorMoves.size() == 0
            )
        {
            NoColorMovesDialog pd = new NoColorMovesDialog(game);
            pd.setName("No Color Moves");
            pd.setLocationRelativeTo(null);
            pd.setSize(300, 300);
            pd.buildAndShow();
            //pd.setVisible(false);
            
            pd.dispose();
        }
        else
        {
            setVisible(true);
        }
        */

        setVisible(true);
        

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
            isquit = false;
            setVisible(false);
        }
    }    

    class PCMQuitActionListener implements ActionListener {

        //close and dispose of the window.
        public void actionPerformed(ActionEvent e) {
            isquit = true;
            setVisible(false);
        }
    }    

}


