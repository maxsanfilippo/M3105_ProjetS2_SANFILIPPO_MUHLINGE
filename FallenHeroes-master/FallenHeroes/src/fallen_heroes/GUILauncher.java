package fallen_heroes;

import javax.swing.SwingUtilities;

/**
 * This is the application class that allows us to start the game
 * @author Group 6
 */
public class GUILauncher
{

	/**
	 * This is the main program
	 * @param args
	 */
	public static void main(String[] args)
	{	

		new Audio(1).start();
		GUI gui = new GUI();

		SwingUtilities.invokeLater(gui);
//		Game newGame = new Game(gui,gui,new PlayerEntryConsole(), new PlayerDisplayConsole());
		Game newGame = new Game(gui,gui,gui,gui);
		newGame.play();
	}

}
