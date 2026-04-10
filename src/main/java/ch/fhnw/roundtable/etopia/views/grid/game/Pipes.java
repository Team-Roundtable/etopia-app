package ch.fhnw.roundtable.etopia.views.grid.game;

import ch.fhnw.roundtable.etopia.views.grid.GridConfiguration;

public class Pipes {

    private final GridConfiguration gridConfiguration;
    private final Pipe[][] elements;

    public Pipes(GridConfiguration gridConfiguration) {
        this.gridConfiguration = gridConfiguration;
        this.elements = new Pipe[gridConfiguration.mapWidth][gridConfiguration.mapHeight];

        setupFossil();
        setupBiogas();
        setupWind();
        setupGeothermal();
        setupSolar();
    }

    public int width() {
        return gridConfiguration.mapWidth;
    }

    public int height() {
        return gridConfiguration.mapHeight;
    }

    public Pipe get(int x, int y) {
        return elements[x][y];
    }

    // todo improve with direct list of consumer pipes
    public boolean consumersPowered() {
        for (Pipe[] pipeRows : elements) {
            for (Pipe pipe : pipeRows) {
                if (pipe != null && pipe.getType() == PipeType.CONSUMER && !pipe.isPowered()) {
                    return false;
                }
            }
        }

        return true;
    }

    private void setupFossil() {
        elements[13][7] = new Pipe(PipeType.CONSUMER);
        elements[13][8] = new Pipe(PipeType.STRAIGHT_IMMUTABLE, 1);
        elements[13][9] = new Pipe(PipeType.STRAIGHT_IMMUTABLE, 1);
        elements[13][10] = new Pipe(PipeType.THREE_WAY_IMMUTABLE, 0);
        elements[12][10] = new Pipe(PipeType.STRAIGHT_IMMUTABLE, 0);
        elements[14][10] = new Pipe(PipeType.STRAIGHT_IMMUTABLE, 0);
        elements[11][10] = new Pipe(PipeType.CORNER_IMMUTABLE, 2);
        elements[15][10] = new Pipe(PipeType.CORNER_IMMUTABLE, 3);
        elements[11][11] = new Pipe(PipeType.STRAIGHT_IMMUTABLE, 1);
        elements[15][11] = new Pipe(PipeType.STRAIGHT_IMMUTABLE, 1);
        elements[11][12] = new Pipe(PipeType.STRAIGHT_EDITABLE, 0);
        elements[15][12] = new Pipe(PipeType.STRAIGHT_EDITABLE, 0);
        elements[11][13] = new Pipe(PipeType.STRAIGHT_IMMUTABLE, 1);
        elements[15][13] = new Pipe(PipeType.STRAIGHT_IMMUTABLE, 1);
        elements[11][14] = new Pipe(PipeType.CORNER_IMMUTABLE, 1);
        elements[15][14] = new Pipe(PipeType.CORNER_IMMUTABLE, 0);
        elements[12][14] = new Pipe(PipeType.STRAIGHT_IMMUTABLE, 0);
        elements[14][14] = new Pipe(PipeType.STRAIGHT_IMMUTABLE, 0);
        elements[13][14] = new Pipe(PipeType.THREE_WAY_IMMUTABLE, 2);
        elements[13][15] = new Pipe(PipeType.PRODUCER);
    }

    private void setupBiogas() {
        elements[12][8] = new Pipe(PipeType.CONSUMER);
        elements[11][8] = new Pipe(PipeType.STRAIGHT_IMMUTABLE, 0);
        elements[10][8] = new Pipe(PipeType.STRAIGHT_EDITABLE, 1);
        elements[9][8] = new Pipe(PipeType.CORNER_IMMUTABLE, 2);
        elements[9][9] = new Pipe(PipeType.STRAIGHT_IMMUTABLE, 1);
        elements[9][10] = new Pipe(PipeType.CORNER_EDITABLE, 1);
        elements[8][10] = new Pipe(PipeType.STRAIGHT_IMMUTABLE, 0);
        elements[7][10] = new Pipe(PipeType.PRODUCER, 0);
    }

