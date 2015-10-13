package fallen_heroes;


import javax.swing.*;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.*;


/**
 * The big card which will display a bigger image of cards when mouse will be hover
 * @author chamboug
 *
 */
public class GraphicalBigCard extends JButton
{
	/**
	 * Big card width in pixel
	 */
	public final static int BIG_CARD_WIDTH = 237;
	/**
	 * Big card height in pixel
	 */
	public final static int BIG_CARD_HEIGHT = 329;
	
	/**
	 * Current card associted to the button
	 */
	private Card currentCard;
	
	/**
	 * Initialize the big card by locating it on the side and set this associated card to null
	 */
	public GraphicalBigCard()
	{
		super();
		this.currentCard = null;

		this.setBounds(3, 189, BIG_CARD_WIDTH, BIG_CARD_HEIGHT);
		this.setBorder(BorderFactory.createEmptyBorder());
		
		this.setOpaque(false);
		this.setBackground(new Color(0,true));
	}
	
	/**
	 * Associate a card to this button
	 * @param p_card : the card to associate
	 */
	public void associateCard(Card p_card)
	{
		this.currentCard = p_card;
		
		String path = "";
		
		if (this.currentCard == null)
			path = "images/dos_carte.png";
		else
			path = "images/" + this.currentCard.getImagePath() + ".png";
		
		

		ImageIcon icon  = new ImageIcon(path);
	    Image image = icon.getImage();
	    image = image.getScaledInstance(GraphicalBigCard.BIG_CARD_WIDTH,GraphicalBigCard.BIG_CARD_HEIGHT, Image.SCALE_SMOOTH);
	    icon.setImage(image);
	    
		this.setIcon(icon);
	}
}
