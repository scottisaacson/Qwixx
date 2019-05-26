/**********************************************************
 *  Copyright 2019, The Precept Group, LLC
 *  See the LICENSE file for license information.
 *
 *********************************************************/

package qwixx;

import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;

class DisplayValue extends JPanel
{
    Color back;
    public boolean h;
    public boolean marked;
    public int val;
    String valString;
    JLabel l;
    public static final Dimension dl = new Dimension(40,40); 
    public static final Dimension dp = new Dimension(50,50);
    public static final Dimension d30 = new Dimension(30,30);
    
    public DisplayValue(int val, Color back, boolean m)
    {
        
        super();
        h = false;
        marked = m;
        this.val = val;
        this.back = back;
    
    }

    public DisplayValue(SheetEntry se)
    {

        super();
        Color c = Qwixx.myred;
        if (se.color == Game.COLORS.RED) c = Qwixx.myred;
        if (se.color == Game.COLORS.BLUE) c = Qwixx.myblue;
        if (se.color == Game.COLORS.YELLOW) c = Qwixx.myyellow;
        if (se.color == Game.COLORS.GREEN) c = Qwixx.mygreen;
        if (se.color == Game.COLORS.WHITE) c = Color.white;

        h = false;
        marked = se.marked;
        this.val = se.val;
        this.back = c;
    
    }
    
    public void buildAndShow()
    {
       
        setLayout(null);
        setMinimumSize(dp);
        setMaximumSize(dp);
        setPreferredSize(dp);
        setSize(dp);
        setBackground(back);
        
        
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
        add(l);
        invalidate();
        setVisible(true);
    }

    
    public void highlight()
    {
        h = true;
        invalidate();
    }

    public void unhighlight()
    {
        h = false;
        invalidate();
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
        }
        
        if (h)
        {
            g.setColor(Color.MAGENTA);
            g.fillRect(0, 0, 50, 5);
            g.fillRect(0, 0, 5, 50);
            g.fillRect(45, 0, 5, 50);
            g.fillRect(0, 45, 50, 5);
            g.setColor(c);
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


/*
class HighlightMouseListener implements MouseListener {
     
    
    public void mouseClicked(MouseEvent e) {
         DisplayValue p = (DisplayValue) e.getComponent();
         if (p.h == true)
         {
             p.h = false;
         }
         else
         {
             p.h = true;
             
         }
         p.repaint();

      }
      public void mousePressed(MouseEvent e) {
      }
      public void mouseReleased(MouseEvent e) {
      }
      public void mouseEntered(MouseEvent e) {
      }
      public void mouseExited(MouseEvent e) {
      }
}

*/

