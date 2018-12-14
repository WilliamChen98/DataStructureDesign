import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import javax.swing.*;

public class picturePanel extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 7637966191176096259L;
    private Graph graph;
    private InfoCatcher info;
    private int paintMode;
    private int indexStart;
    private int indexEnd;
    private int index;
    private Point2D point;
    private int edgeWeight;
    public static final int DRAWMAP = 0;
    public static final int DRAWPOINT = 1;
    public static final int DELETEPOINT = 2;
    public static final int ADDEDGE = 3;
    public static final int DELETEEDGE = 4;
    public static final int SHORTESTPATH = 5;
    public static final int MST = 6;

    public picturePanel(Graph g, InfoCatcher i) {
        super();
        this.graph = g;
        this.info = i;
        this.paintMode = 0;
        this.setVisible(true);
        this.edgeWeight = -1;
        this.point = new Point2D.Double();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                point = new Point2D.Double(event.getX(),event.getY());
                for (int i = 0; i < 20; i++) {
                    if (event.getX() >= info.getX(i) - 30 && event.getX() <= info.getX(i) + 30
                            && event.getY() >= info.getY(i) - 30 && event.getY() <= info.getY(i) + 30) {
                        index = i;
                        break;
                    }
                }
            }
        });
    }

    public Graph getGraph() {
        return this.graph;
    }

    public InfoCatcher getInfo() {
        return this.info;
    }

    public int getIndex() {
        return this.index;
    }

    public void setEdgeWeight(int weight) {
        this.edgeWeight = weight;
    }

    public void setPaintMode(int mode) {
        this.paintMode = mode;
    }

    public void setInfoCatcher(InfoCatcher infoToBeSet) {
        this.info = infoToBeSet;
    }

    public void setStartAndEndIndex(int start, int end) {
        indexStart = start;
        indexEnd = end;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void paintComponent() {
        Graphics g = this.getGraphics();
        super.paintComponents(g);
        switch (this.paintMode) {
        case DRAWMAP:
            this.paintMap(g);
            break;
        case DRAWPOINT:
            this.paintPoint();
            break;
        case DELETEPOINT:
            this.deletePoint();
            break;
        case ADDEDGE:
            this.addEdge();
            break;
        case DELETEEDGE:
            this.delEdge();
            break;
        case SHORTESTPATH:
            this.shortestPath(g);
            break;
        case MST:
            this.minimumSpanningTree(g);
            break;
        }

    }

    private void paintMap(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.clearRect(-150, -150, this.getWidth()+150, this.getHeight()+150);
        Ellipse2D circle = new Ellipse2D.Double();
        for (int i = 0; i < 20; i++) {
            if (info.getX(i) != -100) {
                circle.setFrame(info.getX(i) - 30, info.getY(i) - 30, 60, 60);
                if (info.getIsCapital(i)) {
                    g2.setColor(Color.GREEN);
                    g2.fillOval(info.getX(i) - 29, info.getY(i) - 29, 59, 59);
                } else {
                    g2.setColor(Color.CYAN);
                    g2.fillOval(info.getX(i) - 29, info.getY(i) - 29, 59, 59);
                }
                g2.setColor(Color.BLACK);
                g2.draw(circle);
                g2.drawString(info.getName(i), info.getX(i), info.getY(i));
            }
        }
        g2.setColor(Color.BLACK);
        for (int i = 0; i < 20; i++) {
            for (Edge edge = graph.getFirstEdge(i); graph.isEdge(edge); edge = graph.getNextEdge(edge)) {
                Point2D from = new Point2D.Double(info.getX(edge.getFrom()), info.getY(edge.getFrom()));
                Point2D to = new Point2D.Double(info.getX(edge.getTo()), info.getY(edge.getTo()));
                if (from.getX() >= 0 && to.getX() >= 0) {
                    g2.draw(new Line2D.Double(from, to));
                    g2.drawString("" + edge.getWeight(), (info.getX(edge.getFrom()) + info.getX(edge.getTo())) / 2 - 15,
                            (info.getY(edge.getFrom()) + info.getY(edge.getTo())) / 2 - 15);
                }
            }
        }
        
    }

    private void paintPoint() {
        int i = 0;
        for (i = 0; i < 20; i++) {
            if (info.getX(i) == -100) {
                break;
            }
        }
        if (i < 20) {
            info.setX((int)point.getX(), i);
            info.setY((int)point.getY(), i);
        }
        setPaintMode(DRAWMAP);
        paintComponent();
    }

    private void deletePoint() {
        info.setName("defaultName", index);
        info.setAddr("defaultAddr", index);
        info.setTel("0000-0000", index);
        info.setX(-100, index);
        info.setY(-100, index);
        info.setIsCapital(false, index);
        for (int j = 0; j < 20; j++) {
            graph.delEdge(index, j);
            graph.delEdge(j, index);
        }
        setPaintMode(DRAWMAP);
        paintComponent();
    }

    private void addEdge() {
        graph.addEdge(indexStart, indexEnd, edgeWeight);
        graph.addEdge(indexEnd, indexStart, edgeWeight);
        setPaintMode(DRAWMAP);
        paintComponent();
    }

    private void delEdge() {
        graph.delEdge(indexStart, indexEnd);
        graph.delEdge(indexEnd, indexStart);
        setPaintMode(DRAWMAP);
        paintComponent();
    }

    private void shortestPath(Graphics g) {
        setPaintMode(DRAWMAP);
        paintComponent();
        Graphics2D gra = (Graphics2D) g;
        graph.Floyd(graph);
        int i = graph.getDistance(indexStart, indexEnd).getPre();
        int j = indexEnd;
        do {
            Point2D from = new Point2D.Double(info.getX(i), info.getY(i));
            Point2D to = new Point2D.Double(info.getX(j), info.getY(j));
            Line2D line = new Line2D.Double(from, to);
            gra.setColor(Color.RED);
            gra.setStroke(new BasicStroke(5));
            gra.draw(line);
            j = i;
            i = graph.getDistance(indexStart, i).getPre();
        } while (j != indexStart);
    }

    private void minimumSpanningTree(Graphics g) {
        setPaintMode(DRAWMAP);
        paintComponent();
        Graphics2D gra = (Graphics2D) g;
        Edge[] mst = new Edge[19];
        mst = graph.Prim(0);
        for (int i = 0; graph.isEdge(mst[i]); i++) {
            Line2D line = new Line2D.Double(info.getX(mst[i].getFrom()), info.getY(mst[i].getFrom()),
                    info.getX(mst[i].getTo()), info.getY(mst[i].getTo()));
            gra.setColor(Color.BLUE);
            gra.setStroke(new BasicStroke(5));
            gra.draw(line);
        }
    }
}
