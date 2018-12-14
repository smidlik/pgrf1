package solids;

import transforms.Cubic;
import transforms.Point3D;

public class FergusonCubic extends CubicData {

    public FergusonCubic() {
        verticies.add(new Point3D(1.3,2.4,1));
        verticies.add(new Point3D(1.7,1.4,3));
        verticies.add(new Point3D(1.9,2.,3));
        verticies.add(new Point3D(1.5,2.,3));
        verticies.add(new Point3D(1.0,2.,3));
        verticies.add(new Point3D(1.6,2.,3));
        verticies.add(new Point3D(1.,2.,3));

        for (int i = 0; i < verticies.size()-3; i+=3) {
            cubics.add(new Cubic(Cubic.FERGUSON,
                    verticies.get(i),verticies.get(i+1),
                    verticies.get(i+2),verticies.get(i+3)));
        }
    }
}
