/****************************************************************************
 * 2019 The Precept Group, LLC. 
 * See LICENSE for license information.
 ***************************************************************************/

package com.precept.qwixx;

import java.util.*;
import java.lang.*;
import java.io.*;
import java.net.*;
import java.text.*;


public class TurnMove {
    
    Player player;
    Qwixx.MOVETYPE type;
    Player whoRolled;
    
    public TurnMove (Player p, Qwixx.MOVETYPE t, Player w)
    {
        this.player = p;
        this.type = t;
        this.whoRolled = w;

    }
    
}

