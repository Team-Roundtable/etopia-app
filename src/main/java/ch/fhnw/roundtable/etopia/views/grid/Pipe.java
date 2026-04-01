package ch.fhnw.roundtable.etopia.views.grid;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;


public class Pipe {
    public static final float SIZE = 96;
    private final PipeType pipeType;
    private int rotation;
    private final int gridX, gridY;
    private boolean top, bottom, right, left;
    private boolean powered;
    private static final float OFFSET_X = 40;
    private static final float OFFSET_Y = 20;
    private PipeType power = null;
    private final  Sprite sprite;

    public Pipe(PipeType pipeType, int gridX, int gridY) {

        this.pipeType = pipeType;
        this.gridX = gridX;
        this.gridY = gridY;
        this.rotation = 0;
        this.powered = false;

        this.sprite = new Sprite(new Texture(getAsset(pipeType).getPath()));

        float x = gridX * SIZE + OFFSET_X;
        float y = gridY * SIZE + OFFSET_Y;
        sprite.setBounds(x, y, SIZE, SIZE);
        sprite.setOriginCenter();

        if (pipeType == PipeType.SOURCE || pipeType == PipeType.CITY || pipeType == PipeType.SOURCE_BIO ) {
            sprite.setSize(SIZE * 1.5f, SIZE * 1.7f);
        }
        setColor(1, 1,1,1);
        updateConnections();
    }
    public Sprite getSprite() {
        return sprite;
    }
    public void setColor(float r, float g, float b, float a) {
        sprite.setColor(r,g,b,a);
    }

    public void updateConnections() {
        switch (pipeType) {
            case STRAIGHT -> {
                if (rotation % 180 == 0) {
                    top = bottom = true;
                    left = right = false;
                } else {
                    left = right =true;
                    top = bottom = false;
                }
            }
            case STRAIGHT_INACTIVE -> {
                    left = right = true;
                    top = bottom = false;
            }
            case CORNER -> {
                top = right = bottom = left = false;
                if (rotation == 0) {
                    bottom = left = true;
                } else if (rotation == 90) {
                    bottom = right = true;
                } else if (rotation == 180) {
                    top = right = true;
                } else if (rotation == 270) {
                    top = left = true;
                }
            }
            case T ->  {
                top = rotation == 90 || rotation == 180 || rotation == 270;
                right = rotation == 0 || rotation == 180 || rotation == 270;
                bottom = rotation == 0 || rotation == 180 || rotation == 90;
                left = rotation == 90 || rotation == 270 || rotation == 0;
            }
            case SOURCE, CITY -> {
                top = right = bottom = left = true;
            }
        }
    }

    private static GridAsset getAsset(PipeType pipeType) {
        return switch (pipeType) {
            case STRAIGHT -> GridAsset.PIPE;
            case STRAIGHT_INACTIVE ->  GridAsset.PIPE_INACTIVE;
            case CORNER -> GridAsset.PIPE90;
            case T -> GridAsset.TPIPE;
            case SOURCE -> GridAsset.WIND_SOURCE;
            case SOURCE_BIO ->  GridAsset.BIO_SOURCE;
            case SOURCE_SOLAR -> GridAsset.SOLAR_SOURCE;
            case CITY -> GridAsset.CITY;
        };
    }
    public boolean isRotatable() {
        return pipeType != PipeType.SOURCE && pipeType != PipeType.CITY && pipeType != PipeType.STRAIGHT_INACTIVE;
    }
    public boolean isColorable() {
        return pipeType != PipeType.SOURCE && pipeType != PipeType.CITY;
    }


    public boolean isBottom() {
        return bottom;
    }
    public boolean isTop() {
        return top;
    }
    public boolean isRight() {
        return right;
    }
    public boolean isLeft() {
        return left;
    }


    public void setPowered(boolean powered) {
        this.powered = powered;
        if (powered && isColorable()) {
            setColor(0,1,0,1);
        }else {
            setColor(1, 1, 1, 1);
        }
    }


    public void rotate90(boolean clockwise) {
        rotation += clockwise ? 90 : -90;
        rotation = (rotation + 360) % 360;
        sprite.setRotation(rotation);
        updateConnections();
    }
    public PipeType getType() {
        return pipeType;
    }


}