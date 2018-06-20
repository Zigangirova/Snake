
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
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
    private int[] x;
    private int[] y;
    private boolean inGame;
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    private boolean boom;
    private int score;


    public Field() {
        timer = new Timer(250, this);
        this.init();
    }

    private void init() {
        this.x = new int[allDots];
        this.y = new int[allDots];
        this.inGame = true;
        this.left = false;
        this.right = true;
        this.up = false;
        this.down = false;
        this.score = 0;
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
        createBerry();
        timer.restart();
    }


    public void checkSnake() {
        for (int i = dots; i > 0 ; i--) {
            if (x[i] == berryX || y[i] == berryY){
                createBerry();
            }
        }
    }

    public void createBerry() {
        Random random = new Random();
        int kek = random.nextInt(20) * dotSize;
        int lol = 20 + random.nextInt(20) * dotSize;
        berryX = kek;
        berryY = lol;
        checkSnake();
    }

    public void image() {
        URL url = getClass().getResource("head.jpg");
        ImageIcon ii = new ImageIcon(url);
        head = ii.getImage();

        url = getClass().getResource("body.jpg");
        ImageIcon a = new ImageIcon(url);
        body = a.getImage();

        url = getClass().getResource("berry.jpg");
        ImageIcon b = new ImageIcon(url);
        berry = b.getImage();
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
                Font small = new Font("Helvetica", 1, 18);
                g.setColor(Color.black);
                g.setFont(small);
                g.drawString(c, 170, 19);
                g.drawLine(0, 20, 440, 20);
            }
        } else {
            String a = ("GAME OVER");
            Font gg = new Font("Helvetica", 1, 18);
            g.setColor(Color.red);
            g.setFont(gg);
            g.drawString(a, 145, size / 2);
            String b = ("Your score = " + score);
            g.setColor(Color.black);
            g.drawString(b, 135, 235);
            String c = ("Press ENTER to restart");
            g.drawString(c, 105, 265);
            if (boom) {
                String m = "You eat yourself!";
                g.setColor(Color.blue);
                g.drawString(m, 125, 295);
            }
            else {
                String l = "You crashed into wall!";
                g.setColor(Color.blue);
                g.drawString(l, 110, 295);
            }
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
            score = score + 10;
            createBerry();
        }
    }


    public void checkEdge() {
        boom = false;
        for (int i = dots; i > 0; i--) {
            if (i > 1 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
                boom = true;
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
        if (y[0] < 10) {
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

    private void restart() {
        init();
    }

    class Buttons extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    if (key == KeyEvent.VK_LEFT && !right) {
                        left = true;
                        up = false;
                        down = false;
                    }
                    break;

                case KeyEvent.VK_RIGHT:
                    if (key == KeyEvent.VK_RIGHT && !left) {
                        right = true;
                        up = false;
                        down = false;
                    }
                    break;

                case KeyEvent.VK_UP:
                    if (key == KeyEvent.VK_UP && !down) {
                        up = true;
                        left = false;
                        right = false;
                    }
                    break;

                case KeyEvent.VK_DOWN:
                    if (key == KeyEvent.VK_DOWN && !up) {
                        down = true;
                        left = false;
                        right = false;
                    }
                    break;
            }

            if (!inGame && key == KeyEvent.VK_ENTER) {
                restart();
            }

        }
    }
}
