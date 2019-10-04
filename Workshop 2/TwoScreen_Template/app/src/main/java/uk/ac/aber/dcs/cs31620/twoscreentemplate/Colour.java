package uk.ac.aber.dcs.cs31620.twoscreentemplate;

public class Colour {
    private int red = 0;
    private int green = 0;
    private int blue = 0;
    private int alpha = 255;

    public Colour() {
    }

    public Colour(int red, int green, int blue, int alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public void setRed(int value) {
        red = value;
    }

    public void setGreen(int value) {
        green = value;
    }

    public void setBlue(int value) {
        blue = value;
    }

    public void setAlpha(int value) {
        alpha = value;
    }

    public int getColor() {
        int result = 0;

        result = result + (alpha << 24);
        result = result + (red << 16);
        result = result + (green << 8);
        result = result + blue;

        return result;
    }
}
