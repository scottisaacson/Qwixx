/**********************************************************
 *  Copyright 2019, The Precept Group, LLC
 *  See the LICENSE file for license information.
 *
 *********************************************************/


package com.precept.qwixx;

import java.util.*;
import java.lang.*;
import java.io.*;
import java.net.*;
import java.text.*;
import javax.swing.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
    
public class Game {
    

    ArrayList<Player> players;
    Player current;
    int cpi;
    // public boolean interactive;
    ArrayList<TurnMove> orderedPlayers;

    boolean redslocked;
    boolean yellowslocked;
    boolean greenslocked;
    boolean blueslocked;
    
    enum GAMEOVER {
        QUIT,
        PENALTIES,
        LOCKED,
        NO
    }
    GAMEOVER gameover;

    enum COLORS {
        RED,
        YELLOW,
        GREEN,
        BLUE,
        WHITE
    };


    Dice dice;
    
    JFrame frame;
    SetupDialog sd;

    Table table = null;
    
    public Game(JFrame frame, ArrayList<Player> players) {

        this.frame = frame;
        this.sd = null;
        
        cpi = -1;
        
        redslocked = false;
        yellowslocked = false;
        greenslocked = false;
        blueslocked = false;
        
        gameover = GAMEOVER.NO;

        dice = new Dice(this);
        
        if (players == null || players.size() == 0)
        {
            this.players = Game.defaultPlayers(this);
        }
        else
        {
            this.players = new ArrayList<Player>();
            for (Player p : players)
            {
                Player newp = new Player(this, p.name, p.strategy);
                this.players.add(newp);
            }
        }

        orderedPlayers = null;
        /*
        setNextPlayer();
        orderedPlayers = new ArrayList<TurnMove>();
        */
        
    }

    
    public Game(Game game) {

        this(game.frame, game.players);
    }
    
    public void roll()
    {
        dice.roll();
    }
    
    public void setNextPlayer()
    {
        if (players.size() < 1)
        {
            System.exit(-6);
        }

        if (cpi < 0)
        {
            Random rand = new Random();
            cpi = rand.nextInt(players.size());
        }
        else
        {
            cpi++;
            if (cpi == players.size())
            {
                cpi = 0;
            }
        }
        current = players.get(cpi);
    }

    
    public void playGame()
    {
        
        table = new Table(this);
        table.buildAndShow();
        
        takeMove();
    
    }
    
