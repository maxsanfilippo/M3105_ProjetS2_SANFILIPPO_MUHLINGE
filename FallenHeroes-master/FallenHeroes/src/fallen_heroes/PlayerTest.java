package fallen_heroes;

import junit.framework.*;

public class PlayerTest extends TestCase
{

	private Player player;
        
	private Player opponent;
        
        
	public PlayerTest (String name)
        
	{
		super(name);    
	}
        
        
	protected void setUp()
    {
		player = new Player(false, null, null);
		opponent = new Player(false, null, null);
		player.defineOpponent(opponent);
    }
       
    protected void tearDown()
    {
    	player = null;
        opponent = null;
    }
        
    public void testPlayerLife() throws UncorrectPlayerException
    {
    	assertEquals(player.getLife() == 20, true);
    	assertEquals(player.getLife() != 20, false);
    	player.addLifePoints(3);
    	assertEquals(player.getLife() == 20, true);
    	assertEquals(player.getLife() != 20, false);
               
    }
        
    public void testPlayerMana() throws UncorrectPlayerException
    {
    	assertEquals(player.getMana() == 5, true);
    	assertEquals(player.getMana() != 5, false);
    	
    }
        
 
    public void testOpponentLife() throws UncorrectPlayerException
    {
    	assertEquals(opponent.getLife() == 20, true);
    	assertEquals(opponent.getLife() != 20, false);
    	player.changeOpponentLife(3);
    	assertEquals(opponent.getLife() == 17, true);
    	assertEquals(opponent.getLife() != 17, false);
    }
        
    public void testPlayerCardsNumber() throws UncorrectPlayerException
    {
    	assertEquals(player.getNumberOfCardsInTheHand() == 3, true);
    	assertEquals(player.getNumberOfCardsInTheHand() != 3, false);
    	player.drawACard();
    	assertEquals(player.getNumberOfCardsInTheHand() == 4, true);
    	assertEquals(player.getNumberOfCardsInTheHand() != 4, false);
    	player.drawACard();
    	assertEquals(player.getNumberOfCardsInTheHand() == 5, true);
    	assertEquals(player.getNumberOfCardsInTheHand() != 5, false);
    	player.drawACard();
    	assertEquals(player.getNumberOfCardsInTheHand() == 5, true);
    	assertEquals(player.getNumberOfCardsInTheHand() != 5, false);
    	
    }
        
    public static Test suite()
    {
    	TestSuite suite = new TestSuite();
    	suite.addTest(new PlayerTest("testPlayerLife"));
    	suite.addTest(new PlayerTest("testPlayerMana"));
    	suite.addTest(new PlayerTest("testOpponentLife"));
    	suite.addTest(new PlayerTest("testPlayerCardsNumber"));
    	return suite;
    }
        
        
}
