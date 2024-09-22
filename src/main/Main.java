package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setResizable(false);
        window.setTitle("Hunting Slime");
        GamePanel gamepanel = new GamePanel();
        window.add(gamepanel);
        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        gamepanel.startGameThread();
        }
    }