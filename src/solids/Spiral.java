package solids;

import transforms.Cubic;
import transforms.Point3D;

import java.awt.*;

public class Spiral extends CubicData {

    public Spiral() {
        for (double a = 0; a <= Math.PI * 8; a += 0.1) {
            double x = Math.cos(a);
            double y = Math.sin(a);
            double z = a / 20;
            verticies.add(new Point3D(x, y, z));
        }

        for (int j = 0; j < verticies.size()-3; j+=3) {
            cubics.add(new Cubic(Cubic.BEZIER,
                    verticies.get(j),verticies.get(j+1),
                    verticies.get(j+2),verticies.get(j+3)));
        }
    }

    @Override
    public int getColorByEdge(int index) {
        return Color.MAGENTA.getRGB();
    }

    @Override
    public Point3D getCentr() {
        return null;
    }

    @Override
    public Point3D getPosition() {
        return null;
    }
}
