package org.example;

/**
 * klasa "parent" dla klas wszystkich plemion
 */
public class Tribe {
    Integer physical_strength;
    Integer iq;
    Integer endurance;
    Integer agility;
    Integer multiply_speed_x;
    Integer multiply_speed_y;
    Integer current_x;
    Integer current_y;
    String name;

    Tribe() {
    }

    /**
     * metoda zwracajaca stringa z imieniem danego plemienia aby odpowiednio wpisac je do mapy
     * @return
     */
    public String getName() {
        return name;
    }
}
