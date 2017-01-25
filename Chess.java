//Abhinav Bora
//January 12, 2014
//Chess Game  for Computer Science Project
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.applet.Applet;
import javax.swing.border.Border; //to draw borders
import javax.swing.border.TitledBorder; //to draw Titled Borders

//sources for pictures
//1.Google Images
//Redlands RSL Chess Club
//Outback Teen Center
//Photoshop (myself)
public class Chess extends Applet implements ActionListener
{

    Panel p_screen; //to hold all of the screens
    Panel Multiplayer, Title, Basic, Specific, Train; //the five screens
    int screen = 1; //screen number so we can switch screens without messing up chessboard
    CardLayout cdLayout = new CardLayout ();
    int row = 8, col = 8; //8 rows and 8 columns in a chessboard
    JButton board[] = new JButton [row * col]; //first board for multiplayer
    JButton board2[] = new JButton [row * col]; //second board for training mode
    //original array for boardpieces values that is changed - first digit is piece: 0 = empty, 1 = pawn, 2 = bishop, 3 = knight, 4 = rook, 5 = queen, 6 = king - second digit is piece colour: 0 = empty, 1 = black, 2 = white - third digit is board colour: 1 = black, 2 = white, 3 = blue (piece can move here), 4 = green (piece selected), 5 = red (piece can be taken)
    int boardpieces[] [] = {{412, 311, 212, 511, 612, 211, 312, 411}, {111, 112, 111, 112, 111, 112, 111, 112}, {002, 001, 002, 001, 002, 001, 002, 001}, {001, 002, 001, 002, 001, 002, 001, 002}, {002, 001, 002, 001, 002, 001, 002, 001}, {001, 002, 001, 002, 001, 002, 001, 002}, {122, 121, 122, 121, 122, 121, 122, 121}, {421, 322, 221, 522, 621, 222, 321, 422}};
    //copy array for reset / quit
    int boardpieces2[] [] = {{412, 311, 212, 511, 612, 211, 312, 411}, {111, 112, 111, 112, 111, 112, 111, 112}, {002, 001, 002, 001, 002, 001, 002, 001}, {001, 002, 001, 002, 001, 002, 001, 002}, {002, 001, 002, 001, 002, 001, 002, 001}, {001, 002, 001, 002, 001, 002, 001, 002}, {122, 121, 122, 121, 122, 121, 122, 121}, {421, 322, 221, 522, 621, 222, 321, 422}};
    //copy array for checkmate - since when checking for checkmate it changes the array
    int boardpieces3[] [] = {{412, 311, 212, 511, 612, 211, 312, 411}, {111, 112, 111, 112, 111, 112, 111, 112}, {002, 001, 002, 001, 002, 001, 002, 001}, {001, 002, 001, 002, 001, 002, 001, 002}, {002, 001, 002, 001, 002, 001, 002, 001}, {001, 002, 001, 002, 001, 002, 001, 002}, {122, 121, 122, 121, 122, 121, 122, 121}, {421, 322, 221, 522, 621, 222, 321, 422}};
    //training mode boardpieces array
    int train[] [] = {{412, 1, 2, 1, 2, 411, 612, 1}, {111, 112, 1, 2, 1, 2, 111, 112}, {2, 1, 112, 1, 2, 1, 312, 1}, {1, 2, 1, 112, 1, 2, 321, 2}, {2, 1, 122, 1, 2, 121, 622, 1}, {1, 2, 1, 222, 1, 2, 121, 2}, {122, 121, 2, 1, 2, 1, 2, 511}, {421, 2, 321, 522, 1, 422, 1, 2}};
    //copy of train array for reset / quit
    int train2[] [] = {{412, 1, 2, 1, 2, 411, 612, 1}, {111, 112, 1, 2, 1, 2, 111, 112}, {2, 1, 112, 1, 2, 1, 312, 1}, {1, 2, 1, 112, 1, 2, 321, 2}, {2, 1, 122, 1, 2, 121, 622, 1}, {1, 2, 1, 222, 1, 2, 121, 2}, {122, 121, 2, 1, 2, 1, 2, 511}, {421, 2, 321, 522, 1, 422, 1, 2}};
    int moves[] = new int [200]; //stores up to 200 moves of game for undos
    int num = 0; //stores number of elements in moves array
    int last = -1, current; //hold actionCommand for buttons clicked - there has to be 2 for 2 buttons that are pressed (piece to move and where to move)
    int num1 = 0, num2 = 0, third1 = 0, toadd = 0, swap1 = 0, num3 = 0, num4 = 0, third2 = 0, toadd2 = 0, swap2 = 0; //hold values for the corresponding boardpieces array (buttons clicked correspond to value in boardpieces array)
    int move = 0; //odd number in move = black's turn, even number in move = white's turn
    int wcheck = 0; //if it doesn't hold 0 then it is a check on white
    int bcheck = 0; //if it doesn't hold 0 then it is a check on black
    int incheck = 0; //to check if the king is (still) in check
    int wkingmoved = 0, wrook1moved = 0, wrook2moved = 0; //to check if the white king/rook has moved to check if the player can still castle
    int wqscastleavailable = 0, wkscastleavailable; //if white has castled
    int bkingmoved = 0, brook1moved = 0, brook2moved = 0; //to check if the black king/rook has moved to check if the player can still castle
    int bqscastleavailable = 0, bkscastleavailable = 0; //if black has castled
    int undoposs = 0; //counter to check if undo is possible
    int piecesmoved = 0; //holds number of moves in training movde
    JButton undo;
    int checkmate = 0; //counter to see if there is a checkmate, 0 means there is no checkmate
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem menuItem;
    JLabel bpcapt, wpcapt, bbcapt, wbcapt, bkcapt, wkcapt, brcapt, wrcapt, bqcapt, wqcapt; //JLabels for how many of each piece are captured
    int bpcaptnum = 0, wpcaptnum = 0, bbcaptnum = 0, wbcaptnum = 0, bkcaptnum = 0, wkcaptnum = 0, brcaptnum = 0, wrcaptnum = 0, bqcaptnum = 0, wqcaptnum = 0; // values for how many of each piece are captured
    JProgressBar progressBar;

    public void init ()
    {
	p_screen = new Panel ();
	p_screen.setLayout (cdLayout);
	Title ();
	Multiplayer ();
	Basic ();
	Specific ();
	Train ();
	setLayout (new BorderLayout ());
	add ("Center", p_screen);

	resize (900, 700);


    }


    public void Title ()  //title screen
    {
	Title = new Panel ();
	Title.setBackground (Color.white);
	JPanel main = new JPanel (new GridLayout (3, 1)); //JPanels worked with Borders
	JButton multi = new JButton ("Multiplayer");
	multi.addActionListener (this);
	multi.setActionCommand ("multi");

	menuBar = new JMenuBar ();
	menu = new JMenu ("   Instructions");
	menu.setMnemonic (KeyEvent.VK_A);
	menu.getAccessibleContext ().setAccessibleDescription (
		"Menu");
	menuBar.add (menu);
	menuItem = new JMenuItem ("Basic Rules",
		KeyEvent.VK_T);
	menuItem.setAccelerator (KeyStroke.getKeyStroke (
		    KeyEvent.VK_1, ActionEvent.ALT_MASK));
	menuItem.getAccessibleContext ().setAccessibleDescription (
		"General Instructions");
	menuItem.addActionListener (this);
	menuItem.setActionCommand ("basic");
	menu.add (menuItem);
	menuItem = new JMenuItem ("Game-Specific Rules",
		KeyEvent.VK_T);
	menuItem.setAccelerator (KeyStroke.getKeyStroke (
		    KeyEvent.VK_1, ActionEvent.ALT_MASK));
	menuItem.getAccessibleContext ().setAccessibleDescription (
		"Rules for my game");
	menuItem.addActionListener (this);
	menuItem.setActionCommand ("specific");
	menu.add (menuItem);

	JButton train = new JButton ("Training");
	train.addActionListener (this);
	train.setActionCommand ("train");

	main.add (menuBar);
	main.add (multi);
	main.add (train);
	Border raisedbevel = BorderFactory.createRaisedBevelBorder (); //borders
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
	main.setBorder (compound);
	JLabel image = new JLabel (createImageIcon ("insimage.jpg"));//image source: Redlands RSL Chess Club
	image.setPreferredSize (new Dimension (800, 500));

	JLabel t = new JLabel ("Chess!");
	t.setFont (new Font ("Jokerman", Font.BOLD, 30));
	Title.add (t);
	Title.add (image);
	Title.add (main);


	p_screen.add ("1", Title);
    }


