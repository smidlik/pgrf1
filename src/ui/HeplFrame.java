package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HeplFrame extends JFrame {


        private JTextArea title;
        private JTextArea description;


        public HeplFrame(){
            init();
        }

        private void init() {
            setSize(400,600);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            setUndecorated(true);
            setVisible(true);

            Container pane = this.getContentPane();
            pane.setLayout(new BorderLayout());


            title=new JTextArea("Nápověda:");
            description=new JTextArea();

            title.setLineWrap(true);
            title.setEditable(false);
            title.setBackground(Color.GRAY);
            title.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            title.setFont(new Font("Courier",Font.BOLD,15));
            description.setLineWrap(true);
            description.setEditable(false);
            description.setBackground(Color.LIGHT_GRAY);

            description.setText("Ovládání:\n" +
                    "\n" +
                    "Šipky:         Pohyb kamery doprava/doleva/dopředu/dozadu\n" +
                    "Pravý Shift:   Zrychlení pohybu kamery\n" +
                    "Držení LMB:    Ovládání pohledu kamery\n" +
                    "I/J/K/L:       Ovládání pohledu kamery\n" +
                    "W/S/A/D:       Rotace prvků solids\n" +
                    "Q/E            Scale prvků solids\n" +
                    "Space:         Stoupání kamery\n" +
                    "Space + Ctrl:  Klesání kamery\n" +
                    "1:             view X\n"+
                    "2:             view Z\n" +
                    "3:             view Y\n" +
                    "R:             Reset kamery\n" +
                    "M:             Start/Stop animace\n" +
                    "\n" +
                    "\n" +
                    "Kliknutím zavři nápovědu....");

            pane.add(description,BorderLayout.CENTER);
            pane.add(title,BorderLayout.PAGE_START);

            description.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    dispose();

                }
            });
            pane.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                        dispose();
                }
            });



        }
    }


