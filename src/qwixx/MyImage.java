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

    public MyImage() {

        initPanel();
    }

    private void initPanel() {

        loadImage();
        // var dm = new Dimension(myImage.getWidth(null), myImage.getHeight(null));
        var dm = new Dimension(500, 300);
        setPreferredSize(dm);
    }

    private void loadImage() {

        myImage = new ImageIcon(Qwixx.Resources + "beach.jpg").getImage();
    }

    private void doDrawing(Graphics g) {

        var g2d = (Graphics2D) g;

        g2d.drawImage(myImage, 0, 0, null);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }
}