    public void Multiplayer ()  //multiplayer chess screen
    {
	Multiplayer = new Panel ();
	Multiplayer.setBackground (new Color (255, 230, 177));
	chessboard (Multiplayer, boardpieces, 1); //sets up board on screen


	JPanel capturedwhite = new JPanel (new GridLayout (5, 2));
	Border white = BorderFactory.createTitledBorder ("Captured White");
	capturedwhite.setBorder (white);
	JPanel capturedblack = new JPanel (new GridLayout (5, 2));
	Border black = BorderFactory.createTitledBorder ("Captured Black");
	capturedblack.setBorder (black);
	JLabel blackpawn = new JLabel (createImageIcon ("blackpawn.gif"));
	bpcapt = new JLabel ("x" + bpcaptnum);
	JLabel whitepawn = new JLabel (createImageIcon ("whitepawn.gif"));
	wpcapt = new JLabel ("x" + wpcaptnum);
	JLabel blackbishop = new JLabel (createImageIcon ("blackbishop.gif"));
	bbcapt = new JLabel ("x" + bbcaptnum);
	JLabel whitebishop = new JLabel (createImageIcon ("whitebishop.gif"));
	wbcapt = new JLabel ("x" + wbcaptnum);
	JLabel blackknight = new JLabel (createImageIcon ("blackknight.gif"));
	bkcapt = new JLabel ("x" + bkcaptnum);
	JLabel whiteknight = new JLabel (createImageIcon ("whiteknight.gif"));
	wkcapt = new JLabel ("x" + wkcaptnum);
	JLabel blackrook = new JLabel (createImageIcon ("blackrook.gif"));
	brcapt = new JLabel ("x" + brcaptnum);
	JLabel whiterook = new JLabel (createImageIcon ("whiterook.gif"));
	wrcapt = new JLabel ("x" + wrcaptnum);
	JLabel blackqueen = new JLabel (createImageIcon ("blackqueen.gif"));
	bqcapt = new JLabel ("x" + bqcaptnum);
	JLabel whitequeen = new JLabel (createImageIcon ("whitequeen.gif"));
	wqcapt = new JLabel ("x" + wqcaptnum);
	capturedwhite.add (whitepawn);
	capturedwhite.add (wpcapt);
	capturedwhite.add (whitebishop);
	capturedwhite.add (wbcapt);
	capturedwhite.add (whiteknight);
	capturedwhite.add (wkcapt);
	capturedwhite.add (whiterook);
	capturedwhite.add (wrcapt);
	capturedwhite.add (whitequeen);
	capturedwhite.add (wqcapt);
	Multiplayer.add (capturedwhite);
	capturedblack.add (blackpawn);
	capturedblack.add (bpcapt);
	capturedblack.add (blackbishop);
	capturedblack.add (bbcapt);
	capturedblack.add (blackknight);
	capturedblack.add (bkcapt);
	capturedblack.add (blackrook);
	capturedblack.add (brcapt);
	capturedblack.add (blackqueen);
	capturedblack.add (bqcapt);
	Multiplayer.add (capturedblack);
	JPanel options = new JPanel (new GridLayout (1, 4)); //JPanels worked with Borders
	undo = new JButton ("Undo");
	undo.addActionListener (this);
	undo.setActionCommand ("undo");
	undo.setEnabled (false);
	JButton reset = new JButton ("Reset");
	reset.setActionCommand ("reset");
	reset.addActionListener (this);
	JButton quit = new JButton ("Quit");
	quit.setActionCommand ("quit");
	quit.addActionListener (this);
	options.add (undo);
	options.add (reset);
	options.add (quit);
	Border optionstitle = BorderFactory.createTitledBorder ("Options"); //borders
	options.setBorder (optionstitle);
	Multiplayer.add (options);

	p_screen.add ("2", Multiplayer);
    }


    public void Basic ()  //bsic instructions screen
    {
	Basic = new Panel ();
	Basic.setBackground (Color.orange);
	Panel bas = new Panel (new GridLayout (2, 1));
	JLabel basicimage = new JLabel (createImageIcon ("basicimage.jpg"));//image source: Outcback Teen Center
	JLabel basictitle = new JLabel ("                                                                                             How to Play");
	JTextArea TA2 = new JTextArea (5, 15);
	TA2.setBackground (Color.green); //change this later, it is just so you can see it's edges
	TA2.setText ("                                                                 Main Strategy: PROTECT THE KING \n");
	TA2.append ("1. Pawns can move 2 in front in beginning and then 1 up after.\n");
	TA2.append ("2. Bishops can move diagonally in any direction.\n");
	TA2.append ("3. Knight can move in a L shape in any direction.\n");
	TA2.append ("4. Rooks can move up or down in any direction.\n");
	TA2.append ("5. Queens can move diagonally and up or down in any direction.\n");
	TA2.append ("6. Kings can move 1 space in any direction.\n");
	TA2.append ("7. When pawns reach other end, they can be promoted to a Queen, Bishop, Knight or Rook.\n");
	TA2.append ("8. You can castle kngside or queenside if the king/rook isn't in check and you aren't castling through a check or a piece.\n");
	TA2.append ("\n ");
	TA2.append ("        ");
	bas.add (basictitle);
	bas.add (TA2);
	Basic.add (bas);
	Basic.add (basicimage);
	JButton back = new JButton ("Back to Menu");
	back.addActionListener (this);
	back.setActionCommand ("quit");
	Basic.add (back);
	p_screen.add ("3", Basic);
    }


    public void Specific ()  //game specific instructions screen
    {
	Specific = new Panel ();
	Specific.setBackground (Color.yellow);
	Panel spec = new Panel (new GridLayout (2, 1));
	JLabel institle = new JLabel ("                                                          Welcome to Chess!!!");
	JTextArea TA = new JTextArea (5, 15);
	TA.setBackground (Color.green); //change this later, it is just so you can see it's edges
	TA.setText ("    This game is exactly like the real chess with easy to use features such as saving, \n");
	TA.append ("loading, moving and training. During multiplayer play, click on the piece to view\n");
	TA.append ("possible moves and click on option buttons to perform different functions.\n");
	TA.append ("\n ");
	TA.append ("                                                               HAVE FUN!!!\n");
	JLabel image = new JLabel (createImageIcon ("insimage.jpg"));//image source: Redlands RSL Chess Club
	spec.add (institle);
	spec.add (TA);
	Specific.add (spec);
	Specific.add (image);
	JButton back = new JButton ("Back to Menu");
	back.addActionListener (this);
	back.setActionCommand ("quit");
	Specific.add (back);
	p_screen.add ("4", Specific);
    }


    public void Train ()  //training mode screen
    {
	Train = new Panel ();
	Train.setBackground (new Color (255, 230, 177));
	chessboard (Train, train, 2);
	JPanel training = new JPanel (new GridLayout (1, 2));
	JButton quit = new JButton ("Quit");
	quit.setActionCommand ("quit");
	quit.addActionListener (this);
	JButton restart = new JButton ("Restart");
	restart.setActionCommand ("restart");
	restart.addActionListener (this);
	training.add (restart);
	training.add (quit);
	Border trainingtitle = BorderFactory.createTitledBorder ("Options"); //borders
	training.setBorder (trainingtitle);
	Train.add (training);
	progressBar = new JProgressBar (0, 100);
	progressBar.setValue (0);
	progressBar.setStringPainted (true);
	Train.add (progressBar);
	p_screen.add ("5", Train);
    }


