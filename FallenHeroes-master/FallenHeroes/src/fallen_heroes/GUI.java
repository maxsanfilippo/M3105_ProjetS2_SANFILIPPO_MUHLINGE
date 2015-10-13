package fallen_heroes;


import java.awt.Color;
import java.awt.Image;
import java.awt.event.*;

import javax.swing.*;

public class GUI extends JFrame implements MouseListener, PlayerDisplayInterface, PlayerEntryInterface, Runnable
{
	private static final int CARD_X_ALIGNMENT = 243;

	private GraphicalCard[] cardsInHandOfCurrentPlayer;

	private GraphicalCard[] cardsOnBoardOfCurrentPlayer;

	private GraphicalCard[] cardsInHandOfCurrentPlayerOpponent;
	
	private GraphicalCard[] cardsOnBoardOfCurrentPlayerOpponent;
	
	private GraphicalBigCard bigCardOnTheSide;
	
	private JLabel labelMana;

	private JLabel labelAttackCard;

	private JLabel labelLifeCard;

	private JProgressBar progressBarOpponentLife;
	
	private JProgressBar progressBarCurrentPlayerLife;

	private BackgroundPanel backgroundPanel;

	private JButton nextTurn;

	private Player currentPlayer;
	
	private JLabel[] labelCurrentPlayerMana;
	
	private volatile boolean isActionSelected;
	
	private int actionSelected;
	
	private Card lastCardClicked;

