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


public class Qwixx 
{
    //  Globals
    
    public static final Color mygreen = new Color(100, 255, 100);
    public static final Color myred = new Color(255, 100, 100);
    public static final Color myblue = new Color(100, 100, 255);
    public static final Color myyellow = new Color(255, 255, 100);
    
    public static final Color mygrey = new Color(214, 214, 214);
    public static final Color mydarkgrey = new Color(35, 35, 35);
    
    public static final Color myback1 = new Color(140, 200, 140);
    
    public static final Font myfont12 = new Font("Ariel", Font.BOLD, 12);
    public static final Font myfont14 = new Font("Ariel", Font.BOLD, 14);
    public static final Font myfont18 = new Font("Ariel", Font.BOLD, 18);
    public static final Font myfont28 = new Font("Ariel", Font.BOLD, 28);
    
    public static final Dimension size50 = new Dimension(50, 50);
    // Dimension size30 = new Dimension(30, 30);
      
    // Swing Comments
    static Game game;
    static JFrame frame;
   
    enum MOVETYPE {
        COLORS,
        WHITES,
        WHITES_CONSIDERING_COLORS
    };

    
    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                
                frame = new JFrame("Qwixx Game");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                game = null;

                Container pane = frame.getContentPane();
                pane.setLayout(null);
                pane.setBackground(mydarkgrey);
                Insets paneInsets = pane.getInsets();

                MyImage mi = new MyImage();
                mi.setVisible(true);
                mi.setBounds(10, 10, 400, 300);
                pane.add(mi);
                
                int top = 320;
                int left = 70;
                
                JButton quitButton = new JButton("QUIT");
                quitButton.setBounds(left, top, 80, 40);
                quitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                });
                pane.add(quitButton);

                JButton setupButton = new JButton("SETUP");
                setupButton.setBounds(left + 90, top, 100, 40);
                setupButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                     
                        if (game == null)
                        {
                            game = new Game(frame, null);
                        }
                        else
                        {
                            game = new Game(game);
                        }
                        
                        if (game.sd != null) game.sd.dispose();
                        game.sd = new SetupDialog(game);
                        game.sd.buildAndShow();
                    }
                });
                pane.add(setupButton);

                JButton playButton = new JButton("PLAY");
                playButton.setBounds(left + 200, top, 80, 40);
                playButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        if (game == null)
                        {
                            game = new Game(frame, null);
                        }
                        else
                        {
                            game = new Game(game);
                        }

                        if (game.sd != null) game.sd.dispose();
                        
                        game.playGame();
                        
                    }
                });
                pane.add(playButton);

                frame.setSize(435, 430);

                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);                

                frame.setVisible(true);
                
            }
        });
    }
    
    public static Image getResourceImage(Object o, String resName)
    {
        //javax.swing.ImageIcon ii = new javax.swing.ImageIcon(o.getClass().getResource("/" + resName));
        javax.swing.ImageIcon ii = new javax.swing.ImageIcon("/Users/sisaacson/projects/Qwixx2/resources/" + resName);
        Image ret = ii.getImage();
        
        return ret;
        
    }
}

    

