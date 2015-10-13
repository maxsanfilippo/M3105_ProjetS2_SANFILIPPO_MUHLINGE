package fallen_heroes;

import javax.swing.SwingUtilities;

import junit.framework.*;

public class GUIandConsoleLauncher
{

	public static void main(String[] args)
	{

		new Audio(1).start();
		GUI gui = new GUI();

		SwingUtilities.invokeLater(gui);
		Game newGame = new Game(gui,gui,new PlayerEntryConsole(), new PlayerDisplayConsole());

		newGame.play();

	}

}