	public GUI()
	{
		
		//Creation of the background panel
		this.backgroundPanel = new BackgroundPanel();
		
			    
		setTitle("Fallen Heroes");
		setSize(1366,728);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    //Creation of the big card on the side
	    this.bigCardOnTheSide = new GraphicalBigCard();
	    this.bigCardOnTheSide.associateCard(null);
	    this.backgroundPanel.add(this.bigCardOnTheSide);

		
	    //Creating and disposing cards of current player hand
		this.cardsInHandOfCurrentPlayer = new GraphicalCard[Hand.MAXIMAL_CARDS_NUMBER_IN_HAND];
		for (int currentCardNumber = 0; currentCardNumber < Hand.MAXIMAL_CARDS_NUMBER_IN_HAND; currentCardNumber++)
		{
			this.cardsInHandOfCurrentPlayer[currentCardNumber] = new GraphicalCard(CARD_X_ALIGNMENT + (GraphicalCard.CARD_WIDTH + 5) * currentCardNumber,590, "main1" + (currentCardNumber+1));
			this.cardsInHandOfCurrentPlayer[currentCardNumber].setVisible(false);
			this.backgroundPanel.add(this.cardsInHandOfCurrentPlayer[currentCardNumber]);
			this.cardsInHandOfCurrentPlayer[currentCardNumber].addMouseListener(this);
		}
		
		//Creating and disposing cards of current player opponent hand
		this.cardsInHandOfCurrentPlayerOpponent = new GraphicalCard[Hand.MAXIMAL_CARDS_NUMBER_IN_HAND];
		for (int currentCardNumber = 0; currentCardNumber < Hand.MAXIMAL_CARDS_NUMBER_IN_HAND; currentCardNumber++)
		{
			this.cardsInHandOfCurrentPlayerOpponent[currentCardNumber] = new GraphicalCard(CARD_X_ALIGNMENT + (GraphicalCard.CARD_WIDTH + 5) * currentCardNumber, -GraphicalCard.CARD_HEIGHT + 84, "main2" + (currentCardNumber+1));
			this.cardsInHandOfCurrentPlayerOpponent[currentCardNumber].setVisible(false);
			this.backgroundPanel.add(this.cardsInHandOfCurrentPlayerOpponent[currentCardNumber]);
			this.cardsInHandOfCurrentPlayerOpponent[currentCardNumber].addMouseListener(this);
		}
		

		//Creating and disposing cards of current player board
		this.cardsOnBoardOfCurrentPlayer = new GraphicalCard[Board.MAXIMAL_INVOCATION_NUMBER];
		for (int currentCardNumber = 0; currentCardNumber < Board.MAXIMAL_INVOCATION_NUMBER; currentCardNumber++)
		{
			this.cardsOnBoardOfCurrentPlayer[currentCardNumber] = new GraphicalCard(CARD_X_ALIGNMENT + (GraphicalCard.CARD_WIDTH + 5) * currentCardNumber,335, "terrain1" + (currentCardNumber+1));
			this.cardsOnBoardOfCurrentPlayer[currentCardNumber].setVisible(false);
			this.backgroundPanel.add(this.cardsOnBoardOfCurrentPlayer[currentCardNumber]);
			this.cardsOnBoardOfCurrentPlayer[currentCardNumber].addMouseListener(this);
		}
		
		//Creating and disposing cards of current player opponent board
		this.cardsOnBoardOfCurrentPlayerOpponent = new GraphicalCard[Board.MAXIMAL_INVOCATION_NUMBER];
		for (int currentCardNumber = 0; currentCardNumber < Board.MAXIMAL_INVOCATION_NUMBER; currentCardNumber++)
		{
			this.cardsOnBoardOfCurrentPlayerOpponent[currentCardNumber] = new GraphicalCard(CARD_X_ALIGNMENT + (GraphicalCard.CARD_WIDTH + 5) * currentCardNumber,86, "terrain2" + (currentCardNumber+1));
			this.cardsOnBoardOfCurrentPlayerOpponent[currentCardNumber].setVisible(false);
			this.backgroundPanel.add(this.cardsOnBoardOfCurrentPlayerOpponent[currentCardNumber]);
			this.cardsOnBoardOfCurrentPlayerOpponent[currentCardNumber].addMouseListener(this);
		}
		
		
		
		this.backgroundPanel.setLayout(null);
		
		//Disposing button "nextTurn"
		this.nextTurn = new JButton();				
		this.nextTurn.setBorder(BorderFactory.createEmptyBorder());
		this.nextTurn.setBounds(1170, 550, 150, 120);

		ImageIcon icon = new ImageIcon("images/bouton.png");
		Image image = icon.getImage();
		image = image.getScaledInstance(150, 120, Image.SCALE_SMOOTH);
		icon.setImage(image);
		this.nextTurn.setIcon(icon);

		this.nextTurn.setFocusable(false);
		this.backgroundPanel.add(this.nextTurn);
		this.nextTurn.setOpaque(false);
		this.nextTurn.setBackground(new Color(0,true));
		this.nextTurn.setContentAreaFilled(false);

		this.currentPlayer = null;
		
		
		this.labelMana = new JLabel("Mana");
		this.labelMana.setVisible(false);
		this.backgroundPanel.add(this.labelMana);		
		this.labelMana.setBounds(80, 560,300,100);
		this.labelMana.setForeground(Color.WHITE);
		this.labelMana.setFont(this.labelMana.getFont().deriveFont (32.0f));
		

		
		this.labelAttackCard = new JLabel();
		this.bigCardOnTheSide.add(this.labelAttackCard);
		this.bigCardOnTheSide.setLayout(null);
		this.labelAttackCard.setBounds(16, 248,300,100);
		this.labelAttackCard.setForeground(Color.WHITE);
		this.labelAttackCard.setFont(this.labelAttackCard.getFont().deriveFont (22.0f));
		
		this.labelLifeCard = new JLabel();
		this.bigCardOnTheSide.add(this.labelLifeCard);
		this.bigCardOnTheSide.setLayout(null);
		this.labelLifeCard.setBounds(205, 248,300,100);
		this.labelLifeCard.setForeground(Color.WHITE);
		this.labelLifeCard.setFont(this.labelLifeCard.getFont().deriveFont (22.0f));
		
		
		
		this.setLocationRelativeTo(null);
		
		this.setResizable(false);
		this.actionSelected = 0;
		this.lastCardClicked = null;
		
		
		this.progressBarOpponentLife = new JProgressBar(0, 20);
		this.progressBarOpponentLife.setValue(20);
		this.progressBarOpponentLife.setBounds(5,130,235,30);
		this.progressBarOpponentLife.setFont(this.progressBarOpponentLife.getFont().deriveFont (22.0f));
		this.progressBarOpponentLife.setStringPainted(true);
		this.progressBarOpponentLife.setVisible(false);
		this.backgroundPanel.add(this.progressBarOpponentLife);
		
		
		this.progressBarCurrentPlayerLife = new JProgressBar(0, 20);
		this.progressBarCurrentPlayerLife.setValue(20);
		this.progressBarCurrentPlayerLife.setBounds(5,546,235,30);
		this.progressBarCurrentPlayerLife.setFont(this.progressBarCurrentPlayerLife.getFont().deriveFont (22.0f));
		this.progressBarCurrentPlayerLife.setStringPainted(true);
		this.progressBarCurrentPlayerLife.setVisible(false);
		this.backgroundPanel.add(this.progressBarCurrentPlayerLife);
		
		
		this.labelCurrentPlayerMana = new JLabel[Player.MAXIMAL_MANA_NUMBER];

		for (int currentLabel = 0; currentLabel < Player.MAXIMAL_MANA_NUMBER; currentLabel++)
		{
			this.labelCurrentPlayerMana[currentLabel] = new JLabel();
			this.labelCurrentPlayerMana[currentLabel].setBounds(10 + currentLabel * 23,632,30,30);
			
			icon = new ImageIcon("images/no_mana.png");
			image = icon.getImage();
			image = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			icon.setImage(image);
			this.labelCurrentPlayerMana[currentLabel].setIcon(icon);
			
			this.labelCurrentPlayerMana[currentLabel].setVisible(false);
			this.backgroundPanel.add(this.labelCurrentPlayerMana[currentLabel]);

		}
		
		
		this.setContentPane(this.backgroundPanel);
	}
	