    public void takeMove()
    {
        System.out.println("takeMove: Entering takeMove...");
        if (this.gameover == GAMEOVER.NO)
        {
            // any more players for this turn?
            if (orderedPlayers == null || orderedPlayers.size() == 0)
            {
                System.out.println("takeMove: no more moves on this roll");
                // no more players for this turn, start a new turn
                setNextPlayer();
                System.out.println("takeMove: new current = " + current.name);
                dice.roll();
                table.rd.newDice();
                System.out.println("takeMove: new dice");
                
                orderedPlayers = getOrderedPlayers();
            }

            if (orderedPlayers == null || orderedPlayers.size() == 0)
            {
                if (table != null) table.dispose();
                System.out.println("ERROR: no players");
                System.exit(-20);
            }
            
            TurnMove m = orderedPlayers.remove(0);
            System.out.println("takeMove: working on " + m.player.name + " " + m.type);
            
            // SETUP

            WhichTurn.TYPE turnType = null;
            String turnString = null;
            if (m.type == Qwixx.MOVETYPE.COLORS)
            {
                turnType = WhichTurn.TYPE.COLOR;
                turnString = "COLORS";
            }
            else if (m.type == Qwixx.MOVETYPE.WHITES)
            {
                turnType = WhichTurn.TYPE.WHITE;
                turnString = "WHITES";
            }
            else if (m.type == Qwixx.MOVETYPE.WHITES_CONSIDERING_COLORS)
            {
                turnType = WhichTurn.TYPE.WHITE;
                turnString = "WHITES CONSIDERING COLORS";
            }
            else
            {
                System.out.println("takeMove: bad type");
                System.exit(-10);
            }

            // SET THE PLAYER TURN STATUS
            table.ts.setPlayerTurn(m.player, turnType);
            
            if (m.player.strategy == Player.STRATEGY.Computer)
            {
                SheetEntry se = null;
                
                // GET AND PLAY THE MOVE
                if (m.type == Qwixx.MOVETYPE.COLORS)
                {
                    se = m.player.playColors();
                }
                else if (m.type == Qwixx.MOVETYPE.WHITES)
                {
                    se = m.player.playWhites();
                }
                if (m.type == Qwixx.MOVETYPE.WHITES_CONSIDERING_COLORS)
                {
                    se = m.player.playWhitesConsideringColors();
                }
            
                StringBuffer sb = new StringBuffer();
                if (se == null)
                {
                    sb.append("<null>");
                }
                else
                {
                    sb.append("" + se.color + " " + se.val);
                }
                System.out.println("takeMove: player " + m.player.name + " " + turnString + " chose: " + sb.toString());

                table.ps.update();

                ShowMoveWait smw = new ShowMoveWait(m.player, turnType, se);
                smw.showMoveWait();
            }
            else
            {
                
                SheetEntry thisOne = null;
                ArrayList<Move> whiteMoves = null;                
                ArrayList<Move> colorMoves = null;                

                if (m.type == Qwixx.MOVETYPE.WHITES)
                {
                    m.player.findWhiteMoves();
                    whiteMoves = m.player.whiteMoves;
                    colorMoves = null;
                }
                else if (m.type == Qwixx.MOVETYPE.WHITES_CONSIDERING_COLORS)
                {
                    m.player.findWhiteMoves();
                    m.player.findColorMoves();
                    whiteMoves = m.player.whiteMoves;
                    colorMoves = m.player.colorMoves;
                }
                else // if (m.type == Qwixx.MOVETYPE.COLORS)
                {
                    m.player.findColorMoves();
                    colorMoves = m.player.colorMoves;
                    whiteMoves = null;
                }

                int top = 200;
                int left = 530;

                int pcmWidth = 800;
                int pcmHeight = 400;

                table.pcm = new PlayerChooseMove(m.player, m.type, whiteMoves, colorMoves);
                table.pcm.build();
                table.pcm.setBounds(left, top, pcmWidth, pcmHeight);
                table.pcm.setVisible(true);
                table.add(table.pcm);

                WaitForMove wfm = new WaitForMove(m);
                wfm.waitForMove();
                
                /*
                if (m.type == Qwixx.MOVETYPE.WHITES)
                {
                    m.player.findWhiteMoves();
                    thisOne = m.player.chooseWhiteHuman();
                    if (thisOne == null)
                    {
                    }
                    else
                    {
                        thisOne.markIt();
                    }
                }
                else if (m.type == Qwixx.MOVETYPE.WHITES_CONSIDERING_COLORS)
                {
                    m.player.findWhiteMoves();
                    m.player.findColorMoves();
                    thisOne = m.player.chooseWhiteConsideringColorsHuman();
                    if (thisOne == null)
                    {
                    }
                    else
                    {
                        thisOne.markIt();
                    }
                }
                else // if (m.type == Qwixx.MOVETYPE.COLORS)
                {
                    m.player.findColorMoves();
                    thisOne = m.player.chooseColorHuman();
                    if (thisOne == null)
                    {
                        m.player.sheet.penalties++;
                    }
                    else
                    {
                        thisOne.markIt();
                    }
                }
                m.player.score();
                */

            }
            
        }
        else
        {
            
        }
    }
    
