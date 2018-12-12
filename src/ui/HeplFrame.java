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
                    "Pohyb kamery doprava/doleva/dopředu/dozadu: Šipky\n" +
                    "" +
                    "" +
                    "");

            pane.add(description,BorderLayout.CENTER);
            pane.add(title,BorderLayout.PAGE_START);

            description.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getButton()== MouseEvent.BUTTON1)
                        dispose();
                }
            });
            pane.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getButton()== MouseEvent.BUTTON1)
                        dispose();
                }
            });



        }
    }


