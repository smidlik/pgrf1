package ui;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import drawables.Drawable;
import drawables.DrawableType;
import drawables.Line;
import drawables.Point;
import drawables.Polygon;
import utils.Renderer;

public class PgrfFrame extends JFrame implements MouseMotionListener {

    static int FPS = 1000 / 30;
    private BufferedImage img;
    static int width = 800;
    static int height = 600;
    private JPanel panel;
    private Renderer renderer;
    private int clickX, clickY;

    private List<Drawable> drawables;
    private Drawable drawable;
    private boolean firstClickLine = true;
    private DrawableType type = DrawableType.POLYGON;
    private boolean fillMode = false;

    public static void main(String... args) {
        PgrfFrame pgrfFrame = new PgrfFrame();
        pgrfFrame.img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        pgrfFrame.init(width, height);
    }

    private void init(int width, int height) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(width, height);
        setTitle("Počítačová grafika");
        drawables = new ArrayList<>();
        panel = new JPanel();
        add(panel);

        panel.addMouseMotionListener(this);
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!fillMode) {
                    if (type == DrawableType.LINE) {
                        // zadávání úsečky
                        if (firstClickLine) {
                            clickX = e.getX();
                            clickY = e.getY();
                            drawable = new Line(clickX, clickY, clickX, clickY);
                        } else {
                            drawable = null;
                            drawables.add(new Line(clickX, clickY, e.getX(), e.getY()));
                        }
                        firstClickLine = !firstClickLine;
                    }
                    if (type == DrawableType.POLYGON) {
                        if (drawable == null) {
                            drawable = new Polygon();
                        }
                        if (drawable instanceof Polygon) {
                            if (e.getClickCount() == 2){
                                finishPolygon();
                            } else {
                                ((Polygon) drawable).addPoint(
                                        new Point(e.getX(), e.getY()));
                            }
                        } else {
                            drawable = null;
                        }
                    }
                } else {
                    renderer.seedFill(e.getX(), e.getY(),
                            img.getRGB(e.getX(), e.getY()),
                            Color.BLACK.getRGB());
                }

                super.mouseClicked(e);
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ADD) {
                    // plus na numerické klávesnici
                }
                if (e.getKeyCode() == KeyEvent.VK_U) {
                    type = DrawableType.LINE;
                }
                if (e.getKeyCode() == KeyEvent.VK_N) {
                    type = DrawableType.POLYGON;
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    finishPolygon();
                }
                if (e.getKeyCode() == KeyEvent.VK_F) {
                    fillMode = !fillMode;
                }
                super.keyReleased(e);
            }
        });

        setLocationRelativeTo(null);

        renderer = new Renderer(img);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                draw();
            }
        }, 100, FPS);

        draw();
    }

    private void finishPolygon() {
        if (drawable != null) {
            if (drawable instanceof Polygon) {
                ((Polygon) drawable).setDone(true);
                drawables.add(drawable);
                drawable = null;
            }
        }
    }

    private void draw() {
        if (!fillMode) {
            img.getGraphics().fillRect(0, 0, img.getWidth(), img.getHeight()); //clear
        }

        for (Drawable drawable : drawables) {
            drawable.draw(renderer);
        }
        if (drawable != null) {
            drawable.draw(renderer);
        }

        panel.getGraphics().drawImage(img, 0, 0, null);
        panel.paintComponents(getGraphics());
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (drawable != null) {
            drawable.modifyLastPoint(e.getX(), e.getY());
        }
    }
}
