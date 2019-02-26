package main.java.model;

import java.util.Comparator;

public class NodeCompare implements Comparator<SettingsNode> {
	
    @Override
    public int compare(SettingsNode o1, SettingsNode o2) {
        if (o1 instanceof SettingsNode && o2 instanceof SettingsNode) {
        	if (o1.label.compareTo(o2.label) > 0)
        		return 1;
        }
        
        return -1;
    }	
}