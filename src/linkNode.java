public class linkNode {
    private int vertex;
    private int weight;
    private linkNode next = null;

    public linkNode(int verval, int weival, linkNode nextval) {
        this.vertex = verval;
        this.weight = weival;
        this.next = nextval;
    }

    public linkNode(linkNode nextval) {
        this.next = nextval;
    }

    public void setVertex(int verval) {
        this.vertex = verval;
    }

    public void setWeight(int weival) {
        this.weight = weival;
    }

    public void setNext(linkNode nextval) {
        this.next = nextval;
    }

    public int getVertex() {
        return this.vertex;
    }

    public int getWeight() {
        return this.weight;
    }

    public linkNode getNext() {
        return this.next;
    }
}
