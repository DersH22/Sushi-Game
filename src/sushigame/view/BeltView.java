package sushigame.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import comp401sushi.Plate;
import sushigame.model.Belt;
import sushigame.model.BeltEvent;
import sushigame.model.BeltObserver;

public class BeltView extends JPanel implements BeltObserver {

	private Belt belt;
	private JButton[] belt_buttons;
	
	/*
	 * plate widget
	 */
	private PlateWidget pWidget;

	public BeltView(Belt b) {
		this.belt = b;
		belt.registerBeltObserver(this);
		
		/*
		 * "big widget" is BoxLayout
		 */
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel beltView = new JPanel();
		
		// belt section of widget is GridLayout
		beltView.setLayout(new GridLayout(belt.getSize(), 1));
	
		belt_buttons = new JButton[belt.getSize()];
		
		/*
		 * creating PlateWidget
		 */
		pWidget = new PlateWidget(belt);
		
		for (int i = 0; i < belt.getSize(); i++) {
			JButton pbutton = new JButton();
			pbutton.setMinimumSize(new Dimension(300, 20));
			pbutton.setPreferredSize(new Dimension(300, 20));
			pbutton.setOpaque(true);
			pbutton.setBackground(Color.GRAY);
			beltView.add(pbutton);
			belt_buttons[i] = pbutton;
			
			/*
			 * add action listener to each JButton/Plate
			 */
			pbutton.addActionListener(pWidget);
			pbutton.setActionCommand("empty belt");
			
		}
		
		/*
		 * adds PlateWidget to GUI
		 */
		add(beltView);
		add(pWidget);
		refresh();
	}

	@Override
	public void handleBeltEvent(BeltEvent e) {	
		refresh();
	}

	private void refresh() {
		for (int i=0; i<belt.getSize(); i++) {
			
			Plate p = belt.getPlateAtPosition(i);
			JButton pbutton = belt_buttons[i];

			if (p == null) {
				pbutton.setText("");
				pbutton.setBackground(Color.GRAY);
			} else {
				
				pbutton.setActionCommand(p.toString());
				
				pbutton.setText(p.toString());
				switch (p.getColor()) {
				case RED:
					pbutton.setBackground(Color.RED); break;
				case GREEN:
					pbutton.setBackground(Color.GREEN); break;
				case BLUE:
					pbutton.setBackground(Color.BLUE); break;
				case GOLD:
					pbutton.setBackground(Color.YELLOW); break;
				}
			}
		}
	}
}
