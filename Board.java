import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Board {
    private Cell[][] grid;
    private final ArrayList<String> previousConfig = new ArrayList<>();
    private final Config config = new Config();

    public Board() {
        try {
            initBoard();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        previousConfig.add(hash(grid));
    }

    private void initBoard() throws FileNotFoundException {
        grid = new Cell[config.getHeight()][config.getWidth()];

        String initConfig = "C:\\Users\\Мария\\IdeaProjects\\Game Of Life\\src\\gliner1.txt";
        System.out.println("Введите путь к файлу с начальной конфигурацией \n(или введите \"-\" для выбора конфигурации по умолчанию): ");
        String nextConfig = new Scanner(System.in).nextLine();
        if (!nextConfig.equals("-")) {
            initConfig = nextConfig;
        }
        for (int x = 0; x < config.getHeight(); x++)
            for (int y = 0; y < config.getWidth(); y++)
                grid[x][y] = new Cell();

        int[][] mass = scanFile(initConfig);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите начальные координаты конфигурации:");
        int a = scanner.nextInt();
        int b = scanner.nextInt();

        for (int x = 0; x < config.getHeight(); x++) {
            for (int y = 0; y < config.getWidth(); y++) {
                if (mass[x][y] == 1) {
                    grid[(x + b) % config.getHeight()][(y + a) % config.getWidth()].setNewState(true);
                    grid[(x + b) % config.getHeight()][(y + a) % config.getWidth()].updateState();
                }
            }
        }
    }


    private int[][] scanFile(String pathFile) throws FileNotFoundException {
        FileReader fr = new FileReader(pathFile);
        Scanner in = new Scanner(fr);
        int[][] mass = new int[config.getHeight()][config.getWidth()];
        int i = 0;
        while (in.hasNextLine()) {
            String s = in.nextLine();
            char[] chars = s.toCharArray();
            for (int j = 0; j < s.length(); j++) {
                mass[i][j] = chars[j] - 48;
            }
            i++;
        }
        return mass;
    }

    public void update() {
        prepare();
        commit();
    }

    private void prepare() {
        for (int h = 0; h < config.getHeight(); h++) {
            for (int w = 0; w < config.getWidth(); w++) {
                int nr = neighboursCountAt(h, w);
                if (nr < 2) {
                    grid[h][w].setNewState(false);
                } else if (nr > 3) {
                    grid[h][w].setNewState(false);
                } else if (nr == 3) {
                    grid[h][w].setNewState(true);
                } else if (nr == 2) {
                    grid[h][w].setNewState(grid[h][w].getState());
                }
            }
        }
    }

    int neighboursCountAt(int x, int y) {
        int count = 0;
        for (int dx = -1; dx < 2; dx++) {
            for (int dy = -1; dy < 2; dy++) {
                int nX = x + dx;
                int nY = y + dy;
                nX = (nX < 0) ? config.getHeight() - 1 : nX;
                nY = (nY < 0) ? config.getWidth() - 1 : nY;
                nX = (nX > config.getHeight() - 1) ? 0 : nX;
                nY = (nY > config.getWidth() - 1) ? 0 : nY;
                count += (isAlive(nX, nY)) ? 1 : 0;
            }
        }
        if (isAlive(x, y)) {
            count--;
        }
        return count;
    }

    private void commit() {
        for (int h = 0; h < config.getHeight(); h++) {
            for (int w = 0; w < config.getWidth(); w++) {
                grid[h][w].updateState();
            }
        }
        previousConfig.add(hash(grid));
    }

    private String hash(Cell[][] grid) {
        String str = "";
        for (int x = 0; x < config.getHeight(); x++) {
            for (int y = 0; y < config.getWidth(); y++) {
                if (grid[x][y].getState()) {
                    str += "1";
                } else str += "0";
            }
        }
        return str;
    }

    public boolean isAlive(int x, int y) {
        return grid[x][y].getState();
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public Config getConfig() {
        return config;
    }

    public boolean endOfTheGame() {
        String s = previousConfig.get(previousConfig.size() - 1);
        char[] chars = s.toCharArray();
        boolean flag = false;
        for (int i = 0; i < s.length(); i++) {
            if (chars[i] == '1') {
                flag = true;
            }
        }
        for (int i = 0; i < previousConfig.size() - 1; i++) {
            if (s.equals(previousConfig.get(i)))
                return true;
        }
        return !flag;
    }

}
