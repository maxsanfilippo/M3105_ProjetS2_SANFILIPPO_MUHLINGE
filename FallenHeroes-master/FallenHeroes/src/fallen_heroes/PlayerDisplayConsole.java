package fallen_heroes;

public class PlayerDisplayConsole implements PlayerDisplayInterface
{
	public void displayLogo()
	{
		System.out.println("       _______      ___                              ____    _______   __          ____");
		System.out.println("      /   ___/  /\\  \\  \\                            /   /   /   ___/  /  \\        /   /");
		System.out.println("     /   /     /  \\  \\  \\                          /   /   /   /     /    \\      /   /");
		System.out.println("    /   /__   /    \\  \\  \\                        /   /   /   /__   /      \\    /   /");
		System.out.println("   /   ___/  /  /\\  \\  \\  \\                      /   /   /   ___/  /   /\\   \\  /   /");
		System.out.println("  /   /     /        \\  \\  \\                    /   /   /   /     /   /  \\   \\/   /");
		System.out.println(" /   /     /    /\\    \\  \\  \\_______    _______/   /   /   /__   /   /    \\      /");
		System.out.println("/___/     /____/  \\____\\  \\__________\\ /__________/   /______/  /___/      \\____/ ");
		System.out.println("\n\n ____     ____    ________    ___________      ___________    ________     ___________");
		System.out.println("|    |   |    |  |        |  |           |    /           \\  |        |   |           |");
		System.out.println("|    |   |    |  |     ___|  |    ____   |    |   _____   |  |     ___|   |     ______|");
		System.out.println("|    |___|    |  |    |      |   |____|  |    |  |     |  |  |    |       |    |");
		System.out.println("|             |  |    |___   |           |    |  |     |  |  |    |___    |    |_____");
		System.out.println("|     ___     |  |     ___|  |     _    /     |  |     |  |  |     ___|   |_____     |");
		System.out.println("|    |   |    |  |    |      |    | \\   \\     |  |     |  |  |    |             |    |");
		System.out.println("|    |   |    |  |    |___   |    |  \\   \\    |  |_____|  |  |    |___    ______|    |");
		System.out.println("|    |   |    |  |        |  |    |   \\   \\   |           |  |        |  |           |");
		System.out.println("|____|   |____|  |________|  |____|    \\___\\  \\___________/  |________|  |___________|");
	}
	
	public void displayBoards(int p_player1Life, int p_player2Life, int p_player1Mana, int p_player2Mana, String p_boardPlayer1, String p_boardPlayer2)
	{
		System.out.println("JOUEUR 2 : " + p_player2Life + " POINTS DE VIE");
		System.out.println("===================  MANA : " + p_player2Mana + " ===================\n\n");
		System.out.println(p_boardPlayer2);
		System.out.println(p_boardPlayer1);
		System.out.println("===================  MANA : " + p_player1Mana + " ===================");
		System.out.println("JOUEUR 1 : " + p_player1Life + " POINTS DE VIE");
	}
	
	public void displayNextTurnMessage(int p_playerNumber)
	{
		System.out.println("\n\n*******************************");
		System.out.println("******** Tour joueur " + p_playerNumber + " ********");
		System.out.println("*******************************");
	}


	public void displayChoices()
	{
		System.out.println("\n _____________________\n|1 - Consulter main    |"
						+ "\n ______________________\n|2 - Consulter terrain |"
						+ "\n ______________________\n|3 - Poser cr�ature    |"
						+ "\n ______________________\n|4 - Utiliser sort     |"
						+ "\n ______________________\n|5 - Attaquer          |"
						+ "\n\nS�lectionnez une action : "
						+ "\n-------------------------");
		
	}


	public void displayNotEnoughManaMessage()
	{
		System.out.println("Pas assez de mana pour jouer cette carte !");
		
	}


	public void displayConferationMessage(String p_capacity)
	{
		System.out.println("Cette invocation conf�re la capacit� " + p_capacity + " a une cr�ature.");
		System.out.println("Choisissez la cr�ature a affecter :");
		
	}


	public void displayNotValidCard()
	{
		System.out.println("La carte demand�e n'existe pas");
		
	}


