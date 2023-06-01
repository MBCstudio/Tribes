package org.example;

import java.util.*;

public class Init {
    public static void start_simulation() {
        Scanner scanner = new Scanner(System.in);
        int width = 4;
        int height = 4;
        Integer amount_black = 10;
        Integer amount_white = 0;
        Integer amount_green = 0;
        Integer amount_yellow = 0;
        Integer amount_purple = 0;
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
        board.displayBoard();
        System.out.println();
        //przesuwanie obiektów
        Board updatedBoard; //tymczasowa mapa
        for (int i = 0; i < 3; i++) {
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
                        System.out.println(winner.name);
                        updatedBoard.board.remove(position);
                        updatedBoard.newTribe(winner, x, y);
                    }
                }else {
                    usedPositions.add(position);
                    updatedBoard.newTribe(tribe, x, y); //dodawanie obiektu Tribe z nowymi wspolrzednymi do tymczasowej mapy
                }
            }
            board = updatedBoard;
            board.displayBoard();
            System.out.println();
        }
//        for (Position item : usedPositions) {
//            System.out.println(item.x + " " + item.y);
//        }
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

