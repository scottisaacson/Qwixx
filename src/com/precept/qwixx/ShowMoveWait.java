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
    
    public ShowMoveWait(Player p, WhichTurn.TYPE t, SheetEntry se)
    {
        this.player = p;
        this.type = t;
        this.game = player.game;
        this.se = se;
    }
    
    public void showMoveWait()  
    { 
            
        String turnString = null;
        if (type == WhichTurn.TYPE.COLOR) turnString = "COLORS";
        if (type == WhichTurn.TYPE.WHITE) turnString = "WHITES";
        
        // Now show the move

        if (game.table.ss != null)
        {
            System.out.println("showMoveWait: hiding ShowSheet");
            game.table.ss.setVisible(false);
        }
        if (game.table.sm != null)
        {
            System.out.println("showMoveWait: hiding ShowMove");
            game.table.sm.setVisible(false);
        }

        System.out.println("showMoveWait: creating new ShowMove " + turnString);

        game.table.sm = new ShowMove(player, type, se);
        game.table.sm.build();

        int top = 200;
        int left = 530;
        int showWidth = 800;
        int showHeight = 400;

        game.table.sm.setBounds(left, top, showWidth, showHeight);
        game.table.sm.setVisible(true);
        System.out.println("showMoveWait: adding new ShowMove " + turnString);
        game.table.add(game.table.sm);

        System.out.println("showMoveWait: sleeping...");
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
                    Thread.sleep(100); 
                    if (game.table.isOK) done = true;
                    if (game.table.isquit) done = true;
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
                    
                    System.out.println("showMoveWait.done: ... waking up, done: " + player.name + " " + type);
                    
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
                    
                    game.table.isOK = false;
                    
                    game.checkForNewLock();
                    game.checkForGameOver();

                    if (game.gameover != Game.GAMEOVER.NO)
                    {
                        if (game.gameover == Game.GAMEOVER.LOCKED)
                        {
                            System.out.println("TakeMove.done: game over: LOCKED");
                        }
                        if (game.gameover == Game.GAMEOVER.QUIT)
                        {
                            System.out.println("TakeMove.done: game over: QUIT");
                        }
                        if (game.gameover == Game.GAMEOVER.PENALTIES)
                        {
                            System.out.println("TakeMove.done: game over: PENALTIES");
                        }

                        if (game.table != null) game.table.dispose();
                        game.endGame();
                    }
                    else
                    {
                        System.out.println("showMoveWait.done: start the next move");
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
