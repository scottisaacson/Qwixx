/****************************************************************************
 * 2019 The Precept Group, LLC. 
 * See LICENSE for license information.
 ***************************************************************************/

package com.precept.qwixx;

import static com.precept.qwixx.Qwixx.myfont14;
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

public class PlayDialog extends JDialog {

    public Qwixx q;
    public Result result;
    public Game game;
    public JFrame frame;
    public String name;
    public Player player;
 
    public PlayDialog(Game game) 
    {
        // super(game.frame, "Play");
        super(game.frame, "Play", Dialog.ModalityType.APPLICATION_MODAL);
        this.frame = frame;
        this.game = game;
        this.player = game.current;
        
        result = null;
    }
    
    public void buildAndShow() {
        
        Container pane = getContentPane();
        pane.setLayout(null);
        pane.setBackground(Qwixx.myback1);
        // setLocationRelativeTo(null);
        
        
        
        
        
        JButton quit = new JButton("QUIT");
        quit.setFont(myfont14);
        quit.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
        // Dimension qd = quit.getPreferredSize();
        quit.setBounds(10, 10, 100, 40);
        quit.setVisible(true);
        pane.add(quit);


        JButton turn = new JButton("TAKE A TURN");
        turn.setFont(myfont14);
        turn.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) {

                    /*
                    game.takeTurn();
                    game.checkForGameOver();
                    if (game.gameover == Game.GAMEOVER.LOCKED || game.gameover == Game.GAMEOVER.PENALTIES)
                    {
                        ResultDialog rd = new ResultDialog(game);
                        rd.buildAndShow();

                        dispose();
                    }
                    if (game.gameover == Game.GAMEOVER.QUIT)
                    {
                        dispose();
                    }
                    */
                }
            });
        // Dimension td = turn.getPreferredSize();
        turn.setBounds(120, 10, 300, 40);
        turn.setVisible(true);
        pane.add(turn);
        
        JButton play = new JButton("PLAY ALL");
        play.setFont(myfont14);
        play.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) {

                    game.playGame();

                    if (game.gameover == Game.GAMEOVER.LOCKED || game.gameover == Game.GAMEOVER.PENALTIES)
                    {
                        ResultDialog rd = new ResultDialog(game);
                        rd.buildAndShow();

                        dispose();
                    }
                    if (game.gameover == Game.GAMEOVER.QUIT)
                    {
                        dispose();
                    }
                    
                }
            });
        // Dimension pd = play.getPreferredSize();
        // play.setBounds(10, 50, 100, 40);
        play.setBounds(430, 10, 150, 40);
        play.setVisible(true);
        pane.add(play);
        
        
        
        Insets outerInsets = getInsets();
        setSize(
                600 + outerInsets.left + outerInsets.right,
                100 + outerInsets.top + outerInsets.bottom);
        
        setVisible(true);

    }
}
