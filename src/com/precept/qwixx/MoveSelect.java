/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.precept.qwixx;

import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;

class MoveSelect extends JPanel
{
    
    enum HIGHLIGHT {
        WHITE,
        COLOR,
        BOTH,
        NEITHER
    };
    
    WhichTurn.TYPE type;
    Color back;
    public HIGHLIGHT h;
    public boolean marked;
    public int val;
    String valString;
    JLabel l;
    Player player;
    public static final Dimension dl = new Dimension(40,40); 
    public static final Dimension dp = new Dimension(50,50);
    public static final Dimension d30 = new Dimension(30,30);
    
    public MoveSelect(Player p, int val, Color back, boolean m, WhichTurn.TYPE type)
    {
        
        super();
        h = HIGHLIGHT.NEITHER;
        marked = m;
        this.val = val;
        this.player = p;
        this.back = back;
        this.type = type;
        
        setLayout(null);
        setMinimumSize(dp);
        setMaximumSize(dp);
        setPreferredSize(dp);
        setSize(dp);
        /*
        if (marked == true)
        {
            setBackground(Color.LIGHT_GRAY);
        }
        else
        {
            setBackground(back);
        }
        */
            
        
        valString = "0";
        try
        {
            valString = Integer.toString(val);
        }
        catch (Exception e)
        {
            valString = "0";
        }
        l = new JLabel(valString);
        l.setFont(Qwixx.myfont18);
        l.setMinimumSize(dl);
        l.setMinimumSize(dl);
        Dimension ld = l.getPreferredSize();
        l.setBounds( (0 + (50 / 2) - (ld.width / 2)), (0 + (50 / 2) - (ld.height / 2)), ld.width, ld.height);
        l.setVisible(true);
        addMouseListener(new MoveSelectListener());        
        add(l);
        setVisible(true);
    }
    
    
    public void setHighlight(HIGHLIGHT h)
    {
        if (h == HIGHLIGHT.NEITHER)
        {
            this.h = h;
            
        }
        else if (h == HIGHLIGHT.COLOR)
        {
            if (this.h == HIGHLIGHT.NEITHER)
            {
                this.h = h;
            }
            else if (this.h == HIGHLIGHT.COLOR)
            {
                this.h = h;
            }
            else if (this.h == HIGHLIGHT.WHITE)
            {
                this.h = HIGHLIGHT.BOTH;
            }
            else
            {
                // oops
            }
        }
        else if (h == HIGHLIGHT.WHITE)
        {
            if (this.h == HIGHLIGHT.NEITHER)
            {
                this.h = h;
            }
            else if (this.h == HIGHLIGHT.WHITE)
            {
                this.h = h;
            }
            else if (this.h == HIGHLIGHT.COLOR)
            {
                this.h = HIGHLIGHT.BOTH;
            }
            else
            {
                // oops
            }
        }
        else
        {
            // oops
        }

        invalidate();
    }

    public HIGHLIGHT getHighlight()
    {
        return h;
    }

   
    public void update(int value)
    {
        val = value;
        valString = "0";
        try
        {
            valString = Integer.toString(val);
        }
        catch (Exception e)
        {
            valString = "0";
        }
        l.setText(valString);
        l.setMinimumSize(dl);
        l.setMinimumSize(dl);
        Dimension ld = l.getPreferredSize();
        l.setBounds( (0 + (50 / 2) - (ld.width / 2)), (0 + (50 / 2) - (ld.height / 2)), ld.width, ld.height);
        l.invalidate();
        
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color c = g.getColor();
        // l.repaint();

        g.setColor(back);
        g.fillRect(0, 0, 50, 50);
        
        if (marked)
        {
            g.setColor(Color.black);
            g.drawLine(0, 0, 50, 50);
            g.drawLine(0, 1, 49, 50);
            g.drawLine(1, 0, 50, 49);
            g.drawLine(0, 2, 48, 50);
            g.drawLine(2, 0, 50, 48);

            g.drawLine(0, 50, 50, 0);
            g.drawLine(0, 49, 49, 0);
            g.drawLine(1, 50, 50, 1);
            g.drawLine(0, 48, 48, 0);
            g.drawLine(2, 50, 50, 2);
 
            g.setColor(c);
        }
        if (!(h == HIGHLIGHT.NEITHER))
        {
            if (h == HIGHLIGHT.WHITE)
            {
                g.setColor(Color.white);
                g.fillRect(0, 0, 50, 5);
                g.fillRect(0, 0, 5, 50);
                g.fillRect(45, 0, 5, 50);
                g.fillRect(0, 45, 50, 5);
            }
            if (h == HIGHLIGHT.COLOR)
            {
                g.setColor(Color.magenta);
                g.fillRect(0, 0, 50, 5);
                g.fillRect(0, 0, 5, 50);
                g.fillRect(45, 0, 5, 50);
                g.fillRect(0, 45, 50, 5);
            }
            if (h == HIGHLIGHT.BOTH)
            {
                /*
                g.setColor(Color.magenta);
                g.fillRect(0, 0, 50, 5);
                g.fillRect(0, 0, 5, 50);
                g.fillRect(45, 0, 5, 50);
                g.fillRect(0, 45, 50, 5);
                */

                g.setColor(Color.white);
                g.fillRect(0, 0, 50, 5); // top (top left  50 x 5)
                g.fillRect(0, 0, 5, 50); // left (top left  5 x 50)
                g.fillRect(45, 0, 5, 50); // right (top left 5 x 50)
                g.fillRect(0, 45, 50, 5); // bottom (top left 50 x 5)

                g.setColor(Color.magenta);
                g.fillRect(5, 5, 40, 5); // top (top left  40 x 5)
                g.fillRect(5, 5, 5, 40); // left (top left  5 x 40)
                g.fillRect(40, 5, 5, 40); // right (top left 5 x 40)
                g.fillRect(5, 40, 40, 5); // bottom (top left 40 x 5)
                
            }
        }

        g.setColor(c);

    }    

