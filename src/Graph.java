import java.io.*;

public class Graph {
    private int numVertex;
    private int numEdge;
    private int[] Mark;
    private int[] Indegree;
    private Dist[][] Distance;
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

    public int getMark(int num) {
        return this.Mark[num];
    }

    public int getIndegree(int num) {
        return this.Indegree[num];
    }

    public Dist getDistance(int i, int j) {
        return this.Distance[i][j];
    }

    public int getVerticesNum() {
        return numVertex;
    }

    public int getEdgeNum() {
        return numEdge;
    }

    public Edge FirstEdge(int oneVertex) {
        Edge myEdge = new Edge();
        myEdge.setFrom(oneVertex);
        if (this.graphList[oneVertex] == null) {
            this.graphList[oneVertex] = new LinkList();
        }
        linkNode temp = this.graphList[oneVertex].getHead();
        if (temp.getNext() != null) {
            myEdge.setTo(temp.getNext().getVertex());
            myEdge.setWeight(temp.getNext().getWeight());
        }
        return myEdge;
    }

    public Edge NextEdge(Edge preEdge) {
        Edge myEdge = new Edge();
        myEdge.setFrom(preEdge.getFrom());
        ;
        linkNode temp = this.graphList[preEdge.getFrom()].getHead();
        while (temp.getNext() != null && temp.getNext().getVertex() <= preEdge.getTo()) {
            temp = temp.getNext();
        }
        if (temp.getNext() != null) {
            myEdge.setTo(temp.getNext().getVertex());
            ;
            myEdge.setWeight(temp.getNext().getWeight());
            ;
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
        linkNode temp = this.graphList[fromVertex].getHead();

        while (temp.getNext() != null && temp.getNext().getVertex() < toVertex) {
            temp = temp.getNext();
        }
        if (temp.getNext() == null) {
            temp.setNext(new linkNode(null));
            temp.getNext().setVertex(toVertex);
            temp.getNext().setWeight(weight);
            this.numEdge++;
            this.Indegree[toVertex]++;
            return;
        }
        if (temp.getNext().getVertex() == toVertex) {
            temp.getNext().setWeight(weight);
            return;
        }
        if (temp.getNext().getVertex() > toVertex) {
            linkNode other = temp.getNext();
            temp.setNext(new linkNode(null));;
            temp.getNext().setVertex(toVertex);
            temp.getNext().setWeight(weight);
            temp.getNext().setNext(other);
            this.numEdge++;
            this.Indegree[toVertex]++;
            return;
        }
    }

    public void delEdge(int fromVertex, int toVertex) {
        linkNode temp = this.graphList[fromVertex].getHead();
        while (temp.getNext() != null && temp.getNext().getVertex() != toVertex) {
            temp = temp.getNext();
        }
        if (temp.getNext() == null) {
            return;
        }
        if (temp.getNext().getVertex() > toVertex) {
            return;
        }
        if (temp.getVertex() == toVertex) {
            temp = null;
            numEdge--;
            Indegree[toVertex]--;
            return;
        }
        if (temp.getNext().getVertex() == toVertex) {
            linkNode other = temp.getNext().getNext();
            temp.setNext(other);
            numEdge--;
            Indegree[toVertex]--;
            return;
        }
    }

    public boolean isEdge(Edge oneEdge) {
        if (oneEdge == null) {
            return false;
        } else if (oneEdge.getWeight() > 0 && oneEdge.getWeight() < INFINITY && oneEdge.getTo() >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public int FromVertex(Edge oneEdge) {
        return oneEdge.getFrom();
    }

    public int ToVertex(Edge oneEdge) {
        return oneEdge.getTo();
    }

    public int Weight(Edge oneEdge) {
        return oneEdge.getWeight();
    }

    public void Floyd(Graph G) {
        int i, j, v;
        Distance = new Dist[G.getVerticesNum()][G.getVerticesNum()];
        for (i = 0; i < G.getVerticesNum(); i++) {
            for (j = 0; j < G.getVerticesNum(); j++) {
                Distance[i][j] = new Dist();
            }
        }
        for (i = 0; i < G.getVerticesNum(); i++) {
            for (j = 0; j < G.getVerticesNum(); j++) {
                if (i == j) {
                    Distance[i][j].setLength(0);
                    Distance[i][j].setPre(i);
                } else {
                    Distance[i][j].setLength(INFINITY);
                    Distance[i][j].setPre(-1);
                }
            }
        }
        for (v = 0; v < G.getVerticesNum(); v++) {
            for (Edge e = G.FirstEdge(v); G.isEdge(e); e = G.NextEdge(e)) {
                Distance[v][G.ToVertex(e)].setLength(G.Weight(e));
                Distance[v][G.ToVertex(e)].setPre(v);
            }
        }
        for (v = 0; v < G.getVerticesNum(); v++) {
            for (i = 0; i < G.getVerticesNum(); i++) {
                for (j = 0; j < G.getVerticesNum(); j++) {
                    if (Distance[i][j].getLength() > Distance[i][v].getLength() + Distance[v][j].getLength()) {
                        Distance[i][j].setLength(Distance[i][v].getLength() + Distance[v][j].getLength());
                        Distance[i][j].setPre(Distance[v][j].getPre());
                    }
                }
            }
        }
    }

    public int minVertex(Dist[] D) {
        int v = 0;
        for (int i = 0; i < this.getVerticesNum(); i++) {
            if (this.Mark[i] == UNVISITED) {
                v = i;
                break;
            }
        }
        for (int i = 0; i < this.getVerticesNum(); i++) {
            if (this.Mark[i] == UNVISITED && D[i].getLength() < D[v].getLength()) {
                v = i;
            }
        }
        return v;
    }

    public Edge[] Prim(int s) {
        int MSTtag = 0;
        Edge[] MST = new Edge[this.numVertex];
        Dist[] D;
        D = new Dist[this.numVertex];
        for (int i = 0; i < this.numVertex; i++) {
            D[i] = new Dist();
        }
        for (int i = 0; i < this.numVertex; i++) {
            MST[i] = new Edge();
        }
        for (int i = 0; i < this.numVertex; i++) {
            this.Mark[i] = UNVISITED;
            D[i].setIndex(i);
            ;
            D[i].setLength(INFINITY);
            D[i].setPre(s);
        }
        D[s].setLength(0);
        this.Mark[s] = VISITED;
        int v = s;
        for (int i = 0; i < this.numVertex; i++) {
            if (D[v].getLength() == INFINITY) {
                return null;
            }
            for (Edge e = this.FirstEdge(v); this.isEdge(e); e = this.NextEdge(e)) {
                if (this.Mark[this.ToVertex(e)] != VISITED && D[this.ToVertex(e)].getLength() > e.getWeight()) {
                    D[this.ToVertex(e)].setLength(e.getWeight());
                    D[this.ToVertex(e)].setPre(v);
                }
            }
            v = this.minVertex(D);
            this.Mark[v] = VISITED;
            Edge tmpEdge = new Edge(D[v].getPre(), D[v].getIndex(), D[v].getLength());
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
        this.numVertex = 0;
        this.numEdge = 0;
        int cnt = 0;
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                cnt += tmpMatrix[i][j];
                if (tmpMatrix[i][j] != -1) {
                    this.setEdge(i, j, tmpMatrix[i][j]);
                    this.numEdge++;
                }
            }
            if (cnt > -20) {
                this.numVertex++;
            }
            cnt = 0;
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
                tmpMatrix[e.getFrom()][e.getTo()] = e.getWeight();
                tmpMatrix[e.getTo()][e.getFrom()] = e.getWeight();
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
