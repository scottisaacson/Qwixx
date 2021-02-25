/****************************************************************************
 * 2019 The Precept Group, LLC. 
 * See LICENSE for license information.
 ***************************************************************************/

package com.precept.qwixx;

import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;

class RolledDice extends JPanel {

    Game game;
    Dice dice;
    Frame frame;
    Player player;
    
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
    
    JLabel title;


    public RolledDice (Game game) {
        
        this.game = game;
        this.frame = game.frame;
        this.dice = game.dice;
        this.player = game.current;
        this.title = null;
    }
        
    public void buildAndShow () 
    {
        
        setBackground(Qwixx.myback1);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setLayout(null);

        setSize(400, 390);

        int titleWidth = 300;
        int titleHeight = 50;

        int left = 50;
        int top = 10;
        
        String playerName = "<empty>";
        if (player != null ) playerName = player.name;
        title = new JLabel(playerName + "'s ROLL");
        title.setFont(Qwixx.myfont18);
        title.setBackground(Color.white);
        title.setOpaque(true);
        title.setSize(titleWidth, titleHeight);
        title.setBounds(left, top, titleWidth, titleHeight);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.CENTER);        
        add(title);
        
        
        int diceTop = 80;
        int diceLeft = 10;
        int diceGapLittle = 10;
        int diceGapBig = 20;

        white1 = new RolledDie(dice.white1, Game.COLORS.WHITE);
        white1.setBounds(
                diceLeft + (Qwixx.size50.width * 0) + (diceGapLittle * 0) + (diceGapBig * 0), 
                diceTop,
                Qwixx.size50.width,
                Qwixx.size50.height
        );
        add(white1);
        
        white2 = new RolledDie(dice.white2, Game.COLORS.WHITE);
        white2.setBounds(
                diceLeft + (Qwixx.size50.width * 1) + (diceGapLittle * 1) + (diceGapBig * 0), 
                diceTop,
                Qwixx.size50.width,
                Qwixx.size50.height
        );
        add(white2);
        
        red = new RolledDie(dice.red, Game.COLORS.RED);
        red.setBounds(
                diceLeft + (Qwixx.size50.width * 2) + (diceGapLittle * 1) + (diceGapBig * 1), 
                diceTop,
                Qwixx.size50.width,
                Qwixx.size50.height
        );
        if (game.redslocked)
        {
            red.lock();
        }
        else
        {
            red.unlock();
        }
        add(red);
 
        yellow = new RolledDie(dice.yellow, Game.COLORS.YELLOW);
        yellow.setBounds(
                diceLeft + (Qwixx.size50.width * 3) + (diceGapLittle * 2) + (diceGapBig * 1), 
                diceTop,
                Qwixx.size50.width, 
                Qwixx.size50.height
        );
        if (game.yellowslocked)
        {
            yellow.lock();
        }
        else
        {
            yellow.unlock();
        }
        add(yellow);
        
        
        green = new RolledDie(dice.green, Game.COLORS.GREEN);
        green.setBounds(
                diceLeft + (Qwixx.size50.width * 4) + (diceGapLittle * 3) + (diceGapBig * 1), 
                diceTop,
                Qwixx.size50.width, 
                Qwixx.size50.height
        );
        if (game.greenslocked)
        {
            green.lock();
        }
        else
        {
            green.unlock();
        }
        add(green);

        
        blue = new RolledDie(dice.blue, Game.COLORS.BLUE);
        blue.setBounds(
                diceLeft + (Qwixx.size50.width * 5) + (diceGapLittle * 4) + (diceGapBig * 1), 
                diceTop,
                Qwixx.size50.width,
                Qwixx.size50.height
        );
        if (game.blueslocked)
        {
            blue.lock();
        }
        else
        {
            blue.unlock();
        }
        add(blue);


