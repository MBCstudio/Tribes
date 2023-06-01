package org.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Map;

public class Frame extends JFrame{
    private static final int SIZE = 10; // Rozmiar pojedynczego kwadratu (w pikselach)
    private static final int ROWS = 50; // Liczba wierszy
    private static final int COLS = 50; // Liczba kolumn

    private Color[][] boardColors; // Stores the color for each square
    private JLabel whiteLabel;
    private JLabel blackLabel;
    private JLabel greenLabel;
    private JLabel yellowLabel;
    private JLabel purpleLabel;
    private JLabel counterLabel;


    public Frame(Board board, int counter, int amount_black, int amount_white, int amount_green, int amount_yellow, int amount_purple) {
        setTitle("Plansza z kwadratami");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout()); // Ustawienie układu GridBagLayout
        setResizable(true); // Ustawienie możliwości rozciągania ramki

        boardColors = new Color[ROWS][COLS];
        updateBoardColors(board);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(100, 100, 0, 0)); // Dodanie odstępu od góry i lewej strony

        JPanel boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBoard(g);
            }
        };
        boardPanel.setPreferredSize(new Dimension(COLS * SIZE, ROWS * SIZE)); // Ustawienie preferowanego rozmiaru siatki

        JPanel dataPanel = new JPanel(new GridBagLayout()); // Ustawienie układu GridBagLayout dla panelu danych

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST; // Wyrównanie komponentów do lewej
        gbc.insets = new Insets(10, 10, 10, 10); // Ustawienie marginesów

        counterLabel = new JLabel("Dlugosc symulacji: " + counter);
        gbc.gridx = 0;
        gbc.gridy = 0;
        dataPanel.add(counterLabel, gbc);

        whiteLabel = new JLabel("Liczba rasy Bialej: " + amount_white);
        gbc.gridx = 0;
        gbc.gridy = 1;
        dataPanel.add(whiteLabel, gbc);

        blackLabel = new JLabel("Liczba rasy Czarnej: " + amount_black);
        gbc.gridx = 0;
        gbc.gridy = 2;
        dataPanel.add(blackLabel, gbc);

        greenLabel = new JLabel("Liczba rasy Zielonej: " + amount_green);
        gbc.gridx = 0;
        gbc.gridy = 3;
        dataPanel.add(greenLabel, gbc);

        yellowLabel = new JLabel("Liczba rasy Zoltej: " + amount_yellow);
        gbc.gridx = 0;
        gbc.gridy = 4;
        dataPanel.add(yellowLabel, gbc);

        purpleLabel = new JLabel("Liczba rasy Fioletowej: "+ amount_purple);
        gbc.gridx = 0;
        gbc.gridy = 5;
        dataPanel.add(purpleLabel, gbc);

        mainPanel.add(boardPanel, BorderLayout.WEST);
        mainPanel.add(dataPanel, BorderLayout.EAST);

        add(mainPanel); // Dodanie panelu głównego do ramki

        pack(); // Dopasowanie rozmiaru ramki do zawartości
        setVisible(true);
    }

    public void updateFrame(Board board, int counter, int amount_black, int amount_white, int amount_green, int amount_yellow, int amount_purple) {
        updateBoardColors(board);
        updateData(counter,amount_black,amount_white,amount_green,amount_yellow,amount_purple);
        revalidate();
        repaint();
    }

    private void updateBoardColors(Board board) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                Tribe tribe = board.board.get(new Position(i, j));
                if (tribe != null) {
                    switch (tribe.name) {
                        //Wypelnianie kwadratu wlasciwym kolorem
                        case "White" -> boardColors[j][i] = Color.WHITE;
                        case "Black" -> boardColors[j][i] = Color.BLACK;
                        case "Green" -> boardColors[j][i] = Color.GREEN;
                        case "Purple" -> boardColors[j][i] = new Color(160, 32, 240);
                        case "Yellow" -> boardColors[j][i] = Color.YELLOW;
                    }
                } else {
                    //Wypelnianie tla
                    boardColors[j][i] = Color.LIGHT_GRAY;
                }
            }
        }
    }
    private void updateData(int counter, int amount_black, int amount_white, int amount_green, int amount_yellow, int amount_purple){
        whiteLabel.setText("Liczba rasy Bialej: " + amount_white);
        counterLabel.setText("Dlugosc symulacji: " + counter);
        blackLabel.setText("Liczba rasy Czarnej: "+ amount_black);
        greenLabel.setText("Liczba rasy Zielonej: "+ amount_green);
        yellowLabel.setText("Liczba rasy Zoltej: "+ amount_yellow);
        purpleLabel.setText("Liczba rasy Fioletowej: "+ amount_purple);
    }


    private void drawBoard(Graphics g) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int x = col * SIZE;
                int y = row * SIZE;

                g.setColor(boardColors[row][col]);
                g.fillRect(x, y, SIZE, SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, SIZE, SIZE);
            }
        }
    }

//    public void updateBoard(Board board, int counter, int amount_black, int amount_white, int amount_green, int amount_yellow, int amount_purple) {
//        for (Map.Entry<Position, Tribe> entry : board.board.entrySet()) {
//            Position position = entry.getKey();
//            Tribe tribe = entry.getValue();
//            int row = position.y;
//            int col = position.x;
//
//            switch (tribe.name) {
//                case "White":
//                    boardColors[row][col] = Color.WHITE;
//                    break;
//                case "Black":
//                    boardColors[row][col] = Color.BLACK;
//                    break;
//                case "Green":
//                    boardColors[row][col] = Color.GREEN;
//                    break;
//                case "Purple":
//                    boardColors[row][col] = Color.MAGENTA;
//                    break;
//                case "Yellow":
//                    boardColors[row][col] = Color.YELLOW;
//                    break;
//            }
//        }
//        repaint(); // Odświeżenie panelu, aby zaktualizować wygląd siatki
//    }
}