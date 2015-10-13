package fallen_heroes;
import java.io.IOException;
import java.util.LinkedList;


public class Deck
{

	private LinkedList<Card> deck;
	
	private int numberOfCards;

	public Deck(LinkedList<Card> p_cardList) throws IOException
	{
		this.deck = p_cardList;
		this.numberOfCards = this.deck.size();
	}

	public Card retrieveHeadCardOfTheDeck()
	{
		if (this.numberOfCards > 0)
		{
			this.numberOfCards--;
			Card cardToReturn = this.deck.get(this.numberOfCards);
			this.deck.remove(this.numberOfCards);
			return cardToReturn;
		}
	
		
		return null;

	}
	
	public String toString()
	{
		String toReturn = "";
		
		for(Card myCard : this.deck)
			toReturn += myCard + "\n";
			
		return toReturn;
	}
}
