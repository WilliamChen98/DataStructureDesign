import java.io.*;

public class Graph {
    public int numVertex;
    public int numEdge;
    public int[] Mark;
    public int[] Indegree;
    public Dist[][] Distance;
    public final int INFINITY = 100000000;
    public final int UNVISITED = 0;
    public final int VISITED = 1;
    private LinkList[] graphList;

    public Graph(int numVert) {
        this.numVertex = numVert;
        this.numEdge = 0;
        this.Indegree = new int[numVertex];
        this.Mark = new int[numVertex];
        for (int i = 0; i < numVertex; i++) {
            this.Mark[i] = UNVISITED;
            this.Indegree[i] = 0;
        }
        this.graphList = new LinkList[numVertex];
    }

    public int VerticesNum() {
        return numVertex;
    }

    public int EdgeNum() {
        return numEdge;
    }

    public Edge FirstEdge(int oneVertex) {
        Edge myEdge = new Edge();
        myEdge.from = oneVertex;
        if (this.graphList[oneVertex] == null) {
            this.graphList[oneVertex] = new LinkList();
        }
        linkNode temp = this.graphList[oneVertex].head;
        if (temp.next != null) {
            myEdge.to = temp.next.vertex;
            myEdge.weight = temp.next.weight;
        }
        return myEdge;
    }

    public Edge NextEdge(Edge preEdge) {
        Edge myEdge = new Edge();
        myEdge.from = preEdge.from;
        linkNode temp = this.graphList[preEdge.from].head;
        while (temp.next != null && temp.next.vertex <= preEdge.to) {
            temp = temp.next;
        }
        if (temp.next != null) {
            myEdge.to = temp.next.vertex;
            myEdge.weight = temp.next.weight;
            return myEdge;
        } else {
            return null;
        }

    }

    public void setEdge(int fromVertex, int toVertex, int weight) {
        if (weight <= 0) {
            return;
        }
        if (this.graphList[fromVertex] == null) {
            graphList[fromVertex] = new LinkList();
        }
        linkNode temp = this.graphList[fromVertex].head;

        while (temp.next != null && temp.next.vertex < toVertex) {
            temp = temp.next;
        }
        if (temp.next == null) {
            temp.next = new linkNode(null);
            temp.next.vertex = toVertex;
            temp.next.weight = weight;
            this.numEdge++;
            this.Indegree[toVertex]++;
            return;
        }
        if (temp.next.vertex == toVertex) {
            temp.next.weight = weight;
            return;
        }
        if (temp.next.vertex > toVertex) {
            linkNode other = temp.next;
            temp.next = new linkNode(null);
            temp.next.vertex = toVertex;
            temp.next.weight = weight;
            temp.next.next = other;
            this.numEdge++;
            this.Indegree[toVertex]++;
            return;
        }
    }

    public void delEdge(int fromVertex, int toVertex) {
        linkNode temp = this.graphList[fromVertex].head;
        while (temp.next != null && temp.next.vertex > toVertex) {
            temp = temp.next;
        }
        if (temp.next == null) {
            return;
        }
        if (temp.next.vertex > toVertex) {
            return;
        }
        if (temp.next.vertex == toVertex) {
            linkNode other = temp.next.next;
            temp.next = other;
            numEdge--;
            Indegree[toVertex]--;
            return;
        }
    }

