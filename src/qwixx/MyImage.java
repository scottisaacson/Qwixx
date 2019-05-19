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

public class MyImage extends JPanel {

    private Image myImage;
    private Image blue1;
    private Image red6;
    private Image yellow3;
    private Image green5;
    private Image white4;
    private Image white2;
    private Object o;

    public MyImage(Object o) {

        
        initPanel();
    }

    private void initPanel() {

        //loadImage();
        var dm = new Dimension(500, 300);
        setPreferredSize(dm);

        String path = "QwixxGit/resources/blue1_50.png";
        String fullPath = this.getClass().getClassLoader().getResource("").toString();
        // String fullPath = this.getClass().getResource("").toString();
        System.out.println(fullPath);
        fullPath = fullPath + "";
        System.out.println(fullPath);
        
        try {
            
            String resPath = fullPath + "blue1_50.png";
            blue1 = new ImageIcon(new URL(resPath)).getImage();

            resPath = fullPath + "red6_50.png";
            red6 = new ImageIcon(new URL(resPath)).getImage();

            resPath = fullPath + "yellow3_50.png";
            yellow3 = new ImageIcon(new URL(resPath)).getImage();

            resPath = fullPath + "green5_50.png";
            green5 = new ImageIcon(new URL(resPath)).getImage();

            resPath = fullPath + "white4_50.png";
            white4 = new ImageIcon(new URL(resPath)).getImage();

            resPath = fullPath + "white2_50.png";
            white2 = new ImageIcon(new URL(resPath)).getImage();
            
        } catch (MalformedURLException e)
        {
            System.exit(-8);
        }
    }

    /*
    private void loadImage() {

        myImage = new ImageIcon(Qwixx.Resources + "beach.jpg").getImage();
    }
    */

    private void doDrawing(Graphics g) {

        var g2d = (Graphics2D) g;
        int x, y;
        
        g2d.setColor(Qwixx.myback1);
        g2d.fillRect(0, 0, 400, 300);

        
        g2d.setRenderingHint(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        
        Font font = new Font("ARIEL", Font.BOLD, 36);
        g2d.setFont(font);
        g2d.setColor(Color.BLACK);

        
        
        // 400 x 300
        x = 135;
        y = 165;
        
        g2d.drawString("QWIXX", x, y);
        
        g2d.drawImage(blue1, 20, 20, null);
        g2d.drawImage(red6, 330, 20, null);
        g2d.drawImage(yellow3, 20, 230, null);
        g2d.drawImage(green5, 330, 230, null);

        g2d.drawImage(white4, 50, 125, null);
        g2d.drawImage(white2, 300, 125, null);
        
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }
}
