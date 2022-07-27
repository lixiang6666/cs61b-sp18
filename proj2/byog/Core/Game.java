package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
import java.util.Random;

public class Game{
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private static WorldGenerator wg;
    private static boolean gameOver = false;

    private long seed;
    private Random rand;
    private Position player;
    boolean toLoad =  false;
    private Position door;
    private TETile[][] world;
    private static InformationStored informationStored;


    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    private static class InformationStored implements Serializable{
        public long seedCopy;
        public String Operation = "";
    }
    private static class Position{
        public int xPos;
        public int yPos;
        Position(int x, int y){
            xPos = x;
            yPos = y;
        }
    }
    public void playWithKeyboard(){
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        String input = solicitChatInput();
        if (!toLoad){
            informationStored = new InformationStored();
            seed = parseSeed(input);
            informationStored.seedCopy = seed;
            rand = new Random(seed);
            wg = new WorldGenerator(seed, WIDTH, HEIGHT);
            world = wg.getWorld();
            player = initWorld(world, Tileset.FLOOR, Tileset.PLAYER, rand);
            door = initWorld(world, Tileset.WALL, Tileset.LOCKED_DOOR, rand);
        }
            ter.renderFrame(world);

        while(!gameOver){
            char key;
            if(StdDraw.hasNextKeyTyped()){
                key = StdDraw.nextKeyTyped();
                if(key == ':'){
                    while(!StdDraw.hasNextKeyTyped()){
                    }
                    key = StdDraw.nextKeyTyped();
                    if(String.valueOf(key).equalsIgnoreCase("Q")){
                        objectSerialization();
                        System.exit(0);
                    }
                }
                informationStored.Operation += String.valueOf(key);
                player =  playGame(key, world, player);
                if(world[player.xPos][player.yPos].equals(Tileset.LOCKED_DOOR)){
                    gameOver =true;
                    world[door.xPos][door.yPos] = Tileset.UNLOCKED_DOOR;
                }else{
                    world[player.xPos][player.yPos] = Tileset.PLAYER;
                }
                ter.renderFrame(world);
            }
        }
            drawEnd();
    }

