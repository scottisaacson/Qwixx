package com.precept.qwixx;

import java.util.*;
import java.lang.*;
import java.io.*;
import java.net.*;
import java.text.*;

    

public class Result {
    
    ArrayList<Player> players;
    ArrayList<Player> winners;
    int winnerscore;
    
    public Result (ArrayList<Player> ps)
    {
        players = new ArrayList<Player>();
        for (Player p : ps)
        {
            p.score();
            players.add(p);
        }
        
        winners = new ArrayList<Player> ();
        
        Player maxPlayer = null;

        while (players.size() > 0)
        {

            maxPlayer = null;
            for (Player p : players)
            {
                if (maxPlayer == null)
                {
                    maxPlayer = p;
                }
                else if (p.sheet.score >= maxPlayer.sheet.score)
                {
                    maxPlayer = p;
                }
                else
                {
                    //
                }
            }
            
            winners.add(maxPlayer);
            this.players.remove(maxPlayer);
        }
        
    }
    
    public void print()
    {
                
        StringBuffer sb = new StringBuffer();

        boolean first = true;
        while (winners.size() > 0)
        {
            if (!first)
            {
                sb.append(", ");
            }
            Player p = winners.remove(0);
            sb.append(p.name);
            first = false;
        }
        System.out.println(sb.toString());

    }

    
}

