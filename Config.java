import java.util.Scanner;

public class Config {
    Scanner in = new Scanner(System.in);
    private final int height = in.nextInt(); // столбец
    private final int width = in.nextInt(); //строка

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
