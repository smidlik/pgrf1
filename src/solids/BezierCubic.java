package solids;

import transforms.Cubic;
import transforms.Point3D;

public class BezierCubic extends CubicData {

    public BezierCubic() {
        verticies.add(new Point3D(1.1,2,3));
        verticies.add(new Point3D(1.7,1.4,3));
        verticies.add(new Point3D(2.9,4.2,3));
        verticies.add(new Point3D(1.5,1.3,3));
        verticies.add(new Point3D(2.5,2.5,3));
        verticies.add(new Point3D(1.6,3.0,3));
        verticies.add(new Point3D(1.1,1,3));

        for (int i = 0; i < verticies.size()-3; i+=3) {
            cubics.add(new Cubic(Cubic.BEZIER,
                    verticies.get(i),verticies.get(i+1),
                    verticies.get(i+2),verticies.get(i+3)));
        }
    }

}