	public void setCurrentPlayerAndRefresh(Player p_player)
	{
		this.currentPlayer = p_player;
		this.refreshFullDisplay();
	}
	
	@Override
	public void run()
	{
		this.setVisible(true);
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
	
		for (int currentCardNumber = 0; currentCardNumber < Hand.MAXIMAL_CARDS_NUMBER_IN_HAND; currentCardNumber++)
		{
			if (e.getSource() == this.cardsInHandOfCurrentPlayer[currentCardNumber])
			{
				this.lastCardClicked = this.cardsInHandOfCurrentPlayer[currentCardNumber].getCard();
				
				if (this.lastCardClicked instanceof Invocation)
					this.actionSelected = 3;
				else if (this.lastCardClicked instanceof Spell)
					this.actionSelected = 4;
				
				this.isActionSelected = true;

			}
			
		}
		
		for (int currentCardNumber = 0; currentCardNumber < this.currentPlayer.getNumberOfCardOnTheBoard(); currentCardNumber++)
		{
			if (e.getSource() == this.cardsOnBoardOfCurrentPlayer[currentCardNumber])
			{
				this.lastCardClicked = this.cardsOnBoardOfCurrentPlayer[currentCardNumber].getCard();
				this.actionSelected = 5;
				this.isActionSelected = true;

			}
			
		}
		
		
		
		if (e.getSource() == this.nextTurn)
		{

			this.actionSelected = 0;
			this.isActionSelected = true;
			//this.setCurrentPlayer(this.currentPlayer.getOpponent());

		}
		
		this.refreshFullDisplay();
		
	}


