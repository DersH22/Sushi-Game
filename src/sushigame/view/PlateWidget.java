																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																														package sushigame.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import comp401sushi.Plate;
import comp401sushi.Plate.Color;
import comp401sushi.Sushi;
import sushigame.model.Belt;

/*
 * widget to represent a plate on the belt
 */
public class PlateWidget extends JPanel implements ActionListener {
	
	private JLabel display;
	
	/*
	 * by taking a sushi object as a parameter we can take all the
	 * data we need from each sushi and display it in the GUI
	 */
	private Belt belt;
	
	public PlateWidget(Belt belt) {
		
		this.belt = belt;
		
		/*
		 * making JPanel
		 */
		display = new JLabel();
		display.setVerticalAlignment(SwingConstants.TOP);
		display.setLayout(new BorderLayout());
		add(display, BorderLayout.CENTER);
		display.setText("hi");
		
		/*
		 * setting size of JLabel
		 */
		display.setSize(300, 300);
		
	}

	/*
	 * what to do when a button/plate is clicked on
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		String cmd = arg0.getActionCommand();
		
		if (cmd.equals("empty belt")) {
			display.setText("");
		} else {
			
			Plate p = null;
			
			/*
			 * find correct plate we need
			 */
			for(int i = 0; i < belt.getSize(); i++) {
				
				if (belt.getPlateAtPosition(i) != null) {
					
					if (belt.getPlateAtPosition(i).toString().equals(cmd)) {
						
						p = belt.getPlateAtPosition(i);
						formatOutput(p);
						break;
						
					}
					
				}
				
			}
			
		}
		
	}
	
	/*
	 * helper method for output of plate information
	 */
	public void formatOutput(Plate p) {
		
		String s = p.toString();
		
		/*
		 * finding index where plate is located
		 */
		int index = 0;
		for (int i = 0; i < belt.getSize(); i++) {
			
			if (belt.getPlateAtPosition(i) != null && 
					belt.getPlateAtPosition(i).toString().equals(s)) {
				index = i;
			}
			
		}
		
		String color = "Color: " + p.getColor();
		String sushi = "Sushi type: " + sushiHelper(p);
		String chef = "Chef: " + p.getChef().getName();
		String age = "Age: " + belt.getAgeOfPlateAtPosition(index);
		
		
		display.setText("<html>" + color + "<br>" + 
				chef + "<br/>" + "<br>" + age + "<br/>" + "<br>" +
				sushi + "<br/>" + "</html>");
				
		
		//display.setText(color + "\n" + color);
		
	}
	
	public String sushiHelper(Plate p) {
		
		String s = p.getContents().getName();
		
		int count = 0;
		
		// counts length of ingredients array; if greater than 2, it's sushi
		for (int i = 0; i < p.getContents().getIngredients().length; i++) {
			count++;
		}
		
		String[] ingredients = new String[p.getContents().getIngredients().length];
		
		if (count > 2) {
			
			/*
			 * loops through ingredients array and gets name and amount of each ingredient
			 */
			for (int i = 0; i < p.getContents().getIngredients().length; i++) {
				
				String string = p.getContents().getIngredients()[i].getName() + ": " +
						p.getContents().getIngredients()[i].getAmount() + " oz";
				
				ingredients[i] = string;
				
			}
			
			s += "<br>" + "<ul>";
			
			for (int i = 0; i < ingredients.length; i++) {
				
				s += "<li>" + ingredients[i] + "</li>";
				
			}
			
			s += "</ul>" + "</html>";
			
		}
		
		return s;
		
	}

}
