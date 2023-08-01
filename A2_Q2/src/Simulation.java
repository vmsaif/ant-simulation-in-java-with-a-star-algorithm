/* -----------------------------------------------------------------------------
    Author: Saif Mahmud
    Date: 2023-22-07
    Course: COMP 452
    Student ID: 3433058
    Assignment: 2
    Part: 2
    Description: 
    
    Finite State Machine:
    The objective of this program is to implement a game simulating the behaviour of ants. The ants should be designed based on the logic that their purpose is to collect food and return to their home position. The ants have to follow certain obstacles and rules in the simulation:

    The ants move randomly in their environment in an attempt to locate a piece of food.
    When an ant finds a piece of food, it returns to its home position. You can implement the path to its home position as you wish.
    When an ant arrives home, it (a) becomes thirsty, (b) drops its food; and (c) starts a search for drinking water.
    Ants looking for water roam randomly in their environment in their attempt to find water.
    When an ant finds water, it drinks and then resumes its search for food.
    New ants are born in the colony. Every time an ant returns food to the home position, a new ant is added (born) to the team that will start a search for food.
    The ant population continues to grow as long as more food is brought home.
    The environment is a grilled area with cells that may be of different types: empty ground, food, water or poison.
    Food, water and poison are randomly placed in the environment.
    Ants can walk safely on empty ground, food and water cells; however, any ant that steps on a poison cell must die.  
    The only thing the user has to specify is the starting number of ants in the colony. Food, water and poison should be generated and randomly distributed by the program.
    Ants must be roaming randomly when looking for food or water; otherwise, use your creativity to implement other movements (i.e., returning to the home position).
*/



import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Simulation extends JPanel {

    private final int TILE_SIZE = 40;
    private final int NUM_ROWS = 16;
    private final int NUM_COLS = 16;
    private Tile[][] tiles;

    protected boolean startLocationSelectionMode;

    private ImageIcon foodImg;
    private ImageIcon waterImage;
    private ImageIcon poisonImage;

    private int probablityOfWater;
    private int probablityOfFood; 
    private int probablityOfPoison;
    private ArrayList<Ant> allAnts;
    protected boolean stopSimulation; 

    public Simulation(int antNumber) {
        
        probablityOfWater = 20; // 12%
        probablityOfFood = probablityOfWater/2; 
        probablityOfPoison = probablityOfFood/2;
        // first load the images
        loadImages();

        // create the tiles with the images accordingly
        createTiles();
        
        // place the ant in a random tile
        placeAnt(antNumber);

        // start the timer
        updateComponents();

    }

    private void placeAnt(int antNumber) {
        // place the ant in a random tile
        allAnts = new ArrayList<>();
        for(int i = 0; i < antNumber; i++){
            int randomRow = (int) (Math.random() * NUM_ROWS);
            int randomCol = (int) (Math.random() * NUM_COLS);
            Tile tile = tiles[randomRow][randomCol];
            if(
                tile.isStart() == false && 
                tile.isFood() == false && 
                tile.isWater() == false && 
                tile.isPoison() == false
                ){
                Tile startTile = tile;
                startTile.setStart();
                Ant ant = new Ant(tiles[randomRow][randomCol], TILE_SIZE, tiles);
                allAnts.add(ant);
                
            } else {
                i--;
            }
        }
    }

    private void loadImages() {
        try {
            foodImg = new ImageIcon("food.png");
            waterImage = new ImageIcon("water.png");
            poisonImage = new ImageIcon("poison.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // this method will create the tiles and randomly place the food, water and poison
    private void createTiles() {
        tiles = new Tile[NUM_ROWS][NUM_COLS];
        ImageIcon image = null;
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                // all open terrain by default.
                // randomly set image of water, food and poison
                int random = (int) (Math.random() * 100);

                // smallest to largest
                if(random < probablityOfPoison){
                    image = poisonImage;
                    tiles[i][j] = new Tile(i, j, image);
                    tiles[i][j].setPoison();
                } else if(random < probablityOfFood){
                    image = foodImg;
                    tiles[i][j] = new Tile(i, j, image);
                    tiles[i][j].setGoal();
                } else if(random < probablityOfWater){
                    image = waterImage;
                    tiles[i][j] = new Tile(i, j, image);
                    tiles[i][j].setWater();
                } else {
                    // open terrain
                    image = null;
                    tiles[i][j] = new Tile(i, j, image);
                }
                
            }
        }
    }

    // this method will check the simulation status
    public void updateComponents(){
        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(allAnts != null && allAnts.size() > 0){
                    for(int i = 0; i < allAnts.size(); i++){
                        allAnts.get(i).move();
                        checkAntDead();
                        checkFoundFood();
                        checkWentHome();
                        checkThirsty();
                    }
                } else {
                    // no ants left
                    stopSimulation = true;
                    ((Timer) e.getSource()).stop();
                }
                repaint();
            }

            
        });
        timer.start();
    }

    // this method will check if the ant returned home with food. If so, add a new ant to the colony. 
    // Also the ant will be thirsty and will look for water.
    // the food will be removed from the tile.
    protected void checkWentHome() {
        for(int i = 0; i < allAnts.size(); i++){
            Ant ant = allAnts.get(i);
            Tile currTile = ant.getTile();
            if(ant.wentHome()){
                // add new ant
                Ant ant2 = new Ant(currTile, TILE_SIZE, tiles);
                allAnts.add(ant2);
                ant.resetWentHome();
                
                // now the ant is thirsty
                ant.resetLookingForFood();
                ant.setThirsty();

            }
        }
    }

    // this method will check if the ant is thirsty and is on a water tile. If so, the ant will drink water and will look for food.
    // the water will not be removed from the tile.
    private void checkThirsty() {
        if(allAnts.size() > 0){
            for(int i = 0; i < allAnts.size(); i++){
                Ant ant = allAnts.get(i);
                Tile currTile = ant.getTile();
                if(ant.isThirsty() && currTile.isWater()){
                    currTile.resetWater();
                    ant.resetThirsty();
                    ant.setLookingForFood();
                }
            }
        }
    }

    // this method will check if the ant found food. If so, the ant will pick up the food and will go home.
    private void checkFoundFood() {
        for(int i = 0; i < allAnts.size(); i++){
            Ant ant = allAnts.get(i);
            Tile currTile = ant.getTile();
            if(ant.isLookingForFood() && currTile.isFood()){
                currTile.resetFood();
                ant.goHome(currTile);
            }
        }        
    
    }

    // this method will check if the ant is on a poison tile. If so, the ant will die. The poison will not be removed.
    public void checkAntDead(){
        for(int i = 0; i < allAnts.size(); i++){
            Ant ant = allAnts.get(i);
            if(ant.getTile().isPoison()){
                allAnts.remove(i);
            }
        }
    }

    // this method will draw the canvas
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // draw tiles
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                tiles[i][j].draw(g, TILE_SIZE);
            }
        }

        // draw ants
        if (allAnts != null) {
            for(int i = 0; i < allAnts.size(); i++) {
                allAnts.get(i).draw(g, TILE_SIZE);
            }            
        }

        if(stopSimulation){
            // Display No Ants Alive
            g.setColor(Color.BLACK);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
            g.drawString("No Ants Alive!!!", getWidth()/2-200, getHeight()/2);
        }

    } // end paintComponent

    public int getTileSize() {
        return TILE_SIZE;
    }

}// end class