    public void lock()
    {
        setVisible(false);
    }

    public void unlock()
    {
        setVisible(true);
    }
    
    
    
    
}


class MoveSelectListener implements MouseListener {
     
    
    public void mouseClicked(MouseEvent e) {

        MoveSelect ms = (MoveSelect) e.getComponent();
        PlayerChooseMove pcm = (PlayerChooseMove) ms.getParent();

        /*
        Container c1 = ms.getParent();   // Panel
        Container c2 = c1.getParent();   // Display
        Container c3 = c2.getParent();   // Root
        */
         
        int val = ms.val;
        Game.COLORS c = null;
        if (ms.back.equals(Qwixx.myred)) c = Game.COLORS.RED;
        if (ms.back.equals(Qwixx.myyellow)) c = Game.COLORS.YELLOW;
        if (ms.back.equals(Qwixx.mygreen)) c = Game.COLORS.GREEN;
        if (ms.back.equals(Qwixx.myblue)) c = Game.COLORS.BLUE;
        
        pcm.thisOne = ms.player.sheet.findEntry(c, val);

        boolean goodMove = false;
        ArrayList<Move> moves = null;
        if (ms.type == WhichTurn.TYPE.COLOR)
        {
            moves = ms.player.colorMoves;
        }
        else
        {
            moves = ms.player.whiteMoves;
        }

        for (Move m : moves)
        {
            if (c == m.se.color && val == m.se.val)
            {
                goodMove = true;
            }
        }

        if (goodMove == false)
        {
            // we did not select valid one, keep waiting                
            pcm.thisOne = null;
            pcm.isselected = false;
            pcm.isskip = false;
        }
        else
        {
            // we found a valid one, keep waiting                
            pcm.isselected = true;
            pcm.isskip = false;
        }
    }


    public void mousePressed(MouseEvent e) {

        MoveSelect ms = (MoveSelect) e.getComponent();

        PlayerChooseMove pcm = (PlayerChooseMove) ms.getParent();

        /*
        Container c1 = ms.getParent();   // Panel
        Container c2 = c1.getParent();   // Display
        Container c3 = c2.getParent();   // Root
        */
         
        int val = ms.val;
        Game.COLORS c = null;
        if (ms.back.equals(Qwixx.myred)) c = Game.COLORS.RED;
        if (ms.back.equals(Qwixx.myyellow)) c = Game.COLORS.YELLOW;
        if (ms.back.equals(Qwixx.mygreen)) c = Game.COLORS.GREEN;
        if (ms.back.equals(Qwixx.myblue)) c = Game.COLORS.BLUE;
        
        pcm.thisOne = ms.player.sheet.findEntry(c, val);

        boolean goodMove = false;
        ArrayList<Move> moves = null;
        if (ms.type == WhichTurn.TYPE.COLOR)
        {
            moves = ms.player.colorMoves;
        }
        else
        {
            moves = ms.player.whiteMoves;
        }

        for (Move m : moves)
        {
            if (c == m.se.color && val == m.se.val)
            {
                goodMove = true;
            }
        }

        if (goodMove == false)
        {
            // we did not select valid one, keep waiting                
            pcm.thisOne = null;
            pcm.isselected = false;
            pcm.isskip = false;
        }
        else
        {
            // we found a valid one, keep waiting                
            pcm.isselected = true;
            pcm.isskip = false;
        }

    }


    public void mouseReleased(MouseEvent e) {
    }
    public void mouseEntered(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }
}