	@Override
	public void mouseEntered(MouseEvent e)
	{
		
		for (int currentCardNumber = 0; currentCardNumber < Hand.MAXIMAL_CARDS_NUMBER_IN_HAND; currentCardNumber++)
		{
			if (e.getSource() == this.cardsInHandOfCurrentPlayer[currentCardNumber])
			{
				this.bigCardOnTheSide.associateCard(this.cardsInHandOfCurrentPlayer[currentCardNumber].getCard());
				
				refreshAttackAndLifeLabels(this.cardsInHandOfCurrentPlayer[currentCardNumber].getCard());
				
				if (!this.bigCardOnTheSide.isVisible())
					this.bigCardOnTheSide.setVisible(true);
			}	
		}
		
		for (int currentCardNumber = 0; currentCardNumber < Board.MAXIMAL_INVOCATION_NUMBER; currentCardNumber++)
		{
			if (e.getSource() == this.cardsOnBoardOfCurrentPlayer[currentCardNumber])
			{
				this.bigCardOnTheSide.associateCard(this.cardsOnBoardOfCurrentPlayer[currentCardNumber].getCard());
				
				refreshAttackAndLifeLabels(this.cardsOnBoardOfCurrentPlayer[currentCardNumber].getCard());
				
				if (!this.bigCardOnTheSide.isVisible())
					this.bigCardOnTheSide.setVisible(true);
			}			
		}
		
		for (int currentCardNumber = 0; currentCardNumber < Board.MAXIMAL_INVOCATION_NUMBER; currentCardNumber++)
		{
			if (e.getSource() == this.cardsOnBoardOfCurrentPlayerOpponent[currentCardNumber])
			{
				this.bigCardOnTheSide.associateCard(this.cardsOnBoardOfCurrentPlayerOpponent[currentCardNumber].getCard());
				
				refreshAttackAndLifeLabels(this.cardsOnBoardOfCurrentPlayerOpponent[currentCardNumber].getCard());
				
				if (!this.bigCardOnTheSide.isVisible())
					this.bigCardOnTheSide.setVisible(true);
			}		
		}	
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub		
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}
	
	public void refreshFullDisplay()
	{
		this.displayCurrentPlayerHand();
		this.displayCurrentPlayerBoard();
		this.displayPlayerOpponentHand();
		this.displayPlayerOpponentBoard();
		this.displayCurrentPlayerMana();

		
		
		if (!this.progressBarOpponentLife.isVisible())
			this.progressBarOpponentLife.setVisible(true);
		if (!this.progressBarCurrentPlayerLife.isVisible())
			this.progressBarCurrentPlayerLife.setVisible(true);
		if (!this.labelMana.isVisible())
		{
			this.labelMana.setVisible(true);
			this.nextTurn.addMouseListener(this);
		}

		
		this.progressBarOpponentLife.setValue(this.currentPlayer.getOpponent().getLife());
		this.progressBarOpponentLife.setString("Vie : " + this.currentPlayer.getOpponent().getLife());
		
		this.progressBarCurrentPlayerLife.setValue(this.currentPlayer.getLife());
		this.progressBarCurrentPlayerLife.setString("Vie : " + this.currentPlayer.getLife());
		
		
		for (int currentCard = 0; currentCard < this.currentPlayer.getNumberOfCardOnTheBoard(); currentCard++)
		{
			this.cardsOnBoardOfCurrentPlayer[currentCard].refreshLabels();
		}
		
		for (int currentCard = 0; currentCard < this.currentPlayer.getOpponent().getNumberOfCardOnTheBoard(); currentCard++)
		{
			this.cardsOnBoardOfCurrentPlayerOpponent[currentCard].refreshLabels();
		}
			
	}
	
	public void displayCurrentPlayerMana()
	{
		for (int currentLabel = 0; currentLabel < Player.MAXIMAL_MANA_NUMBER; currentLabel++)
		{
			String path = "";
			
			if (currentLabel >= this.currentPlayer.getMana())
				path = "images/no_mana.png";
			else
				path = "images/mana.png";
				
				
			ImageIcon icon = new ImageIcon(path);
			Image image = icon.getImage();
			image = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			icon.setImage(image);
			this.labelCurrentPlayerMana[currentLabel].setIcon(icon);
			
			this.labelCurrentPlayerMana[currentLabel].setVisible(true);
			this.backgroundPanel.add(this.labelCurrentPlayerMana[currentLabel]);

		}
	}
	
	public void refreshAttackAndLifeLabels(Card p_card)
	{
		if (p_card instanceof Invocation)
		{
			this.labelAttackCard.setText(Integer.toString(((Invocation)p_card).getAttackPoints()));
			this.labelLifeCard.setText(Integer.toString(((Invocation)p_card).getCurrentDefensePoints()));
		}
		else if (p_card instanceof Spell)
		{
			this.labelAttackCard.setText("");
			this.labelLifeCard.setText("");
		}
		
		
	}

