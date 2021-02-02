import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class Gameplay extends JFrame {
	private JPanel played;
	private JPanel player1;
	private JPanel player2;
	private JPanel player1PlayedCrd;
	private JPanel player2PlayedCrd;
	private JPanel currentHand;
	private JPanel cHCards;
	private JLabel cHLbl;
	private JPanel controlButtons;
	private JButton nextTurn;
	private JButton newGame;
	private JScrollPane log;
	private JTextArea logList;
	private JTextField logText;
	private JPanel logPanel;
	private JLabel p1Lbl;
	private JLabel p2Lbl;
	private static Gameplay instance;
	private Board bd;
	private ArrayList<JButton> played1List;
	private ArrayList<JButton> played2List;
	private ActionListener actListener;
	private thehandler handler;

	public static void main(String[] args) {
		getInstance();
	}

	private Gameplay() {
		bd = Board.getInstance();
		played1List = new ArrayList<>();
		played2List = new ArrayList<>();
		setUpGUI();
		newGame();
		refreshGUI();
	}

	public static Gameplay getInstance() {
		if (instance == null)
			instance = new Gameplay();
		return instance;
	}

	public void setUpGUI() {
		this.setSize(1000, 1000);
		handler = new thehandler();
		cHCards = new JPanel();
		cHLbl = new JLabel(bd.getCurrentPlayer().getName() + "'s Hand. Current coins: "
				+ bd.getCurrentPlayer().getCurrentCoins() + "/" + bd.getCurrentPlayer().getMaxCoins());
		cHCards.setLayout(new FlowLayout());
		played = new JPanel();
		player1 = new JPanel();
		player2 = new JPanel();
		played.setLayout(new BorderLayout());
		player1.setLayout(new BorderLayout());
		player2.setLayout(new BorderLayout());
		player1PlayedCrd = new JPanel();
		player1PlayedCrd.setLayout(new FlowLayout());
		player2PlayedCrd = new JPanel();
		player2PlayedCrd.setLayout(new FlowLayout());
		currentHand = new JPanel();
		currentHand.setSize(3000, 500);
		currentHand.setLayout(new BorderLayout());
		currentHand.add(cHLbl, BorderLayout.NORTH);
		currentHand.add(cHCards, BorderLayout.CENTER);
		logList = new JTextArea();
		logText = new JTextField();
		logText.addActionListener(handler);
		logPanel = new JPanel();
		logPanel.setLayout(new BorderLayout());
		log = new JScrollPane(logList);
		p1Lbl = new JLabel(bd.getPlayer1().getName());
		p2Lbl = new JLabel(bd.getPlayer2().getName());
		controlButtons = new JPanel();
		controlButtons.setLayout(new BorderLayout());
		nextTurn = new JButton("Next Turn");
		nextTurn.addActionListener(e -> {
			bd.nextTurn();
			refreshPlayed();
			refreshGUI();
		});
		newGame = new JButton("New Game");
		newGame.addActionListener(e -> newGame());
		log.setPreferredSize(new Dimension(450, 500));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(played, BorderLayout.CENTER);
		add(currentHand, BorderLayout.SOUTH);
		logPanel.add(log, BorderLayout.CENTER);
		logPanel.add(logText, BorderLayout.SOUTH);
		add(logPanel, BorderLayout.WEST);
		add(controlButtons, BorderLayout.EAST);

		controlButtons.add(nextTurn, BorderLayout.CENTER);
		controlButtons.add(newGame, BorderLayout.SOUTH);

		played.add(player1, BorderLayout.NORTH);
		player1.add(p1Lbl, BorderLayout.NORTH);
		player1.add(player1PlayedCrd, BorderLayout.CENTER);

		played.add(player2, BorderLayout.SOUTH);
		player2.add(p2Lbl, BorderLayout.NORTH);
		player2.add(player2PlayedCrd, BorderLayout.CENTER);

	}

	public void refreshGUI() {
		cHCards.removeAll();
		player1.removeAll();
		player2.removeAll();
		player1PlayedCrd.removeAll();
		player2PlayedCrd.removeAll();
		
		handler=new thehandler();
		logText.removeActionListener(handler);
		logPanel.removeAll();
		logText.addActionListener(handler);
		logPanel.add(log, BorderLayout.CENTER);
		logPanel.add(logText, BorderLayout.SOUTH);
		

		player1.add(p1Lbl, BorderLayout.NORTH);
		player1.add(player1PlayedCrd, BorderLayout.CENTER);

		player2.add(p2Lbl, BorderLayout.NORTH);
		player2.add(player2PlayedCrd, BorderLayout.CENTER);

		bd.getCurrentPlayer().getPlayerHand().getHand().stream().map(c -> createHandButton(c))
				.forEach(b -> cHCards.add(b));

		played1List.forEach(b -> player1PlayedCrd.add(b));

		played2List.forEach(b -> player2PlayedCrd.add(b));

		p1Lbl.setText(bd.getPlayer1().getName() + ":   Life Points: " + bd.getPlayer1().getLifePoints() + "     Coins: "
				+ bd.getPlayer1().getCurrentCoins() + "/" + bd.getPlayer1().getMaxCoins());
		p2Lbl.setText(bd.getPlayer2().getName() + ":   Life Points: " + bd.getPlayer2().getLifePoints() + "     Coins: "
				+ bd.getPlayer2().getCurrentCoins() + "/" + bd.getPlayer2().getMaxCoins());
		cHLbl.setText(bd.getCurrentPlayer().getName() + "'s Hand. Current coins: "
				+ bd.getCurrentPlayer().getCurrentCoins() + "/" + bd.getCurrentPlayer().getMaxCoins());

		this.setSize(1000, 1000);
		pack();
		setVisible(true);
	}

	public void refreshPlayed() {
		played1List.clear();
		played2List.clear();

		bd.getPlayer1().getPlayed().forEach(c -> played1List.add(createPlayedButton(c)));
		bd.getPlayer2().getPlayed().forEach(c -> played2List.add(createPlayedButton(c)));
	}
	
	public void newGame(){
		logList.setText("New game Started. Current Player: " + bd.getCurrentPlayer().getName());
		bd.newGame();
		refreshPlayed();
		refreshGUI();
	}

	public JButton createHandButton(Card c) {
		JButton b = new JButton();
		b.setLayout(new GridLayout(4, 2, 10, 10));
//		b.setPreferredSize(new Dimension(200, 300));
		JLabel ct = new JLabel();
		JLabel cst = new JLabel();
		JLabel tp = new JLabel();
		JLabel a = new JLabel();
		JLabel h = new JLabel();
		JLabel n = new JLabel();
		JLabel r = new JLabel();
		b.add(ct);
		b.add(cst);
		b.add(n);
		b.add(tp);
		b.add(a);
		b.add(h);
		b.add(r);
		if (c instanceof Creature) {
			ct.setText("Creature");
			cst.setText("Cost: " + c.getCost());
			tp.setText(bd.typeName((Creature) c));
			a.setText("Atk: " + ((Creature) c).getAttack());
			h.setText("HP: " + ((Creature) c).getHealth());
			n.setText(c.getName());
			if (((Creature) c).getIsRare())
				r.setText("Rare");
			else
				r.setText("Common");

			b.addActionListener(e -> playCreature(c, createPlayedButton(c)));
		} else {
			ct.setText("Enhancement");
			cst.setText("Cost: " + c.getCost());
			a.setText("Atk: " + ((Enhancement) c).getEnhancementAttack());
			h.setText("HP: " + ((Enhancement) c).getEnhancementHealth());
			n.setText(c.getName());
			r.setText("");
			b.addActionListener(e -> playEnhancement((Enhancement) c));
		}

		return b;
	}

	public JButton createPlayedButton(Card c) {
		JButton b = new JButton();
		b.setLayout(new GridLayout(4, 2, 10, 10));
//		b.setPreferredSize(new Dimension(200, 300));
		JLabel ct = new JLabel();
		JLabel cst = new JLabel();
		JLabel tp = new JLabel();
		JLabel a = new JLabel();
		JLabel h = new JLabel();
		JLabel n = new JLabel();
		JLabel r = new JLabel();
		b.add(ct);
		b.add(cst);
		b.add(n);
		b.add(tp);
		b.add(a);
		b.add(h);
		b.add(r);
		if (c instanceof Creature) {
			ct.setText("Creature");
			cst.setText("Cost: " + c.getCost());
			tp.setText(bd.typeName((Creature) c));
			a.setText("Atk: " + ((Creature) c).getAttack());
			h.setText("HP: " + ((Creature) c).getHealth());
			n.setText(c.getName());
			if (((Creature) c).getIsRare())
				r.setText("Rare");
			else
				r.setText("Common");
			actListener = e -> act(c);

			b.addActionListener(actListener);
		}
		return b;
	}

	public JButton createBlankButton(Card c) {
		JButton b = new JButton();
		b.setLayout(new GridLayout(4, 2, 10, 10));
//		b.setPreferredSize(new Dimension(200, 300));
		JLabel ct = new JLabel();
		JLabel cst = new JLabel();
		JLabel tp = new JLabel();
		JLabel a = new JLabel();
		JLabel h = new JLabel();
		JLabel n = new JLabel();
		JLabel r = new JLabel();
		b.add(ct);
		b.add(cst);
		b.add(n);
		b.add(tp);
		b.add(a);
		b.add(h);
		b.add(r);
		if (c instanceof Creature) {
			ct.setText("Creature");
			cst.setText("Cost: " + c.getCost());
			tp.setText(bd.typeName((Creature) c));
			a.setText("Atk: " + ((Creature) c).getAttack());
			h.setText("HP: " + ((Creature) c).getHealth());
			n.setText(c.getName());
			if (((Creature) c).getIsRare())
				r.setText("Rare");
			else
				r.setText("Common");
		}
		return b;
	}

	public void act(Card c) {
		JButton pb;

		if (bd.getTurn() == 1)
			addLog("You cannot attack on the first turn.");

		else {
			if (bd.getCurrentPlayer().equals(bd.getPlayer1())) {
				if (bd.getPlayer2().getPlayed().size() == 0) {
					pb = new JButton(bd.getPlayer2().getName() + ":   Life Points: " + bd.getPlayer2().getLifePoints()
							+ "     Coins: " + bd.getPlayer2().getCurrentCoins() + "/" + bd.getPlayer2().getMaxCoins());
					pb.addActionListener(e -> attackPlayer(bd.getPlayer2(), (Creature) c));
					played1List.forEach(b -> b.removeActionListener(actListener));
					played1List.forEach(b -> b.addActionListener(e -> refreshGUI()));
					addLog("Click the other player's button to attack the player, or click the card again to cancel.");
					player2.removeAll();
					player2.add(pb, BorderLayout.NORTH);
					player2.add(player2PlayedCrd, BorderLayout.CENTER);

					pack();
					setVisible(true);
				} else {
					prepareAttack(bd.getPlayer1(), c);
				}
			}

			else if (bd.getCurrentPlayer().equals(bd.getPlayer2())) {
				if (bd.getPlayer1().getPlayed().size() == 0) {
					pb = new JButton(bd.getPlayer1().getName() + ":   Life Points: " + bd.getPlayer1().getLifePoints()
							+ "     Coins: " + bd.getPlayer1().getCurrentCoins() + "/" + bd.getPlayer1().getMaxCoins());
					pb.addActionListener(e -> attackPlayer(bd.getPlayer1(), (Creature) c));
					played2List.forEach(b -> b.removeActionListener(actListener));
					played2List.forEach(b -> b.addActionListener(e -> refreshGUI()));
					addLog("Click the other player's button to attack the player, or click the card again to cancel.");
					player1.removeAll();
					player1.add(pb, BorderLayout.NORTH);
					player1.add(player1PlayedCrd, BorderLayout.CENTER);
					pack();
					setVisible(true);
				} else {
					prepareAttack(bd.getPlayer2(), c);
				}
			}
		}
	}

	private void prepareAttack(Player p, Card c) {
		player1PlayedCrd.removeAll();
		player2PlayedCrd.removeAll();

		played1List.clear();
		played2List.clear();

		if (p.equals(bd.getPlayer1())) {
			bd.getPlayer1().getPlayed().forEach(cd -> played1List.add(createBlankButton(cd)));
			played1List.forEach(b -> b.addActionListener(e -> refreshGUI()));
			played1List.forEach(b -> player1PlayedCrd.add(b));

			bd.getPlayer2().getPlayed().forEach(cd -> played2List.add(createBlankButton(cd)));
			played2List.forEach(b -> b.addActionListener(e -> attackCard((Creature) c, b)));
			played2List.forEach(b -> player2PlayedCrd.add(b));
			addLog("Choose card for " + c.getName() + " to attack, or click the card again to cancel.");
		} else if (p.equals(bd.getPlayer2())) {
			bd.getPlayer2().getPlayed().forEach(cd -> played2List.add(createBlankButton(cd)));
			played2List.forEach(b -> b.addActionListener(e -> refreshGUI()));
			played2List.forEach(b -> player2PlayedCrd.add(b));

			bd.getPlayer1().getPlayed().forEach(cd -> played1List.add(createBlankButton(cd)));
			played1List.forEach(b -> b.addActionListener(e -> attackCard((Creature) c, b)));
			played1List.forEach(b -> player1PlayedCrd.add(b));
			addLog("Choose card for " + c.getName() + " to attack, or click the card again to cancel.");
		}
		pack();
		setVisible(true);
	}

	public void attackPlayer(Player p, Creature c) {
		try {
			bd.attackPlayer(p, c);
			addLog(p.getName() + " took " + c.getAttack() + " damage!");
			refreshPlayed();
			refreshGUI();
		} catch (Exception ex) {
			refreshGUI();
			addLog(ex.toString());
		}
	}

	public void attackCard(Creature a, JButton b) {
		bd.currentSwitch();
		Creature d = (Creature) getButtonCard(b, bd.getCurrentPlayer());
		try {
			int dmg=bd.attack(a, d);
			a.decrementAtkCount();
			addLog(a.getName() + " attacked " + d.getName() + " for "
					+ ((int) (a.getAttack() * bd.effectivenessCheck(a, d))) + " damage!");
			addLog(bd.getCurrentPlayer().getName()+" took "+dmg+" damage!");
			bd.currentSwitch();
			if (d.getHealth() == 0) {
				bd.currentSwitch();
				String s = bd.getCurrentPlayer().getName();
				bd.discard(d, bd.getCurrentPlayer());
				bd.currentSwitch();
				addLog(bd.getCurrentPlayer().getName() + "'s " + a.getName() + " defeated " + s + "'s " + d.getName()
						+ "!");
			}
			refreshPlayed();
			refreshGUI();

		} catch (Exception e) {
			bd.currentSwitch();
			addLog(e.toString());
		}
	}

	public void playCreature(Card c, JButton b) {
		try {
			bd.checkCoins(c, bd.getCurrentPlayer());
			bd.playCard(c, bd.getCurrentPlayer());

			if (bd.getCurrentPlayer().equals(bd.getPlayer1()))
				played1List.add(b);
			else if (bd.getCurrentPlayer().equals(bd.getPlayer2()))
				played2List.add(b);
			bd.getCurrentPlayer().removeCurrentCoins(c);
			addLog(bd.getCurrentPlayer().getName() + " played " + c.getName());
			refreshGUI();
		} catch (Exception e) {
			addLog(e.toString());
		}
	}

	public void playEnhancement(Enhancement c) {
		if (bd.getCurrentPlayer().equals(bd.getPlayer1())) {
			played1List.forEach(b -> b.addActionListener(e -> enhance(c, b)));
		} else if (bd.getCurrentPlayer().equals(bd.getPlayer2())) {
			played2List.forEach(b -> b.addActionListener(e -> enhance(c, b)));
		}
		played1List.forEach(b -> b.removeActionListener(e -> enhance(c, b)));
		played2List.forEach(b -> b.removeActionListener(e -> enhance(c, b)));
		refreshGUI();

	}

	public void enhance(Enhancement e, JButton b) {
		Creature c = (Creature) getButtonCard(b, bd.getCurrentPlayer());
		try {
			if (bd.getCurrentPlayer().isEnhancementPlayed())
				throw new CardTypePlayedException("Enhancement");
			bd.checkCoins(e, bd.getCurrentPlayer());
			e.enhance(c);
			bd.playCard(e, bd.getCurrentPlayer());
			bd.getCurrentPlayer().removeCurrentCoins(e);
			addLog(bd.getCurrentPlayer().getName() + " enhanced " + c.getName() + " with " + e.getName() + ".");
			bd.discard(e, bd.getCurrentPlayer());

		} catch (Exception ex) {
			played1List.forEach(r -> r.removeActionListener(v -> enhance(e, r)));
			played2List.forEach(r -> r.removeActionListener(v -> enhance(e, r)));
			addLog(ex.toString());
		}
		if (bd.getCurrentPlayer().equals(bd.getPlayer1())) {
			played1List.remove(b);
			played1List.add(createPlayedButton(c));
		}

		else if (bd.getCurrentPlayer().equals(bd.getPlayer2())) {
			played2List.remove(b);
			played2List.add(createPlayedButton(c));
		}
		refreshPlayed();
		refreshGUI();
	}

	public Card getButtonCard(JButton b, Player p) {
		int index;
		Card c = null;
		if (p.equals(bd.getPlayer1())) {
			index = played1List.indexOf(b);
			c = bd.getPlayer1().getPlayed().get(index);
		}

		else if (p.equals(bd.getPlayer2())) {
			index = played2List.indexOf(b);
			c = bd.getPlayer2().getPlayed().get(index);
		}
		return c;
	}

	public Card getButtonCardAtk(JButton b, Player p) {
		int index;
		Card c = null;
		if (p.equals(bd.getPlayer1())) {
			index = played1List.indexOf(b) + 1;
			c = bd.getPlayer1().getPlayed().get(index);
		}

		else if (p.equals(bd.getPlayer2())) {
			index = played2List.indexOf(b) + 1;
			c = bd.getPlayer2().getPlayed().get(index);
		}
		return c;
	}

	public void addLog(String s) {
		logList.append("\n" + s);
	}

	public class thehandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			String s = "Default";
			s = event.getActionCommand();
			System.out.println(s);
			if (s.contains("p1newCreature")) {

				JOptionPane.showMessageDialog(logText, "Enter a name");
				String name = JOptionPane.showInputDialog(logText);
				System.out.println(name);

				JOptionPane.showMessageDialog(logText, "Enter a cost");
				String c = JOptionPane.showInputDialog(logText);
				int cost = Integer.parseInt(c);
				System.out.println(cost);

				JOptionPane.showMessageDialog(logText, "Enter an attack value");
				String a = JOptionPane.showInputDialog(logText);
				int attack = Integer.parseInt(a);
				System.out.println(attack);

				JOptionPane.showMessageDialog(logText, "Enter an health value");
				String h = JOptionPane.showInputDialog(logText);
				int health = Integer.parseInt(h);
				System.out.println(health);

				JOptionPane.showMessageDialog(logText, "Enter a type index");
				String t = JOptionPane.showInputDialog(logText);
				int type = Integer.parseInt(t);
				System.out.println(health);

				JOptionPane.showMessageDialog(logText, "Is it rare true or false?");
				String r = JOptionPane.showInputDialog(logText);
				boolean rarity = Boolean.parseBoolean(r);
				System.out.println(rarity);

				bd.getPlayer1().getPlayerDeck().addToDeck(new Creature(name, cost, attack, health, type, rarity));
				bd.getPlayer1().addToCardCollection(new Creature(name, cost, attack, health, type, rarity));
			}

			if (s.contains("p2newCreature")) {

				JOptionPane.showMessageDialog(logText, "Enter a name");
				String name = JOptionPane.showInputDialog(logText);
				System.out.println(name);

				JOptionPane.showMessageDialog(logText, "Enter a cost");
				String c = JOptionPane.showInputDialog(logText);
				int cost = Integer.parseInt(c);
				System.out.println(cost);

				JOptionPane.showMessageDialog(logText, "Enter an attack value");
				String a = JOptionPane.showInputDialog(logText);
				int attack = Integer.parseInt(a);
				System.out.println(attack);

				JOptionPane.showMessageDialog(logText, "Enter an health value");
				String h = JOptionPane.showInputDialog(logText);
				int health = Integer.parseInt(h);
				System.out.println(health);

				JOptionPane.showMessageDialog(logText, "Enter a type index");
				String t = JOptionPane.showInputDialog(logText);
				int type = Integer.parseInt(t);
				System.out.println(health);

				JOptionPane.showMessageDialog(logText, "Is it rare true or false?");
				String r = JOptionPane.showInputDialog(logText);
				boolean rarity = Boolean.parseBoolean(r);
				System.out.println(rarity);

				bd.getPlayer2().getPlayerDeck().addToDeck(new Creature(name, cost, attack, health, type, rarity));
				bd.getPlayer2().addToCardCollection(new Creature(name, cost, attack, health, type, rarity));

			}

			if (s.contains("p1newEnhancement")) {

				JOptionPane.showMessageDialog(logText, "Enter a name");
				String name = JOptionPane.showInputDialog(logText);
				System.out.println(name);

				JOptionPane.showMessageDialog(logText, "Enter a cost");
				String c = JOptionPane.showInputDialog(logText);
				int cost = Integer.parseInt(c);
				System.out.println(cost);

				JOptionPane.showMessageDialog(logText, "Enter an attack value");
				String a = JOptionPane.showInputDialog(logText);
				int attack = Integer.parseInt(a);
				System.out.println(attack);

				JOptionPane.showMessageDialog(logText, "Enter an health value");
				String h = JOptionPane.showInputDialog(logText);
				int health = Integer.parseInt(h);
				System.out.println(health);

				bd.getPlayer1().getPlayerDeck().addToDeck(new Enhancement(name, cost, attack, health));
				bd.getPlayer1().addToCardCollection(new Enhancement(name, cost, attack, health));

			}

			if (s.contains("p2newEnhancement")) {

				JOptionPane.showMessageDialog(logText, "Enter a name");
				String name = JOptionPane.showInputDialog(logText);
				System.out.println(name);

				JOptionPane.showMessageDialog(logText, "Enter a cost");
				String c = JOptionPane.showInputDialog(logText);
				int cost = Integer.parseInt(c);
				System.out.println(cost);

				JOptionPane.showMessageDialog(logText, "Enter an attack value");
				String a = JOptionPane.showInputDialog(logText);
				int attack = Integer.parseInt(a);
				System.out.println(attack);

				JOptionPane.showMessageDialog(logText, "Enter an health value");
				String h = JOptionPane.showInputDialog(logText);
				int health = Integer.parseInt(h);
				System.out.println(health);

				bd.getPlayer2().getPlayerDeck().addToDeck(new Enhancement(name, cost, attack, health));
				bd.getPlayer2().addToCardCollection(new Enhancement(name, cost, attack, health));

			}

			if (s.contains("printP1Collection")) {
				addLog(bd.getPlayer1().getName() + "'s Collection: ");
				bd.getPlayer1().getCardCollection().forEach(c -> addLog(c.toString()));
			}

			if (s.contains("printP2Collection")) {
				addLog(bd.getPlayer2().getName() + "'s Collection: ");
				bd.getPlayer2().getCardCollection().forEach(c -> addLog(c.toString()));
			}

		}

	}

}