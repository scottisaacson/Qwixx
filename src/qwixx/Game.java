package qwixx;

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
    public boolean interactive;

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
    RolledDice rd = null;
    PlayersSummary ps = null;
    SetupDialog  sd = null;
    
    public Game(JFrame frame) {

        this.frame = frame;
        
        redslocked = false;
        yellowslocked = false;
        greenslocked = false;
        blueslocked = false;
        sd = null;
        
        gameover = GAMEOVER.NO;

        dice = new Dice(this);
        
        players = Game.defaultPlayers(this);
        
        setFirstPlayer();

        
    }

    
    public Game(Game game) {

        this.frame = game.frame;
        
        redslocked = false;
        yellowslocked = false;
        greenslocked = false;
        blueslocked = false;
        sd = null;
        
        gameover = GAMEOVER.NO;

        dice = new Dice(this);
        
        if (game.players == null || game.players.size() == 0)
        {
            players = Game.defaultPlayers(this);
        }
        else
        {
            players = new ArrayList<Player>();
            for (Player p : game.players)
            {
                Player newp = new Player(this, p.name, p.strategy);
                players.add(newp);
            }
        }
        
        setFirstPlayer();

        
    }
    
    public void roll()
    {
        dice.roll();
    }
    
    public void setFirstPlayer()
    {

        interactive = false;
        
        for (Player p : players)
        {
            if (p.strategy == Player.STRATEGY.Human)
            {
                interactive = true;
            }
        }
        
        if (players.size() < 1)
        {
            return;
        }
        
        Random rand = new Random();
        cpi = rand.nextInt(players.size());
        current = players.get(cpi);
    }

    public void setNextPlayer()
    {
        
        // set up for the next turn
        cpi++;
        if (cpi == players.size())
        {
            cpi = 0;
        }
        current = players.get(cpi);
    }

    
    public void playGame()
    {
        
        /*
        for (Player p : players)
        {
            SheetEntry se = p.sheet.findEntry(p.sheet.reds, 11);
            se.marked = true;
            se = p.sheet.findEntry(p.sheet.yellows, 11);
            se.marked = true;
            se = p.sheet.findEntry(p.sheet.greens, 3);
            se.marked = true;
            se = p.sheet.findEntry(p.sheet.blues, 3);
            se.marked = true;
        }
        */
        
        while (gameover == GAMEOVER.NO)
        {
            takeTurn(false);
            checkForGameOver();
        }
    }


    
    public void takeTurn(boolean show)
    {


        if (interactive)
        {
            RollDice rollDice = new RollDice(this);
            rollDice.buildAndShow();
        }
        
        dice.roll();
        
        if (interactive)
        {
            rd = new RolledDice(this);
            rd.buildAndShow();

            ps = new PlayersSummary(this);
            ps.buildAndShow();
        }
        
        
        // dice.print();
        
        for (Player p : players)
        {
            if (p != current)
            {
                p.playWhites();
                p.score();
                if (gameover == GAMEOVER.QUIT)
                {
                    break;
                }
            }
        }

        if (gameover != GAMEOVER.QUIT)
        {
            current.playWhitesConsideringColors();
            current.score();
        }
        
        if (gameover != GAMEOVER.QUIT)
        {
            current.playColors();
            current.score();
        }
        
        checkForNewLock();
        
        if (interactive)
        {
            if (rd != null)
            {
                rd.dispose();
            }

            if (ps != null)
            {
                ps.dispose();
            }
        }

        setNextPlayer();
        
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
    
    /*
    public static Game newGame(JFrame frame)
    {
        Game game = new Game(frame);
        
        if ()
        game.players = Game.defaultPlayers(game);
        return game;
    }
    */
    
    /*
    public static Game newGame(JFrame frame, ArrayList<Player> players)
    {
        Game game = new Game(frame);
        game.players.clear();
        for (Player p : players)
        {
            Player newp = new Player(game, p.name, p.strategy);
            game.players.add(newp);
        }
        
        return game;
    }
    */

    public static ArrayList<Player> defaultPlayers(Game game)
    {
        
        ArrayList<Player> players = new ArrayList<Player>();
    
        Player p = null;
        
        p = new Player(game, "Player 1", Player.STRATEGY.Human);
        players.add(p);
        p = new Player(game, "Player 2", Player.STRATEGY.Computer);
        players.add(p);
        
        return players;
    }    
    
    
    
}

