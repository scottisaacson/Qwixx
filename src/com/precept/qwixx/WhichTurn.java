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

class WhichTurn extends JPanel
{
    
    enum TYPE {
        COLOR,
        WHITE
    };
    
    Color color;
    String text;
    TYPE type;
    JLabel l;
    
    public static final Dimension dl = new Dimension(40, 40); 
    public static final Dimension d = new Dimension(90, 45);
    
    public WhichTurn(TYPE type)
    {
        
        super();
        this.color = Color.gray;
        if (type == TYPE.COLOR)
        {
            this.text = "Color";
        }
        else
        {
            this.text = "White";
        }
    
    }

    
    public void buildAndShow()
    {
       
        setLayout(null);
        setMinimumSize(d);
        setMaximumSize(d);
        setPreferredSize(d);
        setSize(d);

        l = new JLabel(text);
        l.setHorizontalAlignment(SwingConstants.CENTER);             
        l.setFont(Qwixx.myfont14);
        l.setMinimumSize(dl);
        Dimension dPref = l.getPreferredSize();
        int thisLeft = (d.width / 2) - (dPref.width / 2);
        int thisTop = (d.height / 2) - (dPref.height / 2);
        l.setBounds(thisLeft, thisTop, dPref.width, dPref.height);
        l.setVisible(true);
        add(l);

        setVisible(true);
    }

    
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Color c = g.getColor();

        g.setColor(color);
        g.fillRect(0, 0, d.width, d.height);

        g.setColor(c);
    }    

}


