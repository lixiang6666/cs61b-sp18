package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.awt.*;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    /**
     * @param p left bottom start point of a single Hexagon
     * @param s size of the Hexagon
     * @param t type of the tile
     */
    public static void addHexagon(TETile [][] world, Position p, int s, TETile t){
        for(int i = 0; i < s; i ++){
            Position pLow = new Position(p.x - i, p.y + i);
            Position pHigh = new Position(p.x - i, 2*s + p.y - i - 1);
            addLine(world, pLow, s + 2*i,t);
            addLine(world, pHigh, s + 2*i, t);
        }
    }

    /**
     * @param p start point of the line
     * @param s size of the line
     */
    private static void addLine(TETile [][] world, Position p, int s, TETile t){
        for (int i = 0; i < s; i++){
            world[p.x + i][p.y] = new TETile(t, Color.PINK);
        }
    }

    /**
     * @param p start point of the bottom hexagon
     * @param s size of the terrain made by hexagons
     * @param t tetiles
     */
    public static void addTerrain(TETile[][] world, Position p, int s, TETile t){
        for(int i = 0; i < s; i++){
            int xl = p.x -  i * (2* s - 1)  ;
            int yl = p.y + i * s;
            Position pl = new Position(xl, yl);
            int xh = p.x-  i * (2* s - 1) ;
            int yh = p.y + (2*(s -1)-i) *2*s;
            Position ph = new Position(xh, yh);
            addHexagonLine(world, pl, i+1, s, t);
            addHexagonLine(world, ph, i+1, s, t);
        }
    }

    /**
     *
     * @param p start point of the left of the hexagons line
     * @param sizeL length of the hexagons
     * @param sizeH size of the hexagon
     */
    private static void addHexagonLine(TETile[][] world, Position p, int sizeL, int sizeH, TETile t){
        for (int i = 0; i < sizeL; i++){
            p.x += 4  * sizeH -2;
            addHexagon(world, p, sizeH, t);
        }
    }
    private static class Position{
        public int x;
        public int y;
        Position (int xpos, int ypos){
            x = xpos;
            y = ypos;
        }
    }
    public static void main(String[] args){
        TERenderer ter = new TERenderer();
        ter.initialize(100, 50);

        // initialize tiles
        Position p = new Position(20,3);
        TETile[][] world = new TETile[100][50];
        for (int x = 0; x < 100; x += 1) {
            for (int y = 0; y < 50; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        addTerrain(world, p, 3,Tileset.WALL);

        ter.renderFrame(world);
    }
}
