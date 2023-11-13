import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
//import java.util.Timer; wrong package
import javax.swing.Timer;
//import java.awt.event.ActionListener;
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements ActionListener {
    static final int Screen_Width= 600;
    static final int Screen_Height= 600;
    static final int Unit_Size= 25;
    static final int delay= 105;
    static final int Game_Units= (Screen_Width*Screen_Height)/Unit_Size;
    final int x[]= new int[Game_Units];
    final int y[]= new int[Game_Units];
    int body= 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction='R';
    boolean running= false;
    Timer timer;
    Random random;
    GamePanel(){
        random= new Random();
        this.setPreferredSize( new Dimension(Screen_Width,Screen_Height));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame(){
        newApple();
        running= true;
        timer= new Timer(delay,this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(running){
            for(int i=0;i<Screen_Height/Unit_Size;i++){
                g.drawLine(i*Unit_Size,0,i*Unit_Size,Screen_Height);
                g.drawLine(0,i*Unit_Size,Screen_Width,i*Unit_Size);
            }
            // creates Apple
             g.setColor(Color.red);
             g.fillOval(appleX,appleY,Unit_Size,Unit_Size);

             for(int i=0;i<body;i++){
                 if(i==0){
                     g.setColor(Color.green);
                     g.fillRect(x[i],y[i],Unit_Size,Unit_Size);
                 } else{
                     g.setColor(new Color(45, 177, 8));
                     g.fillRect(x[i],y[i],Unit_Size,Unit_Size);
                 }
             }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free",Font.BOLD,40));
            FontMetrics metrics= getFontMetrics(g.getFont());
            g.drawString("SCORE :"+applesEaten,(Screen_Width-metrics.stringWidth("SCORE :"+applesEaten))/2,g.getFont().getSize());
        }
        else {
           gameOver(g);// g is graphics of this method
        }
    }
    public void newApple(){//  (Screen_Width/Unit_Size) sets the range for generating a num randomly and then * UnitSize to make it as big as 1 unit i.e 25 now
        appleX = random.nextInt((Screen_Width/Unit_Size))*Unit_Size;
        appleY = random.nextInt((Screen_Height/Unit_Size))*Unit_Size;
    }
    public void move(){
        for(int i= body;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }

        switch(direction){
            case 'U':
                y[0]=y[0]-Unit_Size;
                break;
            case 'D':
                y[0]=y[0]+Unit_Size;
                break;
            case 'L':
                x[0]=x[0]-Unit_Size;
                break;
            case 'R':
                x[0]=x[0]+Unit_Size;
                break;

        }
    }
    public void checkApple(){
        if((x[0]==appleX)&&(y[0]==appleY)){// if head touches apple
            body++;
            applesEaten++;
            newApple();
        }
    }
    public void checkCollisions(){
        for (int i=body;i>0;i--){
            if((x[0]==x[i]) && (y[0]==y[i])){// checks if head collides with body
                running= false;
            }
        }
        // head collides to left border
        if(x[0]<0) running = false;
        // head collides to right border
        if(x[0]>Screen_Width) running = false;
        // head collides to top border
        if(y[0]<0) running = false;
        // head collides to bottom border
        if(y[0]>Screen_Height) running = false;

        if(!running) timer.stop();
    }
    public void gameOver(Graphics g){
        // displays score at the end
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,40));
        FontMetrics metrics1= getFontMetrics(g.getFont());
        g.drawString("SCORE :"+applesEaten,(Screen_Width-metrics1.stringWidth("SCORE :"+applesEaten))/2,g.getFont().getSize());

        // text when GAME OVER
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics2= getFontMetrics(g.getFont());
        g.drawString("GAME OVER",(Screen_Width-metrics2.stringWidth("GAME OVER"))/2,Screen_Height/2);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){// K is capital for the parameter
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction!='R')
                        direction='L';
                    
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction!='L')
                        direction='R';
                    break;
                case KeyEvent.VK_UP:
                    if(direction!='D')
                        direction='U';
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction!='U')
                        direction='D';
                    break;
            }
        }
    }
}
