public class LinkList {
    private linkNode head;

    public LinkList() {
        this.head = new linkNode(null);
    }

    public linkNode getHead() {
        return this.head;
    }

    public void setHead(linkNode h) {
        this.head = h;
    }
}
