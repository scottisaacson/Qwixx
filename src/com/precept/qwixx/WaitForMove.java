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


public class WaitForMove
{
    SheetEntry se;
    Player player;
    Qwixx.MOVETYPE type;
    Game game;
    WhichTurn.TYPE turnType;
    
    public WaitForMove(TurnMove m)
    {
        this.player = m.player;
        this.type = m.type;
        if (this.type == Qwixx.MOVETYPE.WHITES || type == Qwixx.MOVETYPE.WHITES_CONSIDERING_COLORS) turnType = WhichTurn.TYPE.WHITE;
        if (this.type == Qwixx.MOVETYPE.COLORS) turnType = WhichTurn.TYPE.COLOR;
        this.game = player.game;
        this.se = null;
    }
    
    public void waitForMove()  
    { 

        
        if (game.debug) System.out.println("waitforMove: sleeping...");
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
                    if (game.table.pcm.isselected) done = true;
                    if (game.table.pcm.isskip) done = true;
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
                    if (game.debug) System.out.println("WaitForMove.done: ... waking up, done: " + player.name + " " + type);
                    
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
                    
                    if (!game.table.isquit)
                    {
                        if (type == Qwixx.MOVETYPE.WHITES)
                        {
                            if (game.table.pcm.isselected)
                            {
                                SheetEntry selected = game.table.pcm.thisOne;
                                selected.markIt();
                            }
                        }
                        else if (type == Qwixx.MOVETYPE.WHITES_CONSIDERING_COLORS)
                        {
                            if (game.table.pcm.isselected)
                            {
                                SheetEntry selected = game.table.pcm.thisOne;
                                selected.markIt();
                            }
                        }
                        else // if (type == Qwixx.MOVETYPE.COLORS)
                        {
                            if (game.table.pcm.isselected)
                            {
                                SheetEntry selected = game.table.pcm.thisOne;
                                selected.markIt();
                            }
                            else
                            {
                                player.sheet.penalties++;
                            }
                        }
                        
                        player.score();
                        
                        game.table.ps.update();

                        /*
                        if (game.interactive == true)
                        {
                            // show human move
                            ShowMoveWait smw = new ShowMoveWait(player, turnType, game.table.pcm.thisOne);
                            smw.showMoveWait();
                        }
                        */  
                        
                    }
                    
                    /* */
                    if (game.interactive == true)
                    {
                        // Do not show human move
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
                                if (game.debug) System.out.println("WaitForMove.done: game over: LOCKED");
                            }
                            if (game.gameover == Game.GAMEOVER.QUIT)
                            {
                                if (game.debug) System.out.println("WaitForMove.done: game over: QUIT");
                            }
                            if (game.gameover == Game.GAMEOVER.PENALTIES)
                            {
                                if (game.debug) System.out.println("WaitForMove.done: game over: PENALTIES");
                            }

                            if (game.table != null) game.table.dispose();
                            game.endGame();
                        }
                        else
                        {
                            if (game.debug) System.out.println("WaitForMove.done: start the next move");
                            game.takeMove();
                        }
                    }
                    /*  */
                    
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
