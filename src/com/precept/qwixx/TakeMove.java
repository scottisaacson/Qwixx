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


public class TakeMove
{
    SheetEntry se;
    Player player;
    Qwixx.MOVETYPE type;
    Game game;
    
    public TakeMove(TurnMove m)
    {
        this.player = m.player;
        this.type = m.type;
        this.game = player.game;
        this.se = null;
    }
    
    public void takeMove()  
    { 
        
        if (type == Qwixx.MOVETYPE.COLORS)
        {
            game.table.ts.setPlayerTurn(player, WhichTurn.TYPE.COLOR);
            
            se = player.playColors();
            
            StringBuffer sb = new StringBuffer();
            if (se == null)
            {
                sb.append("<null>");
            }
            else
            {
                sb.append("" + se.color + " " + se.val);
            }
            System.out.println("TakeMove.takeMove: player " + player.name + " COLORS chose: " + sb.toString());

            game.table.ps.update();

        }
        else if (type == Qwixx.MOVETYPE.WHITES)
        {
            game.table.ts.setPlayerTurn(player, WhichTurn.TYPE.WHITE);
            
            se = player.playWhites();
            
            StringBuffer sb = new StringBuffer();
            if (se == null)
            {
                sb.append("<null>");
            }
            else
            {
                sb.append("" + se.color + " " + se.val);
            }
            System.out.println("TakeMove.takeMove: player " + player.name + " WHITES chose: " + sb.toString());

            game.table.ps.update();
            
        }
        else if (type == Qwixx.MOVETYPE.WHITES_CONSIDERING_COLORS)
        {
            game.table.ts.setPlayerTurn(player, WhichTurn.TYPE.WHITE);
            
            se = player.playWhitesConsideringColors();
            
            StringBuffer sb = new StringBuffer();
            if (se == null)
            {
                sb.append("<null>");
            }
            else
            {
                sb.append("" + se.color + " " + se.val);
            }
            System.out.println("TakeMove.takeMove: player " + player.name + " WHITES_CONSIDERING_COLORS chose: " + sb.toString());

            game.table.ps.update();
        }
        else
        {
            System.out.println("TakeMove.takeMove: bad type");
            System.exit(-10);
        }

        if (game.table.ss != null)
        {
            System.out.println("TakeMove.takeMove: hiding ShowSheet");
            game.table.ss.setVisible(false);
        }
        if (game.table.sm != null)
        {
            System.out.println("TakeMove.takeMove: hiding ShowMove");
            game.table.sm.setVisible(false);
        }

        if (type == Qwixx.MOVETYPE.COLORS)
        {
            System.out.println("TakeMove.takeMove: creating new ShowMove COLORS");
            game.table.sm = new ShowMove(player, Qwixx.MOVETYPE.COLORS, se);
            game.table.sm.build();
    
            int top = 100;
            int left = 500;
            int showWidth = 800;
            int showHeight = 300;

            game.table.sm.setBounds(left, top, showWidth, showHeight);
            game.table.sm.setVisible(true);
            System.out.println("TakeMove.takeMove: adding new ShowMove COLORS");
            game.table.add(game.table.sm);
        }
        else
        {
            
            System.out.println("TakeMove.takeMove: creating new ShowMove WHITES");
            game.table.sm = new ShowMove(player, Qwixx.MOVETYPE.WHITES, se);
            game.table.sm.build();
    
            int top = 100;
            int left = 500;
            int showWidth = 800;
            int showHeight = 300;

            game.table.sm.setBounds(left, top, showWidth, showHeight);
            game.table.sm.setVisible(true);
            System.out.println("TakeMove.takeMove: adding ShowMove WHITES");
            game.table.add(game.table.sm);
            
        }
                    
        System.out.println("TakeMove.takeMove: sleeping...");
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
                    Thread.sleep(500); 
                    done = true;
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
                    
                    System.out.println("TakeMove.done: ... waking up, done: " + player.name + " " + type);
                    
                    if (game.table.sm != null)
                    {
                        game.table.sm.setVisible(false);
                    }
                    if (game.table.ss != null)
                    {
                        game.table.ss.setVisible(false);
                    }
                    
                    
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
                        System.out.println("TakeMove.done: start the next move");
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
