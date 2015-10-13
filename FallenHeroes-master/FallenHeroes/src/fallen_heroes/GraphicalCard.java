package fallen_heroes;

import java.awt.Color;
import java.awt.Image;

import javax.swing.*;

public class GraphicalCard extends JButton
{

	public final static int CARD_WIDTH = 175;

	public final static int CARD_HEIGHT = 233;
	
	private JLabel labelAttack;
	
	private JLabel labelLife;
	

	private Card associatedCard;

	public GraphicalCard(int x, int y, String p_name)
	{
		super();
		this.associatedCard = null;

		this.setBounds(x, y, CARD_WIDTH, CARD_HEIGHT);
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setName(p_name);
		
		this.labelAttack = new JLabel();
		
		
		this.labelAttack = new JLabel();
		this.add(this.labelAttack);
		this.setLayout(null);
		this.labelAttack.setBounds(14, 163,100,100);
		this.labelAttack.setForeground(Color.WHITE);
		this.labelAttack.setFont(this.labelAttack.getFont().deriveFont (18.0f));
		
		this.labelLife = new JLabel();
		this.add(this.labelLife);
		this.setLayout(null);
		this.labelLife.setBounds(151, 163,100,100);
		this.labelLife.setForeground(Color.WHITE);
		this.labelLife.setFont(this.labelLife.getFont().deriveFont (18.0f));
		
		
		this.setOpaque(false);
		this.setBackground(new Color(0,true));
	}
	
	public void associateCard(Card p_card)
	{
		this.associatedCard = p_card;
		ImageIcon icon = null;
		
		if (this.associatedCard != null)
			icon  = new ImageIcon("images/" + this.associatedCard.getImagePath() + ".png");
		else
			icon  = new ImageIcon("images/dos_carte.png");
			

		
		Image image = icon.getImage();
		image = image.getScaledInstance(GraphicalCard.CARD_WIDTH,GraphicalCard.CARD_HEIGHT, Image.SCALE_SMOOTH);
		icon.setImage(image);
		this.setIcon(icon);

	}
	
	public void refreshLabels()
	{
		if (this.associatedCard instanceof Invocation)
		{
			this.labelAttack.setText(Integer.toString(((Invocation)this.associatedCard).getAttackPoints()));
			this.labelLife.setText(Integer.toString(((Invocation)this.associatedCard).getCurrentDefensePoints()));
		}
		else
		{
			this.labelAttack.setText("");
			this.labelLife.setText("");
		}

	}
	
	public Card getCard()
	{
		return this.associatedCard;
	}

	


}