	public void displayCurrentPlayerHand()
	{
		for (int currentCardNumber = 0; currentCardNumber < this.currentPlayer.getNumberOfCardsInTheHand(); currentCardNumber++)
		{
			this.cardsInHandOfCurrentPlayer[currentCardNumber].associateCard(this.currentPlayer.getCardInHand(currentCardNumber+1));
			this.cardsInHandOfCurrentPlayer[currentCardNumber].setVisible(true);
		}
		for (int i = this.currentPlayer.getNumberOfCardsInTheHand(); i < Hand.MAXIMAL_CARDS_NUMBER_IN_HAND; i++)
		{
			this.cardsInHandOfCurrentPlayer[i].setVisible(false);
		}
	}

	public void displayCurrentPlayerBoard()
	{
		for (int currentCardNumber = 0; currentCardNumber < this.currentPlayer.getNumberOfCardOnTheBoard(); currentCardNumber++)
		{
			this.cardsOnBoardOfCurrentPlayer[currentCardNumber].associateCard(this.currentPlayer.getCardOnBoard(currentCardNumber));
			this.cardsOnBoardOfCurrentPlayer[currentCardNumber].setVisible(true);
		}
		for (int currentCardNumber = this.currentPlayer.getNumberOfCardOnTheBoard(); currentCardNumber < Board.MAXIMAL_INVOCATION_NUMBER; currentCardNumber++)
		{
			this.cardsOnBoardOfCurrentPlayer[currentCardNumber].setVisible(false);
		}
	}

	public void displayPlayerOpponentHand()
	{
		for (int currentCardNumber = 0; currentCardNumber < this.currentPlayer.getOpponent().getNumberOfCardsInTheHand(); currentCardNumber++)
		{
			this.cardsInHandOfCurrentPlayerOpponent[currentCardNumber].associateCard(null);
			this.cardsInHandOfCurrentPlayerOpponent[currentCardNumber].setVisible(true);
		}
		for (int i = this.currentPlayer.getOpponent().getNumberOfCardsInTheHand(); i < Hand.MAXIMAL_CARDS_NUMBER_IN_HAND; i++)
		{
			this.cardsInHandOfCurrentPlayerOpponent[i].setVisible(false);
		}
	}

	public void displayPlayerOpponentBoard()
	{
		for (int currentCardNumber = 0; currentCardNumber < this.currentPlayer.getOpponent().getNumberOfCardOnTheBoard(); currentCardNumber++)
		{
			this.cardsOnBoardOfCurrentPlayerOpponent[currentCardNumber].associateCard(this.currentPlayer.getOpponent().getCardOnBoard(currentCardNumber));
			this.cardsOnBoardOfCurrentPlayerOpponent[currentCardNumber].setVisible(true);
		}
		for (int currentCardNumber = this.currentPlayer.getOpponent().getNumberOfCardOnTheBoard(); currentCardNumber < Board.MAXIMAL_INVOCATION_NUMBER; currentCardNumber++)
		{
			this.cardsOnBoardOfCurrentPlayerOpponent[currentCardNumber].setVisible(false);
		}
	}



	@Override
	public Invocation choseInvocationInHand(Hand p_hand)
	{
		// TODO Auto-generated method stub
		return (Invocation)this.lastCardClicked;
	}



	@Override
	public Spell choseSpellInHand(Hand p_hand)
	{
		// TODO Auto-generated method stub
		return (Spell)this.lastCardClicked;
	}



	@Override
	public Invocation choseInvocationInCurrentPlayerBoard(Board p_board)
	{
		String[] targetNames = new String[this.currentPlayer.getNumberOfCardOnTheBoard()];
		
		for (int currentCard = 0; currentCard < this.currentPlayer.getNumberOfCardOnTheBoard(); currentCard++)
		{
			targetNames[currentCard] = this.currentPlayer.getCardOnBoard(currentCard).getName();
		}
		
		Invocation targets[] =  new Invocation[this.currentPlayer.getNumberOfCardOnTheBoard()];
		
		for (int currentCard = 0; currentCard < this.currentPlayer.getNumberOfCardOnTheBoard(); currentCard++)
		{
			targets[currentCard] = (Invocation)this.currentPlayer.getCardOnBoard(currentCard);
		}
		
		
		String answer = "";		
		
		boolean deckIsChosen = false;
		do
		{
			answer = (String)JOptionPane.showInputDialog (this, "Choisissez une cible : ", "Fallen Heroes", JOptionPane.QUESTION_MESSAGE, null, targetNames, targetNames[0]) ;
			
			
			if (answer == null)
				continue;
			
			for (int currentCard = 0; currentCard < this.currentPlayer.getNumberOfCardOnTheBoard(); currentCard++)
			{
				if (answer.equals(targetNames[currentCard]))
				{

					return targets[currentCard];
				}

			}

				
			deckIsChosen = true;

			
			
		} while (!deckIsChosen);

		
		return null;
	}
	
