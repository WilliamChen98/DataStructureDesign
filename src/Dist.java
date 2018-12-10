public class Dist {
    private int index;
    private int length;
    private int pre;

    public Dist() {
        this.index = -1;
        this.length = -1;
        this.pre = -1;
    }

    public int getIndex() {
        return this.index;
    }

    public int getLength() {
        return this.length;
    }

    public int getPre() {
        return this.pre;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setPre(int pre) {
        this.pre = pre;
    }
}
