package de.fhstralsund.opentransport.Pathfinding;


import org.lwjgl.util.vector.Vector2f;

public class Node {

    private int heuristicH;
    private int movementCostG = 10;
    private int totalvalueF;
    private Node parentNode;
    private boolean endNode = false;
    private boolean done = false;

    private Vector2f position;

    public Node(Vector2f position, int heuristic, int movementCostG, Node parentNode) {
        this.heuristicH = heuristic;
        this.position = position;
        this.movementCostG = movementCostG;
        this.parentNode = parentNode;
        this.totalvalueF = heuristic + movementCostG;
    }

    public boolean isEndNode() {
        return endNode;
    }

    public void setEndNode(boolean endNode) {
        this.endNode = endNode;
    }

    public Node getParentNode() {
        return parentNode;
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }

    public int getTotalvalueF() {
        return totalvalueF;
    }

    public int getMovementCostG() {
        return movementCostG;
    }

    public void setMovementCostG(int movementCostG) {
        this.movementCostG = movementCostG;
        calcTotalCost();
    }

    private void calcTotalCost() {
        this.totalvalueF = this.heuristicH + this.movementCostG;
    }

    public int getHeuristicH() {
        return heuristicH;
    }

    public void setHeuristicH(int heuristicH) {
        this.heuristicH = heuristicH;
        calcTotalCost();
    }

    public Vector2f getPosition() {
        return position;

    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
