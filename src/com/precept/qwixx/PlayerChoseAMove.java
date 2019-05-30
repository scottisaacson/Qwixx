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

class PlayerChoseAMove extends JDialog {

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
    JButton ok;
    JButton quit;
    JLabel event;
    boolean isQuit = false;
    boolean consider = false;
    
    public SheetEntry thisOne = null;
    public ArrayList<Move> whiteMoves = null;
    public ArrayList<Move> colorMoves = null;
    public Qwixx.MOVETYPE type = Qwixx.MOVETYPE.WHITES;
    public WhichTurn.TYPE turnType = null;
    

    public PlayerChoseAMove (Player player, Qwixx.MOVETYPE type, SheetEntry se) {
        
        super(player.game.frame, "Chose Move", Dialog.ModalityType.APPLICATION_MODAL);
        //super(player.game.frame, "Choose Move");
        
        this.player = player;
        this.game = player.game;
        this.frame = player.game.frame;

        this.consider = true;
        this.whiteMoves = whiteMoves;
        this.colorMoves = colorMoves;
        this.type = type;
        this.thisOne = se;
        this.turnType = WhichTurn.TYPE.COLOR;
        
    }

    
    public void buildAndShow () {
        
        Container pane = getContentPane();
        pane.setBackground(Qwixx.mydarkgrey);
        // pane.setBackground(Qwixx.myback1);
        // setLocationRelativeTo(null);

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
        dvPenalties.buildAndShow();

        // Create a Quit Game Button
        quit = new JButton();
        quit.setText("QUIT GAME");
        quit.setFont(Qwixx.myfont18);
        // set action listener on the button
        quit.addActionListener(new PCAMQuitActionListener());        

        
        // Create an OK button
        ok = new JButton();
        ok.setText("OK");
        ok.setFont(Qwixx.myfont18);
        // set action listener on the button
        ok.addActionListener(new PCAMOKActionListener());        

        String eventString;
        StringBuffer sb = new StringBuffer();
        
        if (thisOne == null)
        {
            if (type == Qwixx.MOVETYPE.WHITES)
                sb.append("SKIPPED");
            if (type == Qwixx.MOVETYPE.COLORS)
                sb.append("TOOK A PENALTY");
        }
        else
        {
            sb.append("TOOK ");
            /*
            if (thisOne.color.equals(Qwixx.myred)) sb.append(" RED ");
            if (thisOne.color.equals(Qwixx.myyellow)) sb.append(" YELLOW ");
            if (thisOne.color.equals(Qwixx.mygreen)) sb.append(" GREEN ");
            if (thisOne.color.equals(Qwixx.myblue)) sb.append(" BLUE ");
            */
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
        pane.add(event);

        
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
        scoreLabel.setBackground(Color.white);
        scoreLabel.setOpaque(true);
        scoreLabel.setBounds(left, top, 70, 50);
        scoreLabel.setSize(70, 50);
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

        
        left = 500;
        event.setBounds(left, top, 250, 50);
        event.setVisible(true);

        
        top = top + 60;
        left = 10;

        quit.setBounds(left, top, 150, 50);
        quit.setVisible(true);
        pane.add(quit);
        
        left = 500;
        ok.setBounds(left, top, 250, 50);
        ok.setVisible(true);
        pane.add(ok);
        
        
        Insets outerInsets = getInsets();
        setSize(
                800 + outerInsets.left + outerInsets.right,
                450 + outerInsets.top + outerInsets.bottom);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        //this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);                
        this.setLocation(400, (dim.height/2-this.getSize().height/2) + 100);                

        
        
        // SheetEntry highlightThis = player.findEntry(thisOne);
        SheetEntry highlightThis = thisOne;
        SheetEntry saveThis = thisOne;
        
        MoveSelect.HIGHLIGHT h = null;
        if (type == Qwixx.MOVETYPE.COLORS) h = MoveSelect.HIGHLIGHT.COLOR;
        if (type == Qwixx.MOVETYPE.WHITES) h = MoveSelect.HIGHLIGHT.WHITE;
        
        highlight(thisOne, h);
        
        setVisible(true);

    }

    private void highlight(SheetEntry se, MoveSelect.HIGHLIGHT h)
    {
        
        if (se == null)
        {
            return;
        }
        
        
        if (se.color == Game.COLORS.RED)
        {
            for (MoveSelect gs : reds)
            {
                if (gs.val == se.val)
                {
                    gs.setHighlight(h);
                }
            }
        }

        if (se.color == Game.COLORS.YELLOW)
        {
            for (MoveSelect gs : yellows)
            {
                if (gs.val == se.val)
                {
                    gs.setHighlight(h);
                }
            }
        }

        if (se.color == Game.COLORS.GREEN)
        {
            for (MoveSelect gs : greens)
            {
                if (gs.val == se.val)
                {
                    gs.setHighlight(h);
                }
            }
        }

        if (se.color == Game.COLORS.BLUE)
        {
            for (MoveSelect gs : blues)
            {
                if (gs.val == se.val)
                {
                    gs.setHighlight(h);
                }
            }
        }
        
        return;
        
    }

    class PCAMOKActionListener implements ActionListener {

        //close and dispose of the window.
        public void actionPerformed(ActionEvent e) {
            isQuit = false;
            setVisible(false);
        }
    }    

    class PCAMQuitActionListener implements ActionListener {

        //close and dispose of the window.
        public void actionPerformed(ActionEvent e) {
            isQuit = true;
            setVisible(false);
        }
    }    

}


