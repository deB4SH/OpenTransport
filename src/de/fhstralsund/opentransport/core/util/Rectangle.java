package de.fhstralsund.opentransport.core.util;

public class Rectangle {

    public float x;
    public float y;
    public float width;
    public float height;

    public Rectangle(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean contains(Rectangle rectangle) {

        if(this.x + this.width < rectangle.x || this.x > rectangle.x + rectangle.width ||
                this.y + this.height < rectangle.y ||this.y > rectangle.y + rectangle.height) {
            return false;
        }
        return true;
    }
}
