package drawables;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import utils.Renderer;

public class Polygon implements Drawable {

    List<Point> points;
    private boolean done;

    public Polygon() {
        points = new ArrayList<>();
    }

    public void addPoint(Point p) {
        points.add(p);
    }

    @Override
    public void draw(Renderer renderer) {
        if (!done) {
            if (points.size() > 1) {
                for (int i = 0; i < points.size(); i++) {
                    Point point1 = points.get(i);
                    Point point2 = points.get((i + 1) % points.size());
                    renderer.lineDDA(point1.getX(), point1.getY(),
                            point2.getX(), point2.getY(), getColor());
                }
            }
        } else {
            renderer.scanLine(points, getColor(), getFillColor());
        }
    }

    @Override
    public void modifyLastPoint(int x, int y) {
        // ignored
    }

    @Override
    public int getColor() {
        return Color.BLUE.getRGB();
    }

    @Override
    public int getFillColor() {
        return Color.YELLOW.getRGB();
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
