public class Edge {
    private int from;
    private int to;
    private int weight;
    public static final int INFINITY = 100000000;

    public Edge() {
        from = -1;
        to = -1;
        weight = -1;
    }

    public Edge(int f, int t, int w) {
        from = f;
        to = t;
        weight = w;
    }

    public int getFrom() {
        return this.from;
    }

    public int getTo() {
        return this.to;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
