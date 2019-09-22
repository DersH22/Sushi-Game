package sushigame.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;

import comp401sushi.AvocadoPortion;
import comp401sushi.CrabPortion;
import comp401sushi.EelPortion;
import comp401sushi.IngredientPortion;
import comp401sushi.Nigiri;
import comp401sushi.Plate;
import comp401sushi.RedPlate;
import comp401sushi.RicePortion;
import comp401sushi.Roll;
import comp401sushi.Sashimi;
import comp401sushi.Sashimi.SashimiType;
import comp401sushi.SeaweedPortion;
import comp401sushi.ShrimpPortion;
import comp401sushi.Sushi;
import comp401sushi.TunaPortion;
import comp401sushi.YellowtailPortion;
import comp401sushi.Nigiri.NigiriType;
import comp401sushi.Plate.Color;

public class PlayerChefView extends JPanel implements ActionListener {

	private List<ChefViewListener> listeners;
	private Sushi kmp_roll;
	private Sushi crab_sashimi;
	private Sushi eel_nigiri;
	private int belt_size;
	
	/*
	 * variables for creating sushi
	 */
	private Plate.Color plateListSelection;
	private String sushiListSelection;
	private String type;
	private int posListSelection;
	private double sliderValue;
	
	
	/*
	 * instantiating plate color JComboBox
	 */
	Plate.Color[] plates;
	JComboBox plateList;
	
	
	/*
	 * instantiating sushi type JComboBox
	 */
	String[] sushiType;
	JComboBox sushiList;
	
	
	/*
	 * instantiating meat type JComboBox
	 */
	String[] sushiIng;
	JComboBox ingList;
	
	
	/*
	 * instantiating JSlider for amount of meat
	 * if sushi is Roll
	 */
	final int MIN = 0;
	final int MAX = 15;
	final int INIT = 10;
	JSlider ingredientAmount;
	Hashtable labelTable;
	
	
	/*
	 * instantiating JComboBox for position of plate
	 */
	JComboBox posList;
	
	
	
	public PlayerChefView(int belt_size) {
		this.belt_size = belt_size;
		listeners = new ArrayList<ChefViewListener>();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JButton sashimi_button = new JButton("Make red plate of crab sashimi at position 3");
		sashimi_button.setActionCommand("red_crab_sashimi_at_3");
		sashimi_button.addActionListener(this);
		add(sashimi_button);

		JButton nigiri_button = new JButton("Make blue plate of eel nigiri at position 8");
		nigiri_button.setActionCommand("blue_eel_nigiri_at_8");
		nigiri_button.addActionListener(this);
		add(nigiri_button);

		JButton roll_button = new JButton("Make gold plate of KMP roll at position 5");
		roll_button.setActionCommand("gold_kmp_roll_at_5");
		roll_button.addActionListener(this);
		add(roll_button);

		kmp_roll = new Roll("KMP Roll", new IngredientPortion[] {new EelPortion(1.0), new AvocadoPortion(0.5), new SeaweedPortion(0.2)});
		crab_sashimi = new Sashimi(Sashimi.SashimiType.CRAB);
		eel_nigiri = new Nigiri(Nigiri.NigiriType.EEL);
		
		
		/*
		 * creating button for custom sushi
		 */
		JButton create = new JButton("Make sushi!");
		create.setActionCommand("create button");
		create.addActionListener(this);
		add(create);
		
		
		/*
		create.setBounds(50,100,95,30);
		this.add(create);
		create.setActionCommand("create button");
		create.setSize(400,400);
	    create.setLayout(null);
	    create.setVisible(true);
	    */
		
		
		/*
		 * creating combo box for plate type
		 */
		plates = new Plate.Color[4];
		plates[0] = Color.RED;
		plates[1] = Color.GREEN;
		plates[2] = Color.BLUE;
		plates[3] = Color.GOLD;
		plateList = new JComboBox(plates);
		//plateList.setSelectedIndex(4);
		plateList.addActionListener(this);
		add(plateList);
		
		
		/*
		 * creating combo box for sushi type
		 */
		sushiType = new String[3];
		sushiType[0] = "Sashimi";
		sushiType[1] = "Nigiri";
		sushiType[2] = "Roll";
		sushiList = new JComboBox(sushiType);
		plateList.addActionListener(this);
		add(sushiList);
		
	
		
		/*
		 * creating combo box for kind of sushi
		 */
		sushiIng = new String[5];
		sushiIng[0] = "Crab";
		sushiIng[1] = "Eel";
		sushiIng[2] = "Yellowtail";
		sushiIng[3] = "Shrimp";
		sushiIng[4] = "Tuna";
		ingList = new JComboBox(sushiIng);
		ingList.addActionListener(this);
		add(ingList);
		
		
		
		/*
		 * creating slider for amount of ingredient if sushiType
		 * is roll
		 */
		// final int MIN = 0;
		// final int MAX = 15;
		// final int INIT = 10;
		ingredientAmount = new JSlider(JSlider.HORIZONTAL,
                MIN, MAX, INIT);
		// framesPerSecond.addChangeListener((ChangeListener) this);
		ingredientAmount.setMajorTickSpacing(10);
		ingredientAmount.setPaintTicks(true);
		
		labelTable = new Hashtable();
		labelTable.put( new Integer( 0 ), new JLabel("0") );
		labelTable.put( new Integer(15), new JLabel("1.5"));
		labelTable.put( new Integer( 1 ), new JLabel("1"));
		ingredientAmount.setLabelTable( labelTable );
		ingredientAmount.setPaintLabels(true);
		add(ingredientAmount);
		
		
		
		
		/*
		 * creating combo box for belt positions
		 */
		posList = new JComboBox();
		for (int i = 0; i < 20; i++) {
			posList.addItem(i);
		}
		posList.addActionListener(this);
		add(posList);
		
		
	}

