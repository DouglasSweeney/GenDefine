package main.java;

import java.awt.event.KeyEvent;

import main.java.keyPressedStates.AKeyPressedState;
import main.java.keyPressedStates.KeyPressedState;
import main.java.keyPressedStates.NormalKeyPressedState;
import main.java.model.SettingsList;

public class KeyPressed {
	private KeyPressedState  currentState;
	private KeyPressedState  normalKeyPressedState;
	private KeyPressedState  aKeyPressedState;
	
	public KeyPressed(Main main, SettingsList settingsList) {
		normalKeyPressedState = new NormalKeyPressedState(this);
		aKeyPressedState = new AKeyPressedState(main, this, settingsList);

		setState(getNormalState());		
	}

	public KeyPressedState getNormalState() {
		return normalKeyPressedState;
	}
	
	public KeyPressedState getAState() {
		return aKeyPressedState;
	}
	
	public void setState(KeyPressedState state) {
		currentState = state;
	}
	
	public void check(int keyCode) {
		currentState.check(keyCode);
	}
	
	public static void main(String[] argv) {
		KeyPressed keyPressed = new KeyPressed(null, null);
		
		keyPressed.check(KeyEvent.VK_CONTROL);
		keyPressed.check(KeyEvent.VK_B);
		keyPressed.check(KeyEvent.VK_PERIOD);
	}
}