    public void checkForNewLock()
    {
        for (Player p : players)
        {
            if (redslocked == false)
            {
                if (p.shouldLockReds())
                {
                    redslocked = true;
                }
            }

            if (yellowslocked == false)
            {
                if (p.shouldLockYellows())
                {
                    yellowslocked = true;
                }
            }

            if (greenslocked == false)
            {
                if (p.shouldLockGreens())
                {
                    greenslocked = true;
                }
            }

            if (blueslocked == false)
            {
                if (p.shouldLockBlues())
                {
                    redslocked = true;
                }
            }
            
        }
        
        
    }
    
    public void checkForGameOver()
    {
        if (table.isquit == true)
        {
            gameover = GAMEOVER.QUIT;
            return;
        }
        
        if (gameover == GAMEOVER.NO)
        {
            
            // two colors are locked
            int countLocked = 0;
            if (redslocked == true)
            {
                countLocked++;
            }
            if (yellowslocked == true)
            {
                countLocked++;
            }
            if (greenslocked == true)
            {
                countLocked++;
            }
            if (blueslocked == true)
            {
                countLocked++;
            }
            if (countLocked >= 2)
            {

                gameover = GAMEOVER.LOCKED;
            }

            // one person reaches 4 penalties
            for (Player p : players)
            {
                Sheet s = p.sheet;
                if (s.penalties >= 4)
                {
                    gameover = GAMEOVER.PENALTIES;
                }
            }
        }
        
    }
    
    public boolean isOver()
    {
        if (gameover == GAMEOVER.NO)
        {
            return false;
        }

        return true;
    }
    
    public Result getResult()
    {

        Result r = new Result(players);
        return r;
    }
    
    public void endGame()
    {

        if (table != null) table.dispose();

        if (gameover == GAMEOVER.QUIT) return;
        
        ResultDialog rd = new ResultDialog(this);
        rd.buildAndShow();
    }
    

    public static ArrayList<Player> defaultPlayers(Game game)
    {
        
        ArrayList<Player> players = new ArrayList<Player>();
    
        Player p = null;

        p = new Player(game, "Scott", Player.STRATEGY.Human);
        players.add(p);
        p = new Player(game, "Nina", Player.STRATEGY.Computer);
        players.add(p);
        
        /*
        p = new Player(game, "Scott", Player.STRATEGY.Human);
        players.add(p);
        p = new Player(game, "Nina", Player.STRATEGY.Computer);
        players.add(p);
        p = new Player(game, "Sam", Player.STRATEGY.Computer);
        players.add(p);
        */
        
        /*
        p = new Player(game, "Scott", Player.STRATEGY.Computer);
        players.add(p);
        p = new Player(game, "Nina", Player.STRATEGY.Computer);
        players.add(p);
        p = new Player(game, "Sam", Player.STRATEGY.Computer);
        players.add(p);
        p = new Player(game, "Hunter", Player.STRATEGY.Computer);
        players.add(p);
        */
        
        return players;
    }    
    
    public ArrayList<TurnMove> getOrderedPlayers()
    {

        TurnMove m = null;
        ArrayList<TurnMove> ret = new ArrayList<TurnMove>();
        
        int count = 0;
        int size = players.size();
        int index = cpi + 1;
        if (index == size) index = 0;
        
        while (count < size)
        {
            Player p = players.get(index);
            if (p.equals(current))
            {
                m = new TurnMove(p, Qwixx.MOVETYPE.WHITES_CONSIDERING_COLORS);
                ret.add(m);
                System.out.println("getOrderedPlayers: adding " + p.name + " " + "WHITES_CONSIDEING_COLORS");
                m = new TurnMove(p, Qwixx.MOVETYPE.COLORS);
                ret.add(m);
                System.out.println("getOrderedPlayers: adding " + p.name + " " + "COLORS");
            }
            else
            {
                m = new TurnMove(p, Qwixx.MOVETYPE.WHITES);
                ret.add(m);
                System.out.println("getOrderedPlayers: adding " + p.name + " " + "WHITES");
                
            }
            index++;
            count++;
            if (index == size) index = 0;
        }
        
        return ret;
    }
    
}

