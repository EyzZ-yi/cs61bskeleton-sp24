package tetris;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TETile;
import tileengine.TERenderer;
import tileengine.Tileset;

import java.util.*;
//error
//public void clearLines(TETile[][] tiles) {
//        // 跟踪当前清除的行数
//        ArrayList<Integer> list=new ArrayList<>();
//        int linesCleared = 0;
//
//        // TODO: 检查已完成多少行，如果完成则清除这些行。
//        for(int y=0;y<HEIGHT;y++){
//            for(int x=0;x<WIDTH;x++){
//                if(tiles[x][y]==Tileset.NOTHING){
//                    break;
//                }
//                if(x==WIDTH-1) {
//                    linesCleared++;
//                    list.add(y);
//                }
//            }
//        }
//        for(int i=0;i<list.size();i++){
//            if(list.get(i)==HEIGHT-1) continue;
//            for(int j=list.get(i);j<HEIGHT-1;j++){
//                for(int k=0;k<WIDTH;k++){
//                    tiles[k][j]=tiles[k][j+1];
//                }
//            }
//            for(int l=0;l<list.size();l++){
//                if(list.get(l)<list.get(i)) continue;
//                list.add(i,list.get(i)-1);
//            }
//        }
//        // TODO: 根据清除的行数增加分数。
//            incrementScore(linesCleared);
//        fillAux();
/**
 *  提供俄罗斯方块的逻辑。
 *
 *  @author Erik Nelson, Omar Yu, Noah Adhikari, Jasmine Lin
 */

public class Tetris {

    private static int WIDTH = 10;
    private static int HEIGHT = 20;

    // 方块在大家看到区域的上方生成，所以我们的俄罗斯方块面板会比展示出来的更高
    private static int GAME_HEIGHT = 25;

    // 包含面板上的图块。
    private TETile[][] board;

    // 帮助处理方块的移动。
    private Movement movement;

    // 检查游戏是否结束。
    private boolean isGameOver;

    // 当前可以由玩家控制的俄罗斯方块。
    private Tetromino currentTetromino;

    // 当前游戏的分数。
    private int score;

    /**
     * 根据 isGameOver 参数检查游戏是否结束。
     * @return boolean 表示游戏是否结束
     */
    private boolean isGameOver() {
        return isGameOver;
    }

    /**
     * 将游戏面板和分数渲染到屏幕上。
     */
    private void renderBoard() {
        ter.drawTiles(board);
        renderScore();
        StdDraw.show();

        if (auxFilled) {
            auxToBoard();
        } else {
            fillBoard(Tileset.NOTHING);
        }
    }

    /**
     * 创建一个新的俄罗斯方块并相应地更新实例变量。
     * 如果面板顶部已满且无法生成新方块，则标记游戏结束。
     */
    private void spawnPiece() {
        // 如果这个图块被填充，游戏结束
        if (board[4][19] != Tileset.NOTHING) {
            isGameOver = true;
        }

        // 否则，生成一个新方块并将其位置设置到生成点
        currentTetromino = Tetromino.values()[bagRandom.getValue()];
        currentTetromino.reset();
    }

    /**
     * 根据用户输入更新面板。根据用户的输入进行适当的移动。
     */
    private void updateBoard() {
        // 获取当前方块。
        Tetromino t = currentTetromino;
        if (actionDeltaTime() > 1000) {
            movement.dropDown();
            resetActionTimer();
            Tetromino.draw(t, board, t.pos.x, t.pos.y);
            return;
        }

        // TODO: 实现交互性，以便用户能够输入按键来移动和旋转图块。
        // 你需要在这里使用一些提供的辅助方法。
        if(StdDraw.hasNextKeyTyped()){
            char key = StdDraw.nextKeyTyped();
            HashMap<Character,int[]> map=new HashMap<>();
            map.put('a',new int[]{-1,0});
            map.put('d',new int[]{1,0});
            map.put('s',new int[]{0,-1});
            if(key=='a' || key=='d' || key=='s') {
                if(movement.canMove(map.get(key)[0],map.get(key)[1])) {
                    movement.tryMove(map.get(key)[0],map.get(key)[1]);
                }
            }
            HashMap<Character,Movement.Rotation> map2=new HashMap<>();
            map2.put('q',Movement.Rotation.LEFT);
            map2.put('e',Movement.Rotation.RIGHT);
            if(key=='q' || key=='e'){
                if(movement.canRotate(t.shape))
                movement.rotate(map2.get(key));
            }
        }

        Tetromino.draw(t, board, t.pos.x, t.pos.y);
    }

