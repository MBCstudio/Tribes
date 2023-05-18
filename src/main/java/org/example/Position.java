package org.example;

public class Position {
    public int x, y;

    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Position drawPosition(int maxX, int maxY) {
        return new Position(RandInt.random(0, maxX), RandInt.random(0, maxY));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        if (x != position.x) return false;
        return y == position.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    } //zapewnia nam że nie będzie dwóch takich samych pozycji
}

