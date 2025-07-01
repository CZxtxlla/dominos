public class tile {
    private int left;
    private int right;

    public tile(int l, int r) {
        this.left = l;
        this.right = r;
    }

    public tile flipped() {
        return new tile(right, left);
    }

    public boolean isDouble() {
        return left == right;
    }

    @Override
    public String toString() {
        return "[" + left + "|" + right + "]";
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public static void main (String[] args) {
        tile T = new tile(3, 5);
        System.out.println(T);
        System.out.println(T.flipped());
        System.out.println(T.getLeft());
    }
}
