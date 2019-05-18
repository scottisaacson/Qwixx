package qwixx;


public class Dice {
    
    Game game;
    Die white1;
    Die white2;
    Die red;
    Die yellow;
    Die green;
    Die blue;
    Sum whites;
    Sum red1;
    Sum red2;
    Sum yellow1;
    Sum yellow2;
    Sum green1;
    Sum green2;
    Sum blue1;
    Sum blue2;
    
    public Dice (Game game)
    {
        this.game = game;
        white1 = new Die();
        white2 = new Die();
        blue = new Die();
        green = new Die();
        red = new Die();
        yellow = new Die();
        whites = null;
        red1 = null;
        red2 = null;
        yellow1 = null;
        yellow2 = null;
        green1 = null;
        green2 = null;
        blue1 = null;
        blue2 = null;
    }
    
    public void print()
    {

        StringBuffer out1 = new StringBuffer();
        out1.append("White1: " + (white1.val) + ", White2: " + (white2.val) +
                ", Red: " + red.val + ", Yellow: " + yellow.val +
                ", Green: " + green.val + ", Blue: " + blue.val);
        System.out.println(out1.toString());

        out1 = new StringBuffer();
        out1.append("Whites: " + (whites.val) + ", ");
        if (!game.redslocked) out1.append("Reds: " + (red1.val) + " OR " + (red2.val) + ", ");
        if (!game.yellowslocked) out1.append("Yellows: " + (yellow1.val) + " OR "+  (yellow2.val) + ", ");
        if (!game.greenslocked) out1.append("Greens: " + (green1.val) + " OR "+  (green2.val) + ", " );
        if (!game.blueslocked) out1.append("Blues: " + (blue1.val) + " OR "+  (blue2.val) );
        
        System.out.println(out1.toString());
        
    }
    
    public void roll()
    {
        
        white1.roll();
        white2.roll();
        whites = new Sum(Sum.COLOR.WHITE, white1.val + white2.val);
        if (!game.redslocked) 
        {
            red.roll();
            red1 = new Sum(Sum.COLOR.RED, white1.val + red.val);
            red2 = new Sum(Sum.COLOR.RED, white2.val + red.val);
        }
        else
        {
            red1 = null;
            red2 = null;
        }
        if (!game.yellowslocked) 
        {
            yellow.roll();
            yellow1 = new Sum(Sum.COLOR.YELLOW, white1.val + yellow.val);
            yellow2 = new Sum(Sum.COLOR.YELLOW, white2.val + yellow.val);
        }
        else
        {
            yellow1 = null;
            yellow2 = null;
        }
        if (!game.greenslocked) 
        {
            green.roll();
            green1 = new Sum(Sum.COLOR.GREEN, white1.val + green.val);
            green2 = new Sum(Sum.COLOR.GREEN, white2.val + green.val);
        }
        else
        {
            green1 = null;
            green2 = null;
        }
        if (!game.blueslocked) 
        {
            blue.roll();
            blue1 = new Sum(Sum.COLOR.BLUE, white1.val + blue.val);
            blue2 = new Sum(Sum.COLOR.BLUE, white2.val + blue.val);
        }
        else
        {
            blue1 = null;
            blue2 = null;
        }
    }
    
}