    public void chessboard (Panel screen, int boardpieces[] [], int b)  //draws chess board for Multiplayer or Train, parameters: specific screen (Multiplayer or Train), with specific array (boardpieces or train) and specific board (1 = board or 2 = board2)
    {
	JPanel p = new JPanel (new GridLayout (8, 8)); //JPanels worked with Borders
	int a = 0;
	for (int i = 0 ; i < row ; i++)
	{
	    for (int j = 0 ; j < col ; j++)
	    {
		if (b == 1)
		{
		    board [a] = new JButton (createImageIcon ((boardpieces [i] [j]) + ".jpg"));//image source: Google Images and then Photoshoped myself
		    board [a].addActionListener (this);
		    board [a].setActionCommand ("" + a);
		    board [a].setPreferredSize (new Dimension (75, 75));
		    p.add (board [a]);
		}
		else
		{
		    board2 [a] = new JButton (createImageIcon ((boardpieces [i] [j]) + ".jpg"));//image source: Google Images and then Photoshoped myself
		    board2 [a].addActionListener (this);
		    board2 [a].setActionCommand ("" + a);
		    board2 [a].setPreferredSize (new Dimension (75, 75));
		    p.add (board2 [a]);
		}
		a++;
	    }
	}
	Border raisedbevel = BorderFactory.createRaisedBevelBorder (); //borders
	Border loweredbevel = BorderFactory.createLoweredBevelBorder ();
	Border compound;

	compound = BorderFactory.createCompoundBorder (
		raisedbevel, loweredbevel);
	Border blackline = BorderFactory.createLineBorder (Color.black);
	compound = BorderFactory.createCompoundBorder (
		blackline, compound);
	Border empty = BorderFactory.createEmptyBorder (5, 5, 5, 5);
	//compound puts together different types of borders
	compound = BorderFactory.createCompoundBorder (
		empty, compound);

	p.setBorder (compound);
	screen.add (p);

    }



    public void redraw ()  //redraws both board and board2 according to boardpieces and train arrays
    {
	int m = 0;
	for (int i = 0 ; i < row ; i++)
	{
	    for (int j = 0 ; j < col ; j++)
	    { //I could use numbers as picture names instead of this "whichimage" method
		board [m].setIcon (createImageIcon ((boardpieces [i] [j]) + ".jpg"));
		m++;
	    }
	}
	m = 0;
	for (int g = 0 ; g < row ; g++)
	{
	    for (int h = 0 ; h < col ; h++)
	    { //I could use numbers as picture names instead of this "whichimage" method
		board2 [m].setIcon (createImageIcon ((train [g] [h]) + ".jpg"));
		m++;
	    }
	}
    }


