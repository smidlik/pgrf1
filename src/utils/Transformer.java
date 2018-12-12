package utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Optional;

import solids.Axis;
import solids.Cube;
import solids.CubicData;
import solids.Solid;
import transforms.*;

public class Transformer {

    private BufferedImage img;

    private Renderer renderer;

    private Mat4 model;
    private Mat4 view;
    private Mat4 projection;

    private String axis;

    public Transformer(BufferedImage img) {

        this.img = img;
        this.model = new Mat4Identity();
        this.view = new Mat4Identity();
        this.projection = new Mat4Identity();
        renderer = new Renderer(img);
    }

    // FUNKCE Drátový model
    public void drawWireFrame(Solid solid) {
        // výsledná matice zobrazení
        Mat4 matFinal;
        if (solid instanceof Axis){
            // Axis nenásobíme MODELEM
            matFinal = view.mul(projection);
        } else {
            matFinal = model.mul(view).mul(projection);
        }

        if (solid instanceof CubicData){
            CubicData cubicData = (CubicData) solid;

            Point3D a,b;
            for (Cubic cubic : cubicData.getCubics()) {
                a = cubic.compute(0);
                for (double i = 0.02; i < 1; i+=0.02) {
                    b=cubic.compute(i);
                    transformEdge(matFinal,a,b,solid.getColorByEdge(0));
                    a=b;
                }
                b = cubic.compute(1);
                transformEdge(matFinal,a,b,solid.getColorByEdge(0));
            }

            return;
        }
        // první index: 1. bod, druhý index: druhý bod úsečky
        for (int i = 0; i < solid.getIndicies().size(); i += 2) {
            Point3D p1 = solid.getVerticies().get(solid.getIndicies().get(i));
            Point3D p2 = solid.getVerticies().get(solid.getIndicies().get(i + 1));
            if(solid instanceof Axis)axis=solid.axis(i/2);
            else axis = null;
            transformEdge(matFinal, p1, p2, solid.getColorByEdge(i / 2));
            //Vykreslení x,y pro osy<


        }
    }

    private void transformEdge(Mat4 mat, Point3D p1, Point3D p2, int color) {
        // 1.) vynásobit body maticí
        p1 = p1.mul(mat);
        p2 = p2.mul(mat);

        // 2.) ořez dle W bodů
        if (p1.getW() <= 0 && p2.getW() <= 0) return;

        // 3.) tvorba z vektorů dehomogenizací (Point3D.dehomog())
        Optional<Vec3D> vo1 = p1.dehomog();
        Optional<Vec3D> vo2 = p2.dehomog();

        if (!vo1.isPresent() || !vo2.isPresent())
            return;




        Vec3D v11 = vo1.get();
        Vec3D v22 = vo2.get();

// ořezání hodnot mimo X<-1,1> Y<-1,1> Z<0,1>
        if (Math.abs(v11.getX()) > 1 || Math.abs(v22.getX()) > 1) return;
        if (Math.abs(v11.getY()) > 1 || Math.abs(v22.getY()) > 1) return;
        if (v11.getZ() > 1 || v11.getZ() < 0 || v22.getZ() > 1 || v22.getZ() < 0) return;

        // 4.) přepočet souřadnic na výšku/šírku našeho okna (viewport)
        v11 = transformViewport(v11);
        v22 = transformViewport(v22);

        // 5.) výsledek vykreslit
        renderer.lineDDA((int) v11.getX(), (int) v11.getY(), (int) v22.getX(),
                (int) v22.getY(), color,axis);
    }

    public Vec3D transformViewport(Vec3D vec3D) {
        return  vec3D.mul(new Vec3D(1, -1, 1))
                     .add(new Vec3D(1, 1, 0))
                     .mul(new Vec3D(0.5 * (img.getWidth() - 1),
                        0.5 * (img.getHeight() - 1), 1));

    }

    // metody vykreslování
//    public void lineDDA(int x1, int y1, int x2, int y2, int color) {
//
//        float k, g, h; //G = PŘÍRŮSTEK X, H = PŘÍRŮSTEK Y
//        int dy = y2 - y1;
//        int dx = x2 - x1;
//        k = dy / (float) dx;
//
//        //určení řídící osy
//        if (Math.abs(dx) > Math.abs(dy)) {
//            g = 1;
//            h = k;
//            if (x1 > x2) { // prohození
//                int temp = x1;
//                x1 = x2;
//                x2 = temp;
//                temp = y1;
//                y1 = y2;
//                y2 = temp;
//            }
//        } else {
//            g = 1 / k;
//            h = 1;
//            if (y1 > y2) { //otočení
//                int temp = x1;
//                x1 = x2;
//                x2 = temp;
//                temp = y1;
//                y1 = y2;
//                y2 = temp;
//            }
//        }
//
//        float x = x1;
//        float y = y1;
//        int max = Math.max(Math.abs(dx), Math.abs(dy));
//
//        for (int i = 0; i <= max; i++) {
//            drawPixel(Math.round(x), Math.round(y), color);
//            x += g;
//            y += h;
//        }
//    }
//
//    private void drawPixel(int x, int y, int color) {
//        if (x < 0 || x >= 800) return;
//        if (y < 0 || y >= 600) return;
//        img.setRGB(x, y, color);
//        // TODO img.getGraphics().drawString("X", x, y);
//    }

    public void setModel(Mat4 model) {
        this.model = model;
    }

    public void setView(Mat4 view) {
        this.view = view;
    }

    public void setProjection(Mat4 projection) {
        this.projection = projection;
    }
}
