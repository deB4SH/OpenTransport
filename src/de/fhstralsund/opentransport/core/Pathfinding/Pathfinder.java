package de.fhstralsund.opentransport.core.pathfinding;

import org.lwjgl.util.vector.Vector2f;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pathfinder {

    private List<Node> openNodes;
    private List<Node> closedNodes;
    private Node[][] notes;

    public Pathfinder() {
        openNodes = new ArrayList<Node>();
        closedNodes = new ArrayList<Node>();
    }

    public ArrayList<Vector2f> findWay(boolean[][] collisionMap, Vector2f start, Vector2f end) {
        openNodes.clear();
        closedNodes.clear();
        notes = new Node[collisionMap.length][collisionMap.length];

        for(int x = 0; x < collisionMap.length; x++) {
            for(int y = 0; y < collisionMap.length; y++) {
                // Manhatten Heuristik
                // anfänglicher Wert
                if(collisionMap[x][y]) {
                    notes[x][y] = new Node(new Vector2f(x, y), (int) (Math.abs(end.x - x) + Math.abs(end.y - y)), 10, null);
                }
            }
        }

        //endNode ist das Ziel
        notes[(int)end.x][(int)end.y].setEndNode(true);
        //startnode hat keine "Bewegungskosten"
        notes[(int)start.x][(int)start.y].setMovementCostG(0);

        ArrayList<Vector2f> resultWay = new ArrayList<Vector2f>();
        // anfangswert
        closedNodes.add(notes[(int) start.x][(int) start.y]);


        while(closedNodes.size() > 0) {
            calcWay(start);
        }

        // partendNodes wieder hoch gehen und weg zusammensetzen
        Node way = notes[(int)end.x][(int)end.y];
        while(way != null) {
            resultWay.add(way.getPosition());
            way = way.getParentNode();
        }

        openNodes.clear();
        closedNodes.clear();
        notes = null;
        Collections.reverse(resultWay);

        return resultWay;
    }

    private void calcWay(Vector2f start) {

        if(closedNodes.size() == 0) {
            return;
        }

        //starthere
        if(!closedNodes.contains(notes[(int)start.y][(int)start.x])) {
            closedNodes.get(0).setDone(true);
        }

        List<Node> nodesToAdd = new ArrayList<Node>();
        Node temp;
        //add left, right, top, down
        if(start.x - 1 >= 0 && notes[(int) start.x - 1][(int) start.y] != null && !notes[(int) start.x - 1][(int) start.y].isDone() ) {
            temp = notes[(int) start.x - 1][(int) start.y];
            temp.setParentNode(notes[(int)start.x][(int)start.y]);
            nodesToAdd.add(temp);

        }
        if(start.x + 1 < notes.length && notes[(int) start.x + 1][(int) start.y] != null && !notes[(int) start.x + 1][(int) start.y].isDone() ) {
            temp = notes[(int) start.x + 1][(int) start.y];
            temp.setParentNode(notes[(int)start.x][(int)start.y]);
            nodesToAdd.add(temp);

        }
        if(start.y + 1 < notes.length && notes[(int) start.x][(int) start.y + 1] != null && !notes[(int) start.x][(int) start.y + 1].isDone() ) {
            temp = notes[(int) start.x][(int) start.y + 1];
            temp.setParentNode(notes[(int)start.x][(int)start.y]);
            nodesToAdd.add(temp);

        }
        if(start.y - 1 >= 0 && notes[(int) start.x][(int) start.y - 1] != null && !notes[(int) start.x][(int) start.y - 1].isDone() ) {
            temp = notes[(int) start.x][(int) start.y - 1];
            temp.setParentNode(notes[(int)start.x][(int)start.y]);
            nodesToAdd.add(temp);

        }

        int cost = Integer.MAX_VALUE;
        Node lowestCostNode = null;

        for(Node element : nodesToAdd) {
            element.setMovementCostG(closedNodes.get(0).getMovementCostG() + 10);
            if(!openNodes.contains(element)) {
                openNodes.add(element);
            }
        }

        for(Node element : openNodes) {
            // isDone bedeutet das die schon mal in der closed List waren
            if(element != null && !element.isDone()) {
                if(element.isEndNode()) {
                    closedNodes.clear();
                    return;
                }
                if(element.getTotalvalueF() < cost) {
                    cost = element.getTotalvalueF();
                    lowestCostNode = element;
                }
            }
        }

        closedNodes.clear();
        if(lowestCostNode == null) {return;}
        closedNodes.add(lowestCostNode);
        lowestCostNode.setDone(true);

        calcWay(closedNodes.get(0).getPosition());
    }
}