	@Override
	public Invocation choseInvocationInCurrentPlayerBoardOrCurrentPlayerHimself(
			Board p_board)
	{
		String[] targetNames = new String[this.currentPlayer.getNumberOfCardOnTheBoard() + 1];
		
		targetNames[0] = "Joueur adverse";
		for (int currentCard = 0; currentCard < this.currentPlayer.getNumberOfCardOnTheBoard(); currentCard++)
		{
			targetNames[currentCard + 1] = this.currentPlayer.getCardOnBoard(currentCard).getName();
		}
		
		Invocation targets[] =  new Invocation[this.currentPlayer.getNumberOfCardOnTheBoard() + 1];
		
		targets[0] = null;
		for (int currentCard = 0; currentCard < this.currentPlayer.getNumberOfCardOnTheBoard(); currentCard++)
		{
			targets[currentCard + 1] = (Invocation)this.currentPlayer.getCardOnBoard(currentCard);
		}
		
		
		String answer = "";		
		
		boolean deckIsChosen = false;
		do
		{
			answer = (String)JOptionPane.showInputDialog (this, "Choisissez une cible : ", "Fallen Heroes", JOptionPane.QUESTION_MESSAGE, null, targetNames, targetNames[0]) ;
			
			
			if (answer == null)
				continue;
			
			if (answer.equals(targetNames[0]))
			{
				return null;
			}
			else
			{
				for (int currentCard = 0; currentCard < this.currentPlayer.getNumberOfCardOnTheBoard(); currentCard++)
				{
					if (answer.equals(targetNames[currentCard + 1]))
					{

						return targets[currentCard + 1];
					}

				}
			}

				
			deckIsChosen = true;

			
			
		} while (!deckIsChosen);

		
		return null;
	}

	
	@Override
	public Invocation choseInvocationInCurrentPlayerBoardToAttack(Board p_board)
	{
		// TODO Auto-generated method stub
		return (Invocation)this.lastCardClicked;
	}
	
	
	@Override
	public Invocation choseInvocationInOpponentBoardOrOpponentHimself(
			Board p_board)
	{
		String[] targetNames = new String[this.currentPlayer.getOpponent().getNumberOfCardOnTheBoard() + 1];
		
		targetNames[0] = "Joueur adverse";
		for (int currentCard = 0; currentCard < this.currentPlayer.getOpponent().getNumberOfCardOnTheBoard(); currentCard++)
		{
			targetNames[currentCard + 1] = this.currentPlayer.getOpponent().getCardOnBoard(currentCard).getName();
		}
		
		Invocation targets[] =  new Invocation[this.currentPlayer.getOpponent().getNumberOfCardOnTheBoard() + 1];
		
		targets[0] = null;
		for (int currentCard = 0; currentCard < this.currentPlayer.getOpponent().getNumberOfCardOnTheBoard(); currentCard++)
		{
			targets[currentCard + 1] = (Invocation)this.currentPlayer.getOpponent().getCardOnBoard(currentCard);
		}
		
		
		String answer = "";		
		
		boolean deckIsChosen = false;
		do
		{
			answer = (String)JOptionPane.showInputDialog (this, "Choisissez une cible : ", "Fallen Heroes", JOptionPane.QUESTION_MESSAGE, null, targetNames, targetNames[0]) ;
			
			
			if (answer == null)
				continue;
			
			if (answer.equals(targetNames[0]))
			{
				return null;
			}
			else
			{
				for (int currentCard = 0; currentCard < this.currentPlayer.getOpponent().getNumberOfCardOnTheBoard(); currentCard++)
				{
					if (answer.equals(targetNames[currentCard + 1]))
					{

						return targets[currentCard + 1];
					}

				}
			}

				
			deckIsChosen = true;

			
			
		} while (!deckIsChosen);

		
		return null;
	}
	
	
	public Invocation choseInvocationInOpponentBoard(Board p_board)
	{
		
		String[] targetNames = new String[this.currentPlayer.getOpponent().getNumberOfCardOnTheBoard()];
		
		for (int currentCard = 0; currentCard < this.currentPlayer.getOpponent().getNumberOfCardOnTheBoard(); currentCard++)
		{
			targetNames[currentCard] = this.currentPlayer.getOpponent().getCardOnBoard(currentCard).getName();
		}
		
		Invocation targets[] =  new Invocation[this.currentPlayer.getOpponent().getNumberOfCardOnTheBoard()];
		
		for (int currentCard = 0; currentCard < this.currentPlayer.getOpponent().getNumberOfCardOnTheBoard(); currentCard++)
		{
			targets[currentCard] = (Invocation)this.currentPlayer.getOpponent().getCardOnBoard(currentCard);
		}
		
		
		String answer = "";		
		
		boolean targetIsChosen = false;
		do
		{
			answer = (String)JOptionPane.showInputDialog (this, "Choisissez une cible : ", "Fallen Heroes", JOptionPane.QUESTION_MESSAGE, null, targetNames, targetNames[0]) ;
			
			
			if (answer == null)
				continue;
			
			for (int currentCard = 0; currentCard < this.currentPlayer.getOpponent().getNumberOfCardOnTheBoard(); currentCard++)
			{
				if (answer.equals(targetNames[currentCard]))
				{

					return targets[currentCard];
				}

			}

				
			targetIsChosen = true;

			
			
		} while (!targetIsChosen);

		
		return null;
	}