    public void Validmoves (int boardpieces[] [], int toswap1, int x, int y)  //boardpieces represents which array to call Valid Moves on (boardpieces or train), toswap1 is the number that represents the piece and its colour (only counting the first 2 digits), int x and y are the x and y values of the piece on the boardpieces array
    {

	//sets valid squares for the piece to move on as blue BY CHANGING 3RD DIGIT OF NUMBERS IN THE BOARDPIECES ARRAY THAT CORRESPOND TO THE VALID SQUARES TO 3
	//all lines of code that subtract a variable % 10 from itself and add 3, make that square a valid square (blue)
	int colour = (toswap1 / 10) % 10; // to find the colour of the piece by figuring out the second number
	int piece = toswap1 / 100; // to find which piece it is by figuring out the first digit of the number
	if (piece == 1 && colour == 2) //if it's a pawn and it's white
	{
	    if (boardpieces [x - 1] [y] / 100 == 0) //to see if sqaure in front is empty
	    {
		boardpieces [x - 1] [y] = boardpieces [x - 1] [y] - (boardpieces [x - 1] [y] % 10) + 3; //changes square colour to blue so the player can see if you can move here
		if (x == 6) //if pawn is at starting position (for white pawns)
		{
		    if (boardpieces [x - 2] [y] / 100 == 0) //to see if the square 2 in front is empty
			boardpieces [x - 2] [y] = boardpieces [x - 2] [y] - (boardpieces [x - 2] [y] % 10) + 3;
		}
	    }

	    if (y - 1 >= 0 && boardpieces [x - 1] [y - 1] / 100 > 0 && (((boardpieces [x - 1] [y - 1] / 10) % 10) == 1)) //if there is a piece/square (if it's inside the array) in front to the left of the pawn and its black
		boardpieces [x - 1] [y - 1] = boardpieces [x - 1] [y - 1] - (boardpieces [x - 1] [y - 1] % 10) + 3;


	    if (y + 1 < col && boardpieces [x - 1] [y + 1] / 100 > 0 && (((boardpieces [x - 1] [y + 1] / 10) % 10) == 1)) //if there is a piece/square (if it's inside the array) in front to the right of the pawn and its black
		boardpieces [x - 1] [y + 1] = boardpieces [x - 1] [y + 1] - (boardpieces [x - 1] [y + 1] % 10) + 3;

	}
	else if (piece == 1 && colour == 1) //if it's a pawn and it's black
	{
	    if (boardpieces [x + 1] [y] / 100 == 0) //if square in front is empty
	    {
		boardpieces [x + 1] [y] = boardpieces [x + 1] [y] - (boardpieces [x + 1] [y] % 10) + 3;
		if (x == 1) //if pawn is at starting position (for black pawns)
		{
		    if (boardpieces [x + 2] [y] / 100 == 0) //to see if the square 2 in front is empty
			boardpieces [x + 2] [y] = boardpieces [x + 2] [y] - (boardpieces [x + 2] [y] % 10) + 3;
		}
	    }

	    if (y - 1 >= 0 && boardpieces [x + 1] [y - 1] / 100 > 0 && (((boardpieces [x + 1] [y - 1] / 10) % 10) == 2)) //if there is a piece/square (if it's inside the array) in front to the left of the pawn and its white
		boardpieces [x + 1] [y - 1] = boardpieces [x + 1] [y - 1] - (boardpieces [x + 1] [y - 1] % 10) + 3;


	    if (y + 1 < col && boardpieces [x + 1] [y + 1] / 100 > 0 && (((boardpieces [x + 1] [y + 1] / 10) % 10) == 2)) //if there is a piece/square (if it's inside the array) in front to the right of the pawn and its white
		boardpieces [x + 1] [y + 1] = boardpieces [x + 1] [y + 1] - (boardpieces [x + 1] [y + 1] % 10) + 3;

	}
	else if (piece == 2) //bishop
	{
	    bishop (x, y, colour);
	}
	else if (piece == 3) //knight
	{ //checks if the piece in front or back or sides in a L shape is not its own colour
	    if (x - 1 >= 0 && y + 2 < col && (boardpieces [x - 1] [y + 2] / 10) % 10 != colour)
		boardpieces [x - 1] [y + 2] = boardpieces [x - 1] [y + 2] - (boardpieces [x - 1] [y + 2] % 10) + 3;
	    if (x - 2 >= 0 && y + 1 < col && (boardpieces [x - 2] [y + 1] / 10) % 10 != colour)
		boardpieces [x - 2] [y + 1] = boardpieces [x - 2] [y + 1] - (boardpieces [x - 2] [y + 1] % 10) + 3;
	    if (x - 2 >= 0 && y - 1 >= 0 && (boardpieces [x - 2] [y - 1] / 10) % 10 != colour)
		boardpieces [x - 2] [y - 1] = boardpieces [x - 2] [y - 1] - (boardpieces [x - 2] [y - 1] % 10) + 3;
	    if (x - 1 >= 0 && y - 2 >= 0 && (boardpieces [x - 1] [y - 2] / 10) % 10 != colour)
		boardpieces [x - 1] [y - 2] = boardpieces [x - 1] [y - 2] - (boardpieces [x - 1] [y - 2] % 10) + 3;
	    if (x + 1 < row && y - 2 >= 0 && (boardpieces [x + 1] [y - 2] / 10) % 10 != colour)
		boardpieces [x + 1] [y - 2] = boardpieces [x + 1] [y - 2] - (boardpieces [x + 1] [y - 2] % 10) + 3;
	    if (x + 2 < row && y - 1 >= 0 && (boardpieces [x + 2] [y - 1] / 10) % 10 != colour)
		boardpieces [x + 2] [y - 1] = boardpieces [x + 2] [y - 1] - (boardpieces [x + 2] [y - 1] % 10) + 3;
	    if (x + 2 < row && y + 1 < col && (boardpieces [x + 2] [y + 1] / 10) % 10 != colour)
		boardpieces [x + 2] [y + 1] = boardpieces [x + 2] [y + 1] - (boardpieces [x + 2] [y + 1] % 10) + 3;
	    if (x + 1 < row && y + 2 < col && (boardpieces [x + 1] [y + 2] / 10) % 10 != colour)
		boardpieces [x + 1] [y + 2] = boardpieces [x + 1] [y + 2] - (boardpieces [x + 1] [y + 2] % 10) + 3;
	}
	else if (piece == 4) //rook
	{
	    rook (x, y, colour);
	}
	else if (piece == 5) //queen - moves the same as a bishop and rook put together
	{
	    bishop (x, y, colour);
	    rook (x, y, colour);
	}
	else if (piece == 6) //king
	{
	    /*white king castling - had to check if it was white's move because if i didn't it would cause a "stackoverflow" error
		    since whenever I called Validmoves inside the following loop or in the black king castling if statement below, it would check the Valid moves
		    for all the black pieces including the black king, and since I called Validmoves on all the white pieces in the black king castle if statement
		    it would call Validmoves on a white king which would in turn call Valid moves again for the black king and this would keep
		    on going forever!!!!! By checking if it is white's turn before entering this if I could avoid Validmoves being called over
		    and over again.*/
	    if (colour == 2 && wkingmoved == 0 && move % 2 == 0) //if king didn't move and it's white
	    {
		for (int i = 0 ; i < 8 ; i++)
		{
		    for (int j = 0 ; j < 8 ; j++)
		    {
			if ((boardpieces [i] [j] / 10) % 10 == 1) //calls valid Moves for all black pieces to check is castling is Valid
			    Validmoves (boardpieces, (boardpieces [i] [j] - (boardpieces [i] [j] % 10)), i, j);
		    }
		}
		//checks if white rook2 (king side) moved and there is no check or pieces in between
		if (wrook2moved == 0 && x == 7 && boardpieces [x] [y] % 10 != 3 && y + 1 < col && boardpieces [x] [y + 1] % 10 != 3 && (boardpieces [x] [y + 1] / 10) % 10 == 0 && y + 2 < col && boardpieces [x] [y + 2] % 10 != 3 && (boardpieces [x] [y + 2] / 10) % 10 == 0) //king's side castling
		{
		    resetcolours (boardpieces); //resets
		    wkscastleavailable++;
		}
		//checks if white rook1 (queen side) moved and there is no check or pieces in between
		if (wrook1moved == 0 && x == 7 && boardpieces [x] [y] % 10 != 3 && y - 1 >= 0 && boardpieces [x] [y - 1] % 10 != 3 && (boardpieces [x] [y - 1] / 10) % 10 == 0 && y - 2 >= 0 && boardpieces [x] [y - 2] % 10 != 3 && (boardpieces [x] [y - 2] / 10) % 10 == 0 && y - 3 >= 0 && boardpieces [x] [y - 3] % 10 != 3) //queen side castling
		{
		    resetcolours (boardpieces);
		    wqscastleavailable++;
		}
		//if no castle is available it sets all the blue squares that were called on black pieces when Valid Moves was called to normal colours
		if (wkscastleavailable == 0 && wqscastleavailable == 0)
		    resetcolours (boardpieces);
	    }
	    /*black king castling - had to check if it was black's move because if i didn't it would cause a "stackoverflow" error
		since whenever I called Validmoves inside the following loop or in the white king castling if statement above, it would check
		the Valid moves for all the white pieces including the white king, and since I called Validmoves on all the black pieces in the
		white king castle if statement it would call Validmoves on a black king which would in turn call Valid moves again for the white
		king and this would keep on going forever!!!!! By checking if it is black's turn before entering this if I could avoid Validmoves
		being called over and over again.*/
	    else if (colour == 1 && bkingmoved == 0 && move % 2 == 1) //if king didn't move and it's black
	    {
		for (int i = 0 ; i < 8 ; i++)
		{
		    for (int j = 0 ; j < 8 ; j++)
		    {
			if ((boardpieces [i] [j] / 10) % 10 == 2) //calls valid Moves for all black pieces to check is castling is Valid
			    Validmoves (boardpieces, (boardpieces [i] [j] - (boardpieces [i] [j] % 10)), i, j);
		    }
		}
		//checks if black rook2 (king side) moved and there is no check or pieces in between
		if (brook2moved == 0 && x == 0 && boardpieces [x] [y] % 10 != 3 && y + 1 < col && boardpieces [x] [y + 1] % 10 != 3 && (boardpieces [x] [y + 1] / 10) % 10 == 0 && y + 2 < col && boardpieces [x] [y + 2] % 10 != 3 && (boardpieces [x] [y + 2] / 10) % 10 == 0) //king's side castling
		{
		    resetcolours (boardpieces);
		    bkscastleavailable++;
		}
		//checks if black rook1 (queen side) moved and there is no check or pieces in between
		if (brook1moved == 0 && x == 0 && boardpieces [x] [y] % 10 != 3 && y - 1 >= 0 && boardpieces [x] [y - 1] % 10 != 3 && (boardpieces [x] [y - 1] / 10) % 10 == 0 && y - 2 >= 0 && boardpieces [x] [y - 2] % 10 != 3 && (boardpieces [x] [y - 2] / 10) % 10 == 0 && y - 3 >= 0 && boardpieces [x] [y - 3] % 10 != 3) //king's side castling
		{
		    resetcolours (boardpieces);
		    bqscastleavailable++;
		}
		//if no castle is available it sets all the blue squares that were called on white pieces when Valid Moves was called to normal colours
		if (bkscastleavailable == 0 && bqscastleavailable == 0)
		    resetcolours (boardpieces);
	    }
	    //king moves (up, down, left, right and diagonally in all directions for one space)
	    if (y + 1 < col && ((boardpieces [x] [y + 1] / 10) % 10) != colour)
	    {
		boardpieces [x] [y + 1] = boardpieces [x] [y + 1] - (boardpieces [x] [y + 1] % 10) + 3;
	    }
	    if (x - 1 >= 0 && y + 1 < col && ((boardpieces [x - 1] [y + 1] / 10) % 10) != colour)
	    {
		boardpieces [x - 1] [y + 1] = boardpieces [x - 1] [y + 1] - (boardpieces [x - 1] [y + 1] % 10) + 3;
	    }
	    if (x - 1 >= 0 && ((boardpieces [x - 1] [y] / 10) % 10) != colour)
	    {
		boardpieces [x - 1] [y] = boardpieces [x - 1] [y] - (boardpieces [x - 1] [y] % 10) + 3;
	    }
	    if (x - 1 >= 0 && y - 1 >= 0 && ((boardpieces [x - 1] [y - 1] / 10) % 10) != colour)
	    {
		boardpieces [x - 1] [y - 1] = boardpieces [x - 1] [y - 1] - (boardpieces [x - 1] [y - 1] % 10) + 3;
	    }
	    if (y - 1 >= 0 && ((boardpieces [x] [y - 1] / 10) % 10) != colour)
	    {
		boardpieces [x] [y - 1] = boardpieces [x] [y - 1] - (boardpieces [x] [y - 1] % 10) + 3;
	    }
	    if (x + 1 < row && y - 1 >= 0 && ((boardpieces [x + 1] [y - 1] / 10) % 10) != colour)
	    {
		boardpieces [x + 1] [y - 1] = boardpieces [x + 1] [y - 1] - (boardpieces [x + 1] [y - 1] % 10) + 3;
	    }
	    if (x + 1 < row && ((boardpieces [x + 1] [y] / 10) % 10) != colour)
	    {
		boardpieces [x + 1] [y] = boardpieces [x + 1] [y] - (boardpieces [x + 1] [y] % 10) + 3;
	    }
	    if (x + 1 < row && y + 1 < col && ((boardpieces [x + 1] [y + 1] / 10) % 10) != colour)
	    {
		boardpieces [x + 1] [y + 1] = boardpieces [x + 1] [y + 1] - (boardpieces [x + 1] [y + 1] % 10) + 3;
	    }
	    //if castles were available it sets the squares for astling blue
	    if (y + 2 < col && (wkscastleavailable == 1 || bkscastleavailable == 1))
		boardpieces [x] [y + 2] = boardpieces [x] [y + 2] - (boardpieces [x] [y + 2] % 10) + 3;
	    if (y - 2 >= 0 && (bqscastleavailable == 1 || wqscastleavailable == 1))
		boardpieces [x] [y - 2] = boardpieces [x] [y - 2] - (boardpieces [x] [y - 2] % 10) + 3;
	}
    }


