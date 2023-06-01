package org.example;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    int width;
    int height;
    Map<Position, Tribe> board = new HashMap<>();

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void addTribe(Tribe tribe) {
        Position position = this.drawPosition();
        tribe.current_x = position.x;
        tribe.current_y = position.y;
        this.addToBoard(tribe, position);
    }

    private Position drawPosition() {
        Position position = Position.drawPosition(width - 1, height - 1);
        while (this.board.containsKey(position)) {
            position = Position.drawPosition(width - 1, height - 1);
        }
        return position;
    }

    private void addToBoard(Tribe tribe, Position position){
        if (!this.board.containsKey(position)){
            this.board.put(position, tribe);
        }
    }

    public void moveTribe(Tribe tribe, Position position) {
        int random = RandInt.random(0, 2);
        if (RandInt.random(-100, 100) < 0){
            position.x -= random * tribe.multiply_speed_x;
            if (position.x  < 0){ //zabeczpiecznie przed wyjsciem poza szerokosc planszy
                position.x = 0;
            }
        } else {
            position.x += random * tribe.multiply_speed_x; //losuje o ile przesłunie sie obiekt (lewo/prawo)
            if (position.x >= width){
                position.x = width-1;
            }
        }
        if (RandInt.random(-100, 100) < 0){
            position.y -= random * tribe.multiply_speed_y; //losuje o ile przesłunie sie obiekt (gora/dol)
            if (position.y < 0) {
                position.y = 0;
            }
        } else {
            position.y += random * tribe.multiply_speed_y; //losuje o ile przesłunie sie obiekt (lewo/prawo)
            if (position.y >= height) {
                position.y = height - 1;
            }
        }
        tribe.current_x = position.x;
        tribe.current_y = position.y;
    }

    public Tribe tribeFight(List<Tribe> listOfFighters){
        int choice = RandInt.random(1,4);
        int max = 0;
        Tribe winner = new Tribe();
        for (Tribe fighter : listOfFighters) {
            if (choice ==1){
                if (fighter.physical_strength > max){
                    max = fighter.physical_strength;
                    winner = fighter;
                }
            } else if (choice == 2) {
                if (fighter.iq > max) {
                    max = fighter.iq;
                    winner = fighter;
                }
            }else if (choice == 3){
                if (fighter.agility > max){
                    max = fighter.agility;
                    winner = fighter;
                }
            } else if (choice == 4) {
                if (fighter.endurance > max) {
                    max = fighter.endurance;
                    winner = fighter;
                }
            }
        }
        return winner;
    }

    public void displayBoard() {
        for (int i=0; i<height; i++){
            for (int j=0; j<width; j++) {
                Tribe tribe = board.get(new Position(j, i));
                if (tribe == null) {
                    System.out.print(".");
                } else {
                    System.out.print(tribe.getName().substring(0,1));
                }
            }
            System.out.println();
        }
    }

    public void newTribe (Tribe tribe, int x, int y) {
        tribe.current_x = x;
        tribe.current_y = y;
        this.board.put(new Position(x, y), tribe);
    }
}
