
public class linkNode {
    public int vertex;
    public int weight;
    public linkNode next = null;

    public linkNode(int verval, int weival, linkNode nextval) {
        this.vertex = verval;
        this.weight = weival;
        this.next = nextval;
    }

    public linkNode(linkNode nextval) {
        this.next = nextval;
    }

    public void setValue(int verval, int weival) {
        this.vertex = verval;
        this.weight = weival;
    }

    public void setNext(linkNode nextval) {
        this.next = nextval;
    }
}