	@Override
	public int selectAction()
	{
		this.isActionSelected = false;
		while (!isActionSelected)
		{
			
		}
		return this.actionSelected;
	}



	@Override
	public int selectDeck()
	{
		
		String[] decks = {"Deck Light", "Deck Dark"};
		String answer = "";		
		
		boolean deckIsChosen = false;
		do
		{
			answer = (String)JOptionPane.showInputDialog (this, "Choisissez un deck", "Fallen Heroes", JOptionPane.QUESTION_MESSAGE, null, decks, decks[0]) ;
			
			
			if (answer == null)
				continue;
			
			if (answer.equals(decks[0]))
			{
				return 1;
			}
			else if (answer.equals(decks[1]))
			{
				return 2;
			}
				
			deckIsChosen = true;

			
			
		} while (!deckIsChosen);

		
		return 1;
	}



	@Override
	public void displayLogo()
	{
		// TODO Auto-generated method stub
		
	}



	@Override
	public void displayBoards(int p_player1Life, int p_player2Life,
			int p_player1Mana, int p_player2Mana, String p_boardPlayer1,
			String p_boardPlayer2)
	{
		// TODO Auto-generated method stub
		
	}



	@Override
	public void displayNextTurnMessage(int p_playerNumber)
	{
		// TODO Auto-generated method stub
		//JOptionPane.showMessageDialog(this, "Tour joueur " + p_playerNumber, "", JOptionPane.INFORMATION_MESSAGE);
	}



	@Override
	public void displayChoices()
	{
		// TODO Auto-generated method stub
		
	}



	@Override
	public void displayNotEnoughManaMessage()
	{
		JOptionPane.showMessageDialog(this, "Pas assez de mana pour jouer cette carte", "", JOptionPane.ERROR_MESSAGE);
		
	}



	@Override
	public void displayConferationMessage(String p_capacity)
	{
		// TODO Auto-generated method stub
		
	}



	@Override
	public void displayNotValidCard()
	{
		// TODO Auto-generated method stub
		
	}



	@Override
	public void displayCriDeGuerreMessage(int p_criDeGuerreEffect)
	{

		JOptionPane.showMessageDialog(this, "Cette invocation enlève " + p_criDeGuerreEffect + " point(s) à une cible ennemie", "", JOptionPane.INFORMATION_MESSAGE);

	}



