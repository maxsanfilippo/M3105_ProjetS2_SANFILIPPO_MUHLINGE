package fallen_heroes;
import java.util.LinkedList;

public class Board
{

	public final static int MAXIMAL_INVOCATION_NUMBER = 5;

	private int numberOfLaidInvocations;
	
	private LinkedList<Invocation> boardCards;

	public Board()
	{
		this.numberOfLaidInvocations = 0;

		
		this.boardCards = new LinkedList<Invocation>();
	}
	

	public int getInvocationsNumber()
	{
		return this.numberOfLaidInvocations;
	}
	
	public int getInvocationNumber()
	{
		return this.numberOfLaidInvocations;
	}
	

	public Invocation getInvocation(int p_invocationIndex)
	{
		if (p_invocationIndex >= 0 && p_invocationIndex < this.numberOfLaidInvocations)	return this.boardCards.get(p_invocationIndex);
		
		return null;

	}

	public void addInvocationOnBoard(Invocation invocationToPlay)
	{
		if (this.numberOfLaidInvocations < MAXIMAL_INVOCATION_NUMBER)
		{
			this.boardCards.add(invocationToPlay);
			this.numberOfLaidInvocations++;
		}
	}
	
	public void removeInvocationFromBoard(Invocation p_invocationToRemove)
	{
		this.boardCards.remove(p_invocationToRemove);
		this.numberOfLaidInvocations--;
	}
	
	
	public boolean thereIsAtLeastOneAvantGardeInvocationOnTheBoard()
	{
		
		for(Invocation myCard : this.boardCards)
		{
			if (myCard.returnCapacity().equals("avant-garde"))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public String returnTextRepresentationOfTheBoard(boolean afficherOutline)
	{
		String Returning = "";
		if (afficherOutline) Returning = "************************************* Le terrain *************************************\n";
		
		int cardNumber = 1;
		for(Invocation myCard : this.boardCards)
		{
			Returning += "\tCarte numéro : " + cardNumber + "\n" + myCard.getCardRepresentation(false) + "\n";
			cardNumber++;
		}


		
		if (afficherOutline) Returning += "**************************************************************************************\n";
		return Returning;

	}
	
}
