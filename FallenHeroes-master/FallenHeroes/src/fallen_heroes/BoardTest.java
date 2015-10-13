package fallen_heroes;

import junit.framework.*;

public class BoardTest extends TestCase
{
	private Board board;
        
	public BoardTest (String name)
	{
		super(name);
	}
        
	protected void setUp()
	{
		board = new Board();
	}
        
	protected void tearDown()
	{
		board = null;  
	}

        
	public void testBoardLaidInvocationNumber()
	{
		assertEquals(board.getInvocationsNumber() == 0, true);
		assertEquals(board.getInvocationsNumber() != 0, false);
		board.addInvocationOnBoard(null);
		assertEquals(board.getInvocationsNumber() == 1, true);
		assertEquals(board.getInvocationsNumber() != 1, false);
		board.addInvocationOnBoard(null);
		board.addInvocationOnBoard(null);
		board.addInvocationOnBoard(null);
		board.addInvocationOnBoard(null);
		assertEquals(board.getInvocationsNumber() == 5, true);
		assertEquals(board.getInvocationsNumber() != 5, false);
		board.addInvocationOnBoard(null);
		assertEquals(board.getInvocationsNumber() == 5, true);
		assertEquals(board.getInvocationsNumber() != 5, false);
	}
        
	public static Test suite()
	{
		TestSuite suite = new TestSuite();
		suite.addTest(new BoardTest("testBoardLaidInvocationNumber"));
		return suite;
	}

}
