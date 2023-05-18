package org.example;

import java.util.*;

public class Init {
    public static void start_simulation() {
        Scanner scanner = new Scanner(System.in);
        int width = 3;
        int height = 3;
        Integer amount_black = 1;
        Integer amount_white = 1;
        Integer amount_green = 1;
        Integer amount_yellow = 1;
        Integer amount_purple = 1;
        Board board = new Board(width, height);
        //String[] tribeTypes = {"black", "white"};
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

        List<Position> repPositions = new ArrayList<>(); //lista powtarzających się pozycji
        List<Position> usedPositions = new ArrayList<>();//lista pozycji
        Board updatedBoard = null;
        for (int i = 0; i < 1; i++) {
            updatedBoard = new Board(width, height);
            for (Map.Entry<Position, Tribe> entry : board.board.entrySet()) {
                board.moveTribeTo(entry.getValue(), entry.getKey());//przesuwanie obiektów
                int x = entry.getKey().x; //zapamietywanie nowych wspolrzednych
                int y = entry.getKey().y;
                Tribe tribe = entry.getValue();
                createTribe(tribe.name); //tworzenie nowego obiektu Tribe
                updatedBoard.newTribe(tribe, x, y); //dodawanie obiektu Tribe z nowymi wspolrzednymi do tymczasowej mapy
                usedPositions.add(new Position(x, y));
            }
            for (Position item : usedPositions) {
                for (Position element : usedPositions) {
                    if (item.x == element.x && item.y == element.y && usedPositions.indexOf(item) != usedPositions.lastIndexOf(element)) {
                        if (repPositions.contains(item)) {
                            continue;
                        } else {
                            List<Tribe> listOfFighters = new ArrayList<>();
                            for (Map.Entry<Position, Tribe> entry : board.board.entrySet()) {
                                if (item.x == entry.getValue().current_x && item.y == entry.getValue().current_y) {
                                    listOfFighters.add(entry.getValue());
                                }
                            }
                            repPositions.add(item);
                            Tribe pozastanie_na_polu = updatedBoard.tribeFight(listOfFighters);
                            System.out.println(pozastanie_na_polu.name);
                        }
                    }
                }
            }
        }
        for (Position item : usedPositions) {
            System.out.println(item.x + " " + item.y);
        }

        board = updatedBoard;
        board.displayBoard();
        System.out.println();
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

