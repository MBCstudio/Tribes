package org.example;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Init {
    public static void start_simulation() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        int width = Frame.COLS;
        int height = Frame.ROWS;
        Integer amount_black = 20;
        Integer amount_white = 20;
        Integer amount_green = 20;
        Integer amount_yellow = 20;
        Integer amount_purple = 20;
        Board board = new Board(width, height);
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
        Frame frame = new Frame(board, counter, amount_black, amount_white,amount_green,amount_yellow,amount_purple);
        frame.setMinimumSize(new Dimension(450+width*10,300+height*10));
        frame.setSize(450+width*11,300+height*11);
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
            updatedBoard = new Board(width, height);
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