	public void displayCriDeGuerreMessage(int p_criDeGuerreEffect)
	{

		System.out.println("Cette invocation enl�ve " + p_criDeGuerreEffect + " point(s) � une cible ennemie");

		
	}

	
	public void displayNoOtherTargetThanOpponentForCriDeGuerreCapacity()
	{
		System.out.println("Le joueur ennemi n'a pas de cr�ature sur son terrain");
		System.out.println("Le cri de guerre va s'appliquer sur le joueur adverse");
	}
	
	public void displayOpponentAsATarget()
	{
		System.out.println("0) Joueur adverse");
	}
	
	public void displayBoard(Board p_board, boolean p_displayOutline)
	{
		System.out.println(p_board.returnBoard(p_displayOutline));
	}
	
	public void displayWaitingForAChoice()
	{
		System.out.println("Entrez votre choix : ");
	}


	public void displaySpellMessage(int p_spellEffect, String p_spellTarget)
	{
		if (p_spellTarget.equals("Soi-m�me (joueur)"))
		{
			System.out.println("Cette carte vous rend " + p_spellEffect + " point(s) de vie");
		}
		else if (p_spellTarget.equals("Joueur ennemi"))
		{
			System.out.println("Cette carte enl�ve " + p_spellEffect + " point(s) de vie au joueur adverse");
		}
		else if (p_spellTarget.equals("Soi-m�me (joueur ou invocation)"))
		{
			System.out.println("Cette carte rend " + p_spellEffect + " point(s) de vie � vous ou une de vos cr�atures");
		}
		else if (p_spellTarget.equals("Invocation ennemie"))
		{
			System.out.println("Cette carte enl�ve " + p_spellEffect + " point(s) de vie � une cr�ature ennemie");
		}
		else if (p_spellTarget.equals("Cible ennemie"))
		{
			System.out.println("Cette carte enl�ve " + p_spellEffect + " point(s) de vie � une cible ennemie");
		}
		
	}


	public void displayCurrentPlayerAsATarget()
	{
		System.out.println("0) Vous");
		
	}


	public void displayNoOtherTargetThanCurrentPlayerForThisSpell()
	{
		System.out.println("Vous n'avez pas de cr�ature � cibler, le sort s'applique sur vous");
		
	}
	
	public void displayNoTargetAvailableForThisSpell()
	{
		System.out.println("Il n'y a pas de cr�ature � cibler avec ce sort");
	}
	
	public void displayTheirIsAnInvocationWithAvantGardeInOpponentBoard()
	{
		System.out.println("Il existe une carte avec avant garde sur le terrain");
	}


	public void displayHasAlreadyAttacked()
	{
		System.out.println("La carte a d�j� attaqu�");
	}
	
	public void displayCantAttackFirstTurn()
	{
		System.out.println("La carte ne peut pas attaquer au premier tour");
	}

	public void displayHand(Hand p_hand)
	{
		System.out.println(p_hand.toString());
		
	}
	
	public void displayPLayerState(int p_life, int p_mana)
	{
		System.out.println("\n************** Etat *************");
		System.out.println("*\tVie joueur : " + p_life + " \t*");
		System.out.println("*\tMana joueur : " + p_mana + " \t*");
		System.out.println("*********************************");
	}
	
	public void displayNoOtherTargetThanOpponentForThisSpell()
	{
		System.out.println("Il n'y a pas de cr�ature � cibler, le sort va s'appliquer au joueur ennemi");
	}
	
	public void displayPossibleDeckChoices(int p_playerNumber)
	{
		System.out.println("\n\n *** Joueur " + p_playerNumber + " ***");
		System.out.println("Choisissez un deck parmi :\n1) Deck light\n2) Deck dark");
	}
	
	public void refreshFullDisplay()
	{
		
	}
	
	@Override
	public void displayToMuchInvocationOnTheBoard()
	{
		System.out.println("Le terrain ne peut pas contenir plus de cartes");
	}

	@Override
	public void displayVictoryMessage()
	{
		// TODO Auto-generated method stub
		
	}
	
}