package ch.fhnw.roundtable.etopia.views.grid;

import ch.fhnw.roundtable.etopia.ETopia;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.views.Scene;
import ch.fhnw.roundtable.etopia.views.SceneType;

import java.util.function.Function;


public class Grid extends Scene<GridAsset> {
    private final Pipe[][] pipes;
    private final int GRID_WIDTH = 20;
    private final int GRID_HEIGHT = 10;
    private int selectedX = 3;
    private int selectedY = 3;

    public Grid() {
       super(GridAsset.class);
        pipes = new Pipe[GRID_WIDTH][GRID_HEIGHT];
        create();
    }

    private void create(){
        pipes[15][8] = new Pipe(PipeType.SOURCE_SOLAR, 15, 8);
      //  pipes[3][9] = new Pipe(PipeType.SOURCE_BIO, 3, 9);
        pipes[2][3] = new Pipe(PipeType.SOURCE, 2, 3);
        pipes[3][3] = new Pipe(PipeType.STRAIGHT_INACTIVE, 3, 3);
        pipes[4][3] = new Pipe(PipeType.STRAIGHT_INACTIVE, 4, 3);
        pipes[5][3] = new Pipe(PipeType.STRAIGHT_INACTIVE, 5, 3);
        pipes[6][3] = new Pipe(PipeType.STRAIGHT_INACTIVE, 6, 3);
        pipes[7][3] = new Pipe(PipeType.CORNER, 7, 3);

        pipes[7][4] = new Pipe(PipeType.CORNER, 7, 4);

        pipes[8][4] = new Pipe(PipeType.STRAIGHT_INACTIVE, 8, 4);
        pipes[9][4] = new Pipe(PipeType.CORNER, 9, 4);

        pipes[9][5] = new Pipe(PipeType.CITY, 9, 5);
        for(int i = 0; i < GRID_WIDTH; i++){
            for(int j = 0; j < GRID_HEIGHT; j++){
                if(pipes[i][j] != null){
                    pipes[i][j].updateConnections();
                }
            }
        }
    }
    private boolean getPower(int x, int y, boolean[][] checked){
        Pipe pipe = pipes[x][y];

        if (pipe == null || checked[x][y]) {
            return false;
        }
        checked[x][y] = true;
        pipe.setPowered(true);
        if (pipe.getType() == PipeType.CITY) {
            return true;
        }
        boolean connection = false;
        connection |= checkIfConnected(x, y, 0, 1, pipe.isTop(), Pipe::isBottom, checked);
        connection |= checkIfConnected(x, y, 0, -1, pipe.isBottom(), Pipe::isTop, checked);
        connection |= checkIfConnected(x, y, 1, 0, pipe.isRight(), Pipe::isLeft, checked);
        connection |= checkIfConnected(x, y, -1, 0, pipe.isLeft(), Pipe::isRight, checked);

        return connection;
    }
    private boolean checkIfConnected(int x, int y, int nextX, int nextY, boolean isConnected, Function<Pipe, Boolean> nextSide, boolean[][] checked) {
        if (!isConnected) return false;

        int nx = x + nextX;
        int ny = y + nextY;

        if (nx < 0 || nx >= GRID_WIDTH || ny < 0 || ny >= GRID_HEIGHT) return false;

        Pipe nextPipe = pipes[nx][ny];
        if (nextPipe != null && nextSide.apply(nextPipe)) {
            return getPower(nx, ny, checked);
        }

        return false;
    }

    private void updateEnergyFlow(){
        reset();
        boolean[][] visited = new boolean[GRID_WIDTH][GRID_HEIGHT];
        boolean connected = getPower(2, 3, visited);
        if (!connected) {
            reset();
        }
    }
    private void reset(){
        for(int i = 0; i < GRID_WIDTH; i++){
            for(int j = 0; j < GRID_HEIGHT; j++){
                Pipe pipe = pipes[i][j];
                if(pipe != null){
                    pipe.setPowered(false);
                }
            }
        }
    }
    @Override
    public void updateScene(float delta, Input input) {
        if (input.isUpJustPressed()) {
            selectedY++;
        }
        if (input.isDownJustPressed()) {
            selectedY--;
        }
        if (input.isRightJustPressed()) {
            selectedX++;
        }
        if (input.isLeftJustPressed()) {
            selectedX--;
        }
        Pipe pipe = pipes[selectedX][selectedY];
        if (pipe != null) {
            if (input.isSelectJustPressed() && pipe.isRotatable()) {
                pipe.rotate90(true);
            }
            updateEnergyFlow();
        }

    }


    @Override
    public void renderScene(Renderer renderer) {
        renderer.batch(batch -> {
            batch.draw(getTexture(GridAsset.BACKGROUND), 0, 0, ETopia.WORLD_WIDTH, ETopia.WORLD_HEIGHT);

            for (int x = 0; x < GRID_WIDTH; x++) {
                for (int y = 0; y < GRID_HEIGHT; y++) {
                    Pipe pipe = pipes[x][y];
                    if (pipe != null) {
                        if (x == selectedX && y == selectedY) {
                            pipe.setColor(1, 1, 0, 1);
                        }
                        pipe.getSprite().draw(batch, 1); // Sprite rendern
                    }
                }
            }
        });

    }

    @Override
    public SceneType change() {
        return null;
    }

}
