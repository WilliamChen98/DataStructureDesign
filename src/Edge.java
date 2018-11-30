public class Edge {
    public int from, to, weight;

    Edge() {
        from = -1;
        to = -1;
        weight = -1;
    }

    Edge(int f, int t, int w) {
        from = f;
        to = t;
        weight = w;
    }
}
