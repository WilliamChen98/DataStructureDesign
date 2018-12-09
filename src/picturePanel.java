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
    private int edgeWeight;
    public final int DRAWMAP = 0;
    public final int DRAWPOINT = 1;
    public final int DELETEPOINT = 2;
    public final int ADDEDGE = 3;
    public final int SHORTESTPATH = 4;

    public picturePanel(Graph g, InfoCatcher i) {
        super();
        this.graph = g;
        this.info = i;
        this.paintMode = 0;
        this.setVisible(true);
        this.edgeWeight = -1;
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
        case SHORTESTPATH:
            this.shortestPath(g);
            break;
        }

    }

    private void paintMap(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.clearRect(0, 0, this.getWidth(), this.getHeight());
        Ellipse2D circle = new Ellipse2D.Double();
        for (int i = 0; i < 20; i++) {
            if (info.x[i] != -100) {
                circle.setFrame(info.x[i] - 30, info.y[i] - 30, 60, 60);
                g2.draw(circle);
            }
        }
        for (int i = 0; i < 20; i++) {
            for (Edge edge = graph.FirstEdge(i); graph.isEdge(edge); edge = graph.NextEdge(edge)) {
                Point2D from = new Point2D.Double(info.x[edge.from], info.y[edge.from]);
                Point2D to = new Point2D.Double(info.x[edge.to], info.y[edge.to]);
                if (from.getX() >= 0 && to.getX() >= 0) {
                    g2.draw(new Line2D.Double(from, to));
                    g2.drawString("" + edge.weight, (info.x[edge.from] + info.x[edge.to]) / 2 - 15,
                            (info.y[edge.from] + info.y[edge.to]) / 2 - 15);
                }
            }
        }
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                for (int i = 0; i < 20; i++) {
                    if (event.getX() >= info.x[i] - 30 && event.getX() <= info.x[i] + 30
                            && event.getY() >= info.y[i] - 30 && event.getY() <= info.y[i] + 30) {
                        index = i;
                        break;
                    }
                }
            }
        });
    }

    private void paintPoint() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                int i = 0;
                for (i = 0; i < 20; i++) {
                    if (info.x[i] == -100) {
                        break;
                    }
                }
                if (i < 20) {
                    info.x[i] = event.getX();
                    info.y[i] = event.getY();
                }
                setPaintMode(DRAWMAP);
                paintComponent();
            }
        });

    }

    private void deletePoint() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                int i = 0;
                for (i = 0; i < 20; i++) {
                    if (event.getX() >= info.x[i] - 30 && event.getX() <= info.x[i] + 30
                            && event.getY() >= info.y[i] - 30 && event.getY() <= info.y[i] + 30) {
                        Graphics2D g = (Graphics2D) getGraphics();
                        g.clearRect(info.x[i] - 30, info.y[i] - 30, 61, 61);
                        g.clearRect(event.getX() - 30, event.getY() - 30, 61, 61);
                        info.name[i] = "defaultName";
                        info.addr[i] = "defaultAddr";
                        info.tel[i] = "00000000";
                        info.x[i] = -100;
                        info.y[i] = -100;
                        info.isCapital[i] = false;
                        for (int j = 0; j < 20; j++) {
                            graph.delEdge(i, j);
                            graph.delEdge(j, i);
                        }
                        break;
                    }
                }
                setPaintMode(DRAWMAP);
                paintComponent();
            }
        });
    }

    private void addEdge() {
        graph.setEdge(indexStart, indexEnd, edgeWeight);
        graph.setEdge(indexEnd, indexStart, edgeWeight);
        setPaintMode(DRAWMAP);
        paintComponent();
    }

    private void shortestPath(Graphics g) {
        Graphics2D gra = (Graphics2D) g;
        graph.Floyd(graph);
        int i = graph.Distance[indexStart][indexEnd].pre;
        int j = indexEnd;
        do {
            Point2D from = new Point2D.Double(info.getX(i), info.getY(i));
            Point2D to = new Point2D.Double(info.getX(j), info.getY(j));
            Line2D line = new Line2D.Double(from, to);
            gra.setColor(Color.RED);
            gra.setStroke(new BasicStroke(5));
            gra.draw(line);
            j = i;
            i = graph.Distance[indexStart][i].pre;
        }while(i != indexStart);
    }
}
