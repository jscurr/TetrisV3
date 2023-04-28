import java.awt.Color;

public enum Hue {
    COLOR1(192,15,28),
    COLOR2(192,91,15),
    COLOR3(28,192,15),
    COLOR4(168,13,102),
    COLOR5(229,193,0);

    int red;
    int blue;
    int green;

    Hue(int red, int blue, int green){
        this.red = red;
        this.blue = blue;
        this.green = green;
    }

    public int getRed(){ return this.red;}
    public int getGreen(){ return this.green;}
    public int getBlue(){ return this.blue;}
    public Color getColor(){return new Color(red,blue,green);}

}
