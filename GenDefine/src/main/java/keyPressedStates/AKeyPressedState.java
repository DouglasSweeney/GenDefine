package main.java.keyPressedStates;

import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

import main.java.KeyPressed;
import main.java.Main;
import main.java.model.SettingsList;

public class AKeyPressedState implements KeyPressedState {
    private Main         main;
	private KeyPressed   keyPressed;
	private SettingsList settingsList;
	
	public AKeyPressedState(Main main, KeyPressed keyPressed, SettingsList settingsList) {
		this.main = main;
		this.keyPressed = keyPressed;
		this.settingsList = settingsList;
	}
	
	@Override
	public void check(int keyCode) {
		boolean           found;
		if (keyCode == KeyEvent.VK_A) {
			String define = JOptionPane.showInputDialog("Enter a new definition to add.");
			if (define != null) {
		    	// Ensure that the item is not in the list already
		    	found = false;
		    	for (int i=0; i<settingsList.size() && found == false; i++) {
		    		if (settingsList.get(i).equals(define)) {
		    			found = true;
		    		}
		    	}
		    	
		    	if (found == false) {			    	
		    		// Add to the list
		    		main.setTextCurrentLabel(define);
		    		main.setAddingToList(true);
		    		settingsList.add(define);
/*
		    		items = new String[settingsList.size()];
		    		for (int i=0; i<settingsList.size(); i++) {
		    			item = settingsList.get(i).getLabel();
		    			items[i] = item;
		    		}
		    		comboBox = new JComboBox<String>(items);
*/
		    		main.setComboBoxItem(define);
		    	}
		    }
			
	        keyPressed.setState(keyPressed.getNormalState());	    
		}
	}
}
