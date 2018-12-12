package solids;

import transforms.Point3D;

public class Block extends SolidData {
    private double lenght;
    private double wight;
    private double height;

    public Block(double size) {
        lenght = size;
        height = size;
        wight = size;
    }

    public Block(double lenght, double wight, double height) {
        this.height = height;
        this.lenght = lenght;
        this.wight = wight;
        init();
    }

    private void init() {

        verticies.add(new Point3D(0, 0, 0));     // 0.
        verticies.add(new Point3D(0, wight, 0));    // 1.
        verticies.add(new Point3D(lenght, 0, 0));    //2
        verticies.add(new Point3D(lenght, wight, 0));    //3
        verticies.add(new Point3D(0, 0, height));     //4
        verticies.add(new Point3D(0, wight, height));    //5
        verticies.add(new Point3D(lenght, 0, height));    //6
        verticies.add(new Point3D(lenght, wight, height));   //7


        indicies.add(0);
        indicies.add(1);
        indicies.add(1);
        indicies.add(3);
        indicies.add(2);
        indicies.add(3);
        indicies.add(2);
        indicies.add(0);

        indicies.add(4);
        indicies.add(5);
        indicies.add(5);
        indicies.add(7);
        indicies.add(6);
        indicies.add(7);
        indicies.add(6);
        indicies.add(4);

        indicies.add(0);
        indicies.add(4);
        indicies.add(1);
        indicies.add(5);
        indicies.add(2);
        indicies.add(6);
        indicies.add(3);
        indicies.add(7);
    }
}
