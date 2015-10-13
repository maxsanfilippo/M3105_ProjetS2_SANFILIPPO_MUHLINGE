package fallen_heroes;
import java.io.IOException;
import java.util.LinkedList;

/**
 * The class that designates the library of cards
 * @author Group 6
 */
public class Deck
{
	/**
	 * The deck is a linked list composed of cards
	 */
	private LinkedList<Card> deck;
	

	
	/**
	 * Number of cards in the deck
	 */
	private int numberOfCards;
	
	/**
	 * Initializes the deck with the selected parameter. We have the choice between two decks. 
	 * Each card is read and recognized according delimiters which are define here.
	 * @param deckChoice Deck "light" or Deck "Dark"
	 * @throws IOException If file is not found
	 */
	public Deck(LinkedList<Card> p_cardList) throws IOException
	{
		this.deck = p_cardList;
		this.numberOfCards = this.deck.size();
	}
	
	/**
	 * Draw a card
	 * @return the following card of the deck
	 */
	public Card retrieveHeadCard()
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
