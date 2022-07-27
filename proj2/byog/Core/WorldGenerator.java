package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;

/**
 * a world generator, return a 2D world TETile [][]
 */
public class WorldGenerator {
    private final TETile [][] world;

   WorldGenerator(long seed, int worldLength, int worldWide){
       ArrayList<Room> roomList = new ArrayList<Room>();
       world = new TETile[worldLength][worldWide];
       for (int i = 0; i < worldLength;i++) {
           for (int j = 0; j < worldWide; j++) {
               world[i][j] = Tileset.NOTHING;
           }
       }
       Random rand = new Random(seed);
       roomGenerate(rand, world, roomList);
       pathGenerate(world, roomList);
   }

   public TETile [][] getWorld(){
       return world;
   }
   private static void roomGenerate(Random rand, TETile[][] world, ArrayList<Room> roomList){
       int worldLength = world.length;
       int worldWide = world[0].length;
       final int MAXTIMESROOM = 100;
       for (int i = 0; i < MAXTIMESROOM; i++) {
           int roomWide = rand.nextInt(worldWide/3 - 3 + 1) + 3;
           int roomLength = rand.nextInt(worldLength/3 - 3 + 1) + 3;
           int roomLBx = rand.nextInt(worldLength - 1);
           int roomLBy = rand.nextInt(worldWide - 1);
           Position roomlb = new Position(roomLBx, roomLBy);
           Room room = new Room(roomWide, roomLength, roomlb);
           addRoom(room, world, roomList);
       }
   }
   private static class Position{
       public int xPos;
       public int yPos;
       Position(int x, int y){
           xPos = x;
           yPos = y;

       }
   }
    /**
     * rectangular room class wide>=3, length>=3, positon lb, lt, rb, rt
     */
   private static class Room{
        public Position lb;
        public Position lt;
        public Position rb;
        public Position rt;

        /**
         * @param wide the widenes of the whole room including wall >=3
         * @param length the length of the whole room including wall >= 3
         * @param leftBottom the left bottom position of the room
         */
        Room(int wide, int length, Position leftBottom){
            lb = leftBottom;
            rb = new Position(lb.xPos + length - 1, lb.yPos );
            lt = new Position(lb.xPos,lb.yPos + wide -1 );
            rt = new Position(rb.xPos, rb.yPos + wide -1);
        }
        }

   private static void addRoom(Room room, TETile[][] world, ArrayList<Room> roomList){
        if(isRoomAllowed(room, roomList,world)){
            int roomWide = room.lt.yPos - room.lb.yPos + 1;
            int roomLength = room.rb.xPos - room.lb.xPos + 1;
            for (int i = 0; i < roomLength; i++ ){
                for(int j = 0; j< roomWide; j++){
                    if(i == 0|| i == roomLength - 1|| j ==0||j==roomWide-1){
                        world[room.lb.xPos + i][room.lb.yPos +j] = Tileset.WALL;
                    }else{
                        world[room.lb.xPos + i][room.lb.yPos +j] = Tileset.FLOOR;
                    }
                }
            }
            roomList.add(room);
        }
   }
   private static boolean isRoomAllowed(Room room, ArrayList<Room> roomList, TETile[][] world){
       int worldLength = world.length;
       int worldWide = world[0].length;
       if (room.lt.yPos > worldWide -1 || room.rb.xPos > worldLength -1 ){
           return false;
       }else if (roomOccupied(room, roomList)){
           return false;
       }else{
           return true;
       }
   }
   private static boolean roomOccupied(Room room, ArrayList<Room> roomList){
       boolean flag = false;
       for (Room rooms : roomList) {
           if (twoRoomOverlap(room, rooms)) {
               flag = true;
               break;
           }
       }
       return flag;
   }

   private static boolean twoRoomOverlap(Room room1, Room room2){
       boolean flag = false;
       if (insideRoom(room1.lb, room2)||insideRoom(room1.lt, room2)||insideRoom(room1.rb, room2)||insideRoom(room1.rt, room2)){
           flag = true;
       }
       if (insideRoom(room2.lb, room1)||insideRoom(room2.lt, room1)||insideRoom(room2.rb, room1)||insideRoom(room2.rt, room1)){
           flag = true;
       }
       if(room1.lt.yPos >=room2.lt.yPos && room1.lb.yPos <= room2.lb.yPos&&room1.lt.xPos >= room2.lt.xPos&&room1.rt.xPos <= room2.rt.xPos){
           flag = true;
       }
       if(room2.lt.yPos >= room1.lt.yPos && room2.lb.yPos <= room1.lb.yPos&&room2.lt.xPos >= room1.lt.xPos&&room2.rt.xPos <= room1.rt.xPos){
           flag = true;
       }
       return flag;
   }

   private static boolean insideRoom(Position p, Room room){
       if(p.xPos >= room.lb.xPos && p.xPos <= room.rb.xPos && p.yPos >= room.lb.yPos && p.yPos<= room.lt.yPos){
           return true;
       }else{
           return false;
       }
   }


   private static void pathGenerate(TETile[][] world, ArrayList<Room> roomList){
       for (int i = 0; i < roomList.size() - 1; i++){
           ArrayList<Path> pathList = new ArrayList<>();
           connectRoom(roomList.get(i), roomList.get(i + 1), pathList);
           addPaths(world, pathList);
       }
   }


   private static class Path{
       public Position p;
       public int direction;
       public int length;

