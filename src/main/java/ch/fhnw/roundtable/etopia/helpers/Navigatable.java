package ch.fhnw.roundtable.etopia.helpers;

@SuppressWarnings("unused")
public interface Navigatable {
    void select();
    void unselect();
    boolean isSelected();

    Navigatable getUp();
    Navigatable getDown();
    Navigatable getLeft();
    Navigatable getRight();

    void setUp(Navigatable nav);
    void setDown(Navigatable nav);
    void setLeft(Navigatable nav);
    void setRight(Navigatable nav);

    default void connectTo(Navigatable nav, Direction direction) {
        switch (direction) {
            case UP -> {
                setUp(nav);
                nav.setDown(this);
            }
            case RIGHT -> {
                setRight(nav);
                nav.setLeft(this);
            }
            case DOWN -> {
                setDown(nav);
                nav.setUp(this);
            }
            case LEFT -> {
                setLeft(nav);
                nav.setRight(this);
            }
        }
    }

    enum Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT
    }
}
