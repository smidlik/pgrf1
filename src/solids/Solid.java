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

    Point3D getCentr();

    Point3D getPosition();

    default int getColorByEdge(int index){
        return Color.BLACK.getRGB();
    }

    default String axis(int index){return "";}

}
