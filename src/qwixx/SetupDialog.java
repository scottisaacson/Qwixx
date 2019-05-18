/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qwixx;

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

public class SetupDialog extends JDialog {

    public ArrayList<SetupPlayer> players;
    public JFrame frame;
    public String name;
    public Game game;
    int autoName;
    int numPlayers;
    JButton ok;
    JLabel  npl;
    JTextField npt;
    JButton apply;
    
 
    public SetupDialog(Game game) 
    {
        // super(game.frame, "Setup");
        super(game.frame, "Setup", Dialog.ModalityType.APPLICATION_MODAL);
        
        this.frame = game.frame;
        this.game = game;
        this.autoName = 1;
        
        this.players = new ArrayList<SetupPlayer>();
        this.numPlayers = game.players.size();
        
    }
    
    public void layoutPlayers(int top)
    {
        
        for (SetupPlayer sp : players)
        {
            sp.setVisible(false);
            remove(sp);
        }
        
        players.clear();
        
        int count = 1;
        for (Player p : game.players)
        {
            SetupPlayer sp = new SetupPlayer(count, p);
            sp.setBounds(10, top + ((count - 1) * 50), 300, 40);
            sp.setVisible(true);
            players.add(sp);
            add(sp);
            count++;
        }
       
        // this.repaint();
    }
    
    public void buildAndShow() {
        
        Container pane = getContentPane();
        pane.setLayout(null);
        pane.setBackground(Qwixx.myback1);

        int top = 10;
        int left = 10;
        int height = 40;
        
        npl = new JLabel("Number of Players:");
        npl.setBackground(Qwixx.myback1);
        npl.setFont(Qwixx.myfont14);
        npl.setVisible(true);
        Dimension npld = npl.getPreferredSize();
        npl.setBounds(left, top,  npld.width, height);
        pane.add(npl);
        
        npt = new JTextField(Integer.toString(numPlayers));
        npt.setFont(Qwixx.myfont14);
        npt.setVisible(true);
        Dimension nptd = npt.getPreferredSize();
        nptd.width = 40;
        npt.setPreferredSize(nptd);
        npt.setBounds((left + npld.width + 10), top, nptd.width, height);
        pane.add(npt);

        apply = new JButton("Apply");
        apply.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) {

                    game.players.clear();
                    
                    for (SetupPlayer sp : players)
                    {
                        String name = sp.name.getText();
                        Player.STRATEGY strategy = Player.STRATEGY.Computer;
                        if (!sp.type.isSelected())
                        {
                            strategy = Player.STRATEGY.Human;
                        }
                        Player newp = new Player(game, name, strategy); 
                        game.players.add(newp);
                    }
                    
                    numPlayers = Integer.parseInt(npt.getText());
                    if (numPlayers <= 2) numPlayers = 2;
                    if (numPlayers >= 6) numPlayers = 6;

                    if (numPlayers <= players.size())
                    {
                        int count = players.size() - numPlayers;
                        for (int i = 0; i < count; i++)
                        {
                            int remove = game.players.size() - 1;
                            game.players.remove(remove);
                        }
                    }
                    else
                    {
                        int count = numPlayers - game.players.size();
                        for (int i = 0; i < count; i++)
                        {
                            Player p = new Player(game, "New Player " + autoName, Player.STRATEGY.Computer);
                            game.players.add(p);
                            autoName++;
                        }
                    }

                    game.sd.dispose();

                    game.sd = new SetupDialog(game);
                    game.sd.buildAndShow();
                    
                }
            });
            
        apply.setFont(Qwixx.myfont14);
        apply.setVisible(true);
        Dimension ad = apply.getPreferredSize();
        ad.height = height;
        apply.setSize(ad);
        apply.setBounds((left + npld.width + 10 + nptd.width + 10), top, ad.width, height);
        pane.add(apply);

        top = top + 50;
        
        layoutPlayers(top);
        
        top = top + (50 * players.size()) + 20;
        
        JButton ok = new JButton("OK");
        ok.setFont(Qwixx.myfont14);
        ok.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) {

                    game.players.clear();
                    
                    for (SetupPlayer sp : players)
                    {
                        String name = sp.name.getText();
                        Player.STRATEGY strategy = Player.STRATEGY.Computer;
                        if (!sp.type.isSelected())
                        {
                            strategy = Player.STRATEGY.Human;
                        }
                        Player newp = new Player(game, name, strategy); 
                        game.players.add(newp);
                    }
                    
                    numPlayers = Integer.parseInt(npt.getText());
                    if (numPlayers <= 2) numPlayers = 2;
                    if (numPlayers >= 6) numPlayers = 6;

                    if (numPlayers <= players.size())
                    {
                        int count = players.size() - numPlayers;
                        for (int i = 0; i < count; i++)
                        {
                            int remove = game.players.size() - 1;
                            game.players.remove(remove);
                        }
                    }
                    else
                    {
                        int count = numPlayers - game.players.size();
                        for (int i = 0; i < count; i++)
                        {
                            Player p = new Player(game, "New Player " + autoName, Player.STRATEGY.Computer);
                            game.players.add(p);
                            autoName++;
                        }
                    }

                    game.sd.dispose();
                }
            });
        ok.setBounds(10, top, 100, height);
        ok.setVisible(true);
        pane.add(ok);
        
        top = top + 110;
        
        Insets outerInsets = getInsets();
        setSize(
            400 + outerInsets.left + outerInsets.right,
            top + outerInsets.top + outerInsets.bottom);
        
        invalidate();
        repaint();
        setVisible(true);

    }
}

