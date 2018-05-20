import javax.swing.*;

public class Window extends JFrame {
    public  Window(){
        setTitle("Змейка");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(440,  465);
        setLocation(500, 500);
        add(new Field());
        setVisible(true);
    }

    public static void main(String[] args) {
        Window w = new Window();
    }
}
