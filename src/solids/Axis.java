package solids;

import java.awt.Color;

import transforms.Point3D;

public class Axis extends SolidData {

    public Axis() {
        verticies.add(new Point3D(0, 0, 0)); // 0.
        verticies.add(new Point3D(1, 0, 0)); // 1.
        verticies.add(new Point3D(0, 1, 0)); // 2.
        verticies.add(new Point3D(0, 0, 1)); // 3.

        indicies.add(0);        indicies.add(1);
        indicies.add(0);        indicies.add(2);
        indicies.add(0);        indicies.add(3);
    }

    @Override
    public int getColorByEdge(int index) {
        switch (index) {
            case 0:
                return Color.RED.getRGB();
            case 1:
                return Color.GREEN.getRGB();
            case 2:
                return Color.BLUE.getRGB();
            default:
                return Color.BLACK.getRGB();
        }
    }

    @Override
    public String axis(int index) {
        switch (index) {
            case 0:
                return "X";
            case 1:
                return "Y";
            case 2:
                return "Z";
            default:
                return "";
        }
    }
}