    public boolean isEdge(Edge oneEdge) {
        if (oneEdge == null) {
            return false;
        } else if (oneEdge.weight > 0 && oneEdge.weight < INFINITY && oneEdge.to >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public int FromVertex(Edge oneEdge) {
        return oneEdge.from;
    }

    public int ToVertex(Edge oneEdge) {
        return oneEdge.to;
    }

    public int Weight(Edge oneEdge) {
        return oneEdge.weight;
    }

    public void Floyd(Graph G) {
        int i, j, v;
        Distance = new Dist[G.VerticesNum()][G.VerticesNum()];
        for (i = 0; i < G.VerticesNum(); i++) {
            for (j = 0; j < G.VerticesNum(); j++) {
                Distance[i][j] = new Dist();
            }
        }
        for (i = 0; i < G.VerticesNum(); i++) {
            for (j = 0; j < G.VerticesNum(); j++) {
                if (i == j) {
                    Distance[i][j].length = 0;
                    Distance[i][j].pre = i;
                } else {
                    Distance[i][j].length = INFINITY;
                    Distance[i][j].pre = -1;
                }
            }
        }
        for (v = 0; v < G.VerticesNum(); v++) {
            for (Edge e = G.FirstEdge(v); G.isEdge(e); e = G.NextEdge(e)) {
                Distance[v][G.ToVertex(e)].length = G.Weight(e);
                Distance[v][G.ToVertex(e)].pre = v;
            }
        }
        for (v = 0; v < G.VerticesNum(); v++) {
            for (i = 0; i < G.VerticesNum(); i++) {
                for (j = 0; j < G.VerticesNum(); j++) {
                    if (Distance[i][j].length > Distance[i][v].length + Distance[v][j].length) {
                        Distance[i][j].length = Distance[i][v].length + Distance[v][j].length;
                        Distance[i][j].pre = Distance[v][j].pre;
                    }
                }
            }
        }
    }

    public int minVertex(Dist[] D) {
        int v = -1;
        for (int i = 0; i < this.VerticesNum(); i++) {
            if (this.Mark[i] == UNVISITED) {
                v = i;
                break;
            }
        }
        for (int i = 0; i < this.VerticesNum(); i++) {
            if (this.Mark[i] == UNVISITED && D[i].length < D[v].length) {
                v = i;
            }
        }
        return v;
    }

    public Edge[] Prim(int s,int VertexNum) {
        int MSTtag = 0;
        Edge[] MST = new Edge[this.numVertex - 1];
        Dist[] D;
        D = new Dist[this.numVertex];
        for(int i = 0;i < this.numVertex;i++) {
            D[i] = new Dist();
        }
        for(int i = 0;i < this.numVertex - 1;i++) {
            MST[i] = new Edge();
        }
        for (int i = 0; i < this.numVertex - 1; i++) {
            this.Mark[i] = UNVISITED;
            D[i].index = i;
            D[i].length = INFINITY;
            D[i].pre = s;
        }
        D[s].length = 0;
        this.Mark[s] = VISITED;
        int v = s;
        for (int i = 0; i < this.numVertex - 1; i++) {
            if (D[v].length == INFINITY) {
                return null;
            }
            for (Edge e = this.FirstEdge(v); this.isEdge(e); e = this.NextEdge(e)) {
                if (this.Mark[this.ToVertex(e)] != VISITED && D[this.ToVertex(e)].length > e.weight) {
                    D[this.ToVertex(e)].length = e.weight;
                    D[this.ToVertex(e)].pre = v;
                }
            }
            v = this.minVertex(D);
            this.Mark[v] = VISITED;
            Edge tmpEdge = new Edge(D[v].pre, D[v].index, D[v].length);
            MST[MSTtag] = tmpEdge;
            MSTtag++;
        }
        return MST;
    }

    public void readMatrixFromFile(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        String[] arr = new String[20];
        int[][] tmpMatrix = new int[20][20];
        try {
            reader = new BufferedReader(new FileReader(file));
            String readLine = null;
            int i = 0;
            while ((readLine = reader.readLine()) != null) {
                arr = readLine.split("\\s+");
                for (int j = 0; j < 20; j++) {
                    tmpMatrix[i][j] = Integer.parseInt(arr[j]);
                }
                i++;
                if (i == 20) {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (tmpMatrix[i][j] != -1) {
                    this.setEdge(i, j, tmpMatrix[i][j]);
                }
            }
        }
    }

    public void writeMatrixToFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BufferedWriter writer = null;
        int[][] tmpMatrix = new int[20][20];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                tmpMatrix[i][j] = -1;
            }
        }
        for (int i = 0; i < 20; i++) {
            for (Edge e = FirstEdge(i); isEdge(e); e = NextEdge(e)) {
                tmpMatrix[e.from][e.to] = e.weight;
                tmpMatrix[e.to][e.from] = e.weight;
            }
        }
        try {
            writer = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
                    writer.write("" + tmpMatrix[i][j]);
                    if (j < 19) {
                        writer.write(' ');
                    }
                }
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e1) {
                }
            }
        }
    }
}
