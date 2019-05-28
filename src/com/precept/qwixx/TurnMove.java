package com.precept.qwixx;

import java.util.*;
import java.lang.*;
import java.io.*;
import java.net.*;
import java.text.*;


public class TurnMove {
    
    Player player;
    Qwixx.MOVETYPE type;
    
    public TurnMove (Player p, Qwixx.MOVETYPE t)
    {
        this.player = p;
        this.type = t;
    }
    
}

