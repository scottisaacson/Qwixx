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

public class PlayersSummary extends JDialog {

    public Game game;
    public JFrame frame;

    // public String name;
    public int size;
 
    public PlayersSummary(Game game) 
    {
        // super(game.frame, "Result");
        // super(game.frame, "Players Summary", Dialog.ModalityType.APPLICATION_MODAL);
        super(game.frame, "Players Summary");
        this.game = game;
        this.frame = game.frame;
        this.size = game.players.size();
    }
    
    public void buildAndShow() {
        
        Container pane = getContentPane();
        pane.setLayout(null);
        pane.setBackground(Qwixx.mydarkgrey);
        setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);        

        int top = 10;
        
        for (Player p : game.players)
        {
            PlayerSummary ps = new PlayerSummary(p);
            ps.setBackground(Qwixx.mydarkgrey);
            ps.setBounds(10, top, 480, 50);
            ps.setVisible(true);
            pane.add(ps);
            
            top = top + 60;
        }
        
        JButton ok = new JButton("OK");
        ok.setFont(Qwixx.myfont14);
        ok.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                    // game = new Game(game);
                    dispose();
                }
            });
        Dimension od = ok.getPreferredSize();
        ok.setBounds(350, ((size * 50) + 60 + 10), 75, 50);
        ok.setVisible(true);
        pane.add(ok);
        
        Insets outerInsets = getInsets();
        setSize(
                510 + outerInsets.left + outerInsets.right,
                ((size * 50) + 60 + 10 + 75 + 30) + outerInsets.top + outerInsets.bottom);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(
                dim.width/2-this.getSize().width/2, 
                0 /* dim.height/2-this.getSize().height/2  */ );                
        
        setVisible(true);

    }
}
