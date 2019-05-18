/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qwixx;

import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;

class RolledDice extends JDialog {

    public static final Dimension size50 = new Dimension(50, 50);
    public static final Dimension size30 = new Dimension(30, 30);
    
    Game game;
    Dice dice;
    Frame frame;
    Player player;
    
    JLabel name;
    JButton ok;
    
    RolledDie white1;
    RolledDie white2;
    RolledDie red;
    RolledDie yellow;
    RolledDie green;
    RolledDie blue;

    DisplayValue red1;
    DisplayValue red2;
    DisplayValue yellow1;
    DisplayValue yellow2;
    DisplayValue green1;
    DisplayValue green2;
    DisplayValue blue1;
    DisplayValue blue2;

    DisplayValue whitesred;
    DisplayValue whitesyellow;
    DisplayValue whitesgreen;
    DisplayValue whitesblue;


    public RolledDice (Game game) {
        
        // super(game.frame, "Rolled Dice", Dialog.ModalityType.APPLICATION_MODAL);
        super(game.frame, "Rolled Dice");        
        this.game = game;
        this.frame = game.frame;
        this.dice = game.dice;
        this.player = game.current;
    }
        
    public void buildAndShow () {
        
        Container pane = getContentPane();
        pane.setBackground(Qwixx.mydarkgrey);
        setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);        
 
        int diceTop = 70;
        int diceLeft = 10;
        int diceGapLittle = 10;
        int diceGapBig = 20;
        
        pane.setLayout(null);
        Insets paneInsets = pane.getInsets();

        name = new JLabel(player.name + "'s Roll");
        name.setBackground(Color.white);
        name.setHorizontalAlignment(JLabel.CENTER);
        name.setVerticalAlignment(JLabel.CENTER);        
        name.setOpaque(true);
        name.setFont(Qwixx.myfont18);
        name.setBounds(diceLeft + 100, 10, 200, 50);
        name.setVisible(true);
        pane.add(name);
        
        white1 = new RolledDie(dice.white1, Game.COLORS.WHITE);
        white1.setBounds(
                (diceLeft + (size50.width * 0) + (diceGapLittle * 0) + (diceGapBig * 0)) + paneInsets.left, 
                diceTop + paneInsets.top,
                size50.width,
                size50.height
        );
        pane.add(white1);
        
        white2 = new RolledDie(dice.white2, Game.COLORS.WHITE);
        white2.setBounds(
                (diceLeft + (size50.width * 1) + (diceGapLittle * 1) + (diceGapBig * 0)) + paneInsets.left, 
                diceTop + paneInsets.top,
                size50.width,
                size50.height
        );
        pane.add(white2);
        
        red = new RolledDie(dice.red, Game.COLORS.RED);
        red.setBounds(
                (diceLeft + (size50.width * 2) + (diceGapLittle * 1) + (diceGapBig * 1)) + paneInsets.left, 
                diceTop + paneInsets.top,
                size50.width,
                size50.height
        );
        if (game.redslocked)
        {
            red.lock();
        }
        else
        {
            red.unlock();
        }
        pane.add(red);
 
        yellow = new RolledDie(dice.yellow, Game.COLORS.YELLOW);
        yellow.setBounds(
                (diceLeft + (size50.width * 3) + (diceGapLittle * 2) + (diceGapBig * 1)) + paneInsets.left, 
                diceTop + paneInsets.top,
                size50.width, 
                size50.height
        );
        if (game.yellowslocked)
        {
            yellow.lock();
        }
        else
        {
            yellow.unlock();
        }
        pane.add(yellow);
        
        
        green = new RolledDie(dice.green, Game.COLORS.GREEN);
        green.setBounds(
                (diceLeft + (size50.width * 4) + (diceGapLittle * 3) + (diceGapBig * 1)) + paneInsets.left, 
                diceTop + paneInsets.top,
                size50.width, 
                size50.height
        );
        if (game.greenslocked)
        {
            green.lock();
        }
        else
        {
            green.unlock();
        }
        pane.add(green);

        
        blue = new RolledDie(dice.blue, Game.COLORS.BLUE);
        blue.setBounds(
                (diceLeft + (size50.width * 5) + (diceGapLittle * 4) + (diceGapBig * 1)) + paneInsets.left, 
                diceTop + paneInsets.top,
                size50.width,
                size50.height
        );
        if (game.blueslocked)
        {
            blue.lock();
        }
        else
        {
            blue.unlock();
        }
        pane.add(blue);