	public void registerChefListener(ChefViewListener cl) {
		listeners.add(cl);
	}

	private void makeRedPlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleRedPlateRequest(plate_sushi, plate_position);
		}
	}

	private void makeGreenPlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleGreenPlateRequest(plate_sushi, plate_position);
		}
	}

	private void makeBluePlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleBluePlateRequest(plate_sushi, plate_position);
		}
	}
	
	private void makeGoldPlateRequest(Sushi plate_sushi, int plate_position, double price) {
		for (ChefViewListener l : listeners) {
			l.handleGoldPlateRequest(plate_sushi, plate_position, price);
		}
	}
	
	/*
	 * method for creating own sushi object
	 */
	private void makeCustomPlateRequest(Plate.Color color, String sushi, String type,
			double amount, int position) {
		
		Sushi item = null;
		IngredientPortion[] ingredients = new IngredientPortion[3];
		
		if (sushi.equals("Sashimi")) {
			
			switch (type) {
			
			case "Crab":
				
				item = new Sashimi(SashimiType.CRAB);
				break;
				
			case "Eel":
				
				item = new Sashimi(SashimiType.EEL);
				break;
				
			case "Yellowtail":
				
				item = new Sashimi(SashimiType.YELLOWTAIL);
				break;
				
			case "Shrimp":
				
				item = new Sashimi(SashimiType.SHRIMP);
				break;
				
			case "Tuna":
				
				item = new Sashimi(SashimiType.TUNA);
				break;
			
			}
			
		}
		
		if (sushi.equals("Nigiri")) {
			
			switch (type) {
			
			case "Crab":
				
				item = new Nigiri(NigiriType.CRAB);
				break;
				
			case "Eel":
				
				item = new Nigiri(NigiriType.EEL);
				break;
				
			case "Yellowtail":
				
				item = new Nigiri(NigiriType.YELLOWTAIL);
				break;
				
			case "Shrimp":
				
				item = new Nigiri(NigiriType.SHRIMP);
				break;
				
			case "Tuna":
				
				item = new Nigiri(NigiriType.TUNA);
				break;
			
			}
			
		}
		
		if (sushi.equals("Roll")) {
			
			switch (type) {
			
			case "Crab":
				
				ingredients[0] = new CrabPortion(amount);
				ingredients[1] = new RicePortion(amount);
				ingredients[2] = new SeaweedPortion(amount);
				item = new Roll("Crab Roll", ingredients);
				break;
				
			case "Eel":
				
				ingredients[0] = new EelPortion(amount);
				ingredients[1] = new RicePortion(amount);
				ingredients[2] = new SeaweedPortion(amount);
				item = new Roll("Eel Roll", ingredients);
				break;
			
			case "Yellowtail":
				
				ingredients[0] = new YellowtailPortion(amount);
				ingredients[1] = new RicePortion(amount);
				ingredients[2] = new SeaweedPortion(amount);
				item = new Roll("Yellowtail Roll", ingredients);
				break;
			
			case "Shrimp":
				
				ingredients[0] = new ShrimpPortion(amount);
				ingredients[1] = new RicePortion(amount);
				ingredients[2] = new SeaweedPortion(amount);
				item = new Roll("Shrimp Roll", ingredients);
				break;
				
			case "Tuna":
				
				ingredients[0] = new TunaPortion(amount);
				ingredients[1] = new RicePortion(amount);
				ingredients[2] = new SeaweedPortion(amount);
				item = new Roll("Tuna Roll", ingredients);
				break;
			
			}
			
		}
		
		
		/*
		 * adding sushi to plate, then adding it to belt
		 */
		switch (color) {
		
		case RED:
			
			for (ChefViewListener l : listeners) {
				l.handleRedPlateRequest(item, position);
			}
			break;
			
		case BLUE:
		
			for (ChefViewListener l : listeners) {
				l.handleBluePlateRequest(item, position);
			}
			break;
			
		case GREEN:
			
			for (ChefViewListener l : listeners) {
				l.handleGreenPlateRequest(item, position);
			}
			break;
			
		case GOLD:
			
			for (ChefViewListener l : listeners) {
				l.handleGoldPlateRequest(item, position, item.getCost());
			}
			break;
		
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "red_crab_sashimi_at_3":
			makeRedPlateRequest(crab_sashimi, 3);
			break;
		case "blue_eel_nigiri_at_8":
			makeBluePlateRequest(eel_nigiri, 8);
			break;
		case "gold_kmp_roll_at_5":
			makeGoldPlateRequest(kmp_roll, 5, 5.00);
		case "create button":
			
			/*
			 * accessing selected values from combo boxes
			 */
			plateListSelection = (Color) plateList.getSelectedItem();
			sushiListSelection = (String) sushiList.getSelectedItem();
			type = (String) ingList.getSelectedItem();
			sliderValue = ((double) (ingredientAmount.getValue() * 100)) / 100;
			posListSelection = (int) posList.getSelectedItem();
			
			// create sushi and plate
			makeCustomPlateRequest(plateListSelection, sushiListSelection, type,
					sliderValue, posListSelection);
		}
	}
}
