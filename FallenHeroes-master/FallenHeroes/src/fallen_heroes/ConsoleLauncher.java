package fallen_heroes;

import junit.framework.Test;
import junit.framework.TestSuite;

public class ConsoleLauncher
{


	public static Test suite()
    {
            TestSuite suite = new TestSuite();
            suite.addTest(PlayerTest.suite());
            suite.addTest(BoardTest.suite());
            return suite;
    }
	
	
	public static void main(String[] args)
	{	
		new Audio(1).start();
		new Game(new PlayerEntryConsole(), new PlayerDisplayConsole(), new PlayerEntryConsole(), new PlayerDisplayConsole()).play();
		
	}

}
