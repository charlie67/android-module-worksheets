package uk.ac.aber.dcs.cs31620.twoscreenapp;

/**
 ** Colour
 **
 ** @author Chris Loftus
 ** @version 22/11/2011.
 */

public class Colour {
	private int red = 0;
	private int green = 0;
	private int blue = 0;
	private int alpha = 255;
	
	public Colour(){}
	
	public Colour(int red, int green, int blue, int alpha){
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}
	
	public void setRed(int value){
		red = value;
	}
	
	public void setGreen(int value){
		green = value;
	}
	
	public void setBlue(int value){
		blue = value;
	}
	
	public void setAlpha(int value){
		alpha = value;
	}

    /**
     * Gets the colour as a single int
     * @return The colour
     */
	public int getColour(){
		int result = 0;

		// Using bitwise leftshift operators to shift alpha value 24 bits to the left filling in the rightmost part with zeros
		// This is the transparency part of argb
        result = result + (alpha << 24);
		// Similar to place the red, green and blue parts correctly
		result = result + (red << 16);
		result = result + (green << 8);
		result = result + blue;
		
		return result;
	}
}
