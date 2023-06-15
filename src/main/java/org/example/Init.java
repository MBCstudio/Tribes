package org.example;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Init {
    public static Integer start_simulation() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        int square_len; //dlugosc krawedzi kwadratu
        Integer amount_black;
        Integer amount_white ;
        Integer amount_green;
        Integer amount_yellow;
        Integer amount_purple;
        System.out.println("Jaka dlugosc ma miec bok kwadratu: ");
        square_len = scanner.nextInt();
        if (square_len<0){
            System.out.println("Dlugosc boku planszy nie moze byc ujemana!");
            return -2;
        }
        System.out.println("Ile chcesz dodac osobnikow plemnienia czarnego: ");
        amount_black = scanner.nextInt();
        System.out.println("Ile chcesz dodac osobnikow plemnienia bialego: ");
        amount_white = scanner.nextInt();
        System.out.println("Ile chcesz dodac osobnikow plemnienia zielonego: ");
        amount_green = scanner.nextInt();
        System.out.println("Ile chcesz dodac osobnikow plemnienia zoltego: ");
        amount_yellow = scanner.nextInt();
        System.out.println("Ile chcesz dodac osobnikow plemnienia fioletowego: ");
        amount_purple = scanner.nextInt();
        if ((square_len*square_len)<(amount_black+amount_green+amount_purple+amount_white+amount_yellow)){ //bład: ilosc jednostek na planszy przekracza ilosc pól
            System.out.println("Podana liczba osobnikow nie miesci sie na planszy");
            System.out.println("Liczba wszystkich osobnikow: " + (amount_black+amount_green+amount_purple+amount_white+amount_yellow));
            System.out.println("Dostepna liczba mijesc na planszy to: " + (square_len*square_len));
            return -1;
        }
        if (amount_black<0 || amount_green<0 || amount_purple<0 || amount_yellow<0 || amount_white<0) {
            System.out.println("Liczba osobnikow nie moze byc ujemna");
            System.out.println("Populacja plemion:");
            System.out.println("Plemie czarnych: "+ amount_black);
            System.out.println("Plemie bialych: " + amount_white);
            System.out.println("Plemie zielonych: " + amount_green);
            System.out.println("Plemie zoltych: " + amount_yellow);
            System.out.println("Plemie bialych: " + amount_purple);
            return -3;
        }
       // if (())
        Board board = new Board(square_len, square_len);
        Map<String, Integer> tribeTypes = new HashMap();
        tribeTypes.put("black", amount_black);
        tribeTypes.put("white", amount_white);
        tribeTypes.put("green", amount_green);
        tribeTypes.put("yellow", amount_yellow);
        tribeTypes.put("purple", amount_purple);
//        for (String item: tribeTypes) {
//            for (int i=0; i<10; i++){
//                Tribe tribe = createTribe(item);
//                board.addTribe(tribe);
//            }
//        }
        for (String item : tribeTypes.keySet()) {
            for (int i = 0; i < tribeTypes.get(item); i++) {
                Tribe tribe = createTribe(item);
                board.addTribe(tribe);
            }
        }
        int counter = 0;
        Frame frame = new Frame(board, counter, amount_black, amount_white,amount_green,amount_yellow,amount_purple, square_len);
        frame.setMinimumSize(new Dimension(450+square_len*10,300+square_len*10));
        frame.setSize(450+square_len*11,300+square_len*11);
        board.displayBoard();
        System.out.println();
        //frame.pack();
        //przesuwanie obiektów
        Board updatedBoard; //tymczasowa mapa
        int tribe_counter = 5;
        while (tribe_counter > 1) {
            tribe_counter=0;
            counter++;
            List<Position> usedPositions = new ArrayList<>();//lista pozycji
            updatedBoard = new Board(square_len, square_len);
            for (Map.Entry<Position, Tribe> entry : board.board.entrySet()) {
                board.moveTribe(entry.getValue(), entry.getKey());//przesuwanie obiektów
                int x = entry.getKey().x; //zapamietywanie nowych wspolrzednych
                int y = entry.getKey().y;
                Tribe tribe = entry.getValue();
                createTribe(tribe.name); //tworzenie nowego obiektu Tribe
                Position position = new Position(x, y);
                Tribe existingTribe = updatedBoard.board.get(position);
                if (usedPositions.contains(position)) {
                    if (tribe.name == existingTribe.name) {
                        while (tribe.current_x == existingTribe.current_x && tribe.current_y == existingTribe.current_y){
                            while (usedPositions.contains(new Position(x,y))){
                                board.moveTribe(entry.getValue(), entry.getKey());
                                x = entry.getKey().x;
                                y = entry.getKey().y;
                            }
                            //existingTribe = updatedBoard.board.get(new Position(x,y));
                        }
                        updatedBoard.newTribe(tribe, x, y);
                        usedPositions.add(new Position(x, y));
                    }else {
                        List<Tribe> listOfFighters = new ArrayList<>();
                        listOfFighters.add(tribe);
                        listOfFighters.add(existingTribe);
                        Tribe winner = updatedBoard.tribeFight(listOfFighters);
                        //System.out.println(winner.name);
                        updatedBoard.board.remove(position);
                        updatedBoard.newTribe(winner, x, y);
                        for (Tribe item : listOfFighters){
                            if (item!=winner){
                                switch (item.name) {
                                    case "Black": amount_black--; break;
                                    case "White": amount_white--; break;
                                    case "Green": amount_green--; break;
                                    case "Yellow": amount_yellow--; break;
                                    case "Purple": amount_purple--; break;
                                }
                            }
                        }
                    }
                }else {
                    usedPositions.add(position);
                    updatedBoard.newTribe(tribe, x, y); //dodawanie obiektu Tribe z nowymi wspolrzednymi do tymczasowej mapy
                }
            }
            board = updatedBoard;
            frame.updateFrame(board, counter, amount_black, amount_white, amount_green, amount_yellow, amount_purple);
            TimeUnit.MILLISECONDS.sleep(500);
            board.displayBoard();
            System.out.println();
            if (amount_black !=0){
                tribe_counter++;
            }
            if (amount_purple !=0) {
                tribe_counter++;
            }
            if (amount_green !=0) {
                tribe_counter++;
            }
            if (amount_white !=0) {
                tribe_counter++;
            }
            if (amount_yellow!=0) {
                tribe_counter++;
            }
        };
        return 0;
    }


    private static Tribe createTribe(String tribeType) {
        switch (tribeType) {
            case "black": return new Black();
            case "white": return new White();
            case "green": return new Green();
            case "yellow": return new Yellow();
            case "purple": return new Purple();
        }
        return null;
    }
}

