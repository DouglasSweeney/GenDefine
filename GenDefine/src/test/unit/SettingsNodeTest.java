package test.unit;


import static org.junit.Assert.*;

import org.junit.Test;

import main.java.model.SettingsNode;

public class SettingsNodeTest {

	@Test
	public void testHappyCase() {
		SettingsNode node = new SettingsNode("AAAA");
		
		assertNotEquals(null, node);
	}
}
