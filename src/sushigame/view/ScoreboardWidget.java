package sushigame.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import sushigame.model.Belt;
import sushigame.model.BeltEvent;
import sushigame.model.BeltObserver;
import sushigame.model.Chef;
import sushigame.model.SushiGameModel;

public class ScoreboardWidget extends JPanel implements BeltObserver, ActionListener {

	private SushiGameModel game_model;
	private JLabel display;
	
	/*
	 * variables to keep track of state of panel
	 */
	private String displayString;
	// private String refresh;
	
	public ScoreboardWidget(SushiGameModel gm) {
		
		game_model = gm;
		game_model.getBelt().registerBeltObserver(this);
		
		JPanel optionPanel = new JPanel();
		JButton balance = new JButton("balance");
		JButton weightSold = new JButton("weight sold");
		JButton weightSpoiled = new JButton("weight spoiled");
		
		
		displayString = "balance";
		
		display = new JLabel();
		display.setVerticalAlignment(SwingConstants.TOP);
		setLayout(new BorderLayout());
		add(display, BorderLayout.CENTER);
		display.setText(makeScoreboardHTML());
		
		
		balance.setActionCommand("balance");
		balance.addActionListener(this);
		weightSold.setActionCommand("weight sold");
		weightSold.addActionListener(this);
		weightSpoiled.setActionCommand("weight spoiled");
		weightSpoiled.addActionListener(this);
		
		optionPanel.add(balance);
		optionPanel.add(weightSold);
		optionPanel.add(weightSpoiled);
		optionPanel.setLayout(new GridLayout(0, 1));
		add(optionPanel, BorderLayout.NORTH);
		
	}

	private String makeScoreboardHTML() {
		String sb_html = "<html>";
		sb_html += "<h1>Scoreboard</h1>";

		// Create an array of all chefs and sort by balance.
		Chef[] opponent_chefs= game_model.getOpponentChefs();
		Chef[] chefs = new Chef[opponent_chefs.length+1];
		chefs[0] = game_model.getPlayerChef();
		for (int i=1; i<chefs.length; i++) {
			chefs[i] = opponent_chefs[i-1];
		}
		Arrays.sort(chefs, new HighToLowBalanceComparator());
		Arrays.sort(chefs, new LowToHighWeightComparator());
		Arrays.sort(chefs, new HighToLowWeightComparator());
		
		switch (displayString) {
		
		case "balance":
			
			for (Chef c : chefs) {
				sb_html += c.getName() + " ($" + Math.round(c.getBalance()*100.0)/100.0 + ") <br>";
			}
			break;
			
		case "weight sold":
			
			for (Chef c : chefs) {
				sb_html += c.getName() + " (" + Math.round(c.getSoldWeight()*100.0)/100.0 + " oz) <br>";
			}
			break;
			
		case "weight spoiled":
			
			for (Chef c : chefs) {
				sb_html += c.getName() + " (" + Math.round(c.getSpoiledAmount()*100.0)/100.0 + " oz) <br>";
			}
			break;
			
		}
		
		
		return sb_html;
	}

	public void refresh() {
		display.setText(makeScoreboardHTML());
	}
	
	@Override
	public void handleBeltEvent(BeltEvent e) {
		if (e.getType() == BeltEvent.EventType.ROTATE) {
			refresh();
		}		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch(e.getActionCommand()) {
		
		case "balance":
			
			displayString = "balance";
			display.setText(makeScoreboardHTML());
			break;
			
		case "weight sold":
			
			displayString = "weight sold";
			display.setText(makeScoreboardHTML());
			break;
			
		case "weight spoiled":
			
			displayString = "weight spoiled";
			display.setText(makeScoreboardHTML());
			break;
		
		}
		
	}

}
