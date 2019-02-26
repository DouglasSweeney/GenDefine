package test.system;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.script.ScriptException;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import main.java.model.SettingsList;
import main.java.model.SettingsNode;

public class SettingsListTest {
  SettingsList settingsList = null;
  String filename;

  @Before
  public void Setup() throws IOException, ScriptException {
      settingsList = null;
      
      filename = "scripts/functional.script";
      settingsList = new SettingsList(filename, null);
      settingsList.create();
      settingsList.build();   
  } 

  @Test
  public void testHappyCase()  {
    int size = 0;
    
    size = settingsList.size();
      
    assertEquals(3, size);
  }
  
  @Test
  public void testGetItem() throws IOException, ScriptException {
    SettingsNode node = null;
    
    node = settingsList.get("capture");
    
    assertNotNull(node);
  }
  
  @Test
  public void testGetMiddleItem() throws IOException, ScriptException {
    SettingsNode node = null;
    
    node = settingsList.get("tab");
    
    assertNotNull(node);
  }
  
  @Test
  public void testGetLastItem() throws IOException, ScriptException {
    SettingsNode node = null;
    
    node = settingsList.get("wait");
    
    assertNotNull(node);
  }
  
  @Test
  public void testGetNonExistentItem() throws IOException, ScriptException {
    SettingsNode node = null;
    
    node = settingsList.get("wait2");
    
    assertNull(node);
  }

  @Test
  public void testOutputFileIsCreated() throws IOException, ScriptException {
    File file = new File("scripts/functional.newScript");
    
    file.delete();
    
    settingsList.write();
     
    assertEquals(true, file.exists());
  }  
  
  @Test
  public void testReadFileIntoString() throws IOException, ScriptException {
    String fileContents;
    File file = new File("scripts/functional.newScript");    
    
    settingsList.write();
     
    fileContents = FileUtils.readFileToString(file, "utf-8");
    
    assertNotEquals(0, fileContents.length());
  }  
  
  @Test
  public void testFirstDefineExists() throws IOException, ScriptException {
    String string;
    boolean found;
    FileContents fileContents = new FileContents();
    File file = new File("scripts/functional.newScript");    
    
    settingsList.write();
     
    string = FileUtils.readFileToString(file, "utf-8");

    // Add string to list of defines in output file
    fileContents.readlines(string);    
    
    found = fileContents.find("capture");
    
    assertEquals(true, found);
  } 
  
  @Test
  public void testFirstDefineFirstCoordinateExists() throws IOException, ScriptException {
    String string;
    Integer coordinate;
    Integer buildCoordinate;
    FileContents fileContents = new FileContents();
    File file = new File("scripts/functional.newScript");    
    
    settingsList.write();
     
    string = FileUtils.readFileToString(file, "utf-8");

    // Add string to list of defines in output file
    fileContents.readlines(string);    
    
    coordinate = fileContents.getCoordinate("capture", 1); // Get first coordinate
    buildCoordinate = settingsList.get("capture").getX();  // Get build coordinate
    
    assertEquals(coordinate, buildCoordinate);
  }  
  
  @Test
  public void testFirstDefineSecondCoordinateExists() throws IOException, ScriptException {
    String string;
    Integer coordinate;
    Integer buildCoordinate;
    FileContents fileContents = new FileContents();
    File file = new File("scripts/functional.newScript");    
    
    settingsList.write();
     
    string = FileUtils.readFileToString(file, "utf-8");

    // Add string to list of defines in output file
    fileContents.readlines(string);    
    
    coordinate = fileContents.getCoordinate("capture", 2); // Get second coordinate
    buildCoordinate = settingsList.get("capture").getY();  // Get build coordinate
    
    assertEquals(coordinate, buildCoordinate);
  }  
  
  @Test
  public void testFirstDefineThirdCoordinateExists() throws IOException, ScriptException {
    String string;
    Integer coordinate;
    Integer buildCoordinate;
    FileContents fileContents = new FileContents();
    File file = new File("scripts/functional.newScript");    
    
    settingsList.write();
     
    string = FileUtils.readFileToString(file, "utf-8");

    // Add string to list of defines in output file
    fileContents.readlines(string);    
    
    coordinate = fileContents.getCoordinate("capture", 3); // Get third coordinate
    buildCoordinate = settingsList.get("capture").getLowerRightX();  // Get build coordinate
    
    assertEquals(coordinate, buildCoordinate);
  }  
  
  @Test
  public void testFirstDefineFourthCoordinateExists() throws IOException, ScriptException {
    String string;
    Integer coordinate;
    Integer buildCoordinate;
    FileContents fileContents = new FileContents();
    File file = new File("scripts/functional.newScript");    
    
    settingsList.write();
     
    string = FileUtils.readFileToString(file, "utf-8");

    // Add string to list of defines in output file
    fileContents.readlines(string);    
    
    coordinate = fileContents.getCoordinate("capture", 4); // Get fourth coordinate
    buildCoordinate = settingsList.get("capture").getLowerRightY();  // Get build coordinate
    
    assertEquals(coordinate, buildCoordinate);
  }  
  
  @Test
  public void testFirstDefineUndefinedCoordinateExists() throws IOException, ScriptException {
    String string;
    Integer coordinate;
    Integer buildCoordinate;
    FileContents fileContents = new FileContents();
    File file = new File("scripts/functional.newScript");    
    
    settingsList.write();
     
    string = FileUtils.readFileToString(file, "utf-8");

    // Add string to list of defines in output file
    fileContents.readlines(string);    
    
    coordinate = fileContents.getCoordinate("capture", 5); // Get undefined coordinate
    buildCoordinate = SettingsNode.INVALID_LOWER_RIGHT;  // Get build coordinate
    
    assertEquals(coordinate, buildCoordinate);
  }  
  
