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

public class ResultDialog extends JDialog {

    public Game game;
    public Result result;
    public JFrame frame;
    public String name;
    public int size;
    public Game.GAMEOVER gameover;
    public ArrayList<Player> players;
 
    public ResultDialog(Game game) 
    {
        // super(game.frame, "Result");
        super(game.frame, "Result", Dialog.ModalityType.APPLICATION_MODAL);
        this.frame = game.frame;
        this.result = game.getResult();
        this.size = game.players.size();
        this.gameover = game.gameover;
    }
    
    public void buildAndShow() {
        
        Container pane = getContentPane();
        pane.setLayout(null);
        pane.setBackground(Qwixx.myback1);

        setSize(510, (size * 50) + 250);

        int top = 20;
        
        String reason = null;
        if (gameover == Game.GAMEOVER.LOCKED)
        {
            reason = "TWO COLORS LOCKED";
        } 
        if (gameover == Game.GAMEOVER.PENALTIES)
        {
            for (Player p : result.winners)
            {
                if (p.sheet.penalties >= 4)
                {
                    reason = p.name + " HAD 4 PENALTIES";
                    break;
                }
            }
        }
        var title = new JLabel("GAME OVER: " + reason);
        title.setBackground(Color.white);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.CENTER);        
        title.setOpaque(true);
        title.setFont(Qwixx.myfont18);
        title.setBounds(20, top, 435, 50);
        title.setVisible(true);
        pane.add(title);
        
        top = top + 60;
        
        for (Player p : result.winners)
        {
            PlayerSummary ps = new PlayerSummary(p);
            ps.buildAndShow();

            //  440 x 50;
            
            ps.setBounds(20, top, 440, 50);
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
        ok.setBounds(350, ((size * 50) + 150), 75, 50);
        ok.setVisible(true);
        pane.add(ok);
        

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);                
        
        setVisible(true);

    }
}
