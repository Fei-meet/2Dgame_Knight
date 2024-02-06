package Ass2;


import javafx.scene.layout.BackgroundImage;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.xml.stream.events.StartElement;

//      ---------------------------------MainFrame.java-------------------------------------
//      |                                                                                  |
//      |                  This is Assignment2 By group 8                                  |
//      |              Group member: Kobe Liu (19029765)                                   |
//      |                            Jane Liu（20008036)                                   |
//      |                            Alex Liu (20008038)     *Leader                       |
//      |                                                                                  |
//      |          This is a 2D game and you will control a KNIGHT to kill the monster!    |
//      |                                                                                  |
//      |                W                                                                 |
//      |              A S D                                   F                           |
//      |           control movement                     pick up items                     |
//      |                                                                                  |
//      |         "MainFrame.java"Responsible for the menu of the game                     |
//      |    You can get more information in Help(in the game) and Document(a PDF in zip)  |
//      |                                                                                  |
//      |                             HAVE A GOOD GAME!                                    |
//      |                                                                                  |
//       ------------------------------------------------------------------------------------

public class MainFrame extends JFrame implements MouseListener {
    private static Image HELP = null;
    private static Image Background = null;

    static {
        try {
            HELP = ImageIO.read(new File("Images/HELP.png"));
            Background = ImageIO.read(new File("Images/Home.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ;

    private static boolean Start;

        JLabel start,help,exit;

        JPanel MainPanel;
    private boolean Help_Active = false;

    public MainFrame() {
//        start label
            start = new JLabel(new ImageIcon("Images/hh1.png"));
            start.setBounds(420,200,150,40);
            start.setEnabled(true);
            start.addMouseListener(this);
            this.add(start);
//        help label
            help = new JLabel(new ImageIcon("Images/hh2.png"));
            help.setBounds(420,300,150,40);
            help.setEnabled(true);
            help.addMouseListener(this);
            this.add(help);
//        help label
            exit = new JLabel(new ImageIcon("Images/hh3.png"));
            exit.setBounds(420, 400, 150, 40);
            exit.setEnabled(true);
            exit.addMouseListener(this);
            this.add(exit);

            MainPanel panel = new MainPanel();
            this.add(panel);

            this.setSize(640,640);
            this.setLocationRelativeTo(null);
            this.setUndecorated(true);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setVisible(true);
        }

        public static void main(String[] args) {
            new MainFrame();
        }

        class MainPanel extends JPanel{
            Image background;
            Image HELP;
            public MainPanel() {
                try {
                    background = ImageIO.read(new File("Images/Home.png"));
                    HELP = ImageIO.read(new File("Images/HELP.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                g.drawImage(background, 0, 0,640,640, null);
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getSource().equals(start)){
                Start = true;
                Main.main();
                dispose();
            }else if(e.getSource().equals(exit)){
                dispose();
                System.exit(0);
            }
        }

        //get Help to pressed the label!

        @Override
        public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub
            if(e.getSource().equals(help)){
                getGraphics().drawImage(HELP,0,0,640,640,null);
            }

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub
            getGraphics().drawImage(Background,0,0,640,640,null);
            start.setEnabled(false);
            start.setEnabled(true);
            help.setEnabled(false);
            help.setEnabled(true);
            exit.setEnabled(false);
            exit.setEnabled(true);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
