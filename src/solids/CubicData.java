package solids;

import transforms.Cubic;

import java.util.ArrayList;
import java.util.List;

public abstract class CubicData extends SolidData {
     protected List<Cubic> cubics;

    public CubicData() {
        cubics =  new ArrayList<>();
    }

    public List<Cubic> getCubics(){
        return cubics;
    }

}
