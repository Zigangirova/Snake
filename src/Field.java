
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Field extends JPanel implements ActionListener {

    private final int size = 400;
    private final int dotSize = 20;
    private final int allDots = 400;
    private int berryX;
    private int berryY;
    private int dots;
    private Timer timer;
    private Image head;
    private Image body;
    private Image berry;
    private int[] x = new int[allDots];
    private int[] y = new int[allDots];
    private boolean inGame = true;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private int score = 0;


    public Field() {
        setBackground(Color.white);
        image();
        game();
        addKeyListener(new Buttons());
        setFocusable(true);
    }


    public void game() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 60 - i * dotSize;
            y[i] = 60;
        }
        timer = new Timer(250, this);
        timer.start();
        createBerry();
    }


    public void createBerry() {
        berryX = new Random().nextInt(20) * dotSize;
        berryY = new Random().nextInt(20) * dotSize;
    }


    public void image() {
        ImageIcon h = new ImageIcon("head.jpg");
        head = h.getImage();
        ImageIcon bd = new ImageIcon("body.jpg");
        body = bd.getImage();
        ImageIcon br = new ImageIcon("berry.jpg");
        berry = br.getImage();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (inGame) {
            g.drawImage(berry, berryX, berryY, this);
            for (int i = 0; i < dots; i++) {
                if (i == 0) {
                    g.drawImage(head, x[i], y[i], this);
                } else {
                    g.drawImage(body, x[i], y[i], this);
                }
                String c = "Score " + score;
                g.setColor(Color.black);
                g.drawString(c, 370, 10);
            }
        } else {
            String a = ("GAME OVER");
            g.setColor(Color.red);
            g.drawString(a, 175, size / 2);
        }
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        if (left) {
            x[0] -= dotSize;
        }

        if (right) {
            x[0] += dotSize;
        }

        if (up) {
            y[0] -= dotSize;
        }

        if (down) {
            y[0] += dotSize;
        }
    }

    public void checkBerry() {
        if (x[0] == berryX && y[0] == berryY) {
            dots++;
            score = score + 1;
            createBerry();
        }
    }

    public void checkEdge() {
        for (int i = dots; i > 0; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
            }
        }

        if (x[0] > size) {
            inGame = false;
        }
        if (x[0] < 0) {
            inGame = false;
        }
        if (y[0] > size) {
            inGame = false;
        }
        if (y[0] < 0) {
            inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            move();
            checkBerry();
            checkEdge();
        }

        repaint();
    }

    class Buttons extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !right) {
                left = true;
                up = false;
                down = false;
            }

            if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            }

            if (key == KeyEvent.VK_UP && !down) {
                up = true;
                left = false;
                right = false;
            }

            if (key == KeyEvent.VK_DOWN && !up) {
                down = true;
                left = false;
                right = false;
            }
        }
    }
}