       /**
        *
        * @param p position of star point of the path
        * @param direction 0:horizontal , 1:vertical
        * @param length the length of the straight length
        */
       Path(Position p, int direction, int length){
           this.p = p;
           this.direction = direction;
           this.length = length;
       }
   }

    /**
     *return a pathList containing the path to connect room1 and room2
     */
   private static void connectRoom(Room room1, Room room2, ArrayList<Path> pathList){
       int xPos;
       int yPos;
       int length;
       int direction;
       Room roomStar;//we star our path at the higher room
       Room roomEnd;
       if (room1.lt.yPos >= room2.lt.yPos){
           roomStar = room1;
           roomEnd = room2;
       }else{
           roomStar = room2;
           roomEnd = room1;
       }
       if(roomEnd.lt.yPos > roomStar.lb.yPos + 1){
           yPos = roomEnd.rt.yPos - 1;
           direction = 0;
           if (roomEnd.lb.xPos >= roomStar.rb.xPos){
               xPos = roomStar.rb.xPos;
               length = roomEnd.lb.xPos  - roomStar.rb.xPos + 1;
           }else{
               xPos = roomStar.lb.xPos;
               length = roomEnd.rb.xPos  - roomStar.lb.xPos - 1;
           }
           Position star = new Position(xPos, yPos);
           Path path = new Path(star, direction, length);
           pathList.add(path);
       }else{
           yPos = roomStar.lb.yPos;
           xPos = roomStar.rb.xPos - 1;
           direction = 1;
           length = roomEnd.lt.yPos - roomStar.rb.yPos - 1;
           Position star1 = new Position(xPos, yPos);
           Path path = new Path(star1, direction, length);
           pathList.add(path);
           if (xPos <= roomEnd.lt.xPos){
               yPos += length;
               length = roomEnd.lt.xPos - xPos + 1;
               direction = 0;
               Position star2 = new Position(xPos, yPos);
               Path path2 = new Path(star2, direction, length);
               pathList.add(path2);
           }
           if (xPos >= roomEnd.rt.xPos){
               yPos += length;
               length = roomEnd.rt.xPos - xPos - 1;
               direction = 0;
               Position star2 = new Position(xPos, yPos);
               Path path2 = new Path(star2, direction, length);
               pathList.add(path2);
           }
       }
   }

    /**
     * add(draw) paths in pathlist to the world.
     */
   private static void addPaths(TETile[][] world, ArrayList<Path> pathList){
       for (Path path : pathList){
           if(path.direction == 0){
               if(path.length < 0){
                   int length = -path.length;
                   for (int i = 0; i<length; i++){
                       Position p = new Position(path.p.xPos - i, path.p.yPos);
                       drawUnitPath(world, path.direction, p);
                   }
               }else{
                   for (int i = 0; i<path.length; i++){
                       Position p = new Position(path.p.xPos + i, path.p.yPos);
                       drawUnitPath(world, path.direction, p);
                   }
               }
           }else{
               if(path.length < 0){
                   int length = -path.length;
                   for (int i = 0; i<length; i++){
                       Position p = new Position(path.p.xPos, path.p.yPos - i);
                       drawUnitPath(world, path.direction, p);
                   }
               }else{
                   for (int i = 0; i<path.length; i++){
                       Position p = new Position(path.p.xPos, path.p.yPos + i);
                       drawUnitPath(world, path.direction, p);
                   }
               }
           }
       }
   }
    private static void drawUnitPath(TETile[][] world, int direction, Position p){
       world[p.xPos][p.yPos] = Tileset.FLOOR;
       if(world[p.xPos + 1][p.yPos] == Tileset.NOTHING){
           world[p.xPos + 1][p.yPos] = Tileset.WALL;
       }
       if(world[p.xPos - 1][p.yPos] == Tileset.NOTHING){
            world[p.xPos - 1][p.yPos] = Tileset.WALL;
        }
       if(world[p.xPos ][p.yPos + 1] == Tileset.NOTHING){
            world[p.xPos ][p.yPos + 1] = Tileset.WALL;
        }
       if(world[p.xPos ][p.yPos - 1] == Tileset.NOTHING){
            world[p.xPos ][p.yPos - 1] = Tileset.WALL;}
       if(world[p.xPos + 1][p.yPos+1] == Tileset.NOTHING){
            world[p.xPos + 1][p.yPos+1] = Tileset.WALL;
        }
        if(world[p.xPos + 1][p.yPos-1] == Tileset.NOTHING){
            world[p.xPos + 1][p.yPos-1] = Tileset.WALL;
        }
        if(world[p.xPos-1 ][p.yPos + 1] == Tileset.NOTHING){
            world[p.xPos-1 ][p.yPos + 1] = Tileset.WALL;
        }
        if(world[p.xPos -1][p.yPos - 1] == Tileset.NOTHING){
            world[p.xPos-1 ][p.yPos - 1] = Tileset.WALL;}
       if (direction == 0){
           if(world[p.xPos][p.yPos + 1] != Tileset.FLOOR && world[p.xPos][p.yPos - 1] != Tileset.FLOOR ){
               world[p.xPos][p.yPos + 1] = Tileset.WALL;
               world[p.xPos][p.yPos - 1] = Tileset.WALL;
           }
       }else{
           if(world[p.xPos + 1][p.yPos ] != Tileset.FLOOR && world[p.xPos - 1][p.yPos] != Tileset.FLOOR ){
               world[p.xPos + 1][p.yPos] = Tileset.WALL;
               world[p.xPos - 1][p.yPos ] = Tileset.WALL;
       }
    }
   }
}
