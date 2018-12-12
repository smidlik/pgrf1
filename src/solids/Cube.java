package solids;

import drawables.Point;
import transforms.Point3D;

public class Cube extends SolidData {


		/*
           7______6
		   /|    /|
		 4/_|___/5|
		 | 3|__|_ |2
		 | /   | /
		 |/____|/
		 0     1
		 */
private Point3D centr;
private Point3D position;

    public Point3D getCentr() {
        return centr;
    }

    @Override
    public Point3D getPosition() {
        return position;
    }

    public Cube(double size) {
        size = size/2;
        verticies.add(new Point3D(-size, -size, -size));     // 0.
        verticies.add(new Point3D(-size, size, -size));    // 1.
        verticies.add(new Point3D(size, -size, -size));    //2
        verticies.add(new Point3D(size, size, -size));    //3
        verticies.add(new Point3D(-size, -size, size));     //4
        verticies.add(new Point3D(-size, size, size));    //5
        verticies.add(new Point3D(size, -size, size));    //6
        verticies.add(new Point3D(size, size, size));   //7

        position = verticies.get(0);
        centr = new Point3D(size,size,size);

        indicies.add(0); indicies.add(1);
        indicies.add(1); indicies.add(3);
        indicies.add(2); indicies.add(3);
        indicies.add(2); indicies.add(0);

        indicies.add(4); indicies.add(5);
        indicies.add(5); indicies.add(7);
        indicies.add(6); indicies.add(7);
        indicies.add(6); indicies.add(4);

        indicies.add(0); indicies.add(4);
        indicies.add(1); indicies.add(5);
        indicies.add(2); indicies.add(6);
        indicies.add(3); indicies.add(7);
    }
}
