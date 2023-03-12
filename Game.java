import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Scanner;

public class Game extends JFrame {
    JLabel label = new JLabel("WELCOME!", SwingConstants.CENTER);
    Field panel = new Field();
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_PURPLE = "\u001B[35m";

    Game() {
        super("Game Of Life");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(panel.getWidth() + 15, panel.getHeight() + 53);
        setLocationRelativeTo(null);
        add(panel);
        add(label, BorderLayout.SOUTH);
        setVisible(true);
        setResizable(false);
    }

    void go() {
        panel.paint(getGraphics());
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Scanner in = new Scanner(System.in);

        System.out.println("Выберите режим: \n" +
                ANSI_PURPLE + "play" + ANSI_RESET + " - автоматическое выполение шагов\n" +
                ANSI_PURPLE + "step" + ANSI_RESET + " - выполнение шагов с помощью ввода \"+\" с клавиатуры");
        String a = in.nextLine();

        label.setText("THE GAME IS ON...");

        if (Objects.equals(a, "play")) {
            System.out.println("Выбран режим \"play\"");
            while (true) {
                panel.updateBoard();
                panel.repaint();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!panel.board.goNextGeneration()) break;
            }
        } else if (Objects.equals(a, "step")) {
            System.out.println("Выбран режим \"step\"");
            a = in.nextLine();
            while (Objects.equals(a, "+")) {
                panel.updateBoard();
                panel.repaint();
                if (!panel.board.goNextGeneration()) break;
                a = in.nextLine();
            }
        }
        label.setText("END OF THE GAME :(");

        System.out.println("Конец игры((((((");
    }
    public static void main(String[] args){
        System.out.println("Введите размерность поля (число строк и столбцов): ");
        new Game().go();
    }
}