    private void setupWind() {
        elements[12][6] = new Pipe(PipeType.CONSUMER);
        elements[12][5] = new Pipe(PipeType.STRAIGHT_IMMUTABLE, 1);
        elements[12][4] = new Pipe(PipeType.CORNER_IMMUTABLE, 3);
        elements[11][4] = new Pipe(PipeType.STRAIGHT_IMMUTABLE, 0);
        elements[10][4] = new Pipe(PipeType.STRAIGHT_EDITABLE, 1);
        elements[9][4] = new Pipe(PipeType.STRAIGHT_IMMUTABLE, 0);
        elements[8][4] = new Pipe(PipeType.STRAIGHT_EDITABLE, 1);
        elements[7][4] = new Pipe(PipeType.CORNER_IMMUTABLE, 2);
        elements[7][5] = new Pipe(PipeType.CORNER_IMMUTABLE, 0);
        elements[6][5] = new Pipe(PipeType.STRAIGHT_IMMUTABLE, 0);
        elements[5][5] = new Pipe(PipeType.THREE_WAY_EDITABLE, 1);
        elements[5][4] = new Pipe(PipeType.STRAIGHT_IMMUTABLE, 1);
        elements[4][5] = new Pipe(PipeType.STRAIGHT_IMMUTABLE, 0);
        elements[3][5] = new Pipe(PipeType.PRODUCER);
    }

    private void setupGeothermal() {
        elements[14][6] = new Pipe(PipeType.CONSUMER);
        elements[15][6] = new Pipe(PipeType.THREE_WAY_IMMUTABLE, 0);
        elements[15][5] = new Pipe(PipeType.STRAIGHT_IMMUTABLE, 1);
        elements[15][4] = new Pipe(PipeType.STRAIGHT_IMMUTABLE, 1);
        elements[15][3] = new Pipe(PipeType.CORNER_EDITABLE, 0);
        elements[16][3] = new Pipe(PipeType.STRAIGHT_IMMUTABLE, 0);
        elements[17][3] = new Pipe(PipeType.STRAIGHT_EDITABLE, 1);
        elements[18][3] = new Pipe(PipeType.STRAIGHT_IMMUTABLE, 0);
        elements[16][6] = new Pipe(PipeType.STRAIGHT_IMMUTABLE, 0);
        elements[17][6] = new Pipe(PipeType.CORNER_IMMUTABLE, 0);
        elements[17][5] = new Pipe(PipeType.CORNER_EDITABLE, 3);
        elements[18][5] = new Pipe(PipeType.STRAIGHT_IMMUTABLE, 0);
        elements[19][5] = new Pipe(PipeType.CORNER_IMMUTABLE, 0);
        elements[19][4] = new Pipe(PipeType.STRAIGHT_IMMUTABLE, 1);
        elements[19][3] = new Pipe(PipeType.THREE_WAY_IMMUTABLE, 2);
        elements[20][3] = new Pipe(PipeType.STRAIGHT_IMMUTABLE, 0);
        elements[21][3] = new Pipe(PipeType.PRODUCER);
    }

    private void setupSolar() {
        elements[14][8] = new Pipe(PipeType.CONSUMER);
        elements[15][8] = new Pipe(PipeType.STRAIGHT_IMMUTABLE, 0);
        elements[16][8] = new Pipe(PipeType.STRAIGHT_IMMUTABLE, 0);
        elements[17][8] = new Pipe(PipeType.CORNER_IMMUTABLE, 3);
        elements[17][9] = new Pipe(PipeType.STRAIGHT_IMMUTABLE, 1);
        elements[17][10] = new Pipe(PipeType.CORNER_IMMUTABLE, 1);
        elements[18][10] = new Pipe(PipeType.STRAIGHT_IMMUTABLE, 0);
        elements[19][10] = new Pipe(PipeType.STRAIGHT_EDITABLE, 1);
        elements[20][10] = new Pipe(PipeType.STRAIGHT_IMMUTABLE, 0);
        elements[21][10] = new Pipe(PipeType.THREE_WAY_IMMUTABLE, 2);
        elements[21][11] = new Pipe(PipeType.STRAIGHT_IMMUTABLE, 1);
        elements[22][10] = new Pipe(PipeType.STRAIGHT_IMMUTABLE, 0);
        elements[21][12] = new Pipe(PipeType.CORNER_EDITABLE, 2);
        elements[23][10] = new Pipe(PipeType.CORNER_EDITABLE, 0);
        elements[22][12] = new Pipe(PipeType.STRAIGHT_IMMUTABLE, 0);
        elements[23][11] = new Pipe(PipeType.STRAIGHT_IMMUTABLE, 1);
        elements[23][12] = new Pipe(PipeType.PRODUCER);
    }
}
