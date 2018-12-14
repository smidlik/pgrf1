package solids;

import java.awt.Color;
import java.util.List;

import drawables.Point;
import transforms.Point3D;

public interface Solid {

    // seznam bodů objektu
    List<Point3D> getVerticies();

    // indexy bodů společně propojených
    List<Integer> getIndicies();
    default Point3D getCentr(){
        return new Point3D();
    }

    default int getColorByEdge(int index){
        return Color.BLACK.getRGB();
    }

    default String axis(int index){return "";}

}
