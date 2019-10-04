package uk.ac.aber.dcs.cs31620.twoscreentemplate;

import android.view.View;
import android.widget.SeekBar;

public class ColourChangeListener implements SeekBar.OnSeekBarChangeListener {

    private View caller;
    private RGBType kind;
    private Colour color;

    public ColourChangeListener(View caller, RGBType kind, Colour color){
        this.caller = caller;
        this.kind = kind;
        this.color = color;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int value, boolean fromUser) {
        // The value will be between 0 .. 255
        // If true, fromUser indicated that the slider value was set by the user
        // sliding the SeekBar.
        switch (kind){
            case RED:
                color.setRed(value);
                break;
            case GREEN:
                color.setGreen(value);
                break;
            case BLUE:
                color.setBlue(value);
                break;
            case ALPHA:
                color.setAlpha(value);
        }

        int colorValue = color.getColor();
        caller.setBackgroundColor(colorValue);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

}
