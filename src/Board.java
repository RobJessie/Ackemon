
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Board {
	private Player player1;
	private Player player2;
	private Player currentPlayer;
	private int turn = 1;

	Random r = new Random();

	private static Board instance;

	private Board() {
		player1 = new Player();
		player2 = new Player();
		player1.setName("Player 1");
		player2.setName("Player 2");
		populate();

		initializeHand(player1);
		initializeHand(player2);

		currentPlayer = pickStartingPlayer();
	}

	public void newGame() {
//		
//		for(Iterator<Card> it= player1.getPlayerHand().getHand().iterator(); it.hasNext();) {
//			Card c=it.next();
//			player1.addToPlayerDeck(c);
//			player1.removeFromPlayerHand(c);
//		}
//		
//		for(Iterator<Card> it= player2.getPlayerHand().getHand().iterator(); it.hasNext();) {
//			Card c=it.next();
//			player2.addToPlayerDeck(c);
//			player2.removeFromPlayerHand(c);
//		}
//		
//		for(Iterator<Card> it= player1.getDiscard().iterator(); it.hasNext();) {
//			Card c=it.next();
//			player1.addToPlayerDeck(c);
//			player1.removeFromDiscard(c);
//			if (c instanceof Creature) {
//				((Creature) c).resetStats();
//			}
//		}
//		
//		for(Iterator<Card> it= player2.getDiscard().iterator(); it.hasNext();) {
//			Card c=it.next();
//			player2.addToPlayerDeck(c);
//			player2.removeFromDiscard(c);
//			if (c instanceof Creature) {
//				((Creature) c).resetStats();
//			}
//		}
//		
//		for(Iterator<Card> it= player1.getPlayed().iterator(); it.hasNext();) {
//			Card c=it.next();
//			player1.addToPlayerDeck(c);
//			player1.removeFromPlayed(c);
//			if (c instanceof Creature) {
//				((Creature) c).resetStats();
//			}
//		}
//		
//		for(Iterator<Card> it= player2.getPlayed().iterator(); it.hasNext();) {
//			Card c=it.next();
//			player2.addToPlayerDeck(c);
//			player2.removeFromPlayed(c);
//			if (c instanceof Creature) {
//				((Creature) c).resetStats();
//			}
//		}
		player1.resetDeck();
		player1.getPlayed().clear();
		player1.getDiscard().clear();
		player1.getPlayerHand().getHand().clear();
		
		player2.resetDeck();
		player2.getPlayed().clear();
		player2.getDiscard().clear();
		player2.getPlayerHand().getHand().clear();
		
		player1.resetLP();
		player2.resetLP();

		initializeHand(player1);
		initializeHand(player2);

		currentPlayer = pickStartingPlayer();
	}

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public static Board getInstance() {
		if (instance == null)
			instance = new Board();
		return instance;
	}

	private Player pickStartingPlayer() {
		int rn = r.nextInt(10);

		if (rn <= 4) {
			player2.addToMaxCoins();
			player2.resetCurrentCoins();
			return player1;
		} else {
			player1.addToMaxCoins();
			player1.resetCurrentCoins();
			return player2;
		}
	}

	public int checkPlayerLifePoints(Player p) {
		return p.getLifePoints();
	}

	public void playCard(Card c, Player p) throws CardTypePlayedException {
		if (c instanceof Creature) {
			if (p.isCreaturePlayed())
				throw new CardTypePlayedException("Creature");
			else {
				p.addToPlayed(c);
				p.removeFromPlayerHand(c);
				p.setCreaturePlayed(true);
			}
		} else if (c instanceof Enhancement) {
			if (p.isEnhancementPlayed())
				throw new CardTypePlayedException("Enhancement");
			else {
				p.addToPlayed(c);
				p.removeFromPlayerHand(c);
				p.setEnhancementPlayed(true);
				discard(c, p);
			}
		}
	}

	public void discard(Card c, Player p) {
		p.addToDiscard(c);
		p.removeFromPlayed(c);
	}

	public void initializeHand(Player p) {
		for (int i = 0; i < 6; i++) {
			Card cd = p.getPlayerDeck().getCard(r.nextInt(p.getPlayerDeck().getDeckSize()));
			p.addToPlayerHand(cd);
			p.getPlayerDeck().removeFromDeck(cd);
		}
	}

	public void nextTurn() {
		while (currentPlayer.getPlayerHand().getHand().size() < 6) {
			Card cd = currentPlayer.getPlayerDeck().getCard(r.nextInt(currentPlayer.getPlayerDeck().getDeckSize()));
			currentPlayer.addToPlayerHand(cd);
			currentPlayer.getPlayerDeck().removeFromDeck(cd);
		}

		currentPlayer.getPlayed().stream().filter(c -> c instanceof Creature).map(c -> (Creature) c)
				.forEach(c -> c.resetAtkCount());
		;

		currentPlayer.setCreaturePlayed(false);
		currentPlayer.setEnhancementPlayed(false);

		currentPlayer.addToMaxCoins();
		currentPlayer.resetCurrentCoins();

		turn++;

		currentSwitch();

	}

	public void currentSwitch() {
		if (currentPlayer.equals(player1))
			currentPlayer = player2;
		else if (currentPlayer.equals(player2))
			currentPlayer = player1;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void addCardToPlayer(Card c, Player p) {
		p.addToCardCollection(c);
		p.addToPlayerDeck(c);
	}

	public int getTurn() {
		return turn;
	}

	public String typeName(Creature c) {
		int t = c.getType();
		switch (t) {
		case 1:
			return "Fire";
		case 2:
			return "Water";
		case 3:
			return "Grass";
		case 4:
			return "Electric";
		case 5:
			return "Ground";
		default:
			return "you broke the project";
		}
	}

	public void checkCoins(Card c, Player p) throws InsufficientCoinsException {
		int cc = c.getCost();
		int pc = p.getCurrentCoins();
		if ((pc - cc) < 0)
			throw new InsufficientCoinsException();
	}

	// returns .5 if the attack is not effective, 1 if neutral, and 2 if super
	// effective
	public double effectivenessCheck(Creature a, Creature d) {
		int at = a.getType();
		int dt = d.getType();

		if ((at == 1) && (dt == 2))
			return .5;
		else if ((at == 1) && (dt == 3)) {
			return 2;
		} else if ((at == 2) && (dt == 4)) {
			return .5;
		} else if ((at == 2) && (dt == 1)) {
			return 2;
		} else if ((at == 3) && (dt == 1)) {
			return .5;
		} else if ((at == 3) && (dt == 5)) {
			return 2;
		} else if ((at == 4) && (dt == 5)) {
			return .5;
		} else if ((at == 4) && (dt == 2)) {
			return 2;
		} else if ((at == 5) && (dt == 3)) {
			return .5;
		} else if ((at == 5) && (dt == 4)) {
			return 2;
		} else
			return 1;
	}

	// public int attack(Creature a, Creature d) {
	public int attack(Creature a, Creature d) throws NoAttacksException {
		int dmg = (int) (a.getAttack() * effectivenessCheck(a, d));
		int pdmg = 0;
		if (a.getAtkCount() == 0)
			throw new NoAttacksException();
		else {
			if (d.getHealth() - dmg > 0)
				d.setHealth(d.getHealth() - dmg);
			else {
				pdmg = dmg - d.getHealth();
				currentPlayer.removeLifePoints(pdmg);
				d.setHealth(0);
			}
		}
		return pdmg;
	}

	public void populate() {
		addCardToPlayer(new Creature("Ground Dragon", 8, 10, 20, 5, true), player1);
		addCardToPlayer(new Creature("Ground Warrior", 5, 5, 10, 5, false), player1);
		addCardToPlayer(new Creature("Ground Scout", 3, 3, 5, 5, false), player1);
		addCardToPlayer(new Creature("Ground Sprite", 1, 1, 1, 5, true), player1);
		addCardToPlayer(new Creature("Electric Dragon", 8, 10, 20, 4, true), player1);
		addCardToPlayer(new Creature("Electric Warrior", 5, 5, 10, 4, false), player1);
		addCardToPlayer(new Creature("Electric Scout", 3, 3, 5, 4, false), player1);
		addCardToPlayer(new Creature("Electric Sprite", 1, 1, 1, 4, true), player1);
		addCardToPlayer(new Creature("Water Dragon", 8, 10, 20, 2, true), player1);
		addCardToPlayer(new Creature("Water Warrior", 5, 5, 10, 2, false), player1);
		addCardToPlayer(new Creature("Water Scout", 3, 3, 5, 2, false), player1);
		addCardToPlayer(new Creature("Water Sprite", 1, 1, 1, 2, true), player1);
		addCardToPlayer(new Creature("Fire Dragon", 8, 10, 20, 1, true), player1);
		addCardToPlayer(new Creature("Fire Warrior", 5, 5, 10, 1, false), player1);
		addCardToPlayer(new Creature("Fire Scout", 3, 3, 5, 1, false), player1);
		addCardToPlayer(new Creature("Fire Sprite", 1, 1, 1, 1, true), player1);
		addCardToPlayer(new Creature("Grass Dragon", 8, 10, 20, 3, true), player1);
		addCardToPlayer(new Creature("Grass Warrior", 5, 5, 10, 3, false), player1);
		addCardToPlayer(new Creature("Grass Scout", 3, 3, 5, 3, false), player1);
		addCardToPlayer(new Creature("Grass Sprite", 1, 1, 1, 3, true), player1);

		addCardToPlayer(new Creature("Ground Dragon", 8, 10, 20, 5, true), player2);
		addCardToPlayer(new Creature("Ground Warrior", 5, 5, 10, 5, false), player2);
		addCardToPlayer(new Creature("Ground Scout", 3, 3, 5, 5, false), player2);
		addCardToPlayer(new Creature("Ground Sprite", 1, 1, 1, 5, true), player2);
		addCardToPlayer(new Creature("Electric Dragon", 8, 10, 20, 4, true), player2);
		addCardToPlayer(new Creature("Electric Warrior", 5, 5, 10, 4, false), player2);
		addCardToPlayer(new Creature("Electric Scout", 3, 3, 5, 4, false), player2);
		addCardToPlayer(new Creature("Electric Sprite", 1, 1, 1, 4, true), player2);
		addCardToPlayer(new Creature("Water Dragon", 8, 10, 20, 2, true), player2);
		addCardToPlayer(new Creature("Water Warrior", 5, 5, 10, 2, false), player2);
		addCardToPlayer(new Creature("Water Scout", 3, 3, 5, 2, false), player2);
		addCardToPlayer(new Creature("Water Sprite", 1, 1, 1, 2, true), player2);
		addCardToPlayer(new Creature("Fire Dragon", 8, 10, 20, 1, true), player2);
		addCardToPlayer(new Creature("Fire Warrior", 5, 5, 10, 1, false), player2);
		addCardToPlayer(new Creature("Fire Scout", 3, 3, 5, 1, false), player2);
		addCardToPlayer(new Creature("Fire Sprite", 1, 1, 1, 1, true), player2);
		addCardToPlayer(new Creature("Grass Dragon", 8, 10, 20, 3, true), player2);
		addCardToPlayer(new Creature("Grass Warrior", 5, 5, 10, 3, false), player2);
		addCardToPlayer(new Creature("Grass Scout", 3, 3, 5, 3, false), player2);
		addCardToPlayer(new Creature("Grass Sprite", 1, 1, 1, 3, true), player2);

		addCardToPlayer(new Enhancement("Rage", 3, 4, 2), player1);
		addCardToPlayer(new Enhancement("Flex", 2, 2, 2), player1);
		addCardToPlayer(new Enhancement("Battle Trance", 4, 4, 4), player1);
		addCardToPlayer(new Enhancement("Reckless Fury", 1, 3, -2), player1);
		addCardToPlayer(new Enhancement("Metallicize", 1, -3, 8), player1);
		addCardToPlayer(new Enhancement("Offering", 2, 5, -3), player1);
		addCardToPlayer(new Enhancement("Strength", 3, 5, 0), player1);
		addCardToPlayer(new Enhancement("Endurance", 3, 0, 6), player1);
		addCardToPlayer(new Enhancement("Fortify", 1, -1, 5), player1);
		addCardToPlayer(new Enhancement("Vitality", 1, 0, 3), player1);

		addCardToPlayer(new Enhancement("Rage", 3, 4, 2), player2);
		addCardToPlayer(new Enhancement("Flex", 2, 2, 2), player2);
		addCardToPlayer(new Enhancement("Battle Trance", 4, 4, 4), player2);
		addCardToPlayer(new Enhancement("Reckless Fury", 1, 3, -2), player2);
		addCardToPlayer(new Enhancement("Metallicize", 1, -3, 8), player2);
		addCardToPlayer(new Enhancement("Offering", 2, 5, -3), player2);
		addCardToPlayer(new Enhancement("Strength", 3, 5, 0), player2);
		addCardToPlayer(new Enhancement("Endurance", 3, 0, 6), player2);
		addCardToPlayer(new Enhancement("Fortify", 1, -1, 5), player2);
		addCardToPlayer(new Enhancement("Vitality", 1, 0, 3), player2);

	}

	public void attackPlayer(Player p, Creature c) throws NoAttacksException {
		int lp = p.getLifePoints();
		int at = c.getAttack();
		if (c.getAtkCount() == 0)
			throw new NoAttacksException();

		if (lp - at > 0)
			p.removeLifePoints(c);
		else
			p.removeAllLP();

		c.decrementAtkCount();
	}
}