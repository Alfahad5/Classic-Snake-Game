import javax.swing.*;

public class GameFrame extends JFrame { // Only the GUI for our Game
    GameFrame(){
    //GamePanel panel = new GamePanel();
    //this.add(panel); // instead of using this we use shortcut as follows!
        this.add(new GamePanel()); // instance of GamePanel
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();// it will size our frame according to no. of components in it Automatically
        this.setVisible(true);
        this.setLocationRelativeTo(null);// locates it to the middle
    }
}