    public void bishop (int x, int y, int colour)  //takes in x and y values of piece and its colour
    {
	int cannot = 1;
	while (cannot != 0) //goes through squares to the bottom and left, cannot increases by one to check the next square over if the previous square was empty
	{
	    if (x + cannot < row && y - cannot >= 0 && ((boardpieces [x + cannot] [y - cannot] / 10) % 10) != colour) //if the square is not its own colour
	    {
		boardpieces [x + cannot] [y - cannot] = boardpieces [x + cannot] [y - cannot] - (boardpieces [x + cannot] [y - cannot] % 10) + 3;
		if (x + cannot < row && y - cannot >= 0 && ((boardpieces [x + cannot] [y - cannot] / 10) % 10) != 0) //if the square is not empty
		    cannot = 0;
		else
		    cannot++;
	    }
	    else
		cannot = 0;
	}
	cannot = 1;
	while (cannot != 0) //goes through squares to the up and left, cannot increases by one to check the next square over if the previous square was empty
	{
	    if (x - cannot >= 0 && y - cannot >= 0 && ((boardpieces [x - cannot] [y - cannot] / 10) % 10) != colour) //if the square is not its own colour
	    {
		boardpieces [x - cannot] [y - cannot] = boardpieces [x - cannot] [y - cannot] - (boardpieces [x - cannot] [y - cannot] % 10) + 3;
		if (((boardpieces [x - cannot] [y - cannot] / 10) % 10) != 0) //if the square is not empty
		    cannot = 0;
		else
		    cannot++;
	    }
	    else
		cannot = 0;
	}
	cannot = 1;
	while (cannot != 0) //goes through squares to the up and right, cannot increases by one to check the next square over if the previous square was empty
	{
	    if (x - cannot >= 0 && y + cannot < col && ((boardpieces [x - cannot] [y + cannot] / 10) % 10) != colour) //if the square is not its own colour
	    {
		boardpieces [x - cannot] [y + cannot] = boardpieces [x - cannot] [y + cannot] - (boardpieces [x - cannot] [y + cannot] % 10) + 3;
		if (((boardpieces [x - cannot] [y + cannot] / 10) % 10) != 0) //if the square is not empty
		    cannot = 0;
		else
		    cannot++;
	    }
	    else
		cannot = 0;
	}
	cannot = 1;
	while (cannot != 0) //goes through squares to the down and right, cannot increases by one to check the next square over if the previous square was empty
	{
	    if (x + cannot < row && y + cannot < col && ((boardpieces [x + cannot] [y + cannot] / 10) % 10) != colour) //if the square is not its own colour
	    {
		boardpieces [x + cannot] [y + cannot] = boardpieces [x + cannot] [y + cannot] - (boardpieces [x + cannot] [y + cannot] % 10) + 3;
		if (((boardpieces [x + cannot] [y + cannot] / 10) % 10) != 0) //if the square is not empty
		    cannot = 0;
		else
		    cannot++;
	    }
	    else
		cannot = 0;
	}

    }


    public void rook (int x, int y, int colour)
    {
	int cannot = 1;
	while (cannot != 0) //goes through squares to the bottom, cannot increases by one to check the next square over if the previous square was empty
	{
	    if (x + cannot < row && ((boardpieces [x + cannot] [y] / 10) % 10) != colour) //if the square is not its own colour
	    {
		boardpieces [x + cannot] [y] = boardpieces [x + cannot] [y] - (boardpieces [x + cannot] [y] % 10) + 3;
		if (((boardpieces [x + cannot] [y] / 10) % 10) != 0) //if the square is not empty
		    cannot = 0;
		else
		    cannot++;
	    }
	    else
		cannot = 0;
	}
	cannot = 1;
	while (cannot != 0) //goes through squares above, cannot increases by one to check the next square over if the previous square was empty
	{
	    if (x - cannot >= 0 && ((boardpieces [x - cannot] [y] / 10) % 10) != colour) //if the square is not its own colour
	    {
		boardpieces [x - cannot] [y] = boardpieces [x - cannot] [y] - (boardpieces [x - cannot] [y] % 10) + 3;
		if (((boardpieces [x - cannot] [y] / 10) % 10) != 0) //if the square is not empty
		    cannot = 0;
		else
		    cannot++;
	    }
	    else
		cannot = 0;
	}
	cannot = 1;
	while (cannot != 0) //goes through squares to the right, cannot increases by one to check the next square over if the previous square was empty
	{
	    if (y + cannot < col && ((boardpieces [x] [y + cannot] / 10) % 10) != colour) //if the square is not its own colour
	    {
		boardpieces [x] [y + cannot] = boardpieces [x] [y + cannot] - (boardpieces [x] [y + cannot] % 10) + 3;
		if (((boardpieces [x] [y + cannot] / 10) % 10) != 0) //if the square is not empty
		    cannot = 0;
		else
		    cannot++;
	    }
	    else
		cannot = 0;
	}
	cannot = 1;
	while (cannot != 0) //goes through squares to the left, cannot increases by one to check the next square over if the previous square was empty
	{
	    if (y - cannot >= 0 && ((boardpieces [x] [y - cannot] / 10) % 10) != colour) //if the square is not its own colour
	    {
		boardpieces [x] [y - cannot] = boardpieces [x] [y - cannot] - (boardpieces [x] [y - cannot] % 10) + 3;
		if (((boardpieces [x] [y - cannot] / 10) % 10) != 0) //if the square is not empty
		    cannot = 0;
		else
		    cannot++;
	    }
	    else
		cannot = 0;
	}
    }


    public void resetcolours (int boardpieces[] [])
    {
	for (int g = 0 ; g < 8 ; g++) //this loop resets 3rd digit to 1 or 2 resetting board colours
	{
	    for (int f = 0 ; f < 8 ; f++)
	    {

		if ((g + f) % 2 == 0)
		    boardpieces [g] [f] = (boardpieces [g] [f] - (boardpieces [g] [f] % 10)) + 2;
		else
		    boardpieces [g] [f] = (boardpieces [g] [f] - (boardpieces [g] [f] % 10)) + 1;
	    }
	}
    }


    public void resetboard ()  //reset boardpieces to its copy from the beginning
    {
	for (int i = 0 ; i < row ; i++)
	    for (int j = 0 ; j < col ; j++)
		boardpieces [i] [j] = boardpieces2 [i] [j];
	redraw ();
	for (int i = 0 ; i < num ; i++) //resets moves array to zero
	    moves [i] = 0;
	//resets variables
	num = 0;
	checkmate = 0;
	move = 0;
	last = -1;
	wqscastleavailable = 0;
	wkscastleavailable = 0;
	bqscastleavailable = 0;
	bkscastleavailable = 0;
	wcheck = 0;
	bcheck = 0;
	incheck = 0;
	wkingmoved = 0;
	wrook1moved = 0;
	wrook2moved = 0;
	bkingmoved = 0;
	brook1moved = 0;
	brook2moved = 0;
	bpcaptnum = 0;
	wpcaptnum = 0;
	bbcaptnum = 0;
	wbcaptnum = 0;
	bkcaptnum = 0;
	wkcaptnum = 0;
	brcaptnum = 0;
	wrcaptnum = 0;
	bqcaptnum = 0;
	wqcaptnum = 0;
	bpcapt.setText ("x" + bpcaptnum);
	wpcapt.setText ("x" + wpcaptnum);
	bbcapt.setText ("x" + bbcaptnum);
	wbcapt.setText ("x" + wbcaptnum);
	bkcapt.setText ("x" + bkcaptnum);
	wkcapt.setText ("x" + wkcaptnum);
	brcapt.setText ("x" + brcaptnum);
	wrcapt.setText ("x" + wrcaptnum);
	bqcapt.setText ("x" + bqcaptnum);
	wqcapt.setText ("x" + wpcaptnum);
	undo.setEnabled (false);
    }


