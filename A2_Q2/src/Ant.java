/* -----------------------------------------------------------------------------
    Author: Saif Mahmud
    Date: 2023-22-07
    Course: COMP 452
    Student ID: 3433058
    Assignment: 2
    Part: 2
*/

import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Ant {

    private int antX;
    private int antY;
    private Tile home;

    private Tile[][] tiles;
    private ArrayList<Tile> path;
    private int tileSize;
    private int direction;

    private ImageIcon antImage;
    private ImageIcon drawingImage;
    private ImageIcon antAndFood;
    private ImageIcon antLookingWater;

    private AStarSearch aStarSearch;

    private boolean dontMove;
    private boolean wentHome;
    
    private boolean isThirsty;
    private boolean isLookingForFood;
    


    public Ant(Tile start, int tileSize, Tile[][] tiles) {
        this.home = start;
        start.setStart();
        this.tileSize = tileSize;
        this.tiles = tiles;
        direction = -1;
        loadAntImg();
        antX = start.getX()*tileSize;
        antY = start.getY()*tileSize;
        isLookingForFood = true;
        aStarSearch = new AStarSearch(tiles);
    }

    private void loadAntImg() {
        try {
            antImage = new ImageIcon("ant.png");
            drawingImage = antImage;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void loadAntAndFoodAndWater() {
        if(antAndFood == null || antLookingWater == null){
            try {
                antAndFood = new ImageIcon("antAndFood.png");
                antLookingWater = new ImageIcon("antLookingWater.png");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // move the ants randomly within the grid
    public void move() {
        if(!dontMove || isThirsty){
            // Generate a random number between 0 and 7 to determine the direction of movement
            int probabilityOfChangingDirection = 30;
            int shouldChangeDirection = (int) (Math.random() * 100); // 0-99
            
            if(direction == -1 || shouldChangeDirection < probabilityOfChangingDirection) {
                direction = (int) (Math.random() * 8);
            }
        
            // Calculate the new position of the ant based on the direction of movement
            int newX = antX;
            int newY = antY;
            if (direction == 0) { // Move up
                newY -= tileSize;
            } else if (direction == 1) { // Move up-right
                newX += tileSize;
                newY -= tileSize;
            } else if (direction == 2) { // Move right
                newX += tileSize;
            } else if (direction == 3) { // Move down-right
                newX += tileSize;
                newY += tileSize;
            } else if (direction == 4) { // Move down
                newY += tileSize;
            } else if (direction == 5) { // Move down-left
                newX -= tileSize;
                newY += tileSize;
            } else if (direction == 6) { // Move left
                newX -= tileSize;
            } else { // Move up-left
                newX -= tileSize;
                newY -= tileSize;
            }
        
            // Check if the new position is within the bounds of the grid
            if (newX >= 0 && newX < tiles[0].length * tileSize && newY >= 0 && newY < tiles.length * tileSize) {
                // Update the ant's position
                antX = newX;
                antY = newY;
            } else {
                // Change direction if the new position is out of bounds
                direction = (int) (Math.random() * 8);
            }
        } 

        if(dontMove){
            // meaning the ant has reached the food
            // so it should go back to home
            // will go through the path obtained from A* search
            if(path != null){
                if(path.size() > 0 ){
                    Tile nextTile = path.get(path.size()-1);
                    path.remove(nextTile);
                    antX = nextTile.getX()*tileSize;
                    antY = nextTile.getY()*tileSize;
                } else {
                    dontMove = false;
                    path = null;
                    wentHome = true;
                    this.setThirsty();
                }
            }
        }
        
    }

    public boolean isLookingForFood(){
        return isLookingForFood;
    }

    public void setLookingForFood(){
        isLookingForFood = true;
        dontMove = false;
        drawingImage = antImage;
    }

    public void resetLookingForFood(){
        isLookingForFood = false;
    }

    public boolean isThirsty(){
        return isThirsty;
    }

    public void setThirsty(){
        this.isThirsty = true;
        drawingImage = antLookingWater;
    }

    public void resetThirsty(){
        this.isThirsty = false;
        drawingImage = antImage;
    }

    public boolean wentHome(){
        return wentHome;
    }

    public void resetWentHome(){
        wentHome = false;
    }

    public void draw(Graphics g, int tileSize) {
        g.drawImage(drawingImage.getImage(), antX, antY, tileSize, tileSize, null);
    }
   
    public void search(Tile starTingLocation, Tile goal) {
        path = aStarSearch.search(starTingLocation, goal);
    }

    public ArrayList<Tile> getPath(Tile currTile) {
        return path;
    }

    public int getX() {
        return antX;
    }

    public int getY() {
        return antY;
    }
    public void setX(int x) {
        antX = x;
    }

    public void setY(int y) {
        antY = y;
    }

    public Tile getHome() {
        return home;
    }

    public Tile getTile(){
        Tile tile = null;
        for(int i = 0; i < tiles.length; i++){
            for(int j = 0; j < tiles[0].length; j++){
                if(tiles[i][j].getTile(antX, antY, tileSize)){
                    tile = tiles[i][j];
                }
            }
        }
        return tile;
    }

    public void goHome(Tile currTile) {
        dontMove = true;
        loadAntAndFoodAndWater();
        drawingImage = antAndFood;
        search(currTile, home);
    }

}// end class Ant
