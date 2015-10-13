package fallen_heroes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class CardFileReader
{
	private LinkedList<Card> cardList;
	
	private boolean deckChoice;
	
	public CardFileReader(boolean p_deckChoice)
	{
		this.cardList =  new LinkedList<Card>();
		this.deckChoice = p_deckChoice;
	}
	
	
	public LinkedList<Card> returnCardList() throws IOException
	{
		String fileName = "";
		
		if (deckChoice == true)
		{
			fileName = "deck_light.txt";
		}
		else
		{
			fileName = "deck_dark.txt";
		}

		BufferedReader entry = new BufferedReader (new FileReader (fileName)) ;
		
		

		String fileContent = entry.readLine() ;
		if (fileContent != null)
		{
			StringTokenizer tok = new StringTokenizer (fileContent, "*") ;
			
				
			int starsNumber = tok.countTokens() ;
			for (int i=0 ; i<starsNumber ; i++)
			{ 
					
				StringTokenizer tok2 = new StringTokenizer (tok.nextToken(), "%") ;
					
					
				int type = Integer.parseInt(tok2.nextToken());
				String name = tok2.nextToken();
				String path = tok2.nextToken();
				String description = tok2.nextToken();
				int manaCost = Integer.parseInt(tok2.nextToken());
				int spellEffect = Integer.parseInt(tok2.nextToken());
				int attackPoints = Integer.parseInt(tok2.nextToken());
				int defensePoints = Integer.parseInt(tok2.nextToken());
				String capacity = tok2.nextToken();
				int criDeGuerreEffect = Integer.parseInt(tok2.nextToken());
				String criDeGuerreTarget = tok2.nextToken();
				String spellTarget = tok2.nextToken();
				String conferationEffect = tok2.nextToken();
	
					
				if (type == 0)
				{
					Card newCard = new Invocation(name, path, description, manaCost, attackPoints, defensePoints, capacity, criDeGuerreEffect, criDeGuerreTarget, conferationEffect);
					this.cardList.add(newCard);
				}
				else
				{
					Card newCard = new Spell(name, path, description, manaCost, spellEffect, spellTarget);
					this.cardList.add(newCard);
				}
			}
			
		}
			
			
		
		
		Collections.shuffle(this.cardList);
		entry.close();
		
		return this.cardList;
	}
}