        whitesred = new DisplayValue(white1.die.val + white2.die.val, Qwixx.myred, false);
        whitesred.setBounds(
                diceLeft + 25, 
                diceTop + (Qwixx.size50.height * 1) + (diceGapLittle * 0) + (diceGapBig * 1),
                Qwixx.size50.width, 
                Qwixx.size50.height
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
        add(whitesred);

        whitesyellow = new DisplayValue(white1.die.val + white2.die.val, Qwixx.myyellow, false);
        whitesyellow.setBounds(
                diceLeft + 25, 
                diceTop + (Qwixx.size50.height * 2) + (diceGapLittle * 1) + (diceGapBig * 1),
                Qwixx.size50.width, 
                Qwixx.size50.height
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
        add(whitesyellow);
        
        whitesgreen = new DisplayValue(white1.die.val + white2.die.val, Qwixx.mygreen, false);
        whitesgreen.setBounds(
                diceLeft + 25, 
                diceTop + (Qwixx.size50.height * 3) + (diceGapLittle * 2) + (diceGapBig * 1),
                Qwixx.size50.width, 
                Qwixx.size50.height
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
        add(whitesgreen);
        
        whitesblue = new DisplayValue(white1.die.val + white2.die.val, Qwixx.myblue, false);
        whitesblue.setBounds(
                diceLeft + 25, 
                diceTop + (Qwixx.size50.height * 4) + (diceGapLittle * 3) + (diceGapBig * 1),
                Qwixx.size50.width, 
                Qwixx.size50.height
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
        add(whitesblue);
        
        red1 = new DisplayValue(white1.die.val + red.die.val, Qwixx.myred, false);
        red1.setBounds(
                diceLeft + (Qwixx.size50.width * 2) + (diceGapLittle * 1) + (diceGapBig * 1), 
                diceTop + (Qwixx.size50.height * 1) + (diceGapLittle * 0) + (diceGapBig * 1),
                Qwixx.size50.width,
                Qwixx.size50.height
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
        add(red1);
        

        red2 = new DisplayValue(white2.die.val + red.die.val, Qwixx.myred, false);
        red2.setBounds(
                diceLeft + (Qwixx.size50.width * 2) + (diceGapLittle * 1) + (diceGapBig * 1), 
                diceTop + (Qwixx.size50.height * 2) + (diceGapLittle * 1) + (diceGapBig * 1),
                Qwixx.size50.width,
                Qwixx.size50.height
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
        add(red2);

        yellow1 = new DisplayValue(white1.die.val + yellow.die.val, Qwixx.myyellow, false);
        yellow1.setBounds(
                diceLeft + (Qwixx.size50.width * 3) + (diceGapLittle * 2) + (diceGapBig * 1), 
                diceTop + (Qwixx.size50.height * 1) + (diceGapLittle * 0) + (diceGapBig * 1),
                Qwixx.size50.width,
                Qwixx.size50.height
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
        add(yellow1);
        

        yellow2 = new DisplayValue(white2.die.val + yellow.die.val, Qwixx.myyellow, false);
        yellow2.setBounds(
                diceLeft + (Qwixx.size50.width * 3) + (diceGapLittle * 2) + (diceGapBig * 1), 
                diceTop + (Qwixx.size50.height * 2) + (diceGapLittle * 1) + (diceGapBig * 1),
                Qwixx.size50.width,
                Qwixx.size50.height
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
        add(yellow2);

        green1 = new DisplayValue(white1.die.val + green.die.val, Qwixx.mygreen, false);
        green1.setBounds(
                diceLeft + (Qwixx.size50.width * 4) + (diceGapLittle * 3) + (diceGapBig * 1), 
                diceTop + (Qwixx.size50.height * 1) + (diceGapLittle * 0) + (diceGapBig * 1),
                Qwixx.size50.width,
                Qwixx.size50.height
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
        add(green1);
        

        green2 = new DisplayValue(white2.die.val + green.die.val, Qwixx.mygreen, false);
        green2.setBounds(
                diceLeft + (Qwixx.size50.width * 4) + (diceGapLittle * 3) + (diceGapBig * 1), 
                diceTop + (Qwixx.size50.height * 2) + (diceGapLittle * 1) + (diceGapBig * 1),
                Qwixx.size50.width,
                Qwixx.size50.height
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
        add(green2);
        
        blue1 = new DisplayValue(white1.die.val + blue.die.val, Qwixx.myblue, false);
        blue1.setBounds(
                diceLeft + (Qwixx.size50.width * 5) + (diceGapLittle * 4) + (diceGapBig * 1), 
                diceTop + (Qwixx.size50.height * 1) + (diceGapLittle * 0) + (diceGapBig * 1),
                Qwixx.size50.width,
                Qwixx.size50.height
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
        add(blue1);
        

        blue2 = new DisplayValue(white2.die.val + blue.die.val, Qwixx.myblue, false);
        blue2.setBounds(
                diceLeft + (Qwixx.size50.width * 5) + (diceGapLittle * 4) + (diceGapBig * 1), 
                diceTop + (Qwixx.size50.height * 2) + (diceGapLittle * 1) + (diceGapBig * 1),
                Qwixx.size50.width,
                Qwixx.size50.height
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
        add(blue2);
        

        setVisible(true);
    }


    public void update() 
    {
        
        setBackground(Qwixx.myback1);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setLayout(null);

        // setSize(400, 390);

        title.setText(game.current.name + "'s ROLL");
        
        
        white1.update(dice.white1.val);
        white2.update(dice.white2.val);
        
        red.update(dice.red.val);
        if (game.redslocked)
        {
            red.lock();
        }
        else
        {
            red.unlock();
        }
        
        yellow.update(dice.yellow.val);
        if (game.yellowslocked)
        {
            yellow.lock();
        }
        else
        {
            yellow.unlock();
        }

        green.update(dice.green.val);
        if (game.greenslocked)
        {
            green.lock();
        }
        else
        {
            green.unlock();
        }
        
        blue.update(dice.blue.val);
        if (game.blueslocked)
        {
            blue.lock();
        }
        else
        {
            blue.unlock();
        }
 
        whitesred.update(white1.die.val + white2.die.val);
        if (game.redslocked)
        {
            whitesred.lock();
        }
        else
        {
            whitesred.unlock();
        }

        whitesyellow.update(white1.die.val + white2.die.val);
        if (game.yellowslocked)
        {
            whitesyellow.lock();
        }
        else
        {
            whitesyellow.unlock();
        }

        whitesgreen.update(white1.die.val + white2.die.val);
        if (game.greenslocked)
        {
            whitesgreen.lock();
        }
        else
        {
            whitesgreen.unlock();
        }
        
        whitesblue.update(white1.die.val + white2.die.val);
        if (game.blueslocked)
        {
            whitesblue.lock();
        }
        else
        {
            whitesblue.unlock();
        }
        
        red1.update(white1.die.val + red.die.val);
        if (game.redslocked)
        {
            red1.lock();
        }
        else
        {
            red1.unlock();
        }
        

        red2.update(white2.die.val + red.die.val);
        if (game.redslocked)
        {
            red2.lock();
        }
        else
        {
            red2.unlock();
        }

        yellow1.update(white1.die.val + yellow.die.val);
        if (game.yellowslocked)
        {
            yellow1.lock();
        }
        else
        {
            yellow1.unlock();
        }
        

        yellow2.update(white2.die.val + yellow.die.val);
        if (game.yellowslocked)
        {
            yellow2.lock();
        }
        else
        {
            yellow2.unlock();
        }

        green1.update(white1.die.val + green.die.val);
        if (game.greenslocked)
        {
            green1.lock();
        }
        else
        {
            green1.unlock();
        }

        green2.update(white2.die.val + green.die.val);
        if (game.greenslocked)
        {
            green2.lock();
        }
        else
        {
            green2.unlock();
        }
        
        blue1.update(white1.die.val + blue.die.val);
        if (game.blueslocked)
        {
            blue1.lock();
        }
        else
        {
            blue1.unlock();
        }

        blue2.update(white2.die.val + blue.die.val);
        if (game.blueslocked)
        {
            blue2.lock();
        }
        else
        {
            blue2.unlock();
        }

        invalidate();
        setVisible(true);
    }

    
}