    private void objectSerialization(){
        File f = new File("./world.ser");
        try{
            FileOutputStream fo = new FileOutputStream(f);
            ObjectOutputStream oo = new ObjectOutputStream(fo);
            oo.writeObject(informationStored);
            oo.close();
            fo.close();

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    private static InformationStored objectDeserialization(){
        File f = new File("./world.ser");
        try{
            FileInputStream fi = new FileInputStream(f);
            ObjectInputStream oi = new ObjectInputStream(fi);
            informationStored = (InformationStored) oi.readObject();
            oi.close();
            fi.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return informationStored;
    }
    public Position playGame(char key, TETile[][] world, Position player) {
        String keyString = String.valueOf(key);
        Position p = player;
        if (keyString.equalsIgnoreCase("w")){
                p = new Position(player.xPos, player.yPos+1);
            }
        if(keyString.equalsIgnoreCase("a")){
                p = new Position(player.xPos-1, player.yPos);
        }
        if(keyString.equalsIgnoreCase("s")){
                p = new Position(player.xPos, player.yPos-1);
        }
        if(keyString.equalsIgnoreCase("d")){
                p = new Position(player.xPos +1, player.yPos);
        }
        if(!world[p.xPos][p.yPos].equals(Tileset.WALL)){
            world[player.xPos][player.yPos] = Tileset.FLOOR;
            player = p;
        }
        return player;
    }
    private static Position initWorld(TETile[][] world, TETile tileToFind, TETile tileToChange, Random rand){
        int x;
        int y;
        while(true){
            x = rand.nextInt(WIDTH);
            y = rand.nextInt(HEIGHT);
            if (world[x][y].equals(tileToFind)){
                world[x][y] = tileToChange;
                break;
            }
        }
        return new Position(x, y);
    }
    private static long parseSeed(String input){
        int start = 0;
        int end = 0;
        for(int i=0; i<input.length();i++){
            if(input.charAt(i) == 'n'|| input.charAt(i) == 'N'){
                start = i + 1;
            }
            if(input.charAt(i) == 's'|| input.charAt(i) == 'S'){
                end = i;
                break;
            }
        }
        String seed = input.substring(start, end);
        return Long.parseLong(seed);
    }
    private static  void drawStartUI(String input){
        int midWidth = WIDTH / 2;
        int midHeight = HEIGHT / 2;
        StdDraw.clear();
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);
        Font bigFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(bigFont);
        StdDraw.text(midWidth, midHeight + 5, "CS61B: Welcome, Hot wind!");
        Font smallFont = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(smallFont);
        StdDraw.text(midWidth, midHeight + 2, "New Game:(N)");
        StdDraw.text(midWidth, midHeight , "Load Game:(L)");
        StdDraw.text(midWidth, midHeight -2, "Quit Game:(Q)");
        StdDraw.text(midWidth, midHeight -4,"Input with keyboard: " + input);
        StdDraw.show();
    }
    private static  void drawEnd(){
        int midWidth = WIDTH / 2;
        int midHeight = HEIGHT / 2;
        StdDraw.clear();
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);
        Font bigFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(bigFont);
        StdDraw.text(midWidth, midHeight , "You Win! By order of the f**king peaky blinders!!!!");
        StdDraw.show();
    }
    public String solicitChatInput(){
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        String input = "";
        drawStartUI(input);
        char key;
        while(true){
            if(StdDraw.hasNextKeyTyped()){
                key = StdDraw.nextKeyTyped();
                input += String.valueOf(key);
                drawStartUI(input);
                if(key == 's'|| key == 'S'){
                    break;
                }
                if(key == 'l' || key == 'L'){
                    informationStored = objectDeserialization();
                    toLoad = true;
                    seed = informationStored.seedCopy;
                    System.out.println(informationStored.Operation);
                    rand = new Random(seed);
                    world = playWithInputString("l");
                    break;
                }
            }
        }
        return input;
    }
    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    private String parseInput(String input){
        if(input.equalsIgnoreCase("")){
            return input;
        }
        if (input.substring(0,1).equalsIgnoreCase("l")){
            toLoad = true;
            informationStored = objectDeserialization();
            seed = informationStored.seedCopy;
            rand = new Random(seed);
            wg = new WorldGenerator(seed, WIDTH, HEIGHT);
            world = wg.getWorld();
            player = initWorld(world, Tileset.FLOOR, Tileset.PLAYER, rand);
            door = initWorld(world, Tileset.WALL, Tileset.LOCKED_DOOR, rand);
            String operation = informationStored.Operation;
            informationStored.Operation = "";
            return operation + input.substring(1);
        }
        return input;
    }
    public TETile[][] playWithInputString(String input) {
        String toPlay;
        toPlay = parseInput(input);
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        if(!toLoad) {
            informationStored = new InformationStored();
            seed = parseSeed(input);
            informationStored.seedCopy = seed;
            rand = new Random(seed);
            wg = new WorldGenerator(seed, WIDTH, HEIGHT);
            world = wg.getWorld();
            player = initWorld(world, Tileset.FLOOR, Tileset.PLAYER, rand);
            door = initWorld(world, Tileset.WALL, Tileset.LOCKED_DOOR, rand);
            int end;
            for(int i = 0; i < input.length();i++){
                if (input.substring(i, i+1).equalsIgnoreCase("N")){
                    end = i + 1;
                    toPlay= input.substring(end);
                }
            }
        }
        char key;
        for(int i = 0; i < toPlay.length();i++){
            key = toPlay.charAt(i);
            if(key == ':'){
                if(toPlay.substring(i+1).equalsIgnoreCase("q")){
                    objectSerialization();
                    System.exit(0);
                }
            }
            informationStored.Operation += String.valueOf(key);
            player =  playGame(key, world, player);
            if(world[player.xPos][player.yPos].equals(Tileset.LOCKED_DOOR)){
                gameOver = true;
                world[door.xPos][door.yPos] = Tileset.UNLOCKED_DOOR;
                break;
            }else{
                world[player.xPos][player.yPos] = Tileset.PLAYER;
            }
        }
        return world;
    }
}
