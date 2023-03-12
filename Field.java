import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Field extends JPanel {
    Board board = new Board();
    final static int POINT_SIZE = 40;
    Color c = new Color(0x00FF33);

    Field() {
        setSize(getWidth() * POINT_SIZE, getHeight() * POINT_SIZE);
        setBorder(new LineBorder(Color.BLACK, 1));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int x = 0; x < board.getConfig().getHeight(); x++) {
            for (int y = 0; y < board.getConfig().getWidth(); y++) {
                if (board.getGrid()[x][y].getState()) {
                    g.setColor(c);
                    g.fillOval(y * POINT_SIZE + 2, x * POINT_SIZE + 2, POINT_SIZE - 2, POINT_SIZE - 2);
                }
                g.setColor(Color.BLACK);
                g.drawRect(y * POINT_SIZE, x * POINT_SIZE, POINT_SIZE, POINT_SIZE);
            }
        }
    }

    public int getWidth() {
        return board.getConfig().getWidth() * POINT_SIZE;
    }


    public int getHeight() {
        return board.getConfig().getHeight() * POINT_SIZE;
    }

    public void updateBoard() {
        board.update();
    }
}
