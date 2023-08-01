
import java.util.ArrayList;
import java.util.PriorityQueue;
import javax.swing.JPanel;

public class AStarSearch extends JPanel {

    private Tile[][] tiles;
    private Tile start;
    private Tile goal;
    
    public AStarSearch(Tile[][] tiles) {
        this.tiles = tiles;
    }
    
    // search method
    public ArrayList<Tile> search(Tile start, Tile goal) {

        this.start = start;
        this.goal = goal;

        ArrayList<Tile> output = new ArrayList<>();
        ArrayList<Tile> closedSet = new ArrayList<>();
        
        // create openSet and add start to it 
        PriorityQueue<Tile> openSet = new PriorityQueue<Tile>();

        // add start to openSet
        openSet.add(start);

        boolean foundGoal = false;
        boolean noPath = false;
        
        // while openSet is not empty 
        while(openSet.size() > 0 && foundGoal == false && noPath == false){
            
            // check if first element in openSet is goal
            Tile current = openSet.poll();
            if(current.equals(goal) && noPath == false){
                // reconstruct path
                output = reconstructPath(current);
                foundGoal = true;
            } else {
                // not goal, keep looking

                // remove current from openSet and add to closedSet
                closedSet.add(current);

                // get neighbors of current/ outgoing connections
                PriorityQueue<Tile> neighbors = getNeighbors(current);

                // for each neighbor of current, evaluate cost 
                while(neighbors.size() > 0){
                    Tile currNeighbor = neighbors.poll();
                                        
                    // if neighbor is not in closedSet and not obstacle, then evaluate cost
                    if(!closedSet.contains(currNeighbor)){
                        double tempCost = current.getG() + currNeighbor.getCost();
                        
                        // check for a better path
                        boolean betterPath = false;
                        if(!openSet.contains(currNeighbor)){
                            // if neighbor is not in openSet, add to openSet and add g cost
                            currNeighbor.setG(tempCost);
                            betterPath = true;
                            
                        } else { 
                            // openSet contains neighbor, check if shorter route
                            if (tempCost < currNeighbor.getG()){
                                // found a shorter route, update g cost then update the openset
                                openSet.remove(currNeighbor);
                                currNeighbor.setG(tempCost);
                                openSet.add(currNeighbor);
                                betterPath = true;
                                
                            }
                        }

                        // if better path, update neighbor's f, g, h, and cameFrom
                        if(betterPath){
                            // set neighbor's heuristic estimated cost to goal
                            currNeighbor.setH(getHeuristic(currNeighbor, goal));

                            // set neighbor's f
                            currNeighbor.setF(currNeighbor.getG() + currNeighbor.getH());

                            // record previous node
                            currNeighbor.setCameFrom(current);
                        }
                        

                        // add neighbor to openSet if not already in it
                        if(!openSet.contains(currNeighbor)){
                            openSet.add(currNeighbor);                            
                        }

                    } // end if statement
                } // end while loop
            } // end else(not goal)

            // if openSet is empty, then no path
            if(openSet.size() == 0){
                System.out.println("No path found");
                noPath = true;
            }
        } // end main while loop
        return output;
    }

    private double getHeuristic(Tile currNeighbor, Tile goal) {

        // manhattan distance between current neighbor and goal
        int dx = Math.abs(currNeighbor.getX() - goal.getX());
        int dy = Math.abs(currNeighbor.getY() - goal.getY());
        return dx + dy;
    }

    // find and return all neighbors of a tile
    private PriorityQueue<Tile> getNeighbors(Tile tile) {

        PriorityQueue<Tile> neighbors = new PriorityQueue<>();

        // get the indexes of the tile from the neighbors 2d array
        
        int x = tile.getX();
        int y = tile.getY();

        // add neighbours including diagonals
        // top left
        if(x > 0 && y > 0){
            neighbors.add(tiles[x-1][y-1]);
        }
        // top
        if(y > 0){
            neighbors.add(tiles[x][y-1]);
        }
        // top right
        if(x < tiles.length-1 && y > 0){
            neighbors.add(tiles[x+1][y-1]);
        }
        // right
        if(x < tiles.length-1){
            neighbors.add(tiles[x+1][y]);
        }
        // bottom right
        if(x < tiles.length-1 && y < tiles[0].length-1){
            neighbors.add(tiles[x+1][y+1]);
        }
        // bottom
        if(y < tiles[0].length-1){
            neighbors.add(tiles[x][y+1]);
        }
        // bottom left
        if(x > 0 && y < tiles[0].length-1){
            neighbors.add(tiles[x-1][y+1]);
        }
        // left
        if(x > 0){
            neighbors.add(tiles[x-1][y]);
        }

        return neighbors;
    }
    
    // reconstruct path from start to goal
    public ArrayList<Tile> reconstructPath(Tile current) {
        ArrayList<Tile> path = new ArrayList<>();

        while (current != null) {
            path.add(current);

            // move to the previous node
            current = current.getCameFrom();
        }

        return path;
    }

    // getters and setters
    public Tile getStart() {
        return start;
    }

    public void setStart(Tile start) {
        this.start = start;
    }

    public Tile getGoal() {
        return goal;
    }

    public void setGoal(Tile goal) {
        this.goal = goal;
    }
    
}