package test.system;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.script.ScriptException;

import main.java.model.SettingsList;
import main.java.model.SettingsNode;

public class FileContents {

  List<String> list = new ArrayList<String>();
  
  private String buildList(String contents) {
    boolean found = false;
    String string = "";
    String temp = "";
    StringTokenizer st = new StringTokenizer(contents);

    if (st.hasMoreTokens()) {
      string = st.nextToken();
      found = false;
      while (st.hasMoreTokens() && !found) {
        temp = st.nextToken();
        if (!temp.matches("[0-9]+")) {
          add(string);
          string = temp;
        }
        else {
          string += " ";
          string += temp;
          if (st.hasMoreTokens() == false)
            add(string);
        }
      }
    }

    return string;
  }
  
  public boolean find(String label) {
    String node;
    boolean found = false;
    
    for (int i=0; i<list.size() && !found; i++) {
      node = list.get(i);
      StringTokenizer st = new StringTokenizer(node);
      if (st.nextToken().equals(label))
        found = true;
    }
    
    return found;
  }
  
  public void readlines(String contents) {
    contents = contents.replace("define ", "");
    buildList(contents);  
  }
 
  public Integer getCoordinate(String label, Integer itemNumber) {
    Integer coordinate = SettingsNode.INVALID_LOWER_RIGHT;
    boolean found;
    String  node;
    StringTokenizer st = null;
    
    // Find the label in the list
    found = false;
    for (int i=0; i<list.size() && !found; i++) {
      node = list.get(i);
      st = new StringTokenizer(node);
      if (st.nextToken().equals(label))
        found = true;
    }

    if (found) {
      switch (itemNumber) {
        case 1:  if (st.hasMoreTokens()) coordinate = new Integer(st.nextToken());
                 break;
        case 2:  if (st.hasMoreTokens()) st.nextToken(); // Skip over first coordinate
                 if (st.hasMoreTokens()) coordinate = new Integer(st.nextToken());
                 break;  
        case 3:  if (st.hasMoreTokens()) st.nextToken(); // Skip over first coordinate
                 if (st.hasMoreTokens()) st.nextToken(); // Skip over second coordinate
                 if (st.hasMoreTokens()) coordinate = new Integer(st.nextToken());
                 break;
        case 4:  if (st.hasMoreTokens()) st.nextToken(); // Skip over first coordinate
                 if (st.hasMoreTokens()) st.nextToken(); // Skip over second coordinate
                 if (st.hasMoreTokens()) st.nextToken(); // Skip over third coordinate
                 if (st.hasMoreTokens()) coordinate = new Integer(st.nextToken());
                 break;
        default: coordinate = SettingsNode.INVALID_LOWER_RIGHT;
                 break;
      }
    }
    
    return coordinate;
  }
    
  private void add(String str) {
    list.add(str);
  }
  
  public static void main(String[] argv) throws IOException, ScriptException {
    String newScriptContents = "define capture 0 1 1366 768\n" +  
                               "define tab 50 50\n" + 
                               "define wait 2000\n";
    FileContents fileContents = new FileContents();
    SettingsList settingsList = null;
    Integer coordinate;
    boolean found;
    
    settingsList = new SettingsList("scripts/functional.script", null);
    settingsList.create();
    settingsList.build();

    // Add string to list of defines in output file
    fileContents.readlines(newScriptContents);    

    // Get the coordinates of capture (the first entry)
    found = fileContents.find("capture");
    if (found) {
      System.out.println("coordinates: <capture found==1>");
      coordinate = fileContents.getCoordinate("capture", 1); // Get first coordinate
      System.out.println("1 coordinate: " + coordinate);
      coordinate = fileContents.getCoordinate("capture", 2); // Get second coordinate
      System.out.println("2 coordinate: " + coordinate);
      coordinate = fileContents.getCoordinate("capture", 3); // Get third coordinate
      System.out.println("3 coordinate: " + coordinate);
      coordinate = fileContents.getCoordinate("capture", 4); // Get fourth coordinate
      System.out.println("4 coordinate: " + coordinate);
      coordinate = fileContents.getCoordinate("capture", 5); // Get non-existent coordinate
      System.out.println("5 coordinate: " + coordinate);
    }
    else {
      System.out.println("coordinates: <capture found==0>");
    }
    
    // Get the coordinates of tab (the middle entry)
    found = fileContents.find("tab");
    if (found) {
      System.out.println("coordinates: <tab found==1>");
      coordinate = fileContents.getCoordinate("tab", 1); // Get first coordinate
      System.out.println("1 coordinate: " + coordinate);
      coordinate = fileContents.getCoordinate("tab", 2); // Get second coordinate
      System.out.println("2 coordinate: " + coordinate);
      coordinate = fileContents.getCoordinate("tab", 3); // Get non-existent coordinate
      System.out.println("3 coordinate: " + coordinate);
      coordinate = fileContents.getCoordinate("tab", 4); // Get non-existent coordinate
      System.out.println("4 coordinate: " + coordinate);
      coordinate = fileContents.getCoordinate("tab", 5); // Get non-existent coordinate
      System.out.println("5 coordinate: " + coordinate);
    }
    else {
      System.out.println("coordinates: <tab found==0>");
    }
    
    // Get the coordinates of wait (the last entry)
    found = fileContents.find("wait");
    if (found) {
      System.out.println("coordinates: <wait found==1>");
      coordinate = fileContents.getCoordinate("wait", 1); // Get first coordinate
      System.out.println("1 coordinate: " + coordinate);
      coordinate = fileContents.getCoordinate("wait", 2); // Get second coordinate
      System.out.println("2 coordinate: " + coordinate);
      coordinate = fileContents.getCoordinate("wait", 3); // Get non-existent coordinate
      System.out.println("3 coordinate: " + coordinate);
      coordinate = fileContents.getCoordinate("wait", 4); // Get non-existent coordinate
      System.out.println("4 coordinate: " + coordinate);
      coordinate = fileContents.getCoordinate("wait", 5); // Get non-existent coordinate
      System.out.println("5 coordinate: " + coordinate);
    }
    else {
      System.out.println("coordinates: <wait found==0>");
    }
  }
}
