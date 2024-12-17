package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class MiniGame extends JPanel implements ActionListener, KeyListener {

    private int playerX = 175;  // Начальное положение игрока по горизонтали
    private int playerY = 480;  // Начальное положение игрока по вертикали
    private int playerSpeed = 20;  // Скорость движения игрока
    private ArrayList<Integer> enemyX = new ArrayList<>();  // X-координаты врагов
    private ArrayList<Integer> enemyY = new ArrayList<>();  // Y-координаты врагов
    private int enemySpeed = 20;  // Скорость движения врагов
    private Timer timer;  // Таймер для обновления экрана
    private boolean gameOver = false;  // Флаг окончания игры
    private int score = 0;  // Счет игрока

    public MiniGame() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(100, this);  // Тут создаем таймер
        timer.start();  // В этой строчке его запускаем
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Mini Game");
        MiniGame game = new MiniGame();
        frame.add(game);
        frame.setSize(400, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.DARK_GRAY); // Заливаем фон черным цветом
        g.fillRect(0, 0, 400, 600);
        g.setColor(Color.YELLOW); // Белый цвет для фигуры игрока
        g.fillRect(playerX, playerY, 50, 50);  // Рисуем объект игрока

        for (int i = 0; i < enemyX.size(); i++) {
            g.setColor(Color.GREEN); // Используем цвет
            g.fillOval(enemyX.get(i), enemyY.get(i), 20, 20);
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Счет: " + score, 10, 30);  // Выводим счет игрока на экран
        if (gameOver) {
            g.setFont(new Font("Arial", Font.PLAIN, 40));
            g.drawString("Конец игры", 120, 300);  // Выводим надпись "Конец игры" при окончании игры
            timer.stop();  // Останавливаем таймер
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            for (int i = 0; i < enemyX.size(); i++) {
                enemyY.set(i, enemyY.get(i) + enemySpeed);  // Двигаем врагов вниз по экрану
                if (enemyY.get(i) >= 600) {
                    enemyX.remove(i);
                    enemyY.remove(i);
                    score++;  // Увеличиваем счет при уничтожении врага
                }
            }
            repaint();  // Перерисовываем экран
            if (enemyX.isEmpty()) {
                spawnEnemy();  // Создаем нового врага, если текущих нет на экране
            }
            checkCollision();  // Проверяем коллизию игрока с врагами
        }
    }

    public void spawnEnemy() {
        Random rand = new Random();
        int numEnemies = rand.nextInt(6) + 1; // Генерируем от 1 до 5 врагов за раз
        for (int i = 0; i < numEnemies; i++) {
            int x = rand.nextInt(350); // Генерируем случайную X-координату для врага
            int y = 0;
            enemyX.add(x);
            enemyY.add(y); // Добавляем врага в списки координат
        }
    }
    public void checkCollision() {
        Rectangle playerBounds = new Rectangle(playerX, playerY, 50, 50);  // Границы игрока
        for (int i = 0; i < enemyX.size(); i++) {
            Rectangle enemyBounds = new Rectangle(enemyX.get(i), enemyY.get(i), 20, 20);  // Границы врага
            if (playerBounds.intersects(enemyBounds)) {
                gameOver = true;  // Если произошло столкновение, игра заканчивается
                break;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (!gameOver) {
            if (key == KeyEvent.VK_LEFT && playerX > 0) {
                playerX -= playerSpeed;  // Перемещаем игрока влево
            }
            if (key == KeyEvent.VK_RIGHT && playerX < 350) {
                playerX += playerSpeed;  // Перемещаем игрока вправо
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