	@Override
	public void displayNoOtherTargetThanOpponentForCriDeGuerreCapacity()
	{
		JOptionPane.showMessageDialog(this, "Il n'y a pas de créature à cibler, le sort va s'appliquer au joueur ennemi", "",JOptionPane.INFORMATION_MESSAGE);
		
	}



	@Override
	public void displayOpponentAsATarget()
	{
		// TODO Auto-generated method stub
		
	}



	@Override
	public void displayCurrentPlayerAsATarget()
	{
		// TODO Auto-generated method stub
		
	}



	@Override
	public void displayHand(Hand p_hand)
	{
		// TODO Auto-generated method stub
		
	}



	@Override
	public void displayBoard(Board p_board, boolean p_displayOutlines)
	{
		// TODO Auto-generated method stub
		
	}



	@Override
	public void displayWaitingForAChoice()
	{
		// TODO Auto-generated method stub
		
	}



	@Override
	public void displaySpellMessage(int p_spellEffect, String p_spellTarget)
	{
		String toDisplay = "";
		if (p_spellTarget.equals("Soi-même (joueur)"))
		{
			toDisplay = "Cette carte vous rend " + p_spellEffect + " point(s) de vie";
		}
		else if (p_spellTarget.equals("Joueur ennemi"))
		{
			toDisplay = "Cette carte enlève " + p_spellEffect + " point(s) de vie au joueur adverse";
		}
		else if (p_spellTarget.equals("Soi-même (joueur ou invocation)"))
		{
			toDisplay = "Cette carte rend " + p_spellEffect + " point(s) de vie à vous ou une de vos créatures";
		}
		else if (p_spellTarget.equals("Invocation ennemie"))
		{
			toDisplay = "Cette carte enlève " + p_spellEffect + " point(s) de vie à une invocation ennemie";
		}
		else if (p_spellTarget.equals("Cible ennemie"))
		{
			toDisplay = "Cette carte enlève " + p_spellEffect + " point(s) de vie à une cible ennemie";
		}
		
		JOptionPane.showMessageDialog(this, toDisplay, "",JOptionPane.INFORMATION_MESSAGE);
	}



	@Override
	public void displayNoOtherTargetThanCurrentPlayerForThisSpell()
	{
		JOptionPane.showMessageDialog(this,"Vous n'avez pas de créature à cibler, le sort s'applique sur vous", "", JOptionPane.INFORMATION_MESSAGE);
		
	}



	@Override
	public void displayNoTargetAvailableForThisSpell()
	{
		JOptionPane.showMessageDialog(this, "Il n'y a pas de cible a viser avec ce sort", "", JOptionPane.ERROR_MESSAGE);
		
	}



	@Override
	public void displayTheirIsAnInvocationWithAvantGardeInOpponentBoard()
	{
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(this, "Il existe une carte avec avant garde sur le terrain", "", JOptionPane.ERROR_MESSAGE);
	}



	@Override
	public void displayHasAlreadyAttacked()
	{
		// TODO Auto-generated method stub
		
	}



	@Override
	public void displayCantAttackFirstTurn()
	{
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(this, "La carte ne peut pas attaquer au premier tour", "", JOptionPane.ERROR_MESSAGE);
	}



	@Override
	public void displayPLayerState(int p_life, int p_mana)
	{
		// TODO Auto-generated method stub
		
	}



	@Override
	public void displayNoOtherTargetThanOpponentForThisSpell()
	{
		JOptionPane.showMessageDialog(this, "Il n'y a pas de créature à cibler, le sort va s'appliquer au joueur ennemi", "", JOptionPane.INFORMATION_MESSAGE);
		
	}



	@Override
	public void displayPossibleDeckChoices(int p_playerNumber)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void displayToMuchInvocationOnTheBoard()
	{
		JOptionPane.showMessageDialog(this, "Le terrain ne peut pas contenir plus de cartes", "", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void displayVictoryMessage()
	{
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(this, "Vous avez gagné", "", JOptionPane.INFORMATION_MESSAGE);
	}




}