  @Test
  public void testSecondDefineExists() throws IOException, ScriptException {
    String string;
    boolean found;
    FileContents fileContents = new FileContents();
    File file = new File("scripts/functional.newScript");    
    
    settingsList.write();
     
    string = FileUtils.readFileToString(file, "utf-8");

    // Add string to list of defines in output file
    fileContents.readlines(string);    
    
    found = fileContents.find("tab");
    
    assertEquals(true, found);
  } 
  
  @Test
  public void testSecondDefineFirstCoordinateExists() throws IOException, ScriptException {
    String string;
    Integer coordinate;
    Integer buildCoordinate;
    FileContents fileContents = new FileContents();
    File file = new File("scripts/functional.newScript");    
    
    settingsList.write();
     
    string = FileUtils.readFileToString(file, "utf-8");

    // Add string to list of defines in output file
    fileContents.readlines(string);    
    
    coordinate = fileContents.getCoordinate("tab", 1); // Get first coordinate
    buildCoordinate = settingsList.get("tab").getX();  // Get build coordinate
    
    assertEquals(coordinate, buildCoordinate);
  }  
  
  @Test
  public void testSecondDefineSecondCoordinateExists() throws IOException, ScriptException {
    String string;
    Integer coordinate;
    Integer buildCoordinate;
    FileContents fileContents = new FileContents();
    File file = new File("scripts/functional.newScript");    
    
    settingsList.write();
     
    string = FileUtils.readFileToString(file, "utf-8");

    // Add string to list of defines in output file
    fileContents.readlines(string);    
    
    coordinate = fileContents.getCoordinate("tab", 2); // Get second coordinate
    buildCoordinate = settingsList.get("tab").getY();  // Get build coordinate
    
    assertEquals(coordinate, buildCoordinate);
  }  
  
  @Test
  public void testSecondDefineUndefinedCoordinateExists() throws IOException, ScriptException {
    String string;
    Integer coordinate;
    Integer buildCoordinate;
    FileContents fileContents = new FileContents();
    File file = new File("scripts/functional.newScript");    
    
    settingsList.write();
     
    string = FileUtils.readFileToString(file, "utf-8");

    // Add string to list of defines in output file
    fileContents.readlines(string);    
    
    coordinate = fileContents.getCoordinate("tab", 3);   // Get third coordinate
    buildCoordinate = SettingsNode.INVALID_LOWER_RIGHT;  // Get build coordinate
    
    assertEquals(coordinate, buildCoordinate);
  } 
  
  @Test
  public void testLastDefineExists() throws IOException, ScriptException {
    String string;
    boolean found;
    FileContents fileContents = new FileContents();
    File file = new File("scripts/functional.newScript");    
    
    settingsList.write();
     
    string = FileUtils.readFileToString(file, "utf-8");

    // Add string to list of defines in output file
    fileContents.readlines(string);    
    
    found = fileContents.find("wait");
    
    assertEquals(true, found);
  } 
  
  @Test
  public void testLastDefineFirstCoordinateExists() throws IOException, ScriptException {
    String string;
    Integer coordinate;
    Integer buildCoordinate;
    FileContents fileContents = new FileContents();
    File file = new File("scripts/functional.newScript");    
    
    settingsList.write();
     
    string = FileUtils.readFileToString(file, "utf-8");

    // Add string to list of defines in output file
    fileContents.readlines(string);    
    
    coordinate = fileContents.getCoordinate("wait", 1); // Get first coordinate
    buildCoordinate = settingsList.get("wait").getX();  // Get build coordinate
    
    assertEquals(coordinate, buildCoordinate);
  }  
  
  @Test
  public void testLastDefineSecondCoordinateExists() throws IOException, ScriptException {
    String string;
    Integer coordinate;
    Integer buildCoordinate;
    FileContents fileContents = new FileContents();
    File file = new File("scripts/functional.newScript");    
    
    settingsList.write();
     
    string = FileUtils.readFileToString(file, "utf-8");

    // Add string to list of defines in output file
    fileContents.readlines(string);    
    
    coordinate = fileContents.getCoordinate("wait", 2); // Get second coordinate
    buildCoordinate = settingsList.get("wait").getY();  // Get build coordinate
    
    assertEquals(coordinate, buildCoordinate);
  }  
  
  @Test
  public void testLastDefineUndefinedCoordinateExists() throws IOException, ScriptException {
    String string;
    Integer coordinate;
    Integer buildCoordinate;
    FileContents fileContents = new FileContents();
    File file = new File("scripts/functional.newScript");    
    
    settingsList.write();
     
    string = FileUtils.readFileToString(file, "utf-8");

    // Add string to list of defines in output file
    fileContents.readlines(string);    
    
    coordinate = fileContents.getCoordinate("wait", 2);   // Get second coordinate
    buildCoordinate = SettingsNode.INVALID_LOWER_RIGHT;   // Get build coordinate
    
    assertEquals(coordinate, buildCoordinate);
  }  
  
  @Test
  public void testNonExistentDefineDoesNotExist() throws IOException, ScriptException {
    String string;
    boolean found;
    FileContents fileContents = new FileContents();
    File file = new File("scripts/functional.newScript");    
    
    settingsList.write();
     
    string = FileUtils.readFileToString(file, "utf-8");

    // Add string to list of defines in output file
    fileContents.readlines(string);    
    
    found = fileContents.find("doesNotExist");
    
    assertEquals(false, found);
  } 
  
}
