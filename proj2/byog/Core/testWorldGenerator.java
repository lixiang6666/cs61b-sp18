package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

public class testWorldGenerator {
    public static void main(String[] args){
        long seed = 714;
        TERenderer ter = new TERenderer();
        ter.initialize(80, 30);
        WorldGenerator wg = new WorldGenerator(seed, 80, 30 );
        TETile[][] world = wg.getWorld();
        ter.renderFrame(world);
    }

}
