package main.java.model;

public class SettingsNode {

	private String label;
	private int x;
	private int y;
	private int lowerRightX = -1;
	private int lowerRightY = -1;
	
  public SettingsNode(String label, Integer x) {
    this.label = label;
    this.x = x;
    this.y = -1;
  }
	
	public SettingsNode(String label, Integer x, Integer y) {
		this.label = label;
		this.x = x;
		this.y = y;
	}
	
	public SettingsNode(String label, Integer x, Integer y, Integer lowerRightX, Integer lowerRightY) {
		this.label = label;
		this.x = x;
		this.y = y;
		this.lowerRightX = lowerRightX;
		this.lowerRightY = lowerRightY;
	}
	
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
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
	public int getX() {
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
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}
	/**
	 * @return the lowerRightX
	 */
	public int getLowerRightX() {
		return lowerRightX;
	}

	/**
	 * @param lowerRightX the lowerRightX to set
	 */
	public void setLowerRightX(int lowerRightX) {
		this.lowerRightX = lowerRightX;
	}

	/**
	 * @return the lowerRightY
	 */
	public int getLowerRightY() {
		return lowerRightY;
	}

	/**
	 * @param lowerRightY the lowerRightY to set
	 */
	public void setLowerRightY(int lowerRightY) {
		this.lowerRightY = lowerRightY;
	}

}
