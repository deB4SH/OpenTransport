package de.fhstralsund.project.entity;

import org.lwjgl.util.vector.Vector2f;

public class Street {

    private Vector2f position;
    private boolean north,south,west,east;

    public Street(Vector2f position, boolean north, boolean south, boolean west, boolean east) {
        this.position = position;
        this.north = north;
        this.south = south;
        this.west = west;
        this.east = east;
    }

    public Vector2f getPosition() {
        return position;
    }

    public boolean isNorth() {
        return north;
    }

    public boolean isSouth() {
        return south;
    }

    public boolean isWest() {
        return west;
    }

    public boolean isEast() {
        return east;
    }
}
