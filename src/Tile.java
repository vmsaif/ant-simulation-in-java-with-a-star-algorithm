/* -----------------------------------------------------------------------------
    Author: Saif Mahmud
    Date: 2023-22-07
*/
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.ImageIcon;

public class Tile implements Comparable<Tile> {
    public static final double COST_OPEN_TERRAIN = 1;

    private int x;
    private int y;
    private double cost;
    private double f; // total cost
    private double g; // cost so far
    private double h; // heuristic
    private boolean isWater;
    private boolean isStart;
    private boolean isGoal;
    private boolean isOpenTerrain;
    private boolean isPoison;

    private Tile cameFrom;
    private ImageIcon image;

    public Tile(int x, int y, ImageIcon image) {
        this.x = x;
        this.y = y;
        this.cost = COST_OPEN_TERRAIN;
        this.image = image;
        isOpenTerrain = true;
        this.cameFrom = null;
    }

    public void draw(Graphics g, int tileSize) {

        if (isStart) {
            g.setColor(Color.GREEN);
        } else {
            g.setColor(Color.WHITE);
        }

        // draw the tile
        g.fillRect((int) x * tileSize, (int) y * tileSize, tileSize, tileSize);

        // draw the border
        g.setColor(Color.BLACK);
        g.drawRect((int) x * tileSize, (int) y * tileSize, tileSize, tileSize);

        // draw the image if not open terrain
        if (image != null) {
            g.drawImage(image.getImage(), x * tileSize, y * tileSize, tileSize, tileSize, null);
        }

    }

    // used to compare and add into the priority queue
    @Override
    public int compareTo(Tile other) {
        int result = Double.compare(this.f, other.f);
        // if f is the same, compare h
        if (result == 0) {
            result = Double.compare(this.h, other.h);

            if (result == 0) {
                result = Double.compare(this.g, other.g);
            }
        }
        return result;
    }

    // return true if the ant is on this tile
    public Boolean getTile(int antX, int antY, int tileSize) {
        int pixelXmin = x * tileSize;
        int pixelYmin = y * tileSize;

        int pixelXmax = pixelXmin + tileSize;
        int pixelYmax = pixelYmin + tileSize;

        Boolean result = false;
        if (antX >= pixelXmin && antX < pixelXmax && antY >= pixelYmin && antY < pixelYmax) {
            result = true;
        }

        return result;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getCost() {
        return cost;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart() {
        this.isStart = true;
    }

    public boolean isFood() {
        return isGoal;
    }

    public void resetFood() {
        this.isGoal = false;
        image = null;
    }

    public void setGoal() {
        this.isGoal = true;
    }

    public boolean isWater() {
        return isWater;
    }

    public void resetWater() {
        this.isWater = false;
        image = null;
    }

    public void setWater() {
        this.isWater = true;
        this.isPoison = false;
    }

    public boolean isPoison() {
        return isPoison;
    }

    public void setPoison() {
        this.isPoison = true;
    }

    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }

    // g is cost so far
    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    // h is heuristic
    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public void setCameFrom(Tile current) {
        cameFrom = current;
    }

    public Tile getCameFrom() {
        return cameFrom;
    }

    // for debugging
    // to string method
    public String toString() {
        return "Tile: " + x + ", " + y + " Cost: " + cost;
    }
}
