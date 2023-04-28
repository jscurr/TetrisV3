import javax.swing.JFrame;

public class App {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Tetrist");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GamePanel panel = new GamePanel();
        GameThread thread = new GameThread(panel);
        frame.getContentPane().add(panel);
        thread.start();
        frame.pack();
        frame.setVisible(true);  
    }
}
