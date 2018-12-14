package solids;

import transforms.Point3D;

public class Pyramid extends SolidData {

    private double diference;

    public Pyramid(double size1, double size2, double height) {

        diference = size1 - size2;

        verticies.add(new Point3D(0, 0, 0));     // 0.
        verticies.add(new Point3D(0, size1, 0));    // 1.
        verticies.add(new Point3D(size1, 0, 0));    //2
        verticies.add(new Point3D(size1, size1, 0));    //3

        verticies.add(new Point3D(diference, diference, height));     //4
        verticies.add(new Point3D(diference, size2+diference, height));    //5
        verticies.add(new Point3D(size2+diference, diference, height));    //6
        verticies.add(new Point3D(size2+diference, size2+diference, height));   //7


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
    public Pyramid(double size1, double height) {
        verticies.add(new Point3D(0, 0, 0));     // 0.
        verticies.add(new Point3D(0, size1, 0));    // 1.
        verticies.add(new Point3D(size1, 0, 0));    //2
        verticies.add(new Point3D(size1, size1, 0));    //3
        verticies.add(new Point3D(size1/2, size1/2, height));     //4
        verticies.add(new Point3D(size1/2, size1/2, height/2));     //Centr


        indicies.add(0); indicies.add(1);
        indicies.add(1); indicies.add(3);
        indicies.add(2); indicies.add(3);
        indicies.add(2); indicies.add(0);


        indicies.add(0); indicies.add(4);
        indicies.add(1); indicies.add(4);
        indicies.add(2); indicies.add(4);
        indicies.add(3); indicies.add(4);
    }

    @Override
    public Point3D getCentr() {
        return null;
    }

}
