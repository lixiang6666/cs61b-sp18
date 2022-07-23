package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

public class testWorldGenerator {
    public static void main(String[] args){
        long seed = 71400;
        TERenderer ter = new TERenderer();
        ter.initialize(100, 50);
        WorldGenerator wg = new WorldGenerator(seed, 100, 50 );
        TETile[][] world = wg.getWorld();
        ter.renderFrame(world);
    }

}
