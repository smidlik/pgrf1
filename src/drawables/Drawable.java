package drawables;

import java.awt.Color;

import utils.Renderer;

public interface Drawable {

    void draw(Renderer renderer);

    void modifyLastPoint(int x, int y);

    int getColor();

    default int getFillColor(){
        return Color.BLACK.getRGB();
    }

    default void setColor(int color){}
}
