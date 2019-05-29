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

class Table extends JDialog {

    public static final Dimension size50 = new Dimension(50, 50);
    public static final Dimension size30 = new Dimension(30, 30);
    
    Game game;
    Frame frame;
    Player player;
    
    RolledDice rd;
    PlayersSummary ps;
    TurnStatus ts;
    ShowSheet ss;
    PlayerChooseMove pcm;
    ShowMove sm;
    
    JButton quit;
    JButton ok;

    boolean isquit = false;
    boolean isOK = false;
    
    
    
    public Table (Game game) {
        
        super(game.frame, "Table");
        this.game = game;
        this.frame = game.frame;

        rd = null;
        ps = null;
        ts = null;
        sm = null;
        ss = null;
        pcm = null;
    }

    public void buildAndShow () 
    {
        setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);        
        setUndecorated(true);        

        Container pane = getContentPane();
        pane.setLayout(null);
        pane.setBackground(Qwixx.myback1);
        ((JPanel) pane).setBorder(BorderFactory.createLineBorder(Color.black));

        
        int fullWidth = 1400;
        int fullHeight = 840;
        int top = 0;
        int left = 0;

        setSize(fullWidth, fullHeight);

        // ROLLED DICE
        rd = new RolledDice(game);
        rd.buildAndShow();
        
        int rdWidth = 400;
        int rdHeight = 320;
        
        top = 5;
        left = 5;
        rd.setBounds(left, top, rdWidth, rdHeight);
        add(rd);
        
        // PLAYERS SUMMARY

        top = top + rdHeight + 10;
        int psWidth = 460;
        int psHeight = (game.players.size() * 50) + 50;

        ps = new PlayersSummary(game);
        ps.buildAndShow();
        ps.setBounds(left, top, psWidth, psHeight);
        add(ps);
        

        // TURNS
        
        top = 5;
        left = 500;
        
        int tsWidth = 850;
        int tsHeight = 120;

        ts = new TurnStatus(game);
        ts.buildAndShow();
        ts.setBounds(left, top, tsWidth, tsHeight);
        add(ts);
        

        // SHOW MOVE
        
        top = 200;
        left = 530;
        
        int showWidth = 800;
        int showHeight = 400;

        pcm = new PlayerChooseMove(null, null, null, null);
        pcm.build();
        pcm.setBounds(left, top, showWidth, showHeight);
        pcm.setVisible(false);
        add(pcm);

        ss = new ShowSheet(game.current);
        ss.build();
        ss.setBounds(left, top, showWidth, showHeight);
        ss.setVisible(false);
        add(ss);
        
        System.out.println("Table.buildAndShow: creating ShowMove null" );
        sm = new ShowMove(game.current, WhichTurn.TYPE.COLOR, null);
        sm.build();
        sm.setBounds(left, top, showWidth, showHeight);
        System.out.println("Table.buildAndShow: showing ShowMove null" );
        sm.setVisible(true);
        add(sm);
        
        
        /*
        // TURNS
        
        top = top + psHeight + 100;
        int tsWidth = 820;
        int tsHeight = 120;

        ts = new TurnStatus(game);
        ts.buildAndShow();
        ts.setBounds(left, top, tsWidth, tsHeight);
        add(ts);
        

        // SHOW MOVE
        
        top = 100;
        left = 500;
        
        int showWidth = 800;
        int showHeight = 400;

        ss = new ShowSheet(game.current);
        ss.build();
        ss.setBounds(left, top, showWidth, showHeight);
        ss.setVisible(false);
        add(ss);
        
        System.out.println("Table.buildAndShow: creating ShowMove null" );
        sm = new ShowMove(game.current, Qwixx.MOVETYPE.WHITES, null);
        sm.build();
        sm.setBounds(left, top, showWidth, showHeight);
        System.out.println("Table.buildAndShow: showing ShowMove null" );
        sm.setVisible(true);
        add(sm);
        
        */
        
        // QUIT
        
        int quitWidth = 150;
        int quitHeight = 50;
        top = fullHeight - (quitHeight + 10);
        left = fullWidth - (quitWidth + 10);

        quit = new JButton();
        quit.setText("QUIT GAME");
        quit.setFont(Qwixx.myfont18);
        quit.addActionListener(new TableQuitActionListener());        
        quit.setSize(quitWidth, quitHeight);
        quit.setBounds(left, top, quitWidth, quitHeight);
        quit.setVisible(true);
        pane.add(quit);


        // OK
        
        int okWidth = 100;
        int okHeight = 50;

        left = left - (okWidth + 10);

        ok = new JButton();
        ok.setText("OK");
        ok.setFont(Qwixx.myfont18);
        ok.addActionListener(new TableOKActionListener());        
        ok.setSize(okWidth, okHeight);
        ok.setBounds(left, top, okWidth, okHeight);
        ok.setVisible(true);
        pane.add(ok);

        
        // now for the dialog itself
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((dim.width/2)-(getSize().width/2), ((dim.height/2)-(getSize().height/2)) - 20);
        setVisible(true);

    }

    class TableQuitActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            isquit = true;
            setVisible(false);
        }
    }    

    class TableOKActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            isOK = true;
            // setVisible(false);
        }
    }    


}


