package uk.ac.aber.dcs.cs31620.twoscreenapp;

/**
 ** ColourChangeListener
 **
 ** @author Chris Loftus
 ** @version 22/11/2011.
 */

import android.view.View;
import android.widget.SeekBar;

public class ColourChangeListener implements SeekBar.OnSeekBarChangeListener {

	private View caller;
	private RGBType kind;
	private Colour colour;

	public ColourChangeListener(View caller, RGBType kind, Colour colour){
		this.caller = caller;
		this.kind = kind;
		this.colour = colour;
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int value, boolean fromUser) {
		// The value will be between 0 .. 255
		switch (kind){
		case RED:
			colour.setRed(value);
			break;
		case GREEN:
			colour.setGreen(value);
			break;
		case BLUE:
			colour.setBlue(value);
			break;
		case ALPHA:
			colour.setAlpha(value);
		}
		
		int colourValue = colour.getColour();
		caller.setBackgroundColor(colourValue);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}

}
