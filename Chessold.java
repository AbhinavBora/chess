import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.applet.Applet;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class Chessold extends Applet implements ActionListener
{
    Panel p_screen; //to hold all of the screens
    Panel Title, screen2, screen3, screen4, screen5; //the two screens
    CardLayout cdLayout = new CardLayout ();
    int row = 8, col = 8;
    JButton board[] = new JButton [row * col];
    int boardpieces[] [] = {{412, 311, 212, 511, 612, 211, 312, 411}, {111, 112, 111, 112, 111, 112, 111, 112}, {002, 001, 002, 001, 002, 001, 002, 001}, {001, 002, 001, 002, 001, 002, 001, 002}, {002, 001, 002, 001, 002, 001, 002, 001}, {001, 002, 001, 002, 001, 002, 001, 002}, {122, 121, 122, 121, 122, 121, 122, 121}, {421, 322, 221, 522, 621, 222, 321, 422}};
    int last = -1, current;
    int num1 = 0, num2 = 0, third1 = 0, toadd = 0, swap1 = 0, num3 = 0, num4 = 0, third2 = 0, toadd2 = 0, swap2 = 0;

    public void init ()
    {
	p_screen = new Panel ();
	p_screen.setLayout (cdLayout);
	Title ();
	screen2 ();
	resize (800, 600);
	setLayout (new BorderLayout ());
	add ("Center", p_screen);


    }


    public void Title ()
    { //screen 1 is set up.
	Title = new Panel ();
	Title.setBackground (Color.white);
	chessboard (Title);
	p_screen.add ("1", Title);
    }


    public void screen2 ()
    {
	screen2 = new Panel ();
	screen2.setBackground (Color.white);
	p_screen.add ("2", screen2);
    }


    public void chessboard (Panel screen)
    {
	JPanel p = new JPanel (new GridLayout (8, 8));
	int a = 0;
	for (int i = 0 ; i < row ; i++)
	{
	    for (int j = 0 ; j < col ; j++)
	    {
		board [a] = new JButton (createImageIcon (whichimage (boardpieces [i] [j]) + ".jpg"));
		board [a].addActionListener (this);
		board [a].setActionCommand ("" + a);
		board [a].setPreferredSize (new Dimension (75, 75));
		p.add (board [a]);
		a++;
	    }
	}
	Border raisedbevel = BorderFactory.createRaisedBevelBorder ();
	Border loweredbevel = BorderFactory.createLoweredBevelBorder ();
	Border compound;

	compound = BorderFactory.createCompoundBorder (
		raisedbevel, loweredbevel);
	Border blackline = BorderFactory.createLineBorder (Color.black);
	compound = BorderFactory.createCompoundBorder (
		blackline, compound);
	Border empty = BorderFactory.createEmptyBorder (5, 5, 5, 5);
	compound = BorderFactory.createCompoundBorder (
		empty, compound);

	p.setBorder (compound);
	screen.add (p);

    }


    public String whichimage (int c)
    {
	int third = c % 10;
	int second = (c % 100) / 10;
	int first = c / 100;
	String image = "empty";
	if (first == 1)
	    image = "pawn";
	else if (first == 2)
	    image = "bishop";
	else if (first == 3)
	    image = "knight";
	else if (first == 4)
	    image = "rook";
	else if (first == 5)
	    image = "queen";
	else if (first == 6)
	    image = "king";
	if (second == 0)
	    image += "empty";
	else if (second == 1)
	    image += "black";
	else
	    image += "white";
	if (third == 1)
	    image += "black";
	else
	    image += "white";
	return image;
    }


    public void redraw ()
    {
	int m = 0;
	for (int i = 0 ; i < row ; i++)
	{
	    for (int j = 0 ; j < col ; j++)
	    {
		board [m].setIcon (createImageIcon (whichimage (boardpieces [i] [j]) + ".jpg"));
		m++;
	    }
	}
    }


    public void actionPerformed (ActionEvent e)
    {
	int current = Integer.parseInt (e.getActionCommand ());

	if (last == -1)
	{
	    last = current;
	    num1 = last / 8;
	    num2 = last % 8;
	    swap1 = boardpieces [num1] [num2];
	    third1 = swap1 % 10;
	    toadd = swap1 - third1;
	}
	else
	{
	    num3 = current / 8;
	    num4 = current % 8;
	    swap2 = boardpieces [num3] [num4];
	    third2 = swap2 % 10;
	    toadd2 = swap2 - third2;
	    boardpieces [num3] [num4] = toadd + third2;
	    if (toadd == 0)
		boardpieces [num1] [num2] = toadd2 + third1;
	    else
		boardpieces [num1] [num2] = third1;
	    board [current].setIcon (createImageIcon (whichimage (boardpieces [num3] [num4]) + ".jpg"));
	    board [last].setIcon (createImageIcon (whichimage (boardpieces [num1] [num2]) + ".jpg"));
	    last = -1;
	}
    }



    protected static ImageIcon createImageIcon (String path)
    {
	java.net.URL imgURL = Chessold.class.getResource (path);
	if (imgURL != null)
	{
	    return new ImageIcon (imgURL);
	}
	else
	{
	    System.err.println ("Couldn't find file: " + path);
	    return null;
	}
    }
}


