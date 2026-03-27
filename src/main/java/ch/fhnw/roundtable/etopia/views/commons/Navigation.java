package ch.fhnw.roundtable.etopia.views.commons;

public class Navigation<T> {
    private Navigation<T> right;
    private Navigation<T> left;
    private Navigation<T> up;
    private Navigation<T> down;

    public T getElement() {
        return element;
    }

    private final T element;

    public Navigation(T element) {
        this.element = element;
    }

    public Navigation<T> getRight() {
        return right;
    }

    public void setRight(Navigation<T> right) {
        this.right = right;
    }

    public Navigation<T> getLeft() {
        return left;
    }

    public void setLeft(Navigation<T> left) {
        this.left = left;
    }

    public Navigation<T> getUp() {
        return up;
    }

    public void setUp(Navigation<T> up) {
        this.up = up;
    }

    public Navigation<T> getDown() {
        return down;
    }

    public void setDown(Navigation<T> down) {
        this.down = down;
    }
}
