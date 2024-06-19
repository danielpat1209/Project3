import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener,KeyListener {


    public class SqureBoard {
        int x;
        int y;

        SqureBoard(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }

    int boardWidth;
    int boardHeight;
    int SqureBoardSize = 25;
    SqureBoard snakeHead;
    ArrayList<SqureBoard> snakeBody;
    SqureBoard food;

    Random random;

    public void mainGameLoop(){
        new Thread(()->{
            while (true){
                move();
            }
        }
        ).start();
    }
    Timer gameLoop;


    int velocityX;
    int velocityY;
    boolean gameOver = false;


    SnakeGame(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new SqureBoard(5, 5);
        snakeBody = new ArrayList<SqureBoard>();
        food = new SqureBoard(10, 10);
        random = new Random();
        placeFood();
        velocityX = 1;
        velocityY = 0;



        Timer gameLoop = new Timer(100, this);
        gameLoop.start();


    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        for (int i = 0; i < boardWidth / SqureBoardSize; i++) {
            g.drawLine(i * SqureBoardSize, 0, i * SqureBoardSize, boardHeight);
            g.drawLine(0, i * SqureBoardSize, boardWidth, i * SqureBoardSize);
        }
        g.setColor(Color.red);
        g.fillRect(food.x * SqureBoardSize, food.y * SqureBoardSize, SqureBoardSize, SqureBoardSize);


        g.setColor(Color.green);
        g.fillRect(snakeHead.x * SqureBoardSize, snakeHead.y * SqureBoardSize, SqureBoardSize, SqureBoardSize);
        for (int i = 0; i < snakeBody.size(); i++) {
            SqureBoard snakePart = snakeBody.get(i);
            g.fillRect(snakePart.x * SqureBoardSize, snakePart.y * SqureBoardSize, SqureBoardSize, SqureBoardSize);
        }
    }

    public void placeFood() {
        food.x = random.nextInt(boardWidth / SqureBoardSize);
        food.y = random.nextInt(boardHeight / SqureBoardSize);
    }

    public boolean collision(SqureBoard squreBoard1, SqureBoard squreBoard2) {
        return squreBoard1.x == squreBoard2.x && squreBoard1.y == squreBoard2.y;

    }

    public void move() {
        if (collision(snakeHead, food)) {
            snakeBody.add(new SqureBoard(food.x, food.y));
            placeFood();
        }

        //The snake body
        for (int i = snakeBody.size() - 1; i >= 0; i--) {
            SqureBoard snakePart = snakeBody.get(i);
            if (i == 0) {
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            } else {
                SqureBoard prevSnakePart = snakeBody.get(i - 1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;


            }

        }

        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        for (int i = 0; i < snakeBody.size(); i++) {
            SqureBoard snakePart = snakeBody.get(i);
            if (collision(snakeHead, snakePart)) {
                gameOver = true;
            }
        }
        if (snakeHead.x * SqureBoardSize < 0 || snakeHead.x * SqureBoardSize > boardWidth || snakeHead.y * SqureBoardSize < 0 || snakeHead.y * SqureBoardSize > boardHeight) {
            gameOver = true;

        }

    }

    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            System.out.println("game over");
            gameLoop.stop();


        }
    }

    public void keyTyped(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            velocityX = 0;
            velocityY = -1;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != 1) {
            velocityX = 0;
            velocityY = 1;

        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != -1) {
            velocityX = -1;
            velocityY = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != 1) {
            velocityX = 1;
            velocityY = 0;

        }


    }


    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {

            velocityX = 0;
            velocityY = -1;

        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;

        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}

























