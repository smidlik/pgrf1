package ui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

import drawables.Point;
import solids.*;
import transforms.*;
import utils.Transformer;

public class PgrfWireFrame extends JFrame {

    static int FPS = 1000 / 30;
    static int width = 1000;
    static int height = 800;
    private JPanel panel;
    private JPanel controls;
    private JButton btnAxis;
    private JButton btnAnimated;
    private JButton btnSpiral;
    private JButton btnCubics;
    private JButton btnHelp;

    private JLabel labelCor;
    private JLabel lblShow;
    private Transformer transformer;
    private Camera camera;
    private List<Solid> solids;
    private Solid axis;
    private List<Solid> animated;
    private BufferedImage img;


    private int beginX, beginY;
    private double moveX, moveY, moveZ;
    private double speed;
    private boolean animationStarter;
    private boolean showAxis;
    private int sensitivity;
    private Mat4 model;

    public static void main(String[] args) {
        PgrfWireFrame frame = new PgrfWireFrame();
        frame.init(width, height);
    }

    private void init(int width, int height) {
        Container pane = this.getContentPane();
        pane.setLayout(new BorderLayout());
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        // nastavení frame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(width, height);
        setTitle("Drátový model");
        panel = new JPanel();
        controls = new JPanel();
        controls.setLayout(new BoxLayout(controls,BoxLayout.PAGE_AXIS));
        labelCor = new JLabel();
        lblShow = new JLabel("Show");
        btnAnimated=new JButton("Animated");
        btnAxis = new JButton("Axes");
        btnCubics=new JButton("Cubics");
        btnHelp = new JButton("Help");
        btnSpiral = new JButton("Spiral");
        controls.add(lblShow);
        controls.add(btnAxis);
        controls.add(btnAnimated);
        controls.add(btnCubics);
        controls.add(btnSpiral);
        controls.add(btnHelp);
        pane.add(labelCor,BorderLayout.NORTH);
        pane.add(panel,BorderLayout.CENTER);
        pane.add(controls,BorderLayout.LINE_START);

        btnAxis.addActionListener(e -> showAxis = !showAxis);
        btnHelp.addActionListener(e -> new HeplFrame());


        solids = new ArrayList<>();
        animated = new ArrayList<>();
        transformer = new Transformer(img);
        transformer.setProjection(new Mat4PerspRH(1, 1, 1, 100));
        camera = new Camera();
        model = new Mat4();
        animationStarter = false;
        showAxis = true;
        resetCamera();

        addAxis();
        solids.add(new Cube(1));
        //addAnimatedCubes(8);


        // listeners
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                beginX = e.getX();
                beginY = e.getY();
                super.mousePressed(e);
            }
        });
        sensitivity = 2500;
        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                camera = camera.addAzimuth((Math.PI / sensitivity) * (beginX - e.getX()));
                camera = camera.addZenith((Math.PI / sensitivity) * (beginY - e.getY()));
                beginX = e.getX();
                beginY = e.getY();
                super.mouseDragged(e);
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                //System.out.println(e.getKeyChar());
                if (e.isShiftDown())
                    speed = 0.3;
                else
                    speed = 0.1;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        camera = camera.forward(speed);
                        break;
                    case KeyEvent.VK_DOWN:
                        camera = camera.backward(speed);
                        break;
                    case KeyEvent.VK_LEFT:
                        camera = camera.left(speed);
                        break;
                    case KeyEvent.VK_RIGHT:
                        camera = camera.right(speed);
                        break;
                    case KeyEvent.VK_SPACE:
                        if(e.isControlDown())
                            camera = camera.up(speed);
                        else
                            camera = camera.down(speed);
                        break;
                    // TODO - camera.up(), camera.down()

                    case KeyEvent.VK_I:
                        camera = camera.addZenith(0.1);
                        break;
                    case KeyEvent.VK_J:
                        camera = camera.addAzimuth(0.1);
                        break;
                    case KeyEvent.VK_K:
                        camera = camera.addZenith(-0.1);
                        break;
                    case KeyEvent.VK_L:
                        camera = camera.addAzimuth(-0.1);
                        break;
                    case KeyEvent.VK_O:
                        break;
                    case KeyEvent.VK_M:
                        animationStarter = !animationStarter;
                        break;
                    case KeyEvent.VK_1:
                        camera = new Camera(new Vec3D(12, 2, -4),
                                -3, 0.4, 1.0, true);
                        break;
                    case KeyEvent.VK_2:
                        camera = new Camera(new Vec3D(-9, -6, -4),
                                -5, 0.4, 1.0, true);
                    case KeyEvent.VK_3:
                        camera = new Camera(new Vec3D(0, 0, -9),
                                -5, 1.57, 1.0, true);
                        break;
                    case KeyEvent.VK_A:
                        moveX -= 0.1;
                        break;
                    case KeyEvent.VK_W:
                        moveZ += 0.1;
                        break;
                    case KeyEvent.VK_S:
                        moveZ -= 0.1;
                        break;
                    case KeyEvent.VK_D:
                        moveX += 0.1;
                        break;
                    // TODO - moveY, rotateX, rotateY, scaleX, scaleY...

                    case KeyEvent.VK_R:
                        resetCamera();
                        break;
                    case KeyEvent.VK_P:
                        System.out.println("X:"+camera.getPosition().getX()+", Y:"+camera.getPosition().getY()+", Z:"+camera.getPosition().getZ());
                        System.out.println("Azimut:"+camera.getAzimuth()+", Radius:"+camera.getRadius()+", Zenith:"+camera.getZenith());

                        break;
                }
                super.keyPressed(e);
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {

                super.keyReleased(e);
            }
        });
        // timer pro refresh draw()
        setLocationRelativeTo(null);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                draw();
            }
        }, 100, FPS);

    }
    private void addAxis(){
        Axis axis = new Axis();
        for (int i = 0; i < axis.getVerticies().size(); i++) {
            Point3D point3D = axis.getVerticies().get(i);
            axis.getVerticies().set(i, point3D.mul(new Mat4Scale(3)));
        }
        this.axis = axis;
    }
    private void addAnimatedCubes(int count){
        for (int i = 0; i < count; i++) {
            Cube cube = new Cube(1);
            for (int v = 0; v < cube.getVerticies().size(); v++) {
                Point3D point3D = cube.getVerticies().get(v);
                Point3D newPoint = point3D
                        .mul(new Mat4Transl(0, 2, 0))
                        .mul(new Mat4RotZ((double) i * 2d * Math.PI / (double) count));
                cube.getVerticies().set(v, newPoint);
            }
            animated.add(cube);
        }
    }


    private void resetCamera() {
        // TODO SPRÁVNÉ HODNOTY
        moveX = 0;
        moveY = 0;
        moveZ = 0;
        camera = new Camera(new Vec3D(9, 9, 6.4),
                -2.35, -0.5, 1.0, true); //Hotovo
    }

    private void draw() {

        labelCor.setText("Camera position - X:"+Math.round(camera.getPosition().getX())+", Y:"+ Math.round(camera.getPosition().getY())+
                ", Z:"+Math.round(camera.getPosition().getZ())+", Azimut:"+ camera.getAzimuth()+", Zenith:"+camera.getZenith()+", Radius:"+camera.getAzimuth());

        // clear
        img.getGraphics().fillRect(0, 0, img.getWidth(), img.getHeight());

        //transformer.setModel(model); // todo úloha 3
        transformer.setView(camera.getViewMatrix());
        if(showAxis)transformer.drawWireFrame(axis);
        for (Solid solid : solids) {
            transformer.drawWireFrame(solid);
        }
        for (Solid solid : animated) {
            transformer.drawWireFrame(solid);
        }
        if(animationStarter)animation();
        panel.getGraphics().drawImage(img, 0, 0, null);
        panel.paintComponents(getGraphics());
    }
    private void animation(){
        Timer animation = new Timer();

        animation.schedule(new TimerTask() {
            @Override
            public void run() {

                for (Solid solid : solids) {
                    for (int v = 0; v < solid.getVerticies().size(); v++) {
                        Point3D point3D = solid.getVerticies().get(v);
                        Point3D newPoint = point3D
                                .mul(new Mat4RotZ(Math.PI/100));
                        solid.getVerticies().set(v, newPoint);

                    }
                }
/*              //Pruchod polem animací
                for (Solid solid : animated) {
                    for (int v = 0; v < solid.getVerticies().size(); v++) {
                        Point3D centr = solid.getCentr();
                        Point3D position = solid.getPosition();
                        Point3D point3D = solid.getVerticies().get(v);
                        Point3D newPoint = point3D
                                //.mul(new Mat4RotZ(Math.PI/100))
                                //.mul(new Mat4RotY(Math.PI/100))
                                .mul(new Mat4Transl(position.getX(),position.getY()-2,position.getZ()))
                                .mul(new Mat4RotZ(Math.PI/100))
                                .mul(new Mat4Transl(position.getX(),position.getY()+2,position.getZ()));
                        solid.getVerticies().set(v, newPoint);
                    }
                 }

//

                for (int v = 0; v < solids.get(0).getVerticies().size(); v++) {
                    Point3D point3D = solids.get(0).getVerticies().get(v);
                    Point3D newPoint = point3D.mul(new Mat4RotXYZ(0.01,0.01,0.01));
                    //Point3D newPoint = point3D.withW(15);
                    solids.get(0).getVerticies().set(v, newPoint);
                }
                for (Solid solid : animated) {
                    for (int v = 0; v < solid.getVerticies().size(); v++) {
                        Point3D point3D = solid.getVerticies().get(v);
                        //Point3D newPoint = point3D.mul(new Mat4RotXYZ(0.01,0.01,0.01));
                        Point3D newPoint = point3D.withW(15);
                        solid.getVerticies().set(v, newPoint);
                    }
                }
//*/
            }
        },FPS);
    }
}