    public void actionPerformed (ActionEvent e)
    {
	if (e.getActionCommand ().equals ("undo"))
	{
	    for (int g = 0 ; g < 8 ; g++) //this loop checks if a piece is selected
	    {
		for (int f = 0 ; f < 8 ; f++)
		{
		    if (boardpieces [g] [f] % 10 == 4)
			undoposs++;
		}
	    }
	    if (undoposs == 0) //if undo is possible
	    {
		//undos moves by making boardpieces array = to the corresponding numbers in the moves array
		boardpieces [moves [num - 1] / 10000] [(moves [num - 1] / 1000) % 10] = moves [num - 1] % 1000;
		boardpieces [moves [num - 2] / 10000] [(moves [num - 2] / 1000) % 10] = moves [num - 2] % 1000;
		num -= 2;
		resetcolours (boardpieces);
		redraw ();
		if (move % 2 == 0) //if its whites turn it becomes blacks turn
		    move = 1;
		else //if its blacks turn it becomes white turns
		    move = 0;

		if (num == 0) //if there is nothing left in the moves array
		    undo.setEnabled (false);
	    }
	    else
	    {
		JOptionPane.showMessageDialog (null, "Can't Undo IF YOU HAVEN'T MOVED!", "Invalid Undo",
			JOptionPane.ERROR_MESSAGE);
		undoposs = 0;
	    }

	}
	else if (e.getActionCommand ().equals ("multi"))
	{
	    cdLayout.show (p_screen, "2");
	    screen = 2;
	    move = 0;
	    showStatus ("Welcome to Multiplayer Chess! It's currently white's turn.");
	}
	else if (e.getActionCommand ().equals ("quit"))
	{
	    screen = 1;
	    resetboard ();
	    for (int i = 0 ; i < row ; i++)
		for (int j = 0 ; j < col ; j++)
		    train [i] [j] = train2 [i] [j];
	    redraw ();
	    last = -1;
	    piecesmoved = 0;
	    cdLayout.show (p_screen, "1");
	}
	else if (e.getActionCommand ().equals ("reset"))
	{
	    resetboard ();
	    showStatus ("White's Turn");
	}
	else if (e.getActionCommand ().equals ("specific"))
	{
	    screen = 4;
	    cdLayout.show (p_screen, "4");
	    showStatus ("Specific Instructions");
	}
	else if (e.getActionCommand ().equals ("basic"))
	{
	    screen = 3;
	    cdLayout.show (p_screen, "3");
	    showStatus ("Basic Instructions");
	}
	else if (e.getActionCommand ().equals ("train"))
	{
	    screen = 5;
	    cdLayout.show (p_screen, "5");
	    showStatus ("Training Mode - Black to Move: CHECKMATE IN TWO");
	}
	else if (e.getActionCommand ().equals ("restart"))
	{ //train becomes original copy
	    for (int i = 0 ; i < row ; i++)
		for (int j = 0 ; j < col ; j++)
		    train [i] [j] = train2 [i] [j];
	    last = -1;
	    piecesmoved = 0;
	    progressBar.setString ("0%");
	    redraw ();
	}
	else
	{
	    int current = Integer.parseInt (e.getActionCommand ());
	    if (screen == 5) //training mode screen
	    {
		if ((train [current / 8] [current % 8] / 10) % 10 == 1 || train [current / 8] [current % 8] % 10 == 3 || train [current / 8] [current % 8] % 10 == 5) //if you picked a black piece, or a piece to take or a selected piece, any other piece is invalid
		{
		    if (last == -1)
		    {
			last = current;
			Validmoves (train, train [last / 8] [last % 8], last / 8, last % 8); //calls valid moves for the piece
			for (int g = 0 ; g < 8 ; g++) //this loop resets 3rd digit to 5 if piece can be taken
			{
			    for (int f = 0 ; f < 8 ; f++)
			    {
				if (train [g] [f] / 100 > 0 && train [g] [f] % 10 == 3) //if you can take a piece the piece becomes red (+5)
				    train [g] [f] = train [g] [f] - (train [g] [f] % 10) + 5;
			    }
			}
			train [last / 8] [last % 8] = train [last / 8] [last % 8] - (train [last / 8] [last % 8] % 10) + 4; //MAKES THIRD DIGIT 4 SO THAT WHEN REDRAW IS CALLED THE SELECTED SQUARE IS GREEN TO SHOW THAT IT IS SELECTED
			redraw ();
		    }
		    else
		    {
			if (last == current) //if you chose to deselect your piece
			{
			    resetcolours (train);
			    redraw ();
			    last = -1;
			}
			else
			{
			    if (last == 22 && current == 28 && piecesmoved == 0) //if you moved to the right spot
			    {
				train [3] [4] = 311;
				train [2] [6] = 2;
				resetcolours (train);
				redraw ();
				progressBar.setString ("50%");
				try
				{
				    Thread.sleep (30);
				}
				catch (InterruptedException m)
				{
				    ;
				}
				train [4] [5] = 1;
				train [3] [4] = 121;
				redraw ();
				piecesmoved++;
			    }
			    else if (last == 15 && current == 31 && piecesmoved > 0) //if you moved to the right spot
			    {
				train [1] [7] = 2;
				train [3] [7] = 112;
				resetcolours (train);
				progressBar.setString ("100%");
				redraw ();
				try
				{
				    Thread.sleep (30);
				}
				catch (InterruptedException m)
				{
				    ;
				}
				JOptionPane.showMessageDialog (null, "Congratulations! Checkmate!\nTraining Complete!", "You Pass!",
					JOptionPane.INFORMATION_MESSAGE);
				piecesmoved = 0;

				for (int i = 0 ; i < row ; i++)
				    for (int j = 0 ; j < col ; j++)
					train [i] [j] = train2 [i] [j];
				redraw ();
				progressBar.setString ("0%");
				cdLayout.show (p_screen, "1");

			    }
			    else
			    {
				JOptionPane.showMessageDialog (null, "Sorry Wrong Move! It's black to move.\n Checkmate in Two!", "Incorrect Move",
					JOptionPane.ERROR_MESSAGE);
				resetcolours (train);
				redraw ();
				last = -1;
			    }
			    last = -1;
			}
		    }
		}
		else
		{
		    JOptionPane.showMessageDialog (null, "Sorry Wrong Move! It's black's turn!\nRemember to click on the blue squares.", "Incorrect Move",
			    JOptionPane.ERROR_MESSAGE);
		    resetcolours (train);
		    last = -1;
		    redraw ();
		}
	    }
	    else //screen 2 - Multiplayer
	    {
		if (last == -1)
		{
		    if ((move % 2 == 0 && ((boardpieces [current / 8] [current % 8] % 100) / 10) == 2) || (move % 2 == 1 && ((boardpieces [current / 8] [current % 8] % 100) / 10) == 1)) //checks if it's supposed to be white's turn then a white piece is clicked to move or if it's supposed to be black's turn a black piece is clicked
		    {
			last = current;
			num1 = last / 8;
			num2 = last % 8;
			swap1 = boardpieces [num1] [num2];
			third1 = swap1 % 10;
			toadd = swap1 - third1;
			Validmoves (boardpieces, toadd, num1, num2); //DETERMINES ALL VALID MOVE SQUARES
			boardpieces [num1] [num2] = toadd + 4; //MAKES THIRD DIGIT 4 SO THAT WHEN REDRAW IS CALLED THE SELECTED SQUARE IS GREEN TO SHOW THAT IT IS SELECTED
			//HAVE A LOOP THAT GOES THROUGH THE ARRAY AND CHECKS IF ANY PIECES CAN BE TAKEN AND MAKES THAT SQUARE RED
			for (int g = 0 ; g < 8 ; g++) //this loop resets 3rd digit to 1 or 2 resetting board colours before checking if the king is still in check allowing he king to move freely if it isn't in check
			{
			    for (int f = 0 ; f < 8 ; f++)
			    {

				if (boardpieces [g] [f] / 100 > 0 && boardpieces [g] [f] % 10 == 3)
				    boardpieces [g] [f] = (boardpieces [g] [f] - (boardpieces [g] [f] % 10)) + 5;
			    }
			}
			redraw ();
		    }
		    else
		    {
			if (move % 2 == 0) //if its whites turn and you chose a black piece
			{
			    JOptionPane.showMessageDialog (null, "It's White's Turn!", "Invalid Move",
				    JOptionPane.ERROR_MESSAGE);
			}
			else //if its blacks turn and you chose a white piece
			{
			    JOptionPane.showMessageDialog (null, "It's Black's Turn!", "Invalid Move",
				    JOptionPane.ERROR_MESSAGE);
			}
		    }
		}
		else
		{ //x/y values (num3, num4)
		    num3 = current / 8; //USED TO BE INSIDE FOLLOWING IF
		    num4 = current % 8; //USED TO BE INSIDE FOLLOWING IF
		    if (last == current)
		    {
			last = -1;
			resetcolours (boardpieces);
			redraw ();
		    }
		    else if ((boardpieces [num3] [num4] % 10) == 3 || (boardpieces [num3] [num4] % 10) == 5) //if you chose a valid square to move onto
		    {
			if (toadd / 100 == 1 && num3 == 0) //black promotion
			{
			    String[] possibleValues = {"Queen", "Rook", "Knight", "Bishop"};
			    String selectedValue = (String) JOptionPane.showInputDialog (null, "Promote to what?",
				    "Promotion", JOptionPane.INFORMATION_MESSAGE, null,
				    possibleValues, possibleValues [0]);
			    if (selectedValue.equals ("Queen"))
				toadd = 520;
			    else if (selectedValue.equals ("Rook"))
				toadd = 420;
			    else if (selectedValue.equals ("Knight"))
				toadd = 320;
			    else
				toadd = 220;
			}
			else if (toadd / 100 == 1 && num3 == 7) //white promotion
			{
			    String[] possibleValues = {"Queen", "Rook", "Knight", "Bishop"};
			    String selectedValue = (String) JOptionPane.showInputDialog (null, "Promote to what?",
				    "Promotion", JOptionPane.INFORMATION_MESSAGE, null,
				    possibleValues, possibleValues [0]);
			    if (selectedValue.equals ("Queen"))
				toadd = 510;
			    else if (selectedValue.equals ("Rook"))
				toadd = 410;
			    else if (selectedValue.equals ("Knight"))
				toadd = 310;
			    else
				toadd = 210;
			}
			swap2 = boardpieces [num3] [num4];
			third2 = swap2 % 10;
			toadd2 = swap2 - third2;
			boardpieces [num3] [num4] = toadd + third2;
			if (toadd == 0)
			    boardpieces [num1] [num2] = toadd2 + third1;
			else
			    boardpieces [num1] [num2] = third1;
			if (last == 60 && current == 62) //if white chose to kingside castle
			{
			    boardpieces [7] [5] = 422;
			    boardpieces [7] [7] = 002;
			    wrook2moved = 1;
			}
			else if (last == 60 && current == 58) //if white chose to queenside castle
			{
			    boardpieces [7] [3] = 422;
			    boardpieces [7] [0] = 001;
			    wrook1moved = 1;
			}
			else if (last == 4 && current == 6) //if black chose to kingside castle
			{
			    boardpieces [0] [5] = 411;
			    boardpieces [0] [7] = 001;
			    brook2moved = 1;
			}
			else if (last == 4 && current == 2) //if black chose to queenside castle
			{
			    boardpieces [0] [3] = 411;
			    boardpieces [0] [0] = 002;
			    brook1moved = 1;
			}
			else
			{
			    wqscastleavailable = 0;
			    wkscastleavailable = 0;
			    bqscastleavailable = 0;
			    bkscastleavailable = 0;
			}
			for (int i = 0 ; i < 8 ; i++)
			    for (int j = 0 ; j < 8 ; j++)
				boardpieces3 [i] [j] = boardpieces [i] [j]; //boardpieces3 holds boardpieces original value before checking for checkmate

			for (int i = 0 ; i < 8 ; i++)
			{
			    for (int j = 0 ; j < 8 ; j++)
			    {
				Validmoves (boardpieces, (boardpieces [i] [j] - (boardpieces [i] [j] % 10)), i, j); //called to see the moves for the piece from where it was moved in order to check for a "check"
				for (int g = 0 ; g < 8 ; g++) //this loop resets 3rd digit to 1 or 2 resetting board colours
				{
				    for (int f = 0 ; f < 8 ; f++)
				    {
					if (boardpieces [g] [f] / 100 == 6 && (boardpieces [g] [f] / 10) % 10 == 2 && boardpieces [g] [f] % 10 == 3) //if white king can be taken - it is a check on white
					    wcheck++;
					else if (boardpieces [g] [f] / 100 == 6 && (boardpieces [g] [f] / 10) % 10 == 1 && boardpieces [g] [f] % 10 == 3) //if black king can be taken - it is a check on black
					    bcheck++;
				    }
				}
				resetcolours (boardpieces);
			    }
			}

			if (bcheck > 0) //if black is in check
			{
			    bcheck = 0;
			    for (int i = 0 ; i < 8 ; i++)
			    {
				for (int j = 0 ; j < 8 ; j++)
				{
				    if ((boardpieces [i] [j] / 10) % 10 == 1) //black checkmate - white wins
				    {
					Validmoves (boardpieces, boardpieces [i] [j], i, j); //calls validmoves on all black

					for (int g = 0 ; g < 8 ; g++)
					{
					    for (int f = 0 ; f < 8 ; f++)
					    {
						if (boardpieces [g] [f] % 10 == 3)
						{
						    boardpieces [g] [f] = boardpieces [i] [j]; //switches pieces
						    boardpieces [i] [j] = 0;
						    resetcolours (boardpieces);
						    for (int a = 0 ; a < 8 ; a++)
						    {
							for (int b = 0 ; b < 8 ; b++)
							{
							    if ((boardpieces [a] [b] / 10) % 10 == 2 && boardpieces [a] [b] / 100 != 6) //calls valid moves on white pieces except for king
								Validmoves (boardpieces, (boardpieces [a] [b] - (boardpieces [a] [b] % 10)), a, b); //called to see the moves for the piece from where it was moved in order to check for a "check"
							}
						    }
						    for (int a = 0 ; a < 8 ; a++)
						    {
							for (int b = 0 ; b < 8 ; b++)
							{
							    if (boardpieces [a] [b] == 611 || boardpieces [a] [b] == 612) //checks if the king can escape if it can't be taken
								bcheck++;
							}
						    }
						    for (int k = 0 ; k < 8 ; k++)
						    {
							for (int l = 0 ; l < 8 ; l++)
							{
							    boardpieces [k] [l] = boardpieces3 [k] [l]; //boardpieces3 holds boardpieces original value before checking for checkmate
							}
						    }
						    resetcolours (boardpieces);
						}
						Validmoves (boardpieces3, boardpieces3 [i] [j], i, j);
					    }
					}
					resetcolours (boardpieces);
				    }
				}
			    }
			    if (bcheck == 0) //if black king can't be protected
			    {
				redraw ();
				JOptionPane.showMessageDialog (null, "Black Checkmate! White Wins!", "White Win!!!",
					JOptionPane.INFORMATION_MESSAGE);
				checkmate++;
				resetboard ();
				cdLayout.show (p_screen, "1");
			    }
			}
			if (wcheck > 0) //same comments as black checmate
			{
			    wcheck = 0;
			    for (int i = 0 ; i < 8 ; i++)
			    {
				for (int j = 0 ; j < 8 ; j++)
				{
				    if ((boardpieces [i] [j] / 10) % 10 == 2) //white checkmate - black wins
				    {
					Validmoves (boardpieces, boardpieces [i] [j], i, j); //called to see the moves for the piece from where it was moved in order to check for a "check"

					for (int g = 0 ; g < 8 ; g++)
					{
					    for (int f = 0 ; f < 8 ; f++)
					    {
						if (boardpieces [g] [f] % 10 == 3)
						{
						    boardpieces [g] [f] = boardpieces [i] [j];
						    boardpieces [i] [j] = 0;
						    resetcolours (boardpieces);
						    for (int a = 0 ; a < 8 ; a++)
						    {
							for (int b = 0 ; b < 8 ; b++)
							{
							    if ((boardpieces [a] [b] / 10) % 10 == 1 && boardpieces [a] [b] / 100 != 6)
								Validmoves (boardpieces, (boardpieces [a] [b] - (boardpieces [a] [b] % 10)), a, b); //called to see the moves for the piece from where it was moved in order to check for a "check"
							}
						    }
						    for (int a = 0 ; a < 8 ; a++)
						    {
							for (int b = 0 ; b < 8 ; b++)
							{
							    if (boardpieces [a] [b] == 621 || boardpieces [a] [b] == 622)
								wcheck++;
							}
						    }
						    for (int k = 0 ; k < 8 ; k++)
						    {
							for (int l = 0 ; l < 8 ; l++)
							{
							    boardpieces [k] [l] = boardpieces3 [k] [l]; //boardpieces3 holds boardpieces original value before checking for checkmate
							}
						    }
						    resetcolours (boardpieces);
						}
						Validmoves (boardpieces3, boardpieces3 [i] [j], i, j);
					    }
					}
					resetcolours (boardpieces);
				    }
				}
			    }
			    if (wcheck == 0)//if white king can't be protected
			    {
				redraw ();
				JOptionPane.showMessageDialog (null, "White Checkmate! Black Wins!", "Black Win!!!",
					JOptionPane.INFORMATION_MESSAGE);
				checkmate++;
				resetboard ();
				cdLayout.show (p_screen, "1");
			    }
			}
			wcheck = 0;
			bcheck = 0;
			if (checkmate == 0)
			{
			    /*board [current].setIcon (createImageIcon (whichimage (boardpieces [num3] [num4]) + ".jpg"));//instead of this we can call redraw
			    board[last].setIcon(createImageIcon(whichimage(boardpieces [num1] [num2]) + ".jpg"));//instead of this we can call redraw
			    */
			    //HAVE A LOOP THAT GOES THROUGH EACH NUMBER IN BOARDPIECES ARRAY AND CHANGES THE THIRD DIGIT BACK TO A 1 OR 2 DEPENDING ON ODD OR EVEN
			    resetcolours (boardpieces);
			    for (int i = 0 ; i < 8 ; i++)
			    {
				for (int j = 0 ; j < 8 ; j++)
				{
				    Validmoves (boardpieces, (boardpieces [i] [j] - (boardpieces [i] [j] % 10)), i, j); //called to see the moves for all the pieces in order to check for a "check"
				    for (int g = 0 ; g < 8 ; g++) //this loop resets 3rd digit to 1 or 2 resetting board colours
				    {
					for (int f = 0 ; f < 8 ; f++)
					{
					    if (move % 2 == 0 && boardpieces [g] [f] / 100 == 6 && ((boardpieces [g] [f] / 10) % 10) == 2 && boardpieces [g] [f] % 10 == 3) //if king can be taken - it is a check
					    {
						boardpieces [num1] [num2] = swap1;
						boardpieces [num3] [num4] = swap2;
						incheck++;
						move--;
						JOptionPane.showMessageDialog (null, "    You'll be in check!\nYou can't move there.", "Invalid Move",
							JOptionPane.ERROR_MESSAGE);

					    }
					    else if (move % 2 == 1 && boardpieces [g] [f] / 100 == 6 && ((boardpieces [g] [f] / 10) % 10) == 1 && boardpieces [g] [f] % 10 == 3) //if king can be taken - it is a check
					    {
						boardpieces [num1] [num2] = swap1;
						boardpieces [num3] [num4] = swap2;
						incheck++;
						move--;
						JOptionPane.showMessageDialog (null, "    You'll be in check!\nYou can't move there.", "Invalid Move",
							JOptionPane.ERROR_MESSAGE);
					    }
					}
				    }
				    resetcolours (boardpieces);
				}
			    }
			    for (int i = 0 ; i < 8 ; i++)
			    {
				for (int j = 0 ; j < 8 ; j++)
				{
				    Validmoves (boardpieces, (boardpieces [i] [j] - (boardpieces [i] [j] % 10)), i, j); //called to see the moves for the piece from where it was moved in order to check for a "check"
				    for (int g = 0 ; g < 8 ; g++) //this loop resets 3rd digit to 1 or 2 resetting board colours
				    {
					for (int f = 0 ; f < 8 ; f++)
					{
					    if (incheck == 0 && boardpieces [g] [f] / 100 == 6 && (boardpieces [g] [f] / 10) % 10 == 2 && boardpieces [g] [f] % 10 == 3) //if white king can be taken - it is a check on white
						wcheck++;
					    else if (incheck == 0 && boardpieces [g] [f] / 100 == 6 && (boardpieces [g] [f] / 10) % 10 == 1 && boardpieces [g] [f] % 10 == 3) //if black king can be taken - it is a check on black
						bcheck++;
					}
				    }
				    resetcolours (boardpieces);
				}
			    }
			    move++;
			    redraw ();
			    if (wcheck > 0)
			    {
				JOptionPane.showMessageDialog (null, wcheck + " check(s)!\n White is in check!", "Check!",
					JOptionPane.INFORMATION_MESSAGE);
				wcheck = 0;
			    }
			    else if (bcheck > 0)
			    {
				JOptionPane.showMessageDialog (null, bcheck + " check(s)!\n Black is in check!", "Check!",
					JOptionPane.INFORMATION_MESSAGE);
				bcheck = 0;
			    }
			    if (last == 60 && current != 60)
				wkingmoved = 1;
			    else if (last == 56 && current != 56)
				wrook1moved = 1;
			    else if (last == 63 && current != 63)
				wrook2moved = 1;
			    else if (last == 4 && current != 4)
				bkingmoved = 1;
			    else if (last == 0 && current != 0)
				brook1moved = 1;
			    else if (last == 7 && current != 7)
				brook2moved = 1;

			    if (incheck == 0)
			    {
				moves [num] = (num1 * 10000) + (num2 * 1000) + swap1;
				moves [num + 1] = (num3 * 10000) + (num4 * 1000) + swap2;
				num += 2;
				if (swap2 / 100 == 1 && (swap2 / 10) % 10 == 1)
				{
				    bpcaptnum++;
				    bpcapt.setText ("x" + bpcaptnum);
				}
				else if (swap2 / 100 == 1 && (swap2 / 10) % 10 == 2)
				{
				    wpcaptnum++;
				    wpcapt.setText ("x" + wpcaptnum);
				}
				else if (swap2 / 100 == 2 && (swap2 / 10) % 10 == 1)
				{
				    bbcaptnum++;
				    bbcapt.setText ("x" + bbcaptnum);
				}
				else if (swap2 / 100 == 2 && (swap2 / 10) % 10 == 2)
				{
				    wbcaptnum++;
				    wbcapt.setText ("x" + wbcaptnum);
				}
				else if (swap2 / 100 == 3 && (swap2 / 10) % 10 == 1)
				{
				    bkcaptnum++;
				    bkcapt.setText ("x" + bkcaptnum);
				}
				else if (swap2 / 100 == 3 && (swap2 / 10) % 10 == 2)
				{
				    wkcaptnum++;
				    wkcapt.setText ("x" + wkcaptnum);
				}
				else if (swap2 / 100 == 4 && (swap2 / 10) % 10 == 1)
				{
				    brcaptnum++;
				    brcapt.setText ("x" + brcaptnum);
				}
				else if (swap2 / 100 == 4 && (swap2 / 10) % 10 == 2)
				{
				    wrcaptnum++;
				    wrcapt.setText ("x" + wrcaptnum);
				}
				else if (swap2 / 100 == 5 && (swap2 / 10) % 10 == 1)
				{
				    bqcaptnum++;
				    bqcapt.setText ("x" + bqcaptnum);
				}
				else if (swap2 / 100 == 5 && (swap2 / 10) % 10 == 2)
				{
				    wqcaptnum++;
				    wqcapt.setText ("x" + wqcaptnum);
				}
			    }
			    if (move % 2 == 0)
				showStatus ("White's Turn");
			    else
				showStatus ("Black's Turn");
			    incheck = 0;
			    last = -1;
			    undo.setEnabled (true);
			}

		    }
		    else
		    {
			//errormessage (last, current)- do this maybe if there is time to code all possible errors
			last = -1;
			resetcolours (boardpieces);
			redraw ();
			JOptionPane.showMessageDialog (null, "         You can't move there.\n Move only on the blue squares.", "Invalid Move",
				JOptionPane.ERROR_MESSAGE);
		    }
		}
	    }
	}
    }


    protected static ImageIcon createImageIcon (String path)
    {
	java.net.URL imgURL = Chess.class.getResource (path);
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