    /**
     * 根据清除的行数增加分数。
     *
     * @param linesCleared
     */
    private void incrementScore(int linesCleared) {
        // TODO: 根据清除的行数增加分数。
        switch (linesCleared){
            case 1:
                score+=100;
                break;
            case 2:
                score+=300;
                break;
            case 3:
                score+=500;
                break;
            case 4:
                score+=800;
                break;
        }
    }

    /**
     * 在提供的图块/面板上清除水平填充的行。
     * 重复此过程以产生级联效应，并相应地更新分数。
     * @param tiles
     */
    public void clearLines(TETile[][] tiles) {
        int linesCleared = 0;
        int writeY = 0; // “写”指针，指向下一个非满行应该被移动到的位置

        // 使用“读/写”双指针法遍历整个游戏面板（使用GAME_HEIGHT）
        for (int readY = 0; readY < GAME_HEIGHT; readY++) {
            boolean isLineFull = true;
            // 检查当前“读”指针所在的行 (readY) 是否已满
            for (int x = 0; x < WIDTH; x++) {
                if (tiles[x][readY] == Tileset.NOTHING) {
                    isLineFull = false;
                    break;
                }
            }

            if (isLineFull) {
                // 如果行已满，增加清除行数计数，然后“跳过”这一行（不增加 writeY）
                linesCleared++;
            } else {
                // 如果行未满，则将其内容复制到 writeY 的位置
                if (readY != writeY) { // 仅在需要移动时才复制
                    for (int x = 0; x < WIDTH; x++) {
                        tiles[x][writeY] = tiles[x][readY];
                    }
                }
                writeY++; // 移动“写”指针到下一行
            }
        }

        // 将 writeY 以上的所有剩余行填充为空白
        for (int y = writeY; y < GAME_HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
        incrementScore(linesCleared);
    }



    /**
     * 游戏逻辑发生的地方。只要游戏没有结束，游戏就应该继续。
     */
    //想象一下你是一位老式动画师，你面前有两样东西：
    //1.
    //auxiliary (辅助面板): 这是一张透明的背景画。上面画着所有不会动的东西，比如远处的山、已经落地的方块堆。这张背景画会一直保留着。
    //2.
    //board (主画板): 这是你最终要**拿去拍摄（渲染）**的那一帧画面。它是一张白纸。
    //你的工作流程是：
    //•
    //第1步 (准备): 把透明的背景画 (auxiliary) 放到白纸 (board) 上面。现在 board 上就有了所有已经落地的方块。
    //•
    //第2步 (画上活动物体): 在这张 board 上，画上当前正在下落的方块 (currentTetromino)。
    //•
    //第3步 (拍摄): “咔嚓！” 拍摄这张 board，把它展示给观众 (renderBoard())。现在观众看到了完整的一帧画面。
    //•
    //第4步 (思考下一帧): 观众在看这一帧的时候，你开始思考下一帧要怎么画。
    //◦
    //重力时间到了吗？如果到了，活动方块是不是该往下挪一格？
    //◦
    //如果方块已经碰到底了，怎么办？
    //▪
    //你需要拿出你的背景画 (auxiliary)。
    //▪
    //把活动方块永远地画在背景画上 (placePieceOnBoard)。
    //▪
    //检查背景画上有没有哪一行被填满了，有的话就擦掉 (clearLines)。
    //▪
    //然后准备一个新的活动方块 (spawnPiece)。
    //•
    //第5步 (循环): 扔掉旧的 board 白纸，拿一张新的，回到第1步，开始绘制下一帧。
    public void runGame() {
        isGameOver=false;
        spawnPiece();
        long time=500;
        while(!isGameOver()){
            auxToBoard();
            Tetromino.draw(currentTetromino,board,currentTetromino.pos.x,currentTetromino.pos.y);
            renderBoard();
            if(actionDeltaTime()>time) {
                if (currentTetromino != null && !movement.canMove(0, -1)) {
                    Tetromino.draw(currentTetromino,auxiliary,currentTetromino.pos.x,currentTetromino.pos.y);
                    clearLines(auxiliary);
                    copyArray(auxiliary,board);
                    spawnPiece();
                } else{
                    if(currentTetromino==null) continue;
                    movement.tryMove(0,-1);
                }
                resetActionTimer();
            }
            if(currentTetromino!=null){
                updateBoard();
            }
            StdDraw.pause(20);
        }
    }

    /**
     * 使用 StdDraw 库渲染分数。
     */
    private void renderScore() {
        // TODO: 使用 StdDraw 库绘制分数。
        StdDraw.setPenColor(255,255,255);
        StdDraw.text(7,19,"Score: "+score);
    }

    /**
     * 使用此方法运行俄罗斯方块。
     * @param args
     */
    public static void main(String[] args) {
        long seed = args.length > 0 ? Long.parseLong(args[0]) : (new Random()).nextLong();
        Tetris tetris = new Tetris(seed);
        tetris.runGame();
    }

    /**
     * 这里下面的所有东西你都不需要动。
     */

    // 这是我们的图块渲染引擎。
    private final TERenderer ter = new TERenderer();

    // 用于随机化生成的方块。
    private Random random;
    private BagRandomizer bagRandom;

    private long prevActionTimestamp;
    private long prevFrameTimestamp;

    // 辅助面板。在每个时间步，当方块向下移动时，面板会被清除并重绘，
    // 所以我们保留一个辅助面板来跟踪到目前为止已经放置了什么，以帮助在更新时渲染当前游戏面板。
    private TETile[][] auxiliary;
    private boolean auxFilled;

    public Tetris() {
        board = new TETile[WIDTH][GAME_HEIGHT];
        auxiliary = new TETile[WIDTH][GAME_HEIGHT];
        random = new Random(new Random().nextLong());
        bagRandom = new BagRandomizer(random, Tetromino.values().length);
        auxFilled = false;
        movement = new Movement(WIDTH, GAME_HEIGHT, this);
        fillBoard(Tileset.NOTHING);
        fillAux();
    }

    public Tetris(long seed) {
        board = new TETile[WIDTH][GAME_HEIGHT];
        auxiliary = new TETile[WIDTH][GAME_HEIGHT];
        random = new Random(seed);
        bagRandom = new BagRandomizer(random, Tetromino.values().length);
        auxFilled = false;
        movement = new Movement(WIDTH, GAME_HEIGHT, this);

        ter.initialize(WIDTH, HEIGHT);
        fillBoard(Tileset.NOTHING);
        fillAux();
    }

    // Setter 和 getter 方法。

    /**
     * 返回当前游戏面板。
     * @return
     */
    public TETile[][] getBoard() {
        return board;
    }

    /**
     * 返回分数。
     */
    public int getScore() {
        return score;
    }

    /**
     * 返回当前辅助面板。
     * @return
     */
    public TETile[][] getAuxiliary() {
        return auxiliary;
    }


    /**
     * 返回当前的俄罗斯方块/方块。
     * @return
     */
    public Tetromino getCurrentTetromino() {
        return currentTetromino;
    }

    /**
     * 将当前的俄罗斯方块设置为 null。
     * @return
     */
    public void setCurrentTetromino() {
        currentTetromino = null;
    }

    /**
     * 将布尔值 auxFilled 设置为 true；
     */
    public void setAuxTrue() {
        auxFilled = true;
    }

    /**
     * 用传入的特定图块填充整个面板。
     * @param tile
     */
    private void fillBoard(TETile tile) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = tile;
            }
        }
    }

    /**
     * 使用 System.arraycopy 将 src 数组的内容复制到 dest 数组中。
     * @param src
     * @param dest
     */
    private static void copyArray(TETile[][] src, TETile[][] dest) {
        for (int i = 0; i < src.length; i++) {
            System.arraycopy(src[i], 0, dest[i], 0, src[0].length);
        }
    }

    /**
     * 将游戏面板的图块复制到辅助面板。
     */
    public void fillAux() {
        copyArray(board, auxiliary);
    }

    /**
     * 将辅助面板的图块复制到游戏面板。
     */
    private void auxToBoard() {
        copyArray(auxiliary, board);
    }

    /**
     * 计算与上一个动作的增量时间。
     * @return 上一次俄罗斯方块移动与现在之间的时间量
     */
    private long actionDeltaTime() {
        return System.currentTimeMillis() - prevActionTimestamp;
    }

    /**
     * 将动作时间戳重置为当前的毫秒时间。
     */
    private void resetActionTimer() {
        prevActionTimestamp = System.currentTimeMillis();
    }

}
