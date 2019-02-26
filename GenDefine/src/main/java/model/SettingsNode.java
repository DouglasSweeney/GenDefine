package main.java.model;

import java.awt.Point;

public class SettingsNode {

	public static final int INVALID_LOWER_RIGHT=-1;
	public static final String WAS_MODIFIED = " - modified";
	
	public  String label;
	private boolean modified;
	private int x;
	private int y;
	private Point lowerRight = new Point();
	
	public SettingsNode(String label) {
		this.label = label;
		this.modified = false;
		this.x = -1;
		this.y = -1;
		this.lowerRight.x = INVALID_LOWER_RIGHT;
		this.lowerRight.y = INVALID_LOWER_RIGHT;
	}
	
  public SettingsNode(String label, String x, String y) {
    this.label = label;
    this.modified = false;
    this.x = new Integer(x).intValue();
    this.y = new Integer(y).intValue();
    this.lowerRight.x = INVALID_LOWER_RIGHT;
    this.lowerRight.y = INVALID_LOWER_RIGHT;
  }
  
  public SettingsNode(String label, String x, String y, String LrX, String LrY) {
    this.label = label;
    this.modified = false;
    this.x = new Integer(x).intValue();
    this.y = new Integer(y).intValue();
    this.lowerRight.x = new Integer(LrX);
    this.lowerRight.y = new Integer(LrY);
  }
  
	/**
	 * @return the label
	 */
	public String getLabel() {
		String string;
		
		if (modified == false) {
			string =  label;
		}
		else {
			string = label + WAS_MODIFIED;
		}
		
		return string;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the x
	 */
	public Integer getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public Integer getY() {
		return y;
	}
	
	/**
	 * @param lowerRight the lowerRight to set
	 */
	public void setLowerRight(Point lowerRight) {
		this.lowerRight.x = lowerRight.x;
		this.lowerRight.y = lowerRight.y;
	}


	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * @return the lowerRight
	 */
	public int getLowerRightX() {
		return lowerRight.x;
	}
	
	/**
	 * @return the lowerRight
	 */
	public int getLowerRightY() {
		return lowerRight.y;
	}
	/**
	 * @return the modified
	 */
	public boolean isModified() {
		return modified;
	}

	/**
	 * @param modified the modified to set
	 */
	public void setModified(boolean modified) {
		this.modified = modified;
	}
}