        whitesred = new DisplayValue(white1.die.val + white2.die.val, Qwixx.myred, false);
        whitesred.setBounds(
                diceLeft + 25 + paneInsets.left, 
                (diceTop + (size50.height * 1) + (diceGapLittle * 0) + (diceGapBig * 1)) + paneInsets.top,
                size50.width, 
                size50.height
        );
        whitesred.buildAndShow();
        if (game.redslocked)
        {
            whitesred.lock();
        }
        else
        {
            whitesred.unlock();
        }
        pane.add(whitesred);

        whitesyellow = new DisplayValue(white1.die.val + white2.die.val, Qwixx.myyellow, false);
        whitesyellow.setBounds(
                diceLeft + 25  + paneInsets.left, 
                (diceTop + (size50.height * 2) + (diceGapLittle * 1) + (diceGapBig * 1)) + paneInsets.top,
                size50.width, 
                size50.height
        );
        whitesyellow.buildAndShow();
        if (game.yellowslocked)
        {
            whitesyellow.lock();
        }
        else
        {
            whitesyellow.unlock();
        }
        pane.add(whitesyellow);
        
        whitesgreen = new DisplayValue(white1.die.val + white2.die.val, Qwixx.mygreen, false);
        whitesgreen.setBounds(
                diceLeft + 25  + paneInsets.left, 
                (diceTop + (size50.height * 3) + (diceGapLittle * 2) + (diceGapBig * 1)) + paneInsets.top,
                size50.width, 
                size50.height
        );
        whitesgreen.buildAndShow();
        if (game.greenslocked)
        {
            whitesgreen.lock();
        }
        else
        {
            whitesgreen.unlock();
        }
        pane.add(whitesgreen);
        
        whitesblue = new DisplayValue(white1.die.val + white2.die.val, Qwixx.myblue, false);
        whitesblue.setBounds(
                diceLeft + 25  + paneInsets.left, 
                (diceTop + (size50.height * 4) + (diceGapLittle * 3) + (diceGapBig * 1)) + paneInsets.top,
                size50.width, 
                size50.height
        );
        whitesblue.buildAndShow();
        if (game.blueslocked)
        {
            whitesblue.lock();
        }
        else
        {
            whitesblue.unlock();
        }
        pane.add(whitesblue);
        
        red1 = new DisplayValue(white1.die.val + red.die.val, Qwixx.myred, false);
        red1.setBounds(
                (diceLeft + (size50.width * 2) + (diceGapLittle * 1) + (diceGapBig * 1)) + paneInsets.left, 
                (diceTop + (size50.height * 1) + (diceGapLittle * 0) + (diceGapBig * 1)) + paneInsets.top,
                size50.width,
                size50.height
        );
        red1.buildAndShow();
        if (game.redslocked)
        {
            red1.lock();
        }
        else
        {
            red1.unlock();
        }
        pane.add(red1);
        

        red2 = new DisplayValue(white2.die.val + red.die.val, Qwixx.myred, false);
        red2.setBounds(
                (diceLeft + (size50.width * 2) + (diceGapLittle * 1) + (diceGapBig * 1)) + paneInsets.left, 
                (diceTop + (size50.height * 2) + (diceGapLittle * 1) + (diceGapBig * 1)) + paneInsets.top,
                size50.width,
                size50.height
        );
        red2.buildAndShow();
        if (game.redslocked)
        {
            red2.lock();
        }
        else
        {
            red2.unlock();
        }
        pane.add(red2);

