package main.java.keyPressedStates;

import java.awt.event.KeyEvent;

import main.java.KeyPressed;

public class NormalKeyPressedState implements KeyPressedState {

	private KeyPressed keyPressed;
	
	public NormalKeyPressedState(KeyPressed keyPressed) {
		this.keyPressed = keyPressed;
	}
	
	@Override
	public void check(int keyCode) {
		if (keyCode == KeyEvent.VK_CONTROL) {
	        keyPressed.setState(keyPressed.getAState());	    
		}
		else {
		    keyPressed.setState(keyPressed.getNormalState());	
		}
	}
}
