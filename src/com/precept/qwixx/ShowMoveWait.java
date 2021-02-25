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


public class ShowMoveWait
{
    SheetEntry se;
    Player player;
    WhichTurn.TYPE type;
    Game game;
    Player whoRolled;
    
    public ShowMoveWait(Player p, WhichTurn.TYPE t, SheetEntry se, Player w)
    {
        this.player = p;
        this.type = t;
        this.game = player.game;
        this.se = se;
        this.whoRolled = w;
    }
    
    public void showMoveWait()  
    { 
            
        String turnString = null;
        if (type == WhichTurn.TYPE.COLOR) turnString = "COLORS";
        if (type == WhichTurn.TYPE.WHITE) turnString = "WHITES";
        
        // Now show the move

        if (game.table.ss != null)
        {
            if (game.debug) System.out.println("showMoveWait: hiding ShowSheet");
            game.table.ss.setVisible(false);
        }
        if (game.table.sm != null)
        {
            if (game.debug) System.out.println("showMoveWait: hiding ShowMove");
            game.table.sm.setVisible(false);
        }

        if (game.debug) System.out.println("showMoveWait: creating new ShowMove " + turnString);

        game.table.sm = new ShowMove(player, type, se, whoRolled);
        game.table.sm.build();

        int top = 200;
        int left = 530;
        int showWidth = 800;
        int showHeight = 420;

        game.table.sm.setBounds(left, top, showWidth, showHeight);
        game.table.sm.setVisible(true);
        if (game.debug) System.out.println("showMoveWait: adding new ShowMove " + turnString);
        game.table.add(game.table.sm);

        if (game.debug) System.out.println("showMoveWait: sleeping...");
        startThread();  
            
    }
    
    
    private void startThread()  
    { 

        SwingWorker sw1 = new SwingWorker<String, Integer>()
        { 

            @Override
            protected String doInBackground() throws Exception  
            { 
                boolean done = false;
                while (!done)  
                { 
                    if (game.interactive == true)
                    {
                        //manual next
                        Thread.sleep(100);
                        if (game.table.sm.isok) done = true;
                        if (game.table.isquit) done = true;
                    }
                    else
                    {

                        // auto next
                        Thread.sleep(game.interactiveWaitTime);
                        done = true;
                    }

                } 

                String res = "Done"; 
                return res; 
            } 

            @Override
            protected void process(java.util.List<Integer> chunks) 
            { 
                //
            } 

            @Override
            protected void done()  
            { 
                try 
                {
                    
                    if (game.debug) System.out.println("ShowMoveWait.done: ... waking up, done: " + player.name + " " + type);
                    
                    if (game.table.sm != null)
                    {
                        game.table.sm.setVisible(false);
                    }
                    if (game.table.ss != null)
                    {
                        game.table.ss.setVisible(false);
                    }
                    if (game.table.pcm != null)
                    {
                        game.table.pcm.setVisible(false);
                    }
                    
                    game.table.sm.isok = false;
                    game.table.isOK = false;
                    
                    game.checkForNewLock();
                    game.checkForGameOver();

                    if (game.gameover != Game.GAMEOVER.NO)
                    {
                        if (game.gameover == Game.GAMEOVER.LOCKED)
                        {
                            if (game.debug) System.out.println("ShowMoveWait.done: game over: LOCKED");
                        }
                        if (game.gameover == Game.GAMEOVER.QUIT)
                        {
                            if (game.debug) System.out.println("ShowMoveWait.done: game over: QUIT");
                        }
                        if (game.gameover == Game.GAMEOVER.PENALTIES)
                        {
                            if (game.debug) System.out.println("ShowMoveWait.done.done: game over: PENALTIES");
                        }

                        if (game.table != null) game.table.dispose();
                        game.endGame();
                    }
                    else
                    {
                        if (game.debug) System.out.println("ShowMoveWait.done: start the next move");
                        game.takeMove();
                    }
                }  
                catch (Exception e)  
                { 
                    e.printStackTrace(); 
                    System.exit(-5);
                }  
            } 
        }; 

        // executes the swingworker on worker thread 
        sw1.execute();  
    } 

}