        yellow1 = new DisplayValue(white1.die.val + yellow.die.val, Qwixx.myyellow, false);
        yellow1.setBounds(
                (diceLeft + (size50.width * 3) + (diceGapLittle * 2) + (diceGapBig * 1)) + paneInsets.left, 
                (diceTop + (size50.height * 1) + (diceGapLittle * 0) + (diceGapBig * 1)) + paneInsets.top,
                size50.width,
                size50.height
        );
        yellow1.buildAndShow();
        if (game.yellowslocked)
        {
            yellow1.lock();
        }
        else
        {
            yellow1.unlock();
        }
        pane.add(yellow1);
        

        yellow2 = new DisplayValue(white2.die.val + yellow.die.val, Qwixx.myyellow, false);
        yellow2.setBounds(
                (diceLeft + (size50.width * 3) + (diceGapLittle * 2) + (diceGapBig * 1)) + paneInsets.left, 
                (diceTop + (size50.height * 2) + (diceGapLittle * 1) + (diceGapBig * 1)) + paneInsets.top,
                size50.width,
                size50.height
        );
        yellow2.buildAndShow();
        if (game.yellowslocked)
        {
            yellow2.lock();
        }
        else
        {
            yellow2.unlock();
        }
        pane.add(yellow2);

        green1 = new DisplayValue(white1.die.val + green.die.val, Qwixx.mygreen, false);
        green1.setBounds(
                (diceLeft + (size50.width * 4) + (diceGapLittle * 3) + (diceGapBig * 1)) + paneInsets.left, 
                (diceTop + (size50.height * 1) + (diceGapLittle * 0) + (diceGapBig * 1)) + paneInsets.top,
                size50.width,
                size50.height
        );
        green1.buildAndShow();
        if (game.greenslocked)
        {
            green1.lock();
        }
        else
        {
            green1.unlock();
        }
        pane.add(green1);
        

        green2 = new DisplayValue(white2.die.val + green.die.val, Qwixx.mygreen, false);
        green2.setBounds(
                (diceLeft + (size50.width * 4) + (diceGapLittle * 3) + (diceGapBig * 1)) + paneInsets.left, 
                (diceTop + (size50.height * 2) + (diceGapLittle * 1) + (diceGapBig * 1)) + paneInsets.top,
                size50.width,
                size50.height
        );
        green2.buildAndShow();
        if (game.greenslocked)
        {
            green2.lock();
        }
        else
        {
            green2.unlock();
        }
        pane.add(green2);
        
        blue1 = new DisplayValue(white1.die.val + blue.die.val, Qwixx.myblue, false);
        blue1.setBounds(
                (diceLeft + (size50.width * 5) + (diceGapLittle * 4) + (diceGapBig * 1)) + paneInsets.left, 
                (diceTop + (size50.height * 1) + (diceGapLittle * 0) + (diceGapBig * 1)) + paneInsets.top,
                size50.width,
                size50.height
        );
        blue1.buildAndShow();
        if (game.blueslocked)
        {
            blue1.lock();
        }
        else
        {
            blue1.unlock();
        }
        pane.add(blue1);
        

        blue2 = new DisplayValue(white2.die.val + blue.die.val, Qwixx.myblue, false);
        blue2.setBounds(
                (diceLeft + (size50.width * 5) + (diceGapLittle * 4) + (diceGapBig * 1)) + paneInsets.left, 
                (diceTop + (size50.height * 2) + (diceGapLittle * 1) + (diceGapBig * 1)) + paneInsets.top,
                size50.width,
                size50.height
        );
        blue2.buildAndShow();
        if (game.blueslocked)
        {
            blue2.lock();
        }
        else
        {
            blue2.unlock();
        }
        pane.add(blue2);
        
        ok = new JButton();
        ok.setText("OK");
        ok.setFont(Qwixx.myfont14);
        // set action listener on the button
        ok.addActionListener(new RDOKActionListener());        
        ok.setBounds(250, 320, 100, 50);
        ok.setVisible(true);
        pane.add(ok);

        
        
        Insets outerInsets = getInsets();
        setSize(
            420 + outerInsets.left + outerInsets.right,
            420 + outerInsets.top + outerInsets.bottom);
        
        setVisible(true);
    }

    class RDOKActionListener implements ActionListener {

        //close and dispose of the window.
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
        }
    }    

    
}
